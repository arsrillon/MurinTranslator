package Main;

import java.util.Map;
import java.util.Set;

public class TranslationService {

    private final Map<String, String> directDict;
    private final Map<String, String> reverseDict;
    private final Set<String> exceptions;

    public TranslationService(DictionaryRepository repository) {
        this.directDict = repository.getDirectDict();
        this.reverseDict = repository.getReverseDict();
        this.exceptions = repository.getExceptions();
    }

    public String translate(String text, boolean isDirect) {
        if (text == null || text.trim().isEmpty()) return "";

        StringBuilder result = new StringBuilder();
        String[] tokens = text.split("(?=[\\s\\p{Punct}])|(?<=[\\s\\p{Punct}])");

        for (String token : tokens) {
            if (token.matches("[а-яА-ЯёЁ]+")) {
                if (isDirect) {
                    result.append(processToMurin(token));
                } else {
                    result.append(processToRussian(token));
                }
            } else {
                result.append(token);
            }
        }
        return result.toString();
    }

    private String processToMurin(String word) {
        String lower = word.toLowerCase();

        if (exceptions.contains(lower)) return word;
        if (directDict.containsKey(lower)) return matchCase(word, directDict.get(lower));
        if (lower.length() <= 2) return word;

        String base = lower;
        if (lower.endsWith("и") && !lower.endsWith("ий") && !lower.endsWith("ций")) {
            base = lower;
        } else {
            base = lower.replaceAll("(ый|ий|ая|яя|ое|ее|а|я|о|е|ь|ы|у|ю)$", "");
        }

        String result = base + "ность";
        result = result.replace("нность", "ность");
        return matchCase(word, result);
    }

    private String processToRussian(String word) {
        String lower = word.toLowerCase();

        if (exceptions.contains(lower)) return word;

        if (reverseDict.containsKey(lower)) {
            return matchCase(word, reverseDict.get(lower));
        }

        if (lower.endsWith("ность")) {
            String base = lower.substring(0, lower.length() - 5);
            return matchCase(word, base);
        }

        return word;
    }

    private String matchCase(String original, String translated) {
        if (original.isEmpty()) return translated;
        if (Character.isUpperCase(original.charAt(0))) {
            if (translated.length() > 1) {
                return translated.substring(0, 1).toUpperCase() + translated.substring(1);
            } else {
                return translated.toUpperCase();
            }
        }
        return translated;
    }
}
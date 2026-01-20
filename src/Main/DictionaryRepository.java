package Main;

import java.util.*;

public class DictionaryRepository {
    private final Map<String, String> directDict = new HashMap<>();   // Рус -> Мур
    private final Map<String, String> reverseDict = new HashMap<>();  // Мур -> Рус
    private final Set<String> exceptions = new HashSet<>();

    public DictionaryRepository() {
        initData();
    }

    private void initData() {

        addPairs("друг", "друн", "друга", "друна", "другу", "друну", "другом", "друном", "друге", "друне", "друзья", "друни", "друзей", "друней");
        addPairs("враг", "вран", "врага", "врана", "врагу", "врану", "врагом", "враном", "враге", "вране", "враги", "врани");
        addPairs("дед", "дод", "деда", "дода", "деду", "доду", "дедом", "додом", "деде", "доде");
        addPairs("мама", "мом", "мамы", "момы", "маме", "моме", "маму", "мому", "мамой", "момой");
        addPairs("сын", "сыр", "сына", "сыра", "сыну", "сыру", "сыном", "сыром", "сыне", "сыре", "сыновья", "сыровья");

        addPairs("учитель", "учиха", "учителя", "учиху", "учителю", "учихе", "учителем", "учихой", "учителе", "учихе");

        addPairs("жена", "жинка", "жены", "жинки", "жене", "жинке", "жену", "жинку", "женой", "жинкой");

        addPairs("девушка", "дедушка", "девушки", "дедушки", "девушке", "дедушке", "девушку", "дедушку", "девушкой", "дедушкой");

        addPairs("я", "ч");

        exceptions.add("что");

        List<String> personalPronouns = Arrays.asList(
                "ты", "тебя", "тебе", "тобой",
                "вы", "вас", "вам", "вами",
                "мы", "нас", "нам", "нами",
                "он", "его", "ему", "им", "нем",
                "она", "ее", "ей", "ею", "ней",
                "оно",
                "они", "их", "ими", "них"
        );
        exceptions.addAll(personalPronouns);

        List<String> possessivePronouns = Arrays.asList(
                "свой", "своя", "свое", "свои", "своего", "своей", "своих", "своему", "своим", "свою", "своими", "своем",
                "мой", "моя", "мое", "мои", "моего", "моей", "моих", "моему", "моим", "мою", "моими", "моем",
                "твой", "твоя", "твое", "твои", "твоего", "твоей", "твоих", "твоему", "твоим", "твою", "твоими", "твоем",
                "наш", "наша", "наше", "наши", "нашего", "нашей", "наших", "нашему", "нашим", "нашу", "нашими", "нашем",
                "ваш", "ваша", "ваше", "ваши", "вашего", "вашей", "ваших", "вашему", "вашим", "вашу", "вашими", "вашем"
        );
        exceptions.addAll(possessivePronouns);

        List<String> prepositions = Arrays.asList(
                "и", "а", "но", "или", "в", "на", "под", "за", "из", "от", "к", "по", "не", "ни", "до", "у", "со", "об"
        );
        exceptions.addAll(prepositions);

        exceptions.add("фог");
        exceptions.add("фогость");
    }

    private void addPairs(String... args) {
        for (int i = 0; i < args.length; i += 2) {
            if (i + 1 < args.length) {
                String rus = args[i];
                String mur = args[i + 1];
                directDict.put(rus, mur);
                reverseDict.putIfAbsent(mur, rus);
            }
        }
    }

    public Map<String, String> getDirectDict() { return directDict; }
    public Map<String, String> getReverseDict() { return reverseDict; }
    public Set<String> getExceptions() { return exceptions; }
}
package Main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MurinTranslatorApp extends Application {

    private TranslationService service;
    private boolean isDirectDirection = true;

    private Label directionLabel;
    private TextArea inputArea;
    private TextArea outputArea;

    @Override
    public void init() {
        DictionaryRepository repository = new DictionaryRepository();
        service = new TranslationService(repository);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Муринский Переводчик");

        directionLabel = new Label("Русский -> Муринский");
        directionLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        directionLabel.setStyle("-fx-text-fill: #2c3e50;");

        Button swapButton = new Button("⇄");
        swapButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 15; -fx-cursor: hand;");
        swapButton.setOnAction(e -> toggleDirection());

        HBox headerBox = new HBox(10, directionLabel, swapButton);
        headerBox.setAlignment(Pos.CENTER);

        inputArea = new TextArea();
        inputArea.setPromptText("Введите текст...");
        inputArea.setWrapText(true);
        inputArea.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");

        outputArea = new TextArea();
        outputArea.setPromptText("Результат...");
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setStyle("-fx-control-inner-background: #ecf0f1; -fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");

        Button translateButton = new Button("ПЕРЕВЕСТИ");
        translateButton.setMaxWidth(Double.MAX_VALUE);
        translateButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        translateButton.setOnAction(event -> performTranslation());

        VBox root = new VBox(15, headerBox, inputArea, translateButton, outputArea);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #e6eef5;");
        VBox.setVgrow(inputArea, Priority.ALWAYS);
        VBox.setVgrow(outputArea, Priority.ALWAYS);

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void toggleDirection() {
        isDirectDirection = !isDirectDirection;
        directionLabel.setText(isDirectDirection ? "Русский -> Муринский" : "Муринский -> Русский");

        String currentOutput = outputArea.getText();
        if (!currentOutput.isEmpty()) {
            inputArea.setText(currentOutput);
            outputArea.clear();
        }
    }

    private void performTranslation() {
        String text = inputArea.getText();
        String result = service.translate(text, isDirectDirection);
        outputArea.setText(result);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
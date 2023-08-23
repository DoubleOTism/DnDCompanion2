import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends Application {
    private TabPane tabPane = new TabPane();

    public static void main(String[] args) {
        launch(args);
    }

    private List<CharacterData> characterDataList = new ArrayList<>();
    private List<CharacterTab> characterTabs = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1200, 900);

        root.setCenter(tabPane);

        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Character App");
        root.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu startMenu = new Menu("Start");
        MenuItem newCharacterMenuItem = new MenuItem("Nový Charakter");
        newCharacterMenuItem.setOnAction(event -> showNewCharacterDialog());
        MenuItem loadCharacters = new MenuItem("Uložit charaktery");
        loadCharacters.setOnAction(actionEvent -> saveCharacterData(characterDataList, "characterData.ser"));
        MenuItem saveCharacters = new MenuItem("Načíst charaktery");
        saveCharacters.setOnAction(actionEvent -> loadCharacterData("characterData.ser"));
        startMenu.getItems().addAll(newCharacterMenuItem, saveCharacters,loadCharacters);


        menuBar.getMenus().add(startMenu);
        return menuBar;
    }

    private void showNewCharacterDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Nový Charakter");

        VBox creationVBox = new VBox();
        TextField characterNameField = new TextField();
        characterNameField.setPromptText("Jméno postavy");
        TextField characterRaceField = new TextField();
        characterRaceField.setPromptText("Rasa postavy");

        CheckBox useCustomStatsCheckbox = new CheckBox("Použít vlastní statistiky");
        Button addButton = new Button("Vytvořit");
        Button cancelButton = new Button("Zrušiť");

        TextField strengthField = new TextField();
        strengthField.setPromptText("Síla");
        TextField defenseField = new TextField();
        defenseField.setPromptText("Obrana");
        TextField luckField = new TextField();
        luckField.setPromptText("Štěstí");
        TextField intelligenceField = new TextField();
        intelligenceField.setPromptText("Inteligence");
        TextField perceptionField = new TextField();
        perceptionField.setPromptText("Vnímavost");
        TextField speechField = new TextField();
        speechField.setPromptText("Výřečnost");
        TextField accuracyField = new TextField();
        accuracyField.setPromptText("Přesnost");

        List<TextField> customStatNameFields = new ArrayList<>();
        List<TextField> customStatValueFields = new ArrayList<>();



        VBox customStatsVBox = new VBox();

        useCustomStatsCheckbox.setOnAction(event -> {
            customStatsVBox.getChildren().clear();

            if (useCustomStatsCheckbox.isSelected()) {
                Button addCustomStatButton = new Button("Přidat nový stat");
                addCustomStatButton.setOnAction(addEvent -> {
                    TextField newStatNameField = new TextField();
                    newStatNameField.setPromptText("Název statu");
                    TextField newStatValueField = new TextField();
                    newStatValueField.setPromptText("Hodnota statu");
                    customStatNameFields.add(newStatNameField);
                    customStatValueFields.add(newStatValueField);
                    HBox statEntry = new HBox(newStatNameField, newStatValueField);
                    customStatsVBox.getChildren().add(customStatsVBox.getChildren().size() - 1, statEntry);
                });

                customStatsVBox.getChildren().add(addCustomStatButton);
            }
        });
        creationVBox.getChildren().addAll(characterNameField, characterRaceField, useCustomStatsCheckbox, strengthField, defenseField, luckField, intelligenceField, perceptionField, speechField, accuracyField,customStatsVBox, addButton, cancelButton);
        creationVBox.setSpacing(10);
        creationVBox.setPadding(new Insets(10));


        addButton.setOnAction(event -> {
            String name = characterNameField.getText();
            String race = characterRaceField.getText();
            Map<String, Integer> baseStats = new HashMap<>();
            baseStats.put("Síla", parseIntOrDefault(strengthField.getText()));
            baseStats.put("Obrana", parseIntOrDefault(defenseField.getText()));
            baseStats.put("Štěstí", parseIntOrDefault(luckField.getText()));
            baseStats.put("Inteligence", parseIntOrDefault(intelligenceField.getText()));
            baseStats.put("Vnímavost", parseIntOrDefault(perceptionField.getText()));
            baseStats.put("Výřečnost", parseIntOrDefault(speechField.getText()));
            baseStats.put("Přesnost", parseIntOrDefault(accuracyField.getText()));

            Map<String, Integer> customStats = new HashMap<>();
            for (int i = 0; i < customStatNameFields.size(); i++) {
                String statName = customStatNameFields.get(i).getText();
                if (!statName.isEmpty()) {
                    int statValue = parseIntOrDefault(customStatValueFields.get(i).getText());
                    customStats.put(statName, statValue);
                }
            }

            createNewCharacterTab(name, race, baseStats, customStats);
            dialog.close();

        });
        cancelButton.setOnAction(event -> dialog.close());
        Scene dialogScene = new Scene(creationVBox, 300, 800);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    private void createNewCharacterTab(String characterName, String characterRace, Map<String, Integer> baseStats, Map<String, Integer> customStats) {
        CharacterData characterData = new CharacterData(characterName, characterRace, baseStats, customStats);
        CharacterTab characterTab = new CharacterTab(characterData);
        characterDataList.add(characterData);
        characterTabs.add(characterTab);
        tabPane.getTabs().add(characterTab);
    }


    private void saveCharacterData(List<CharacterData> characterDataList, String filename) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(characterDataList);
            System.out.println("Character data saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int parseIntOrDefault(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void loadCharacterData(String filename) {
        List<CharacterData> loadedCharacterDataList;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            loadedCharacterDataList = (List<CharacterData>) inputStream.readObject();
            System.out.println("Character data loaded from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        for (CharacterData loadedCharacterData : loadedCharacterDataList) {
            CharacterTab loadedCharacterTab = new CharacterTab(loadedCharacterData);
            characterDataList.add(loadedCharacterData);
            characterTabs.add(loadedCharacterTab);
            tabPane.getTabs().add(loadedCharacterTab);
        }
    }




}

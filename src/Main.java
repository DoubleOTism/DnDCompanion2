import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    private TabPane tabPane = new TabPane();
    public static void main(String[] args) {
        launch(args);
    }
    CharacterData characterData;
    private List<CharacterData> characterDataList = new ArrayList<>();
    private List<CharacterTab> characterTabs = new ArrayList<>();
    private Stage primaryStage;
    private ScheduledExecutorService autosaveExecutor;
    BorderPane root = new BorderPane();
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Scene scene = new Scene(root, 1200, 900);
        Image icon = new Image("icon.png");


        primaryStage.getIcons().add(icon);

        root.setCenter(tabPane);

        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        primaryStage.setScene(scene);
        primaryStage.setTitle("DnD Companion 2");
        root.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));
        primaryStage.show();
        primaryStage.setResizable(false);
        loadLatestCharacterData();
        setupAutosave();

        primaryStage.setOnCloseRequest(event -> {
            event.consume(); // Consume the close event to prevent immediate app exit

            // Create an alert to confirm the exit
            Alert exitConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            exitConfirmation.setTitle("Odejít");
            exitConfirmation.setHeaderText("Odchod z aplikace DnD Companion 2");
            exitConfirmation.setContentText("Jsi si jistý, že chceš odejít? Data charakterů se ti automaticky uloží.");

            // Add buttons to the alert
            exitConfirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            // Show the alert and handle the user's choice
            exitConfirmation.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.YES) {
                    // Save the data here
                    saveCharacterData();

                    // Exit the application
                    Platform.exit();
                    primaryStage.close();
                    System.exit(0);
                }
            });
        });
    }

    private void manualSaveCharacterData(List<CharacterData> characterDataList) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Manuální uložení dat");

        // Set extension filters if needed (e.g., to filter by .dat files)
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Data Files (*.dat)", "*.dat");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
                outputStream.writeObject(characterDataList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void manualLoadCharacterData() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Manuální načtení dat");

        // Set extension filters if needed (e.g., to filter by .dat files)
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Data Files (*.dat)", "*.dat");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            List<CharacterData> loadedCharacterDataList;
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
                loadedCharacterDataList = (List<CharacterData>) inputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }

            // Clear existing character tabs
            tabPane.getTabs().clear();
            characterDataList.clear();

            // Create character tabs and add loaded data
            for (CharacterData loadedCharacterData : loadedCharacterDataList) {
                CharacterTab loadedCharacterTab = new CharacterTab(loadedCharacterData);
                characterDataList.add(loadedCharacterData);
                characterTabs.add(loadedCharacterTab);
                tabPane.getTabs().add(loadedCharacterTab);

                // Update equipped items' buttons based on saved data
                Map<String, String> equippedItemNames = loadedCharacterData.getEquippedItemNames();
                List<EquipmentSlotHandler> slotHandlers = loadedCharacterTab.getSlotHandlers();
                for (EquipmentSlotHandler slotHandler : slotHandlers) {
                    Button slotButton = slotHandler.getSlotButton();
                    String equippedItemName = equippedItemNames.get(slotHandler.getUniqueIdentifier()); // Use uniqueIdentifier
                    slotHandler.updateSlotButtonText(slotButton, equippedItemName);
                }
            }
        }
    }




    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu startMenu = new Menu("Start");
        MenuItem newCharacterMenuItem = new MenuItem("Nový Charakter");
        newCharacterMenuItem.setOnAction(event -> showNewCharacterDialog());
        startMenu.getItems().addAll(newCharacterMenuItem);

        Menu akceMenu = new Menu("Akce");
        MenuItem loadCharacters = new MenuItem("Manuálně uložit charaktery");
        loadCharacters.setOnAction(actionEvent -> manualSaveCharacterData(characterDataList));
        MenuItem saveCharacters = new MenuItem("Manuálně načíst charaktery");
        saveCharacters.setOnAction(actionEvent -> manualLoadCharacterData());
        MenuItem loadAutosaveFile = new MenuItem("Načíst poslední automatické uložení");
        loadAutosaveFile.setOnAction(event -> {loadAutosaveCharacterData();});
        akceMenu.getItems().addAll(loadAutosaveFile, loadCharacters, saveCharacters);

        Menu viceMenu = new Menu("Aplikace");
        MenuItem about = new MenuItem("O Aplikaci");
        about.setOnAction(event -> {
            Alert exitConfirmation = new Alert(Alert.AlertType.INFORMATION);
            exitConfirmation.setTitle("O Aplikaci");
            exitConfirmation.setHeaderText("DnD Companion 2, verze R1");
            exitConfirmation.setContentText("Aplikace specificky určena pro užití na DC serveru Ředitelství. \nPokud si chceš vytvořit nový charakter, jdi do Start>Nový Charakter \nNepoužívej manuální uložení a načtení pokud nemusíš, vše se načte při zapnutí a uloží při odchodu a autosaves jsou v intervalech 5 minut. \nPro více info se ptej na DC. \n\nVytvořil Erich Pross (King Erich the Terrible/DoubleOTism) za PyJCollective, 2023.");
            exitConfirmation.showAndWait();
        });
        MenuItem exit = new MenuItem("Odejít");
        exit.setOnAction(event -> {
            Alert exitConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            exitConfirmation.setTitle("Odejít");
            exitConfirmation.setHeaderText("Odchod z aplikace DnD Companion 2");
            exitConfirmation.setContentText("Jsi si jistý, že chceš odejít? Data charakterů se ti automaticky uloží.");

            // Add buttons to the alert
            exitConfirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            // Show the alert and handle the user's choice
            exitConfirmation.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.YES) {
                    // Save the data here
                    saveCharacterData();

                    // Exit the application
                    Platform.exit();
                    primaryStage.close();
                    System.exit(0);
                }
            });
        });
        viceMenu.getItems().addAll(about, exit);



        menuBar.getMenus().addAll(startMenu, akceMenu, viceMenu);
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
        TextField hpField = new TextField();
        hpField.setPromptText("Životy");

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
        creationVBox.getChildren().addAll(characterNameField, characterRaceField, useCustomStatsCheckbox, strengthField, defenseField, luckField, intelligenceField, perceptionField, speechField, accuracyField, hpField,customStatsVBox, addButton, cancelButton);
        creationVBox.setSpacing(10);
        creationVBox.setPadding(new Insets(10));


        addButton.setOnAction(event -> {
            String name = characterNameField.getText();
            String race = characterRaceField.getText();
            Integer health = Integer.valueOf(hpField.getText());
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
            List<EquipmentItem> allItems = new ArrayList<>();
            List<EquipmentItem> equippedItems = new ArrayList<>();
            Map<Button, String> equippedItemNames = new HashMap<>();
            createNewCharacterTab(name, race, health, baseStats, customStats, allItems, equippedItems, equippedItemNames);
            dialog.close();

        });
        cancelButton.setOnAction(event -> dialog.close());
        Scene dialogScene = new Scene(creationVBox, 300, 800);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }
    private void createNewCharacterTab(String characterName, String characterRace, Integer health, Map<String, Integer> baseStats, Map<String, Integer> customStats, List<EquipmentItem> allItems, List<EquipmentItem> equippedItems,Map<Button, String> equippedItemNames) {
        CharacterData characterData = new CharacterData(characterName, characterRace, health, baseStats, customStats, allItems, equippedItems, "Prázdné ruce");
        CharacterTab characterTab = new CharacterTab(characterData);
        characterDataList.add(characterData);
        characterTabs.add(characterTab);
        tabPane.getTabs().add(characterTab);
    }

    private int parseIntOrDefault(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String getDocumentsDirectory() {
        String userHome = System.getProperty("user.home");
        String documentsPath = userHome + File.separator + "Documents" + File.separator + "PyJCollective" + File.separator + "DnDCompanion2" + File.separator + "saves";

        createDirectoriesIfNeeded(documentsPath);

        return documentsPath;
    }

    private void createDirectoriesIfNeeded(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
            } else {
            }
        }
    }


    private String getAutosavesDirectory() {
        String userHome = System.getProperty("user.home");
        String documentsPath = userHome + File.separator + "Documents" + File.separator + "PyJCollective" + File.separator + "DnDCompanion2" + File.separator + "autosaves";

        createAutosavesDirectoriesIfNeeded(documentsPath);

        return documentsPath;
    }

    private void createAutosavesDirectoriesIfNeeded(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
            } else {
            }
        }
    }

    public void autosaveCharacterData() {
        String documentsDirectory = getAutosavesDirectory();
        String filename = documentsDirectory + File.separator + "autosave" + ".dat";

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(characterDataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAutosaveCharacterData() {
        characterDataList.clear();
        characterTabs.clear();
        tabPane.getTabs().clear();

        List<CharacterData> loadedCharacterDataList;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(getAutosavesDirectory()+File.separator + "autosave.dat"))) {
            loadedCharacterDataList = (List<CharacterData>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        for (CharacterData loadedCharacterData : loadedCharacterDataList) {
            CharacterTab loadedCharacterTab = new CharacterTab(loadedCharacterData);
            characterDataList.add(loadedCharacterData);
            characterTabs.add(loadedCharacterTab);
            tabPane.getTabs().add(loadedCharacterTab);

            // Update equipped items' buttons based on saved data
            Map<String, String> equippedItemNames = loadedCharacterData.getEquippedItemNames();
            List<EquipmentSlotHandler> slotHandlers = loadedCharacterTab.getSlotHandlers();
            for (EquipmentSlotHandler slotHandler : slotHandlers) {
                Button slotButton = slotHandler.getSlotButton();
                String equippedItemName = equippedItemNames.get(slotHandler.getUniqueIdentifier()); // Use uniqueIdentifier
                slotHandler.updateSlotButtonText(slotButton, equippedItemName);
            }
        }
    }

    private void setupAutosave() {
        autosaveExecutor = Executors.newScheduledThreadPool(1);
        long initialDelay = 0;
        long period = 5;

        autosaveExecutor.scheduleAtFixedRate(this::autosaveCharacterData, initialDelay, period, TimeUnit.MINUTES);
    }





    private String generateTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return now.format(formatter);
    }

    public void saveCharacterData() {
        String documentsDirectory = getDocumentsDirectory();
        String timestamp = generateTimestamp();
        String filename = documentsDirectory + File.separator + "save" + timestamp + ".dat";

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(characterDataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void loadLatestCharacterData() {
        String documentsDirectory = getDocumentsDirectory();
        File[] saveFiles = new File(documentsDirectory).listFiles((dir, name) -> name.startsWith("save") && name.endsWith(".dat"));

        if (saveFiles != null && saveFiles.length > 0) {
            // Sort the files by name (which includes the timestamp) to find the latest one
            File latestSaveFile = getLatestFile(saveFiles);

            if (latestSaveFile != null) {
                try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(latestSaveFile))) {
                    List<CharacterData> loadedCharacterDataList = (List<CharacterData>) inputStream.readObject();

                    characterDataList.clear();
                    characterTabs.clear();
                    tabPane.getTabs().clear();

                    for (CharacterData loadedCharacterData : loadedCharacterDataList) {
                        CharacterTab loadedCharacterTab = new CharacterTab(loadedCharacterData);
                        characterDataList.add(loadedCharacterData);
                        characterTabs.add(loadedCharacterTab);
                        tabPane.getTabs().add(loadedCharacterTab);

                        // Update equipped items' buttons based on saved data
                        Map<String, String> equippedItemNames = loadedCharacterData.getEquippedItemNames();
                        List<EquipmentSlotHandler> slotHandlers = loadedCharacterTab.getSlotHandlers();
                        for (EquipmentSlotHandler slotHandler : slotHandlers) {
                            Button slotButton = slotHandler.getSlotButton();
                            String equippedItemName = equippedItemNames.get(slotHandler.getUniqueIdentifier());
                            slotHandler.updateSlotButtonText(slotButton, equippedItemName);
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Vítej");
            alert.setHeaderText("Vítej v DnDCompanionu 2");
            alert.setContentText("Vypadá to, že jsi zde poprvé (nemáš žádné uložené charaktery), pro nový charakter jdi do Start>Nový charakter. \nJdi do Více>O aplikaci než začneš aplikaci používat. \n \nJakékoliv chyby mi hlaš na DC, zkusím je opravit. \n \nHodně štěstí s používáním, \nErich za PyJCollective.");
            alert.showAndWait();
        }
    }


    private File getLatestFile(File[] files) {
        File latestFile = null;
        String latestTimestamp = "";

        for (File file : files) {
            String filename = file.getName();
            String timestamp = filename.substring(filename.indexOf("save") + 4, filename.indexOf(".dat"));

            if (timestamp.compareTo(latestTimestamp) > 0) {
                latestTimestamp = timestamp;
                latestFile = file;
            }
        }

        return latestFile;
    }

}
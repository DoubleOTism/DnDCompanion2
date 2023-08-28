import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.*;

public class CharacterTab extends Tab {
    private CharacterData characterData;
    private CharacterTab characterTab;
    private VBox rightBox = new VBox();
    private List<EquipmentSlotHandler> slotHandlers = new ArrayList<>();
    ProgressBar xpProgressBar = new ProgressBar();
    Label xpInfoLabel = new Label();
    Label hpLabel = new Label();

    VBox statsVBox = new VBox();
    ListView<String> itemsListView = new ListView<>();






    public CharacterTab(CharacterData characterData) {
        this.characterData = characterData;
        this.characterTab = this;
        ImageView characterImageView = createCharacterImageView();
        BorderPane borderPane = new BorderPane();
        HBox mainBox = new HBox();

        Label characterName = new Label(characterData.getCharacterName());
        Label characterRace = new Label("Rasa: " + characterData.getCharacterRace());


        characterName.setStyle("-fx-text-fill: White; -fx-font-size: 20px;");
        characterRace.setStyle("-fx-text-fill: White; -fx-font-size: 16px;");

    //Tlačítka pro výbavu
        List<EquipmentItem> allItems = characterData.getInventory();
        List<EquipmentItem> equippedItems = characterData.getEquippedItems();
        //Helma
        Button equipHelmetButton = new Button("Helma");
        EquipmentSlotHandler helmaSlotHandler = new EquipmentSlotHandler("helmaSlot","Helma", equipHelmetButton, equippedItems, allItems, characterData, characterTab);
        equipHelmetButton.setLayoutX(500);
        equipHelmetButton.setLayoutY(40);
        //Chestplate
        Button equipChestplateButton = new Button("Hrudní plát");
        EquipmentSlotHandler chestplateSlotHandler = new EquipmentSlotHandler("hrudSlot","Hrudní plát", equipChestplateButton, equippedItems, allItems, characterData, characterTab);
        equipChestplateButton.setLayoutX(500);
        equipChestplateButton.setLayoutY(150);
        //Ruce
        Button equipArmsButton = new Button("Zbroj rukou");
        EquipmentSlotHandler armsSlotHandler = new EquipmentSlotHandler("ruceSlot","Zbroj rukou", equipArmsButton, equippedItems, allItems, characterData, characterTab);
        equipArmsButton.setLayoutX(500);
        equipArmsButton.setLayoutY(250);
        //Zbroj nohou
        Button equipLeggingsButton = new Button("Zbroj nohou");
        EquipmentSlotHandler leggingsSlotHandler = new EquipmentSlotHandler("nohySlot","Zbroj nohou", equipLeggingsButton, equippedItems, allItems, characterData, characterTab);
        equipLeggingsButton.setLayoutX(500);
        equipLeggingsButton.setLayoutY(400);
        //Boty
        Button equipBootsButton = new Button("Boty");
        EquipmentSlotHandler bootsSlotHandler = new EquipmentSlotHandler("botySlot","Boty", equipBootsButton, equippedItems, allItems, characterData, characterTab);
        equipBootsButton.setLayoutX(500);
        equipBootsButton.setLayoutY(500);
        //Prsteny
        Button equipRing1Button = new Button("Prsten");
        EquipmentSlotHandler ring1SlotHandler = new EquipmentSlotHandler("prsten1Slot","Prsten", equipRing1Button, equippedItems, allItems, characterData, characterTab);
        equipRing1Button.setLayoutX(20);
        equipRing1Button.setLayoutY(80);

        Button equipRing2Button = new Button("Prsten");
        EquipmentSlotHandler ring2SlotHandler = new EquipmentSlotHandler("prsten2Slot","Prsten", equipRing2Button, equippedItems, allItems, characterData, characterTab);
        equipRing2Button.setLayoutX(20);
        equipRing2Button.setLayoutY(120);

        Button equipRing3Button = new Button("Prsten");
        EquipmentSlotHandler ring3SlotHandler = new EquipmentSlotHandler("prsten3Slot","Prsten", equipRing3Button, equippedItems, allItems, characterData, characterTab);
        equipRing3Button.setLayoutX(20);
        equipRing3Button.setLayoutY(160);

        Button equipRing4Button = new Button("Prsten");
        EquipmentSlotHandler ring4SlotHandler = new EquipmentSlotHandler("prsten4Slot","Prsten", equipRing4Button, equippedItems, allItems, characterData, characterTab);
        equipRing4Button.setLayoutX(20);
        equipRing4Button.setLayoutY(200);

        slotHandlers.add(helmaSlotHandler);
        slotHandlers.add(chestplateSlotHandler);
        slotHandlers.add(armsSlotHandler);
        slotHandlers.add(leggingsSlotHandler);
        slotHandlers.add(bootsSlotHandler);
        slotHandlers.add(ring1SlotHandler);
        slotHandlers.add(ring2SlotHandler);
        slotHandlers.add(ring3SlotHandler);
        slotHandlers.add(ring4SlotHandler);



        Pane buttonOverlayPane = new Pane();
        buttonOverlayPane.getChildren().addAll(equipHelmetButton, equipChestplateButton, equipArmsButton, equipLeggingsButton, equipBootsButton, equipRing1Button, equipRing2Button, equipRing3Button, equipRing4Button);

        StackPane imagePane = new StackPane(characterImageView, buttonOverlayPane);





        setText(characterData.getCharacterName());
        VBox leftBox = new VBox();
        Button newArmorButton = new Button("Přidat novou zbroj");
        newArmorButton.setOnAction(event -> showEquipmentDialog());


        Button addStatButton = new Button("Přidat vlastní stat");
        addStatButton.setOnAction(event -> showAddStatDialog());




        //xp handle

        xpProgressBar.setPrefWidth(200); // Set your preferred width
        xpProgressBar.setProgress(characterData.getXPProgress());
        System.out.println(characterData.getXPProgress());
        Button addXPButton = new Button("Přidat XP");
        addXPButton.setOnAction(event -> addXP());



        updateItemsListView();
        updateHPLabel();
        hpLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        updateXPLabel();
        Region spacer = new Region();
        spacer.setMinWidth(200);
        HBox levelManagement = new HBox(xpProgressBar, xpInfoLabel, spacer, hpLabel);
        VBox bottomVBox = new VBox();
        Button modifyHP = new Button("Upravit HP");
        modifyHP.setOnAction(event -> showModifyHPDialog());
        HBox bottomBox = new HBox(addStatButton, newArmorButton, addXPButton, modifyHP);
        bottomVBox.getChildren().addAll(bottomBox, levelManagement);
        bottomBox.setAlignment(Pos.BOTTOM_CENTER);
        bottomBox.setSpacing(10);
        bottomBox.setPrefWidth(1200);




        leftBox.getChildren().addAll(imagePane, characterName, characterRace);
        updateTotalStatsLabel(characterData.getTotalStats());
        mainBox.getChildren().addAll(leftBox, rightBox);
        rightBox.getChildren().addAll(statsVBox, itemsListView);
        rightBox.setMinWidth(600);
        leftBox.setMinWidth(600);
        borderPane.setCenter(mainBox);
        borderPane.setBottom(bottomVBox);
        setContent(borderPane);




    }

    public void updateItemsListView() {
        itemsListView.getItems().clear();
        itemsListView.setMaxHeight(400);
        if (characterData.getAllItems().isEmpty()) {
            itemsListView.getItems().add("Batoh je prázdný");
        } else {
            for (EquipmentItem equipmentItem : characterData.getAllItems()) {
                String itemInfo = equipmentItem.getName();

                // Append item weight if available
                if (equipmentItem.getWeight() > 0) {
                    itemInfo += " (" + equipmentItem.getWeight() + " kg)";
                }

                Map<String, Integer> itemStats = equipmentItem.getModifiedStats();
                if (!itemStats.isEmpty()) {
                    StringBuilder statsBuilder = new StringBuilder("\nStaty- ");
                    boolean firstStat = true;
                    for (Map.Entry<String, Integer> statEntry : itemStats.entrySet()) {
                        if (!firstStat) {
                            statsBuilder.append(", ");
                        }
                        statsBuilder.append(statEntry.getKey()).append(": ").append(statEntry.getValue());
                        firstStat = false;
                    }
                    itemInfo += statsBuilder.toString();
                }

                itemsListView.getItems().add(itemInfo);
            }
        }

        itemsListView.setOnMouseClicked(event -> {
            String itemNameSelect = itemsListView.getSelectionModel().getSelectedItem();
            if (itemNameSelect != null) {
                String itemName = itemNameSelect.split(" \\(")[0]; // Extract item name before stats
                EquipmentItem selectedItem = characterData.findItemByName(itemName);
                if (itemName != null) {
                    // Create a dialog to display detailed item information and removal button
                    Dialog<String> itemDialog = new Dialog<>();
                    itemDialog.setTitle("Detaily předmětu: " + itemName);

                    VBox dialogVBox = new VBox();
                    dialogVBox.setSpacing(10);

                    // Display item name and weight
                    Label nameLabel = new Label("Název: " + selectedItem.getName());
                    dialogVBox.getChildren().add(nameLabel);

                    if (selectedItem.getWeight() > 0) {
                        Label weightLabel = new Label("Váha: " + selectedItem.getWeight() + " kg");
                        dialogVBox.getChildren().add(weightLabel);
                    }

                    if (selectedItem.getHpChange() != 0) {
                        Label healthLabel = new Label("Změna životů: " + selectedItem.getHpChange());
                        dialogVBox.getChildren().add(healthLabel);
                    }

                    // Display item stats if available
                    Map<String, Integer> itemStats = selectedItem.getModifiedStats();
                    if (!itemStats.isEmpty()) {
                        Label statsLabel = new Label("Staty: \n" );
                        StringBuilder statsBuilder = new StringBuilder();
                        for (Map.Entry<String, Integer> statEntry : itemStats.entrySet()) {
                            statsBuilder.append(statEntry.getKey()).append(": ").append(statEntry.getValue()).append(", ");
                        }
                        statsBuilder.setLength(statsBuilder.length() - 2); // Remove the trailing comma and space
                        statsLabel.setText(statsLabel.getText() + " " + statsBuilder.toString());
                        dialogVBox.getChildren().add(statsLabel);
                    }

                    // Add a button to remove the item
                    Button removeButton = new Button("Odebrat předmět");
                    removeButton.setOnAction(removeEvent -> {
                        if (characterData.getEquippedItems().contains(selectedItem)) {
                            // Show error dialog if the item is equipped
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Chyba");
                            errorAlert.setHeaderText("Nelze odebrat právě vybraný předmět");
                            errorAlert.setContentText("Vybraný předmět je v tuto chvíli vybaven. Odeber si ho z vybavení a zkus to znovu.");
                            errorAlert.showAndWait();
                        } else {
                            characterData.getAllItems().remove(selectedItem);
                            updateItemsListView(); // Update the ListView after removing the item
                            itemDialog.close(); // Close the dialog
                        }
                    });
                    dialogVBox.getChildren().add(removeButton);

                    itemDialog.getDialogPane().setContent(dialogVBox);
                    itemDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

                    itemDialog.showAndWait();
                }
            }
        });



    }


    private void addXP() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add XP");
        dialog.setHeaderText("Enter the amount of XP to add:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                int xpToAdd = Integer.parseInt(result.get());
                int newXp = characterData.getXP() + xpToAdd;
                if (newXp >= characterData.getRequireXP()) {
                    showLevelUpDialog();
                }
                characterData.setXP(newXp);
                xpProgressBar.setProgress(characterData.getXPProgress());
                updateXPLabel();
            } catch (NumberFormatException e) {
                // Handle invalid input (non-integer)
                System.out.println("Invalid input for XP.");
            }
        }
    }


    private void showLevelUpDialog() {
        Map<String, Integer> totalStats = characterData.getTotalStats();
        List<EquipmentItem> equipmentItems = characterData.getEquippedItems();
        Dialog<Pair<String, Integer>> dialog = new Dialog<>();
        dialog.setTitle("Level Up!");
        dialog.setHeaderText("Gratuluji, máš level up!");

        ButtonType increaseStatButtonType = new ButtonType("Vyber stat na vylepšení", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(increaseStatButtonType, ButtonType.CANCEL);

        ComboBox<String> statComboBox = new ComboBox<>();
        statComboBox.getItems().addAll(characterData.getTotalStats().keySet());

        TextField hpChangeField = new TextField();
        hpChangeField.setPromptText("Změna HP");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Vyber stat:"), 0, 0);
        grid.add(statComboBox, 1, 0);
        grid.add(new Label("Změna HP:"), 0, 1);
        grid.add(hpChangeField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == increaseStatButtonType) {
                String selectedStat = statComboBox.getValue();
                int hpChange = 0;
                try {
                    hpChange = Integer.parseInt(hpChangeField.getText());
                } catch (NumberFormatException ignored) {
                }
                return new Pair<>(selectedStat, hpChange);
            }
            return null;
        });

        Optional<Pair<String, Integer>> result = dialog.showAndWait();
        result.ifPresent(selectedPair -> {
            String selectedStat = selectedPair.getKey();
            int hpChange = selectedPair.getValue();

            characterData.increaseStat(selectedStat);
            characterData.modifyHP(hpChange);
            updateHPLabel();

            updateTotalStatsLabel(totalStats);
            updateItemsListView();
            System.out.println(characterData.getMaxHP());
        });
    }










    public List<EquipmentSlotHandler> getSlotHandlers() {
        return slotHandlers;
    }



    private void showEquipmentDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Nová Výbava");

        VBox creationVBox = new VBox();
        TextField itemNameField = new TextField();
        itemNameField.setPromptText("Název předmětu");

        List<HBox> statChangeFields = new ArrayList<>(); // Store stat change fields dynamically

        ComboBox<String> slotComboBox = new ComboBox<>();
        slotComboBox.getItems().addAll(
                "Helma", "Hrudní plát", "Zbroj rukou", "Zbroj nohou", "Boty",
                "Prsten", "Náhrdelník"
        );
        slotComboBox.setPromptText("Typ slotu");

        ComboBox<String> statComboBox = new ComboBox<>();
        statComboBox.getItems().addAll(characterData.getBaseStats().keySet());
        statComboBox.getItems().addAll(characterData.getCustomStats().keySet());
        statComboBox.setPromptText("Změněná statistika");

        Button addStatChangeButton = new Button("Přidat změnu statistiky");
        addStatChangeButton.setOnAction(addEvent -> {
            HBox newStatChangeField = new HBox();
            ComboBox<String> newStatComboBox = new ComboBox<>();
            newStatComboBox.getItems().addAll(characterData.getBaseStats().keySet());
            newStatComboBox.getItems().addAll(characterData.getCustomStats().keySet());
            newStatComboBox.setPromptText("Změněná statistika");
            TextField newStatChangeTextField = new TextField();
            newStatChangeTextField.setPromptText("Změna statistiky");
            newStatChangeField.getChildren().addAll(newStatComboBox, newStatChangeTextField);
            statChangeFields.add(newStatChangeField);
            creationVBox.getChildren().add(creationVBox.getChildren().size() - 2, newStatChangeField); // Add before the last two buttons
        });

        TextField hpChangeField = new TextField();
        hpChangeField.setPromptText("Změna životů");

        TextField weightField = new TextField();
        weightField.setPromptText("Váha předmětu");
        Button addButton = new Button("Přidat");
        Button cancelButton = new Button("Zrušit");

        addButton.setOnAction(event -> {
            String itemName = itemNameField.getText();
            String selectedSlot = slotComboBox.getValue();
            int hpChange = Integer.parseInt(hpChangeField.getText());
            int weight = Integer.parseInt(weightField.getText());

            Map<String, Integer> modifiedStats = new HashMap<>();
            for (HBox statChangeField : statChangeFields) {
                ComboBox<String> statChangeComboBox = (ComboBox<String>) statChangeField.getChildren().get(0);
                TextField statChangeTextField = (TextField) statChangeField.getChildren().get(1);
                String selectedStat = statChangeComboBox.getValue();
                int statChange = Integer.parseInt(statChangeTextField.getText());
                modifiedStats.put(selectedStat, statChange);
            }

            createNewEquipmentItem(itemName, selectedSlot, modifiedStats, hpChange, weight);
            updateItemsListView();
            dialog.close();
        });

        cancelButton.setOnAction(event -> dialog.close());

        creationVBox.getChildren().addAll(
                itemNameField, slotComboBox, addStatChangeButton, hpChangeField, weightField, addButton, cancelButton
        );
        creationVBox.setSpacing(10);
        creationVBox.setPadding(new Insets(10));

        Scene dialogScene = new Scene(creationVBox, 300, 300);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }


    private void createNewEquipmentItem(
            String itemName, String selectedSlot, Map<String, Integer> modifiedStats, int hpChange, int weight
    ) {
        EquipmentItem equipmentItem = new EquipmentItem(itemName, selectedSlot, modifiedStats, hpChange, weight);
        characterData.addItem(equipmentItem);
    }

    private void showAddStatDialog() {
        List<EquipmentItem> equippedItems = characterData.getEquippedItems();
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Přidat nový stat");

        Label nameLabel = new Label("Název:");
        TextField nameTextField = new TextField();

        Label valueLabel = new Label("Hodnota:");
        TextField valueTextField = new TextField();

        Button addButton = new Button("Přidat");
        addButton.setOnAction(event -> {
            String newStatName = nameTextField.getText();
            int baseValue = Integer.parseInt(valueTextField.getText());

            characterData.getCustomStats().put(newStatName, baseValue);
            characterData.updateTotalStats(equippedItems);
            updateTotalStatsLabel(characterData.getTotalStats());

            dialog.close();
        });

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(nameLabel, nameTextField, valueLabel, valueTextField, addButton);

        Scene dialogScene = new Scene(vbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }


    private void showModifyHPDialog() {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Modify HP");
        dialog.setHeaderText("Enter the amount to modify HP:");

        ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        TextField hpChangeField = new TextField();
        hpChangeField.setPromptText("Enter HP change");

        dialog.getDialogPane().setContent(hpChangeField);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                try {
                    return Integer.parseInt(hpChangeField.getText());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });

        Optional<Integer> result = dialog.showAndWait();
        result.ifPresent(hpChange -> {
            characterData.modifyCurrentHealth(hpChange);
            updateHPLabel();
        });
    }

    public void updateHPLabel() {
        Integer currentHP = characterData.getCurrentHealth();
        Integer maxHP = characterData.getMaxHP();
        System.out.println(currentHP);
        System.out.println(maxHP);
        hpLabel.setText("HP: " + currentHP + "/" + maxHP);
    }




    public void updateTotalStatsLabel(Map<String, Integer> totalStats) {

        // Clear existing labels
        statsVBox.getChildren().removeIf(node -> node instanceof Label);
        Label statLabelTop = new Label("Statistiky charakteru (kompletní + základ): ");
        statLabelTop.setStyle("-fx-text-fill: White; -fx-font-size: 20px;");
        statsVBox.getChildren().add(statLabelTop);


        // Update the labels based on the totalStats map
        totalStats.forEach((stat, value) -> {
            Label statLabel = new Label(formatStatText(stat, value));
            statLabel.setStyle("-fx-text-fill: White; -fx-font-size: 16px;");
            statsVBox.getChildren().add(statLabel);
        });
    }

    private String formatStatText(String stat, int value) {
        int baseStatValue = characterData.getBaseStats().getOrDefault(stat, 0);
        int customStatValue = characterData.getCustomStats().getOrDefault(stat, 0);
        String statText = stat + ": " + value;

        // Append base stat value in parentheses if available
        if (baseStatValue != 0 || value == 0) {
            statText += " [" + baseStatValue + "]";
        }

        // Append custom stat value in square brackets if available
        if (customStatValue != 0 || characterData.getCustomStats().containsKey(stat)) {
            statText += " [" + customStatValue + "]";
        }

        return statText;
    }

    private void updateXPLabel() {
        xpInfoLabel.setText("XP: " + characterData.getXP() + " / " + characterData.getRequireXP()
                + " | Level: " + characterData.getLevel());
        xpInfoLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
    }








    private ImageView createCharacterImageView() {
        Image characterImage = new Image("background.png");
        ImageView imageView = new ImageView(characterImage);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(600);
        imageView.setOpacity(0.5);
        return imageView;
    }



}

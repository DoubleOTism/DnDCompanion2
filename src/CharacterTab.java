import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Float.parseFloat;

public class CharacterTab extends Tab {
    private CharacterData characterData;
    private CharacterTab characterTab;
    private VBox rightBox = new VBox();
    private List<EquipmentSlotHandler> slotHandlers = new ArrayList<>();
    ProgressBar xpProgressBar = new ProgressBar();
    ProgressBar carryProgressBar = new ProgressBar();
    Label xpInfoLabel = new Label();

    Label weaponDamage = new Label("Poškození: Nemáš vybavenou zbraň");
    Label hpLabel = new Label();
    VBox statsVBox = new VBox();
    ListView<String> itemsListView = new ListView<>();
    private Label carryWeightLabel = new Label();

    private Label playerMoney = new Label();

    Button weaponSecondaryButton = new Button("Sekundární zbraň");

    public CharacterTab(CharacterData characterData) {
        this.characterData = characterData;
        this.characterTab = this;
        ImageView characterImageView = createCharacterImageView();
        BorderPane borderPane = new BorderPane();
        HBox mainBox = new HBox();
        Label characterName = new Label(characterData.getCharacterName() + ", " + characterData.getCharacterRace());
        characterName.setStyle("-fx-text-fill: White; -fx-font-size: 20px;");
    //Tlačítka pro výbavu
        List<EquipmentItem> allItems = characterData.getInventory();
        List<EquipmentItem> equippedItems = characterData.getEquippedItems();
        //Helma
        Button equipHelmetButton = new Button("Helma");
        EquipmentSlotHandler helmaSlotHandler = new EquipmentSlotHandler("helmaSlot","Helma", equipHelmetButton, equippedItems, allItems, characterData, characterTab, false);
        equipHelmetButton.setLayoutX(500);
        equipHelmetButton.setLayoutY(40);
        //Chestplate
        Button equipChestplateButton = new Button("Hrudní plát");
        EquipmentSlotHandler chestplateSlotHandler = new EquipmentSlotHandler("hrudSlot","Hrudní plát", equipChestplateButton, equippedItems, allItems, characterData, characterTab, false);
        equipChestplateButton.setLayoutX(500);
        equipChestplateButton.setLayoutY(150);
        //Ruce
        Button equipArmsButton = new Button("Zbroj rukou");
        EquipmentSlotHandler armsSlotHandler = new EquipmentSlotHandler("ruceSlot","Zbroj rukou", equipArmsButton, equippedItems, allItems, characterData, characterTab, false);
        equipArmsButton.setLayoutX(500);
        equipArmsButton.setLayoutY(250);
        //Zbroj nohou
        Button equipLeggingsButton = new Button("Zbroj nohou");
        EquipmentSlotHandler leggingsSlotHandler = new EquipmentSlotHandler("nohySlot","Zbroj nohou", equipLeggingsButton, equippedItems, allItems, characterData, characterTab, false);
        equipLeggingsButton.setLayoutX(500);
        equipLeggingsButton.setLayoutY(400);
        //Boty
        Button equipBootsButton = new Button("Boty");
        EquipmentSlotHandler bootsSlotHandler = new EquipmentSlotHandler("botySlot","Boty", equipBootsButton, equippedItems, allItems, characterData, characterTab, false);
        equipBootsButton.setLayoutX(500);
        equipBootsButton.setLayoutY(500);
        //Prsteny
        Button equipRing1Button = new Button("Prsten");
        EquipmentSlotHandler ring1SlotHandler = new EquipmentSlotHandler("prsten1Slot","Prsten", equipRing1Button, equippedItems, allItems, characterData, characterTab, false);
        equipRing1Button.setLayoutX(20);
        equipRing1Button.setLayoutY(80);

        Button equipRing2Button = new Button("Prsten");
        EquipmentSlotHandler ring2SlotHandler = new EquipmentSlotHandler("prsten2Slot","Prsten", equipRing2Button, equippedItems, allItems, characterData, characterTab, false);
        equipRing2Button.setLayoutX(20);
        equipRing2Button.setLayoutY(120);

        Button equipRing3Button = new Button("Prsten");
        EquipmentSlotHandler ring3SlotHandler = new EquipmentSlotHandler("prsten3Slot","Prsten", equipRing3Button, equippedItems, allItems, characterData, characterTab, false);
        equipRing3Button.setLayoutX(20);
        equipRing3Button.setLayoutY(160);

        Button equipRing4Button = new Button("Prsten");
        EquipmentSlotHandler ring4SlotHandler = new EquipmentSlotHandler("prsten4Slot","Prsten", equipRing4Button, equippedItems, allItems, characterData, characterTab, false);
        equipRing4Button.setLayoutX(20);
        equipRing4Button.setLayoutY(200);

        Button equipNecklace1Button = new Button("Náhrdelník");
        EquipmentSlotHandler necklace1SlotHandler = new EquipmentSlotHandler("nahrdelnik1Slot","Náhrdelník", equipNecklace1Button, equippedItems, allItems, characterData, characterTab, false);
        equipNecklace1Button.setLayoutX(20);
        equipNecklace1Button.setLayoutY(300);

        Button equipNecklace2Button = new Button("Náhrdelník");
        EquipmentSlotHandler necklace2SlotHandler = new EquipmentSlotHandler("nahrdelnik2Slot","Náhrdelník", equipNecklace2Button, equippedItems, allItems, characterData, characterTab, false);
        equipNecklace2Button.setLayoutX(20);
        equipNecklace2Button.setLayoutY(340);

        Button weaponPrimaryButton = new Button("Primární zbraň");
        EquipmentSlotHandler weaponPrimary = new EquipmentSlotHandler("weaponPrimary", "Zbraň", weaponPrimaryButton, equippedItems, allItems, characterData, characterTab, true);
        weaponPrimaryButton.setLayoutX(20);
        weaponPrimaryButton.setLayoutY(400);



        EquipmentSlotHandler weaponSecondary = new EquipmentSlotHandler("weaponSecondary", "Zbraň", weaponSecondaryButton, equippedItems, allItems, characterData, characterTab, false);
        weaponSecondaryButton.setLayoutX(20);
        weaponSecondaryButton.setLayoutY(440);


        slotHandlers.add(helmaSlotHandler);
        slotHandlers.add(chestplateSlotHandler);
        slotHandlers.add(armsSlotHandler);
        slotHandlers.add(leggingsSlotHandler);
        slotHandlers.add(bootsSlotHandler);
        slotHandlers.add(ring1SlotHandler);
        slotHandlers.add(ring2SlotHandler);
        slotHandlers.add(ring3SlotHandler);
        slotHandlers.add(ring4SlotHandler);
        slotHandlers.add(necklace1SlotHandler);
        slotHandlers.add(necklace2SlotHandler);
        slotHandlers.add(weaponPrimary);
        slotHandlers.add(weaponSecondary);



        Pane buttonOverlayPane = new Pane();
        buttonOverlayPane.getChildren().addAll(equipHelmetButton, equipChestplateButton, equipArmsButton, equipLeggingsButton, equipBootsButton, equipRing1Button, equipRing2Button, equipRing3Button, equipRing4Button, equipNecklace1Button, equipNecklace2Button, weaponPrimaryButton, weaponSecondaryButton);

        StackPane imagePane = new StackPane(characterImageView, buttonOverlayPane);





        setText(characterData.getCharacterName());
        VBox leftBox = new VBox();
        Button newArmorButton = new Button("Přidat novou zbroj");
        newArmorButton.setOnAction(event -> showEquipmentDialog());


        Button addStatButton = new Button("Přidat vlastní stat");
        addStatButton.setOnAction(event -> showAddStatDialog());

        Button addNewItem = new Button("Přidat předmět");
        addNewItem.setOnAction(event -> createNewItemDialog());

        Button addWeaponButton = new Button("Přidat novou zbraň");
        addWeaponButton.setOnAction(event -> showWeaponDialog());

        Button showWallet = new Button("Peněženka");
        showWallet.setOnAction(event -> { showCurrencyManagementDialog();
            
        });




        //xp handle

        xpProgressBar.setPrefWidth(200); // Set your preferred width
        xpProgressBar.setProgress(characterData.getXPProgress());
        carryProgressBar.setPrefWidth(200);
        carryProgressBar.setProgress(characterData.getWeightRatio());
        Button addXPButton = new Button("Přidat XP");
        addXPButton.setOnAction(event -> addXP());

        updateMoneyLabel();
        updateCarryWeightLabel();
        updateItemsListView();
        updateHPLabel();
        updateDamageLabel();
        hpLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        carryWeightLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        updateXPLabel();
        Region spacer = new Region();
        spacer.setMinWidth(200);
        Region spacr = new Region();
        spacr.setMinWidth(200);
        Region spacer4 = new Region();
        spacer4.setMinWidth(50);
        carryProgressBar.setStyle("-fx-accent: red;");
        HBox levelManagement = new HBox(spacer4, xpProgressBar, xpInfoLabel, spacer, hpLabel, spacr, carryWeightLabel, carryProgressBar);
        VBox bottomVBox = new VBox();
        Button modifyHP = new Button("Upravit HP");
        modifyHP.setOnAction(event -> showModifyHPDialog());
        Region spacer5 = new Region();
        spacer5.setMinWidth(270);
        HBox bottomBox = new HBox(spacer5, addStatButton, newArmorButton, addNewItem, addWeaponButton,addXPButton, modifyHP, showWallet);
        bottomVBox.getChildren().addAll(bottomBox, levelManagement);
        bottomBox.setSpacing(10);


        VBox leftBoxLower = new VBox(characterName, weaponDamage, playerMoney);
        leftBoxLower.setPadding(new Insets(0,0,0,20));
        leftBox.getChildren().addAll(imagePane, leftBoxLower);

        updateTotalStatsLabel(characterData.getTotalStats());
        Region spacer3 = new Region();
        spacer3.setMinWidth(100);
        mainBox.getChildren().addAll(leftBox, spacer3, rightBox);
        Region spacer2 = new Region();
        spacer2.setMinHeight(20);
        ScrollPane scrollPane = new ScrollPane(statsVBox);
        scrollPane.setPrefHeight(400);
        scrollPane.setMaxWidth(480);
        scrollPane.setFitToWidth(true);
        Region spacer6 = new Region();
        spacer6.setMinHeight(20);
        scrollPane.setStyle("-fx-background: rgb(40, 40, 40); -fx-border-color: rgb(40, 40, 40);");
        rightBox.getChildren().addAll(spacer6, scrollPane, spacer2, itemsListView);
        itemsListView.setMaxWidth(480);
        itemsListView.setMaxHeight(350);
        rightBox.setMinWidth(600);
        rightBox.setMaxHeight(600);
        borderPane.setCenter(mainBox);
        borderPane.setBottom(bottomVBox);
        setContent(borderPane);




    }
    public void disableSecondarySlotHandler() {
        weaponSecondaryButton.setDisable(true);
    }

    public void enableSecondarySlotHandler() {
        weaponSecondaryButton.setDisable(false);
    }

    private void showCurrencyManagementDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Peněženka");

        VBox dialogVBox = new VBox();
        dialogVBox.setSpacing(10);
        dialogVBox.setPadding(new Insets(10));

        CheckBox addCheckBox = new CheckBox("Přidat do měšce");
        addCheckBox.setSelected(true);
        ChoiceBox<String> currencyChoiceBox = new ChoiceBox<>();
        currencyChoiceBox.getItems().addAll("Měď", "Stříbro", "Zlato", "Platina");
        currencyChoiceBox.setValue("Měď"); // Set default currency

        TextField amountTextField = new TextField();
        amountTextField.setPromptText("Kolik?");

        Button confirmButton = new Button("Potvrdit");
        confirmButton.setOnAction(event -> {
            boolean isAdd = addCheckBox.isSelected();
            String currencyType = currencyChoiceBox.getValue();
            long amount;

            try {
                amount = Long.parseLong(amountTextField.getText());
                updateMoneyLabel();
            } catch (NumberFormatException e) {
                // Show error if amount is not a valid number
                showErrorAlert("Nesprávně zadné údaje..");
                return;
            }

            // Calculate the actual amount to add or deduct based on currency type
            long currencyValue = 1;
            switch (currencyType) {
                case "Stříbro":
                    currencyValue = 100;
                    break;
                case "Zlato":
                    currencyValue = 10000;
                    break;
                case "Platina":
                    currencyValue = 1000000;
                    break;
                // "Měď" is already 1, so no change needed
            }

            long totalAmount = amount * currencyValue;

            if (isAdd) {
                characterData.addCurrency(totalAmount);
                updateMoneyLabel();
            } else {
                boolean success = characterData.deductCurrency(totalAmount);
                updateMoneyLabel();
                if (!success) {
                    // Show error if deducting fails
                    showErrorAlert("Nedostatek kreditů pro transakci: " + totalAmount/currencyValue + " " + currencyType + ".");
                    updateMoneyLabel();
                    return;
                }
            }

            dialog.close();
        });

        // Helper method to convert currency units into tiers
        dialogVBox.getChildren().addAll(addCheckBox, currencyChoiceBox, amountTextField, confirmButton);
        Scene dialogScene = new Scene(dialogVBox);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    // Helper method to convert currency units into tiers
    private String convertCurrencyUnits(long currencyUnits) {
        String[] currencyNames = {"Platina", "Zlato", "Stříbro", "Měď"};
        long[] currencyValues = {1000000, 10000, 100, 1};

        StringBuilder tiers = new StringBuilder();
        boolean firstTier = true;

        for (int i = 0; i < currencyNames.length; i++) {
            long tierAmount = currencyUnits / currencyValues[i];

            if (tierAmount > 0) {
                if (!firstTier) {
                    tiers.append(", ");
                }

                tiers.append(tierAmount).append(" ").append(currencyNames[i]);

                currencyUnits %= currencyValues[i];
                firstTier = false;
            }
        }

        return tiers.toString();
    }

    private void updateMoneyLabel() {
        String currencyTiers = convertCurrencyUnits(characterData.getCurrency());
        playerMoney.setText("V měšci máš: " + currencyTiers);
        playerMoney.setStyle("-fx-text-fill: rgb(255, 215, 0); -fx-font-size: 16px;");

    }

    // Helper method to show an error alert
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void createNewItemDialog() {
        Dialog<String[]> itemCreationDialog = new Dialog<>();
        itemCreationDialog.setTitle("Vytvořit předmět");
        itemCreationDialog.setHeaderText("Zadej info o předmětu");

        // Set the dialog's content
        VBox content = new VBox();
        TextField itemNameField = new TextField();
        TextField carryWeightField = new TextField();
        content.getChildren().addAll(
                new Label("Název:"),
                itemNameField,
                new Label("Váha:"),
                carryWeightField
        );
        itemCreationDialog.getDialogPane().setContent(content);

        // Add buttons to the dialog
        ButtonType createButtonType = new ButtonType("Vytvořit", ButtonBar.ButtonData.OK_DONE);
        itemCreationDialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        // Convert the input to an array and close the dialog
        itemCreationDialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                String itemName = itemNameField.getText().trim();
                String carryWeightStr = carryWeightField.getText().trim();

                if (itemName.isEmpty() || carryWeightStr.isEmpty()) {
                    showMissingInputError();
                    return null;
                }

                return new String[] { itemName, carryWeightStr };
            }
            return null;
        });

        // Show the dialog and handle the result
        Optional<String[]> result = itemCreationDialog.showAndWait();
        result.ifPresent(itemData -> {
            String itemName = itemData[0];
            float carryWeight = parseFloat(itemData[1]);
            float capacity = characterData.calculateAvailableWeight();
            if (carryWeight > capacity) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Nelze přidat");
                alert.setHeaderText("Nelze vytvořit předmět");
                alert.setContentText("Tenhle předmět nelze v tuto chvíli uložit do inventáře, překročil by jsi tak svůj limit pro nostnost.");
                alert.showAndWait();
            } else {

                // Create the EquipmentItem instance and add it to allItems
                EquipmentItem newItem = new EquipmentItem(itemName, null, null, 0, carryWeight, false, false, null);
                characterData.getAllItems().add(newItem);

                // Update the items list view or perform other necessary updates
                updateItemsListView();
                updateCarryWeightLabel();
            }
        });
    }









    private void showMissingInputError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Chybějící údaje");
        alert.setHeaderText("Vyplň všechny údaje");
        alert.setContentText("Prosím, vyplň všechny pole.");
        alert.showAndWait();
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
                if (itemStats != null && !itemStats.isEmpty()) {
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

                // Check if the item is a weapon
                if (equipmentItem.isWeapon()) {

                    if (equipmentItem.isTwoHanded()) {
                        itemInfo += "\nObouruční zbraň, ";
                    } else {
                        itemInfo += "\nJednoruční zbraň, ";
                    }
                    // Display damage for weapons
                    String damageDice = equipmentItem.getDamageDice();
                    if (damageDice != null && !damageDice.isEmpty()) {
                        itemInfo += "poškození: " + damageDice;
                    }

                    // Check if the weapon is two-handed

                }

                itemsListView.getItems().add(itemInfo);
            }
        }

        itemsListView.setOnMouseClicked(event -> {
            String itemNameSelect = itemsListView.getSelectionModel().getSelectedItem();
            if (itemNameSelect != null) {
                String itemName = itemNameSelect.split(" \\(")[0]; // Extract item name before stats
                EquipmentItem selectedItem = characterData.findItemByName(itemName);
                if (selectedItem != null) {
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
                    if (itemStats != null && !itemStats.isEmpty()) {
                        Label statsLabel = new Label("Staty: \n" );
                        StringBuilder statsBuilder = new StringBuilder();
                        for (Map.Entry<String, Integer> statEntry : itemStats.entrySet()) {
                            statsBuilder.append(statEntry.getKey()).append(": ").append(statEntry.getValue()).append(", ");
                        }
                        statsBuilder.setLength(statsBuilder.length() - 2); // Remove the trailing comma and space
                        statsLabel.setText(statsLabel.getText() + " " + statsBuilder.toString());
                        dialogVBox.getChildren().add(statsLabel);
                    }

                    // Display damage for weapons
                    if (selectedItem.isWeapon()) {
                        String damageDice = selectedItem.getDamageDice();
                        if (damageDice != null && !damageDice.isEmpty()) {
                            Label damageLabel = new Label("Poškození: " + damageDice);
                            dialogVBox.getChildren().add(damageLabel);
                        }

                        // Check if the weapon is two-handed
                        if (selectedItem.isTwoHanded()) {
                            Label weaponTypeLabel = new Label("Typ zbraně: Obouruční zbraň");
                            dialogVBox.getChildren().add(weaponTypeLabel);
                        } else {
                            Label weaponTypeLabel = new Label("Typ zbraně: Jednoruční zbraň");
                            dialogVBox.getChildren().add(weaponTypeLabel);
                        }
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
                            updateCarryWeightLabel();
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
        dialog.setTitle("Přidání XP");
        dialog.setHeaderText("Kolik XP charakter získá?:");

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
            updateCarryWeightLabel();
        });
    }
    public List<EquipmentSlotHandler> getSlotHandlers() {
        return slotHandlers;
    }
    private void showEquipmentDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Nová výbava");

        VBox creationVBox = new VBox();
        HBox creationHBox = new HBox();
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
            creationVBox.getChildren().add(creationVBox.getChildren().size() - 3, newStatChangeField);

            double preferredHeight = creationVBox.getChildren().stream()
                    .mapToDouble(node -> node.getBoundsInParent().getMaxY())
                    .max()
                    .orElse(0.0) + 80;
            dialog.setHeight(preferredHeight);
        });
        TextField hpChangeField = new TextField();
        hpChangeField.setPromptText("Změna životů");
        TextField weightField = new TextField();
        weightField.setPromptText("Váha předmětu");
        Button addButton = new Button("Přidat");
        Button cancelButton = new Button("Zrušit");
        addButton.setOnAction(event -> {
            String itemNameText = itemNameField.getText();
            String selectedSlotText = slotComboBox.getValue();
            String hpChangeText = hpChangeField.getText();
            String weightText = weightField.getText();
            if (itemNameText.isEmpty() || selectedSlotText == null || hpChangeText.isEmpty() || weightText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Chybějící údaje");
                alert.setHeaderText("Nelze vytvořit předmět");
                alert.setContentText("Všechny povinné údaje (název, slot, změna životů, váha) musí být vyplněny.");
                alert.showAndWait();
                return;
            }

            if (parseFloat(weightField.getText()) > characterData.calculateAvailableWeight()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Nelze přidat");
                alert.setHeaderText("Nelze vytvořit předmět");
                alert.setContentText("Tenhle předmět nelze v tuto chvíli uložit do inventáře, překročil by jsi tak svůj limit pro nostnost.");
                alert.showAndWait();
            } else {
                String itemName = itemNameField.getText();
                String selectedSlot = slotComboBox.getValue();
                int hpChange = Integer.parseInt(hpChangeField.getText());
                float weight = parseFloat(weightField.getText());

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
                updateCarryWeightLabel();
                dialog.close();
            }
        });
        cancelButton.setOnAction(event -> dialog.close());
        creationHBox.getChildren().addAll(addButton, cancelButton);
        creationHBox.setSpacing(10);

        creationVBox.getChildren().addAll(
                itemNameField, slotComboBox, addStatChangeButton, hpChangeField, weightField, creationHBox);

        creationVBox.setSpacing(10);
        creationVBox.setPadding(new Insets(10));
        Scene dialogScene = new Scene(creationVBox);
        dialog.setWidth(400);
        dialog.setScene(dialogScene);
        dialog.showAndWait();

    }

    private void showWeaponDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Přidání nové zbraně");

        VBox creationVBox = new VBox();
        HBox creationHBox = new HBox();
        TextField weaponNameField = new TextField();
        weaponNameField.setPromptText("Název zbraně");
        AtomicBoolean isDualWield = new AtomicBoolean(false);


        CheckBox dualWieldCheckBox = new CheckBox("Je to Zweihander?");
        dualWieldCheckBox.setSelected(false); // Set the default value to false
        dualWieldCheckBox.setOnAction(event -> {
            isDualWield.set(dualWieldCheckBox.isSelected());
        });


        TextField hpChangeField = new TextField();
        hpChangeField.setPromptText("Změna HP");

        TextField weightField = new TextField();
        weightField.setPromptText("Váha");

        Map<String, Spinner<Integer>> diceSpinners = new LinkedHashMap<>(); // Use LinkedHashMap to maintain order

        Spinner<Integer> d4Spinner = new Spinner<>(0, 100, 0);
        d4Spinner.setEditable(true);
        d4Spinner.setMaxWidth(60);
        diceSpinners.put("d4", d4Spinner);

        Spinner<Integer> d6Spinner = new Spinner<>(0, 100, 0);
        d6Spinner.setEditable(true);
        d6Spinner.setMaxWidth(60);
        diceSpinners.put("d6", d6Spinner);

        Spinner<Integer> d8Spinner = new Spinner<>(0, 100, 0);
        d8Spinner.setEditable(true);
        d8Spinner.setMaxWidth(60);
        diceSpinners.put("d8", d8Spinner);

        Spinner<Integer> d10Spinner = new Spinner<>(0, 100, 0);
        d10Spinner.setEditable(true);
        d10Spinner.setMaxWidth(60);
        diceSpinners.put("d10", d10Spinner);

        Spinner<Integer> d12Spinner = new Spinner<>(0, 100, 0);
        d12Spinner.setEditable(true);
        d12Spinner.setMaxWidth(60);
        diceSpinners.put("d12", d12Spinner);

        Spinner<Integer> d20Spinner = new Spinner<>(0, 100, 0);
        d20Spinner.setEditable(true);
        d20Spinner.setMaxWidth(60);
        diceSpinners.put("d20", d20Spinner);

        List<HBox> statChangeFields = new ArrayList<>(); // Store stat change fields dynamically

        Button addStatChangeButton = new Button("Přidat změnu statu");
        addStatChangeButton.setOnAction(addEvent -> {
            HBox newStatChangeField = new HBox();
            ComboBox<String> newStatComboBox = new ComboBox<>();
            newStatComboBox.getItems().addAll(characterData.getBaseStats().keySet());
            newStatComboBox.getItems().addAll(characterData.getCustomStats().keySet());
            newStatComboBox.setPromptText("Změněný stat");
            TextField newStatChangeTextField = new TextField();
            newStatChangeTextField.setPromptText("Změna statu");
            newStatChangeField.getChildren().addAll(newStatComboBox, newStatChangeTextField);
            statChangeFields.add(newStatChangeField);
            creationVBox.getChildren().add(creationVBox.getChildren().size() - 2, newStatChangeField);

            double preferredHeight = creationVBox.getChildren().stream()
                    .mapToDouble(node -> node.getBoundsInParent().getMaxY())
                    .max()
                    .orElse(0.0) + 80;
            dialog.setHeight(preferredHeight);
        });

        Button addButton = new Button("Přidat");
        Button cancelButton = new Button("Zrušit");

        addButton.setOnAction(event -> {
            String weaponNameText = weaponNameField.getText();
            String hpChangeText = hpChangeField.getText();
            String weightText = weightField.getText();

            // Check if weapon name, hp change, and weight are empty
            if (weaponNameText.isEmpty() || hpChangeText.isEmpty() || weightText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Chybějící údaje");
                alert.setHeaderText("Zbraň nemůže být vytvořená");
                alert.setContentText("Nejsou vyplněna všechna potřebná pole. Zkus to znovu.");
                alert.showAndWait();
                return; // Abort creation
            }

            // Check if at least one spinner is set
            boolean atLeastOneSpinnerSet = diceSpinners.values().stream()
                    .anyMatch(spinner -> spinner.getValue() > 0);

            if (!atLeastOneSpinnerSet) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Zbraň bez damage");
                alert.setHeaderText("Zbraň nemůže být vytvořena");
                alert.setContentText("Nastav aspoň jednu kostku na damage a zkus to znovu");
                alert.showAndWait();
                return; // Abort creation
            }
            if (parseFloat(weightField.getText()) > characterData.calculateAvailableWeight()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Nelze přidat");
                alert.setHeaderText("Nelze vytvořit předmět");
                alert.setContentText("Tenhle předmět nelze v tuto chvíli uložit do inventáře, překročil by jsi tak svůj limit pro nostnost.");
                alert.showAndWait();
            } else {

                // Generate the damageDice string based on the selected dice and their counts
                StringBuilder damageDiceBuilder = new StringBuilder();
                boolean firstDice = true;
                for (Map.Entry<String, Spinner<Integer>> entry : diceSpinners.entrySet()) {
                    int count = entry.getValue().getValue();
                    if (count > 0) {
                        if (!firstDice) {
                            damageDiceBuilder.append(" + ");
                        }
                        damageDiceBuilder.append(count).append(entry.getKey());
                        firstDice = false;
                    }
                }
                String damageDice = damageDiceBuilder.toString();


                Map<String, Integer> modifiedStats = new HashMap<>();
                for (HBox statChangeField : statChangeFields) {
                    ComboBox<String> statChangeComboBox = (ComboBox<String>) statChangeField.getChildren().get(0);
                    TextField statChangeTextField = (TextField) statChangeField.getChildren().get(1);
                    String selectedStat = statChangeComboBox.getValue();
                    int statChange = Integer.parseInt(statChangeTextField.getText());
                    modifiedStats.put(selectedStat, statChange);
                }

                createNewWeapon(weaponNameText, damageDice, modifiedStats, Integer.parseInt(hpChangeText), Float.parseFloat(weightText), isDualWield.get());
                updateItemsListView();
                updateCarryWeightLabel();
                dialog.close();
            }
        });

        cancelButton.setOnAction(event -> dialog.close());

        HBox spinnersHBox = new HBox();
        // Add the initialized spinners to the dialog
        for (Map.Entry<String, Spinner<Integer>> entry : diceSpinners.entrySet()) {
            HBox spinnerBox = new HBox(new Label(entry.getKey() + ":"), entry.getValue());
            spinnerBox.setSpacing(10);
            spinnerBox.setPadding(new Insets(0,10,10,5));
            spinnersHBox.getChildren().add(spinnerBox);
        }

        creationHBox.getChildren().addAll(addButton, cancelButton);
        creationHBox.setSpacing(10);

        creationVBox.getChildren().addAll(
                weaponNameField, hpChangeField, weightField, dualWieldCheckBox, addStatChangeButton, spinnersHBox, creationHBox);


        creationVBox.setSpacing(10);
        creationVBox.setPadding(new Insets(10));
        Scene dialogScene = new Scene(creationVBox);
        dialog.setWidth(700);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    private void createNewWeapon(String itemName, String damageDice, Map<String, Integer> modifiedStats, int hpChange, float weight, boolean isTwoHanded) {

        EquipmentItem equipmentItem = new EquipmentItem(itemName, "Zbraň", modifiedStats, hpChange, weight, true, isTwoHanded, damageDice);
        characterData.addItem(equipmentItem);
        System.out.println(isTwoHanded);

    }

    private void createNewEquipmentItem(String itemName, String selectedSlot, Map<String, Integer> modifiedStats, int hpChange, float weight) {

        EquipmentItem equipmentItem = new EquipmentItem(itemName, selectedSlot, modifiedStats, hpChange, weight, false, false, null);
        characterData.addItem(equipmentItem);

    }





    private void showAddStatDialog() {
        List<EquipmentItem> equippedItems = characterData.getEquippedItems();
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Přidat nový stat");
        dialog.setHeaderText(null);

        // Set the dialog's content
        VBox content = new VBox(10);
        TextField nameTextField = new TextField();
        TextField valueTextField = new TextField();
        content.getChildren().addAll(
                new Label("Název:"),
                nameTextField,
                new Label("Hodnota:"),
                valueTextField
        );
        dialog.getDialogPane().setContent(content);

        // Add buttons to the dialog
        ButtonType addButtonType = new ButtonType("Přidat", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Convert the input to an array and close the dialog
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new String[] { nameTextField.getText().trim(), valueTextField.getText().trim() };
            }
            return null;
        });

        // Show the dialog and handle the result
        Optional<String[]> result = dialog.showAndWait();
        result.ifPresent(statData -> {
            String newStatName = statData[0];
            String baseValueText = statData[1];

            if (!newStatName.isEmpty() && !baseValueText.isEmpty()) {
                int baseValue = Integer.parseInt(baseValueText);
                characterData.getCustomStats().put(newStatName, baseValue);
                characterData.updateTotalStats(equippedItems);
                updateTotalStatsLabel(characterData.getTotalStats());
            }
        });
    }


    private void showModifyHPDialog() {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Změna v životech");
        dialog.setHeaderText("O kolik chceš změnit životy charakteru " + characterData.getCharacterName() + "?");

        // Set the dialog's content
        VBox content = new VBox();
        TextField hpChangeField = new TextField();
        hpChangeField.setPromptText("Změna v životech");
        content.getChildren().addAll(
                hpChangeField
        );
        dialog.getDialogPane().setContent(content);

        // Add buttons to the dialog
        ButtonType confirmButtonType = new ButtonType("Potvrdit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        // Convert the input to an Integer and close the dialog
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

        // Show the dialog and handle the result
        Optional<Integer> result = dialog.showAndWait();
        result.ifPresent(hpChange -> {
            characterData.modifyCurrentHealth(hpChange);
            updateHPLabel();
        });
    }

    public void updateHPLabel() {
        Integer currentHP = characterData.getCurrentHealth();
        Integer maxHP = characterData.getMaxHP();
        hpLabel.setText("HP: " + currentHP + "/" + maxHP);
    }

    public void updateDamageLabel() {
        weaponDamage.setText("Poškození: " + characterData.consolidateDamageDice());
        weaponDamage.setStyle("-fx-text-fill: Red; -fx-font-size: 16px;");
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

    public void updateCarryWeightLabel() {
        carryWeightLabel.setText("Nosnost: " + characterData.calculateCurrentWeight() + "/" + characterData.calculateCarryWeight());
        carryProgressBar.setProgress(characterData.getWeightRatio());
    }





}
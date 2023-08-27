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





    public CharacterTab(CharacterData characterData) {
        this.characterData = characterData;
        this.characterTab = this;
        ImageView characterImageView = createCharacterImageView();
        BorderPane borderPane = new BorderPane();
        VBox rootBox = new VBox();
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


        Button addStatButton = new Button("Add Custom Stat");
        addStatButton.setOnAction(event -> showAddStatDialog());
        rightBox.getChildren().add(addStatButton);



        //xp handle

        xpProgressBar.setPrefWidth(200); // Set your preferred width
        xpProgressBar.setProgress(characterData.getXPProgress());
        System.out.println(characterData.getXPProgress());
        Button addXPButton = new Button("Přidat XP");
        addXPButton.setOnAction(event -> addXP());
        HBox levelManagement = new HBox(xpProgressBar);
        VBox bottomVBox = new VBox();
        HBox bottomBox = new HBox(addStatButton, newArmorButton, addXPButton);
        bottomVBox.getChildren().addAll(levelManagement, bottomBox);
        bottomBox.setAlignment(Pos.BOTTOM_CENTER);
        bottomBox.setSpacing(10);
        bottomBox.setPrefWidth(1200);





        leftBox.getChildren().addAll(imagePane, characterName, characterRace);
        updateTotalStatsLabel(characterData.getTotalStats());
        mainBox.getChildren().addAll(leftBox, rightBox);
        borderPane.setCenter(mainBox);
        borderPane.setBottom(bottomVBox);
        setContent(borderPane);




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

                // Check for level-up


                System.out.println(characterData.getLevel());
                System.out.println(characterData.getXP());
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
        dialog.setHeaderText("Congratulations! You've leveled up!");

        ButtonType increaseStatButtonType = new ButtonType("Increase a Stat", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(increaseStatButtonType, ButtonType.CANCEL);

        ComboBox<String> statComboBox = new ComboBox<>();
        statComboBox.getItems().addAll(characterData.getTotalStats().keySet());

        TextField hpChangeField = new TextField();
        hpChangeField.setPromptText("Enter HP change");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Select Stat:"), 0, 0);
        grid.add(statComboBox, 1, 0);
        grid.add(new Label("HP Change:"), 0, 1);
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

            updateTotalStatsLabel(totalStats);
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




    public void updateTotalStatsLabel(Map<String, Integer> totalStats) {

        // Clear existing labels
        rightBox.getChildren().removeIf(node -> node instanceof Label);
        Label statLabelTop = new Label("Statistiky charakteru (kompletní + základ): ");
        statLabelTop.setStyle("-fx-text-fill: White; -fx-font-size: 20px;");
        rightBox.getChildren().add(statLabelTop);


        // Update the labels based on the totalStats map
        totalStats.forEach((stat, value) -> {
            Label statLabel = new Label(formatStatText(stat, value));
            statLabel.setStyle("-fx-text-fill: White; -fx-font-size: 16px;");
            rightBox.getChildren().add(statLabel);
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








    private ImageView createCharacterImageView() {
        Image characterImage = new Image("background.png");
        ImageView imageView = new ImageView(characterImage);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(600);
        imageView.setOpacity(0.5);
        return imageView;
    }



}

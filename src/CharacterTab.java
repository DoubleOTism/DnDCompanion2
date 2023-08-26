import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterTab extends Tab {
    private CharacterData characterData;

    private CharacterTab characterTab;
    private VBox rightBox = new VBox();

    private List<EquipmentSlotHandler> slotHandlers = new ArrayList<>();


    public CharacterTab(CharacterData characterData) {
        this.characterData = characterData;
        this.characterTab = this;
        ImageView characterImageView = createCharacterImageView();
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
        leftBox.getChildren().addAll(imagePane, characterName, characterRace, newArmorButton);









        /**
        // Základní staty
        Label baseStatsLabel = new Label("Základní Staty:");
        baseStatsLabel.setStyle("-fx-text-fill: White; -fx-font-size: 20px;");
        rightBox.getChildren().add(baseStatsLabel);

        characterData.getBaseStats().forEach((stat, value) -> {
            Label statLabel = new Label(stat + ": " + value);
            statLabel.setStyle("-fx-text-fill: White; -fx-font-size: 16px;");
            rightBox.getChildren().add(statLabel);
        });

        // Vlastní staty
        Label customStatsLabel = new Label("Vlastní Staty:");
        customStatsLabel.setStyle("-fx-text-fill: White; -fx-font-size: 20px;");
        rightBox.getChildren().add(customStatsLabel);

        characterData.getCustomStats().forEach((stat, value) -> {
            Label statLabel = new Label(stat + ": " + value);
            statLabel.setStyle("-fx-text-fill: White; -fx-font-size: 16px;");
            rightBox.getChildren().add(statLabel);
        });
        **/

        updateTotalStatsLabel(characterData.getTotalStats());
        mainBox.getChildren().addAll(leftBox, rightBox);

        setContent(mainBox);




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

    public void updateTotalStatsLabel(Map<String, Integer> totalStats) {

        // Clear existing labels
        rightBox.getChildren().removeIf(node -> node instanceof Label);

        // Update the labels based on the totalStats map
        totalStats.forEach((stat, value) -> {
            Label statLabel = new Label(stat + ": " + value);
            statLabel.setStyle("-fx-text-fill: White; -fx-font-size: 16px;");
            rightBox.getChildren().add(statLabel);
        });
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

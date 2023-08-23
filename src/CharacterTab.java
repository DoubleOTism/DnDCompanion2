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

    public CharacterTab(CharacterData characterData) {
        this.characterData = characterData;
        ImageView characterImageView = createCharacterImageView();
        HBox mainBox = new HBox();

        Label characterName = new Label(characterData.getCharacterName());
        Label characterRace = new Label("Rasa: " + characterData.getCharacterRace());
        characterName.setStyle("-fx-text-fill: White; -fx-font-size: 20px;");
        characterRace.setStyle("-fx-text-fill: White; -fx-font-size: 16px;");



        //equipment buttons
        Button equipHelmetButton = new Button("Helma");
        List<EquipmentItem> allItems = characterData.getInventory();
        List<EquipmentItem> equippedItems = characterData.getEquippedItems();
        EquipmentSlotHandler helmaSlotHandler = new EquipmentSlotHandler("Helma", equipHelmetButton, equippedItems, allItems);
        equipHelmetButton.setLayoutX(500);
        equipHelmetButton.setLayoutY(37);
        Button equipChestplateButton = new Button("Equip Chestplate");



        Pane buttonOverlayPane = new Pane();
        buttonOverlayPane.getChildren().addAll(equipHelmetButton);

        StackPane imagePane = new StackPane(characterImageView, buttonOverlayPane);





        setText(characterData.getCharacterName());
        VBox leftBox = new VBox();
        Button newArmorButton = new Button("Přidat novou zbroj");
        newArmorButton.setOnAction(event -> showEquipmentDialog());
        leftBox.getChildren().addAll(imagePane, characterName, characterRace, newArmorButton);
        VBox rightBox = new VBox();
        rightBox.getChildren().addAll();








        // Base Stats
        Label baseStatsLabel = new Label("Základní Staty:");
        baseStatsLabel.setStyle("-fx-text-fill: White; -fx-font-size: 20px;");
        rightBox.getChildren().add(baseStatsLabel);
        characterData.getBaseStats().forEach((stat, value) -> {
            Label statLabel = new Label(stat + ": " + value);
            statLabel.setStyle("-fx-text-fill: White; -fx-font-size: 16px;");

            rightBox.getChildren().add(statLabel);
        });
        // Custom Stats
        Label customStatsLabel = new Label("Vlastní Staty:");
        customStatsLabel.setStyle("-fx-text-fill: White; -fx-font-size: 20px;");
        rightBox.getChildren().add(customStatsLabel);
        characterData.getCustomStats().forEach((stat, value) -> {
            Label statLabel = new Label(stat + ": " + value);
            statLabel.setStyle("-fx-text-fill: White; -fx-font-size: 16px;");
            rightBox.getChildren().add(statLabel);
        });

        mainBox.getChildren().addAll(leftBox, rightBox);
        setContent(mainBox);




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

    private ImageView createCharacterImageView() {
        Image characterImage = new Image("background.png");
        ImageView imageView = new ImageView(characterImage);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(600);
        imageView.setOpacity(0.5);
        return imageView;
    }
}

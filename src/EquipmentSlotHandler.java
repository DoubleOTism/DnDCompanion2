import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EquipmentSlotHandler {
    private String slotName;
    private Button slotButton;
    private List<EquipmentItem> equippedItems;

    private List<EquipmentItem> allItems;

    private CharacterData characterData;
    private CharacterTab characterTab;

    public EquipmentSlotHandler(String slotName, Button slotButton, List<EquipmentItem> equippedItems, List<EquipmentItem> allItems, CharacterData characterData, CharacterTab characterTab) {
        this.slotName = slotName;
        this.slotButton = slotButton;
        this.equippedItems = equippedItems;
        this.allItems = allItems;
        this.characterData = characterData;
        this.characterTab = characterTab;

        slotButton.setOnAction(event -> handleButtonClick());
    }








    private void handleButtonClick() {
        if (slotButton.getText().equals(slotName)) {
            EquipmentItem selectedEquipment = showEquipmentSelectionDialog(slotName);
            if (selectedEquipment != null) {
                unequipMultiSlotItems(slotButton);
                equippedItems.add(selectedEquipment);
                updateSlotButtonText(slotButton, selectedEquipment.getName());
                characterData.updateTotalStats(equippedItems); // Pass the updated list of equipped items
                characterTab.updateTotalStatsLabel(characterData.getTotalStats()); // Update label in CharacterTab
                characterData.getEquippedItemNames().put(slotButton, selectedEquipment.getName());
            }
        } else {
            EquipmentItem equippedItem = getEquippedItemInSlot(slotName);
            unequipMultiSlotItems(slotButton);
            updateSlotButtonText(slotButton, null);
            characterData.updateTotalStats(equippedItems); // Pass the updated list of equipped items
            characterTab.updateTotalStatsLabel(characterData.getTotalStats()); // Update label in CharacterTab
            characterData.getEquippedItemNames().remove(slotButton);
        }
    }







    private EquipmentItem showEquipmentSelectionDialog(String slotName) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Vyberte vybavení");
        System.out.println("All: " + allItems);
        System.out.println("Equipped: " + equippedItems);
        VBox selectionVBox = new VBox();
        ListView<EquipmentItem> equipmentListView = new ListView<>();

        for (EquipmentItem equipmentItem : allItems) {
            if (equipmentItem.getEquipSlots().contains(slotName) && !equippedItems.contains(equipmentItem)) {
                equipmentListView.getItems().add(equipmentItem);
            }
        }

        Button useButton = new Button("Použít");
        Button cancelButton = new Button("Zrušit");

        useButton.setOnAction(event -> dialog.close());
        cancelButton.setOnAction(event -> dialog.close());

        selectionVBox.getChildren().addAll(equipmentListView, useButton, cancelButton);
        selectionVBox.setSpacing(10);
        selectionVBox.setPadding(new Insets(10));

        Scene dialogScene = new Scene(selectionVBox, 300, 300);
        dialog.setScene(dialogScene);
        dialog.showAndWait();

        return equipmentListView.getSelectionModel().getSelectedItem();
    }
    private EquipmentItem getEquippedItemInSlot(String slotName) {
        for (EquipmentItem equippedItem : equippedItems) {
            if (equippedItem.getEquipSlots().contains(slotName)) {
                return equippedItem;
            }
        }
        return null;
    }

    Button getSlotButton() {
        return slotButton;
    }


    private void unequipMultiSlotItems(Button slotButton) {
        String itemNameToRemove = slotButton.getText();

        EquipmentItem equippedItemToRemove = null;
        for (EquipmentItem equippedItem : equippedItems) {
            if (equippedItem.getName().equals(itemNameToRemove)) {
                equippedItemToRemove = equippedItem;
                break;
            }
        }

        if (equippedItemToRemove != null) {
            equippedItems.remove(equippedItemToRemove);
            updateSlotButtonText(slotButton, slotName);


            // Recalculate totalStats after unequipping
            characterData.updateTotalStats(equippedItems);
            characterTab.updateTotalStatsLabel(characterData.getTotalStats());
        }
    }















    private boolean isItemEquipped(EquipmentItem equipmentItem) {
        return equippedItems.contains(equipmentItem);
    }

    void updateSlotButtonText(Button buttonToBeUpdated, String text) {
        if (text == null) {
            buttonToBeUpdated.setText(slotName);
        } else {
            buttonToBeUpdated.setText(text);
        }
    }

}

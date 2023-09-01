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
    private String uniqueIdentifier;
    public EquipmentSlotHandler(String uniqueIdentifier, String slotName, Button slotButton, List<EquipmentItem> equippedItems, List<EquipmentItem> allItems, CharacterData characterData, CharacterTab characterTab) {
        this.uniqueIdentifier = uniqueIdentifier;
        this.slotName = slotName;
        this.slotButton = slotButton;
        this.equippedItems = equippedItems;
        this.allItems = allItems;
        this.characterData = characterData;
        this.characterTab = characterTab;
        updateSlotButtonText(slotButton, characterData.getEquippedItemName(slotName));
        slotButton.setOnAction(event -> handleButtonClick());
    }
    String getSlotName() {
        return slotName;
    }
    private void handleButtonClick() {
        EquipmentItem equippedItemToRemove = null;
        String equippedItemNameToRemove = characterData.getEquippedItemName(uniqueIdentifier);

        // Find the EquipmentItem to remove based on the equipped item name
        for (EquipmentItem equippedItem : equippedItems) {
            if (equippedItem.getName().equals(equippedItemNameToRemove)) {
                equippedItemToRemove = equippedItem;
                break;
            }
        }

        if (equippedItemToRemove != null) {
            equippedItems.remove(equippedItemToRemove);
            characterData.setEquippedItemName(uniqueIdentifier, null); // Clear the equipped item name


            // Recalculate totalStats after unequipping
            characterData.updateTotalStats(equippedItems);
            characterTab.updateTotalStatsLabel(characterData.getTotalStats());

            int hpChange = equippedItemToRemove.getHpChange();
            characterData.modifyHP(-hpChange);
            characterData.modifyCurrentHealth(-hpChange);

            // Update button text to show the slot name
            updateSlotButtonText(slotButton, slotName);
            characterTab.updateHPLabel();
        } else {
            // Show equipment selection dialog and handle the selected item
            EquipmentItem selectedEquipment = showEquipmentSelectionDialog(slotName);
            if (selectedEquipment != null) {
                equippedItems.add(selectedEquipment);
                characterData.setEquippedItemName(uniqueIdentifier, selectedEquipment.getName()); // Update equipped item name

                int hpChange = selectedEquipment.getHpChange();
                characterData.modifyHP(hpChange);
                characterData.modifyCurrentHealth(hpChange);


                // Recalculate totalStats after equipping
                characterData.updateTotalStats(equippedItems);
                characterTab.updateTotalStatsLabel(characterData.getTotalStats());

                // Update button text to show the equipped item's name
                updateSlotButtonText(slotButton, selectedEquipment.getName());
                characterTab.updateHPLabel();
            }
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
    String getUniqueIdentifier() {
        return uniqueIdentifier;
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

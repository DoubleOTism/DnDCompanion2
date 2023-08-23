import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class EquipmentSlotHandler {
    private String slotName;
    private Button slotButton;
    private List<EquipmentItem> equippedItems;

    private List<EquipmentItem> allItems;

    public EquipmentSlotHandler(String slotName, Button slotButton, List<EquipmentItem> equippedItems, List<EquipmentItem> allItems) {
        this.slotName = slotName;
        this.slotButton = slotButton;
        this.equippedItems = equippedItems;
        this.allItems = allItems;

        slotButton.setOnAction(event -> handleButtonClick());
    }

    private void handleButtonClick() {
        EquipmentItem selectedEquipment = showEquipmentSelectionDialog(slotName);
        if (selectedEquipment != null) {
            unequipMultiSlotItems(selectedEquipment);

            EquipmentItem equippedItem = getEquippedItemInSlot(slotName);
            if (equippedItem != null) {
                equippedItems.remove(equippedItem);
            }

            equippedItems.add(selectedEquipment);
            updateSlotButtonText(selectedEquipment.getName());
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
            if (equipmentItem.getEquipSlots().contains(slotName)) {
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


    private void unequipMultiSlotItems(EquipmentItem selectedEquipment) {
        equippedItems.removeIf(equippedItem -> equippedItem.getEquipSlots().contains(slotName));
    }


    private boolean isItemEquipped(EquipmentItem equipmentItem) {
        return equippedItems.contains(equipmentItem);
    }

    private void updateSlotButtonText(String text) {
        slotButton.setText(text != null ? text : slotName);
    }
}

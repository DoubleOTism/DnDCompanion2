import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EquipmentWindow {
    private ListView<String> itemList;
    private Button equipButton;
    private Stage stage;

    public EquipmentWindow() {
        itemList = new ListView<>();
        equipButton = new Button("Equip");
        equipButton.setOnAction(event -> equipSelectedItem());

        VBox vbox = new VBox(itemList, equipButton);
        Scene scene = new Scene(vbox, 300, 200);

        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Equipment Window");
    }

    public void show() {
        stage.show();
    }

    private void equipSelectedItem() {
        String selectedItem = itemList.getSelectionModel().getSelectedItem();
        // Logic to equip the selected item to the character
        // Update the equippedItems list and character stats
        // Change the text of the corresponding button on CharacterTab
        // Update the UI accordingly
        stage.close();
    }
}

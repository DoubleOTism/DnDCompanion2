import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private TabPane tabPane = new TabPane();

    public static void main(String[] args) {
        launch(args);
    }

    private List<CharacterData> characterDataList = new ArrayList<>();
    private List<CharacterTab> characterTabs = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1200, 900);

        root.setCenter(tabPane);

        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Character App");
        root.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu startMenu = new Menu("Start");
        MenuItem newCharacterMenuItem = new MenuItem("Nový Charakter");
        newCharacterMenuItem.setOnAction(event -> showNewCharacterDialog());
        MenuItem loadCharacters = new MenuItem("Uložit charaktery");
        loadCharacters.setOnAction(actionEvent -> saveCharacterData(characterDataList, "characterData.ser"));
        MenuItem saveCharacters = new MenuItem("Načíst charaktery");
        saveCharacters.setOnAction(actionEvent -> loadCharacterData("characterData.ser"));
        startMenu.getItems().addAll(newCharacterMenuItem, saveCharacters,loadCharacters);


        menuBar.getMenus().add(startMenu);
        return menuBar;
    }

    private void showNewCharacterDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Nový Charakter");
        dialog.setHeaderText("Zadajte meno postavy:");

        TextField characterNameField = new TextField();
        ButtonType addButton = new ButtonType("Pridať", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(characterNameField);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == addButton) {
                return characterNameField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(characterName -> createNewCharacterTab(characterName));
    }

    private void createNewCharacterTab(String characterName) {
        CharacterData characterData = new CharacterData(characterName);
        CharacterTab characterTab = new CharacterTab(characterData);
        characterDataList.add(characterData);
        characterTabs.add(characterTab);
        tabPane.getTabs().add(characterTab);
    }


    private void saveCharacterData(List<CharacterData> characterDataList, String filename) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(characterDataList);
            System.out.println("Character data saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCharacterData(String filename) {
        List<CharacterData> loadedCharacterDataList;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            loadedCharacterDataList = (List<CharacterData>) inputStream.readObject();
            System.out.println("Character data loaded from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        for (CharacterData loadedCharacterData : loadedCharacterDataList) {
            CharacterTab loadedCharacterTab = new CharacterTab(loadedCharacterData);
            characterDataList.add(loadedCharacterData);
            characterTabs.add(loadedCharacterTab);
            tabPane.getTabs().add(loadedCharacterTab);
        }
    }




}

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CharacterTab extends Tab {
    private CharacterData characterData;

    public CharacterTab(CharacterData characterData) {
        this.characterData = characterData;
        ImageView characterImageView = createCharacterImageView();
        Pane imagePane = new Pane(characterImageView);
        HBox mainBox = new HBox();

        Label characterName = new Label(characterData.getCharacterName());
        Label characterRace = new Label("Rasa: " + characterData.getCharacterRace());
        characterName.setStyle("-fx-text-fill: White; -fx-font-size: 20px;");
        characterRace.setStyle("-fx-text-fill: White; -fx-font-size: 16px;");

        setText(characterData.getCharacterName());
        VBox leftBox = new VBox();
        leftBox.getChildren().addAll(imagePane, characterName, characterRace);
        VBox rightBox = new VBox();
        rightBox.getChildren().addAll();

        // Base Stats
        Label baseStatsLabel = new Label("Base Stats:");
        rightBox.getChildren().add(baseStatsLabel);
        characterData.getBaseStats().forEach((stat, value) -> {
            Label statLabel = new Label(stat + ": " + value);
            rightBox.getChildren().add(statLabel);
        });
        // Custom Stats
        Label customStatsLabel = new Label("Custom Stats:");
        rightBox.getChildren().add(customStatsLabel);
        characterData.getCustomStats().forEach((stat, value) -> {
            Label statLabel = new Label(stat + ": " + value);
            rightBox.getChildren().add(statLabel);
        });



        mainBox.getChildren().addAll(leftBox, rightBox);
        setContent(mainBox);



        //equipment buttons

        Button equipHelmetButton = new Button("Equip Helmet");

        Button equipChestplateButton = new Button("Equip Chestplate");

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

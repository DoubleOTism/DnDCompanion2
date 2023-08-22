import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CharacterTab extends Tab {
    private CharacterData characterData;

    public CharacterTab(CharacterData characterData) {
        ImageView characterImageView = createCharacterImageView();
        Pane imagePane = new Pane(characterImageView);


        this.characterData = characterData;
        setText(characterData.getCharacterName());
        setContent(imagePane);

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

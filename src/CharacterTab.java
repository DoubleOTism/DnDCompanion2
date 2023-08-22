import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class CharacterTab extends Tab {
    private CharacterData characterData;

    public CharacterTab(CharacterData characterData) {
        this.characterData = characterData;
        setText(characterData.getCharacterName());
        // Add more content to the character tab as needed
    }
}

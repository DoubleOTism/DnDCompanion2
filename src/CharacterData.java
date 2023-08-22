import java.io.Serializable;
import java.util.Map;

public class CharacterData implements Serializable {
    private String characterName;

    public CharacterData(String characterName) {
        this.characterName = characterName;
    }

    public String getCharacterName() {
        return characterName;
    }

}

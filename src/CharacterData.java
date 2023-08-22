import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterData implements Serializable {
    private String characterName;
    private Map<String, Integer> statistics = new HashMap<>();

    public CharacterData(String characterName) {
        this.characterName = characterName;
    }

    public String getCharacterName() {
        return characterName;
    }


    public void updateStats() {
        // Implement your logic to update character stats based on equipped items
    }

}

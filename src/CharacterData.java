import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterData implements Serializable {
    private String characterName;
    private String characterRace;

    private Map<String, Integer> baseStats;

    private Map<String, Integer> customStats;


    public CharacterData(String characterName, String characterRace, Map<String, Integer> baseStats, Map<String, Integer> custoMStats) {
        this.characterName = characterName;
        this.characterRace = characterRace;
        this.baseStats = baseStats;
        this.customStats = custoMStats;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getCharacterRace() {
        return characterRace;
    }

    public Map<String, Integer> getBaseStats() {
        return baseStats;
    }

    public Map<String, Integer> getCustomStats() {
        return customStats;
    }

}

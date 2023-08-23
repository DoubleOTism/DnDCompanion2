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

    private List<EquipmentItem> equippedItems;
    private List<EquipmentItem> allItems;


    public CharacterData(String characterName, String characterRace, Map<String, Integer> baseStats, Map<String, Integer> customStats, List<EquipmentItem> allItems, List<EquipmentItem> equippedItems) {
        this.characterName = characterName;
        this.characterRace = characterRace;
        this.baseStats = baseStats;
        this.customStats = customStats;
        this.allItems = allItems;
        this.equippedItems = equippedItems;
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

    public List<EquipmentItem> getInventory() {
        return allItems;
    }

    public List<EquipmentItem> getEquippedItems() {
        return equippedItems;
    }

    public void addEquippedItem(EquipmentItem item) {
        equippedItems.add(item);
    }

    public void addItem(EquipmentItem item) {
        allItems.add(item);
    }

}

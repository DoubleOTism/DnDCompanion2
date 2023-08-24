import javafx.scene.control.Button;

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

    private Map<String, Integer> totalStats;

    private Map<Button, String> equippedItemNames;



    public CharacterData(String characterName, String characterRace, Map<String, Integer> baseStats, Map<String, Integer> customStats, List<EquipmentItem> allItems, List<EquipmentItem> equippedItems, Map<Button, String> equippedItemNames) {
        this.characterName = characterName;
        this.characterRace = characterRace;
        this.baseStats = baseStats;
        this.customStats = customStats;
        this.allItems = allItems;
        this.equippedItems = equippedItems;
        this.equippedItemNames = equippedItemNames;
        totalStats = new HashMap<>(baseStats);
        customStats.forEach((stat, value) -> totalStats.merge(stat, value, Integer::sum));

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

    public Map<Button, String> getEquippedItemNames() {
        return equippedItemNames;
    }


    public void addItem(EquipmentItem item) {
        allItems.add(item);
    }

    public void updateTotalStats(List<EquipmentItem> equippedItems) {
        // Clear the existing totalStats
        totalStats.clear();

        // Recalculate totalStats by adding equipped item stats
        totalStats.putAll(baseStats);
        customStats.forEach((stat, value) -> totalStats.merge(stat, value, Integer::sum));

        for (EquipmentItem equippedItem : equippedItems) {
            Map<String, Integer> itemStats = equippedItem.getModifiedStats();
            for (Map.Entry<String, Integer> statEntry : itemStats.entrySet()) {
                System.out.println(statEntry.getKey());
                System.out.println(statEntry.getValue());
                String statName = statEntry.getKey();
                int statChange = statEntry.getValue();

                if (customStats.containsKey(statName)) {
                    totalStats.put(statName, totalStats.get(statName) + statChange);
                } else if (baseStats.containsKey(statName)) {
                    totalStats.put(statName, totalStats.get(statName) + statChange);
                }
            }
        }
        System.out.println(totalStats);
    }

    public Map<String, Integer> getTotalStats() {
        return totalStats;
    }











}

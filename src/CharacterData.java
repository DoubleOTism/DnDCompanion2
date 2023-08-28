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

    private Map<String, String> equippedItemNames = new HashMap<>();

    private Map<String, Integer> statIncreases = new HashMap<>();

    private int xp;
    private int level;
    private int requireXP = 100;

    private Integer health;

    private Integer currentHealth;






    public CharacterData(String characterName, String characterRace, Integer health, Map<String, Integer> baseStats, Map<String, Integer> customStats, List<EquipmentItem> allItems, List<EquipmentItem> equippedItems) {
        this.characterName = characterName;
        this.characterRace = characterRace;
        this.baseStats = baseStats;
        this.customStats = customStats;
        this.allItems = allItems;
        this.equippedItems = equippedItems;
        this.xp = 0;
        this.level = 0;
        this.health = health;
        this.currentHealth = health;
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
    public void setEquippedItemName(String slotName, String itemName) {
        equippedItemNames.put(slotName, itemName);
    }

    public String getEquippedItemName(String slotName) {
        return equippedItemNames.getOrDefault(slotName, null);
    }

    public Map<String, String> getEquippedItemNames() {
        return equippedItemNames;
    }

    public EquipmentItem findItemByName(String itemName) {
        for (EquipmentItem equipmentItem : allItems) {
            if (equipmentItem.getName().equals(itemName)) {
                return equipmentItem;
            }
        }
        return null; // Item not found
    }


    //XP
    public int getXP() {
        return xp;
    }

    public int getRequireXP() {
        return requireXP;
    }

    public void setXP(int xp) {
        this.xp = xp;
        updateLevel();
    }

    public int getLevel() {
        return level;
    }

    public void updateLevel() {
        int requiredXP = requireXP;
        double levelMultiplier = 1.5; // 150% of previous XP
        int currentLevel = level; // Start from the current level

        while (xp >= requiredXP) {
            xp -= requiredXP;
            requiredXP = (int) (requiredXP * levelMultiplier);
            currentLevel++;
        }

        level = currentLevel;
        this.requireXP = requiredXP;
    }

    public double getXPProgress() {
        double progress = (double) xp / requireXP;
        return Math.min(1.0, progress); // Cap at 1.0 (100%)
    }

    public void increaseStat(String stat) {
        if (baseStats.containsKey(stat)) {
            baseStats.put(stat, baseStats.get(stat) + 1);
            updateTotalStats(equippedItems);
        } else if (customStats.containsKey(stat)) {
            customStats.put(stat, customStats.get(stat) + 1);
            updateTotalStats(equippedItems);
        }
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


    public Integer getMaxHP() {
        return health;
    }



    public Integer getCurrentHealth() {
        return currentHealth;
    }

    public void modifyCurrentHealth(int amount) {
        currentHealth += amount;
        if (currentHealth < 0) {
            currentHealth = 0;
        } else if (currentHealth > getMaxHP()) {
            currentHealth = getMaxHP();
        }
    }

    public void modifyHP(Integer amount) {
        health = getMaxHP() + amount;
    }

    public List<EquipmentItem> getAllItems() {
        return allItems;
    }













}

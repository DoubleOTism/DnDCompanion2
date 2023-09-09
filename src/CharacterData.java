import java.io.Serializable;
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
    private static final int MIN_CARRY_WEIGHT = 5;

    private String currentDamage = "Nic";

    private long currencyUnits;

    private Map<String, String> characterAbilities = new HashMap<>();








    public CharacterData(String characterName, String characterRace, Integer health, Map<String, Integer> baseStats, Map<String, Integer> customStats, List<EquipmentItem> allItems, List<EquipmentItem> equippedItems, String currentDamage) {
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
        this.currentDamage = currentDamage;
        this.currencyUnits = 0;

    }
    public void addAbility(String name, String description) {
        characterAbilities.put(name, description);
    }

    public void removeAbility(String name) {
        characterAbilities.remove(name);
    }




    public void addToStat(String statName, int amount) {
        // Check if the stat is in baseStats
        if (baseStats.containsKey(statName)) {
            int currentStatValue = baseStats.get(statName);
            int newStatValue = currentStatValue + amount;
            baseStats.put(statName, newStatValue);
            updateTotalStats(equippedItems);
        }
        // Check if the stat is in customStats
        else if (customStats.containsKey(statName)) {
            int currentStatValue = customStats.get(statName);
            int newStatValue = currentStatValue + amount;
            customStats.put(statName, newStatValue);
            updateTotalStats(equippedItems);
        }
        // Ignore if the stat is not found in either baseStats or customStats
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
    public void setCurrentPlayerDamage(String newDamage) {
        this.currentDamage = newDamage;
    }

    public String getCurrentPlayerDamage() {
        return currentDamage;
    }
    public String consolidateDamageDice() {
        Map<String, Integer> consolidatedDice = new HashMap<>();

        boolean hasWeapons = false; // Flag to check if there are any weapons equipped

        // Iterate through equipped weapons
        for (EquipmentItem weapon : equippedItems) {
            if (weapon.isWeapon()) {
                hasWeapons = true; // Set the flag to true if a weapon is found

                String damageDiceString = weapon.getDamageDice();

                // Split the damageDiceString into individual dice components
                String[] diceComponents = damageDiceString.split("\\s*\\+\\s*");

                for (String component : diceComponents) {
                    // Split each component into count and dice type
                    String[] parts = component.split("d");
                    if (parts.length == 2) {
                        int diceCount = Integer.parseInt(parts[0]);
                        String diceType = "d" + parts[1];

                        // Add to consolidated dice or update the count
                        consolidatedDice.put(diceType, consolidatedDice.getOrDefault(diceType, 0) + diceCount);
                    }
                }
            }
        }

        if (!hasWeapons) {
            return "1d4"; // Return this message if no weapons are equipped
        }

        // Create the consolidated damage dice string
        StringBuilder consolidatedDamageDice = new StringBuilder();
        boolean firstDice = true;
        for (Map.Entry<String, Integer> entry : consolidatedDice.entrySet()) {
            int diceCount = entry.getValue();
            if (diceCount > 0) {
                if (!firstDice) {
                    consolidatedDamageDice.append(" + ");
                }
                consolidatedDamageDice.append(diceCount).append(entry.getKey());
                firstDice = false;
            }
        }

        return consolidatedDamageDice.toString();
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

                String statName = statEntry.getKey();
                int statChange = statEntry.getValue();

                if (customStats.containsKey(statName)) {
                    totalStats.put(statName, totalStats.get(statName) + statChange);
                } else if (baseStats.containsKey(statName)) {
                    totalStats.put(statName, totalStats.get(statName) + statChange);
                }
            }
        }
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
    public float calculateCarryWeight() {
        int silaStat = totalStats.get("Síla");
        int carryWeight;
        if (silaStat < 0) {
            carryWeight = 20 + silaStat * 5;
            carryWeight = Math.max(carryWeight, 5);
        } else {
            carryWeight = 20 + silaStat * 10;
        }
        return Math.max(carryWeight, MIN_CARRY_WEIGHT);
    }

    public float calculateCurrentWeight() {
        float maxCarryWeight = calculateCarryWeight();
        float totalWeight = 0;
        for (EquipmentItem item : allItems) {
            totalWeight += item.getWeight();
        }
        float currentCW = maxCarryWeight-totalWeight;

        return totalWeight;
    }

    public float calculateAvailableWeight() {
        float maxCarry = calculateCarryWeight();
        float currentCarry = calculateCurrentWeight();
        float availableWeight = maxCarry - currentCarry;

        return availableWeight;
    }

    public double getWeightRatio() {
        double maxCarryWeight = calculateCarryWeight();
        double currentWeight = calculateCurrentWeight();
        double progress = currentWeight / maxCarryWeight;
        return Math.min(1.0, progress); // Cap at 1.0 (100%)
    }

    public void addCurrency(long units) {
        this.currencyUnits += units;
    }

    // Deduct units from the character's currency, returns true if there are enough units to deduct, false otherwise
    public boolean deductCurrency(long units) {
        if (this.currencyUnits >= units) {
            this.currencyUnits -= units;
            return true;
        }
        return false;
    }

    // Get the character's currency in various denominations
    public long getCurrency() {
        return currencyUnits;
    }


    public Map<String, String> getCharacterAbilities() {
        return characterAbilities;
    }
}
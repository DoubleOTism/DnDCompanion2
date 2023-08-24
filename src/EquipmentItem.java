import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EquipmentItem implements Serializable {
    private String name;
    private Map<String, Integer> modifiedStats;
    private int hpChange;
    private int weight;
    private String selectedSlot;

    public EquipmentItem(String name,String selectedSlot, Map<String, Integer> modifiedStats, int hpChange, int weight) {
        this.name = name;
        this.selectedSlot = selectedSlot;
        this.modifiedStats = modifiedStats;
        this.hpChange = hpChange;
        this.weight = weight;
    }

    // Getters and setters for all fields

    public String getName() {
        return name;
    }

    public Map<String, Integer> getModifiedStats() {
        return modifiedStats;
    }

    public int getHpChange() {
        return hpChange;
    }

    public int getWeight() {
        return weight;
    }

    public String getEquipSlots() {
        return selectedSlot;
    }



    @Override
    public String toString() {
        return name;
    }
}

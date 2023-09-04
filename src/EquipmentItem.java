import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EquipmentItem implements Serializable {
    private String name;
    private Map<String, Integer> modifiedStats;
    private int hpChange;
    private float weight;
    private String selectedSlot;
    private boolean isWeapon;
    private boolean isTwoHanded;
    private String damageDice;
    public EquipmentItem(String name,String selectedSlot, Map<String, Integer> modifiedStats, int hpChange, float weight, boolean isWeapon, boolean isTwoHanded, String damageDice) {
        this.name = name;
        this.selectedSlot = selectedSlot;
        this.modifiedStats = modifiedStats;
        this.hpChange = hpChange;
        this.weight = weight;
        this.isWeapon = isWeapon;
        this.isTwoHanded = isTwoHanded;
        this.damageDice = damageDice;


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
    public float getWeight() {
        return weight;
    }
    public String getEquipSlots() {
        return selectedSlot;
    }
    @Override
    public String toString() {
        return name;
    }

    public void setName(String name) {
    }

    public void setWeight(double weight) {
    }

    public void setHpChange(int hpChange) {
    }

    public void setDamageDice(String damageDice) {
    }

    public void setIsWeapon(boolean b) {
    }

    public String getDamageDice() {
        return damageDice;
    }

    public boolean isTwoHanded() {
        return isTwoHanded;
    }

    public boolean isWeapon() {
        return isWeapon;
    }


}

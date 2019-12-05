package Model;

public class Inhouse extends Part {

    private int machineId;

    public Inhouse(int id, String name, double price, int stock, int minPart, int max, int machineId) {
        super(id, name, price, stock, minPart, max);
        this.machineId = machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public int getMachineId() {
        return this.machineId;
    }
}

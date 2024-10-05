package indie.outsource.model;

public abstract class Seeds extends Product {

    private int weight;
    private boolean edible;

    public Seeds(int id, String name, float price, int weight, boolean edible) {
        super(id, name, price);
        this.weight = weight;
        this.edible = edible;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isEdible() {
        return edible;
    }

    public void setEdible(boolean edible) {
        this.edible = edible;
    }

    @Override
    public String getProductInfo() {
        return getWeight()+ " bag of " + getName() + " seeds";
    }
}

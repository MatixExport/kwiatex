package indie.outsource.model;

public class GrassesSeeds extends Seeds{

    public GrassesSeeds(int id, String name, float price, int weight, boolean edible) {
        super(id, name, price, weight, edible);
    }

    @Override
    public float calculateSellingPrice() {
        return getWeight() * getPrice();
    }


}

package indie.outsource.model;

public class VegetableSeeds extends Seeds{


    public VegetableSeeds(int id, String name, float price, int weight, boolean edible) {
        super(id, name, price, weight, edible);
    }

    @Override
    public float calculateSellingPrice() {
        return getPrice();
    }

}

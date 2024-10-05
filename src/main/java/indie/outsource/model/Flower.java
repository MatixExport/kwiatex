package indie.outsource.model;

public class Flower extends Plant{

    private String color;

    public Flower(int id, String name, float price, int growthStage, String color) {
        super(id, name, price, growthStage);
        this.color = color;
    }

    @Override
    public float calculateSellingPrice() {
        return getPrice();
    }

    @Override
    public String getProductInfo() {
        return String.format("%s %s within %d growth stage.",getColor(), getName(), getGrowthStage());
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

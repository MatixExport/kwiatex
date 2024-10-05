package indie.outsource.model;

public abstract class Plant extends Product{

    private int growthStage;

    public Plant(int id, String name, float price, int growthStage) {
        super(id, name, price);
        this.growthStage = growthStage;
    }

    public int getGrowthStage() {
        return growthStage;
    }

    public void setGrowthStage(int growthStage) {
        this.growthStage = growthStage;
    }
}

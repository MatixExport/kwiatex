package indie.outsource.model.products;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Access(AccessType.FIELD)
@NoArgsConstructor
public abstract class Seeds extends Product {

    private int weight;
    private boolean edible;

    public Seeds(int id, String name, float price, int weight, boolean edible) {
        super(id, name, price);
        this.weight = weight;
        this.edible = edible;
    }

    @Override
    public String getProductInfo() {
        return getWeight()+ " bag of " + getName() + " seeds";
    }
}

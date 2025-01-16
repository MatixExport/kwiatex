package indie.outsource.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
public abstract class AbstractEntity implements Serializable {
    private UUID id;


    public AbstractEntity(UUID id) {
        this.id = id;
    }

    public AbstractEntity() {
    }
}

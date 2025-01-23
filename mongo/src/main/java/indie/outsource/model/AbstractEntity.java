package indie.outsource.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

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

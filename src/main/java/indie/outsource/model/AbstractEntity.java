package indie.outsource.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.io.Serializable;

@Setter
@Getter
public abstract class AbstractEntity implements Serializable {

    @BsonId
    @BsonProperty("_id")
    private ObjectId id;

    public AbstractEntity(ObjectId id) {
        this.id = id;
    }

    public AbstractEntity() {
    }
}

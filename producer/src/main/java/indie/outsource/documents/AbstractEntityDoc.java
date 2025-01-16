package indie.outsource.documents;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
public abstract class AbstractEntityDoc implements Serializable {
    @BsonId
    @BsonProperty("_id")
    private UUID id;


    public AbstractEntityDoc(UUID id) {
        this.id = id;
    }

    public AbstractEntityDoc() {
    }
}

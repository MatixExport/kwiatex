package indie.outsource.model;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.UUID;

@Getter
public abstract class AbstractEntity implements Serializable {

    @BsonProperty("_id")
    private UUID id;

    public AbstractEntity(UUID id) {
        this.id = id;
    }

    public AbstractEntity() {
    }
}

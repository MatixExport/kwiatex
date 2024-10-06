package indie.outsource.model;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @NotNull
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID entityId;

    @Version
    private long version;


}

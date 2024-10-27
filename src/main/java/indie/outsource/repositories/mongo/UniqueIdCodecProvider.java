package indie.outsource.repositories.mongo;

import lombok.NoArgsConstructor;
import org.bson.codecs.Codec;
import org.bson.codecs.UuidCodec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.UUID;


public class UniqueIdCodecProvider implements CodecProvider {

    public UniqueIdCodecProvider() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
        if(aClass == UUID.class) {
            return (Codec<T>) new UniqueIdCodec(codecRegistry);
        }
        return null;
    }
}

package indie.outsource.repositories.mongo;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;

import java.util.UUID;

public class UniqueIdCodec implements Codec<UUID> {

    private final Codec<UUID> uuidCodec;

    public UniqueIdCodec(CodecRegistry codecRegistry) {
        uuidCodec = codecRegistry.get(UUID.class);
    }

    @Override
    public UUID decode(BsonReader bsonReader, DecoderContext decoderContext) {
        ObjectId objectId = bsonReader.readObjectId();
        return UUID.nameUUIDFromBytes(objectId.toByteArray());
//        return uuidCodec.decode(bsonReader, decoderContext);
    }

    @Override
    public void encode(BsonWriter bsonWriter, UUID uuid, EncoderContext encoderContext) {
        uuidCodec.encode(bsonWriter, uuid, encoderContext);
    }

    @Override
    public Class<UUID> getEncoderClass() {
        return UUID.class;
    }
}

package indie.outsource.repositories.kafka;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class AvroPOJOMapper {
    public static GenericRecord mapToGenericRecord(Schema schema, Object pojo) throws IllegalAccessException {
        GenericRecord record = new GenericData.Record(schema);
        for (Schema.Field field : schema.getFields()) {
            String fieldName = field.name();
            Field pojoField = null;
            try {
                pojoField = pojo.getClass().getDeclaredField(fieldName);
                pojoField.setAccessible(true);

                Object fieldValue = pojoField.get(pojo);
                Schema fieldSchema = field.schema();

                record.put(fieldName, mapValue(fieldSchema, fieldValue));
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Field mismatch: " + fieldName);
            }
        }
        return record;
    }

    private static Object mapValue(Schema schema, Object value) throws IllegalAccessException {
        if (value == null) {
            return null;
        }
        switch (schema.getType()) {
            case RECORD:
                return mapToGenericRecord(schema, value);
            case ARRAY:
                Schema elementType = schema.getElementType();
                Collection<?> collection = (Collection<?>) value;
                List<Object> avroArray = new ArrayList<>();
                for (Object element : collection) {
                    avroArray.add(mapValue(elementType, element));
                }
                return avroArray;

            default:
                return value;
        }
    }
}

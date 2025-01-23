package indie.outsource.repositories.mongo;

import com.mongodb.client.model.ValidationOptions;
import org.bson.Document;

public abstract class MongoSchemaConfig {
    public static ValidationOptions getValidationOptions() {
        return new ValidationOptions().validator(
            Document.parse("""
                {
                  $jsonSchema:{
                      "bsonType": "object",
                      "required": ["product","productInfo"],
                      "properties":{
                      "productInfo":{
                          "bsonType": "object",
                          "required": ["quantity"],
                          "properties":{
                              "quantity": {
                                  "bsonType": "int",
                                  "minimum":0
                              }
                          }
            
                      }
                      }
                  }
                }
                """
            )
        );
    }
}

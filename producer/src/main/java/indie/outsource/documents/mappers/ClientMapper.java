package indie.outsource.documents.mappers;

import indie.outsource.documents.ClientDoc;
import indie.outsource.model.Client;


public class ClientMapper {
    public static Client toDomainModel(ClientDoc doc){
        return doc.toDomainModel();
    }
    public static ClientDoc fromDomainModel(Client client){
        return new ClientDoc(
                client.getId(),
                client.getName(),
                client.getSurname(),
                client.getAddress()
        );
    }
}

package repository;

import model.Client;

import java.util.Vector;

public class ClientRepository {
    Vector <Client> clients = new Vector<>();

    public ClientRepository() {}
    public void addClient(Client client) {
        clients.add(client);
    }
    public Client lookupClient(int clientId) {

        for (Client client : clients) {
            if (client.getId() == clientId) {
                return client;
            }
        }

        return null;
    }
}

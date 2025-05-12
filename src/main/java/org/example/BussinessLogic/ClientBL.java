package org.example.BussinessLogic;

import org.example.DataAccess.ClientDAO;
import org.example.DataModels.Client;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class created to be used on Client Objects
 * <p>
 * Provides all the functionalities necessary for the CRUD operations on the DB from the Client Panel
 * </p>
 */
public class ClientBL{
    private ClientDAO clientDAO;

    public ClientBL(){
        clientDAO = new ClientDAO();
    }

    public List<Client> findAllClients() {
        List<Client> clients = clientDAO.findAll();
        if(clients == null || clients.isEmpty()) {
            throw new NoSuchElementException("No clients could be found");
        }
        return clients;
    }

    public Client findClientById(int id) {
        Client client = clientDAO.findById(id);
        if(client == null) {
            throw new NoSuchElementException("Client with id " + id + " could not be found");
        }
        return client;
    }

    public void insertClient(List<String> values){
        clientDAO.insert(values);
    }

    public void updateClient(List<String> values){
        clientDAO.update(values);
    }

    public void deleteClientWithId(int id){
        clientDAO.deleteWithId(id);
    }
}

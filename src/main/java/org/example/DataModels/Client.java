package org.example.DataModels;

/**
 * Represents the Client Object in the Project
 * <p>
 * Has all the CRUD Operations for the DB in the ClientBL Class
 * </p>
 */
public class Client {
    private int id;
    private String name;
    private String address;
    private String email;

    public Client() {};

    public Client(int id, String email, String address, String name) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.name = name;
    }

    public Client(String email, String address, String name) {
        this.email = email;
        this.address = address;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

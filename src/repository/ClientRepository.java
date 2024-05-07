package repository;

import model.Client;
import model.DisabledClient;
import model.GuestClient;
import model.RegisteredClient;

import java.sql.*;
import java.util.Vector;

public class ClientRepository {
    Connection db;
    public ClientRepository(Connection db) {
        this.db = db;
    }
    public int addClient(String name, String email, String phone, String userType) {
        // add client to the database
        int id = -1;
        try {
            String insert = String.format("INSERT INTO clients (userType, name, phone, email, nrPoints) " +
                    "VALUES (%s, %s, %s, %s, 0)",
                    "'" + userType + "'", "'" + name + "'", "'" + phone + "'", "'" + email + "'");
            PreparedStatement stmt = db.prepareStatement(insert, new String[]{"ID"});

            stmt.executeUpdate();
            db.commit();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
                System.out.println("Successfully added client with id = " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    public void updateClient(Client client) {
        try {
            PreparedStatement stmt = db.prepareStatement("UPDATE clients SET nrPoints = " + client.getNrPoints() +
                    " WHERE id = " + client.getId());
            stmt.executeUpdate();
            db.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public Client lookupClient(int clientId) {
        Client user = null;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM clients WHERE id=" + clientId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String userType = rs.getString("userType");
                String name = rs.getString("name");
                String phone  = rs.getString("phone");
                String email = rs.getString("email");
                int nrPoints = rs.getInt("nrPoints");

                switch (userType) {
                    case "guest": user = new GuestClient(id, name, phone, email);
                    case "disabled": user = new DisabledClient(id, name, phone, email);
                    default: user = new RegisteredClient(id, name, phone, email, nrPoints);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}

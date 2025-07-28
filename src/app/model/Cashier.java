package app.model;

import java.sql.*;
import app.utility.DatabaseUtility;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cashier {
    private int id;
    private int idUser;
    private int idStore;

    public Cashier(int id, int idUser, int idStore) {
        this.id = id;
        this.idUser = idUser;
        this.idStore = idStore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdStore() {
        return idStore;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    
    
}


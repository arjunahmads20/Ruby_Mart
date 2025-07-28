package app.model;

import java.sql.*;
import app.utility.DatabaseUtility;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Admin {
    private int id;
    private int idUser;

    public Admin(int id, int idUser) {
        this.id = id;
        this.idUser = idUser;
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
    
}


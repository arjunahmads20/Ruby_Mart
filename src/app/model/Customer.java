package app.model;

import java.sql.*;
import app.utility.DatabaseUtility;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Customer {
    private int id;
    private int idUser;
    private String status;
    private String accountNumber;
    private int accountPin;
    private BigDecimal accountBalance;

    public Customer(int id, int idUser, String status, String accountNumber, int accountPin, BigDecimal accountBalance) {
        this.id = id;
        this.idUser = idUser;
        this.status = status;
        this.accountNumber = accountNumber;
        this.accountPin = accountPin;
        this.accountBalance = accountBalance;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getAccountPin() {
        return accountPin;
    }

    public void setAccountPin(int accountPin) {
        this.accountPin = accountPin;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }
    
    

}


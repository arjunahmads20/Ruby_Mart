/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.exception;

/**
 *
 * @author ACER
 */
public class ItemIsOutOfStockException extends RuntimeException {
    public ItemIsOutOfStockException () {
        super("The item is currently out of stock");
    }
    public ItemIsOutOfStockException (String message) {
        super(message);
    }
    public ItemIsOutOfStockException (String message, Throwable cause) {
        super(message, cause);
    }
}

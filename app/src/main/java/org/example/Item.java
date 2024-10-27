package org.example;

public class Item {
    String name;
    int x, y;
    String message;

    public Item(String name, int x, int y, String message) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Item{name='" + name + "', location=(" + x + ", " + y + "), message='" + message + "'}";
    }
}


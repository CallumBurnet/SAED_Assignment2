package org.example;

public class Item {
    String name;
    int x, y;
    String message;
    String type;

    public Item(String name, int x, int y, String message) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.message = message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getName(){
        return this.name;
    }
    public String getMessage(){
        return message;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    @Override
    public String toString() {
        return "Item{name='" + name + "', location=(" + x + ", " + y + "), message='" + message + "'}";
    }
}


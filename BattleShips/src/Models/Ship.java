package Models;

public class Ship {

    private String name;
    private int x;
    private int y;

    private int size;

    public Ship(String name, int x, int y, int size){
        this.name = name;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public String getName(){
        return name;
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }
}

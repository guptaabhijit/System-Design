package Models;

import java.util.*;

public class Player {
    private String name;
    private int[][] battlefield;
    private List<Ship> fleet;
    private Set<String> misslesFired;

    public Player(String name, int battlefielSize){
        this.name = name;
        this.battlefield = new int[battlefielSize][battlefielSize];
        this.fleet = new ArrayList<>();
        this.misslesFired = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public List<Ship> getFleet() {
        return fleet;
    }

    public int[][] getBattlefield() {
        return battlefield;
    }

    public Set<String> getMisslesFired() {
        return misslesFired;
    }

    public void addShip(Ship ship){
        fleet.add(ship);
        placeShip(ship);
    }

    private void placeShip(Ship ship){
        int x = ship.getX();
        int y = ship.getY();
        int size = ship.getSize();

        for(int i=x;i<x+size;i++){
            for(int j=y;j<y+size;j++){
                battlefield[i][j] = 1;
            }
        }
    }
}

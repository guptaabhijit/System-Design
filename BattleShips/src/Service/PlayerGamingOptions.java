package Service;

import Models.Player;
import Models.Ship;

import java.util.Random;

public class PlayerGamingOptions {
    private Player player;

    public PlayerGamingOptions(Player player){
        this.player = player;
    }

    public boolean hasShipRemaining(){
        for(Ship ship: player.getFleet()){
            if(!isShipDestroyed(ship)){
                return true;
            }
        }
        return false;
    }

    public boolean isShipDestroyed(Ship ship){
        int x = ship.getX();
        int y = ship.getY();
        int size = ship.getSize();

        for(int i=x;i<x+size;i++){
            for(int j=y;j<y+size;j++){
               if (player.getBattlefield()[i][j]!=0){
                   return false;
               }
            }
        }

        return true;
    }

    private boolean receiveHit(Player opponent, int x, int y) {
        if (opponent.getBattlefield()[x][y] == 1) {
            opponent.getBattlefield()[x][y] = 0;
            return true;
        }
        return false;
    }


    public void fireMissile(Player opponent, MissileFiringStrategy firingStrategy) {
        int[] target = firingStrategy.fireMissile(player, opponent);
        int x = target[0];
        int y = target[1];

        opponent.getMisslesFired().add(x + "," + y);

        if (receiveHit(opponent, x, y)) {
            System.out.println("Hit!: " + opponent.getName());
        } else {
            System.out.println("Miss!: "+ opponent.getName());
        }
    }

//
//    public void fireMissile(Player opponent) {
//        Random random = new Random();
//        int x, y;
//        do {
//            x = random.nextInt(opponent.getBattlefield().length);
//            y = random.nextInt(opponent.getBattlefield().length);
//        } while (opponent.getMisslesFired().contains(x + "," + y));
//
//        opponent.getMisslesFired().add(x + "," + y);
//
//        if (receiveHit(opponent, x, y)) {
//            System.out.println("Hit!");
//        } else {
//            System.out.println("Miss!");
//        }
//    }
}

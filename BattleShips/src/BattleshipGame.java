import Models.Player;
import Models.Ship;
import Service.MissileFiringStrategy;
import Service.PlayerGamingOptions;
import Service.RandomCoordinateFiringStrategy;

import java.util.Scanner;

public class BattleshipGame {

    private Player playerA;
    private Player playerB;

    private PlayerGamingOptions optionsA;
    private PlayerGamingOptions optionsB;


    public BattleshipGame(int battlefieldSize){
        playerA = new Player("Player A", battlefieldSize);
        playerB = new Player("Player B", battlefieldSize);

        optionsA = new PlayerGamingOptions(playerA);
        optionsB = new PlayerGamingOptions(playerB);
    }

    private boolean isValidShipLocation(Ship ship, Player P) {
        int x = ship.getX();
        int y = ship.getY();
        int size = ship.getSize();


        int [][] playerBattlefield = P.getBattlefield();

        if (x < 0 || y < 0 || x + size > playerBattlefield.length || y + size > playerBattlefield.length) {
            return false;
        }

        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                if (playerBattlefield[i][j] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public void showBattleField(){
        System.out.println("Player A field");

        for(int i=0;i<playerA.getBattlefield().length;i++){
            for(int j=0;j<playerA.getBattlefield().length;j++){
                System.out.print(playerA.getBattlefield()[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Player B field");


        for(int i=0;i<playerB.getBattlefield().length;i++){
            for(int j=0;j<playerB.getBattlefield().length;j++){
                System.out.print(playerB.getBattlefield()[i][j] + " ");
            }
            System.out.println();
        }

    }
    public void addShipsToPlayers(){

        Scanner scanner = new Scanner(System.in);

        int fleetSize = 2;

        int shipCount = 1;

        System.out.println("fleet size is: ");
        System.out.println(fleetSize);
        int i=0;
        while (shipCount <= fleetSize) {
            //System.out.println("Enter details for Ship " + shipCount + ":");
            //System.out.print("Enter player A's ship name: ");
            String shipName = "" + i+1;
            //System.out.print("Enter X coordinate: ");
            int x = i;
            //System.out.print("Enter Y coordinate: ");
            int y = i;
            //System.out.print("Enter ship size: ");
            int size = 1;
            //scanner.nextLine();
            i++;
            Ship ship = new Ship(shipName, x, y, size);
            if (isValidShipLocation(ship,playerA)) {
                playerA.addShip(ship);
                shipCount++;
            } else {
                System.out.println("Invalid ship location. Please try again.");
            }
        }

//        showBattleField();
        shipCount = 1;
        i=0;
        while (shipCount <= fleetSize) {

            String shipName = ""+i+1;
            int x = i;
            int y = i;
            int size = 1;
            i++;

            Ship ship = new Ship(shipName, x, y, size);
            if (isValidShipLocation(ship,playerB)) {
                playerB.addShip(ship);
                shipCount++;
            } else {
                System.out.println("Invalid ship location. Please try again.");
            }
        }

        showBattleField();
    }


    public void play(MissileFiringStrategy randomStrategy) {
        addShipsToPlayers();

        System.out.println("Entering while loop....");
        while (optionsA.hasShipRemaining() && optionsB.hasShipRemaining()) {
            System.out.println(playerA.getName() + "'s turn:");
            optionsA.fireMissile(playerB,randomStrategy);

            if (!optionsB.hasShipRemaining()) {
                System.out.println(playerA.getName() + " wins!");
                break;
            }

            System.out.println(playerB.getName() + "'s turn:");
            optionsB.fireMissile(playerA, randomStrategy);

            if (!optionsA.hasShipRemaining()) {
                System.out.println(playerB.getName() + " wins!");
                break;
            }
        }

        System.out.println("Exiting...");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the battlefield size: ");
        int battlefieldSize = scanner.nextInt();

        MissileFiringStrategy randomStrategy = new RandomCoordinateFiringStrategy();


        BattleshipGame game = new BattleshipGame(battlefieldSize);
        game.play(randomStrategy);

    }
}

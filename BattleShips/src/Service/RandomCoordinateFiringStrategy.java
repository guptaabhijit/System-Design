package Service;

import Models.Player;

import java.util.Random;

public class RandomCoordinateFiringStrategy implements MissileFiringStrategy{
    @Override
    public int[] fireMissile(Player ownPlayer, Player opponent) {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(opponent.getBattlefield().length);
            y = random.nextInt(opponent.getBattlefield().length);
        } while (opponent.getMisslesFired().contains(x + "," + y));

        return new int[]{x, y};
    }
}

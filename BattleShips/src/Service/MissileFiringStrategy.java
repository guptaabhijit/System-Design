package Service;

import Models.Player;

public interface MissileFiringStrategy {
    int[] fireMissile(Player ownPlayer, Player opponent);

}

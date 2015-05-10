package ch.hslu.mpbro15.team10.battleship.model;

/**
 * Created by dave on 10.05.2015.
 */
public class GODestroyer extends BattleshipGameObject {

    private int lenght = 3;
    private GODestroyer next;
    private GODestroyer last;

    public GODestroyer(GODestroyer pLast) {
        this.last = pLast;
        if (this.last != null) {
            this.last.next = this;
        }
    }

    @Override
    public boolean isSunk() {
        boolean sunk = true;
        GODestroyer c = this;
        // retireve first part of ship
        while (c.last != null) {
            c = c.last;
        }

        // check each part of the ship if its sunk
        while (c.next != null && sunk) {
            sunk &= c.isHit();
            c = c.next;
        }
        return sunk &= c.isHit(); // check last part of ship;
    }

    @Override
    public int getLength() {
        return lenght;
    }

    @Override
    public void shoot() {
        super.hit();
        super.shot();
        BattleshipGameObject.hitCountDestroyer++;
    }
}
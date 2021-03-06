package ch.hslu.mpbro15.team10.battleship.model;

import android.graphics.drawable.Drawable;
import android.view.View;

import ch.hslu.mpbro15.team10.battleship.R;

/**
 * Created by dave on 10.05.2015.
 */
public class GOCarrier extends BattleshipGameObject {

    private int lenght = 5;
    private GOCarrier next;
    private GOCarrier last;

    public GOCarrier(GOCarrier pLast) {
        this.last = pLast;
        if (this.last != null) {
            this.last.next = this;
        }
    }

    @Override
    public boolean isSunk() {
        boolean sunk = true;
        GOCarrier c = this;
        // retireve first part of ship
        while(c.last != null) {
            c = c.last;
        }

        // check each part of the ship if its sunk
        while(c.next != null && sunk) {
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
        BattleshipGameObject.hitCountCarrier++;
    }

    @Override
    public Drawable getBackground(View view)
    {
        if(super.isShot())
            return view.getResources().getDrawable(R.drawable.miss);
        if(super.isHit())
            return view.getResources().getDrawable(R.drawable.hit);
        return view.getResources().getDrawable(R.drawable.ship);
    }

    private String coordinates;
    @Override
    public void setCoordinates(String x, String y) {
        coordinates = x+y;
    }

    @Override
    public String getCoordinates() {
        return coordinates;
    }
}

package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


/**
 * Created by Dinna on 05/11/2015.
 */
public class Bomb {
    Sprite[] spBomb;
    SpriteBatch spriteBatch;
    int j, nSpeed;
    boolean isExploded;

    Bomb(TextureAtlas taBombExplode, float fX, float fY) {
        spriteBatch = new SpriteBatch();
        isExploded = false;
        spBomb = new Sprite[16];
        j = 0;
        nSpeed = 0;

        //Loop through regions of the TextureAtlas and assign each to an index of the array
        for(int a = 0; a < 16; a++) {
            spBomb[a] = new Sprite(taBombExplode.findRegion("frame_"+ a));
            spBomb[a].setOrigin(spBomb[a].getHeight()/2, spBomb[a].getWidth()/2);
            spBomb[a].setPosition(fX, fY);
            spBomb[a].setSize(50, 50);
        }
    }

    public void render () {
        spriteBatch.begin();
        spBomb[j].draw(spriteBatch);
        nSpeed++;   //nSpeed changes the time interval at which the sprites are drawn
        if (nSpeed%8 == 0) {
            isExploded = isExploded(j);
//            if (!isExploded(j)) {
                j++;
//            }
        }
        spriteBatch.end();
    }

    //Check if index is at the end of the animation
    public boolean isExploded(int j) {
        if (j == 15) {
            return true;
        }
        return false;
    }
}

package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


/**
 * Created by Dinna on 05/11/2015.
 */
public class Bomb {
    Sprite[] spBomb;
//    Sprite spBomb;
    SpriteBatch spriteBatch;
    OrthographicCamera camera;
    int j, nSpeed;

    Bomb(TextureAtlas taBombExplode, /*Texture txBomb,*/ float fX, float fY, OrthographicCamera camera_) {
        camera = camera_;
        spriteBatch = new SpriteBatch();
//        spBomb = new Sprite(txBomb);
//        spBomb.setOrigin(spBomb.getHeight()/2, spBomb.getWidth()/2);
//        spBomb.setPosition(fX, fY);
//        spBomb.setSize(50, 50);
        spBomb = new Sprite[14];
        j = 0;
        nSpeed = 0;
        for(int a = 0; a < 14; a++) {
            spBomb[a] = new Sprite(taBombExplode.findRegion("frame_"+a));
            spBomb[a].setOrigin(spBomb[a].getHeight()/2, spBomb[a].getWidth()/2);
            spBomb[a].setPosition(fX, fY);
            spBomb[a].setSize(50, 50);
        }

    }

    public void render () {
        spriteBatch.begin();
//        spBomb.draw(spriteBatch);
        spBomb[j].draw(spriteBatch);
        //nSpeed changes the time interval at which the sprites are drawn
        nSpeed++;
        if (nSpeed%6 == 0) {
            if (j == 13) { //Make sure index stays within the bounds the array
                j = 0;
            } else {
                j++;
            }
        }
        spriteBatch.end();
    }
}

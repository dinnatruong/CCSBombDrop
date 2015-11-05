package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Dinna on 05/11/2015.
 */
public class Bomb {
    Sprite spBomb;
    SpriteBatch spriteBatch;
    OrthographicCamera camera;

    Bomb(Texture txBomb, float fX, float fY, OrthographicCamera camera_) {
        camera = camera_;
        spriteBatch = new SpriteBatch();
        spBomb = new Sprite(txBomb);
        spBomb.setOrigin(spBomb.getHeight()/2, spBomb.getWidth()/2);
        spBomb.setPosition(fX, fY);
        spBomb.setSize(50, 50);
    }

    public void render () {
        spriteBatch.begin();
        spBomb.draw(spriteBatch);
        spriteBatch.end();
    }
}

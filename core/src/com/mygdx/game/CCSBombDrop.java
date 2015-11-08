package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.ArrayList;

public class CCSBombDrop extends ApplicationAdapter {
	private OrthographicCamera camera;
	Stage stage;
	private SpriteBatch batch;
	private Touchpad touchpad;
	private Touchpad.TouchpadStyle touchpadStyle;
	private Skin skTouchPad, skBombDrop;
	private Drawable drwTouchbg;
	private Drawable drwTouchpad;
	private float fSpeed;
	TextureAtlas taBomberman, taBombDrop, taBombExplode;
	Sprite spFG, spBG, spBomberman;
	ImageButton.ImageButtonStyle ibsBombDrop;
	ImageButton ibBombDrop;
	ArrayList<Bomb> arlBombs;

	//Touchpad: https://github.com/JStruk/Bomberman/blob/master/core/src/com/mygdx/game/Thumbstick.java
	@Override
	public void create() {
		batch = new SpriteBatch();
		float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 10f * aspectRatio, 10f);
		//Create a skin for the touchpad
		skTouchPad = new Skin();
		//Set background and knob imgs
		spFG = new Sprite(new Texture("ThumbstickFGsmall.png"));
		spBG = new Sprite(new Texture("ThumbstickBG.png"));
		skTouchPad.add("drwTouchbg", spBG);
		skTouchPad.add("drwTouchpad", spFG);
		touchpadStyle = new Touchpad.TouchpadStyle();
		//make drawables based off the skin
		drwTouchbg = skTouchPad.getDrawable("drwTouchbg");
		drwTouchpad = skTouchPad.getDrawable("drwTouchpad");
		//Apply the bg and knob drawables to the touchpad
		touchpadStyle.background = drwTouchbg;
		touchpadStyle.knob = drwTouchpad;

		//Initiate the touchpad based on the style we just created
		touchpad = new Touchpad(10, touchpadStyle);
		//set where the touchpad will be on the screen
		touchpad.setBounds(30, 30, 100, 100);
		//create the stage and add the touchpad to it
		stage = new Stage();
		stage.addActor(touchpad);
		Gdx.input.setInputProcessor(stage);

		//create sprite that is going to move around with the touchpad
		taBomberman = new TextureAtlas(Gdx.files.internal("bomberman.txt"));
		spBomberman = new Sprite(taBomberman.findRegion("frame_1"));
		spBomberman.setPosition(Gdx.graphics.getWidth() / 2 - spBomberman.getWidth() / 2, Gdx.graphics.getHeight() / 2 + spBomberman.getHeight() / 2);
		//set the default speed to multiply by when the touchpad is moved around to move the rect
		fSpeed = 5;

		//Create an image button to "drop a bomb" when pressed
		//https://github.com/captainkesty/imagebutton.git
		//https://github.com/MatthewBrock/TheDeepDarkTaurock.git
		taBombDrop = new TextureAtlas("BombBtn.atlas");
		skBombDrop = new Skin();
		skBombDrop.addRegions(taBombDrop);
		ibsBombDrop = new ImageButton.ImageButtonStyle();
		ibsBombDrop.up = skBombDrop.getDrawable("bombbtnON");
		ibsBombDrop.down = skBombDrop.getDrawable("bombbtnOFF");
		ibsBombDrop.checked = skBombDrop.getDrawable("bombbtnON");
		ibBombDrop = new ImageButton(ibsBombDrop);
		ibBombDrop.setSize(120, 120);
		ibBombDrop.setPosition(Gdx.graphics.getWidth() - (ibBombDrop.getWidth() + 30), 30);
		ibBombDrop.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Bomb dropped");
				makeBomb(); // Create a bomb
			}
		});
		stage.addActor(ibBombDrop);

		//Load file for bomb animation and create an array list for bombs
		taBombExplode = new TextureAtlas(Gdx.files.internal("BombExploding/BombExploding.atlas"));
		arlBombs = new ArrayList<Bomb>();
	}

	//Adding sprites using a button: https://github.com/MatthewBrock/TheDeepDarkTaurock/tree/FireBallScratch/core/src/taurockdeepdark
	public void makeBomb() {
		arlBombs.add(new Bomb(taBombExplode, spBomberman.getX(), spBomberman.getY()));
	}

	public ArrayList<Bomb> getBombs() {
		return arlBombs;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		//Move bomberman based on the knob percent
		spBomberman.setX(spBomberman.getX() + touchpad.getKnobPercentX() * fSpeed);
		spBomberman.setY(spBomberman.getY() + touchpad.getKnobPercentY() * fSpeed);

		//Render each bomb in the array
		for (int i = 0; i < arlBombs.size(); i++) {
			arlBombs.get(i).render();
			if (arlBombs.get(i).isExploded) {	//Remove bomb once animation ends
				arlBombs.remove(i);
				System.out.println("Bomb removed");
			}
		}

		batch.begin();
		spBomberman.draw(batch);
		batch.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
}

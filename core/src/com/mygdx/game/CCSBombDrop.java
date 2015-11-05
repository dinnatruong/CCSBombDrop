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
	TextureAtlas taBomberman, taBombDrop;
	Texture txFG, txBomb;
	Sprite sprFG, spBomberman;
	ImageButton.ImageButtonStyle ibsBombDrop;
	ImageButton ibBombDrop;
	int nHeight, nWidth;
	ArrayList<Bomb> arlBombs;


	@Override
	public void create() {
		batch = new SpriteBatch();
		float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 10f * aspectRatio, 10f);
		//Create a skin for the touchpad
		skTouchPad = new Skin();
		//Set background and knob imgs
		txFG = new Texture("ThumbstickFG.png");
		sprFG = new Sprite(txFG);
		sprFG.setBounds(15, 15, 100, 100);
		skTouchPad.add("drwTouchbg", new Texture("ThumbstickBG.png"));
		skTouchPad.add("drwTouchpad", sprFG);
		touchpadStyle = new Touchpad.TouchpadStyle();
		//make drawables based off the skin
		drwTouchbg = skTouchPad.getDrawable("drwTouchbg");
		drwTouchpad = skTouchPad.getDrawable("drwTouchpad");
		//Apply the bg and knob drawables to the touchpad
		touchpadStyle.background = drwTouchbg;
		touchpadStyle.knob = drwTouchpad;
		touchpadStyle.knob.setTopHeight(10);
		touchpadStyle.knob.setBottomHeight(10);
		touchpadStyle.knob.setLeftWidth(10);
		touchpadStyle.knob.setRightWidth(10);
		touchpad = new Touchpad(10, touchpadStyle);
		//Initiate the touchpad based on the style we just created
		touchpad.setBounds(15, 15, 200, 200);
		//set where the touchpad will be on the screen
		stage = new Stage();
		//create the stage and add the touchpad to it

		stage.addActor(touchpad);
		Gdx.input.setInputProcessor(stage);

		//create sprite that is going to move around with the touchpad
		taBomberman = new TextureAtlas(Gdx.files.internal("bomberman.txt"));
		spBomberman = new Sprite(taBomberman.findRegion("frame_1"));
		spBomberman.setPosition(Gdx.graphics.getWidth() / 2 - spBomberman.getWidth() / 2, Gdx.graphics.getHeight() / 2 + spBomberman.getHeight() / 2);
		fSpeed = 5;
		//set the default speed to multiply by when the touchpad is moved around to move the rect

		nHeight = Gdx.graphics.getHeight();
		nWidth = Gdx.graphics.getWidth();

		//Load bomb image and create an array list of bombs
		txBomb = new Texture(Gdx.files.internal("Bomb.png"));
		arlBombs = new ArrayList<Bomb>();

		//Create an image button: https://github.com/captainkesty/imagebutton.git
		//https://github.com/MatthewBrock/TheDeepDarkTaurock.git
		taBombDrop = new TextureAtlas("BombBtn.atlas");
		skBombDrop = new Skin();
		skBombDrop.addRegions(taBombDrop);
		ibsBombDrop = new ImageButton.ImageButtonStyle();
		ibsBombDrop.up = skBombDrop.getDrawable("bombbtnON");
		ibsBombDrop.down = skBombDrop.getDrawable("bombbtnOFF");
		ibsBombDrop.checked = skBombDrop.getDrawable("bombbtnON");
		ibBombDrop = new ImageButton(ibsBombDrop);
		ibBombDrop.setSize(150, 150);
		ibBombDrop.setPosition(nWidth / 4 + ibBombDrop.getWidth() * 2, 30);
		ibBombDrop.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Bomb dropped");
				makeBomb(); // Create a bomb when button is pressed
			}
		});
		stage.addActor(ibBombDrop);
	}

	//Creating sprites using a button: https://github.com/MatthewBrock/TheDeepDarkTaurock/tree/FireBallScratch/core/src/taurockdeepdark
	public void makeBomb() {
		arlBombs.add(new Bomb(txBomb, spBomberman.getX(), spBomberman.getY(), camera));
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

		//Render the bombs
		for (Bomb arlBomb : arlBombs) {
			arlBomb.render();
			}

		batch.begin();
		spBomberman.draw(batch);
		batch.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
}

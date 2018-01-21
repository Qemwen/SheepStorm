/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sheepgame;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {

    final SheepStorm game;
    Texture hexagon;
    Texture dropImage;
    Texture bucketImage;
    Sound dropSound;
    //Sound bleating;
    Sound stampede;
    Music meadowMusic;
    OrthographicCamera camera;
    Polygon tile;
    Array<Polygon> tiles;
    long lastDropTime;
    int dropsGathered;

    public GameScreen(final SheepStorm game) {
        this.game = game;

        // load the images for the droplet and the bucket, 64x64 pixels each
        dropImage = new Texture(Gdx.files.internal("Fishie2.JPG"));
        bucketImage = new Texture(Gdx.files.internal("butterflyklein.JPG"));
        hexagon = new Texture(Gdx.files.internal("hexagon.png"));
        // load the drop sound effect and the rain background "music"
        dropSound = Gdx.audio.newSound(Gdx.files.internal("clank.mp3"));
        stampede = Gdx.audio.newSound(Gdx.files.internal("stampede.wav"));
        //bleating = Gdx.audio.newSound(Gdx.files.internal("bleating.wav"));
        meadowMusic = Gdx.audio.newMusic(Gdx.files.internal("meadow.wav"));
        meadowMusic.setLooping(true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 800);

        // create a Rectangle to logically represent the bucket
        tile = new Polygon();
        tile.setPosition(100, 100);// plaats de tegel
        tile.setScale(200, 200); // groott van de tegel

        // create the raindrops array and spawn the first raindrop
        tiles = new Array<Polygon>();
		spawnTile();

    }

	private void spawnTile() {
		Polygon tile = new Polygon();
		tile.setPosition(MathUtils.random(0, 800 - 64), 480);
		tile.setScale(64, 64);
		tiles.add(tile);
		lastDropTime = TimeUtils.nanoTime();
	}    
    
    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        game.font.draw(game.batch, "Hexagons Collected: " + dropsGathered, 0, 480);
        game.batch.draw(hexagon, 100, 100, 200, 200);
        game.batch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            tile.setPosition(touchPos.x - 64 / 2, tile.getY());
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            tile.setPosition((tile.getX() - 200) * Gdx.graphics.getDeltaTime(), tile.getY());
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            tile.setPosition((tile.getX() + 200) * Gdx.graphics.getDeltaTime(), tile.getY());
        }

        // make sure the bucket stays within the screen bounds
        float x = (float) 0;
        if (tile.getX() < 0) {
            tile.setPosition(0, tile.getY());
        }
        if (tile.getX() > 800 - 64) {
            tile.setPosition(800 - 64, tile.getY());
        }
// check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            tile.setPosition(tile.getX(), tile.getY() + 50);
        }

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the 
        // value our drops counter and add a sound effect.
        Iterator<Polygon> iter = tiles.iterator();
        while (iter.hasNext()) {
            Polygon tile = iter.next();
            tile.setPosition(tile.getX(),(tile.getY() - 200) * Gdx.graphics.getDeltaTime());
            if (tile.getY() + 64 < 0) {
                iter.remove();
            }
            if (tile.getY() < 0) {
                dropsGathered++;
                //long play = dropSound.play();
                iter.remove();
            }

        }
    }

        @Override
        public void resize
        (int width, int height
        
        
        ) {
    }

    @Override
        public void show
        
            () {
        // start the playback of the background music
        // when the screen is shown
        meadowMusic.play();
        }

        @Override
        public void hide
        
        
        () {
    }

    @Override
        public void pause
        
        
        () {
    }

    @Override
        public void resume
        
        
        () {
    }

    @Override
        public void dispose
        
            () {
        dropImage.dispose();
            bucketImage.dispose();
            dropSound.dispose();
            meadowMusic.dispose();
            stampede.dispose();
        }

    }

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sheepgame;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
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
    Sound stampede;
    Music meadowMusic;
    OrthographicCamera camera;
    public static Hexmap hexmap;
    int screenHeight;
    int screenWidth;
    int tileLength = 56;

    public GameScreen(final SheepStorm game) {
        this.game = game;
        stampede = Gdx.audio.newSound(Gdx.files.internal("stampede.wav"));
        //bleating = Gdx.audio.newSound(Gdx.files.internal("bleating.wav"));
        meadowMusic = Gdx.audio.newMusic(Gdx.files.internal("meadow.wav"));
        meadowMusic.setLooping(true);
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        screenHeight = 800;
        screenWidth = 1100;
        camera.setToOrtho(false, screenWidth, screenHeight);

    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0.2f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);
        SheepStorm ssGame = (SheepStorm) this.game;
        ssGame.gamelogic.hexmap.renderer.setView(camera);
        ssGame.gamelogic.hexmap.renderer.render();
        SideMenu menu = new SideMenu();
//        menu.create();
//        menu.render();
//        menu.resize(100,100);
        if (Gdx.input.justTouched()) {
            if (ssGame.gamelogic.hexmap.drawPile.size > 0) {
                System.out.println("Ik zet een tile op " + (Gdx.input.getX() / 92) + " en y = " + (Gdx.input.getY() / 97));
                int[] thatTile = whichTile(Gdx.input.getX(), Gdx.input.getY());

                if (ssGame.gamelogic.hexmap.checkPlek(thatTile[0], thatTile[1])) {
                    ssGame.gamelogic.hexmap.placeTile(thatTile[0], thatTile[1]);
                    ssGame.gamelogic.hexmap.checkPlekken();
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        meadowMusic.play();
    }

    @Override
    public void hide() {
        meadowMusic.pause();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        meadowMusic.dispose();
        stampede.dispose();
    }

    //zorgt ervoor dat de goede tegel bedoeld wordt
    public int[] whichTile(int x, int y) {
        int[] thatTile = {0, 0};
        int breedte = x;
        int breedtePlek = x / (int) (1.5 * tileLength);
        double hoogte = screenHeight - y;
        double tileHeight = (tileLength * 1.7321);
        boolean evenColumn = false;
        //is het een even kolom?
        if (breedtePlek % 2 == 0) {
            evenColumn = true;
        }
        //relatieve hoogte
        double heightInTile = hoogte % tileHeight;
        //in een even kolom is de relatieve hoogte anders
        if (evenColumn) {
            heightInTile += 0.5 * tileHeight;
            heightInTile = heightInTile % tileHeight;
        }
        //bereken de onderste helft van de hoogte om de afwijking te kunnen bepalen
        if (heightInTile > .5 * tileHeight) {
            heightInTile = tileHeight - heightInTile;
        }
        double widthInTile = breedte % (1.5 * tileLength);
        //in het linkerstukje bedoel je soms net een andere
        if (widthInTile < 0.5 * tileLength) {
            breedte -= 0.5 * tileLength - ((heightInTile / tileHeight) * tileLength);
            breedtePlek = (breedte / (int) (1.5 * tileLength));
        }
        //een even kolom staat wat hoger, corrigeer dit
        if (breedtePlek % 2 == 0) {
            hoogte -= (tileHeight / 2.0);
        }
        double hoogtePlek = hoogte / tileHeight;
        thatTile[0] = breedtePlek;
        thatTile[1] = (int) hoogtePlek;

        return thatTile;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sheepgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen implements Screen {

    final SheepStorm game;
    OrthographicCamera camera;
    public Stage stage;

    public MainMenuScreen(final SheepStorm gam) {
        game = gam;
        stage = new Stage();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 800);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
   
        camera.update();
        Label welcome = new Label("Welcome to SheepStorm!", Constants.SKIN, "font", Color.LIME);
        welcome.setPosition((float)(Constants.GAMEWIDTH * .1), (float)(Constants.GAMEHEIGHT * .55));
        welcome.setFontScale(1.5f);

        Label intro = new Label("Due to recent climate changes new mushrooms are growing in the meadows surrounding your dragon farm. "
                               + "These mushrooms make the sheep go crazy and when they are, they come rampaging in a great flock, trampling everything in their way. "
                               + "Including your baby dragons. Bring them home safely before they get trampled!", Constants.SKIN, "font", Color.WHITE);
        intro.setPosition((float)(Constants.GAMEWIDTH * .1), (float)(Constants.GAMEHEIGHT * .2));
        intro.setSize(500, 200);
        intro.setWrap(true);
     
        Label start = new Label("Tap anywhere to start", Constants.SKIN,  "font", Color.WHITE);
        start.setPosition((float)(Constants.GAMEWIDTH * .1), (float)(Constants.GAMEHEIGHT * .15));
        
        Image madSheep = new Image(new Texture(Gdx.files.internal("madSheep.jpg")));
        madSheep.setBounds((float)(Constants.GAMEWIDTH *.7), (float)(Constants.GAMEHEIGHT *.25), 200, 200);
        Image angrySheep = new Image(new Texture(Gdx.files.internal("angrySheep.jpg")));        
        angrySheep.setBounds((float)(Constants.GAMEWIDTH *.7), (float)(Constants.GAMEHEIGHT *.55), 200, 200);        

        stage.addActor(welcome);
        stage.addActor(intro);
        stage.addActor(start);
        stage.addActor(madSheep);
        stage.addActor(angrySheep);
        stage.draw();
        
        if (Gdx.input.isTouched()) {
            game.gamelogic.init(game);
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}

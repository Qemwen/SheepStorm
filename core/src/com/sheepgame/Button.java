/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// https://github.com/wmora/martianrun/tree/master/core/src/com/gamestudio24/martianrun
package com.sheepgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class Button extends Game {

    Stage stage;
    TextButton buttonPlace;
    TextButton buttonClick;
    TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;
    Actor menu;
    int screenWidth;
    int screenHeight;

    @Override
    public void create() {
        stage = new Stage();
        menu = new Widget();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        skin = new Skin();
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.CHARTREUSE;
        buttonPlace = new TextButton("Place tile", Constants.SKIN);
        buttonClick = new TextButton("Click Here", Constants.SKIN);


        screenWidth = Constants.GAMEWIDTH;
        screenHeight = Constants.GAMEHEIGHT;
        buttonPlace.setPosition((int) (screenWidth * .8), (int) (screenHeight * .85));
        buttonClick.setPosition((int) (screenWidth * .8), (int) (screenHeight * .75));
        buttonClick.setBounds(200, 200, 200, 200);
//        buttonPlace.setHeight(14f);
//        buttonPlace.setWidth(40f);
//        buttonPlace.setHeight(14f);
//        buttonClick.setWidth(40f);
        buttonPlace.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Ik ben geklikt");
            }
        });
        stage.addActor(buttonPlace);
        stage.addActor(buttonClick);
        

        
       // addBackgroundGuide(4);
    }
//    public void addBackgroundGuide(int columns){
//		Texture texture = new Texture(Gdx.files.internal("butterflyklein.JPG"));
//		texture.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
//
//		TextureRegion textureRegion = new TextureRegion(texture);
//		textureRegion.setRegion(0,0,texture.getWidth()*columns,texture.getWidth()*columns);
//		Image background = new Image(textureRegion);
//		background.setSize(80, 40);
//		background.setPosition(200, 200);
//		stage.addActor(background);
//	}
        @Override
        public void render() {      
        super.render();
        stage.act();
        stage.draw();
    }


    

}

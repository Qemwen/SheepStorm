/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sheepgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class SideMenu {

    static Stage stage;
    public static TextButton button;
    Table table;
    static ImageButton nextTileButton;
     static ImageButton firstOpenTile;
      static ImageButton secondOpenTile;
       static ImageButton thirdOpenTile;
    static String sheepWeather;
    static Label sheepState;
    static int drawPileSize = 60;
    static StaticTiledMapTile openFirst = (StaticTiledMapTile) Hexmap.drawPile.pop();
    static StaticTiledMapTile openSecond = (StaticTiledMapTile) Hexmap.drawPile.pop();
    static StaticTiledMapTile openThird = (StaticTiledMapTile) Hexmap.drawPile.pop();
    static Drawable hexagon = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("img/hexagon.png"))));

    public SideMenu() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Create a table that fills the screen. Everything else will go inside this table.
        table = new Table();
        table.setFillParent(true);
        table.align(Align.right);
        table.right().top();

        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        button = new TextButton("Place Tile!", Constants.SKIN, "default");
        sheepWeather = GameLogic.getSheepState();
        System.out.println(sheepWeather);
        sheepState = new Label(sheepWeather, Constants.SKIN, "font-label", Color.WHITE);
        sheepState.setPosition((float) (Constants.GAMEWIDTH * .1), (float) (Constants.GAMEHEIGHT * .15));

        table.add(button).width(100);
        table.row();
        StaticTiledMapTile peek = (StaticTiledMapTile) Hexmap.drawPile.peek();
        nextTileButton = new ImageButton(new TextureRegionDrawable(peek.getTextureRegion()));
        table.add(nextTileButton).size(112f, 97f);
        table.row();

        table.add(sheepState);
        table.row();
   
        firstOpenTile = new ImageButton(new TextureRegionDrawable(openFirst.getTextureRegion()));
        table.add(firstOpenTile);
        table.row();
        secondOpenTile = new ImageButton(new TextureRegionDrawable(openSecond.getTextureRegion()));
        table.add(secondOpenTile);
        table.row();
        thirdOpenTile = new ImageButton(new TextureRegionDrawable(openThird.getTextureRegion()));
        table.add(thirdOpenTile);
        stage.addActor(table);
    }

    public static void render() {
        updateNext();
        stage.act();
        updateNext();
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
    }

    public static void updateNext() {
        if (Hexmap.drawPile.size > 0 && drawPileSize != Hexmap.drawPile.size) {
            StaticTiledMapTile first = (StaticTiledMapTile) Hexmap.drawPile.peek();
            //nextTileButton. (new TextureRegionDrawable();
            nextTileButton.getStyle().imageUp=new TextureRegionDrawable(first.getTextureRegion());
            drawPileSize = Hexmap.drawPile.size;
        } else if (Hexmap.drawPile.size == 0){
            nextTileButton.getStyle().imageUp= hexagon;
        }
    }
}

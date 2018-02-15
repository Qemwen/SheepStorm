/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sheepgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class SideMenu {

    static Stage stage;
    TextButton button;
    Table table;
    static Image nextTile;
    
    public SideMenu() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Create a table that fills the screen. Everything else will go inside this table.
        table = new Table();
        table.setFillParent(true);
        table.align(Align.right);

        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        button = new TextButton("Place Tile!", Constants.SKIN, "small");

        button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked!");
                button.setText("Good job!");
            }
        });
        table.add(button).width(100);
        // Add an image actor. Have to set the size, else it would be the size of the drawable (which is the 1x1 texture).
        table.add(new Image(new Texture(Gdx.files.internal("hexes.png")))).size(94);
        updateNext();
        table.add(nextTile).size(200);
        stage.addActor(table);
    }

    public static void render() {
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
        nextTile = new Image(new Texture(Gdx.files.internal("hexagon.png")));
        if (Hexmap.drawPile.size > 0) {
            StaticTiledMapTile first = (StaticTiledMapTile) Hexmap.drawPile.peek();
            nextTile = new Image(first.getTextureRegion());
        } 
    }
}

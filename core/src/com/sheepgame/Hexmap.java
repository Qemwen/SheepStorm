/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sheepgame;

import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Carlijn
 */
public class Hexmap {

    TiledMap map = new TiledMap();
    Texture hexture;
    Texture hexagon;
    TiledMapTile[] availableTiles;
    public HexagonalTiledMapRenderer renderer;
    static Array drawPile;
    static TiledMapTileLayer layer;
    int hoogte = 10;
    int breedte = 12;
    StaticTiledMapTile lightblue = new StaticTiledMapTile(new TextureRegion(new Texture(Gdx.files.internal("lightblue.png"))));

    public Hexmap() {
        hexture = new Texture(Gdx.files.internal("hexes.png"));
        TextureRegion[][] hexes = TextureRegion.split(hexture, 112, 97);
        //Verklein het plaatje naar de tegel
        Pixmap groteHexagon = new Pixmap(Gdx.files.internal("hexagon.png"));
        Pixmap kleineHexagon = new Pixmap(112, 97, groteHexagon.getFormat());
        kleineHexagon.drawPixmap(groteHexagon,
                0, 0, groteHexagon.getWidth(), groteHexagon.getHeight(),
                0, 0, kleineHexagon.getWidth(), kleineHexagon.getHeight()
        );
        Texture hexagon = new Texture(kleineHexagon);
        groteHexagon.dispose();
        kleineHexagon.dispose();

        map = new TiledMap();
        MapLayers layers = map.getLayers();
        availableTiles = new TiledMapTile[60];

        for (int i = 0; i < availableTiles.length; i++) {
            //Later komen hier de 60 verschillende plaatjes
            if (i < 20) {
                availableTiles[i] = new StaticTiledMapTile(
                        new TextureRegion(hexes[0][0]));
            } else if (i < 40) {
                availableTiles[i] = new StaticTiledMapTile(
                        new TextureRegion(hexes[0][1]));
            } else {
                availableTiles[i] = new StaticTiledMapTile(
                        new TextureRegion(hexes[1][0]));
            }
        }
        //Maak er een geshufflede lijst van
        drawPile = new Array(availableTiles);
        drawPile.shuffle();

        //Bouw het veld
        layer = new TiledMapTileLayer(breedte, hoogte, 112, 97);

        //Maak alle cells en tiles
        for (int l = 0;
                l < 1; l++) {
            for (int x = 0; x < breedte; x++) {
                for (int y = 0; y < hoogte; y++) {
                    Cell plek = new Plek(x, y);
                    layer.setCell(x, y, plek);
                }
            }

        }
        layers.add(layer);

        //Leg de starttegel neer
        StaticTiledMapTile startTile = new StaticTiledMapTile(new TextureRegion(hexagon));
        Cell celly = new Plek(4, 4);
        celly.setTile(startTile);
        layer.setCell(4, 4, celly);
        checkPlekken();
        renderer = new HexagonalTiledMapRenderer(map);
    }

    public void placeTile(int x, int y) {
        //System.out.println("Ik ga checken" + x + y);
        checkPlek(x, y);
        //System.out.println("Ik heb gecheckt " + x + y);
        if (x < breedte && y < hoogte) {
            Cell cell1 = new Plek(x, y);
            cell1.setTile((StaticTiledMapTile) drawPile.pop());
            layer.setCell(x, y, cell1);
            SideMenu.updateNext();
            SideMenu.render();
        } 
    }

    public void checkPlekken() {
        for (int i = 0; i < breedte; i++) {
            for (int j = 0; j < hoogte; j++) {
                //System.out.println("Ik ga alles checken en ben nu bij: "+ i + j);
                if (checkPlek(i, j)) {
                    Plek checkedPlek = (Plek) layer.getCell(i, j);
                    checkedPlek.setTile(lightblue);
                    checkedPlek.setStatus("available");
                }
            }
        }
    }

    public boolean checkPlek(int x, int y) {
        Plek checkedPlek = (Plek) layer.getCell(x, y);
        if (checkedPlek.getStatus() == "available" || checkedPlek.getStatus() == "empty") {
            return checkedPlek.hasNeighbours(layer);

        } else {
            return false;
        }
    }
}

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
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    public HexagonalTiledMapRenderer rendererHex;
    public OrthogonalTiledMapRenderer rendererBG;
    static Array drawPile;
    public static TiledMapTileLayer layer;
    public static TiledMapTileLayer effectLayer;
    int hoogte = Constants.rows;
    int breedte = Constants.columns;
    StaticTiledMapTile lightblue = new StaticTiledMapTile(new TextureRegion(new Texture(Gdx.files.internal("img/lightblue.png"))));
    StaticTiledMapTile red = new StaticTiledMapTile(new TextureRegion(new Texture(Gdx.files.internal("img/red.png"))));
    Texture background;
    public static Vector3 center;
    StaticTiledMapTile newOpenTile;

    public Hexmap() {
        hexture = new Texture(Gdx.files.internal("img/hexes.png"));
        TextureRegion[][] hexes = TextureRegion.split(hexture, 112, 97);
        //Verklein het plaatje naar de tegel
        Pixmap groteHexagon = new Pixmap(Gdx.files.internal("img/hexagon.png"));
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

        //Make a background
        background = new Texture(Gdx.files.internal("img/grass.jpg"));
        TextureRegion textureRegion = new TextureRegion(background);
        textureRegion.setRegion(0, 0, Constants.GAMEWIDTH, Constants.GAMEHEIGHT);
        TiledMapTileLayer tileLayer = new TiledMapTileLayer(breedte, hoogte, 112, 97);
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        TextureRegion background = new TextureRegion(new Texture(Gdx.files.internal("img/grass.jpg")), (int) (Constants.GAMEWIDTH * .8), (int) (Constants.GAMEHEIGHT));
        cell.setTile(new StaticTiledMapTile(background));
        tileLayer.setCell(0, 0, cell);
        layers.add(tileLayer);

        //Make tiles
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
        effectLayer = new TiledMapTileLayer(breedte, hoogte, 112, 97);
        for (int l = 0;
                l < 1; l++) {
            for (int x = 0; x < breedte; x++) {
                for (int y = 0; y < hoogte; y++) {
                    Cell myCell = new Cell();
                    effectLayer.setCell(x, y, myCell);
                }
            }
        }
        layers.add(effectLayer);
        //Leg de starttegel neer
        StaticTiledMapTile startTile = new StaticTiledMapTile(new TextureRegion(hexagon));
        Cell celly = new Plek(5, 3);
        celly.setTile(startTile);
        layer.setCell(5, 3, celly);
        checkPlekken();
        rendererHex = new HexagonalTiledMapRenderer(map);
        rendererBG = new OrthogonalTiledMapRenderer(map);
        center = new Vector3(layer.getWidth() * layer.getTileWidth() / 2, layer.getHeight() * layer.getTileHeight() / 2, 0);

    }

    public void placeTile(int x, int y) {
        checkPlek(x, y);
        if (x < breedte && y < hoogte) {
            Cell cell1 = new Plek(x, y);
            cell1.setTile((StaticTiledMapTile) drawPile.pop());
            layer.setCell(x, y, cell1);
            Plek plek1 = (Plek) cell1;
        }
    }

    public void placeOpenTile(int x, int y, StaticTiledMapTile openTile, int tileLocation) {
         placeTile(x,y);
//        checkPlek(x, y);
//        if (x < breedte && y < hoogte) {
//            Cell cell1 = new Plek(x, y);
//            cell1.setTile(openTile);
//            layer.setCell(x, y, cell1);
//            Plek plek1 = (Plek) cell1;
            if (drawPile.size > 0) {
                switch (tileLocation) {
                    case 1:
                        SideMenu.openFirst = (StaticTiledMapTile) Hexmap.drawPile.pop();
                        SideMenu.firstOpenTile.getStyle().imageUp = new TextureRegionDrawable(SideMenu.openFirst.getTextureRegion());
                        break;
                    case 2:
                        SideMenu.openSecond = (StaticTiledMapTile) Hexmap.drawPile.pop();
                        SideMenu.secondOpenTile.getStyle().imageUp = new TextureRegionDrawable(SideMenu.openSecond.getTextureRegion());
                        break;
                    case 3:
                        SideMenu.openThird = (StaticTiledMapTile) Hexmap.drawPile.pop();
                        SideMenu.thirdOpenTile.getStyle().imageUp = new TextureRegionDrawable(SideMenu.openThird.getTextureRegion());
                        break;
                }
            } else {
                switch (tileLocation) {
                    case 1:
                        SideMenu.firstOpenTile.setVisible(false);
                        break;
                    case 2:
                        SideMenu.secondOpenTile.setVisible(false);
                        break;
                    case 3:
                        SideMenu.thirdOpenTile.setVisible(false);
                        break;
                }
            }
            //pop om de lege plek van tilelocation te vullen.

        
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

    public void checkThreatened(int direction) {
        for (int i = 0; i < breedte; i++) {
            for (int j = 0; j < hoogte; j++) {
                Plek threatPlek = (Plek) layer.getCell(i, j);
                if (threatPlek.getStatus() == "tiled") {
                    Plek neighbourPlek = (Plek) layer.getCell(threatPlek.neighbours()[direction - 1][0], threatPlek.neighbours()[direction - 1][1]);
                    if (neighbourPlek == null || neighbourPlek.getStatus() != "tiled") {
                        effectLayer.getCell(i, j).setTile(red);

                    }
                }
            }
        }
    }
}

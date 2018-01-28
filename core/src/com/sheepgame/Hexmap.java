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
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
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
    Array drawPile;
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
                    System.out.println("ik maak een cel op " + x + y);
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

        //Toon de bovenste tegel
        placeTile(3, 4);
        placeTile(2, 4);
        placeTile(3, 3);
        placeTile(7, 0);
        placeTile(4, 6);
        checkPlekken();

//        Cell cell2 = new Cell();
//        cell2.setTile((StaticTiledMapTile)drawPile.get(2));
//        layer.setCell(1, 0, cell2);        
//
//        Cell cell3 = new Cell();
//        cell3.setTile((StaticTiledMapTile)drawPile.get(3));
//        layer.setCell(2, 0, cell3);        
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
        } else {
            //System.out.println("Ik kan geen tegel plaatsen");
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
        if (drawPile.size > 0) {
            Cell cell1 = new Plek(0, 0);
            cell1.setTile((StaticTiledMapTile) drawPile.peek());
            layer.setCell(0, 0, cell1);
        } else {
            Plek pilePlek = (Plek) layer.getCell(0, 0);
            pilePlek.setStatus("empty");
            layer.setCell(0, 0, null);
            
        }
    }

    public boolean checkPlek(int x, int y) {
        Plek checkedPlek = (Plek) layer.getCell(x, y);
        if (checkedPlek.getStatus() == "available" || checkedPlek.getStatus() == "empty") {
            //System.out.println("ik geloof dat ik buren heb is "+ tileAim.hasNeighbours(layer));
            return checkedPlek.hasNeighbours(layer);

        } else {
            //System.out.println("Ik was niet null" + x + y);
            return false;
        }

    }
}

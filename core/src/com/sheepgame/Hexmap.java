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

    public Hexmap() {
        hexture = new Texture(Gdx.files.internal("hexes.png"));
        TextureRegion[][] hexes = TextureRegion.split(hexture, 112, 97);

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
        for (int i = 0; i < 20; i++) {
            //Later komen hier de 60 verschillende plaatjes
            availableTiles[0] = new StaticTiledMapTile(
                    new TextureRegion(hexagon));
            availableTiles[1] = new StaticTiledMapTile(
                    new TextureRegion(hexes[0][1]));
            availableTiles[2] = new StaticTiledMapTile(
                    new TextureRegion(hexes[1][0]));
        }

        TiledMapTileLayer layer = new TiledMapTileLayer(45, 30, 112, 97);
        //Maak één specifieke tegel aan
        Cell celly = new Cell();
        celly.setTile(availableTiles[0]);
        layer.setCell(4, 4, celly);

        Cell cell = new Cell();
        cell.setTile(availableTiles[1]);
        layer.setCell(0, 0, cell);

//Vul alle cells met random tiles
//            for (int y = 0; y < 30; y++) {
//                for (int x = 0; x < 45; x++) {
//                    if (y == 4 && x == 4) {
//                        continue;
//                    }
//                    Cell cell = new Cell();
//                    cell.setTile(availableTiles[1]);
//                    layer.setCell(x, y, cell);
//                }
//            }
//            
        layers.add(layer);

        /*        MapObjects alltiles = new MapObjects();
        float[] vertices = {1.0f, 500.1f, 300.0f, 0.5f, 7.12f, 60.1f};
        PolygonMapObject sometile = new PolygonMapObject(vertices);
        //   sometile.setColor(GREEN);
        //   alltiles.add(sometile); */
        renderer = new HexagonalTiledMapRenderer(map);
    }
}

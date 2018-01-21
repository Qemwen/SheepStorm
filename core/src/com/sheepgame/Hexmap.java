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

/**
 *
 * @author Carlijn
 */
public class Hexmap {

    TiledMap map = new TiledMap();
    Texture hexture;
    public HexagonalTiledMapRenderer renderer;
    public Hexmap() {
        hexture = new Texture(Gdx.files.internal("hexes.png"));
        TextureRegion[][] hexes = TextureRegion.split(hexture, 112, 97);
        map = new TiledMap();
        MapLayers layers = map.getLayers();
        TiledMapTile[] tiles = new TiledMapTile[3];
        tiles[0] = new StaticTiledMapTile(
                new TextureRegion(hexes[0][0]));
        tiles[1] = new StaticTiledMapTile(
                new TextureRegion(hexes[0][1]));
        tiles[2] = new StaticTiledMapTile(
                new TextureRegion(hexes[1][0]));

        for (int l = 0;
                l < 1; l++) {
            TiledMapTileLayer layer = new TiledMapTileLayer(45, 30, 112, 97);
            for (int y = 0; y < 30; y++) {
                for (int x = 0; x < 45; x++) {
                    int id = (int) (Math.random() * 3);
                    Cell cell = new Cell();
                    cell.setTile(tiles[id]);
                    layer.setCell(x, y, cell);
                }
            }
            layers.add(layer);
        }
/*        MapObjects alltiles = new MapObjects();
        float[] vertices = {1.0f, 500.1f, 300.0f, 0.5f, 7.12f, 60.1f};
        PolygonMapObject sometile = new PolygonMapObject(vertices);
        //   sometile.setColor(GREEN);
        //   alltiles.add(sometile); */
        renderer = new HexagonalTiledMapRenderer(map);
    }
}

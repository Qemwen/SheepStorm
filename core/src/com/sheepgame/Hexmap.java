/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sheepgame;

import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;




/**
 *
 * @author Carlijn
 */
public class Hexmap {
    //    TiledMap map = new TiledMap();
        MapLayer tilelayer = new MapLayer();
        MapObjects alltiles = new MapObjects();
        float[] vertices = {1.0f, 500.1f, 300.0f, 0.5f, 7.12f, 60.1f};
        PolygonMapObject sometile = new PolygonMapObject(vertices);
     //   sometile.setColor(GREEN);
     //   alltiles.add(sometile);
    //HexagonalTiledMapRenderer tilemap = new HexagonalTiledMapRenderer(map);


}

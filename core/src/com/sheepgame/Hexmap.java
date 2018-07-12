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
import com.badlogic.gdx.utils.ArrayMap;
import java.util.ArrayList;
import java.util.Arrays;

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
    static ArrayMap drawPile;
    public static TiledMapTileLayer layer;
    public static TiledMapTileLayer effectLayer;
    public static TiledMapTileLayer wallsLayer;
    
    int hoogte = Constants.rows;
    int breedte = Constants.columns;
    StaticTiledMapTile lightblue = new StaticTiledMapTile(new TextureRegion(new Texture(Gdx.files.internal("img/lightblue.png"))));
    StaticTiledMapTile red = new StaticTiledMapTile(new TextureRegion(new Texture(Gdx.files.internal("img/red.png"))));
    StaticTiledMapTile wall1 = new StaticTiledMapTile(new TextureRegion(new Texture(Gdx.files.internal("img/wall1.png"))));
    
    public StaticTiledMapTile green = new StaticTiledMapTile(new TextureRegion(new Texture(Gdx.files.internal("img/green.png"))));
    Texture background;
    public static Vector3 center;
    StaticTiledMapTile newOpenTile;
    TextureRegion[][] hexes;

    public Hexmap() {
        hexture = new Texture(Gdx.files.internal("img/hexes.png"));
        hexes = TextureRegion.split(hexture, 112, 97);
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
        drawPile = new ArrayMap();

        for (int i = 0; i < 60; i++) {
            //Later komen hier de 60 verschillende plaatjes

            if (i < 20) {
                drawPile.put(i, new StaticTiledMapTile(new TextureRegion(hexes[0][0])));
            } else if (i < 40) {
                drawPile.put(i, new StaticTiledMapTile(new TextureRegion(hexes[0][1])));
            } else {
                drawPile.put(i, new StaticTiledMapTile(new TextureRegion(hexes[1][0])));
            }
        }
        //Maak er een geshufflede lijst van
        
        drawPile.shuffle();
        System.out.println("Drawpile = " + drawPile.get(0));
        
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
        wallsLayer = new TiledMapTileLayer(breedte*3, hoogte*2, Constants.TILELENGTH/2, (int)(Math.sqrt(3) * Constants.TILELENGTH)/2);
        for (int l = 0;
                l < 1; l++) {
            for (int x = 0; x < breedte; x++) {
                for (int y = 0; y < hoogte; y++) {
                    Cell myCell = new Cell();
                    wallsLayer.setCell(x, y, myCell);
                }
            }
        }
        layers.add(wallsLayer);        
        
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
            cell1.setTile((StaticTiledMapTile) drawPile.getValueAt(0));
            layer.setCell(x, y, cell1);
            Plek plek1 = (Plek) layer.getCell(x,y);
            plek1.setDefenses(defenses(cell1.getTile().getTextureRegion()));
            System.out.println("Defenses van de geplaatste tegel: " + plek1.defenses);
            drawPile.removeIndex(0);
        }
    }
    
    public void addWall(int x, int y, int direction){
        Plek plek = (Plek) layer.getCell(x,y);
        plek.addWall(direction);
        int wallX = (x - 1) * 3;
        int wallY = (y - 1) * 2;
        switch(direction){
            case 1: wallX += 2; wallY +=2; wallsLayer.getCell(wallX, wallY).setTile(wall1); break;
            case 2: wallX += 4; wallY +=2; wallsLayer.getCell(wallX, wallY).setTile(wall1); break;          
            case 3: wallX += 4; wallY +=1; wallsLayer.getCell(wallX, wallY).setTile(wall1); break;
            case 4: wallX += 2; wallY +=1; wallsLayer.getCell(wallX, wallY).setTile(wall1); break;
            case 5: wallX += 1; wallY +=1; wallsLayer.getCell(wallX, wallY).setTile(wall1); break;
            case 6: wallX += 1; wallY +=2; wallsLayer.getCell(wallX, wallY).setTile(wall1); break;
        }
        System.out.println("wall positie: " + wallX + " " + wallY);
    }

    public void placeOpenTile(int x, int y, StaticTiledMapTile openTile, int tileLocation) {
            Cell cell1 = new Plek(x, y);
            cell1.setTile(openTile);
            layer.setCell(x, y, cell1);
            Plek plek1 = (Plek) layer.getCell(x,y);
            if (drawPile.size > 0) {
                switch (tileLocation) {
                    case 1:
                        SideMenu.openFirst = (StaticTiledMapTile) drawPile.getValueAt(0); drawPile.removeIndex(0);
                        SideMenu.firstOpenTile.getStyle().imageUp = new TextureRegionDrawable(SideMenu.openFirst.getTextureRegion());
                        break;
                    case 2:
                        SideMenu.openSecond = (StaticTiledMapTile) Hexmap.drawPile.getValueAt(0); drawPile.removeIndex(0);
                        SideMenu.secondOpenTile.getStyle().imageUp = new TextureRegionDrawable(SideMenu.openSecond.getTextureRegion());
                        break;
                    case 3:
                        SideMenu.openThird = (StaticTiledMapTile) Hexmap.drawPile.getValueAt(0); drawPile.removeIndex(0);
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
     private ArrayList<Integer> defenses(TextureRegion tegel){
        ArrayList<Integer> defenses = new ArrayList();
        if (tegel.getTexture().equals(hexes[0][0])) {defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0));}
        if (tegel.getTexture().equals(hexes[0][1])) {defenses.addAll(Arrays.asList(1, 1, 1, 1, 1, 1));}       
        if (tegel.getTexture().equals(hexes[1][0])) {defenses.addAll(Arrays.asList(2, 2, 2, 2, 2, 2));} 
//         switch(tegel){
//             case 0: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 0: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 1: defenses.addAll(Arrays.asList(1, 1, 1, 1, 1, 1)); break;
//           case 2: defenses.addAll(Arrays.asList(2, 2, 2, 2, 2, 2)); break;
//           case 3: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 4: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 5: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 6: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 7: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 8: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 9: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 10: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 11: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 12: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 13: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 14: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 15: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 16: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 17: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 18: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 19: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 20: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 21: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 22: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 23: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 24: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 25: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 26: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 27: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 28: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 29: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 30: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 31: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 32: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 33: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 34: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 35: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 36: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 37: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 38: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 39: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 40: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 41: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 42: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 43: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 44: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 45: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 46: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 47: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 48: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 49: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 50: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 51: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 52: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 53: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 54: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 55: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 56: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 57: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 58: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 59: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
//           case 60: defenses.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); break;
            System.out.println("Defenses = " + defenses);
        return defenses;
    }
}

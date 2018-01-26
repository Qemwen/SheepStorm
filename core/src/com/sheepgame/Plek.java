package com.sheepgame;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

/**
 *
 * @author Carlijn
 */
public class Plek extends Cell {

    int boven;
    int links;
    int rechts = links - 2 * links;
    int onder = boven - 2 * boven;

    public Plek(int naarLinks, int naarBoven) {
        links = naarLinks;
        boven = naarBoven;

    }

    public int[][] Neighbours() {

        //getY
        int[][] neighbours = {
            {links, boven + 1},
            {links + 1, boven},
            {links + 1, boven - 1},
            {links, boven - 1},
            {links - 1, boven},
            {links - 1, boven + 1}
        };
        return neighbours;
    }

    public void hasNeighbors() {
        for (int i = 0; i < 6; i++) {
            
            //if tile at neighbors[i][j] exists, geef ja
            // maar dan niet in deze for-loop geloof ik
        }

    }

    public static void main(String[] arguments) {
        Plek somePlace = new Plek(0, 0);
        System.out.println("I made it!");
    }
}

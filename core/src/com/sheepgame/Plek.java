package com.sheepgame;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import java.util.ArrayList;

/**
 *
 * @author Carlijn
 */
public class Plek extends TiledMapTileLayer.Cell {

    private String status = "empty"; //Possible: empty, available, tiled, trampled
    int boven;
    int links;
    ArrayList<Integer> defenses;

    public Plek(int naarLinks, int naarBoven) {
        links = naarLinks;
        boven = naarBoven;
    }

    public int[][] neighbours() {
        int[][] neighbours;
        if (links % 2 == 0) {
            int[][] evenNeighbours = {
                {links, boven + 1},
                {links + 1, boven +1},
                {links + 1, boven},
                {links, boven - 1},
                {links - 1, boven},
                {links - 1, boven +1}
            };
            neighbours = evenNeighbours;

        } else {
            int[][] oddNeighbours = {
                {links, boven + 1},
                {links + 1, boven},
                {links + 1, boven - 1},
                {links, boven - 1},
                {links - 1, boven - 1},
                {links - 1, boven}
            };
            neighbours = oddNeighbours;
        }
        //  System.out.println("ik heb mijn buren opgesteld");
        return neighbours;
    }

    public boolean hasNeighbours(TiledMapTileLayer layer) {
        boolean hasThem = false;
        for (int[] possNeighbour : neighbours()) {
            if (possNeighbour[0] >= 0 && possNeighbour[1] >= 0 && possNeighbour[0] < 12 && possNeighbour[1] < 10) {
                Plek neighbourCell = (Plek) layer.getCell(possNeighbour[0], possNeighbour[1]);
                if (neighbourCell.getStatus().equals("tiled") || neighbourCell.getStatus().equals("trampled")) {
                    //System.out.println("Ik heb een buur gevonden op " + possNeighbour[0] + possNeighbour[1]);
                    hasThem = true;
                    status = "available";
                }
            }
        }
        return hasThem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String newStatus) {
        status = newStatus;
    }

    public TiledMapTileLayer.Cell setTile(TiledMapTile tile) {
        super.setTile(tile);
        status = "tiled";
        return this;
    }
    
   public void setDefenses(ArrayList<Integer> defenses){
       this.defenses = defenses;
   }
}

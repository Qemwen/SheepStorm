/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sheepgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import static com.sheepgame.GameScreen.GAME;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlijn
 */
public class GameLogic implements Screen {

    // 1. Het spel initieert. Dingen worden klaargelegd
    //Methode initieer?
    public static Hexmap hexmap = new Hexmap();
    public static SheepStorm game;
    boolean gewonnen = false;
    boolean verloren = false;
    static int sheepPhase = 1;
    static String sheepState;
    static int phase;
    static GameScreen gameScreen;
    static int openTileNumber;
    static StaticTiledMapTile openTile;

    public void init(SheepStorm game) {
        this.game = game;
        gameScreen = new GameScreen(game);
        game.setScreen(gameScreen);
        gameScreen.render(.5f);
        //TODO: Maak spelers (nu nog even 1)
        phase = 1;
    }

    public static void update() {

//  2. Player 0 legt 3 tegels aan. 
//  3. Doorloop een beurt:
        switch (phase) {
//     1. Volgende fase schapen
            case 1:
                if (sheepPhase < 6) {
                    sheepPhase++;
                } else {
                    sheepPhase = 1;
                }
                SideMenu.sheepState.setText(GameLogic.getSheepState());
                phase = 2;
                break;
//     2. Open de bovenste tegel.
            case 2:
                openTile();
                break;
//     3. Plaats die tegel.
            case 3:
                clickNextTile();
                break;
            case 31:
                placeTile();
                break;
//        3a. Voer mogelijk effect uit.
            case 32:
                break;
//     4. Kies 1 van 3: 
            case 4:

                clickOpenTile();
                break;
//                      1. Verplaats een draak.

//                      2. Leg een open tegel aan.
            case 41:
                placeOpenTile();
                break;
//                      3. Krijg drie nieuwe open tegels.
//        4a. Kijk of er gewonnen is. 
            case 44:
                break;
//     5. Plaats muren.
            case 5:
                break;
//     6. Check of er een storm komt. Zo ja, storm!
            case 6:
                if ("stormy" == sheepState) {
                    sheepStorm();
                } else {phase = 1;}
                break;
//     7. Kijk of er verloren is. (break)
            case 7:
                break;
//     8. Ga naar de volgende beurt. 
        }
//  4. Geef een samenvatting. 

    }

    public void dispose() {

    }

    public static String getSheepState() {
        switch (sheepPhase) {

            case 1:
                sheepState = "sunny";
                break;
            case 2:
                sheepState = "cloudy";
                break;
            case 3:
                sheepState = "breezy";
                break;
            case 4:
                sheepState = "windy";
                break;
            case 5:
                sheepState = "rainy";
                break;
            case 6:
                sheepState = "stormy";
                break;
            default:
                System.out.println("My, but it's misty today");
        }
        return sheepState;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
//        if (Gdx.input.justTouched()) {
//            sheepPhase++;
//            System.out.println("Sheep state = " + getSheepState());
//
//        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    private static void placeTile() {
        if (Gdx.input.justTouched() && Gdx.input.getX() < (Constants.GAMEWIDTH * .75)) {
            if (GAME.gamelogic.hexmap.drawPile.size > 0) {
                int[] thatTile = gameScreen.whichTile(Gdx.input.getX(), Gdx.input.getY());

                if (GAME.gamelogic.hexmap.checkPlek(thatTile[0], thatTile[1])) {
                    GAME.gamelogic.hexmap.placeTile(thatTile[0], thatTile[1]);
                    GAME.gamelogic.hexmap.checkPlekken();
                }
            }
            SideMenu.nextTileButton.setVisible(false);
            phase = 4;
        }
        if (GAME.gamelogic.hexmap.drawPile.size == 0){phase = 4;}
    }

    private static void placeOpenTile() {
        if (Gdx.input.justTouched() && Gdx.input.getX() < (Constants.GAMEWIDTH * .75)) {
            int[] thatTile = gameScreen.whichTile(Gdx.input.getX(), Gdx.input.getY());
            if (GAME.gamelogic.hexmap.checkPlek(thatTile[0], thatTile[1])) {
                GAME.gamelogic.hexmap.placeOpenTile(thatTile[0], thatTile[1], openTile, openTileNumber);
                GAME.gamelogic.hexmap.checkPlekken();
            }
            phase = 6;
        }

    }

    private static void clickNextTile() {
        SideMenu.nextTileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (phase == 3) {
                    phase = 31;

                }
            }
        });
    }

    private static void clickOpenTile() {
        SideMenu.firstOpenTile.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (phase == 4) {
                    System.out.println("Geklikt op de eerste");
                    openTileNumber = 1;
                    openTile = SideMenu.openFirst;
                    phase = 41;
                }
            }
        });
        SideMenu.secondOpenTile.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (phase == 4) {
                    phase = 41;
                    openTileNumber = 2;
                    openTile = SideMenu.openSecond;
                }
            }
        });
        SideMenu.thirdOpenTile.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (phase == 4) {
                    phase = 41;
                    openTileNumber = 3;
                    openTile = SideMenu.openThird;
                }
            }
        });
    }

    private static void openTile() {
        SideMenu.drawPile.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (phase == 2) {
                    SideMenu.nextTileButton.setVisible(true);
                    phase = 3;
                }
            }
        });
    }

    private static void sheepStorm() {
        if(SheepComeStorming.hereTheyCome()){
            phase = 1;
        }
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sheepgame;

/**
 *
 * @author Carlijn
 */
public class GameLogic {

    // 1. Het spel initieert. Dingen worden klaargelegd
    //Methode initieer?
    Hexmap hexmap;

    public void init() {
        // Maak een hexgrid
        hexmap = new Hexmap();
        //Maak spelers (nu nog even 1)
        //Maak schaapstatussen
        //Toon alles dat gemaakt is in gamescreen
        //Klaar
    }

    /*
        2. Player 0 legt 3 tegels aan. 
        3. Doorloop een beurt:  1. Volgende fase schapen
                                2. Open de bovenste tegel.
                                3. Plaats die tegel.
                                3a. Voer mogelijk effect uit.
                                4. Kies 1 van 3:    1. Verplaats een draak.
                                                    2. Leg een open tegel aan.
                                                    3. Krijg drie nieuwe open tegels.
                                4a. Kijk of er gewonnen is. (break)
                                5. Plaats muren.
                                6. Check of er een storm komt.
                                6a. Zo ja, storm. 
                                7. Kijk of er verloren is. (break)
                                8. Ga naar de volgende beurt.
        4. Geef een samenvatting. 
     */

    public void dispose() {

    }

}

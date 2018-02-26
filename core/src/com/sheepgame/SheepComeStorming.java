/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sheepgame;

import static com.sheepgame.GameScreen.GAME;

/**
 *
 * @author CBroers
 */
public class SheepComeStorming {

    public static int direction;
    public static int strength;

    public static boolean hereTheyCome() {

        System.out.println("here they come!");
        direction = throwDie();
        strength = throwDie();
        SideMenu.directionLabel.setVisible(true);
        SideMenu.direction.setText("" + direction);
        SideMenu.direction.setVisible(true);
        SideMenu.strengthLabel.setVisible(true);
        SideMenu.strength.setText("" + strength);
        SideMenu.strength.setVisible(true);
        GAME.gamelogic.hexmap.checkThreatened(direction);
        return true;
    }

    private static int throwDie() {
        int result = (int) (Math.random() * 6) + 1;
        System.out.println("Ik heb gerold: " + result);
        return result;
    }
}

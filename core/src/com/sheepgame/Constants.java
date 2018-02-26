/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sheepgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Constants {
    public final static int GAMEWIDTH = 1100;
    public final static int GAMEHEIGHT = 700;
    public static int rows = 15;
    public static int columns = 18;
    public static Skin SKIN = new Skin(Gdx.files.internal("shade/skin/uiskin.json"));  
}

package com.sheepgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sheepgame.SheepStorm;
import com.sheepgame.Constants;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "SheepStorm";
        config.width = Constants.GAMEWIDTH;
        config.height = Constants.GAMEHEIGHT;
        new LwjglApplication(new SheepStorm(), config);
    }
}

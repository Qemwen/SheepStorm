package com.sheepgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sheepgame.SheepStorm;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "SheepStorm";
        config.width = 1100;
        config.height = 800;
        new LwjglApplication(new SheepStorm(), config);
    }
}

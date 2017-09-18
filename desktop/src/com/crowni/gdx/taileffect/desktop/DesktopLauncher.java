package com.crowni.gdx.taileffect.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.crowni.gdx.taileffect.Test.Begin;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Actor with tail Example";
        config.width = 1080 / 3;
        config.height = 1920 / 3;
        new LwjglApplication(new Begin(), config);
    }
}

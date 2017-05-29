package com.mustard.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mustard.game.Mustard;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 576;
		config.width = 1024;
		config.foregroundFPS = 20;
		config.resizable = false;
		new LwjglApplication(new Mustard(), config);
	}
}


package com.mustard.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mustard.game.Mustard;
import com.mustard.server.object.User;

public class LobbyPageScreen implements Screen {
	
	

	Mustard game;
	User user;
	Texture ArenaActive;
	Texture ArmoryActive;
	Texture ExitActive;
	Texture ArenaInactive;
	Texture ArmoryInactive;
	Texture ExitInactive;
	Texture lobbyPageBackground;
	
	public LobbyPageScreen(Mustard game) {
		this.game = game;
		ArenaActive = new Texture("LobbyPage/Arena_ac.png");
		ArenaInactive = new Texture("LobbyPage/Arena_in.png");
		ArmoryActive = new Texture("LobbyPage/Armory_ac.png");
		ArmoryInactive = new Texture("LobbyPage/Armory_in.png");
		ExitActive = new Texture("LobbyPage/Exit_ac.png");
		ExitInactive = new Texture("LobbyPage/Exit_in.png");
		lobbyPageBackground = new Texture("LobbyPage/LobbyPage_background.png");
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(lobbyPageBackground, 0, 0);
		
		int ArenaX = 280;
		int ArmoryX = 25;
		int ExitX = 725;
		int ArenaY = 42;
		int ArmoryY = 60;
		int ExitY = 55;
		
		if(Gdx.input.getX() < 250 && Gdx.input.getX() > 80 && 576 - Gdx.input.getY() < 520  && 576 - Gdx.input.getY() > 430)
		{
			game.batch.draw(ArmoryActive, ArmoryX, ArmoryY);
			if(Gdx.input.isTouched())
			{
				game.playButton();
				this.dispose();
				game.setScreen(new ArmoryPageScreen(game));
			}
		}
		else
		{
			game.batch.draw(ArmoryInactive, ArmoryX, ArmoryY);
		}
		
		/* Enter the arena */
		if(Gdx.input.getX() < 580 && Gdx.input.getX() > 410 && 576 - Gdx.input.getY() < 520  && 576 - Gdx.input.getY() > 430)
		{
			game.batch.draw(ArenaActive, ArenaX, ArenaY);
			if(Gdx.input.isTouched())
			{
				game.playButton();
				if (game.cl.joinGame()){
					this.dispose();
					game.setScreen(new WaitingScreen (game));
				}
			}
		}
		else
		{
			game.batch.draw(ArenaInactive, ArenaX, ArenaY);
		}
		
		if(Gdx.input.getX() < 930 && Gdx.input.getX() > 760 && 576 - Gdx.input.getY() < 520  && 576 - Gdx.input.getY() > 430)
		{
			game.batch.draw(ExitActive, ExitX, ExitY);
			if(Gdx.input.isTouched())
			{
				game.playButton();
				this.dispose();
				game.setScreen(new StartPageScreen(game));
			}
		}
		else
		{
			game.batch.draw(ExitInactive, ExitX, ExitY);
		}
		game.batch.end();

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

	@Override
	public void dispose() {
		ArenaActive.dispose();
		ArenaInactive.dispose();
		ArmoryActive.dispose();
		ArmoryInactive.dispose();
		ExitActive.dispose();
		ExitInactive.dispose();
		lobbyPageBackground.dispose();
	}

}

package com.mustard.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mustard.game.Mustard;
import com.mustard.server.object.User;

public class VictoryScreen implements Screen {
	
	

	Mustard game;
	User user;
	Texture enlistBActive;
	Texture lobbyBActive;
	Texture lobbyBInactive;
	Texture enlistBInactive;
	Texture lostPageBackground;
	
	public VictoryScreen(Mustard game) {
		this.game = game;
		lobbyBActive = new Texture("ResolutionPage/Lobby_ac.png");
		lobbyBInactive = new Texture("ResolutionPage/Lobby_in.png");
		enlistBActive = new Texture("StartPage/Enlist_ac.png");
		enlistBInactive = new Texture("StartPage/Enlist_in.png");
		lostPageBackground = new Texture("ResolutionPage/VicScreen.png");
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(lostPageBackground, 0, 0);
		
		int enlistX = 590;
		int Y = 150;
		
		if(Gdx.input.getX() < enlistX + lobbyBInactive.getWidth() && Gdx.input.getX() > enlistX && 576 - Gdx.input.getY() < Y + lobbyBInactive.getHeight() && 576 - Gdx.input.getY() > Y)
		{
			if(game.cl.getUser().isGuest()) {
				game.batch.draw(enlistBActive, enlistX, Y);
			} else {
				game.batch.draw(lobbyBActive, enlistX, Y);
			}
			if(Gdx.input.isTouched())
			{
				game.playButton();
				game.musicManager.stopVictoryMusic();
				game.musicManager.playMenuMusic();
				if(game.cl.getUser().isGuest()) {
					game.cl.deleteCharacter();
					game.cl.resetUser();
					this.dispose();
					game.setScreen(new StartPageScreen(game));
				}
				else {
					game.cl.giveResources(game.cl.getUser().getUsername());
					game.cl.deleteSession(game.cl.getUser().getUsername());
					this.dispose();
					game.setScreen(new LobbyPageScreen(game));
				}
			}
		}
		else
		{
			if(game.cl.getUser().isGuest()) {
				game.batch.draw(enlistBInactive, enlistX, Y);
			} else {
				game.batch.draw(lobbyBInactive, enlistX , Y);
			}
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
		enlistBActive.dispose();;
		lobbyBActive.dispose();;
		lobbyBInactive.dispose();;
		enlistBInactive.dispose();;
		lostPageBackground.dispose();;
	}

}

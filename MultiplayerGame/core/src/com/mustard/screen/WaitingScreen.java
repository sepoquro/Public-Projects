package com.mustard.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
// import com.mustard.game.Map;
import com.mustard.game.Mustard;
import com.mustard.server.object.User;

public class WaitingScreen implements Screen
{
	Mustard game;
	User user;
	Texture waitingPageBackground;
	
	WaitingScreen(Mustard game)
	{
		this.game = game;
		waitingPageBackground = new Texture("WaitScreen.png");
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(waitingPageBackground, 0, 0);
		if (game.cl.canStartGame()) {
			System.out.println ("TIME TO DUEL");
			game.playTransition();
			game.stopMenuMusic();
			game.playBackground();
			game.cl.getAllUsers();
			this.dispose();
			game.setScreen(new BattleScreen(game));
		}
		game.batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		waitingPageBackground.dispose();
	}

}

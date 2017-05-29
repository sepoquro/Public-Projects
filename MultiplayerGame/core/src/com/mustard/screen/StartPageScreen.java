package com.mustard.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mustard.game.Mustard;
import com.mustard.server.object.Constants;
import com.mustard.server.object.User;

public class StartPageScreen implements Screen {
	
	

	Mustard game;
	User user;
	Texture enlistBActive;
	Texture reportBActive;
	Texture visitBActive;
	Texture enlistBInactive;
	Texture reportBInactive;
	Texture visitBInactive;
	Texture startPageBackground;
	
	public StartPageScreen(Mustard game) {
		this.game = game;
		enlistBActive = new Texture("StartPage/Enlist_ac.png");
		enlistBInactive = new Texture("StartPage/Enlist_in.png");
		reportBActive = new Texture("StartPage/Reportback_ac.png");
		reportBInactive = new Texture("StartPage/ReportBack_in.png");
		visitBActive = new Texture("StartPage/Visit_ac.png");
		visitBInactive = new Texture("StartPage/Visit_in.png");
		startPageBackground = new Texture("StartPage/StartPage_Background.png");
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(startPageBackground, 0, 0);
		
		int enlistX = 130;
		int reportX = 440;
		int visitX = 740;
		int Y = 150;
		if(Gdx.input.getX() < enlistX + enlistBInactive.getWidth() && Gdx.input.getX() > enlistX && 576 - Gdx.input.getY() < Y + enlistBInactive.getHeight() && 576 - Gdx.input.getY() > Y)
		{
			game.batch.draw(enlistBActive, enlistX, Y);
			if(Gdx.input.isTouched())
			{
				game.playButton();
				this.dispose();
				game.setScreen(new NewUserPageScreen(game));
			}
		}
		else
		{
			game.batch.draw(enlistBInactive, enlistX , Y);
		}
		if(Gdx.input.getX() < reportX + reportBInactive.getWidth() && Gdx.input.getX() > reportX && 576 - Gdx.input.getY() < Y + reportBInactive.getHeight() && 576 - Gdx.input.getY() > Y)
		{
			game.batch.draw(reportBActive, reportX, Y);
			if(Gdx.input.isTouched())
			{
				game.playButton();
				this.dispose();
				game.setScreen(new LoginPageScreen(game));
			}
		}
		else
		{
			game.batch.draw(reportBInactive, reportX, Y);
		}
		if(Gdx.input.getX() < visitX + visitBInactive.getWidth() && Gdx.input.getX() > visitX && 576 - Gdx.input.getY() < Y + enlistBInactive.getHeight() && 576 - Gdx.input.getY() > Y)
		{
			game.batch.draw(visitBActive, visitX, Y);
			/* Creates a guest user and allows him to instantly join a game.
			 * However, this guest account is temporary, and will be deleted once the client exits the game. */
			if(Gdx.input.isTouched())
			{
				game.playButton();
				game.cl.createGuestUser();
				game.cl.createCharacter(Constants.SOLDIER_ID);
				
				if (game.cl.joinGame()) {
					this.dispose();
					game.setScreen(new WaitingScreen (game));
				}
			}
		}
		else
		{
			game.batch.draw(visitBInactive, visitX, Y);
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
		enlistBActive.dispose();
		enlistBInactive.dispose();
		reportBActive.dispose();
		reportBInactive.dispose();
		visitBActive.dispose();
		visitBInactive.dispose();
		startPageBackground.dispose();
	}

}

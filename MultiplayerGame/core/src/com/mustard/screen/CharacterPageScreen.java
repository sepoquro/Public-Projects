package com.mustard.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mustard.game.Mustard;
import com.mustard.server.object.Constants;
import com.mustard.server.object.User;

public class CharacterPageScreen implements Screen {
	
	

	Mustard game;
	User user;
	Texture cakeActive;
	Texture enforcerActive;
	Texture kerriganActive;
	Texture sheepherdActive;
	Texture cakeInactive;
	Texture enforcerInactive;
	Texture kerriganInactive;
	Texture sheepherdInactive;
	Texture characterPageBackground;
	Sprite tank;
	Sprite soldier;
	Sprite marksman;
	Sprite support;
	
	Texture arrowKeyLeft;
	Texture arrowKeyRight;
	
	
	public CharacterPageScreen(Mustard game) {
		this.game = game;
		cakeActive = new Texture("CharacterPage/Cake_ac.png");
		cakeInactive = new Texture("CharacterPage/Cake_in.png");
		enforcerInactive = new Texture("CharacterPage/Enforcer_in.png");
		enforcerActive = new Texture("CharacterPage/Enforcer_ac.png");
		sheepherdActive = new Texture("CharacterPage/Sheepherd_ac.png");
		sheepherdInactive = new Texture("CharacterPage/Sheepherd_in.png");
		kerriganActive = new Texture("CharacterPage/Kerrigan_ac.png");
		kerriganInactive = new Texture("CharacterPage/Kerrigan_in.png");
		characterPageBackground = new Texture("CharacterPage/CharacterPage_background.png");
		
		soldier = new Sprite (new Texture("CharacterSprite/shooter_black.png"));
		marksman = new Sprite (new Texture("CharacterSprite/sniper_mask.png"));
		support = new Sprite (new Texture("CharacterSprite/support_white.png"));
		tank = new Sprite (new Texture("CharacterSprite/Tank_black.png"));
		
		soldier.setScale(0.65f);
		marksman.setScale(0.7f);
		support.setScale(0.55f);
		tank.setScale(0.7f);
		
		tank.setPosition(32*7, 32*7 - 8);
		soldier.setPosition(32*20+16, 32*7);
		marksman.setPosition(32*7+16, 32*1);
		support.setPosition(32*20+16, 32*2-8);		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(characterPageBackground, 0, 0);
		tank.draw(game.batch);
		soldier.draw(game.batch);
		marksman.draw(game.batch);
		support.draw(game.batch);
		int leftX = 130;
		int rightX = 555;
		int upperY = 285;
		int lowerY = 105;
		
		/* Enforcer button */
		if(Gdx.input.getX() < leftX + enforcerInactive.getWidth() && Gdx.input.getX() > leftX && 576 - Gdx.input.getY() < upperY + enforcerInactive.getHeight() && 576 - Gdx.input.getY() > upperY)
		{
			game.batch.draw(enforcerActive, leftX, upperY);
			if(Gdx.input.isTouched())
			{
				game.cl.createCharacter(Constants.TANK_ID);
				game.playButton();
				this.dispose();
				game.setScreen(new LobbyPageScreen(game));
			}
		}
		else
		{
			game.batch.draw(enforcerInactive, leftX , upperY);
		}
		
		/* Kerrigan Button */
		if(Gdx.input.getX() < leftX + kerriganInactive.getWidth() && Gdx.input.getX() > leftX && 576 - Gdx.input.getY() < lowerY + kerriganInactive.getHeight() && 576 - Gdx.input.getY() > lowerY)
		{
			game.batch.draw(kerriganActive, leftX, lowerY);
			if(Gdx.input.isTouched())
			{
				game.cl.createCharacter(Constants.MARKSMAN_ID);
				game.playButton();
				this.dispose();
				game.setScreen(new LobbyPageScreen(game));
			}
		}
		else
		{
			game.batch.draw(kerriganInactive, leftX , lowerY);
		}
		
		
		/* Cake button */
		if(Gdx.input.getX() < rightX + cakeInactive.getWidth() && Gdx.input.getX() > rightX && 576 - Gdx.input.getY() < lowerY + 3 + cakeInactive.getHeight() && 576 - Gdx.input.getY() > lowerY + 3)
		{
			game.batch.draw(cakeActive, rightX, lowerY + 3);
			if(Gdx.input.isTouched())
			{
				game.cl.createCharacter(Constants.SUPPORT_ID);
				game.playButton();
				this.dispose();
				game.setScreen(new LobbyPageScreen(game));
			}
		}
		else
		{
			game.batch.draw(cakeInactive, rightX , lowerY + 3);
		}
		
		/* Shepherd button (Soldier) */
		if(Gdx.input.getX() < rightX + sheepherdInactive.getWidth() && Gdx.input.getX() > rightX && 576 - Gdx.input.getY() < upperY - 4 + sheepherdInactive.getHeight() && 576 - Gdx.input.getY() > upperY - 7)
		{
			game.batch.draw(sheepherdActive, rightX, upperY - 4);
			if(Gdx.input.isTouched())
			{
				game.cl.createCharacter(Constants.SOLDIER_ID);
				game.playButton();
				this.dispose();
				game.setScreen(new LobbyPageScreen(game));
			}
		}
		else
		{
			game.batch.draw(sheepherdInactive, rightX , upperY - 4);
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
		cakeActive.dispose();
		cakeInactive.dispose();
		enforcerInactive.dispose();
		enforcerActive.dispose();
		sheepherdActive.dispose();
		sheepherdInactive.dispose();
		kerriganActive.dispose();
		kerriganInactive.dispose();
		characterPageBackground.dispose();
		soldier.getTexture().dispose();
		marksman.getTexture().dispose();
		support.getTexture().dispose();
		tank.getTexture().dispose();
		
	}

}

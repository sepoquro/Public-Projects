package com.mustard.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mustard.game.Mustard;
import com.mustard.server.Commands;
import com.mustard.server.object.User;

public class ArmoryPageScreen implements Screen
{
	Mustard game;
	User user;
	Texture armoryPageBackground;
	Texture backButtonActive;
	Texture backButtonInactive;
	Sprite character;
	BitmapFont font;
	
	ArmoryPageScreen(Mustard game)
	{
		this.game = game;
		backButtonActive = new Texture("LoginPage/Exit_Button_ac.png");
		backButtonInactive = new Texture("LoginPage/Exit_Button_in.png");
		character = new Sprite (new Texture(this.game.cl.getUser().getCharacter().getImage()));
		character.setScale(.85f);
		character.setPosition(135, 140);
		armoryPageBackground = new Texture("ArmoryScreen.png");
		font = new BitmapFont();
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
		game.batch.draw(armoryPageBackground, 0, 0);
		character.draw(game.batch);
		user = game.cl.getUser();
		String health = "";
		health += user.getCharacter().getMaxHealth();
		String level = "";
		level += user.getCharacter().getLevel();
		String exp =  "";
		exp += user.getCharacter().getEXP();
		String armor = "";
		armor += user.getCharacter().getArmorValue();
		String attackRange = "";
		attackRange += user.getCharacter().getAttackRange();
		String movingRange = "";
		movingRange += user.getCharacter().getMovementRange();
		String attackStre = "";
		attackStre += user.getCharacter().getAttackStrength();
		String dodge = "";
		dodge += user.getCharacter().getDodge();
		String currancy = "";
		currancy += user.getCurrency();
		String stats = "";
		stats += user.getCharacter().getActionPoints();
		font.draw(game.batch, health, 270, 145);
		font.draw(game.batch, level, 275, 110);
		font.draw(game.batch, exp, 275, 80);
		
		/* Movement range button */
		if(Gdx.input.getX() < 800 && Gdx.input.getX() > 780 && 576 - Gdx.input.getY() > 230 && 576 - Gdx.input.getY() < 250)
		{
			font.setColor(Color.RED);
			font.draw(game.batch, movingRange, 790, 240);
			font.setColor(Color.WHITE);
			if(Gdx.input.isTouched())
			{
				game.cl.upgradeAbilities(Commands.ABILITY_MOVEMENT_RANGE);
			}
		}
		else
		{
			font.draw(game.batch, movingRange, 790, 240);
		}
		
		/* Attack range button */
		if(Gdx.input.getX() < 800 && Gdx.input.getX() > 780 && 576 - Gdx.input.getY() > 338 && 576 - Gdx.input.getY() < 358)
		{
			font.setColor(Color.RED);
			font.draw(game.batch, attackRange, 790, 348);
			font.setColor(Color.WHITE);
			if(Gdx.input.isTouched())
			{
				game.cl.upgradeAbilities(Commands.ABILITY_ATTACK_RANGE);
			}
		}
		else
		{
			font.draw(game.batch, attackRange, 790, 348);
		}
		
		/* Armor button */
		if(Gdx.input.getX() < 565 && Gdx.input.getX() > 545 && 576 - Gdx.input.getY() > 230 && 576 - Gdx.input.getY() < 250)
		{
			font.setColor(Color.RED);
			font.draw(game.batch, armor, 555, 240);
			font.setColor(Color.WHITE);
			if(Gdx.input.isTouched())
			{
				game.cl.upgradeEquipment(Commands.EQUIPMENT_ARMOR);
			}
		}
		else
		{
			font.draw(game.batch, armor, 555, 240);
		}
		
		/* Dodge */
		/* if(Gdx.input.getX() < 800 && Gdx.input.getX() > 780 && 576 - Gdx.input.getY() > 115 && 576 - Gdx.input.getY() < 135)
		{
			font.setColor(Color.RED);
			font.draw(game.batch, dodge, 790, 125);
			font.setColor(Color.WHITE);
			if(Gdx.input.isTouched())
			{
				game.cl.upgradeAbilities(Commands.ABILITY_DODGE);
			}
		}
		else
		{
			font.draw(game.batch, dodge, 790, 125);
		}
		*/
		
		/* Dodge */
		if(Gdx.input.getX() < 800 && Gdx.input.getX() > 780 && 576 - Gdx.input.getY() > 115 && 576 - Gdx.input.getY() < 135)
		{
			font.setColor(Color.RED);
			font.draw(game.batch, dodge, 790, 125);
			font.setColor(Color.WHITE);
			if(Gdx.input.isTouched())
			{
				game.cl.upgradeAbilities(Commands.ABILITY_DODGE);
			}
		}
		else
		{
			font.draw(game.batch, dodge, 790, 125);
		}
		
		
		if(Gdx.input.getX() < 565 && Gdx.input.getX() > 545 && 576 - Gdx.input.getY() > 338 && 576 - Gdx.input.getY() < 358)
		{
			font.setColor(Color.RED);
			font.draw(game.batch, attackStre, 555, 348);
			font.setColor(Color.WHITE);
			if(Gdx.input.isTouched())
			{
				game.cl.upgradeEquipment(Commands.EQUIPMENT_WEAPON);
			}
		}
		else
		{
			font.draw(game.batch, attackStre, 555, 348);
		}
		
		
		font.draw(game.batch, currancy, 535, 543);
		font.draw(game.batch, stats, 770, 543);
		int backX = 30;
		int backY = 500;
		if(Gdx.input.getX() < backX + backButtonInactive.getWidth() && Gdx.input.getX() > backX && 576 - Gdx.input.getY() < backY + backButtonInactive.getHeight() && 576 - Gdx.input.getY() > backY)
		{
			game.batch.draw(backButtonActive, backX, backY);
			if(Gdx.input.isTouched())
			{
				this.dispose();
				game.setScreen(new LobbyPageScreen(game));
			}
		}
		else
		{
			game.batch.draw(backButtonInactive, backX, backY);
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
		backButtonActive.dispose();
		backButtonInactive.dispose();
		character.getTexture().dispose();
		armoryPageBackground.dispose();
		font.dispose();
	
	}

}

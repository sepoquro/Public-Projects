/**
 * 
 */
package com.mustard.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mustard.game.Mustard;
import com.mustard.server.object.User;

/**
 * @author jiami
 *
 */
public class NewUserPageScreen implements Screen{

	Mustard game;
	User user;
	Texture enlistBActive;
	Texture enlistBInactive;
	Texture loginPageBackground;
	Texture loginPageUsername;
	Texture loginPagePassword;
	Texture loginPageNickname;
	Texture backButtonActive;
	Texture backButtonInactive;
	
	Stage stage;
	TextField usernameBox;
	TextField passwordBox;
	TextField nicknameBox;
	
	boolean shouldPrintError;
	String errorMessage;
	
	private String username;
	private String password;
	private String nickname;
	
	BitmapFont font;
	
	public NewUserPageScreen(Mustard game) {
		this.game = game;
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		enlistBActive = new Texture("StartPage/Enlist_ac.png");
		enlistBInactive = new Texture("StartPage/Enlist_in.png");
		loginPageBackground = new Texture("LoginPage/LoginPage_background.png");
		loginPageUsername = new Texture("LoginPage/LoginPage_username.png");
		loginPagePassword = new Texture("LoginPage/LoginPage_password.png");
		loginPageNickname = new Texture("LoginPage/nickname.png");
		backButtonActive = new Texture("LoginPage/Exit_Button_ac.png");
		backButtonInactive = new Texture("LoginPage/Exit_Button_in.png");
		
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		font = new BitmapFont();
		font.setColor(Color.RED);
		font.getData().setScale(1.5f);
		
		usernameBox = new TextField("", skin);
		usernameBox.setPosition(445, 305);
		usernameBox.setSize(300, 30);
		
		passwordBox = new TextField("", skin);
		passwordBox.setPosition(445, 250);
		passwordBox.setSize(300, 30);
		
		nicknameBox = new TextField("", skin);
		nicknameBox.setPosition(445, 195);
		nicknameBox.setSize(300, 30);
		
		stage.addActor(nicknameBox);
		stage.addActor(passwordBox);
		stage.addActor(usernameBox);
		
		username = "";
		password = "";
		nickname = "";
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(loginPageBackground, 0, 0);
		int reportX = 440;
		int Y = 340;
		
		int backX = 170;
		int backY = 395;
		
		if(shouldPrintError)
		{
			font.draw(game.batch, errorMessage, 150, 130);
		}
		
		if(Gdx.input.getX() < reportX + enlistBInactive.getWidth() && Gdx.input.getX() > reportX && 576 - Gdx.input.getY() < Y + enlistBInactive.getHeight() && 576 - Gdx.input.getY() > Y)
		{
			game.batch.draw(enlistBActive, reportX, Y);
			if(Gdx.input.isTouched())
			{
				username = usernameBox.getText();
				password = passwordBox.getText();
				nickname = nicknameBox.getText();
				
				if (game.cl.signup(username, password, nickname)) {
					game.playButton();
					this.dispose();
					game.setScreen(new CharacterPageScreen(game));
				}
				/* TODO: Display some error message */
			}
		}
		else
		{
			game.batch.draw(enlistBInactive, reportX, Y);
		}
		
		if(Gdx.input.getX() < backX + backButtonInactive.getWidth() && Gdx.input.getX() > backX && 576 - Gdx.input.getY() < backY + backButtonInactive.getHeight() && 576 - Gdx.input.getY() > backY)
		{
			game.batch.draw(backButtonActive, backX, backY);
			if(Gdx.input.isTouched())
			{
				game.playButton();
				this.dispose();
				game.setScreen(new StartPageScreen(game));
			}
			
		}
		else
		{
			game.batch.draw(backButtonInactive, backX, backY);
		}
		
		game.batch.draw(loginPageUsername, 270, 305, 150, 34);
		game.batch.draw(loginPagePassword, 270, 250, 150, 34);
		game.batch.draw(loginPageNickname, 262, 195, 150, 34);
		
		
		game.batch.end();
		stage.draw();
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
		loginPageBackground.dispose();
		loginPageUsername.dispose();
		loginPagePassword.dispose();
		loginPageNickname.dispose();
		backButtonActive.dispose();
		backButtonInactive.dispose();
		stage.dispose();
	}
	
	public void printError(String errorMessage2) {
		shouldPrintError = true;
		errorMessage = errorMessage2;
	}
	
	public void resetError(){
		shouldPrintError = false;
	}


}

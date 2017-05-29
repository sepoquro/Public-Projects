/**
 * 
 */
package com.mustard.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
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
public class LoginPageScreen implements Screen, TextInputListener{

	Mustard game;
	User user;
	Texture reportBActive;
	Texture reportBInactive;
	Texture loginPageBackground;
	Texture loginPageUsername;
	Texture loginPagePassword;
	Texture backButtonActive;
	Texture backButtonInactive;
	
	
	String username;
	String password;
	
	Stage stage;
	
	boolean shouldPrintError;
	String errorMessage;

	BitmapFont font;
	
	TextField usernameBox;
	TextField passwordBox;
	
	public LoginPageScreen(Mustard game) {
		this.game = game;
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		reportBActive = new Texture("StartPage/Reportback_ac.png");
		reportBInactive = new Texture("StartPage/ReportBack_in.png");
		loginPageBackground = new Texture("LoginPage/LoginPage_background.png");
		loginPageUsername = new Texture("LoginPage/LoginPage_username.png");
		loginPagePassword = new Texture("LoginPage/LoginPage_password.png");
		backButtonActive = new Texture("LoginPage/Exit_Button_ac.png");
		backButtonInactive = new Texture("LoginPage/Exit_Button_in.png");
		
		font = new BitmapFont();
		font.setColor(Color.RED);
		font.getData().setScale(1.5f);
		
		shouldPrintError = false;
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		usernameBox = new TextField("", skin);
		usernameBox.setPosition(445, 280);
		usernameBox.setSize(300, 40);
		
		passwordBox = new TextField("", skin);
		passwordBox.setPosition(445, 200);
		passwordBox.setSize(300, 40);
		
		stage.addActor(passwordBox);
		stage.addActor(usernameBox);
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
		
		if(Gdx.input.getX() < reportX + reportBInactive.getWidth() && Gdx.input.getX() > reportX && 576 - Gdx.input.getY() < Y + reportBInactive.getHeight() && 576 - Gdx.input.getY() > Y)
		{
			game.batch.draw(reportBActive, reportX, Y);
			if(Gdx.input.isTouched())
			{
				username = usernameBox.getText();
				password = passwordBox.getText();
				
				if(game.cl.login(username, password))
				{
					game.playButton();
					this.resetError();
					this.dispose();
					game.setScreen(new LobbyPageScreen(game));
				}
			}
			
		}
		else
		{
			game.batch.draw(reportBInactive, reportX, Y);
		}
		
		if(Gdx.input.getX() < backX + backButtonInactive.getWidth() && Gdx.input.getX() > backX && 576 - Gdx.input.getY() < backY + backButtonInactive.getHeight() && 576 - Gdx.input.getY() > backY)
		{
			game.batch.draw(backButtonActive, backX, backY);
			if(Gdx.input.isTouched())
			{
				game.playButton();
				this.resetError();
				this.dispose();
				game.setScreen(new StartPageScreen(game));
			}
			
		}
		else
		{
			game.batch.draw(backButtonInactive, backX, backY);
		}
		
		game.batch.draw(loginPageUsername, 270, 280, 150, 44);
		game.batch.draw(loginPagePassword, 270, 200, 150, 44);		
		
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
		reportBActive.dispose();
		reportBInactive.dispose();
		loginPageBackground.dispose();
		loginPageUsername.dispose();
		loginPagePassword.dispose();
		backButtonActive.dispose();
		backButtonInactive.dispose();
		font.dispose();
		stage.dispose();
	}

	@Override
	public void input(String text) {
		
		
	}

	@Override
	public void canceled() {
		// TODO Auto-generated method stub
		
	}

	public void printError(String errorMessage2) {
		shouldPrintError = true;
		errorMessage = errorMessage2;
	}
	
	public void resetError(){
		shouldPrintError = false;
	}

	

}

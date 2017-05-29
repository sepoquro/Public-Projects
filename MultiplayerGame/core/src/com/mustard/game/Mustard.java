package com.mustard.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Mustard extends Game {
	public SpriteBatch batch;
	public Client cl;
	public MusicPlayer musicManager;
	
	@Override
	public void create () {
		//batch = new SpriteBatch();
		musicManager = new MusicPlayer();
		musicManager.playMenuMusic();
		
		cl = new Client(this, 8000, "localhost");
		
	}
	
	public void playButton()
	{
		musicManager.playButton();
	}
	
	public void playMove()
	{
		musicManager.playMove();
	}
	
	public void stopMenuMusic()
	{
		musicManager.stopMenuMusic();
	}
	
	public void playBackground()
	{
		musicManager.playBackground();
	}
	
	public void playTransition()
	{
		musicManager.playTransition();
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}

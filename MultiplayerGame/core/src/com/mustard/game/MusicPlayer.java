package com.mustard.game;

import java.util.Random;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicPlayer {
	public Music menuMusic;
	public Music buttonClick;
	public Music transition;
	public Vector<Music> background;
	public Vector<Music> gunfire;
	public Vector<Music> gunfireAutomatic;
	public Music footsteps;
	public Music reload;
	public Music skill;
	public Music Victory;
	public Music Lost;
	public Music skip;
	public Music currentBackground;
	
	public MusicPlayer()
	{
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("menu.mp3"));
		buttonClick = Gdx.audio.newMusic(Gdx.files.internal("Button.wav"));
		transition = Gdx.audio.newMusic(Gdx.files.internal("transition.mp3"));
		background = new Vector<Music>();
		background.add(Gdx.audio.newMusic(Gdx.files.internal("background1.mp3")));
		background.add(Gdx.audio.newMusic(Gdx.files.internal("background2.mp3")));
		background.add(Gdx.audio.newMusic(Gdx.files.internal("background3.mp3")));
		background.add(Gdx.audio.newMusic(Gdx.files.internal("background5.mp3")));
		background.add(Gdx.audio.newMusic(Gdx.files.internal("background6.mp3")));
		skill = Gdx.audio.newMusic(Gdx.files.internal("Skill.wav"));
		Victory = Gdx.audio.newMusic(Gdx.files.internal("Victory.ogg"));
		Lost = Gdx.audio.newMusic(Gdx.files.internal("Lost.mp3"));
		skip = Gdx.audio.newMusic(Gdx.files.internal("skipTurn.wav"));
		gunfire = new Vector<Music>();
		gunfireAutomatic = new Vector<Music>();
		gunfireAutomatic.add(Gdx.audio.newMusic(Gdx.files.internal("gunfire1.wav")));
		gunfire.add(Gdx.audio.newMusic(Gdx.files.internal("gunfire2.wav")));
		gunfire.add(Gdx.audio.newMusic(Gdx.files.internal("gunfire3.wav")));
		gunfire.add(Gdx.audio.newMusic(Gdx.files.internal("gunfire4.wav")));
		gunfire.add(Gdx.audio.newMusic(Gdx.files.internal("gunfire5.wav")));
		gunfireAutomatic.add(Gdx.audio.newMusic(Gdx.files.internal("gunfire6.wav")));
		gunfireAutomatic.add(Gdx.audio.newMusic(Gdx.files.internal("gunfire7.wav")));
		gunfire.add(Gdx.audio.newMusic(Gdx.files.internal("gunfire8.wav")));
		footsteps = Gdx.audio.newMusic(Gdx.files.internal("footstep.wav"));
		reload = Gdx.audio.newMusic(Gdx.files.internal("preattack.wav"));
		
		for(Music m : background)
		{
			m.setLooping(true);
		}
	}
	
	public void playonReload()
	{
		reload.play();
	}
	
	public void playSkill()
	{
		skill.play();
	}
	
	public void playSkip()
	{
		skip.play();
	}
	
	public void playVictory()
	{
		Victory.setLooping(true);
		Victory.play();
	}
	
	public void playLost()
	{
		Lost.setLooping(true);
		Lost.play();
	}
	
	public void playMenuMusic()
	{
		menuMusic.setLooping(true);
		menuMusic.setVolume(0.5f);
		menuMusic.play();
	}
	
	public void playButton()
	{
		buttonClick.play();
	}
	
	public void playMove()
	{
		footsteps.play();
	}
	
	public void stopMenuMusic()
	{
		menuMusic.stop();
	}
	
	public void stopBackgroundMusic()
	{
		currentBackground.stop();
	}
	
	public void stopVictoryMusic()
	{
		Victory.stop();
	}
	
	public void stopLostMusic()
	{
		Lost.stop();
	}
	
	public void playBackground()
	{
		Random rand = new Random();
		int i = rand.nextInt(background.size());
		currentBackground = background.get(i);
		background.get(i).setVolume(0.3f);
		background.get(i).play();
	}
	
	public void playGunfire()
	{
		Random rand = new Random();
		int i = rand.nextInt(gunfire.size());
		gunfire.get(i).play();
	}
	
	public void playAutomaticGunfire()
	{
		Random rand = new Random();
		int i = rand.nextInt(gunfireAutomatic.size());
		gunfireAutomatic.get(i).play();
	}
	
	public void playTransition()
	{
		transition.play();
	}
}

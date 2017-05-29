package com.mustard.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MustardSprite extends Sprite{
	
	private String username;
	
	public MustardSprite(Texture texture) {
		// TODO Auto-generated constructor stub
		super(texture);
	}

	public void setUsername(String u) {
		username = u;
	}
	
	public String getUsername() {
		return username;
	}
	
}

package com.mustard.game;

import com.badlogic.gdx.graphics.Texture;

public class Tile {
	private Effect tileEffect;
	private String tileImage;
	private boolean walkable;
	private boolean shootable;
	private int xpos;
	private int ypos;
	private Texture tile_texture;
	
	/** Constructs a new tile object.
	 * 		@param img - Image URL of the tile piece
	 * 		@param p - Determines if the tile is an obstacle or not
	 *  	@param x - X coord of the tile object
	 *  	@param y - Y coord of the tile object
	 *  */
	public Tile(String img, boolean w, boolean s, int x, int y) {
		tileEffect = null;
		tileImage = img;
		tile_texture = new Texture(img);
		walkable = w;
		shootable = s;
		xpos = x;
		ypos = y;
	}
	
	/** Setters */
	
	/** Updates the coordinates of the tile
	 * 		@param x - New X coord
	 * 		@param y - New Y coord */
	public void updateCoordinates (int x, int y) {
		xpos = x;
		ypos = y;
	}
	
	/** Sets the walkable status of this object to the @param w */
	public void setWalkableStatus (boolean w) {
		walkable = w;
	}
	
	/** Sets the walkable status of this object to the @param s */
	public void setShootableStatus (boolean s) {
		shootable = s;
	}
	
	/** Updates the tile texture of this tile
	 * 		@param img_url - IMG url of the new tile texture */
	public void setTileTexture (String img_url) {
		tileImage = img_url;
		tile_texture = new Texture (img_url);
	}
	
	/** GETTERS */
	
	/** Returns a boolean variable determining if the tile is walkable
	 * 	(not an object) or not. */
	public boolean isWalkable () { return walkable; }
	
	/** Returns a boolean variable determining if the tile is shootable
	 * 	(not an object) or not. */
	public boolean isShootable () { return shootable; }
	
	/** Returns the tile's IMG URL */
	public String getImage () { return tileImage; }
	
	/** Returns the tile's effect */
	public Effect getTileEffect () { return tileEffect; }
	
	/** Returns the tile's texture object */
	public Texture getTileTexture(){ return tile_texture; }
	
	/** Returns the tile's x position in the grid */
	public int getXCoord () { return xpos; }
	
	/** Returns the tile's y position in the grid */
	public int getYCoord () { return ypos; }
}
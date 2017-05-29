package com.mustard.util;

import java.util.Random;
import java.util.Vector;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mustard.server.object.User;

public class AttackRangeFinder {
	public TiledMap map;
	
	public AttackRangeFinder(TiledMap map)
	{
		this.map = map;
	}
	
	public Vector<Integer> FindAttackerRange(User user, int xPos, int yPos)
	{
		Vector<Integer> vec = new Vector<Integer>();
		int playerX = xPos;
		int playerY = yPos;
		int playerRange = user.getCharacter().getAttackRange();

		boolean[] isblock = new boolean[8];
		for(int k = 0; k < isblock.length; k++)
		{
			isblock[k] = true;
		}
		for(int i=0;i<8;i++){
			vec.add(0);
		}
		
        for(int i = 1; i <= playerRange; i++)
        {
        	if(cellValid(playerX + i, playerY + i) && isblock[0])
        	{
        		vec.set(0,vec.get(0)+1);
        	}
        	else
        	{
        		isblock[0] = false;
        	}
        	
        	if(cellValid(playerX - i, playerY + i) && isblock[1])
        	{
        		vec.set(1,vec.get(1)+1);
        	}
        	else
        	{
        		isblock[1] = false;
        	}
        	
        	if(cellValid(playerX - i, playerY - i) && isblock[2])
        	{
        		vec.set(2,vec.get(2)+1);
        	}
        	else
        	{
        		isblock[2] = false;
        	}
        	
        	if(cellValid(playerX + i, playerY - i) && isblock[3])
        	{
        		vec.set(3,vec.get(3)+1);
        	}
        	else
        	{
        		isblock[3] = false;
        	}
        	
        	if(cellValid(playerX, playerY + i) && isblock[4])
        	{
        		vec.set(4,vec.get(4)+1);
        	}
        	else
        	{
        		isblock[4] = false;
        	}
        	
        	if(cellValid(playerX, playerY - i) && isblock[5])
        	{
        		vec.set(5,vec.get(5)+1);
        	}
        	else
        	{
        		isblock[5] = false;
        	}
        	
        	if(cellValid(playerX - i, playerY) && isblock[6])
        	{
        		vec.set(6,vec.get(6)+1);
        	}
        	else
        	{
        		isblock[6] = false;
        	}
        	
        	if(cellValid(playerX + i, playerY) && isblock[7])
        	{
        		vec.set(7,vec.get(7)+1);
        	}
        	else
        	{
        		isblock[7] = false;
        	}
        }
		return vec;
	}

	public boolean cellValid(int playerX, int playerY) {
		//System.out.println("x" + playerX + "y" + playerY);
		if(playerX<0||playerX>31){
			return false;
		}
		if(playerY<0||playerY>17){
			return false;
		}
		TiledMapTileLayer layer1 = (TiledMapTileLayer) map.getLayers().get(0);
        TiledMapTileLayer layer2 = (TiledMapTileLayer) map.getLayers().get(1);
        TiledMapTileLayer layer3 = (TiledMapTileLayer) map.getLayers().get(2);
        TiledMapTileLayer.Cell cell1 = layer1.getCell(playerX, playerY);
        TiledMapTileLayer.Cell cell2 = layer2.getCell(playerX, playerY);
        TiledMapTileLayer.Cell cell3 = layer3.getCell(playerX, playerY);
        Boolean shootable1 = null;
        Boolean shootable2 = null;
        Boolean shootable3 = null;
        if(cell1!=null){
        	shootable1 = (Boolean)cell1.getTile().getProperties().get("shootable");
        }
        if(shootable1==null){
        	shootable1 = true;
        }
        if(cell2!=null){
        	shootable2 = (Boolean)cell2.getTile().getProperties().get("shootable");
        }
        if(shootable2==null){
        	shootable2 = true;
        }
        if(cell3!=null){
        	shootable3 = (Boolean)cell3.getTile().getProperties().get("shootable");
        }
        if(shootable3==null){
        	shootable3 = true;
        }
        Boolean shootable = shootable1&&shootable2&&shootable3;
        if(shootable)
        {
        	return true;
        }
        else
        {
        	return false;
        }
	}
	
	public boolean cellValidServer(int cellX, int cellY, Vector<User> user) {
		if(cellValid(cellX, cellY))
		{
			for(User i : user)
			{
				if(i.getCharacter().x == cellX && i.getCharacter().y == cellY)
				{
					return true;
				}
			}
		}
		return false;
	}

	public int attackCalculator(User attacker, User receiver)
	{
		int damage = 0;
		if(attacker.getCharacter().getAttackStrength() > receiver.getCharacter().getArmorValue())
		{
			Random rand = new Random();
			int hit = rand.nextInt(100);
			if(hit < receiver.getCharacter().getDodge())
			{
				damage = attacker.getCharacter().getAttackStrength() - receiver.getCharacter().getArmorValue();
				receiver.getCharacter().decrementHealth(damage);
			}
		}
		return damage;
	}
	
	public void skillHandler(User user, User receiver, int type)
	{
		if(type == 1)
		{
			
		}
		else if(type == 2)
		{
			
		}
		else if(type == 3)
		{
			
		}
		else if(type == 4)
		{
			
		}
	}
}

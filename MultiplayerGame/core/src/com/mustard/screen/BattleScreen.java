/**
 * 
 */
package com.mustard.screen;

import java.util.Vector;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mustard.game.Mustard;
import com.mustard.game.MustardSprite;
import com.mustard.server.action.Response;
import com.mustard.server.object.Constants;
import com.mustard.server.object.User;
import com.mustard.util.AttackRangeFinder;

public class BattleScreen extends ApplicationAdapter implements Screen, InputProcessor{

	Mustard game;
	User user;
	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;
	TiledMapTileLayer tileLayer;
	Texture isPlayerTurn;
	Texture skillIns1;
	Texture skillIns2;
	int textureCounter;
	int skillCounter;
	
	SpriteBatch sb;
	Vector<MustardSprite> players;
	MustardSprite sprite;
    Vector<Sprite> redTileVector;
    Vector<User> users;
    int xPos;
    int yPos;
    int redTileCounter;
    int sprite_index;
    boolean shouldUpdate;
    int frameCounter;
    BitmapFont font;
    int server_ping_counter;
    boolean skillPressed;
    
    Vector<MustardSprite> usr_sprites;
    
    boolean moved;
    boolean attackedPressed;
    boolean inAttackRange;
    
	
	public BattleScreen(Mustard game) {
		moved = false;
		attackedPressed = false;
		skillPressed = false;
		inAttackRange = false;
		this.game = game;
		server_ping_counter = Constants.SERVER_PING_RATE;
		
		this.game.cl.sendMovement(false);
	    users = this.game.cl.getUserList();
	    
	    isPlayerTurn = new Texture("TurnIndicator.png");
	    skillIns1 = new Texture("skillIns1.png");
	    skillIns2 = new Texture("skillIns2.png");
	    textureCounter = 0;
	    skillCounter = 0;
	    
		/* render user textures */
		usr_sprites = new Vector<MustardSprite> ();
		for (User u : users) {
			MustardSprite mSprite = new MustardSprite (new Texture (u.getCharacter().getImage()));
			mSprite.setUsername(u.getUsername());
			usr_sprites.add(mSprite);
		}
		sprite_index = 0;
		
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        redTileCounter = 0;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        tiledMap = new TmxMapLoader().load("dock.tmx");
        tileLayer = (TiledMapTileLayer)tiledMap.getLayers().get(0);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        Gdx.input.setInputProcessor(this);
        
        font = new BitmapFont();
        
        sb = new SpriteBatch();
        
        players = new Vector<MustardSprite>();
        int playerCounter = 0;
        for(User u : users) {
        	switch(playerCounter) {
	        	case 0: if(this.game.cl.getUser().getUsername().equals(u.getUsername())){
		        			xPos = 1;
		        			yPos = 1;
		        			this.game.cl.getUser().getCharacter().setXCoord(xPos);
		        			this.game.cl.getUser().getCharacter().setYCoord(yPos);
	        			}
	        			break;
	        	case 2: if(this.game.cl.getUser().getUsername().equals(u.getUsername())){
		        			xPos = 30;
		        			yPos = 1;
		        			this.game.cl.getUser().getCharacter().setXCoord(xPos);
		        			this.game.cl.getUser().getCharacter().setYCoord(yPos);
	        			}
	        			break;
	        	case 1: if(this.game.cl.getUser().getUsername().equals(u.getUsername())){
		        			xPos = 1;
		        			yPos = 16;
		        			this.game.cl.getUser().getCharacter().setXCoord(xPos);
		        			this.game.cl.getUser().getCharacter().setYCoord(yPos);
	        			}
	        			break;
	        	case 3: if(this.game.cl.getUser().getUsername().equals(u.getUsername())){
		        			xPos = 30;
		        			yPos = 16;
		        			this.game.cl.getUser().getCharacter().setXCoord(xPos);
		        			this.game.cl.getUser().getCharacter().setYCoord(yPos);
		    			}
		    			break;
        	}
        	playerCounter++;

        }
        
        redTileVector = new Vector<Sprite>();
        for(int i=0; i< 200; i++) {
        	Sprite redTile = new Sprite(new Texture(Gdx.files.internal("red3232.png")));
        	redTileVector.add(redTile);
        }
        frameCounter = 0;
        this.game.cl.sendMovement(false);
	}

	@Override
	public void show() {
		
	}
	
	public void shouldUpdate()
	{
		shouldUpdate = true;
	}

	@Override
	public void render(float delta) {
		 //System.out.println(game.cl.getUser().getCharacter().getRemainingPoints());
		 Gdx.gl.glClearColor(0, 0, 0, 0);
	     Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	     camera.update();
	     tiledMapRenderer.setView(camera);
	     tiledMapRenderer.render();
	     sb.begin(); 
	     if(!moved&&attackedPressed){
	    	 //System.out.println("print red tiles: " + moved + " " + attackedPressed);
	    	 for(int i=0; i<redTileCounter;i++){
		    	 redTileVector.get(i).draw(sb);
		     }
	    	 moved=false;
	    	 attackedPressed=true;
		 } 
	     if(frameCounter==0) {
	    	 game.cl.getAllUsers();
	    	 users = game.cl.getUserList();
	     }
	     /* Pings server once every 1000 ticks */
	     if (server_ping_counter <= 0) {
		     game.cl.sendMovement(false);
		     game.cl.getAllUsers();
		     users = game.cl.getUserList();
		     server_ping_counter = Constants.SERVER_PING_RATE;
	     }
	     else {
	    	 --server_ping_counter;
	     }
	     
	     sprite_index = 0;
	     for(User u : users) {
	    	 if(u.getCharacter().getHealth() > 0)
	    	 {
	    		 MustardSprite temp = usr_sprites.get(sprite_index);
		    	 temp.setScale(0.15f);
		    	 temp.setRotation(u.getCharacter().getRotation());
		    	 temp.setPosition(32*u.getCharacter().getXCoord()-226/2, 32*u.getCharacter().getYCoord()-226/2-4);
		    	 
		    	 if(u.getTeam()) {
				   		 font.setColor(104,0,0,1);
				 } else {
				   		 font.setColor(0,0,165,1);
				 }
		    	 font.draw(sb, u.getNickname(), 32*u.getCharacter().getXCoord(), 32*u.getCharacter().getYCoord());
		    	 String health = "";
			     health += u.getCharacter().getHealth();
			     health += "/";
			     health += u.getCharacter().getMaxHealth();
			     font.draw(sb, health, 32*u.getCharacter().getXCoord(), 32*u.getCharacter().getYCoord()-16);

		     	if(this.game.cl.getUser().getUsername().equals(u.getUsername())) {
	        		sprite = temp;
	        	} else {
	        		players.add(temp);
	        	}
	        	temp.draw(sb);
	        	sprite_index++;
	    	 } else
	    		 sprite_index++;
	     }
	     
	     font.setColor(255,255,255,1);
	     if(game.cl.isMyTurn())
	    	 font.draw(sb, "Current Player's Turn.", 32*27, (32*2));
	     String cooltime = "";
	     if(game.cl.getUser().getCharacter().checkSkillActive())
	    	 cooltime += "Skill ACTIVE";
	     else {
	    	 cooltime += "Skill Cooltime: ";
	    	 cooltime += game.cl.getUser().getCharacter().getCooldownTimer();
	     }
	     font.draw(sb, cooltime, 32*27, (32*2)-16);
	     String remainingMovement = "Remaining Movement: ";
	     remainingMovement += game.cl.getUser().getCharacter().getRemainingPoints();
	     font.draw(sb, remainingMovement, 32*27, 32*1);
	    
	     if(textureCounter == 60 && !game.cl.isMyTurn()) {
	    	 textureCounter = 0;
	     }
	     if(game.cl.isMyTurn() && textureCounter < 60) {
	    	 sb.draw(isPlayerTurn, 32*13, 32*9);
	    	 textureCounter++;
	     }
	     if(!skillPressed && skillCounter == 60)
	     {
	    	 textureCounter = 0;
	     }
	     if(skillPressed && skillCounter < 60) {
	    	 if(game.cl.getUser().getCharacterType() == Constants.MARKSMAN_ID || game.cl.getUser().getCharacterType() == Constants.SOLDIER_ID)
	    	 {
	    		 sb.draw(skillIns1, 32*8, 32*9);
	    		 skillCounter++;
	    	 }
	    	 else
	    	 {
	    		 sb.draw(skillIns2, 32*8, 32*9);
	    		 skillCounter++;
	    	 }
	     }
	     
	     sb.end();
	     
	     if(game.cl.checkIfGameEnded())
	     {
			if(game.cl.getWhoWon()) {
				//redTeamWon
				if(game.cl.getUser().getTeam()) {
					//redTeam
					//load new screen
					game.musicManager.stopBackgroundMusic();
					game.musicManager.playVictory();
					game.setScreen(new VictoryScreen(game));
				} else {
					//blueTeam
					game.musicManager.stopBackgroundMusic();
					game.musicManager.playLost();
					game.setScreen(new LostScreen(game));
				}
			} else { //blue team won
				if(!game.cl.getUser().getTeam()) {
					//blue team
					game.musicManager.stopBackgroundMusic();
					game.musicManager.playVictory();
					game.setScreen(new VictoryScreen(game));
				} else {
					//redTeam
					game.musicManager.stopBackgroundMusic();
					game.musicManager.playLost();
					game.setScreen(new LostScreen(game));
				}
			}
	     }
	     
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

		tiledMap.dispose();
		isPlayerTurn.dispose();
		skillIns1.dispose();
		skillIns2.dispose();
		for(MustardSprite spr : players) {
			spr.getTexture().dispose();
		}
		players.clear();
		players = null;
		sprite.getTexture().dispose();
		for(Sprite spr : redTileVector) {
			spr.getTexture().dispose();
		}
		redTileVector.clear();
	    users.clear();
	    users = null;
	    for(MustardSprite spr : usr_sprites) {
	    	spr.getTexture().dispose();
	    }
	    usr_sprites.clear();
	    font.dispose();
	    sb.dispose();
	}
	
	@Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
    	if (!game.cl.isMyTurn()) {
    		System.out.println("Not my turn yet");
    		return false;
    	}	
    	if(game.cl.getUser().getCharacter().getHealth() > 0)
    	{
    		boolean arrow = true;
        	if(keycode == Input.Keys.LEFT){
        		xPos = xPos - 1;
        		sprite.setRotation(Constants.ROTATE_LEFT);
        		game.cl.getUser().getCharacter().setRotation(Constants.ROTATE_LEFT);
        		if(xPos<0){
        			xPos = xPos + 1;
        		}
        		moved = true;
        		attackedPressed = false;
        	}
        	else if(keycode == Input.Keys.RIGHT){
            	xPos = xPos + 1;
            	sprite.setRotation(Constants.ROTATE_RIGHT);
            	game.cl.getUser().getCharacter().setRotation(Constants.ROTATE_RIGHT);
            	if(xPos>31){
        			xPos = xPos - 1;
            	}
        		moved = true;
        		attackedPressed = false;
            }
        	else if(keycode == Input.Keys.UP){
            	yPos = yPos + 1;
            	sprite.setRotation(Constants.ROTATE_UP);
            	game.cl.getUser().getCharacter().setRotation(Constants.ROTATE_UP);
            	if(yPos>17){
        			yPos = yPos - 1;
        		}
        		moved = true;
        		attackedPressed = false;
            }
        	else if(keycode == Input.Keys.DOWN){
            	yPos = yPos - 1;
            	sprite.setRotation(Constants.ROTATE_DOWN);
            	game.cl.getUser().getCharacter().setRotation(Constants.ROTATE_DOWN);
            	if(yPos<0){
        			yPos = yPos + 1;
        		}
        		moved = true;
        		attackedPressed = false;
            } else {
            	arrow = false;
            }
            if(arrow) {
            	TiledMapTileLayer layer1 = (TiledMapTileLayer) tiledMap.getLayers().get(0);
                TiledMapTileLayer layer2 = (TiledMapTileLayer) tiledMap.getLayers().get(1);
                TiledMapTileLayer layer3 = (TiledMapTileLayer) tiledMap.getLayers().get(2);
                TiledMapTileLayer.Cell cell1 = layer1.getCell(xPos,yPos);
                TiledMapTileLayer.Cell cell2 = layer2.getCell(xPos,yPos);
                TiledMapTileLayer.Cell cell3 = layer3.getCell(xPos,yPos);
                Boolean passable1 = null;
                Boolean passable2 = null;
                Boolean passable3 = null;
                if(cell1!=null){
                	passable1 = (Boolean)cell1.getTile().getProperties().get("passable");
                }
                if(passable1==null){
                	passable1 = true;
                }
                if(cell2!=null){
                	passable2 = (Boolean)cell2.getTile().getProperties().get("passable");
                }
                if(passable2==null){
                	passable2 = true;
                }
                if(cell3!=null){
                	passable3 = (Boolean)cell3.getTile().getProperties().get("passable");
                }
                if(passable3==null){
                	passable3 = true;
                }
                Boolean passable = passable1&&passable2&&passable3;
                //System.out.println("x: " + xPos + " y: " + yPos + " passable: " + passable);
                if(passable&&!game.cl.getUser().getCharacter().movementDone()){
                	game.cl.getUser().getCharacter().usedMove();
                	sprite.setPosition(32*xPos-226/2, 32*yPos-226/2-4);
                	game.cl.getUser().getCharacter().setXCoord(xPos);
                	game.cl.getUser().getCharacter().setYCoord(yPos);
                	game.playMove();
            		game.cl.sendMovement(true);
                } else {
                	if(keycode == Input.Keys.LEFT){
                		xPos = xPos +1;
                	}
                    if(keycode == Input.Keys.RIGHT){
                    	xPos = xPos - 1;
                    }
                    if(keycode == Input.Keys.UP){
                    	yPos = yPos - 1;
                    }
                    if(keycode == Input.Keys.DOWN){
                    	yPos = yPos + 1;
                    }
                }
            }
    	}
    	return false;
    }

    @Override
    public boolean keyTyped(char character) {
    	if (!game.cl.isMyTurn()) {
    		return false;
    	}
    	if(character=='a' || character=='A'){
    		// System.out.println("Attacking!");
    		
    		attackedPressed = true;
    		moved = false;
    		game.musicManager.playonReload();
    		redTileCounter = 0;
    		game.cl.getAllUsers();
        	users = game.cl.getUserList();
    		AttackRangeFinder arf = new AttackRangeFinder(tiledMap);
    		Vector<Integer> range = arf.FindAttackerRange(game.cl.getUser(), xPos, yPos);
    		for(int i=1;i<=range.get(0);i++){
    			redTileVector.get(redTileCounter).setPosition(32*(xPos+i), 32*(yPos+i));
    			redTileCounter++;
    			for(User u : users) {
    				if(u.getCharacter().getXCoord() == (xPos+i)) {
    					if(u.getCharacter().getYCoord() == (yPos+i)) {
    						if(!u.getCharacter().isDead()) {
    							inAttackRange = true;
    						}
        				}
    				}
    			}
    		}
    		for(int i=1;i<=range.get(1);i++){
    			redTileVector.get(redTileCounter).setPosition(32*(xPos-i), 32*(yPos+i));
    			redTileCounter++;
    			for(User u : users) {
    				if(u.getCharacter().getXCoord() == (xPos-i)) {
    					if(u.getCharacter().getYCoord() == (yPos+i)) {
    						if(!u.getCharacter().isDead()) {
    							inAttackRange = true;
    						}
        				}
    				}
    			}
    		}
    		for(int i=1;i<=range.get(2);i++){
    			redTileVector.get(redTileCounter).setPosition(32*(xPos-i), 32*(yPos-i));
    			redTileCounter++;
    			for(User u : users) {
    				if(u.getCharacter().getXCoord() == (xPos-i)) {
    					if(u.getCharacter().getYCoord() == (yPos-i)) {
    						if(!u.getCharacter().isDead())
    							inAttackRange = true;
        				}
    				}
    			}
    		}
    		for(int i=1;i<=range.get(3);i++){
    			redTileVector.get(redTileCounter).setPosition(32*(xPos+i), 32*(yPos-i));
    			redTileCounter++;
    			for(User u : users) {
    				if(u.getCharacter().getXCoord() == (xPos+i)) {
    					if(u.getCharacter().getYCoord() == (yPos-i)) {
    						if(!u.getCharacter().isDead())
    							inAttackRange = true;
        				}
    				}
    			}
    		}
    		for(int i=1;i<=range.get(4);i++){
    			redTileVector.get(redTileCounter).setPosition(32*(xPos), 32*(yPos+i));
    			redTileCounter++;
    			for(User u : users) {
    				if(u.getCharacter().getXCoord() == (xPos)) {
    					if(u.getCharacter().getYCoord() == (yPos+i)) {
    						if(!u.getCharacter().isDead())
    							inAttackRange = true;
        				}
    				}
    			}
    		}
    		for(int i=1;i<=range.get(5);i++){
    			redTileVector.get(redTileCounter).setPosition(32*(xPos), 32*(yPos-i));
    			redTileCounter++;
    			for(User u : users) {
    				if(u.getCharacter().getXCoord() == (xPos)) {
    					if(u.getCharacter().getYCoord() == (yPos-i)) {
    						if(!u.getCharacter().isDead())
    							inAttackRange = true;
        				}
    				}
    			}
    		}
    		for(int i=1;i<=range.get(6);i++){
    			redTileVector.get(redTileCounter).setPosition(32*(xPos-i), 32*(yPos));
    			redTileCounter++;
    			for(User u : users) {
    				if(u.getCharacter().getXCoord() == (xPos-i)) {
    					if(u.getCharacter().getYCoord() == (yPos)) {
    						if(!u.getCharacter().isDead())
    							inAttackRange = true;
        				}
    				}
    			}
    		}
    		for(int i=1;i<=range.get(7);i++){
    			redTileVector.get(redTileCounter).setPosition(32*(xPos+i), 32*(yPos));
    			redTileCounter++;
    			for(User u : users) {
    				if(u.getCharacter().getXCoord() == (xPos+i)) {
    					if(u.getCharacter().getYCoord() == (yPos)) {
    						if(!u.getCharacter().isDead())
    							inAttackRange = true;
        				}
    				}
    			}
    		}
    		
    		System.out.println("UPDATED inAttackRange: " + inAttackRange);
    	}
    	
    	else if(character=='s' || character=='S')
		{
    		skillPressed = true;
		}
    	
    	else if(character=='e' || character=='E')
    	{
    		attackedPressed = false;
    		skillPressed = false;
    		inAttackRange = false;
    		game.musicManager.playSkip();
    		game.cl.nextTurn();
    		game.cl.getUser().getCharacter().resetUsedPoints();
    		if(game.cl.getUser().getCharacterType() == Constants.TANK_ID) {
				if(game.cl.getUser().getCharacter().checkIfTankSkillOn()){
					if(game.cl.getUser().getCharacter().getCooldownTimer() == 2){
						game.cl.getUser().getCharacter().setTankSkillReceivedOff();
					}
				}
			} else {
				if(game.cl.getUser().getCharacter().checkIfTankSkillOn()) {
					game.cl.getUser().getCharacter().setTankSkillReceivedOff();
	 			}
			}
			game.cl.getUser().getCharacter().updateSkillTimer();
			if(game.cl.getUser().getCharacterType() == Constants.MARKSMAN_ID) {
 				if(game.cl.getUser().getCharacter().checkSkillActive()) {
 					game.cl.getUser().getCharacter().deactivateSkill();
 				}
 			} 
    	}
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    	if (!game.cl.isMyTurn()) {
    		return false;
    	}
    	Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
        Vector3 position = camera.unproject(clickCoordinates);
        int clickedX = (int) (position.x/32);
        int clickedY = (int) (position.y/32);
        System.out.println("x: " + clickedX + " y: " + clickedY);
        System.out.println("Attack Pressed: " + attackedPressed);
        
        game.cl.getAllUsers();
    	users = game.cl.getUserList();
        if(skillPressed){
        	attackedPressed = false;
        	Vector3 SkillclickCoordinates = new Vector3(screenX,screenY,0);
            Vector3 Skillposition = camera.unproject(SkillclickCoordinates);
            int SkillclickedX = (int) (Skillposition.x/32);
            int SkillclickedY = (int) (Skillposition.y/32);
        	for(User u : users) {
        		if(u.getCharacter().getXCoord() == SkillclickedX)
        		{
        			if(u.getCharacter().getYCoord() == SkillclickedY)
        			{
        				if(game.cl.getUser().getCharacter().checkSkillActive()) 
        				{
        					game.musicManager.playSkill();
        					game.cl.useSkills(u.getUsername());
        					game.cl.getAllUsers();
        		        	users = game.cl.getUserList(); 
        		        	if(game.cl.getUser().getCharacterType() == Constants.TANK_ID || game.cl.getUser().getCharacterType() == Constants.SUPPORT_ID) {
        		        		game.cl.nextTurn();
        		        	}
        				}
        			}
        		}
        	}
        	attackedPressed = false;
        	skillPressed = false;
        	inAttackRange = false;
        }
        else if(attackedPressed){
        	System.out.println("inAttackRange: " + inAttackRange);
        	if(inAttackRange){
            	for(User u : users) {
            		System.out.println("User game session:" + u.getSessionID());
            		System.out.println("Character coord: " + u.getCharacter().getXCoord() + " | " + u.getCharacter().getYCoord());
            		if(u.getCharacter().getXCoord() == clickedX) {
            			if(u.getCharacter().getYCoord() == clickedY) {
            				if(!u.getUsername().equals(game.cl.getUser().getUsername())) {
            					if(!u.getCharacter().isDead())
            					{
            						if(game.cl.getUser().getCharacterType() == Constants.MARKSMAN_ID || game.cl.getUser().getCharacterType() == Constants.SUPPORT_ID)
                					{
                						game.musicManager.playGunfire();
                					}
                					else
                					{
                						game.musicManager.playAutomaticGunfire();
                					}
                    				game.cl.attack(u.getUsername());
                    				game.cl.getUser().getCharacter().resetUsedPoints();
                    				if(game.cl.getUser().getCharacterType() == Constants.TANK_ID) {
                    					if(game.cl.getUser().getCharacter().checkIfTankSkillOn()){
                    						if(game.cl.getUser().getCharacter().getCooldownTimer() == 2){
                    							game.cl.getUser().getCharacter().setTankSkillReceivedOff();
                    						}
                    					}
                    				} else {
                    					if(game.cl.getUser().getCharacter().checkIfTankSkillOn()) {
                    						game.cl.getUser().getCharacter().setTankSkillReceivedOff();
                    		 			}
                    				}
                    	 			game.cl.getUser().getCharacter().updateSkillTimer();
                    	 			if(game.cl.getUser().getCharacterType() == Constants.MARKSMAN_ID) {
                    	 				if(game.cl.getUser().getCharacter().checkSkillActive()) {
                    	 					game.cl.getUser().getCharacter().deactivateSkill();
                    	 				}
                    	 			}
            					}
            				}
            			}
            		}
            	}
            	inAttackRange = false;
            	attackedPressed = false;
            	skillPressed = false;
        	} else {
        		System.out.println("Clicked non-enemy tile");
        		inAttackRange = false;
            	attackedPressed = false;
        	}
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


}

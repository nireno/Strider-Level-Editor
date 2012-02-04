package genie.lwjgl.slick2d;

import genie.GLCtools.camera.GridCamera;
import genie.XMLdb.dbInterfaces.ImagesLoader;
import genie.XMLdb.dbInterfaces.LevelData;
import genie.XMLdb.dbInterfaces.Tile;
import genie.sprite.Sprite;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class AddTileState extends BasicGameState {
	private LevelData level;

	private HashMap<Point2D.Float, Tile> placedTiles = null; /* Stores the tiles currently placed in the level */
	
	private ArrayList<Tile> tilePalette = null; /* Stores all tiles available to be placed in the level */
    private int selectedTile = 0; // The tile that is currently selected in the editor toolbox.
	
	private GridCamera cam = null;
    private int mouseX = 0;
    private int mouseY = 0;
    
    
	@Override
	/*
	 * The integer returned here is the same integer used switch to this
	 * gamestate. Every gamestate should therefore have a unique id.
	 */
	public int getID() {
		return 1;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		System.out.println("addtilestate init method");
		/* this method is executed at least once. It is called by the
		 * AppGame'Container' and is used to initialize all the data 
		 * which would be used later.
		 */
		init((Genie)game);
		
    	
	}

	public void init(Genie game)
	{
		level = game.getLevelData();
		placedTiles = createTileMap(level.getTiles());
		
		/* Initialize tile palette */
		ImagesLoader imgLoader = level.getImagesLoader();
		tilePalette = imgLoader.createTilePalette(imgLoader.getSpriteSheet("tiles.png"));
    	
    	/* Initialize the sprites from LevelData*/
    	
    	/* Initialize the GridCamera with loaded data */
		game.setupCamera();
		cam = game.getCamera();
	}
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		/* this method, like the update method above, is called by the AppGame'Container'
		 * at least once but will run until the end of the program's execution. This code
		 * is responsible for committing the logic in the game to the screen.
		 */
		
		cam.render(arg0, arg2);
		
		//draw tile ghost
		Image image = tilePalette.get(selectedTile).getSprite().getImage().copy();
		image.setAlpha(0.75f);
		float x = mouseX - 0.5f*image.getWidth();
		float y = mouseY - 0.5f*image.getHeight();
		arg2.drawImage(image, x, y);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int delta)
			throws SlickException {
		/* this method is called by the AppGame'Container' and will run at
		 * least once, but will continue to run indefinately. this method is 
		 * used to update the 'logic' within the game so that changes can be 
		 * refleceted.
		 */
    	handleInput(arg0.getInput());
    	cam.update(arg0, delta);
	}
	
	public void handleInput(Input input){
    	mouseX = input.getMouseX();
    	mouseY = input.getMouseY();
    	
    	if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
    	{
    		/*TODO: put this code in a separate method*/
    		float insertYWorld = cam.getyWorld() + mouseY;
    		float insertXWorld = cam.getxWorld() + mouseX;
    		
    		/* snap the coordinates to the grid */
    		insertYWorld = GridCamera.getGridSnapY(insertYWorld, cam.getGridHeight());
    		insertXWorld = GridCamera.getGridSnapX(insertXWorld, cam.getGridWidth());
    		
    		/* create a tile with these coordinates */
    		Point2D.Float point = new Point2D.Float(insertXWorld, insertYWorld);
    		
    		/*DELETE_TILE: This condition controls the deletion of tiles */
    		if(input.isKeyDown(Input.KEY_LCONTROL))
    		{
    			if(placedTiles.containsKey(point))
    			{
    				Sprite sprite = placedTiles.get(point).getSprite();
    				level.removeTile(placedTiles.get(point));
    				placedTiles.remove(point);
    				cam.removeSprite(sprite);
    			}
    			
    			return;
    		}
    		
    		Tile paletteTile = tilePalette.get(selectedTile);
    		Tile newTile = paletteTile.createCopy();
    		newTile.setXWorld(insertXWorld);
    		newTile.setYWorld(insertYWorld);
    		
			/*
			 * Test if a tile already exists at the point. If not we can safely
			 * add a new tile. Otherwise, only overwrite the old tile if the new
			 * one is different.
			 */
    		if(placedTiles.containsKey(point))
    		{
    			Tile tile = placedTiles.get(point);
    			if(!(tile.getSprite().getImage() == newTile.getSprite().getImage()))
    			{
					/*
					 * Then the tiles are different. Overwrite the old one with
					 * the new. 
					 */
    				System.out.println("tiles are different");
    				cam.removeSprite(tile.getSprite());
    				placedTiles.remove(point);
    				level.removeTile(placedTiles.get(point));
    				placedTiles.put(point, newTile);
    				level.addTile(newTile);
    				cam.addSprite(new Sprite(newTile.getSprite().getImage(),insertXWorld, insertYWorld) );
    			}
    			System.out.println("Image already there!");
    		}
    		else
    		{
    			System.out.println("Adding Tile : " + newTile);
    			placedTiles.put(point, newTile);
    			level.addTile(newTile);
				cam.addSprite(new Sprite(newTile.getSprite().getImage(),insertXWorld, insertYWorld) );
				
	    		ArrayList<Tile> tilesList = new ArrayList<Tile>(placedTiles.values());
	    		for(int i = 0; i < tilesList.size(); i++)
	    		{
	    			System.out.println(tilesList.get(i).toString());
	    		}
    		}
    		
    	}
    	
    	if(input.isKeyPressed(Input.KEY_S))
    	{
    		/* Save tile data to file */
    		System.out.println("Saving tile Data...");
    		updateLevelData();
    		level.saveData();
    		level.saveAsStriderLevel();
    	}
    	
    	if(input.isKeyPressed(Input.KEY_PERIOD))
		{
    		selectedTile++;
    		if(selectedTile >= tilePalette.size())
    		{
    			selectedTile = 0;
    		}
		}
    	
    	if(input.isKeyPressed(Input.KEY_COMMA))
		{
    		selectedTile--;
    		if(selectedTile < 0)
    		{
    			selectedTile = tilePalette.size() -1;
    		}
		}
    	
    }

	public int getSelectedTile() {
		return selectedTile;
	}

	public void setSelectedTile(int selectedTile) {
		this.selectedTile = selectedTile;
	}
	
	/**
	 * Creates a HashMap of Tiles using a Collection of Tiles.
	 * @param tiles
	 * @return a HashMap of Tiles;
	 */
	public static HashMap<Point2D.Float, Tile> createTileMap(Collection<Tile> tiles)
	{
		HashMap<Point2D.Float, Tile> tileMap = new HashMap<Point2D.Float, Tile>();
		Iterator<Tile> i = tiles.iterator();
		while(i.hasNext())
		{
			Tile tile = i.next();
			Sprite sprite = tile.getSprite();
			Point2D.Float point = new Point2D.Float(sprite.getxWorld(), sprite.getyWorld());
			if(!tileMap.containsKey(point))
			{
				tileMap.put(point, tile);
			}
		}
		return tileMap;
	}
	
	/* This method will update the Tiles stored in LevelData object */
	public void updateLevelData()
	{
		System.out.println("AddTileState::UpdateLevelData");
		ArrayList<Tile> tilesList = new ArrayList<Tile>(placedTiles.values());
		for(int i = 0; i < tilesList.size(); i++)
		{
			System.out.println(tilesList.get(i).toString());
		}
		level.setTiles(new ArrayList<Tile>(placedTiles.values()));
	}
	
	public ArrayList<Tile> getTilePalette()
	{
		return tilePalette;
	}
}

package genie.lwjgl.slick2d;

import genie.GLCtools.camera.GridCamera;
import genie.XMLdb.dbInterfaces.ImagesLoader;
import genie.XMLdb.dbInterfaces.LevelData;
import genie.XMLdb.dbInterfaces.Tile;
import genie.XMLdb.dbInterfaces.TilesReader;
import genie.sprite.Sprite;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

@SuppressWarnings("unused")
public class Genie extends StateBasedGame {
	/*
	 * this class provides the basic functionality of a game (hence it extends
	 * Basic Game from lwjgl slick) it provides a shell of execution, but is
	 * useless if it is not placed into a game container. A game container is
	 * the class responsible for calling all the methods within this class in
	 * order for the update-draw-sleep cycles to take place.(non-Javadoc)
	 * 
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	
	public static final int LOADING_STATE = 0;
	public static final int ADD_TILE_STATE = 1;
	
	private AddTileState addTileState;
	private LoadingState loadingState;
	
	private LevelData level;
	private static final String defaultPath = "res/new.xml";
	private String savePath = null;
	private GridCamera cam;
	private int screenWidth = 640, screenHeight = 480; /* Default screen sizes provided */
	
	/* By default the editor will start up with a blank level */ 
	public Genie(String title) {
		super(title);
		cam = new GridCamera();
		loadingState = new LoadingState(defaultPath);
		addTileState = new AddTileState();
		
	}
	
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		cam.setWidth(container.getScreenWidth());
		cam.setHeight(container.getScreenHeight());
		 
		System.out.println("Number of states: " + this.getStateCount());
    	this.addState(loadingState);
		this.addState(addTileState);
    	this.enterState(0);
	}
	
	public AddTileState getAddTileState()
	{
		return addTileState;
	}
	
	/*TODO: This method sets the path */
	public void setLevelSaveLocation(String path)
	{
		
	}
	
	public void setLevelData(LevelData level)
	{
		this.level = level;
	}
	
	public boolean saveLevel()
	{
		if(savePath == null) return false;
		
		level.saveData(savePath);
		level.saveAsStriderLevel();
		return true;
	}
	
	public void setSavePath(String path)
	{
		savePath = path;
	}
	
	public LevelData getLevelData()
	{
		return level;
	}

	public String getSavePath() {
		return savePath;
	}
	
	public String getDefaultPath()
	{
		return defaultPath;
	}
	
	public void loadLevel(String path)
	{
		level = new LevelData(path);
		if(TilesReader.verifyDocument(path))
			this.savePath = path;
		addTileState.init(this);
	}
	
	public GridCamera getCamera()
	{
		return cam;
	}
	
	public void setupCamera()
	{
		ImagesLoader imgLoader = level.getImagesLoader();
		ArrayList<Tile> tilePalette = imgLoader.createTilePalette(imgLoader.getSpriteSheet("tiles.png"));
		int tileWidth = tilePalette.get(0).getSprite().getImage().getWidth();
		int tileHeight = tilePalette.get(0).getSprite().getImage().getHeight();
		cam.setGridWidth(tileWidth);
		cam.setGridHeight(tileHeight);
		cam.setSprites(new ArrayList<Sprite>(Tile.convertToSprites(level.getTiles())));
	}
}

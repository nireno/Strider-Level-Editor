package genie.lwjgl.slick2d;

import genie.XMLdb.dbInterfaces.LevelData;
import genie.XMLdb.dbInterfaces.XMLReader;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LoadingState extends BasicGameState {

	Genie game;
	String path = null;
	
	public LoadingState(String path)
	{
		this.path = path;
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		System.out.println("LoadingState init method");
		
		if(!XMLReader.verifyDocument(path)) /* then the provided path contains an invalid editor document */
		{
			path = ((Genie)game).getDefaultPath(); //Use the default path to produce an empty level.
		}
			
		if(game instanceof Genie)
		{
			Genie editor = (Genie)game;
			editor.setLevelData(new LevelData(path));
		}
		this.leave(container, game);
		game.enterState(Genie.ADD_TILE_STATE);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub

	}
}

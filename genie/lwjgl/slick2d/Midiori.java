/**
 * 
 */
package genie.lwjgl.slick2d;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.SlickException;

/**
 * @author Josh
 *
 */
public class Midiori extends AppGameContainer {

	public Midiori(Game game, int width, int height, boolean fullscreen) throws SlickException {
		super(game, width, height, fullscreen); //provides AppGame'Container' with constructor.
		this.setShowFPS(false); //the container displays the FPS in the top-left corner by default.
		this.setVSync(true);
		this.start();	//Execution of game loop begins at this line.
	}
}

package genie;

import genie.forms.EditorToolBox;
import genie.lwjgl.slick2d.Genie;
import genie.lwjgl.slick2d.Midiori;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;



public class GenieLevelCreator {

@SuppressWarnings("unused")
 private static AppGameContainer gameContainer;
 @SuppressWarnings("unused")
private static EditorToolBox Konsole;
 private static Genie basicgame;

	public static void main(String[] args) {
		/* the code below creates a new BasicGame and it's Container (AppGameContainer).
		 * this container will execute the different methods within the BasicGame which
		 * would handle all the programming aspects of the code.
		 */
		System.out.println(System.getProperty("user.dir"));
		basicgame = new Genie("Project Midiori: Genie Level Creator");  //basic game class
		
		try{
		Konsole = new EditorToolBox(basicgame); //supporting toolbox class
		gameContainer = new Midiori(basicgame, 640, 480, false); //AppGame'Container'
		
//		gameContainer.setTargetFrameRate(60);
		}catch(SlickException e){
			
			System.err.println("there was an error within the program. Genie will now exit...");
			System.exit(1);
		}
		
		
	System.exit(0);
	}

 }

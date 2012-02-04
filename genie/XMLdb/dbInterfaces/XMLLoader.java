package genie.XMLdb.dbInterfaces;

import java.util.HashMap;
import genie.sprite.Sprite;

public interface XMLLoader {
	/* the method getSprites(XMLfile f) is supposed to scan an
	 * XMLfile and return a HashMap Containing all the Sprites
	 * as defined within. The Key for insertion is the same as the
	 * file's name excluding the extension.
	 */
	public abstract HashMap <String,Sprite> getSprites(XMLfile f);
	
	
	/* the method getAudio(XMLfile f) is supposed to scan an
	 * XMLfile and return a HashMap Containing all the Audio
	 * Clips (in ogg extensions) as defined within. The Key for insertion
	 * is the same as the file's name excluding the extension.
	 */
	public abstract HashMap	getAudio(XMLfile f);
	
	
	
	
	/* the method getTiles(XMLfile f) is supposed to scan an
	 * XMLfile and return a HashMap containing all the Tiles as
	 * defined within. The Key for insertion is the same as the file's
	 * name excluding the extension. 
	 */
	public abstract HashMap <String,Sprite> getTiles(XMLfile f);
	
}

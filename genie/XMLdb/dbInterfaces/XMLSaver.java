package genie.XMLdb.dbInterfaces;

import java.util.HashMap;
public interface XMLSaver {
	public abstract boolean saveSprites(HashMap H, XMLfile f);
	public abstract boolean saveAudio(HashMap H, XMLfile f);
	public abstract boolean saveTiles(HashMap H, XMLfile f);
}

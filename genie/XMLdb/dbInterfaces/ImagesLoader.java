package genie.XMLdb.dbInterfaces;

import genie.sprite.Sprite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ImagesLoader extends XMLReader {
	private HashMap<String, SpriteSheet> spriteSheetMap;
	private SpriteSheet tileSheet;
	/* TODO: Temporary variable for determining which of the spritesheets loaded is a tilesheet. 
	 * Need to find a standard way for getting tilesheets.
	 */
	
	private String path;  /* The path to the images folder related to the xml file*/
	

	public ImagesLoader(Document doc)
	{
		super(doc);
		path = "res/images/";
		System.out.println(path);
		spriteSheetMap = new HashMap<String, SpriteSheet>();
		
		try {
			loadImages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadImages() throws IOException
	{
		if(doc == null)
		{
			throw new NullPointerException("Document is null");
		}
		
		NodeList images = doc.getElementsByTagName("image");
		for(int i = 0; i < images.getLength(); i++)
		{
			Element image = (Element)images.item(i);
			NodeList nodes = image.getElementsByTagName("type");
			
			Element data = (Element)nodes.item(0);
			/*The first child must be the type of image, whether it is spritesheet or not */
			if(data.getTextContent().matches("tilesheet"))
			{
				/* Attempt to add an item to the spriteSheetMap */
				data = (Element)image.getElementsByTagName("filename").item(0); /* data is now the filename */
				String uri = data.getTextContent();
				System.out.println(data.getNodeName() + " textContent: " + uri);
				data = (Element)image.getElementsByTagName("width").item(0); /* data is now width */
				int width = Integer.parseInt(data.getTextContent());
				data = (Element)image.getElementsByTagName("height").item(0); /*data is now height */
				int height = Integer.parseInt(data.getTextContent());
				if(!getSpriteSheetMap().containsKey(uri))
				{
					try {
						spriteSheetMap.put(uri, new SpriteSheet(new Image(uri), width, height));
						/* TODO: this is just a temporary method of getting the tilesheet. 
						 * Here we are assuming that there is only one spritesheet of type
						 * tilesheet in the database
						 */
						tileSheet = spriteSheetMap.get(uri);
					} catch (SlickException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * Builds a list of Tile objects using the cells of the given SpriteSheet
	 * 
	 * @param SpriteSheet
	 * @return the ArrayList of Tiles
	 */
	public ArrayList<Tile> createTilePalette(SpriteSheet tileSheet)
	{
		int hCount = tileSheet.getHorizontalCount();
		int vCount = tileSheet.getVerticalCount();
		
		ArrayList<Tile> tilePalette = new ArrayList<Tile>();
		/* Create a tile for each image in the sprite sheet */
		for(int j = 0; j < vCount; j++)
		{
			for(int i = 0; i < hCount; i++)
			{
				Image image = tileSheet.getSprite(i, j);
				Sprite sprite = new Sprite(image, 0, 0); /* The sprite is created with coordinates 0,0 since it is only going to be used as a reference */
				String uri = tileSheet.getResourceReference();
				tilePalette.add(new Tile(sprite, uri, i, j));
			}
		}
		return tilePalette;
	}

	/**
	 * @param spriteSheetMap the spriteSheetMap to set
	 */
	public void setSpriteSheetMap(HashMap<String, SpriteSheet> spriteSheetMap) {
		this.spriteSheetMap = spriteSheetMap;
	}

	/**
	 * @return the spriteSheetMap
	 */
	public HashMap<String, SpriteSheet> getSpriteSheetMap() {
		return spriteSheetMap;
	}
	
	/**
	 * Get an image from a specified SpriteSheet.
	 * 
	 * @param uri
	 * @param x
	 * @param y
	 * @return
	 */
	public Image getImage(String uri, int x, int y)
	{
		SpriteSheet sheet = spriteSheetMap.get(uri);
		if(sheet == null)
		{
			System.out.println(uri + " is not a key in spriteSheetMap");
			return null;
		}
		return sheet.getSprite(x, y);
	}
	
	/**
	 * Get a SpriteSheet by its filename.
	 * @param String filename
	 * @return the queried SpriteSheet
	 */
	public SpriteSheet getSpriteSheet(String filename) {
		String path = this.path + filename;
		return spriteSheetMap.get(path);
	}
	
	/**
	 * This was meant to be a helper for building the right uri to query the
	 * SpriteSheetMap
	 * 
	 * @return the path used by this object to access images.
	 */
	public String getPath()
	{
		return this.path;
	}
	
	/* Get the working directory of the project 
	 * TODO: incomplete*/
	public String getWorkingDirectory(Document doc)
	{
		String path = doc.getDocumentURI();
		//remove the document filename from the path
		
		return path;
	}
}

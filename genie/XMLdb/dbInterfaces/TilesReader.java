package genie.XMLdb.dbInterfaces;

import genie.sprite.Sprite;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TilesReader extends XMLReader {
	private ImagesLoader imgLoader;
//	private String path = "/res/images/";
	
	public TilesReader(ImagesLoader loader)
	{
		imgLoader = loader;
	}
	public void readDocument(String fileName)
	{
		doc = XMLReader.createDocument(fileName);
	}
	
	/**
	 * @deprecated
	 * @param tilePoints
	 * @param tileImages
	 */
	public void readTileData(Collection<Point2D.Float> tilePoints, Collection<Image> tileImages)
	{
		NodeList tiles = doc.getElementsByTagName("tile");
		for(int i = 0; i < tiles.getLength(); i++)
		{
			HashMap<String, Image> imgMap = new HashMap<String, Image>();
			Element tile = (Element)tiles.item(i);
			
			/* Get point from file and add to tilePoints Collection */
			NodeList data = tile.getElementsByTagName("x_world");
			float x = Float.parseFloat(data.item(0).getTextContent());
			data = tile.getElementsByTagName("y_world");
			float y = Float.parseFloat(data.item(0).getTextContent());
			tilePoints.add(new Point2D.Float(x, y));
			
			/* Get filename for the tile's image and add an image object corresponding to this tile, to the tileImages Collection. */
			data = tile.getElementsByTagName("filename");
			String fileName = data.item(0).getTextContent();
			/* Ensure that we only create one image per filename since the same filename may appear several times */
			if(!imgMap.containsKey(fileName)) 
			{
				try {
					imgMap.put(fileName, new Image(fileName));
				}catch(SlickException e){ e.printStackTrace(); }
			}
			tileImages.add(imgMap.get(fileName));
		}
	}
	
	public void readTileData(ArrayList<Tile> tiles)
	{
		NodeList tileNodes = doc.getElementsByTagName("tile");
		for(int i = 0; i < tileNodes.getLength(); i++)
		{
			/* This HashMap guards against creating multiple SpriteSheets for the same file */
//			HashMap<String, Image> imgMap = new HashMap<String, Image>();
			Element tileElement = (Element)tileNodes.item(i);
			
			/* Get point from file and add to tilePoints Collection */
			NodeList data = tileElement.getElementsByTagName("x_world");
			float x = Float.parseFloat(data.item(0).getTextContent());
			data = tileElement.getElementsByTagName("y_world");
			float y = Float.parseFloat(data.item(0).getTextContent());
			data = tileElement.getElementsByTagName("filename");
			String uri = data.item(0).getTextContent();
			data = tileElement.getElementsByTagName("x_sheet");
			int xSheet = Integer.parseInt(data.item(0).getTextContent());
			data = tileElement.getElementsByTagName("y_sheet");
			int ySheet = Integer.parseInt(data.item(0).getTextContent());
			
			Sprite sprite = new Sprite(imgLoader.getImage(uri, xSheet, ySheet), x, y);
			
			
			
			
			tiles.add(new Tile(sprite, uri, xSheet, ySheet));
			
//			/* Get filename for the tile's sprite sheet, retrieve the x,y coordinate of the image cell on the sheet and create a Sprite*/
//			data = tileElement.getElementsByTagName("filename");
//			String fileName = data.item(0).getTextContent();
//			/* Ensure that we only create one image per filename since the same filename may appear several times */
//			if(!imgMap.containsKey(fileName)) 
//			{
//				try {
//					imgMap.put(fileName, new SpriteSheet(fileName, i, i));
//				}catch(SlickException e){ e.printStackTrace(); }
//			}
//			tileImages.add(imgMap.get(fileName));
		}
	}
}

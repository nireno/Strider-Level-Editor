package genie.XMLdb.dbInterfaces;

import genie.sprite.Sprite;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Tile implements Comparable<Tile>{
	Sprite sprite;
	String sheetURI;
	
	/* (x,y) coordinate of a cell on the SpriteSheet */
	int xSheet;
	int ySheet;
	
	public Tile(Sprite s, String URI, int xSheet, int ySheet)
	{
		sprite = s;
		sheetURI = URI;
		this.xSheet = xSheet;
		this.ySheet = ySheet;
	}
	
	public float getXWorld()
	{
		return sprite.getxWorld();
	}
	
	public float getYWorld()
	{
		return sprite.getyWorld();
	}
	
	public String getURI()
	{
		return sheetURI;
	}
	
	public int getxSheet() {
		return xSheet;
	}

	public int getySheet() {
		return ySheet;
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	public void setXWorld(float xWorld)
	{
		sprite.setxWorld(xWorld);
	}
	
	public void setYWorld(float yWorld)
	{
		sprite.setyWorld(yWorld);
	}
	/**
	 * A class method to convert a collection of Tiles to a Collection of Sprites
	 * @param Collection - Tile Generic
	 * @return a Collection of Sprite Generic
	 */
	public static Collection<Sprite> convertToSprites(Collection<Tile> tiles)
	{
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		Iterator<Tile> i = tiles.iterator();
		while(i.hasNext())
		{
			Tile tile = i.next();
			sprites.add(tile.getSprite());
		}
		return sprites;
	}
	
	/**
	 * A class method to convert a HashMap of Tiles to a Collection of Sprites
	 * @param Collection - Tile Generic
	 * @return a Collection of Sprite Generic
	 */
	public static Collection<Sprite> convertToSprites(HashMap<Point2D.Float, Tile> tileMap)
	{
		Collection<Tile> tiles = tileMap.values();
		return convertToSprites(tiles);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Tile)
		{
			Tile t = (Tile)obj;
			Sprite sprite = t.getSprite();

			if(sprite.equals(this.getSprite()))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public int compareTo(Tile tile) {
		if(this.equals(tile))
		{
			return 0; 
		}
		return -1;
	}
	
	@Override
	public String toString() {
		String result = "World coordinates: ";
		result += "(" + sprite.getxWorld() + ", " + sprite.getyWorld() + ")";
		return result;
	}
	
	/*Why we copying again!*/
	public Tile createCopy()
	{
		return new Tile(sprite.createCopy(), sheetURI, xSheet, ySheet);
	}
}

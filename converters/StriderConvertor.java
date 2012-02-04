package converters;

import genie.XMLdb.dbInterfaces.Tile;
import genie.sprite.Sprite;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Convert Midiori level data into the text format that can be read by Davison's
 * bricksManager
 * 
 * @author nitro
 * 
 */
public abstract class StriderConvertor {
	
	
	public static char[][] convert(ArrayList<Tile> tiles)
	{
		ArrayList<Tile> newTiles = copyTiles(tiles);
		normalizeTiles(newTiles);
		
		char[][] charData = createCharMap(newTiles);
		System.out.println("charData.length: "+ charData.length);
		System.out.println("charData[0].length: "+ charData[0].length);
		
		for(int j = 0; j < charData[0].length; j++)
		{
			for(int i = 0; i < charData.length; i++)
			{
				System.out.print(charData[i][j]);
			}
			System.out.println();
		}
		
		
		return charData;
	}
	
	private static ArrayList<Tile> copyTiles(ArrayList<Tile> tiles)
	{
		/* Make a copy of the tiles leaving the original intact */
		ArrayList<Tile> newTiles = new ArrayList<Tile>();
		for(int i = 0; i < tiles.size(); i++)
		{
			newTiles.add(tiles.get(i).createCopy());
		}
		return newTiles;
	}
	
	/**
	 * Expects normalized Tile data. i.e. all tile coordinates should be in the
	 * positive axes.
	 */
	private void initCharData()
	{
		
	}
	
	private static void normalizeTiles(ArrayList<Tile> tiles)
	{
		/*
		 * Find the leftMost and topMost tile. These will have the largest
		 * negative values in the x and y coordinates respectively. Find the
		 * offset that will push these tiles into non-negative coordinates and
		 * apply this offset to all tiles in the list.
		 */
		float xOffset = Float.MAX_VALUE;
		float yOffset = Float.MAX_VALUE;
		for(int i = 0; i < tiles.size(); i++)
		{
			Sprite sprite = tiles.get(i).getSprite();
			float x = sprite.getxWorld();
			float y = sprite.getyWorld();
			
			if(x < xOffset) xOffset = x;
			if(y < yOffset) yOffset = y;
		}
		
		/* ensure offsets are positive */
		xOffset *= -1;
		yOffset *= -1;
		
		for(int i = 0; i < tiles.size(); i++)
		{
			Sprite sprite = tiles.get(i).getSprite();
			sprite.setxWorld(sprite.getxWorld() + xOffset);
			sprite.setyWorld(sprite.getyWorld() + yOffset);
		}
		/* Tiles are now normalized to the positive x-axis and y-axis */
	}
	
	/**
	 * This method 
	 * This method expects that the given tile list has no tile in negative
	 * coordinates.
	 * 
	 * @param normalizedTiles
	 * @return
	 */
	private static Point2D.Float getMapSize(int tileWidth, int tileHeight, ArrayList<Tile> normalizedTiles)
	{
		float xMax = Float.MIN_VALUE;
		float yMax = Float.MIN_VALUE;
		for(int i = 0; i < normalizedTiles.size(); i++)
		{
			Sprite sprite = normalizedTiles.get(i).getSprite();
			float x = sprite.getxWorld();
			float y = sprite.getyWorld();
			if(x > xMax) xMax = x;
			if(y > yMax) yMax = y;
		}
		
		Point2D.Float point = new Point2D.Float(xMax, yMax);
		point.x += tileWidth;
		point.y += tileHeight;
		return point;
	}
	
	private static char[][] createCharMap(ArrayList<Tile> tiles)
	{
		int tileWidth = tiles.get(0).getSprite().getImage().getWidth();
		int tileHeight = tiles.get(0).getSprite().getImage().getHeight();
		
		Point2D.Float mapSize = getMapSize(tileWidth, tileHeight, tiles);
		
		int horizontalTileCount = (int) (mapSize.x/tileWidth);
		int verticalTileCount = (int) (mapSize.y/tileHeight);
		char[][] charData = new char[horizontalTileCount][verticalTileCount];
		
		for(int i = 0; i < verticalTileCount; i++)
		{
			for(int j = 0; j < horizontalTileCount; j++)
			{
				charData[j][i] = ' ';
			}
		}
		
		for(int i = 0; i < tiles.size(); i++)
		{
			Point point = getMapInsertPoint(tileWidth, tileHeight,tiles.get(i));
			charData[point.x][point.y] = Integer.toString(tiles.get(i).getxSheet()).charAt(0);
//			charData[point.x][point.y] = 'p';
			System.out.println("Map insert point: "+ point.x + " " + point.y);
		}
		return charData;
	}
	
	private static Point getMapInsertPoint(int tileWidth, int tileHeight, Tile tile)
	{
		Sprite sprite = tile.getSprite();
		int xWorld = (int)sprite.getxWorld();
		int yWorld = (int)sprite.getyWorld();
		
		int x = xWorld/tileWidth;
		int y = yWorld/tileHeight;
		
		return new Point(x,y);
	}
	
}

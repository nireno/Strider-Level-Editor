package genie.XMLdb.dbInterfaces;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.newdawn.slick.Image;
import org.w3c.dom.Document;

import converters.StriderConvertor;

/**
 * Data structure storing all the data for a level. It controls the creation of
 * the XML Document object which is used to load the necessary data from an XML
 * file. It is later used to output the data in XML format.
 */
public class LevelData {
	
	private ArrayList<Tile> tiles; /* A collection of all the tiles placed in the level */
	private ImagesLoader imgLoader;
	private String filePath; /* Path of the xml file to be loaded using the TilesReader */
	private TilesReader reader;
	private TilesWriter writer;
	
	private String fileName;
	
	public LevelData(String filePath)
	{
		this.filePath = filePath;
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init() throws Exception
	{
		Document doc = XMLReader.createDocument(filePath);
		if(doc == null) /* then the file specified was an invalid Genie xml. */
		{
			throw(new Exception());
		}
		tiles = new ArrayList<Tile>();
		imgLoader = new ImagesLoader(XMLReader.createDocument(filePath));
		
		reader = new TilesReader(imgLoader);
		reader.readDocument(filePath);
		reader.readTileData(tiles);
		writer = new TilesWriter(reader.getDocument());
		
	}
	
	/*
	 * The TileManager can initialize its tilemap by getting the list of images
	 * and points.
	 */
	public Collection<Point2D.Float> getTilePoints()
	{
		/* Build a list of tile points from Tile data */
		ArrayList<Point2D.Float> tilePoints = new ArrayList<Point2D.Float>();
		for(int i = 0; i < tiles.size(); i++)
		{
			Tile tile = tiles.get(i);
			float x = tile.getXWorld();
			float y = tile.getYWorld();
			Point2D.Float point = new Point2D.Float(x, y);
			tilePoints.add(point);
		}
		return tilePoints;
	}
	
	public Collection<Image> getTileImages()
	{
		/* Build a list of tile images from Tile data */
		ArrayList<Image> tileImages = new ArrayList<Image>();
		for(int i = 0; i < tiles.size(); i++)
		{
			Tile tile = tiles.get(i);
			tileImages.add(tile.getSprite().getImage());
		}
		return tileImages;
	}
	
	public void setTilePoints(Collection<Point2D.Float> tilePoints)
	{
		
	}
	
	public void setTileImages(Collection<Image> tileImages)
	{
		
	}
	
	public ImagesLoader getImagesLoader()
	{
		return imgLoader;
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}
	
	public void addTile(Tile t)
	{
		tiles.add(t);
	}
	
	public void removeTile(Tile t)
	{
		if(tiles.remove(t))
			System.out.println("tile removed");
		else
			System.out.println("no tile removed");
	}
	
	public void setTiles(ArrayList<Tile> tiles)
	{
		this.tiles = tiles;
	}
	
	/**
	 * 
	 * This method updates the information in the Document before writing it to
	 * the XML file
	 */
	public void saveData()
	{
		writer.updateTiles(tiles);
		writer.writeXML(this.reader.getDocument(), this.filePath);
	}
	public void saveData(String path)
	{
		this.filePath = path;
		saveData();
	}
	
	public void saveAsStriderLevel()
	{
		char[][] data = StriderConvertor.convert(tiles);
		try{
			File file = new File("res/stage1.txt");
			System.out.println(file.getAbsolutePath());
			FileWriter out = new FileWriter(file);
			out.write("s tiles.png 11\n");
			
			for(int j = 0; j < data[0].length; j++)
			{
				for(int i = 0; i < data.length; i++)
				{
					out.write(data[i][j]);
				}
				out.write("\n");
			}
			
			out.close();
		}catch(IOException e){e.printStackTrace();}
	}
	
	public void setFilePath(String path)
	{
		filePath = path;
	}

	public ImagesLoader getImgLoader() {
		return imgLoader;
	}

	public void setImgLoader(ImagesLoader imgLoader) {
		this.imgLoader = imgLoader;
	}
	
}

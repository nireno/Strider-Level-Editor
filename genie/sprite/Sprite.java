package genie.sprite;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.newdawn.slick.Image;

public class Sprite implements Comparable{
	private Image image = null;
	private float xWorld = 0f;
	private float yWorld = 0f;
	
	public Sprite(Image image, float xWorld, float yWorld)
	{
		this.image = image;
		this.xWorld = xWorld;
		this.yWorld = yWorld;
	}
	
	public Sprite(Image image, String imgName, float xWorld, float yWorld)
	{
		this.image = image;
		image.setName(imgName);
		this.xWorld = xWorld;
		this.yWorld = yWorld;
	}
	
	public void draw(float x, float y)
	{
		image.draw(x, y);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public float getxWorld() {
		return xWorld;
	}

	public void setxWorld(float xWorld) {
		this.xWorld = xWorld;
	}

	public float getyWorld() {
		return yWorld;
	}

	public void setyWorld(float yWorld) {
		this.yWorld = yWorld;
	}
	
	/*
	 * This method provides a simple procedure for building an array of sprites
	 * given an appropriate set of Images and coordinates
	 */
	public static ArrayList<Sprite> createSpriteList(ArrayList<Image> spriteImages, 
										  ArrayList<Point2D.Float> spritePoints)
	{
		if(spriteImages.size() != spritePoints.size())
		{
			System.err.println("createSpriteList() Error: Lists of different length.");
			return null;
		}
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		for(int i = 0; i < spriteImages.size(); i++)
		{
			sprites.add(new Sprite(spriteImages.get(i), spritePoints.get(i).x, spritePoints.get(i).y));
		}
		return sprites;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Sprite)
		{
			Sprite s = (Sprite)obj;
			if(s.image == this.image)
			{
				if(s.xWorld == this.xWorld &&
				   s.yWorld == this.yWorld)
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int compareTo(Object obj) {
		if(obj instanceof Sprite)
		{
			Sprite sprite = (Sprite)obj;
			if(sprite.equals(this))
				return 0;
		}
		return -1;
	}

	public Sprite createCopy() {
		return new Sprite(image, xWorld, yWorld);
	}
}

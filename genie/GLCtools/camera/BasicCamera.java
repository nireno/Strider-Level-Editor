package genie.GLCtools.camera;

import genie.sprite.Sprite;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * Motivation: Need to be able to move images around the screen as if it were
 * being recorded by a Camera
 */
public class BasicCamera {
	private ArrayList<Sprite> sprites;
	float xWorld = 0;
	float yWorld = 0;

	float xVel = 0;
	float yVel = 0;
	float xMoveSize = 5.0f;
	float yMoveSize = 5.0f;
	
	/*Resolution of the camera */
	float width; 
	float height;
	
	/* rightEdge - rightmost bound of the camera in world coordinates
	/* bottomEdge - lower bound of the camera in world coordinates
	 * These are helpful in determining intersections with sprites. 
	 */
	/*TODO Must be updated in the update loop. */
	float rightEdge = 0; 
	float bottomEdge = 0;
	
	/*Location data for printing camera information */
	static final float infoX = 0;
	static final float infoY = 50;
	boolean showInfo = false;
	
	public BasicCamera()
	{
		this.width = 640;
		this.height = 480;
		init();
	}
	public BasicCamera(int width, int height)
	{
		this.width = width;
		this.height = height;
		init();
	}
	
	public BasicCamera(int width, int height, ArrayList<Sprite> sprites)
	{
		this.width = width;
		this.height = height;
		this.sprites = sprites;
		init();
		
	}
	
	public void init()
	{
		rightEdge = xWorld + width;
		bottomEdge = yWorld + height;

	}
	
	public void render(GameContainer gc, Graphics g)
	{
		for(int i = 0; i < sprites.size(); i++)
		{
			if(this.intersects(sprites.get(i)))
			{
				drawSprite(sprites.get(i));
			}
		}
		Graphics graphics = gc.getGraphics();
		
		if(showInfo){
			
			graphics.drawString(""+this.getxWorld(), infoX, infoY);
			System.out.println("Camera World Coordinates: (" + xWorld + ", " + yWorld + ")");
	    }
		
	}
	
	public void update(GameContainer gc, int delta)
	{
		Input input = gc.getInput();
		if(input.isKeyDown(Input.KEY_UP))
		{
			yWorld -= yMoveSize;
//			if(yWorld <= 0)
//				yWorld = 0;
		}
		if(input.isKeyDown(Input.KEY_DOWN))
		{
			yWorld += yMoveSize;
		}
		if(input.isKeyDown(Input.KEY_LEFT))
		{
			xWorld -= xMoveSize;
//			if(xWorld <= 0)
//				xWorld = 0;
		}
		if(input.isKeyDown(Input.KEY_RIGHT))
		{
			xWorld += xMoveSize;
		}
		
		rightEdge = xWorld + width;
		bottomEdge = yWorld + height;
		
	}
	
	public boolean intersects(Sprite sprite)
	{
		float spriteBottomEdge =sprite.getyWorld() + sprite.getImage().getHeight();
		float spriteRightEdge = sprite.getxWorld() + sprite.getImage().getWidth();

		/* Using the Separating Axis Theorem (SAT)*/ 
	    if (sprite.getyWorld() > bottomEdge) return false;
	    if (spriteBottomEdge < yWorld) return false;

	    if (sprite.getxWorld() > rightEdge) return false;
	    if (spriteRightEdge < xWorld) return false;

		return true;
	}
	
	public void drawSprite(Sprite sprite)
	{
		sprite.draw(sprite.getxWorld() - xWorld, sprite.getyWorld() - yWorld);
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
	
	public void addSprite(Sprite s)
	{
		this.sprites.add(s);
	}
	
	public void removeSprite(Sprite s)
	{
		this.sprites.remove(s);
	}
	
	public void setSprites(ArrayList<Sprite> sprites)
	{
		this.sprites = sprites;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
}

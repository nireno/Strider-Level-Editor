package genie.GLCtools.camera;

import genie.sprite.Sprite;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class GridCamera extends BasicCamera {

	float gridWidth = 0;
	float gridHeight = 0;
	
	private static final boolean showDebugInfo = false;
	public GridCamera()
	{
		super();
		this.gridWidth = 10;
		this.gridHeight = 10;
	}
	public GridCamera(int width, int height, float gridWidth, float gridHeight)
	{
		super(width, height);
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
	}
	
	public GridCamera(int width, int height, ArrayList<Sprite> sprites, float gridWidth, float gridHeight) {
		super(width, height, sprites);
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		// TODO Auto-generated constructor stub
		
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		
		drawGrid(gc, g);
		
		if(showDebugInfo){
			StringBuilder b = new StringBuilder(""+this.getxWorld());
			g.drawString(b.toString(), infoX, infoY);
			System.out.println("Camera World Coordinates: (" + xWorld + ", " + yWorld + ")");
	    }
		
		super.render(gc, g);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		// TODO Auto-generated method stub
		super.update(gc, delta);
	}
	
	private void drawGrid(GameContainer gc, Graphics g)
	{
		g.setColor(Color.darkGray);
		/* calculate yScreen coordinate of first horizontal gridline */
		float gridStart = ((int)(yWorld/gridHeight))*gridHeight; /* World Coordinate */
		gridStart = gridStart - yWorld;
		
				
		for(float i = gridStart; i <= this.height; i += gridHeight)
		{
			g.drawLine(0, i, width, i);
		}
		
		gridStart = ((int)(xWorld/gridWidth))*gridWidth; /* World Coordinate */
		gridStart = gridStart - xWorld;
		
				
		for(float i = gridStart; i <= this.width; i += gridWidth)
		{
			g.drawLine(i, 0, i, height);
		}
	}
	
	public float getGridHeight()
	{
		return this.gridHeight;
	}
	
	public float getGridWidth()
	{
		return this.gridWidth;
	}
	
	public static float getGridSnapY(float yWorld, float gridHeight)
	{
		int snapY = ((int)(yWorld/gridHeight));
		if (yWorld < 0) snapY--;
		yWorld = snapY * gridHeight;
		return yWorld;
	}
	
	public static float getGridSnapX(float xWorld, float gridWidth)
	{
		int snapX = ((int)(xWorld/gridWidth));
		if(xWorld < 0) snapX--;
		xWorld = snapX * gridWidth;
		return xWorld;
	}
	public void setGridWidth(float gridWidth) {
		this.gridWidth = gridWidth;
	}
	public void setGridHeight(float gridHeight) {
		this.gridHeight = gridHeight;
	}
}

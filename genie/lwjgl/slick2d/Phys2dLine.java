package genie.lwjgl.slick2d;

import net.phys2d.math.ROVector2f;
import net.phys2d.raw.shapes.Line;

public class Phys2dLine extends Line {

	public Phys2dLine(float x, float y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	public Phys2dLine(ROVector2f start, ROVector2f end) {
		super(start, end);
		// TODO Auto-generated constructor stub
	}

	public Phys2dLine(float x, float y, boolean inner, boolean outer) {
		super(x, y, inner, outer);
		// TODO Auto-generated constructor stub
	}

	public Phys2dLine(float x1, float y1, float x2, float y2) {
		super(x1, y1, x2, y2);
		// TODO Auto-generated constructor stub
	}

}

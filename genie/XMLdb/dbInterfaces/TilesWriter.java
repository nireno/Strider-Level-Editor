package genie.XMLdb.dbInterfaces;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.newdawn.slick.Image;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author nitro The TileWriter is responsibe for writing a given set of tile
 *         information to an XML file with the given filename. The writer
 *         expects that the supplied Document is already formatted with a
 *         <tiles> tag.
 */
public class TilesWriter extends XMLWriter {
	private Document doc;
	private static final String error = "Error: Failed to write tile data.";

	/*
	 * The doc passed at this stage should be a pre-formatted document that
	 * already includes the <tiles> tag
	 */
	public TilesWriter(Document doc) {
		this.doc = doc;
		// this.filename = filename;
	}

	/**
	 * @deprecated This method will overwrite any previous tiles that were
	 *             stored in the document. TODO: Perhaps overwriting should be
	 *             made an option, not a default.
	 */
	public void updateTiles(Collection<Point2D.Float> tilePoints,
			Collection<Image> tileImages) {
		if (tilePoints.size() != tileImages.size()) {
			System.err.println(error);
			System.err
					.println("Cause: Unequal amounts of tilePoints and images.");
			return;
		}

		/* Get the <tiles> node */
		Node tilesNode = getTilesNode();

		/*
		 * If there are no nodes with the tag "tiles" then this is an invalid
		 * document.
		 */
		if (tilesNode == null || !tilesNode.getNodeName().matches("tiles")) {
			System.err.println(error);
			System.err
					.println("Cause: Invalid document supplied. No \"tiles\" tag found in document.");
			return;
		}

		/*
		 * Remove all the previous tiles under the given "tiles" node before
		 * inserting the given data.
		 */
		Node parent = tilesNode.getParentNode();
		parent.removeChild(tilesNode);

		Iterator<Float> iteratePoints = tilePoints.iterator();
		Iterator<Image> iterateImages = tileImages.iterator();

		for (int i = 0; i < tilePoints.size(); i++) {
			Node tile = createTileNode((Point2D.Float) iteratePoints.next(),
					(Image) iterateImages.next());
			tilesNode.appendChild(tile);
		}
	}

	/*
	 * This method removes all tiles from the Document and replaces them with
	 * those supplied in the given ArrayList. Note that this does not actually
	 * write data to the xml file.
	 */
	public void updateTiles(ArrayList<Tile> tiles) {
		/* Get the <tiles> node */
		Node tilesNode = getTilesNode();

		/*
		 * If there are no nodes with the tag "tiles" then this is an invalid
		 * document.
		 */
		if (tilesNode == null || !tilesNode.getNodeName().matches("tiles")) {
			System.err.println(error);
			System.err
					.println("Cause: Invalid document supplied. No \"tiles\" tag found in document.");
			return;
		}

		/* Remove all previous tile nodes from the document. */
		Node parent = tilesNode.getParentNode();
		parent.removeChild(tilesNode);
		/*
		 * Convert the tile data into xml data and inject it into the document
		 * under the tiles tag
		 */
		Node newTilesNode = doc.createElement("tiles");
		System.out.println("nodename"+newTilesNode.getNodeName());
		System.out.println("size " + tiles.size());
		for (int i = 0; i < tiles.size(); i++) {
			System.out.println("Adding " + tiles.get(i));
			Node tile = createTileNode(tiles.get(i));
			newTilesNode.appendChild(tile);
		}
		parent.appendChild(newTilesNode);
	}

	/*
	 * Returns the "tiles" node from the TileWriter's Document. If none is found
	 * then null is returned
	 */
	private Node getTilesNode() {
		NodeList nodes = doc.getElementsByTagName("tiles");
		/*
		 * If there are no nodes with the tag "tiles" then this is an invalid
		 * document
		 */
		if (nodes.getLength() == 0) {
			System.out.println("No tile data in document");
			return null;
		}
		return nodes.item(0);
	}

	/**
	 * @deprecated
	 * @param point
	 * @param image
	 * @return
	 */
	private Node createTileNode(Point2D.Float point, Image image) {
		Element tile = doc.createElement("tile");

		Element data = doc.createElement("x_world");
		data.setTextContent(String.valueOf(point.x));
		tile.appendChild(data);

		data = doc.createElement("y_world");
		data.setTextContent(String.valueOf(point.y));
		tile.appendChild(data);

		data = doc.createElement("filename");
		data.setTextContent(image.getResourceReference());
		tile.appendChild(data);

		return tile;
		// Node textNode = doc.createTextNode(String.valueOf(point.x));
		// data.appendChild(textNode); /*TODO Test if data.setTextContent() will
		// suffice instead of appending a new text node */
	}

	private Node createTileNode(Tile tile) {
		Node tileNode = doc.createElement("tile");

		Node data = doc.createElement("x_world");
		data.setTextContent(String.valueOf(tile.getXWorld()));
		tileNode.appendChild(data);

		data = doc.createElement("y_world");
		data.setTextContent(String.valueOf(tile.getYWorld()));
		tileNode.appendChild(data);

		data = doc.createElement("filename");
		data.setTextContent(tile.getSprite().getImage().getResourceReference());
		tileNode.appendChild(data);

		data = doc.createElement("x_sheet");
		data.setTextContent(String.valueOf(tile.getxSheet()));
		tileNode.appendChild(data);

		data = doc.createElement("y_sheet");
		data.setTextContent(String.valueOf(tile.getySheet()));
		tileNode.appendChild(data);

		return tileNode;
	}

	public Document getDocument() {
		return doc;
	}
}

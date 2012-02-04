package genie.XMLdb.dbInterfaces;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * 
 * @author nitro
 * This abstract class provides a base for creating a Java Document from an
 * XML file.
 */
public abstract class XMLReader {
	protected Document doc;
	
	public XMLReader()
	{
		doc = null;
	}
	
	public XMLReader(Document doc)
	{
		this.doc = doc;
	}
	
	public static Document createDocument(String filePath)
	{
		Document doc = null;
		/* Use a document builder to parse the xml file into a document*/
		try 
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = docFactory.newDocumentBuilder();
			doc = parser.parse(filePath);
		} 
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (SAXException e)
		{
			e.printStackTrace();
		}
		
		if(verifyDocument(doc))return doc;
		
		return null;
	}
	
	/**
	 * Test to determine whether the XML file has the structure required by the
	 * editor. Two tests are performed: ensure the root has the required tag
	 * name "midiori_level" and the root contains a tiles tag.
	 * @param Document
	 * @return boolean
	 */
	public static boolean verifyDocument(Document doc)
	{
		Node root = doc.getFirstChild();
		if(!root.getNodeName().equals("midiori_level")) return false;
		
		NodeList result = doc.getElementsByTagName("tiles");
		if(result.getLength() == 0) return false;
		Node tilesNode = result.item(0);
		if(tilesNode.getParentNode()!= root) return false;
		
		return true;
	}
	public static boolean verifyDocument(String path)
	{
		Document doc = XMLReader.createDocument(path);
		return verifyDocument(doc);
	}
	
	public Document getDocument()
	{
		return doc;
	}
}

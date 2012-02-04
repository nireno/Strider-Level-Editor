package genie.XMLdb.dbInterfaces;

import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public abstract class XMLWriter {
	public void writeXML(Document doc, String filename)
	{
		try{
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			File file = new File(filename);
			Result result = new StreamResult(file);
			Source source = new DOMSource(doc);
			transformer.transform(source, result);
		}
		catch(TransformerConfigurationException e){ e.printStackTrace(); }
		catch(TransformerException e) {e.printStackTrace(); }
	}
}

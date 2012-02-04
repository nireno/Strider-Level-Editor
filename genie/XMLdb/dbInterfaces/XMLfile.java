package genie.XMLdb.dbInterfaces;

import java.io.File;
import java.net.URI;

public class XMLfile extends File {
	private static final long serialVersionUID = 1L;
	
	public XMLfile(File parent, String child){
		super(parent, child);
	}
	public XMLfile(String pathName){
		super(pathName);
	}
	public XMLfile(String parent, String child){
		super(parent, child);
	}
	public XMLfile(URI url){
		super(url);
	}
}

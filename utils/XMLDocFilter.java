package utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class XMLDocFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		if(file.isDirectory())
			return true;
		
		String ext = FileUtils.getExtension(file);
		if(!ext.equals(FileUtils.xml))
			return false;
		
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "XML files";
	}

}

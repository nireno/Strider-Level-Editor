package utils;
import java.io.*;

public class FileUtils{
	public static final String xml = "xml";
  public static void copyFile(File in, File out) throws IOException {
    FileInputStream fis  = new FileInputStream(in);
    FileOutputStream fos = new FileOutputStream(out);
    try {
        byte[] buf = new byte[1024];
        int i = 0;
        while ((i = fis.read(buf)) != -1) {
            fos.write(buf, 0, i);
        }
    } 
    catch (IOException e) {
        e.printStackTrace();
    }
    finally {
        if (fis != null) fis.close();
        if (fos != null) fos.close();
    }
  }
  
	/**
	 * Extracts the extension from a given file. Note that this method will
	 * never return a null pointer.
	 * 
	 * @param file
	 * @return
	 */
  public static String getExtension(File file)
  {
	  String name = file.getName();
	  int lastIndex = name.lastIndexOf('.');
	  String ext = "";
	  for(int i = lastIndex+1; i < name.length(); i++)
	  {
		  ext = ext + name.charAt(i);
	  }
	  return ext;
  }
}

package tools;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class Files extends File{
	
	public static final boolean REWRITE = false;
	public static final boolean ADD = true;

	public Files(String child){ super(child); }
	
	public static String loadPath(String name) {
		return System.getProperty("user.dir").toString() + "\\" + name;
	}
	
	public static File loadFile(String name) {
		File file = new File(loadPath(name));
		if(!file.exists()){ 
			try{ file.createNewFile();
			}catch(Exception e){ e.printStackTrace(); } 
		}
		return file;
	}
	
	public static BufferedImage loadBufferedImage(String name) {
		try{
			return ImageIO.read(loadFile(name));
		}catch(Exception e){ e.printStackTrace(); }
		return null;
	}
	
	public static FileReader loadFileReader(String name) {
		try{
			return new FileReader(loadPath(name));
		}catch(Exception e){ e.printStackTrace(); }
		return null;
	}
	
	public static FileWriter loadFileWriter(String name) {
		return loadFileWriter(name, true);
	}
	
	public static FileWriter loadFileWriter(String name, boolean b) {
		try{
			return new FileWriter(loadPath(name), b);
		}catch(Exception e){ e.printStackTrace(); }
		return null;
	}
	
	public static BufferedReader loadBufferedReader(String name) {
		try{
			return new BufferedReader(loadFileReader(name));
		}catch(Exception e){ e.printStackTrace(); }
		return null;
	}
	
	public static BufferedWriter loadBufferedWriter(String name) {
		return loadBufferedWriter(name, true);
	}
	
	public static BufferedWriter loadBufferedWriter(String name, boolean b) {
		try{
			return new BufferedWriter(loadFileWriter(name, b));
		}catch(Exception e){ e.printStackTrace(); }
		return null;
	}
	
	public static Scanner loadScanner(String name) {
		try{
			return new Scanner(loadFile(name));
		}catch(Exception e){ e.printStackTrace(); }
		return null;
	}
}

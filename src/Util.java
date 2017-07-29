import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*************************************************************************************
 * Misc static methods that are used by the model and could be reused for other
 * projects. Includes:
 * 
 * void 	downloadFromPage(String pageURL, String folderDestination)
 * String 	findBlogTitle(String url)
 * String 	fixFolderName(String folderName)
 * boolean 	mkdirs(String path)
 * void 	pull(String imageURL, String destination)
 * boolean 	validateURL(String url)
 * 
 * @author isw4
 *
 */
public class Util {

	//downloads all the images in a tistory blog post
	public static void downloadFromPage(String pageURL, String folderDestination) throws IOException {
		assert(folderDestination.charAt(folderDestination.length() - 1) == '\\');
		
		System.out.println("Downloading from " + pageURL + "...");
		
		//getting page
		Document doc = Jsoup.connect(pageURL).get();
		
		//selects all img elements
		Elements allImages = doc.select("img");
		
		//filtering all images for those with src that contains cfile
		Elements wantedImages = new Elements();
		for(Element item : allImages) {
			if (item.attr("src").contains("cfile")) {
				wantedImages.add(item);
			}
		}
		
		//downloading all wanted images
		int m = 0;
		String n = String.valueOf(wantedImages.size());
		String source, filename, finalDest;
		for(Element item : wantedImages) {
			//replaces "image" by "original" since that is usually a better resolution picture
			source = item.attr("src");
			source = source.replace("image", "original");
			
			//forms destination with the filename of the image
			filename = item.attr("filename");
			if (filename.isEmpty()) filename = String.valueOf(m+1) + ".jpg";
			finalDest = folderDestination + filename;
			System.out.println("Downloading " + String.valueOf(++m) + "/" + n + "...");
			
			//downloads single image
			pull(source, finalDest);
		}
	}
	
	//finds the blog title in a tistory blog page
	public static String findBlogTitle(String url) throws IOException {
		//getting page
		Document doc = Jsoup.connect(url).get();
		
		//selecting h2 > a, which is usually the title of the post
		return doc.select("h2 > a").text();
	}
	
	//when appending the title as a folder nameto a destination path, it 
	//may contain invalid char. this removes all invalid char(for Windows)
	public static String fixFolderName(String folderName) {
		folderName = folderName.replace("<", "");
		folderName = folderName.replace(">", "");
		folderName = folderName.replace(":", "");
		folderName = folderName.replace("\"", "");
		folderName = folderName.replace("/", "");
		folderName = folderName.replace("\\", "");
		folderName = folderName.replace("|", "");
		folderName = folderName.replace("?", "");
		folderName = folderName.replace("*", "");
		
		return folderName;
	}
		
	//makes directories from the path given, but returns true if the
	//dir is created or if it already exists. returns false if some other
	//error occurs
	public static boolean mkdirs(String path) {
		File dir = new File(path);
		if (dir.exists()) return true;
		else return dir.mkdirs();
	}
	
	//downloads a single image from src
	private static void pull(String imageURL, String destination) {
		//declaring input and output streams
		InputStream iStream = null;
		OutputStream oStream = null;
		
		//tries to download
		try {
			//tries to open a connection to the url
			URL url = new URL(imageURL);
			iStream = url.openStream();
			
			//tries to open a connection to the destination
			oStream = new FileOutputStream(destination);
			
			//writes the data from the input to the output
			byte[] b = new byte[2048];
			int length;
			while((length = iStream.read(b)) != -1) {
				oStream.write(b, 0, length);
			}
			
			System.out.println("...download successful");
		}
		
		catch(MalformedURLException e) {
			System.out.println("Unable to parse properly: " + imageURL);
			System.out.println(e.getMessage());
		}
		catch(FileNotFoundException e) {
			System.out.println("Unable to find path: " + destination);
			System.out.println(e.getMessage());
		}
		catch(IOException e) {
			System.out.println("Unable to download properly from: " + imageURL +
							   "\nto: " + destination);
			System.out.println(e.getMessage());
		}
		
		//closes the streams
		finally {
			try {
				iStream.close();
				oStream.close();
			}
			catch(IOException e) {
				System.out.println("Error closing stream properly from: " + imageURL +
								   "\nto: " + destination);
				System.out.println(e.getMessage());
			}
		}
	}
	
	//checks if the url given is valid
	public static boolean validateURL(String url) {
		UrlValidator validator = new UrlValidator();
		return validator.isValid(url);
	}

}

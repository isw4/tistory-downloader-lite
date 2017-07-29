import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*************************************************************************************
 * Main class for the tistory-downloader-lite. 
 * 
 * @author isw4
 *
 */
public class MainModel {
	private String downloadFolder;
	
	/**********************************************************************************
	 * Constructor
	 *********************************************************************************/
	public MainModel() {
		//make config directory and file if it does not already exist
		try {
			mkRes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//get the default destination stored in the config file
		downloadFolder = getDefaultDest();
	}
	
	/**********************************************************************************
	 * Methods
	 *********************************************************************************/
	public String run(String url, String downloadFolder, String folderOption) {
		//checking validity of url provided
		boolean valid = Util.validateURL(url);
		if (!valid) return "Invalid URL";
		
		//checking the validity of the path provided, and making it if it does not exist
		valid = Util.mkdirs(downloadFolder);
		if (!valid) return "Invalid download destination";
		
		
		if (downloadFolder.charAt(downloadFolder.length()-1) != '\\') 
			downloadFolder = downloadFolder + "\\";
		
		//confirming the path based on the options provided
		if (folderOption.equals("Generate")) {
			try {
				downloadFolder = generateFolderDest(url, downloadFolder);
				Util.mkdirs(downloadFolder);
			} catch (IOException e) {
				e.printStackTrace();
				return "Invalid URL";
			}
		}
		else if (!folderOption.equals("Direct")) {
			return "Invalid folder option";
		}
		
		//downloading from the page
		try {
			Util.downloadFromPage(url, downloadFolder);
		} catch (IOException e) {
			e.printStackTrace();
			return "Unable to download from page";
		}
		
		System.out.println("Download from " + url + " complete");
		return "Download from " + url + " complete";
	}
	
	//generate new folder path with the title of the blog as the terminal folder's name
	private String generateFolderDest(String url, String downloadFolder) throws IOException {
		//find blog title
		String title = Util.findBlogTitle(url);
		
		//fixing invalid char in title
		title = Util.fixFolderName(title);
		
		//adding to path
		return downloadFolder + title + "\\";
	}
	
	private void mkRes() throws IOException {
		File dir = new File("res\\");
		dir.mkdir();
		File f = new File("res\\defaultdest.txt");
		f.createNewFile();
	}
	
	//getting the default destination from resource
	private String getDefaultDest() {
		String defaultDest = "";
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(new File("res\\defaultdest.txt")));
			defaultDest = br.readLine();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return defaultDest;
	}
	
	//getting the destination to display to the view
	public String getDest() {
		return downloadFolder;
	}
	
	//setting the default destination to resource
	public String setDefaultDest(String defaultDest) {
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter(new File("res\\defaultdest.txt")));
			bw.write(defaultDest);
			bw.flush();
			bw.close();
			return "Destination saved";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "Unable to save destination";
	}
}

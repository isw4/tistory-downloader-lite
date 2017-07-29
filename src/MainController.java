import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*************************************************************************************
 * Main controller for the tistory-downloader-lite. The interface between the model
 * and the view
 * 
 * @author isw4
 *
 */
public class MainController {
	private MainModel model;
	private MainView view;
	
	/**********************************************************************************
	 * Constructor
	 *********************************************************************************/
	public MainController(MainModel m, MainView v) {
		model = m;
		view = v;
		
		v.addRunListener(new runListener());
		v.addSaveListener(new saveListener());
	}
	
	/**********************************************************************************
	 * Methods
	 *********************************************************************************/
	public class runListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			String url = view.getURLInput();
			String downloadFolder = view.getDownloadFolder();
			String folderOption = view.getFolderOption();
			String feedback = model.run(url, downloadFolder, folderOption);
			view.updateFeedback(feedback);
		}
	}
	
	public class saveListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			String downloadFolder = view.getDownloadFolder();
			String feedback = model.setDefaultDest(downloadFolder);
			view.updateFeedback(feedback);
		}
	}
}

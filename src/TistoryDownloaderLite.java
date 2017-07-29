import java.awt.EventQueue;

/*************************************************************************************
 * Main class for the tistory-downloader-lite.
 * 
 * @author isw4
 *
 */
public class TistoryDownloaderLite {
	public static void main(String[] args) {
		//initializes the model
		MainModel model = new MainModel();
		
		//using the model to initialize the GUI
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("unused")
			public void run() {
				try {
					//creating the view
					MainView view = new MainView(model);
					view.setVisible(true);
					
					//linking the view to the model with the controller
					MainController controller = new MainController(model, view);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
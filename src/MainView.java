import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

/*************************************************************************************
 * Main class for the tistory-downloader-lite. 
 * 
 * @author isw4
 *
 */
@SuppressWarnings("serial")
public class MainView extends JFrame {
	
	private MainModel model;
	private JPanel content;
	private JTextField urlInput;
	private JButton run;
	private JButton save;
	private JTextArea feedbackField;
	private JTextField folderInput;
	private final ButtonGroup folderOptions = new ButtonGroup();

	/**********************************************************************************
	 * Methods
	 *********************************************************************************/
	//getters
	public String getURLInput() {
		return urlInput.getText();
	}
	
	public String getDownloadFolder() {
		return folderInput.getText();
	}
	
	public String getFolderOption() {
		//returns either "Direct" or "Generate"
		return folderOptions.getSelection().getActionCommand();
	}
		
	//setters
	public void updateFeedback(String s) {
		feedbackField.setText(s);
	}
	
	//adding action listeners
	public void addRunListener(ActionListener a) {
		run.addActionListener(a);
	}
	
	public void addSaveListener(ActionListener a) {
		save.addActionListener(a);
	}
	
	/**********************************************************************************
	 * Constructor
	 *********************************************************************************/
	public MainView(MainModel m) {
		model = m;
		
		setTitle("Tistory Downloader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		content = new JPanel();
		content.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(content);
		
		urlInput = new JTextField();
		urlInput.setColumns(10);
		
		JLabel title = new JLabel("Tistory Downloader");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel urlInputLabel = new JLabel("Page URL:");
		
		JLabel folderLabel = new JLabel("Download Folder:");
		
		run = new JButton("Run");
		
		feedbackField = new JTextArea();
		feedbackField.setForeground(Color.WHITE);
		feedbackField.setBackground(Color.DARK_GRAY);
		
		String defaultDest = model.getDest();
		folderInput = new JTextField(defaultDest);
		folderInput.setColumns(10);
		
		JRadioButton GenRadio = new JRadioButton("Generate Page Folder");
		folderOptions.add(GenRadio);
		GenRadio.setToolTipText("Automatically generates a page folder by detecting the title of the page");
		GenRadio.setActionCommand("Generate");
		GenRadio.setSelected(true);
		
		JRadioButton DirRadio = new JRadioButton("Direct");
		folderOptions.add(DirRadio);
		DirRadio.setActionCommand("Direct");
		DirRadio.setToolTipText("Downloads directly to the download folder");
		
		save = new JButton("Save");
		
		GroupLayout gl_content = new GroupLayout(content);
		gl_content.setHorizontalGroup(
			gl_content.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_content.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_content.createParallelGroup(Alignment.TRAILING)
						.addComponent(feedbackField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_content.createSequentialGroup()
							.addGroup(gl_content.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(folderLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(urlInputLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_content.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_content.createSequentialGroup()
									.addComponent(folderInput)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(save))
								.addGroup(gl_content.createSequentialGroup()
									.addComponent(DirRadio)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(GenRadio, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE))
								.addComponent(urlInput, GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(run, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
						.addComponent(title, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_content.setVerticalGroup(
			gl_content.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_content.createSequentialGroup()
					.addContainerGap()
					.addComponent(title, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_content.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_content.createSequentialGroup()
							.addGroup(gl_content.createParallelGroup(Alignment.BASELINE)
								.addComponent(urlInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(urlInputLabel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_content.createParallelGroup(Alignment.LEADING, false)
								.addComponent(folderInput)
								.addComponent(folderLabel)
								.addComponent(save, 0, 0, Short.MAX_VALUE))
							.addGap(5)
							.addGroup(gl_content.createParallelGroup(Alignment.LEADING)
								.addComponent(DirRadio)
								.addComponent(GenRadio))
							.addGap(7))
						.addGroup(gl_content.createSequentialGroup()
							.addComponent(run, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
							.addGap(13)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(feedbackField, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
					.addContainerGap())
		);
		content.setLayout(gl_content);
	}
}

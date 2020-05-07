import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class MainWindow extends JFrame{

	private JFrame frame;
	private JTextField textFieldChooseFile;
	private JButton btnChooseDest;
	private JTextField textFieldChooseDest;
	private JButton btnRun;
	private JTextArea textAreaShow;
	private JFileChooser SrcFileChooser;
	private JFileChooser DestDirectoryChooser;
	private static MainWindow window;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 500);
		frame.setTitle("Infoarchive XML Creator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SrcFileChooser = new JFileChooser();
		SrcFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		SrcFileChooser.setAcceptAllFileFilterUsed(false);
		
		DestDirectoryChooser = new JFileChooser();
		DestDirectoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		DestDirectoryChooser.setAcceptAllFileFilterUsed(false);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panelTop = new JPanel();
		frame.getContentPane().add(panelTop);
		panelTop.setLayout(null);
		
		btnRun = new JButton("Run Parser");
		btnRun.setBounds(12, 86, 558, 42);
		panelTop.add(btnRun);
		btnRun.setPreferredSize(new Dimension(420, 30));
		
		btnChooseDest = new JButton("Choose Output Directory");
		btnChooseDest.setBounds(12, 50, 185, 31);
		panelTop.add(btnChooseDest);
		
		textFieldChooseDest = new JTextField();
		textFieldChooseDest.setBounds(201, 50, 369, 31);
		panelTop.add(textFieldChooseDest);
		textFieldChooseDest.setColumns(25);
		
		JButton btnChooseFile = new JButton("Choose Input File/Directory");
		btnChooseFile.setBounds(12, 13, 185, 31);
		panelTop.add(btnChooseFile);
		btnChooseFile.setHorizontalAlignment(SwingConstants.LEFT);
		
		textFieldChooseFile = new JTextField();
		textFieldChooseFile.setBounds(201, 13, 369, 31);
		panelTop.add(textFieldChooseFile);
		textFieldChooseFile.setColumns(25);
		
		JScrollPane scrollPane = new JScrollPane(textAreaShow);
		scrollPane.setBounds(10, 141, 560, 300);
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
	        public void adjustmentValueChanged(AdjustmentEvent e) {  
	            e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
	        }
	    });
		panelTop.add(scrollPane);
		
		textAreaShow = new JTextArea();
		textAreaShow.setEditable(false);
		scrollPane.setViewportView(textAreaShow);
		//panelTop.add(textAreaShow);
		//textAreaShow.setPreferredSize(new Dimension(420, 144));
		textAreaShow.append("Select an input csv file or directory of csv files and output directory, then hit run.\n");
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initFileChooserAction(SrcFileChooser, textFieldChooseFile);
			}
		});
		btnChooseDest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initFileChooserAction(DestDirectoryChooser, textFieldChooseDest);
			}
		});
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String srcFilePath = textFieldChooseFile.getText();
				String destPathName = textFieldChooseDest.getText();
				if(!srcFilePath.isEmpty() && !destPathName.isEmpty()) {
					XmlCreator creator = new XmlCreator(srcFilePath, destPathName, window);
					creator.run();
					textAreaShow.append("\n\nParser Completed!\n");
				}
				else {
					textAreaShow.append("\nError: Input and/or Output paths cannot be empty!\n");
				}
			}
		});
	}
	
	private void initFileChooserAction(JFileChooser fileChooser, JTextField txtFileName) {
		int returnVal = fileChooser.showOpenDialog(MainWindow.this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			txtFileName.setText(file.getAbsolutePath());		
		}
	}
	
	public void updateTextArea(final String s) {
		textAreaShow.append("\n" + s);
	}
}

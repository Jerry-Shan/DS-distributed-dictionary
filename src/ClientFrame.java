import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

public class ClientFrame {

	private JFrame frame;
	private JTextField textFieldSearch;
	private JTextField textFieldRevome;
	private JButton btnSearch;
	private JTextField textField;
	private String sendData;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientFrame window = new ClientFrame();
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
	public ClientFrame() {
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblDictionaryClient = new JLabel("Dictionary Client");
		lblDictionaryClient.setFont(new Font("宋体", Font.PLAIN, 16));
		GridBagConstraints gbc_lblDictionaryClient = new GridBagConstraints();
		gbc_lblDictionaryClient.insets = new Insets(25, 0, 5, 5);
		gbc_lblDictionaryClient.gridx = 2;
		gbc_lblDictionaryClient.gridy = 1;
		frame.getContentPane().add(lblDictionaryClient, gbc_lblDictionaryClient);
		
		JLabel lblWord = new JLabel("Word");
		GridBagConstraints gbc_lblWord = new GridBagConstraints();
		gbc_lblWord.insets = new Insets(25, 25, 25, 5);
		gbc_lblWord.gridx = 1;
		gbc_lblWord.gridy = 2;
		frame.getContentPane().add(lblWord, gbc_lblWord);
		
		textFieldSearch = new JTextField();
		GridBagConstraints gbc_textFieldSearch = new GridBagConstraints();
		gbc_textFieldSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSearch.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSearch.gridx = 2;
		gbc_textFieldSearch.gridy = 2;
		frame.getContentPane().add(textFieldSearch, gbc_textFieldSearch);
		textFieldSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchWord = gettextFieldSearch().getText();
				setSendData("search-"+searchWord);
//				System.out.println("click Search: " + getSendData());
			}
		});
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 4;
		gbc_btnSearch.gridy = 2;
		frame.getContentPane().add(btnSearch, gbc_btnSearch);
		
		JLabel lblResponse = new JLabel("Response");
		GridBagConstraints gbc_lblResponse = new GridBagConstraints();
		gbc_lblResponse.gridwidth = 3;
		gbc_lblResponse.insets = new Insets(0, 0, 5, 5);
		gbc_lblResponse.gridx = 5;
		gbc_lblResponse.gridy = 2;
		frame.getContentPane().add(lblResponse, gbc_lblResponse);
		
		JButton btnRemove = new JButton("Remove");
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemove.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemove.gridx = 4;
		gbc_btnRemove.gridy = 3;
		frame.getContentPane().add(btnRemove, gbc_btnRemove);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 3;
		gbc_textField.gridheight = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 5;
		gbc_textField.gridy = 3;
		frame.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblMeaning = new JLabel("Meaning");
		GridBagConstraints gbc_lblMeaning = new GridBagConstraints();
		gbc_lblMeaning.insets = new Insets(25, 25, 25, 5);//(25, 25, 25, 25)
		gbc_lblMeaning.gridx = 1;
		gbc_lblMeaning.gridy = 4;
		frame.getContentPane().add(lblMeaning, gbc_lblMeaning);
		
		textFieldRevome = new JTextField();
		GridBagConstraints gbc_textFieldRevome = new GridBagConstraints();
		gbc_textFieldRevome.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldRevome.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldRevome.gridx = 2;
		gbc_textFieldRevome.gridy = 4;
		frame.getContentPane().add(textFieldRevome, gbc_textFieldRevome);
		textFieldRevome.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 4;
		gbc_btnAdd.gridy = 4;
		frame.getContentPane().add(btnAdd, gbc_btnAdd);
		frame.setVisible(true);
	}
	
	// Getter methods
	public JTextField gettextFieldSearch() {
		return textFieldSearch;
	}

	public JButton getBtnSearch() {
		return btnSearch;
	}

	public String getSendData() {
		return sendData;
	}

	public void setSendData(String sendData) {
		this.sendData = sendData;
	}


}

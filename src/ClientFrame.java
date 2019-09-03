import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

public class ClientFrame {

	private JFrame frame;
	private JTextField wordField;
	private JTextField meaningField;
	private JButton btnSearch;
	private static JTextField responseField;
	private String sendData;
	
	private static String ip = "localhost";
	private static int port = 3005;
	private static Socket socket;
	private static BufferedReader input;
	private static BufferedWriter output;
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
		frame.setTitle("Client GUI");
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
		
		wordField = new JTextField();
		GridBagConstraints gbc_wordField = new GridBagConstraints();
		gbc_wordField.fill = GridBagConstraints.BOTH;
		gbc_wordField.insets = new Insets(0, 0, 5, 5);
		gbc_wordField.gridx = 2;
		gbc_wordField.gridy = 2;
		frame.getContentPane().add(wordField, gbc_wordField);
		wordField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchWord = wordField.getText();
				String searchData = "search-"+searchWord;
				System.out.println("click Search: " + searchData);
				sendData(searchData);
			}
		});
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 4;
		gbc_btnSearch.gridy = 2;
		frame.getContentPane().add(btnSearch, gbc_btnSearch);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String removeWord = wordField.getText();
				String removeData = "remove-"+removeWord;
				System.out.println("click Search: " + getSendData());
				sendData(removeData);
			}
		});
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemove.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemove.gridx = 4;
		gbc_btnRemove.gridy = 3;
		frame.getContentPane().add(btnRemove, gbc_btnRemove);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String addWord = wordField.getText();
				String addMeaning = meaningField.getText();
				String addData = "add-"+addWord+"-"+addMeaning;
				System.out.println("click Add: " + addData);
				sendData(addData);}
		});
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 4;
		gbc_btnAdd.gridy = 4;
		frame.getContentPane().add(btnAdd, gbc_btnAdd);
		
		JLabel lblResponse = new JLabel("Response");
		GridBagConstraints gbc_lblResponse = new GridBagConstraints();
		gbc_lblResponse.gridwidth = 3;
		gbc_lblResponse.insets = new Insets(0, 0, 5, 5);
		gbc_lblResponse.gridx = 5;
		gbc_lblResponse.gridy = 2;
		frame.getContentPane().add(lblResponse, gbc_lblResponse);
		

		
		responseField = new JTextField();
		GridBagConstraints gbc_responseField = new GridBagConstraints();
		gbc_responseField.gridwidth = 3;
		gbc_responseField.gridheight = 2;
		gbc_responseField.insets = new Insets(0, 0, 5, 5);
		gbc_responseField.fill = GridBagConstraints.BOTH;
		gbc_responseField.gridx = 5;
		gbc_responseField.gridy = 3;
		frame.getContentPane().add(responseField, gbc_responseField);
		responseField.setColumns(10);
		
		JLabel lblMeaning = new JLabel("Meaning");
		GridBagConstraints gbc_lblMeaning = new GridBagConstraints();
		gbc_lblMeaning.insets = new Insets(25, 25, 25, 5);//(25, 25, 25, 25)
		gbc_lblMeaning.gridx = 1;
		gbc_lblMeaning.gridy = 4;
		frame.getContentPane().add(lblMeaning, gbc_lblMeaning);
		
		meaningField = new JTextField();
		GridBagConstraints gbc_meaningField = new GridBagConstraints();
		gbc_meaningField.fill = GridBagConstraints.BOTH;
		gbc_meaningField.insets = new Insets(0, 0, 5, 5);
		gbc_meaningField.gridx = 2;
		gbc_meaningField.gridy = 4;
		frame.getContentPane().add(meaningField, gbc_meaningField);
		meaningField.setColumns(10);
		
		frame.setVisible(true);
	}
	
	private static void sendData(String inputData) {
		System.out.println("Into sendData method");
		try {
			socket = new Socket(ip, port);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try
		{
			String sendData = inputData;
			System.out.println("Message sent to Server--> " + sendData);
		    output.write(sendData + "\n");
			output.newLine();
	    	output.flush();
	    	String message = input.readLine();
	    	if(message!=null)
		    {
	    		System.out.println(message+"\n");
	    		responseField.setText(message);
		    }
	    	
		    
		} 
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	// Getter methods
	public JTextField gettextFieldSearch() {
		return wordField;
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

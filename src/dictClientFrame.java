/**   
* @Title: ${file_name} 
* @Package ${package_name} 
* @Description: ${This is a GUI of dictionary client for searching,remove or add words}
* @author Jinzhe Shan  
* @date ${date} ${time} 
* @version V2.1
*/

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JPanel;
import java.awt.Color;

public class dictClientFrame {

	JFrame frame;
	private JTextField wordField;
	private JTextField meaningField;
	private static JTextField responseField;
	
	private static String ip = "localhost";
	private static int port = 2019;
	private static Socket socket;
	private static BufferedReader input;
	private static BufferedWriter output;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		dictFrame serverGUI = new dictFrame();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dictClientFrame window = new dictClientFrame();
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
	public dictClientFrame() {
		
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 300);
		frame.setTitle("Client GUI");
		
		// override the close method
		frame.addWindowListener(new MyWindowListener());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 148, 100, 71, 67, 0};
		gridBagLayout.rowHeights = new int[]{55, 19, 60, 10, 0, 60, 40, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblDictionaryClient = new JLabel("Dictionary Client");
		lblDictionaryClient.setFont(new Font("宋体", Font.PLAIN, 16));
		GridBagConstraints gbc_lblDictionaryClient = new GridBagConstraints();
		gbc_lblDictionaryClient.insets = new Insets(0, 0, 5, 5);
		gbc_lblDictionaryClient.gridx = 1;
		gbc_lblDictionaryClient.gridy = 1;
		frame.getContentPane().add(lblDictionaryClient, gbc_lblDictionaryClient);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitClient();
			}
		});
		GridBagConstraints gbc_btnQuit = new GridBagConstraints();
		gbc_btnQuit.insets = new Insets(0, 0, 5, 0);
		gbc_btnQuit.gridx = 4;
		gbc_btnQuit.gridy = 1;
		frame.getContentPane().add(btnQuit, gbc_btnQuit);
		
		JLabel lblWord = new JLabel("Word");
		GridBagConstraints gbc_lblWord = new GridBagConstraints();
		gbc_lblWord.insets = new Insets(0, 0, 5, 5);
		gbc_lblWord.gridx = 0;
		gbc_lblWord.gridy = 2;
		frame.getContentPane().add(lblWord, gbc_lblWord);
		
		wordField = new JTextField();
		GridBagConstraints gbc_wordField = new GridBagConstraints();
		gbc_wordField.fill = GridBagConstraints.BOTH;
		gbc_wordField.insets = new Insets(0, 0, 5, 5);
		gbc_wordField.gridx = 1;
		gbc_wordField.gridy = 2;
		frame.getContentPane().add(wordField, gbc_wordField);
		wordField.setColumns(10);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 2;
		frame.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton btnSearch = new JButton("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.anchor = GridBagConstraints.NORTH;
		gbc_btnSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSearch.insets = new Insets(0, 0, 5, 0);
		gbc_btnSearch.gridx = 0;
		gbc_btnSearch.gridy = 0;
		panel.add(btnSearch, gbc_btnSearch);
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchWord = wordField.getText();
				String searchData = "search-"+searchWord;
				System.out.println("click Search: " + searchData);
				sendData(searchData);
			}
		});
		
		JButton btnRemove = new JButton("Remove");
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemove.gridx = 0;
		gbc_btnRemove.gridy = 1;
		panel.add(btnRemove, gbc_btnRemove);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String removeWord = wordField.getText();
				String removeData = "remove-"+removeWord;
				System.out.println("click Search: " + removeData);
				sendData(removeData);
			}
		});
		
		JLabel lblMeaning = new JLabel("Meaning");
		GridBagConstraints gbc_lblMeaning = new GridBagConstraints();
		gbc_lblMeaning.insets = new Insets(0, 0, 5, 5);
		gbc_lblMeaning.gridx = 0;
		gbc_lblMeaning.gridy = 4;
		frame.getContentPane().add(lblMeaning, gbc_lblMeaning);
		
		meaningField = new JTextField();
		GridBagConstraints gbc_meaningField = new GridBagConstraints();
		gbc_meaningField.fill = GridBagConstraints.BOTH;
		gbc_meaningField.insets = new Insets(0, 0, 5, 5);
		gbc_meaningField.gridx = 1;
		gbc_meaningField.gridy = 4;
		frame.getContentPane().add(meaningField, gbc_meaningField);
		meaningField.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String addWord = wordField.getText();
				String addMeaning = meaningField.getText();
				if(addMeaning!=null) {
					String addData = "add-"+addWord+"-"+addMeaning;
					System.out.println("click Add: " + addData);
					sendData(addData);
				}
				else{
					System.out.println("Error: please input the word you want to add with its meaning");
				}
			}
			});
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 2;
		gbc_btnAdd.gridy = 4;
		frame.getContentPane().add(btnAdd, gbc_btnAdd);
		
		JLabel lblResponse = new JLabel("Response");
		GridBagConstraints gbc_lblResponse = new GridBagConstraints();
		gbc_lblResponse.insets = new Insets(0, 0, 5, 5);
		gbc_lblResponse.gridx = 0;
		gbc_lblResponse.gridy = 5;
		frame.getContentPane().add(lblResponse, gbc_lblResponse);
		
		responseField = new JTextField();
		GridBagConstraints gbc_responseField = new GridBagConstraints();
		gbc_responseField.insets = new Insets(0, 0, 5, 5);
		gbc_responseField.gridwidth = 2;
		gbc_responseField.fill = GridBagConstraints.BOTH;
		gbc_responseField.gridx = 1;
		gbc_responseField.gridy = 5;
		frame.getContentPane().add(responseField, gbc_responseField);
		responseField.setColumns(10);
		
		frame.setVisible(true);
	}
	
	private static void sendData(String inputData) {
		try
		{
			socket = new Socket(ip, port);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			String sendData = inputData;
			System.out.println("Message sent to Server--> " + sendData);
		    output.write(sendData);
			output.newLine();
	    	output.flush();
	    	String message = input.readLine();
	    	if(message!=null)
		    {
	    		System.out.println(message+"\n");
	    		responseField.setText(message);
		    }
	    	// release resources
	    	output.close();
	    	input.close();
	    	socket.close();
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

	// close the window after close connection
	class MyWindowListener extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			exitClient();
		}
	
	private void exitClient() {
		try {
			socket = new Socket(ip, port);
			output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			output.write("Client is exiting...");
			output.newLine();
	    	output.flush();
	    	
			output.close();
            socket.close();
            System.exit(0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	}

	
	public static Socket getSocket() {
		return socket;
	}

	public static BufferedReader getInput() {
		return input;
	}

	public static BufferedWriter getOutput() {
		return output;
	}


}

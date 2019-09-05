/**   
* @Title: ${file_name} 
* @Package ${package_name} 
* @Description: ${This is the GUI for dictionary server and display the current dictionary}
* @author Jinzhe Shan  
* @date ${date} ${time} 
* @version V2.0
*/

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class dictFrame {

	private JFrame frame;
	private static HashMap<String, String> dictionary =  new HashMap<String, String>();
	private static String DICT_PATH = "dictionary.txt";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dictFrame window = new dictFrame();
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
	public dictFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Dictionary Frame");
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblwordmeaning = new JLabel("Current Dictionary");
		lblwordmeaning.setBackground(Color.WHITE);
		lblwordmeaning.setFont(new Font("Calibri", Font.PLAIN, 16));
		frame.getContentPane().add(lblwordmeaning, BorderLayout.NORTH);
		
		JButton btnFresh = new JButton("Fresh");
		frame.getContentPane().add(btnFresh, BorderLayout.SOUTH);
		
		JTextArea textArea = new JTextArea();
		frame.getContentPane().add(textArea, BorderLayout.CENTER);
		
		readDict();
		String currentDict = currentDict(dictionary);
		textArea.setText(currentDict);
		
		btnFresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readDict();
				String currentDict = currentDict(dictionary);
				textArea.setText(currentDict);
			}
		});
		frame.setVisible(true);
	}
	
	@SuppressWarnings("unchecked")
	private static void readDict() {
		File f = new File(DICT_PATH);
		try{
			if(!f.exists()){
				dictionary = new HashMap<String,String>();
				dictionary.put("England", "London");
				f.createNewFile();
				FileOutputStream fileOutput = new FileOutputStream(DICT_PATH);
				ObjectOutputStream objOutput = new ObjectOutputStream(fileOutput);
				objOutput.writeObject(dictionary);
				objOutput.close();
				fileOutput.close();
			}
			FileInputStream fileInput = new FileInputStream(DICT_PATH);
			ObjectInputStream objInput = new ObjectInputStream(fileInput);
			dictionary = (HashMap<String,String>) objInput.readObject();
			fileInput.close();
			objInput.close();
		} 
		catch (SocketException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String currentDict(HashMap<String, String> dictionary) {
		
        final int size = dictionary.size();
        String currentDict = new String();
        
        String[] word = new String[size];
        dictionary.keySet().toArray(word);
        String[] meaning = new String[size];
        dictionary.values().toArray(meaning);
        System.out.println("Current Dictionary:");
        for(int i = 0; i<size;i++) {
        	currentDict += word[i]+"\t: "+meaning[i]+"\n";
			System.out.println(word[i]+":"+meaning[i]);
		}
		return currentDict;

	}

}

/**   
* @Title: ${file_name} 
* @Package ${package_name} 
* @Description: ${This is a multi-thread dictionary server }
* @author Jinzhe Shan  
* @date ${date} ${time} 
* @version V3.0
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.net.ServerSocketFactory;

public class dictServer {
	
	// Declare the port number
	private static int port = 2019;
	
	// Declare the Dictionary Path
	private static String DICT_PATH = "src/dictionary.txt";
	
	// Store Dictionary in HashMap
	private static HashMap<String, String> dictionary =  new HashMap<String, String>();
	
	public static void main(String[] args)
	{
		ServerSocketFactory factory = ServerSocketFactory.getDefault();
		
		// read dictionary from dictionary.txt to HashMap<String,String> dictionary
		readDict();
		
		try(ServerSocket server = factory.createServerSocket(port))
		{
			System.out.println("Waiting for client connection-");
			
			dictServerFrame5 serverGUI = new dictServerFrame5();
			serverGUI.currentDict(dictionary);
			
			// Wait for connections.
			while(true)
			{
				Socket client = server.accept();
				// Start a new thread for a connection
				Thread t = new Thread(() -> serveClient(client));
				t.start();
			}
			
		} catch (BindException e) {
			System.out.println("The port is using now,please exit it and try again."+"\n");
			return;
		} catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
		
	}
	

	private static void serveClient(Socket client)
	{
		printDict();
		
		try(Socket clientSocket = client)
		{
			// Input stream
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
			// Output Stream
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));
			
			String client_Message = in.readLine();
			System.out.println("Client Message : " + client_Message);
//			String meaning = dictionary.get(client_Message);
			if(client_Message!=null){
				String[] msgList = client_Message.split("-");
				if(msgList.length <2) {
					System.out.println("Error: please type the word you want to search, add or remove");
					out.write("Error: please type the word you want to search, add or remove");
					out.newLine();
					out.flush();
				}
//				for(int i =0;i<msgList.length;i++) {
//					System.out.println(msgList[i]);
//				}
				else if(msgList.length==2 || msgList.length==3) {
					String type = msgList[0];
					String word = msgList[1];
					switch(type){
					case "search": searchDict(word, client);
					case "remove": addorRemoveDict(client_Message,client);
					case "add"   : addorRemoveDict(client_Message,client);
//					default :{
//						System.out.println("Client Input may be wrong, sending the Error Code to Client...");
//						out.write("Client Input is wrong. Please input as norm.");
//						out.newLine();
//						out.flush();
//					}
					}
				}
				else {
//					System.out.println("Client Input may be wrong, sending the Error Code to Client...");
					out.write("Client Input is wrong. Please input as norm.");
					out.newLine();
					out.flush();
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
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
			
			// print dictionary
			printDict();
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
	
	private static void writeDict(){
		try {
			File f = new File(DICT_PATH);
			if(!f.exists()) {
				f.createNewFile();
			}
			FileOutputStream fileOutput = new FileOutputStream(DICT_PATH);
			ObjectOutputStream objOutput = new ObjectOutputStream(fileOutput);
			
			objOutput.writeObject(dictionary);
			
			objOutput.close();
			fileOutput.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void printDict() {
		// print dictionary
		System.out.println("Current Dictionary is:");
		for (Entry<String, String> entry : dictionary.entrySet()) {
			 System.out.println(entry.getKey()+"->"+entry.getValue());
		}
	}
	
	private static void searchDict(String word,Socket client) {
		try {
			BufferedWriter out = null;
			out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));
			String meaning = dictionary.get(word);
			if(word!=null){
			if(meaning!=null) {
				System.out.println("the word is in the dectionary.");
				out.write("The meaning of " + word + " is "+ meaning);
			}
			else {
				System.out.println("the word is not in the dectionary.");
				out.write("The word of " + word + " is not in dictionary");
			}
			out.newLine();
			out.flush();
		}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static synchronized void addorRemoveDict(String msg, Socket client) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));
			String[] msgList = msg.split("-");
			String type = msgList[0];
			String word = msgList[1];
			if(type.equals("add")) {
				if (msgList.length !=3) {
					System.out.println("Error: please input the word you want to add with its meaning");
					out.write("Error: please input the word you want to add with its meaning");
					out.newLine();
					out.flush();
				}
				else if (dictionary.get(word) == null) {
					String meaning = msgList[2];
					dictionary.put(word,meaning);
					System.out.println("The word of " + word + " is added into the dectionary.");
					out.write("The word of " + word + " is added into the dictionary");
					out.newLine();
					out.flush();
					writeDict();
				}
				else {
					System.out.println("The word of " + word + " is already in the dectionary.");
					out.write("The word of " + word + " is already in the dictionary.");
					out.newLine();
					out.flush();
				}
			}
			if(type.equals("remove")) {
				if(dictionary.get(word)!=null) {
					dictionary.remove(word);
					System.out.println("The word of " + word + " has been removed from the dectionary.");
					out.write("The word of " + word + " has been removed from the dictionary.");
					out.newLine();
					out.flush();
					writeDict();
				}
				else {
					out.write("The word of " + word + " is not in dictionary,please try another word.");
					out.newLine();
					out.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

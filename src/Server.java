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
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.net.ServerSocketFactory;

public class Server {
	
	// Declare the port number
	private static int port = 3005;
	
	// Identifies the user number connected
	private static int counter = 0;
	
	// Declare the Dictionary Path
	private static String DICT_PATH = "src/dictionary.txt";
	
	// Store Dictionary in HashMap
	private static HashMap<String, String> dictionary =  new HashMap<String, String>();
	
	public static void main(String[] args)
	{
		ServerSocketFactory factory = ServerSocketFactory.getDefault();
		
		// read dictionary from dictionary.txt to HashMap<String,String> dictionary
		dictionary.put("England", "London");
		writeDict();
		readDict();
		
		// print dictionary
		for (Entry<String, String> entry : dictionary.entrySet()) {
			 System.out.println(entry.getKey()+"->"+entry.getValue()+"\n");
		}
		
		try(ServerSocket server = factory.createServerSocket(port))
		{
			System.out.println("Waiting for client connection-");
			
			// Wait for connections.
			while(true)
			{
				Socket client = server.accept();
				counter++;
				System.out.println("Client "+counter+": Applying for connection!"+"\n");
							
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
		try(Socket clientSocket = client)
		{
			// Input stream
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
			// Output Stream
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));
			
			String client_Message = in.readLine();
			System.out.println("Client Message : " + client_Message);
			String meaning = dictionary.get(client_Message);
			if(client_Message!=null){
				if(meaning!=null) {
					out.write("The meaning of " + client_Message + " is "+ dictionary.get(client_Message));
					
				}
				else {
					out.write("The word of " + client_Message + " is not in dictionary");
				}
				out.newLine();
				out.flush();
				
			}
			System.out.println("CLIENT:"+ client_Message);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void readDict() {
		File f = new File(DICT_PATH);
		if(!f.exists()){
			dictionary = new HashMap<String,String>();
		}
		try{
			FileInputStream fileInput = new FileInputStream(DICT_PATH);
			ObjectInputStream objInput = new ObjectInputStream(fileInput);
			dictionary = (HashMap<String,String>) objInput.readObject();
			fileInput.close();
			objInput.close();
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

}

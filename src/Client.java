
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	// IP and port
	private static String ip = "localhost";
	private static int port = 3005;
	private static Socket socket;
	private static BufferedReader input;
	private static BufferedWriter output;
	
	public static void main(String[] args) 
	{
		try {
			socket = new Socket(ip, port);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			setupClientFrame();
		} catch (NullPointerException e) {
			System.out.println("Null Pointer Exception");
		}
	}

	private static void setupClientFrame() {
		ClientFrame clientFrame = new ClientFrame();
		
		System.out.println(clientFrame.getSendData());
		while (clientFrame.getSendData()!= null) {
			System.out.println("client message:"+clientFrame.getSendData());
			sendData(clientFrame.getSendData());
		}
	}

	private static void sendData(String inputData) {
		System.out.println("Into sendData method");
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
}


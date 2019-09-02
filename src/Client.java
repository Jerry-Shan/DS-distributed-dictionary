import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	
	// IP and port
	private static String ip = "localhost";
	private static int port = 3005;
	
	public static void main(String[] args) 
	{
		try
		{
			@SuppressWarnings("resource")
			Socket socket = new Socket(ip, port);
			//Input and Output Stream
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			System.out.println("Input the word you want to search, add or remove \n");
			System.out.println("Input Norm like:\n search-word \n add-word-meaning \n remove-word\n");
		    @SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
		    String str2 = null;
		    if (scan.hasNextLine()) {
	            str2 = scan.nextLine();
			    String sendData =str2;
		    	output.write(sendData + "\n");
		    	System.out.println("Message sent to Server--> " + sendData);
				output.newLine();
		    	output.flush();
		    	String message = input.readLine();
		    	if(message!=null)
			    {
		    		System.out.println(message+"\n");
			    }
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

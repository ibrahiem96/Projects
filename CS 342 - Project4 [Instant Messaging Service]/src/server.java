

/**
 * Multi CLient Instant Messaging Service
 * Version 1.0.1
 * 
 * Group: Anas Ahmad, Mohammad Hasan, Ibrahiem Mohammad, Hassan Pasha (Although there are only a max of 3 in a group,
 * there was a situation with the health of Hassan Pasha's mother so we included him in our group)
 * 
 * CS 342
 * 
 * This project was officially submitted by Anas Ahmad on 4/12/16
 * This is an updated, commented version--increased readability.
 * 
 * Issues:-
 * - send and receive on same thread so messages can be received and sent to both clients only after both clients
 * have completed their message and sent
 * - port number hard coded
 * 
 */


import java.net.*;
import java.util.ArrayList;
import java.io.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;

public class server extends JFrame implements ActionListener{
	
	//structures to hold client details
	static ArrayList<Socket> clientPorts = new ArrayList<Socket>();				//the client information...
	static ArrayList<String> clientNames = new ArrayList<String>();		//change it to privateee
	static ArrayList<client> clients = new ArrayList<client>();

	//max clients connect
	public static int [] check = new int [10];

	public static int five;
	public static String five2 = "five";


	// GUI items
	JButton ssButton;
	JLabel machineInfo;
	JLabel portInfo;
	JTextArea history;
	JTextArea clientList;
	JTextArea messageTo;
	private boolean running;

	// Network Items
	boolean serverContinue;
	ServerSocket serverSocket;



	//constructor
	public server()
	{
		//super( "Echo Server" );

		//check for num clients connected
		for(int i = 0; i< 10; i++)
		{
			check[i] = i;
		}

		five = 5;

		// get content pane and set its layout
		Container container = getContentPane();
		container.setLayout( new FlowLayout() );

		// create buttons
		running = false;
		ssButton = new JButton( "Start Listening" );
		ssButton.addActionListener( this );
		//container.add( ssButton );

		String machineAddress = null;
		try
		{  
			InetAddress addr = InetAddress.getLocalHost();
			machineAddress = addr.getHostAddress();
		}
		catch (UnknownHostException e)
		{
			machineAddress = "127.0.0.1";
		}
		machineInfo = new JLabel (machineAddress + "\n");
		container.add( machineInfo );
		portInfo = new JLabel (" Not Listening ");
		container.add( portInfo );

		container.add ( new JLabel ("Chat: ", JLabel.LEFT) );
		history = new JTextArea ( 10, 40 );
		history.setEditable(false);
		container.add( new JScrollPane(history) );

		container.add ( new JLabel ("Msg to: ", JLabel.LEFT) );
		messageTo = new JTextArea ( 10, 40 );
		messageTo.setEditable(false);
		container.add( new JScrollPane(messageTo) );

		container.add ( new JLabel ("Users Connected: ", JLabel.LEFT) );
		clientList = new JTextArea ( 10, 40 );
		clientList.setEditable(false);
		container.add( new JScrollPane(clientList) );



		new ConnectionThread (this);
		setSize( 500, 600 );
		setVisible( true );

	} // end CountDown constructor

	public static void main( String args[] )
	{ 

		server application = new server();

		application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}

	//return list of client objects
	public ArrayList<client> getClients(){
		ArrayList<client> temp = clients;
		
		return temp;
	}
	
	//return list of client names
	public static ArrayList<String> getClientNames(){

		ArrayList<String> temp = clientNames;



		return temp;
	}

	//return list of client ports
	public ArrayList<Socket> getClientPorts(){
		return clientPorts;
	}



	// handle button event
	public void actionPerformed( ActionEvent event )
	{
		if (running == false)
		{
			new ConnectionThread (this);
		}
		else
		{
			serverContinue = false;
			ssButton.setText ("Start Listening");
			portInfo.setText (" Not Listening ");
		}
	}


} // end class server

/**
 * 
 * @author Ibrahiem Mohammad
 *
 * Class for connecting threads [needed to multi-client capability]
 * 
 */
class ConnectionThread extends Thread
{
	server gui;


	public ConnectionThread (server es3)
	{
		gui = es3;
		gui.clientNames = server.clientNames;
		start();
	}

	public void run()
	{
		gui.serverContinue = true;

		try 
		{ 
			gui.serverSocket = new ServerSocket(12345); 
			gui.portInfo.setText("Listening on Port: " + gui.serverSocket.getLocalPort());
			System.out.println ("Connection Socket Created");
			try { 
				while (gui.serverContinue)
				{
					System.out.println ("Waiting for Connection");
					gui.ssButton.setText("Stop Listening");
					new CommunicationThread (gui.serverSocket.accept(), gui); 
				}
			} 
			catch (IOException e) 
			{ 
				System.err.println("Accept failed."); 
				System.exit(1); 
			} 
		} 
		catch (IOException e) 
		{ 
			System.err.println("Could not listen on port: 10008."); 
			System.exit(1); 
		} 
		finally
		{
			try {
				gui.serverSocket.close(); 
			}
			catch (IOException e)
			{ 
				System.err.println("Could not close port: 10008."); 
				System.exit(1); 
			} 
		}
	}
}


class CommunicationThread extends Thread
{ 
	//private boolean serverContinue = true;
	private Socket clientSocket;
	private server gui;


	public CommunicationThread (Socket clientSoc, server ec3) throws IOException
	{
		clientSocket = clientSoc;

		gui = ec3;
		start();

	}

	public void run()
	{
		System.out.println ("New Communication Thread Started");
		boolean checkSocket;

		String inputMessage;
		String inputUsers;
		//String fullname;

		try 
		{ 
			checkSocket = false;
			PrintWriter out;
			BufferedReader in = new BufferedReader( 
					new InputStreamReader( clientSocket.getInputStream()));

			out = new PrintWriter(clientSocket.getOutputStream(), true);

			out.println("" + gui.clientNames.size());				//send all of the clients to the one client for names
			for(String s: gui.clientNames)
			{
				out.println(s);
			}




			for(Socket s: gui.clientPorts)								//check if the client is already in the list the we have
			{
				if(s.getPort() == clientSocket.getPort())
					checkSocket = true;	
			}


			if(checkSocket == false)									//if the client doesn't exist in the list we have already
			{ 
				gui.clientPorts.add(clientSocket);							//add the client to the list



				if((inputMessage = in.readLine()) != null)					//read the first input, which would be the name of the client
				{													//then add it to the clients name list.
					//fullname = inputMessage + ": " + clientSocket.getPort();
					gui.clientNames.add(inputMessage);						//add client names to the server gui, to check
					//out.println(inputLine);
				}
			}




			gui.clientList.setText("");

			for(String s: gui.clientNames)
			{

				gui.clientList.insert(s + "\n", 0);

			}

			System.out.println("No. of Clients" + gui.clients.size());

			System.out.println("No. of ClientNames" + gui.clientNames.size());

			System.out.println("No. of ClientPorts" + gui.clientPorts.size());

			//what is user, what is the message check..
			int indexUser;
			int realIndex = 0;
			boolean containsUser;// = false;




			while ((inputMessage = in.readLine()) != null) 
			{ 

				if((inputUsers = in.readLine()) == null)
					break;







				gui.messageTo.insert(inputUsers + "\n", 0);
				gui.history.append (inputMessage + "\n");

				containsUser = false;
				indexUser = 0;
				for(String s: gui.clientNames)						//check if the username given my the other user to send a message
				{												//exists or not.

					if(s.equals(inputUsers))
					{
						containsUser = true;
						realIndex = indexUser;
						break;
					}
					indexUser++;
				}


				if(inputUsers.equals(""))
				{
					JOptionPane.showMessageDialog(new JFrame(), "Please enter a User... ", "Dialog",
							JOptionPane.ERROR_MESSAGE);

					out.println();	
				}


				else if(inputUsers.equals("SENDTOALL"))				//the inputUsers received is 'SENDTOALL' then send to all users.
				{

					JOptionPane.showMessageDialog(new JFrame(), "send to alllllll", "Dialog",
							JOptionPane.ERROR_MESSAGE);
					gui.messageTo.insert("Sent to all", 0);
					gui.history.insert(inputMessage, 0);

					for(Socket s: gui.clientPorts)
					{
						if(s.getPort() != clientSocket.getPort())
						{
							out = new PrintWriter(s.getOutputStream(), true);	//prints to all clients one by one except the sender
							out.println(inputMessage);								//sends the output
						}
					}

					out.println(inputMessage);
				}

				else if(inputUsers.equals("EXITING"))
				{
					gui.clientNames.remove(inputMessage);
					out.println("");
				}



				else if(containsUser == true)
				{
					if(gui.clientPorts.get(realIndex) == clientSocket)
					{
						out.println();	
					}	

					else
					{
						out = new PrintWriter(gui.clientPorts.get(realIndex).getOutputStream(), true);
						out.println(inputMessage);
					}
				}

				else if(containsUser == false)
				{
					gui.history.append ("This user isn't available.\n");
					out.println();
				}




				else if (inputMessage.equals("Bye.")) 
					break; 

				else if (inputMessage.equals("End Server.")) 
					gui.serverContinue = false; 
			} 



			out.close(); 
			in.close(); 
			clientSocket.close(); 
		} 
		catch (IOException e) 
		{ 
			System.err.println("Problem with Communication Server");
			//System.exit(1); 
		} 
	}
} 

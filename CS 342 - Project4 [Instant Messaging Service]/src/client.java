
import java.net.*;
import java.util.ArrayList;
import java.io.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class client extends JFrame implements ActionListener
{  
	// GUI items
	JButton sendButton;
	JButton sendAllButton;
	JTextField machineInfo;
	JTextField portInfo;
	JTextField message;
	JTextArea history;
	JTextField user;
	JTextArea AllUsers;

	// Network Items
	boolean connected;
	Socket echoSocket;
	PrintWriter out;
	PrintWriter out2;
	BufferedReader in;
	int CountName;
	static String name;
	boolean SentToAll;
	int check = 0;
	//ArrayList<String> check;// = server.clientNames;

	// set up GUI
	public client(String name) throws IOException
	{
		super(name);

		//check = server.clientNames;
		//check = server.clientNames;
		// get content pane and set its layout
		Container container = getContentPane();
		container.setLayout (new BorderLayout ());

		// set up the North panel
		JPanel upperPanel = new JPanel ();
		upperPanel.setLayout (new GridLayout (4,2));
		JPanel lowerPanel = new JPanel ();
		lowerPanel.setLayout (new GridLayout (4,2));
		container.add (upperPanel, BorderLayout.NORTH);
		//container.add (lowerPanel, BorderLayout.SOUTH);


		// create buttons
		connected = false;

		upperPanel.add ( new JLabel ("Message: ", JLabel.LEFT) );
		message = new JTextField ("");
		message.addActionListener( this );
		upperPanel.add( message );


		upperPanel.add ( new JLabel ("Username: ", JLabel.LEFT) );
		user = new JTextField ("");
		user.addActionListener( this );
		upperPanel.add( user );


		sendButton = new JButton( "Send Message" );
		sendButton.addActionListener( this );
		sendButton.setEnabled (false);
		upperPanel.add( sendButton );

		sendAllButton = new JButton( "Send to All" );
		sendAllButton.addActionListener( this );
		upperPanel.add( sendAllButton );

		SentToAll = false;

		history = new JTextArea ( 5, 20 );
		history.setEditable(false);
		container.add( new JScrollPane(history), BorderLayout.CENTER);

		AllUsers = new JTextArea ( 5, 20 );
		AllUsers.setEditable(false);
		container.add(new JScrollPane(AllUsers), BorderLayout.SOUTH);

		for(String s: server.getClientNames())
		{
			AllUsers.append(s + "\n");
		}








		//      Thread worker = new Thread(new Runnable(){
		//    	    public void run(){
		//    	        jTextArea.append("Test" + "\n");
		//    	        try {
		//    	            Thread.sleep(3000);
		//    	        } catch (InterruptedException e1) {
		//    	            e1.printStackTrace();
		//    	        }
		//    	        jTextArea.append("Test1" + "\n");
		//
		//    	    }
		//    	});
		//    	worker.start();

		//server.clients = server.addtoClients(this);

		doManageConnection();
		out.println(name);					//sending the name to the server
		message.setText("");

		setSize( 500, 450 );
		setVisible( true );



		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {

				out.println(name);


				out2.println("EXITING");


				System.exit(0);

			}
		});


	} // end CountDown constructor

	public static void main( String args[] ) throws IOException
	{ 

		name = JOptionPane.showInputDialog(null, "Enter your name..");
		client application = new client(name);
		server.clients.add(application);

		application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}


	// handle button event
	public void actionPerformed( ActionEvent event )
	{


		SentToAll = false;

		try {
			if(check == 0)
			{
				receiveClients();
				check = 1;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(event.getSource() == sendAllButton)
		{
			SentToAll = true;
			doSendMessage();

		}

		if ( connected && (event.getSource() == sendButton || event.getSource() == message || event.getSource() == user) )
		{ 
			doSendMessage();
		}

		receiveMessage();

		for(String s: server.clientNames)										//adding all of the clients to the gui
		{
			AllUsers.insert(s, 0);
		}

	}


	public void receiveClients() throws NumberFormatException, IOException
	{
		String input;
		int number = 0;
		if(in != null)
			if((input = in.readLine()) != null)
				number = Integer.parseInt(input);


		for(int i = 0; i < number; i++)
		{
			if((input = in.readLine()) != null)
				AllUsers.append(input + "\n");
		}

	}




	public void receiveMessage()
	{
		String input;

		try 
		{
			if(in != null)
				if((input = in.readLine()) != null)    		
					history.append(input + "\n");
		} 

		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}





	public void doSendMessage()
	{


		out.println(name + ": " + message.getText());
		history.append(name + ": " + message.getText() + "\n");


		if(SentToAll == true)
			out2.println("SENDTOALL");

		else
			out2.println(user.getText());

		message.setText("");

	}

	public void doManageConnection()
	{
		if (connected == false)
		{
			String machineName = null;
			int portNum = -1;
			try {
				machineName = "127.0.0.1";
				portNum = 12345;//Integer.parseInt(portInfo.getText());

				echoSocket = new Socket(machineName, portNum);

				out = new PrintWriter(echoSocket.getOutputStream(), true);
				out2 = new PrintWriter(echoSocket.getOutputStream(), true);

				in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

				sendButton.setEnabled(true);
				connected = true;
				//connectButton.setText("Disconnect from Server");
			} catch (NumberFormatException e) {
				history.insert ( "Server Port must be an integer\n", 0);
			} catch (UnknownHostException e) {
				history.insert("Don't know about host: " + machineName , 0);
			} catch (IOException e) {
				history.insert ("Couldn't get I/O for "
						+ "the connection to: " + machineName , 0);
			}

		}
		else
		{
			try 
			{

				out.close();
				in.close();
				echoSocket.close();
				sendButton.setEnabled(false);
				connected = false;
				//connectButton.setText("Connect to Server");
			}
			catch (IOException e) 
			{
				history.insert ("Error in closing down Socket ", 0);
			}
		}


	}

} // end class EchoServer3






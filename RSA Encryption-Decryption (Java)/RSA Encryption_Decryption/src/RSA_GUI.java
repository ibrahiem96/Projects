import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;

public class RSA_GUI extends JFrame implements ActionListener {

	private JLabel label = new JLabel("HELLO.");
	private JFrame frame = new JFrame();
	private JButton b1 = new JButton("Generate Public and Private Keys");
	private JButton b2 = new JButton("Encrypt Message");
	private JButton b3 = new JButton("Decrypt Message");
	private JButton b4 = new JButton("About");
	
	public int choice;
	
	public RSA_GUI() {

		// the clickable button
		
		
		this.b1.addActionListener(this);
		this.add(b1);
		b2.addActionListener(this);
		this.add(b2);
		b3.addActionListener(this);
		this.add(b3);
		b4.addActionListener(this);
		this.add(b4);

		// the panel with the button and text
		JPanel panel = new JPanel();
		
		panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 10, 50));
		panel.setLayout(new GridLayout(2, 4, 10, 10));
		panel.add(b1);
		panel.add(b2);
		panel.add(b3);
		panel.add(b4);

		// set up the frame and display it
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("RSA Encryption/Decryption");
		frame.pack();
		frame.setVisible(true);
	}

	// process the button clicks
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1){
			
			int c1 = JOptionPane.showOptionDialog(null, 
					"Do You Have Your Own Primes?", "Prime Provider", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
					null, null, null);
			
			if (c1 == JOptionPane.YES_OPTION){
				choice = 1;
				JOptionPane.showMessageDialog(null, "This option is not available with the trial version");
				/*try {
					KeyGenerator k1 = new KeyGenerator();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
			}
			
			else if (c1 == JOptionPane.NO_OPTION) {
				choice = 2;
				try {
					KeyGenerator k2 = new KeyGenerator();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
						
			
			JOptionPane.showMessageDialog(null, 
					"Public/Private Key Pair Created");
			
		}
		if (e.getSource() == b2){
	        
			String msg = JOptionPane.showInputDialog("Enter Message:");
			
			try {
				KeyGenerator k3 = new KeyGenerator(msg);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if (e.getSource() == b3){
			
			String dec = JOptionPane.showInputDialog("Enter message to be decrypted \n"
					+ "(This can be found in your encrypted file)");
			
			try {
				KeyGenerator k4 = new KeyGenerator(dec, 2);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource() == b4)
	        JOptionPane.showMessageDialog(frame.getComponent(0), "This program was"
	        		+ " created by Dhruv Patel and Ibrahiem Mohammad for CS 342\n"
	        		+ " It was created during the Spring 2016 Semester at UIC.\n This program is an"
	        		+ " imitation of the RSA Encryption/Decryption tool used by\n"
	        		+ " many softwares/technologies throughout the globe.");
		};
	
		public void openFile(String f)
		   {
		      File file;         // For file input
		      Scanner inputFile; // For file input

		      // Attempt to open the file.
		      try
		      {
		         file = new File(f);
		         inputFile = new Scanner(file);
		         JOptionPane.showMessageDialog(null, 
		                          "The file was found.");
		      }
		      catch (FileNotFoundException e)
		      {
		         JOptionPane.showMessageDialog(null, 
		                               "File not found.");
		      }
		      
		      System.exit(0);
		   }
		
	// create one Frame
	public static void main(String[] args) {
		new RSA_GUI();
	}
}


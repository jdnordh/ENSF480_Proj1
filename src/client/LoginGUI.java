package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import server.Server;
import structures.*;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;

public class LoginGUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static LoginGUI loginFrame;
	private JPanel loginPanel;
	private JTextField usernameTF;
	private JPasswordField passwordTF;
	private Socket aSocket;
	private ObjectOutputStream output;
	private ObjectInputStream input;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginFrame = new LoginGUI(Server.NAME, Server.PORT);
					loginFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI(String serverName , int portNum) {
		try {
			InetAddress a = InetAddress.getByName(serverName);
			aSocket = new Socket(a , portNum);
			output = new ObjectOutputStream(aSocket.getOutputStream());
			input = new ObjectInputStream(aSocket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 508, 210);
		loginPanel = new JPanel();
		loginPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(loginPanel);
		loginPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "LoginPanel", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(25, 25, 443, 118);
		loginPanel.add(panel);
		panel.setLayout(null);
		
		usernameTF = new JTextField();
		usernameTF.setBounds(102, 18, 329, 22);
		panel.add(usernameTF);
		usernameTF.setColumns(10);
		
		JLabel usernameL = new JLabel("Username");
		usernameL.setBounds(6, 21, 84, 16);
		panel.add(usernameL);
		
		JLabel passwordL = new JLabel("Password");
		passwordL.setBounds(6, 56, 84, 16);
		panel.add(passwordL);
		
		passwordTF = new JPasswordField();
		passwordTF.setBounds(102, 53, 329, 22);
		panel.add(passwordTF);
		passwordTF.setColumns(10);
		
		JButton submitB = new JButton("Login");
		submitB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				MessageDigest digest;
				//TODO try and avoid using getText(), use getPassword() instead which returns a 
				//char[] instead
				String password = passwordTF.getText();
				
<<<<<<< HEAD
				User loginUser = new User(usernameTF.getText(), passwordTF.getText());
=======
				//User loginUser = new User(usernameTF.getText(), password /* This will get changed*/);
				User user = new User();
				user.setUserName(usernameTF.getText());
>>>>>>> de5e8a172ec2c2c2988dcfdb632fa583846b9dfc
				
				try {
					digest = MessageDigest.getInstance("SHA-256");
					String hashed_password = new String(digest.digest(password.getBytes(StandardCharsets.UTF_8)));
					user.setPassword(hashed_password);
				} catch (NoSuchAlgorithmException e) {}
				
				
<<<<<<< HEAD
				System.out.println("Username: " + loginUser.getUserName() + "\nPassword: " + loginUser.getPassword());
				
=======
				//System.out.println("Username: " + user.getUserName() + "\nPassword: " + user.getPassword());
>>>>>>> de5e8a172ec2c2c2988dcfdb632fa583846b9dfc
				//create login packet
				Packet temp = new Packet(Packet.LOGIN);
				
				//add user to packet
<<<<<<< HEAD
				temp.addUser(loginUser);
				
=======
				temp.addUser(user);
>>>>>>> de5e8a172ec2c2c2988dcfdb632fa583846b9dfc
				//send packet to server
				try {
					output.writeObject(temp);
					output.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//read a packet from server
				try {
					temp = (Packet) input.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Type: "+ temp.getType());
				if(temp.getType() == Packet.LOGIN_CONFIRM_USER){
					//TODO client gui
					loginFrame.dispose();
					ClientGUI gui = new ClientGUI(loginUser.getUserName());
					gui.setVisible(true);
					//ClientGUI
				}else if(temp.getType() == Packet.LOGIN_CONFIRM_ADMIN){
					//TODO
					loginFrame.dispose();
					//AdminGUI();
					//open admin gui
					//AdminGUI
				}else if(temp.getType() == Packet.LOGIN_DENY){
					//TODO
					//open deny 
					JOptionPane.showMessageDialog(panel, "Incorrect username or password. Please try again.");
				}else if (temp.getType() == Packet.CLOSE_CONNECTION){
					//TODO close window, server was shut down
					//TODO this is not functioning properly
					JOptionPane.showMessageDialog(panel, "Server shut down.");
					loginFrame.dispose();
				} else {
					//error message
					JOptionPane.showMessageDialog(panel, "Something terrible happened.");
				}
				
			}
		});
		submitB.setBounds(6, 85, 425, 25);
		panel.add(submitB);
	/*
	}else{
		onlyInstance.setVisible(true);
	}
	*/
	}
	
}
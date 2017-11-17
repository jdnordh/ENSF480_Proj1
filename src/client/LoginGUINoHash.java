package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
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
import java.security.MessageDigest;
import java.awt.event.ActionEvent;

public class LoginGUINoHash extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static LoginGUINoHash loginFrame;
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
					loginFrame = new LoginGUINoHash(Server.NAME, Server.PORT);
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
	public LoginGUINoHash(String serverName , int portNum) {
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
				String password = new String(passwordTF.getPassword());
				
				User user = new User();
				user.setUserName(usernameTF.getText());
				user.setPassword(password);
				
				
				System.out.println("Username: " + user.getUserName() + "\nPassword: " + user.getPassword());
				
				//create login packet
				Packet temp = new Packet(Packet.LOGIN);
				
				//add user to packet				
				temp.addUser(user);

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
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Type: "+ temp.getType());
				if(temp.getType() == Packet.LOGIN_CONFIRM_USER){
					System.out.println("opening user page");
					loginFrame.dispose();
					ClientGUI gui = new ClientGUI(user, input, output);
					gui.setVisible(true);
				}else if(temp.getType() == Packet.LOGIN_CONFIRM_ADMIN){
					System.out.println("opening Admin page");
					AdminGUI gui = new AdminGUI(user, input, output);
					gui.setVisible(true);
					loginFrame.dispose();
				}else if(temp.getType() == Packet.LOGIN_DENY){
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
	}
	
}
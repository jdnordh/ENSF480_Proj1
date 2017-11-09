package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import structures.Packet;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField usernameTF;
	private JTextField passwordTF;
	private static LoginGUI onlyInstance;
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
					LoginGUI frame = new LoginGUI("localhost", 65535);
					frame.setVisible(true);
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
			aSocket = new Socket(serverName , portNum);
			output = new ObjectOutputStream(aSocket.getOutputStream());
			input = new ObjectInputStream(aSocket.getInputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//if(onlyInstance == null){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "LoginPanel", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(199, 143, 443, 118);
		contentPane.add(panel);
		panel.setLayout(null);
		
		usernameTF = new JTextField();
		usernameTF.setBounds(102, 18, 329, 22);
		panel.add(usernameTF);
		usernameTF.setColumns(10);
		
		JLabel usernameL = new JLabel("username");
		usernameL.setBounds(6, 21, 84, 16);
		panel.add(usernameL);
		
		JLabel passwordL = new JLabel("password");
		passwordL.setBounds(6, 56, 84, 16);
		panel.add(passwordL);
		
		passwordTF = new JTextField();
		passwordTF.setBounds(102, 53, 329, 22);
		panel.add(passwordTF);
		passwordTF.setColumns(10);
		
		JButton submitB = new JButton("submit");
		submitB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String password = passwordTF.getText();
				MessageDigest digest;
				try {
					digest = MessageDigest.getInstance("SHA-256");
					String hashed_password = new String(digest.digest(password.getBytes(StandardCharsets.UTF_8)));
					System.out.print(hashed_password);
				} catch (NoSuchAlgorithmException e) {}
				/*
				Packet temp = new Packet(usernameTF.getText(), digest);
				output.writeObject(temp);
				Packet returned = (Packet) input.readObject();
				
				if(returned.LOGIN_CONFIRM_USER == 20){
					//open user gui
					//ClientGUI
				}else if(returned.LOGIN_CONFIRM_ADMIN == 21){
					//open admin gui
					//AdminGUI
				}else if(returned.LOGIN_DENY == 22){
					//open deny 
					JOptionPane.showMessageDialog(panel, "That was not correct username and password");
				}else{
					//error message
					JOptionPane.showMessageDialog(panel, "Something terrible happened");
				}
				*/
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
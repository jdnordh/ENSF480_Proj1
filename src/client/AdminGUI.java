package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import server.Server;
import structures.Packet;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class AdminGUI extends JFrame implements ClientGUIFunctionality {

	private JPanel contentPane;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Socket aSocket;
	private static AdminGUI onlyInstance;
	private Packet info;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminGUI frame = new AdminGUI(Server.NAME, Server.PORT);
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
	public AdminGUI(String serverName , int portNum) {
		//will add if empty right now it does not act as singleton
		onlyInstance = this;
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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton addUserButton = new JButton("addUser");
		addUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//create new frame to add user
				addUserFrame open = new addUserFrame(onlyInstance);
				open.setVisible(true);
				onlyInstance.setVisible(false);
				//the addUserFrame will create a packet set info in this class then send the pakcet 
				//from this class and admin will wait for response.
				try {
					info = (Packet) input.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(info.getType() == Packet.ADD_USER_CONFIRM){
					//let admin know success
					JOptionPane.showMessageDialog(null,"User has been added");
				}
				if (info.getType() == Packet.ADD_USER_DENY){
					//let admin know of denial
					JOptionPane.showMessageDialog(null,"User has been not been added something went wrong try again");
				}
				
			}
		});
		addUserButton.setBounds(161, 32, 97, 25);
		contentPane.add(addUserButton);
		
		JButton deletuserbtn = new JButton("deleteUser");
		deletuserbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DeleteUserFrame DUF = new DeleteUserFrame(onlyInstance);
				DUF.setVisible(true);
				onlyInstance.setVisible(false);
			}
		});
		deletuserbtn.setBounds(161, 85, 97, 25);
		contentPane.add(deletuserbtn);
		
	}

	@Override
	public void getAllMeetings() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initateMeeting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acceptMeeting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void declineMeeting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addLocationPref() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}

	public void setInfo(Packet x) {
		info = x;
	}
	public void sendPacket() throws IOException{
		output.writeObject(info);
	}
	public Packet getPacket(){
		return info;
	}
	public void recievePacket(){
		try {
			info = (Packet) input.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

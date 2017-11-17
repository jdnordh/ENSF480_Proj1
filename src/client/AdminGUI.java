package client;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;


import server.Server;
import server.ShutdownThread;
import structures.Meeting;
import structures.Packet;
import structures.User;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JList;
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
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;

public class AdminGUI extends JFrame implements ClientGUIFunctionality {

	private JPanel contentPane;
	private  ObjectInputStream input;
	private  ObjectOutputStream output;
	private Socket aSocket;
	private static AdminGUI onlyInstance;
	private Packet info;
	private String username;
	private Packet serverUpdate;
	private User Us;
	private ClientThread MyThread;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User un = new User("Jacob", "Jacob1243" , "123");
					AdminGUI frame = new AdminGUI(un,Server.NAME, Server.PORT);
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
	public AdminGUI(User Un,String serverName , int portNum) {
		//will add if empty right now it does not act as singleton
		Us = Un;
		onlyInstance = this;
		System.out.println("Creating Opening Page");
		try {
			InetAddress a = InetAddress.getByName(serverName);
			aSocket = new Socket(a , portNum);
			output = new ObjectOutputStream(aSocket.getOutputStream());
			input = new ObjectInputStream(aSocket.getInputStream());
			//set timeout
			aSocket.setSoTimeout(100);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//
		MyThread = new ClientThread(input,onlyInstance);
		MyThread.start();
		
		
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
				/*
				 * try {
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
				*/
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
		
		JButton editlocationbtn = new JButton("editLocation");
		editlocationbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
			}
		});
		editlocationbtn.setBounds(161, 123, 97, 25);
		contentPane.add(editlocationbtn);
		
		JButton createmeetingbtn = new JButton("createMeeting");
		createmeetingbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MeetingInitiatorFrame MIF = new MeetingInitiatorFrame(onlyInstance);
				MIF.setVisible(true);
				onlyInstance.setVisible(false);
				
			}
		});
		createmeetingbtn.setBounds(161, 177, 97, 25);
		contentPane.add(createmeetingbtn);
		
	}

	@Override
	public void getAllMeetings() {
		info = new Packet(Packet.REQUEST_ALL_MEETINGS);
		try {
			this.sendPacket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.recievePacket();
	}

	@Override
	public void initateMeeting() {
		
	}

	@Override
	public void acceptMeeting() {
		
		
		
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

	public User getUser() {return Us;}

	@Override
	public void getThreadPacket(Packet P) {
		info = P;
	}
	
}

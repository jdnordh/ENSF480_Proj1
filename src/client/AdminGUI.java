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
import javax.swing.SwingConstants;

/**
 * 
 * 
 * @author Jacob Turnbull
 *
 */
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
	/*
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
	*/
	/**
	 * Create the frame.
	 */
	public AdminGUI(User Un,ObjectInputStream ip ,ObjectOutputStream op) {
		//will add if empty right now it does not act as singleton
		Us = Un;
		onlyInstance = this;
		System.out.println("Creating Opening Page");
		output = op;
		input = ip;
		
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
				
			}
		
		});
		addUserButton.setBounds(12, 104, 147, 25);
		contentPane.add(addUserButton);
		
		JButton deletuserbtn = new JButton("deleteUser");
		deletuserbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DeleteUserFrame DUF = new DeleteUserFrame(onlyInstance);
				DUF.setVisible(true);
				onlyInstance.setVisible(false);
			}
		});
		deletuserbtn.setBounds(12, 142, 147, 25);
		contentPane.add(deletuserbtn);
		
		JButton editlocationbtn = new JButton("editLocation");
		editlocationbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LocationFrame LF = new LocationFrame(onlyInstance);
				LF.setVisible(true);
				onlyInstance.setVisible(false);
				
			}
		});
		editlocationbtn.setBounds(12, 177, 147, 25);
		contentPane.add(editlocationbtn);
		
		JButton createmeetingbtn = new JButton("createMeeting");
		createmeetingbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MeetingInitiatorFrame MIF = new MeetingInitiatorFrame(onlyInstance);
				MIF.setVisible(true);
				onlyInstance.setVisible(false);
				
			}
		});
		createmeetingbtn.setBounds(12, 215, 147, 25);
		contentPane.add(createmeetingbtn);
		
		JButton aMDPFbtn = new JButton("MeetingDatePrefs");
		aMDPFbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addMeetingDatePrefFrame aMDPF = new addMeetingDatePrefFrame(onlyInstance);
				aMDPF.setVisible(true);
				onlyInstance.setVisible(false);
				
			}
		});
		aMDPFbtn.setBounds(12, 69, 147, 25);
		contentPane.add(aMDPFbtn);
		
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
		System.out.println("Admin sending: "+info.getType());
		System.out.println("");
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
		if(P.getType() == Packet.NOTIFY_NEW_MEETING){
			JOptionPane.showMessageDialog(null,"You have a new Meeting please view");
		}
		if(P.getType() == Packet.NOTIFY_MEETING_DROP_REQUEST){
			JOptionPane.showMessageDialog(null,"You have been Droped from a meeting");
		}
		//Packet. your meeting failed
		 
		//if(P.getType() == Packet.n)
	}

	@Override
	public void removeMeeting(Meeting m) {
		// TODO Auto-generated method stub
		
	}
	
}

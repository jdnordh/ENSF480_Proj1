package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import structures.Packet;
import structures.User;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeleteUserFrame extends JFrame {

	private JPanel contentPane;
	private  ObjectInputStream input;
	private  ObjectOutputStream output;
	private Socket aSocket;
	private ArrayList<User> users;
	private AdminGUI owner;
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteUserFrame frame = new DeleteUserFrame();
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
	 * @throws IOException 
	 */
	public DeleteUserFrame(AdminGUI o){
		owner = o;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.addWindowListener(new java.awt.event.WindowAdapter() {
		        @Override
		        public void windowClosing(java.awt.event.WindowEvent e) {
		            owner.setVisible(true);
		        	e.getWindow().dispose();
		        }
		    });
		setBounds(100, 100, 554, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList();
		list.setBounds(58, 46, 241, 271);
		contentPane.add(list);
		
		JButton deleteuserbtn = new JButton("Delete");
		deleteuserbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				Packet p = new Packet(Packet.DELETE_USER);
				p.addUser(users.get(index));
				owner.setInfo(p);
				try {
					owner.sendPacket();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				owner.recievePacket();
				p = owner.getPacket();
				
				if(p.getType() == Packet.DELETE_USER_CONFIRM){
					JOptionPane.showMessageDialog(null,"The User has been Deleted");
					users.remove(index);
					list.remove(index);
				}
				if(p.getType() == Packet.DELETE_LOCATION_DENY){
					JOptionPane.showMessageDialog(null,"The User was not Deleted something went wrong");
				}
				
			}
		});
		deleteuserbtn.setBounds(368, 252, 97, 25);
		contentPane.add(deleteuserbtn);
		
		JLabel lblNewLabel = new JLabel("Select a user to delete then hit the delete button");
		lblNewLabel.setBounds(327, 123, 181, 102);
		contentPane.add(lblNewLabel);
		
		JLabel lblUsers = new JLabel("users");
		lblUsers.setBounds(152, 13, 56, 16);
		contentPane.add(lblUsers);
		
		Packet p = new Packet(Packet.REQUEST_ALL_USERS);
		owner.setInfo(p);
		try {
			owner.sendPacket();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		owner.recievePacket();
		p = owner.getPacket();
		
		users  = new ArrayList();
		users = p.getUsers();
		for(int i = 0; i < users.size(); i++)
			list.add(users.get(i).getUserName()+users.get(i).getName(), null);
	}
}

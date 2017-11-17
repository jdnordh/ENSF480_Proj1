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
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import structures.Packet;
import structures.User;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 * 
 * @author Jacob Turnbull
 *
 */
public class DeleteUserFrame extends JFrame {

	private JPanel contentPane;
	private ArrayList<User> users;
	private AdminGUI owner;
	private DefaultListModel listModel;
	private JButton backbtn;
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
		users = new ArrayList<User>();
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
		listModel = new DefaultListModel<String>();
		list.setModel(listModel);
		
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
				
				//owner.getThreadPacket(owner.getPacket());
				while(owner.getPacket().getType() != Packet.DELETE_USER_CONFIRM && owner.getPacket().getType() !=Packet.DELETE_LOCATION_DENY){
					try {
						System.out.println("waitting paket type : "+ owner.getPacket().getType());
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				//owner.recievePacket();
				p = owner.getPacket();
				
				if(p.getType() == Packet.DELETE_USER_CONFIRM){
					JOptionPane.showMessageDialog(null,"The User has been Deleted");
					users.remove(index);
					listModel.remove(index);
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
		
		
		backbtn =new JButton("Back");
		backbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeFrame();
			}

			

			
		});
		backbtn.setBounds(368, 290, 97, 25);
		contentPane.add(backbtn);
		
		JLabel lblUsers = new JLabel("users");
		lblUsers.setBounds(152, 13, 56, 16);
		contentPane.add(lblUsers);
		
		Packet p = new Packet(Packet.REQUEST_ALL_USERS);
		p.addUser(owner.getUser());
		owner.setInfo(p);
		try {
			owner.sendPacket();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(owner.getPacket().getType() != Packet.RESPONSE_ALL_USERS){
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//owner.recievePacket();
		p = owner.getPacket();
		users = p.getUsers();
		System.out.println("DeleteUserFrame Recieved:"+ users.size()+ "Users");
		for(int i = 0; i < users.size(); i++){
			listModel.addElement(users.get(i).getUserName());
	}
	}
	private void closeFrame() {
		owner.setVisible(true);
		this.dispose();
		}
}

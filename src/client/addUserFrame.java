package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import structures.Packet;
import structures.User;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
/**
 * 
 * 
 * @author Jacob Turnbull
 *
 */
public class addUserFrame extends JFrame {

	private JPanel contentPane;
	private JTextField usernametf;
	private JTextField passwordtf;
	private JTextField nametf;
	private JRadioButton isAdminrdbtn;
	private AdminGUI owner;
	private JButton backbtn;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addUserFrame frame = new addUserFrame();
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
	public addUserFrame(AdminGUI o) {
		System.out.println("Creating add UserFrame");
		owner = o;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 569, 382);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel Usernamelb = new JLabel("Username");
		Usernamelb.setBounds(85, 69, 78, 16);
		contentPane.add(Usernamelb);
		
		usernametf = new JTextField();
		usernametf.setBounds(210, 66, 116, 22);
		contentPane.add(usernametf);
		usernametf.setColumns(10);
		
		JLabel passwordlb = new JLabel("password");
		passwordlb.setBounds(85, 114, 78, 16);
		contentPane.add(passwordlb);
		
		passwordtf = new JTextField();
		passwordtf.setBounds(210, 111, 116, 22);
		contentPane.add(passwordtf);
		passwordtf.setColumns(10);
		
		JLabel namelb = new JLabel("name");
		namelb.setBounds(85, 157, 56, 16);
		contentPane.add(namelb);
		
		nametf = new JTextField();
		nametf.setBounds(210, 154, 116, 22);
		contentPane.add(nametf);
		nametf.setColumns(10);
		
		isAdminrdbtn = new JRadioButton("isAdmin");
		isAdminrdbtn.setBounds(199, 203, 127, 25);
		contentPane.add(isAdminrdbtn);
		
		
		JButton submitbtn = new JButton("submit");
		submitbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if anything is left out do nothing
				if(nametf.getText().equals("")||usernametf.getText().equals("")||passwordtf.getText().equals(""))
					return;
				//else create a user add it info packet and send it to server
				owner.setInfo(getinfo());
				try {
					owner.sendPacket();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("sent Packet to server");
				owner.getThreadPacket(owner.getPacket());
				System.out.println("closeing add user");
				closeFrame();
			}
		});
		submitbtn.setBounds(229, 250, 97, 25);
		contentPane.add(submitbtn);
		
		backbtn = new JButton("Back");
		backbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeFrame();
				owner.setVisible(true);
			}

			
		});
		backbtn.setBounds(229, 297, 97, 25);
		contentPane.add(backbtn);
	}
	private void closeFrame() {
		owner.setVisible(true);
		this.dispose();
	}
	public Packet getinfo() {
		Packet p = new Packet(Packet.ADD_USER);
		String password = passwordtf.getText();
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			String hashed_password = new String(digest.digest(password.getBytes(StandardCharsets.UTF_8)));
			System.out.println("hashed password: "+ hashed_password);
			User u = new User(nametf.getText(),usernametf.getText(),hashed_password,isAdminrdbtn.isSelected());
			p.addUser(u);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(nametf.getText()+usernametf.getText());
		return p;
	}
}

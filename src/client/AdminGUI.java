package client;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
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
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import server.Server;
import structures.Meeting;
import structures.Packet;
import structures.User;
/**
 * 
 */
public class AdminGUI extends JFrame implements ClientGUIFunctionality {

	private String username;

	//Windows
	protected static AdminGUI adminFrame;
	
	//Buttons
	private JButton viewMeetings = new JButton("View Meetings");
	private JButton initiateMeeting= new JButton("Initiate Meeting");
	private JButton viewPending = new JButton("View Pending Meetings");
	private JButton editLocationPrefs = new JButton("Edit Location Preferences");
	private JButton removeMeeting = new JButton("Remove Meeting");
	private JButton returnToMain = new JButton("Return to Main Menu");
	private JButton acceptMeeting = new JButton("Accept");
	private JButton declineMeeting = new JButton("Decline");
	private JButton addLocation = new JButton("Add");
	private JButton removeLocation = new JButton("Remove");
	private JButton addUsers = new JButton("Add Users");
	private JButton removeUsers = new JButton("Remove Users");
	private JButton createUser = new JButton("Create User");
	private JButton removeSelectedUser = new JButton("Remove User");
	
	//Lists
	protected JList<Meeting> meetingList;
	private DefaultListModel<Meeting> meetingModel;
	protected JList<User> userList;
	private DefaultListModel<User> userModel;
	
	//Text area
	protected JTextArea textArea;
	
	//Text fields
	private JTextField location;
	private JTextField newUsername;
	private JTextField newPassword;

	
    public AdminGUI(String un) {
    	adminFrame = this;
    	username = un;
		this.setTitle("Welcome " + username + "!");
		this.setBounds(100, 100, 300, 250);
		this.setLayout(new GridLayout(6, 1));
		
		JPanel panel1 = new JPanel();
		viewMeetings.addActionListener(new ClientListener());
		panel1.add(viewMeetings);
		this.add(panel1);
		
		JPanel panel2 = new JPanel();
		initiateMeeting.addActionListener(new ClientListener());
		panel2.add(initiateMeeting);
		this.add(panel2);
		
		JPanel panel3 = new JPanel();
		viewPending.addActionListener(new ClientListener());
		panel3.add(viewPending);
		this.add(panel3);

		JPanel panel4 = new JPanel();
		editLocationPrefs.addActionListener(new ClientListener());
		panel4.add(editLocationPrefs);
		this.add(panel4);
		
		JPanel panel5 = new JPanel();
		addUsers.addActionListener(new ClientListener());
		panel5.add(addUsers);
		this.add(panel5);
		
		JPanel panel6 = new JPanel();
		removeUsers.addActionListener(new ClientListener());
		panel6.add(removeUsers);
		this.add(panel6);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
    }
    
    //default ctor
    public AdminGUI() {}

	public AdminGUI viewMeetingsFrame() {
		AdminGUI tmp = new AdminGUI();
		tmp.setTitle("Welcome " + username + "!");
		tmp.setBounds(100, 100, 400, 400);
		
		//create the list area
		meetingModel = new DefaultListModel<Meeting>();
		meetingList = new JList<Meeting>(meetingModel);
		meetingList.addListSelectionListener(new ListAction());
		meetingList.setFont(new Font("Courier New", Font.BOLD, 12));
		meetingList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		meetingList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		meetingList.setVisibleRowCount(-1);
		JScrollPane textAreaScrollPane = new JScrollPane();
		textArea = new JTextArea();
		textAreaScrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		textArea.setFont(new Font("Courier New", Font.BOLD, 12));
		textArea.setEditable(false);
		textAreaScrollPane = new JScrollPane(meetingList);
		textAreaScrollPane.setPreferredSize(new Dimension(190, 400));
		
		//TODO fill the text area
		
		//create the label
		JLabel label = new JLabel("Scheduled meetings:");

		//create the panel
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel1.add(label);
		panel1.add(Box.createRigidArea(new Dimension(0,5)));
		panel1.add(textAreaScrollPane);
		panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		//add to the container
		tmp.add(panel1, BorderLayout.CENTER);
		
		//create button panel
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));
		panel2.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		panel2.add(Box.createHorizontalGlue());
		removeMeeting.addActionListener(new ClientListener());
		returnToMain.addActionListener(new ClientListener());		
		panel2.add(removeMeeting);
		panel2.add(Box.createRigidArea(new Dimension(10, 0)));
		panel2.add(returnToMain);
		
		//add to the container
		tmp.add(panel2, BorderLayout.PAGE_END);
		
		tmp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tmp.setVisible(true);
		return tmp;
	}
	
	public AdminGUI viewPendingMeetingsFrame() {
		AdminGUI tmp = new AdminGUI();
		tmp.setTitle("Welcome " + username + "!");
		tmp.setBounds(100, 100, 400, 400);
		
		//create the list area
		meetingModel = new DefaultListModel<Meeting>();
		meetingList = new JList<Meeting>(meetingModel);
		meetingList.addListSelectionListener(new ListAction());
		meetingList.setFont(new Font("Courier New", Font.BOLD, 12));
		meetingList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		meetingList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		meetingList.setVisibleRowCount(-1);
		JScrollPane textAreaScrollPane = new JScrollPane();
		textArea = new JTextArea();
		textAreaScrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		textArea.setFont(new Font("Courier New", Font.BOLD, 12));
		textArea.setEditable(false);
		textAreaScrollPane = new JScrollPane(meetingList);
		textAreaScrollPane.setPreferredSize(new Dimension(190, 400));
		
		//TODO fill the text area
		
		//create the label
		JLabel label = new JLabel("Pending meetings:");

		//create the panel
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel1.add(label);
		panel1.add(Box.createRigidArea(new Dimension(0,5)));
		panel1.add(textAreaScrollPane);
		panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		//add to the container
		tmp.add(panel1, BorderLayout.CENTER);
		
		//create button panel
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));
		panel2.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		panel2.add(Box.createHorizontalGlue());
		acceptMeeting.addActionListener(new ClientListener());
		declineMeeting.addActionListener(new ClientListener());
		returnToMain.addActionListener(new ClientListener());		
		panel2.add(acceptMeeting);
		panel2.add(Box.createRigidArea(new Dimension(10, 0)));
		panel2.add(declineMeeting);
		panel2.add(Box.createRigidArea(new Dimension(10, 0)));
		panel2.add(returnToMain);
		
		//add to the container
		tmp.add(panel2, BorderLayout.PAGE_END);
		
		tmp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tmp.setVisible(true);
		return tmp;
	}
		
	public AdminGUI editLocationPrefsFrame() {
		AdminGUI tmp = new AdminGUI();
		tmp.setTitle("Welcome " + username + "!");
		tmp.setBounds(100, 100, 400, 150);
		
		//create the text field
		location = new JTextField(50);
		location.setMaximumSize(new Dimension(100, 25));
		
		//create the label
		JLabel label = new JLabel("Location:");

		//create the panel
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		addLocation.addActionListener(new ClientListener());
		removeLocation.addActionListener(new ClientListener());
		panel1.add(label);
		panel1.add(Box.createRigidArea(new Dimension(10, 0)));
		panel1.add(location);
		panel1.add(Box.createRigidArea(new Dimension(10, 0)));
		panel1.add(addLocation);
		panel1.add(Box.createRigidArea(new Dimension(5, 0)));
		panel1.add(removeLocation);
		panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		//add to the container
		tmp.add(panel1, BorderLayout.CENTER);
		
		//create button panel
		JPanel panel2 = new JPanel();
		//panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
		panel2.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		//panel2.add(Box.createHorizontalGlue());
		returnToMain.addActionListener(new ClientListener());		
		panel2.add(returnToMain, BorderLayout.CENTER);
		
		//add to the container
		tmp.add(panel2, BorderLayout.PAGE_END);
		
		tmp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tmp.setVisible(true);
		return tmp;
	}
	
	public AdminGUI addUsersFrame(){
		AdminGUI tmp = new AdminGUI();
		tmp.setTitle("Welcome " + username + "!");
		tmp.setBounds(100, 100, 400, 195);
		tmp.setLayout(new GridLayout(4, 1));
		
		//create the text field
		newUsername = new JTextField(50);
		newUsername.setMaximumSize(new Dimension(50, 25));
		newPassword = new JTextField(50);
		newPassword.setMaximumSize(new Dimension(50, 25));
		
		
		//create the panel
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		panel1.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
		JLabel label1 = new JLabel("Create User:");
		panel1.add(label1);
		tmp.add(panel1);
		
		//create the panel
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		panel2.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
		JLabel label2 = new JLabel("Username:");
		panel2.add(label2);
		panel2.add(Box.createRigidArea(new Dimension(10, 0)));
		panel2.add(newUsername);
		tmp.add(panel2);

		JPanel panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
		panel3.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
		JLabel label3 = new JLabel("Password:");
		panel3.add(label3);
		panel3.add(Box.createRigidArea(new Dimension(10, 0)));
		panel3.add(newPassword);
		tmp.add(panel3);
		
		JPanel panel4 = new JPanel();
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.LINE_AXIS));
		panel4.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		panel4.add(Box.createHorizontalGlue());
		createUser.addActionListener(new ClientListener());
		returnToMain.addActionListener(new ClientListener());
		panel4.add(createUser);
		panel4.add(Box.createRigidArea(new Dimension(10, 0)));
		panel4.add(returnToMain);
		tmp.add(panel4, BorderLayout.CENTER);
		
		
		tmp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tmp.setVisible(true);
		return tmp;
	}
	
	public AdminGUI removeUsersFrame(){
		AdminGUI tmp = new AdminGUI();
		tmp.setTitle("Welcome " + username + "!");
		tmp.setBounds(100, 100, 400, 400);
		
		//TODO it is not turning white for some reason
		//create the list area
		userModel = new DefaultListModel<User>();
		userList = new JList<User>(userModel);
		userList.addListSelectionListener(new ListAction());
		userList.setFont(new Font("Courier New", Font.BOLD, 12));
		userList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		userList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		userList.setVisibleRowCount(-1);
		JScrollPane textAreaScrollPane = new JScrollPane();
		textArea = new JTextArea();
		textAreaScrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		textArea.setFont(new Font("Courier New", Font.BOLD, 12));
		textArea.setEditable(false);
		textAreaScrollPane = new JScrollPane(meetingList);
		textAreaScrollPane.setPreferredSize(new Dimension(190, 400));
		
		
		//TODO fill the text area
		
		//create the label
		JLabel label = new JLabel("All Users:");

		//create the panel
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel1.add(label);
		panel1.add(Box.createRigidArea(new Dimension(0,5)));
		panel1.add(textAreaScrollPane);
		panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		//add to the container
		tmp.add(panel1, BorderLayout.CENTER);
		
		//create button panel
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));
		panel2.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		panel2.add(Box.createHorizontalGlue());
		removeSelectedUser.addActionListener(new ClientListener());
		returnToMain.addActionListener(new ClientListener());		
		panel2.add(removeSelectedUser);
		panel2.add(Box.createRigidArea(new Dimension(10, 0)));
		panel2.add(returnToMain);
		
		//add to the container
		tmp.add(panel2, BorderLayout.PAGE_END);
		
		tmp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tmp.setVisible(true);
		return tmp;
	}

    
	private class ClientListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == viewMeetings) {
				try {
					System.out.println("viewMeetings pressed");
					adminFrame.dispose();
					adminFrame = viewMeetingsFrame();
				} catch (Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == initiateMeeting) {
				try {
					System.out.println("initiateMeeting pressed");
					//TODO initiate meeting window
				} catch (Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == viewPending) {
				try {
					System.out.println("viewPending pressed");
					adminFrame.dispose();
					adminFrame = viewPendingMeetingsFrame();
				} catch (Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == editLocationPrefs) {
				try {
					System.out.println("editLocationPrefs pressed");
					adminFrame.dispose();
					adminFrame = editLocationPrefsFrame();
					//TODO editLocationPrefs 
				} catch (Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == removeMeeting) {
				try {
					System.out.println("removeMeeting pressed");
					//TODO remove selected meeting from the list 
				} catch (Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == addUsers) {
				try {
					System.out.println("addUsers pressed");
					adminFrame.dispose();
					adminFrame = addUsersFrame();
				} catch (Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == removeUsers) {
				try {
					System.out.println("removeUsers pressed");
					adminFrame.dispose();
					adminFrame = removeUsersFrame();
				} catch (Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == returnToMain) {
				try {
					System.out.println("return to main menu pressed");
					adminFrame.dispose();
					adminFrame = new AdminGUI(username);
				} catch (Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == acceptMeeting) {
				try {
					System.out.println("acceptMeeting pressed");
					//TODO accept selected meeting
				} catch (Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == declineMeeting) {
				try {
					System.out.println("declineMeeting pressed");
					//TODO decline selected meeting
				} catch (Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == addLocation) {
				try {
					System.out.println("addLocation pressed");
					//TODO add location by name
				} catch (Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == removeLocation) {
				try {
					System.out.println("removeLocation pressed");
					//TODO decline selected meeting
				} catch (Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == createUser) {
				try {
					System.out.println("createUser pressed");
					//TODO create the new user
				} catch (Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == removeSelectedUser) {
				try {
					System.out.println("removeSelectedUser pressed");
					//TODO remove selected user
				} catch (Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
		}
	}
	
	private class ListAction implements ListSelectionListener {
		@SuppressWarnings("unchecked")
		@Override
		public void valueChanged(ListSelectionEvent e) {
			Object o = ((JList<Meeting>)e.getSource()).getSelectedValue();
			if (o instanceof Meeting){
				Meeting m = (Meeting) o;
			}
		}
	}

	
	//TODO GUI Functionality
	@Override
	public void getAllMeetings() {
		
	}

	@Override
	public void initateMeeting() {
		
	}

	@Override
	public void acceptMeeting() {
		
	}

	@Override
	public void declineMeeting() {
		
	}

	@Override
	public void addLocationPref() {
		
	}

	@Override
	public void Update() {
		
	}

	public static void main(String[] args) {
		new AdminGUI("Admin");
	}



}
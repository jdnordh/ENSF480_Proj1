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
public class ClientGUI extends JFrame implements ClientGUIFunctionality {

	private String username;
	
	//Windows
	private static ClientGUI clientFrame;
	private static ClientGUI viewMeetingsFrame;
	
	//Buttons
	private JButton viewMeetings = new JButton("View Meetings");
	private JButton initiateMeeting= new JButton("Initiate meeting");
	private JButton viewPending = new JButton("View Pending Meetings");
	private JButton editLocationPrefs = new JButton("Edit Location Preferences");
	private JButton removeMeeting = new JButton("Remove meeting");
	private JButton returnToMain = new JButton("Return to main menu");
	private JButton addLocation = new JButton("Add");
	private JButton removeLocation = new JButton("Remove");
	
	//Lits
	protected JList<Meeting> meetingList;
	private DefaultListModel<Meeting> meetingModel;
	
	//Text area
	protected JTextArea textArea;
	
	//Text fields
	private JTextField location;
	
    /**
     * Default constructor
     */
    public ClientGUI(String un) {
    	clientFrame = this;
    	username = un;
		this.setTitle("Welcome " + username + "!");
		this.setBounds(100, 100, 300, 200);
		this.setLayout(new GridLayout(4, 1));
		
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
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
    
    public ClientGUI() {
		// TODO Auto-generated constructor stub
	}

	public ClientGUI viewMeetingsFrame() {
    	ClientGUI tmp = new ClientGUI();
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
	
	public ClientGUI viewPendingMeetingsFrame() {
    	ClientGUI tmp = new ClientGUI();
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
	
	public ClientGUI editLocationPrefsFrame() {
    	ClientGUI tmp = new ClientGUI();
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
    
	private class ClientListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == viewMeetings) {
				try {
					System.out.println("viewMeetings pressed");
					clientFrame.dispose();
					clientFrame = viewMeetingsFrame();
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
					clientFrame.dispose();
					clientFrame = viewPendingMeetingsFrame();
				} catch (Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == editLocationPrefs) {
				try {
					System.out.println("editLocationPrefs pressed");
					clientFrame.dispose();
					clientFrame = editLocationPrefsFrame();
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
			else if (e.getSource() == returnToMain) {
				try {
					System.out.println("return to main menu pressed");
					clientFrame.dispose();
					clientFrame = new ClientGUI(username);
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
	
	public static void main(String[] args) {
		new ClientGUI("User");
	}

   

}
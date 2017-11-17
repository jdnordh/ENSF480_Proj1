package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import structures.Location;
import structures.Meeting;
import structures.Packet;
import structures.Participant;
import structures.User;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
/**
 * 
 * 
 * @author Jacob Turnbull
 *
 */
public class MeetingInitiatorFrame extends JFrame {

	private JPanel contentPane;
	private JTextField descriptiontf;
	private AdminGUI owner;
	private JButton backbtn;
	
	//lists
	private DefaultListModel<String> allParticipantsListModel; 
	private static JList allParticipantsList;
	private DefaultListModel<String> allLocationsListModel; 
	private static JList allLocationsList;
	private DefaultListModel<String> meetingParticipantsListModel; 
	private static JList meetingParticipantsList;
	private DefaultListModel<String> meetingLocationsListModel; 
	private static JList meetingLocationsList;
	private JList allDateList;
	private DefaultListModel<String> allDateListModel;
	private JList meetingDateList;
	private DefaultListModel<String> meetingDateListModel;
	
	//arraylists
	private ArrayList<Location> meetingLocations;
	private ArrayList<Location> allLocations;
	private ArrayList<Participant> meetingParticipants;
	private ArrayList<User> allUsers;
	private ArrayList<Date> allDates;
	private ArrayList<Date> meetingDates;
	
	
	
	private JButton datebtn;
	private JLabel lblDates;
	private JButton importantbtn;
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MeetingInitiatorFrame frame = new MeetingInitiatorFrame();
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
	public MeetingInitiatorFrame(AdminGUI o) {
		allDates = new ArrayList<Date>();
		meetingDates =new ArrayList<Date>();
		allLocations = new ArrayList<Location>();
		meetingLocations = new ArrayList<Location>();
		allUsers = new ArrayList<User>();
		meetingParticipants= new ArrayList<Participant>();
		owner = o;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.addWindowListener(new java.awt.event.WindowAdapter() {
		        @Override
		        public void windowClosing(java.awt.event.WindowEvent e) {
		            owner.setVisible(true);
		        	e.getWindow().dispose();
		        }
		    });
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1005, 905);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		allParticipantsList = new JList();
		allParticipantsList.setBounds(12, 52, 338, 253);
		contentPane.add(allParticipantsList);
		allParticipantsListModel = new DefaultListModel<String>();
		allParticipantsList.setModel(allParticipantsListModel);
		
		
		meetingParticipantsList = new JList();
		meetingParticipantsList.setBounds(424, 52, 338, 253);
		contentPane.add(meetingParticipantsList);
		meetingParticipantsListModel= new DefaultListModel<String>();
		meetingParticipantsList.setModel(meetingParticipantsListModel);
		
		//location lists
		allLocationsList = new JList();
		allLocationsList.setBounds(12, 344, 338, 236);
		contentPane.add(allLocationsList);
		allLocationsListModel = new DefaultListModel<String>();
		allLocationsList.setModel(allLocationsListModel);
		
		meetingLocationsList = new JList();
		meetingLocationsList.setBounds(424, 327, 338, 253);
		contentPane.add(meetingLocationsList);
		meetingLocationsListModel= new DefaultListModel<String>();
		meetingLocationsList.setModel(meetingLocationsListModel);
		
		//date lists
		allDateList = new JList();
		allDateList.setBounds(12, 613, 338, 203);
		contentPane.add(allDateList);
		allDateListModel =new DefaultListModel<String>();
		allDateList.setModel(allDateListModel); 
		allDateList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		
		meetingDateList = new JList();
		meetingDateList.setBounds(424, 613, 338, 203);
		contentPane.add(meetingDateList);
		meetingDateListModel=new DefaultListModel<String>();
		meetingDateList.setModel(meetingDateListModel);
		meetingDateList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		
		JButton locationbtn = new JButton("add");
		locationbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(allLocationsList.isSelectionEmpty())
					return;
				int index = allLocationsList.getSelectedIndex();
				
				meetingLocationsListModel.addElement(allLocations.get(index).getAddress()+" "+allLocations.get(index).getName());
				meetingLocations.add(allLocations.get(index));
				allLocationsListModel.remove(index);
				allLocations.remove(index);
			}
		});
		locationbtn.setBounds(355, 423, 66, 25);
		contentPane.add(locationbtn);
		
		JButton participantbtn = new JButton("add");
		participantbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(allParticipantsList.isSelectionEmpty())
					return;
				int index = allParticipantsList.getSelectedIndex();
				
				meetingParticipantsListModel.addElement(allUsers.get(index).getUserName());
				Participant Part = new Participant(allUsers.get(index));
				meetingParticipants.add(Part);
				allParticipantsListModel.remove(index);
				allUsers.remove(index);
			}
		});
		participantbtn.setBounds(355, 117, 66, 25);
		contentPane.add(participantbtn);
		
		JLabel Participantslb = new JLabel("Participants");
		Participantslb.setBounds(138, 23, 113, 16);
		contentPane.add(Participantslb);
		
		JLabel lblNewLabel_1 = new JLabel("Locations");
		lblNewLabel_1.setBounds(138, 315, 146, 16);
		contentPane.add(lblNewLabel_1);
		
		descriptiontf = new JTextField();
		descriptiontf.setBounds(774, 82, 201, 223);
		contentPane.add(descriptiontf);
		descriptiontf.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Describtion");
		lblNewLabel.setBounds(777, 53, 198, 16);
		contentPane.add(lblNewLabel);
		
		JButton createMeetingbtn = new JButton("createMeeting");
		createMeetingbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(meetingParticipants.isEmpty()||meetingDates.isEmpty()||descriptiontf.getText() == ""|| meetingLocations.isEmpty())
					return;
				Packet P = new Packet(Packet.INITIATE_MEETING);
				
				if(meetingLocations.size() == 1){
					//participants,Location, Description, MeetingIniatorPrefDates , MeetingIniator
					Meeting m = new Meeting(meetingParticipants, meetingLocations.get(0), descriptiontf.getText(),meetingDates ,owner.getUser() );
					P.addMeeting(m);
					P.addUser(owner.getUser());
					owner.setInfo(P);
					try {
						owner.sendPacket();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//wait for input
					while(owner.getPacket().getType() != Packet.INITIATE_MEETING_CONFIRM){
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					//owner.recievePacket();
					P = owner.getPacket();
					if(Packet.INITIATE_MEETING_CONFIRM  == P.getType()){
						JOptionPane.showMessageDialog(null, "Meeting made successfully");
						closeFrame();
					}
				}
				if(meetingLocations.size() != 1){
					Meeting m = new Meeting(meetingParticipants,meetingLocations, descriptiontf.getText(),meetingDates ,owner.getUser() );
					P.addMeeting(m);
					P.addUser(owner.getUser());
					owner.setInfo(P);
					try {
						owner.sendPacket();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//wait for input
					while(owner.getPacket().getType() != Packet.INITIATE_MEETING_CONFIRM){
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					//owner.recievePacket();
					P = owner.getPacket();
					if(Packet.INITIATE_MEETING_CONFIRM  == P.getType()){
						JOptionPane.showMessageDialog(null, "Meeting made successfully");
						closeFrame();
					}
				}
				
			}
		});
		createMeetingbtn.setBounds(774, 327, 201, 253);
		contentPane.add(createMeetingbtn);
		
		datebtn = new JButton("add");
		datebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(allDateList.isSelectionEmpty())
					return;
				int index = allDateList.getSelectedIndex();
				
				meetingDateListModel.addElement(format1.format(allDates.get(index+1)));
				meetingDates.add(allDates.get(index+1));
				allDateListModel.remove(index);
				allDates.remove(index+1);
			}
		});
		datebtn.setBounds(355, 691, 66, 25);
		contentPane.add(datebtn);
		
		lblDates = new JLabel("Dates");
		lblDates.setBounds(150, 584, 56, 16);
		contentPane.add(lblDates);
		
		importantbtn = new JButton("important");
		importantbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(allParticipantsList.isSelectionEmpty())
					return;
				int index = allParticipantsList.getSelectedIndex();
				
				meetingParticipantsListModel.addElement(allUsers.get(index).getUserName());
				Participant Part = new Participant(allUsers.get(index));
				Part.setImportant(true);
				meetingParticipants.add(Part);
				allParticipantsListModel.remove(index);
				allUsers.remove(index);
			}
		});
		importantbtn.setBounds(355, 159, 66, 47);
		contentPane.add(importantbtn);
		
		Date D = new Date();
		
		allDates.add(D);
		for(int i = 0; i< 30 ;i++){
			allDateListModel.addElement(format1.format(D));
			allDates.add(D);
			D = addDays(D,1);
		}
		
		Packet P = new Packet(Packet.REQUEST_ALL_LOCATIONS);
		P.addUser(owner.getUser());
		owner.setInfo(P);
		try {
			owner.sendPacket();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(owner.getPacket().getType() != Packet.RESPONSE_ALL_LOCATIONS){
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//owner.recievePacket();
		P = owner.getPacket();
		if(P.getType() == Packet.RESPONSE_ALL_LOCATIONS){
			System.out.println("Locations got: " +P.getLocations().size());
			allLocations = P.getLocations();
			for(int i = 0; i < allLocations.size(); i++)
				allLocationsListModel.addElement(allLocations.get(i).getAddress()+" "+allLocations.get(i).getName());
		}
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
		allUsers = p.getUsers();
		for(int i = 0; i < allUsers.size(); i++){
			allParticipantsListModel.addElement(allUsers.get(i).getUserName());
	}
		backbtn = new JButton("Back");
		backbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeFrame();
				owner.setVisible(true);
			}
		});
		backbtn.setBounds(818, 691, 97, 25);
		contentPane.add(backbtn);
	}
	public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
	public void closeFrame(){
		owner.setVisible(true);
		this.dispose();
	}
}

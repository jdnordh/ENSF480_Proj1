package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;

import structures.Location;
import structures.Meeting;
import structures.Packet;

import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
/**
 * 
 * 
 * @author Jacob Turnbull
 *
 */
public class addMeetingDatePrefFrame extends JFrame {

	private JPanel contentPane;
	private JTextField desciptiontf;
	private JTextField mitf;
	private JTextField ltf;
	private JButton sumbitbtn;
	private AdminGUI owner;
	
	private DefaultListModel<String> pDatePrefListModel; 
	private static JList pDatePrefList;
	private DefaultListModel<String> miDatePrefListModel; 
	private static JList miDatePrefList;
	private DefaultListModel<String> pLocationPrefListModel; 
	private static JList pLocationPrefList;
	private DefaultListModel<String> miLocationPrefListModel; 
	private static JList miLocationPrefList;
	private DefaultListModel<Meeting> userMeetingListModel;
	private static JList userMeetingList;
	
	private ArrayList<Meeting> Meetings;
	private ArrayList<Date> miDates;
	private ArrayList<Location> miLocationPrefs;
	private ArrayList<Date> pDates;
	private ArrayList<Location> pLocationPrefs;

	/**
	 * Create the frame.
	 */
	public addMeetingDatePrefFrame(AdminGUI o) {
		Meetings = new ArrayList<Meeting>();
		miDates = new ArrayList<Date>();
		pDates = new ArrayList<Date>();
		miLocationPrefs = new ArrayList<Location>();
		pLocationPrefs = new ArrayList<Location>();
		
		owner = o;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 951, 642);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		miLocationPrefList = new JList();
		miLocationPrefList.setBounds(338, 195, 222, 163);
		contentPane.add(miLocationPrefList);
		miLocationPrefListModel= new DefaultListModel<String>();
		miLocationPrefList.setModel(miLocationPrefListModel);
		
		JButton locationbtn = new JButton("add");
		locationbtn.setBounds(571, 242, 97, 25);
		contentPane.add(locationbtn);
		
		pDatePrefList = new JList();
		pDatePrefList.setBounds(680, 385, 222, 197);
		contentPane.add(pDatePrefList);
		
		JLabel lblNewLabel = new JLabel("Please pick your dates for your meetings and locations if acceptable");
		lblNewLabel.setBounds(418, 38, 412, 16);
		contentPane.add(lblNewLabel);
		
		JLabel Desciptionlb = new JLabel("Desciption");
		Desciptionlb.setBounds(338, 92, 86, 16);
		contentPane.add(Desciptionlb);
		
		desciptiontf = new JTextField();
		desciptiontf.setBounds(437, 89, 465, 22);
		contentPane.add(desciptiontf);
		desciptiontf.setColumns(10);
		
		JLabel meetingInitiatorlb = new JLabel("meetingInitiator");
		meetingInitiatorlb.setBounds(338, 124, 90, 16);
		contentPane.add(meetingInitiatorlb);
		
		mitf = new JTextField();
		mitf.setColumns(10);
		mitf.setBounds(437, 121, 465, 22);
		contentPane.add(mitf);
		
		JLabel locationlb = new JLabel("Location");
		locationlb.setBounds(338, 153, 90, 16);
		contentPane.add(locationlb);
		
		ltf = new JTextField();
		ltf.setColumns(10);
		ltf.setBounds(437, 156, 465, 22);
		contentPane.add(ltf);
		
		miDatePrefList = new JList();
		miDatePrefList.setBounds(338, 371, 222, 211);
		contentPane.add(miDatePrefList);
		miDatePrefListModel = new DefaultListModel<String>();
		miDatePrefList.setModel(miDatePrefListModel);
		
		pLocationPrefList = new JList();
		pLocationPrefList.setBounds(680, 195, 222, 163);
		contentPane.add(pLocationPrefList);
		
		JButton dateprefbtn = new JButton("add");
		dateprefbtn.setBounds(571, 460, 97, 25);
		contentPane.add(dateprefbtn);
		
		sumbitbtn = new JButton("submit");
		sumbitbtn.setBounds(571, 519, 97, 25);
		contentPane.add(sumbitbtn);
		
		JButton backbtn = new JButton("back");
		backbtn.setBounds(571, 557, 97, 25);
		contentPane.add(backbtn);
		
		userMeetingList = new JList();
		userMeetingList.setBounds(12, 13, 308, 569);
		contentPane.add(userMeetingList);
		userMeetingListModel = new DefaultListModel<Meeting>();
		userMeetingList.setModel(userMeetingListModel);
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		
		userMeetingList.getSelectionModel().addListSelectionListener(x-> {
			Meeting m = (Meeting) userMeetingList.getSelectedValue();
			desciptiontf.setText(m.getDescription());
			mitf.setText(m.getMeetingInitiator().toString());
			if(m.getmeetingState() == Meeting.waitingForDates){
				for(int i =0; i <m.getPreferedDates().size() ; i++){
					miDatePrefListModel.addElement(format1.format(m.getPreferedDates().get(i)));
					miDates.add(m.getPreferedDates().get(i));
				}
			}
			if(m.getmeetingState() == Meeting.waitingForLocationPref){
				int newsize = 0;
				for(int j = 0 ; j<m.getLocations().size(); j++){
					for(int k = 0; j< m.getLocations().size(); k++){
						if(m.getLocations().get(j).equals(m.getLocations().get(k)))
							newsize = k;
					}
				}
				if(newsize  == 0){newsize =m.getLocations().size();}
				for(int i =0; i <newsize ; i++){
					miLocationPrefListModel.addElement(m.getLocations().toString());
					miLocationPrefs.add(m.getLocations().get(i));
				}
			}
			
			
		});
		
		Packet P = new Packet(Packet.REQUEST_ALL_MEETINGS);
		P.addUser(owner.getUser());
		owner.setInfo(P);
		try {
			owner.sendPacket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(owner.getPacket().getType() != Packet.RESPONSE_ALL_MEETINGS){
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		P = owner.getPacket();
		ArrayList<Meeting> allMeetings = P.getMeetings();
		
		for(int i =0 ; i < allMeetings.size() ; i++){
			if(allMeetings.get(i).getmeetingState() ==Meeting.waitingForDates|| allMeetings.get(i).getmeetingState() == Meeting.waitingForLocationPref){
				userMeetingListModel.addElement(allMeetings.get(i));
				Meetings.add(allMeetings.get(i));
			}
		}
		
		
		
	}
}

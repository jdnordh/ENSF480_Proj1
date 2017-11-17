package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import structures.Location;
import structures.Packet;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class LocationFrame extends JFrame {

	private JPanel contentPane;
	private AdminGUI owner;
	private JTextField citytf;
	private JTextField nametf;
	private JTextField addresstf;
	private DefaultListModel<String> listModel; 
	private static JList list;
	private ArrayList<Location> Locations;
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LocationFrame frame = new LocationFrame();
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
	public LocationFrame(AdminGUI o) {
		owner = o;
		Locations = new ArrayList<Location>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.addWindowListener(new java.awt.event.WindowAdapter() {
		        @Override
		        public void windowClosing(java.awt.event.WindowEvent e) {
		            owner.setVisible(true);
		        	e.getWindow().dispose();
		        }
		    });
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 682, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		list = new JList();
		list.setBounds(12, 35, 421, 367);
		contentPane.add(list);
		listModel = new DefaultListModel<String>();
		list.setModel(listModel);
		
		JLabel lblLocations = new JLabel("Locations");
		lblLocations.setBounds(150, 13, 56, 16);
		contentPane.add(lblLocations);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "JPanel title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(445, 13, 207, 168);
		contentPane.add(panel);
		panel.setLayout(null);
		
		citytf = new JTextField();
		citytf.setBounds(85, 53, 116, 22);
		panel.add(citytf);
		citytf.setColumns(10);
		
		JButton addLocationbtn = new JButton("Add Lcoation");
		addLocationbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Location (name, city , address)
				Location L = new Location(nametf.getText(), citytf.getText() , addresstf.getText());
				Packet P = new Packet(Packet.ADD_LOCATION);
				owner.setInfo(P);
				try {
					owner.sendPacket();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//wait for input back
				P = owner.getPacket();
			}
		});
		addLocationbtn.setBounds(6, 136, 195, 25);
		panel.add(addLocationbtn);
		
		nametf = new JTextField();
		nametf.setBounds(85, 88, 116, 22);
		panel.add(nametf);
		nametf.setColumns(10);
		
		addresstf = new JTextField();
		addresstf.setBounds(85, 18, 116, 22);
		panel.add(addresstf);
		addresstf.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("city");
		lblNewLabel.setBounds(17, 56, 56, 16);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("address");
		lblNewLabel_1.setBounds(6, 21, 56, 16);
		panel.add(lblNewLabel_1);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(17, 91, 56, 16);
		panel.add(lblName);
		
		JButton removebtn = new JButton("Remove Location");
		removebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Packet P = new Packet(Packet.DELETE_LOCATION);
				int index = list.getSelectedIndex();
				P.addLocation(Locations.get(index));
				owner.setInfo(P);
				try {
					owner.sendPacket();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//wait for response
				
				P = owner.getPacket();
				if(P.getType() == Packet.DELETE_LOCATION_CONFIRM){
					listModel.remove(index);
					Locations.remove(index);
				}
				if(P.getType() == Packet.DELETE_LOCATION_DENY){
					JOptionPane.showMessageDialog(null,"something went wrong refresh");
				}
				
			}
		});
		removebtn.setBounds(457, 213, 195, 25);
		contentPane.add(removebtn);
		
		Packet P = new Packet(Packet.REQUEST_ALL_LOCATIONS);
		owner.setInfo(P);
		try {
			owner.sendPacket();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//wait for info back
		
		P = owner.getPacket();
		if(Packet.RESPONSE_ALL_LOCATIONS == P.getType()){
			Locations = P.getLocations();
			for(int i = 0; i < Locations.size(); i++)
				listModel.addElement(Locations.get(i).getAddress()+ Locations.get(i).getCity()+ Locations.get(i).getName());
		}
		}
		
}


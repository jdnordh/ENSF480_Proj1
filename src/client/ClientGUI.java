package client;

import java.awt.EventQueue;
import java.awt.Frame;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import server.Server;

/**
 * 
 */
public class ClientGUI extends JFrame implements ClientGUIFunctionality {

	private String username;
	
	private static ClientGUI clientFrame;
	private JPanel Panel1;

	
    /**
     * Default constructor
     */
    public ClientGUI(String un) {
    	username = un;
    	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		Panel1 = new JPanel();
		setContentPane(Panel1);
		Panel1.setLayout(null);
		
		
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
		clientFrame = new ClientGUI("User");
		clientFrame.setVisible(true);

	}

   

}
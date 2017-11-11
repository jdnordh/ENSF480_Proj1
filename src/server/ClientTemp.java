package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import structures.*;

public class ClientTemp {

	
	public static void main(String [] args){
		try {
			
			String serverName = Server.NAME;
			int port = 16386;
			
			InetAddress a = InetAddress.getByName(serverName);
			System.out.println("Connecting to server...");
			Socket socket = new Socket(a, port);
			
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			
			Packet p = new Packet(Packet.LOGIN);
			User user = new User("tester", "123456");
			p.addUser(user);
			
			out.writeObject(p);
			out.flush();
			p = (Packet) in.readObject();
			
			System.out.println("Type: " + p.getType());
			
			p = new Packet(Packet.ADD_LOCATION);
			Location l = new Location("New", "Location", "Test");
			p.addLocation(l);
			out.writeObject(p);
			out.flush();
			p = (Packet) in.readObject();
			
//			if (p.getType() == Packet.LOGIN_CONFIRM_USER){
//				System.out.println("Type: " + p.getType());
//				System.out.println("Name: " + p.getUsers().get(0).getName());
//			}
//			else if (p.getType() == Packet.LOGIN_CONFIRM_ADMIN){
//				System.out.println("Type: " + p.getType());
//				System.out.println("Name: " + p.getUsers().get(0).getName());
//			}
			System.out.println("Type: " + p.getType());
			for (int i = 0; i < p.getMeetings().size(); i++){
				System.out.println(p.getMeetings().get(i).getID());
			}
			
			p = new Packet(Packet.CLOSE_CONNECTION);
			
			out.writeObject(p);
			out.flush();
			
			in.close();
			out.close();
			socket.close();
			
		} catch (Exception e){
			e.printStackTrace();
		}
	
	}
}

package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
			
			p = new Packet(Packet.ADD_USER);
			
			 String password = "root";
				MessageDigest digest;
				
				User loginUser = new User("Testing User", "user", "", false);
				try {
					digest = MessageDigest.getInstance("SHA-256");
					String hashed_password = new String(digest.digest(password.getBytes(StandardCharsets.UTF_8)));
					loginUser.setPassword(hashed_password);
				} catch (NoSuchAlgorithmException e) {}
				
				
				System.out.println("Username: " + loginUser.getUserName() + "\nPassword: " + loginUser.getPassword());
				//create login packet
				p = new Packet(Packet.ADD_USER);
				//add user to packet
				p.addUser(loginUser);
			
//			p = new Packet(Packet.ADD_LOCATION);
//			Location l = new Location("New", "Location", "Test");
//			p.addLocation(l);
			
			//p = new Packet(Packet.ADD_USER);
			
			
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
			for (int i = 0; i < p.getUsers().size(); i++){
				System.out.println(p.getUsers().get(i).getName());
			}
			
			p = new Packet(Packet.CLOSE_CONNECTION);
			
			//out.writeObject(p);
			//out.flush();
			
			in.close();
			out.close();
			socket.close();
			
		} catch (Exception e){
			e.printStackTrace();
		}
	
	}
}

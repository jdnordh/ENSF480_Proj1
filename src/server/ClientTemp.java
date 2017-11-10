package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import structures.*;

public class ClientTemp {

	
	public static void main(String [] args){
		try {
			Socket socket = new Socket("localhost", 16386);
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			
			Packet p = new Packet(Packet.LOGIN);
			p.addUser(new User("tester", "123456"));
			
			out.writeObject(p);
			out.flush();
			p = (Packet) in.readObject();
			
			System.out.print("Type: " + p.getType());
			System.out.print("Name: " + p.getUsers().get(0).getName());
			
			in.close();
			out.close();
			socket.close();
			
		} catch (Exception e){
			e.printStackTrace();
		}
	
	}
}

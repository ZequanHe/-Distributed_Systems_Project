package Server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import RMI.IRemoteWhiteboard;

public class server {
	public static void main(String args[]) {
		try {
			IRemoteWhiteboard whiteboard = new server_whiteboard();
			int port = Integer.parseInt(args[0]);
			Registry regist= LocateRegistry.createRegistry(port);
			regist.bind("whiteboard", whiteboard);
			System.out.println("using the port: "+ port +" as server");
		} catch(RemoteException e) {
			e.printStackTrace();
		} catch(AlreadyBoundException e) {
			e.printStackTrace();
		}
	}
}

package CreateWhiteBoard;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import RMI.IRemoteClient;
import RMI.IRemoteWhiteboard;

public class CreateWhiteBoard extends UnicastRemoteObject implements Serializable,IRemoteClient{
	public CreateWhiteBoard(String user,String ip_addr,String port) throws RemoteException {
		this.user = user.trim();
		this.host = ip_addr +":"+port;
		this.service = "whiteboard";
		this.client_Ser = "manager";
		this.gui = new Create_GUI();
		this.port= port;
	}

	private static final long serialVersionUID = 1L;
	protected String host;
	protected String user;
	protected String service;
	protected String client_Ser;
	protected IRemoteWhiteboard board;
	protected Create_GUI gui;
	protected String port;
	
	public static void main(String[] args) {
		CreateWhiteBoard whiteboard;
		try {
			if(args[2].equals("")) {
				System.out.println("please enter username after port");
				System.exit(-1);
			}else if(Integer.parseInt(args[1])< 0) {
				System.out.println("port is not correct");
				System.exit(-1);
			}
			//System.out.println(args[2]+args[0]+args[1]);
			whiteboard = new CreateWhiteBoard(args[2],args[0],args[1]);
			whiteboard.connect_ser();
		} catch(RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void ServerMessage(String mess) throws RemoteException {
		// TODO Auto-generated method stub
		gui.getTextField_msg().append(mess);
	}

	@Override
	public void update(String[] userlist) throws RemoteException {
		// TODO Auto-generated method stub
		gui.getList().setListData(userlist);
	}

	@Override
	public void load(byte[] bt) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			ByteArrayInputStream input = new ByteArrayInputStream(bt);
			BufferedImage img = ImageIO.read(input);
			gui.getpanel().img_load(img);
		}catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	@Override
	public void reject(String info) throws RemoteException {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, info + "rejected, sorry", "error", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}

	@Override
	public boolean determine(String str) throws RemoteException {
		// TODO Auto-generated method stub
		int yes_or_no = JOptionPane.showConfirmDialog(null,str + " want to join\n","Judge", JOptionPane.YES_NO_OPTION);
		if (yes_or_no == 1) {
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public void infomation(String info) throws RemoteException {
		// TODO Auto-generated method stub
		Thread thre = new Thread(()->{
			JOptionPane.showMessageDialog(null, info, "info for you", JOptionPane.INFORMATION_MESSAGE);
			});
			thre.start();
	}
	
	public void connect_ser() {
		try {
			InetAddress ip = InetAddress.getLocalHost();
			String host_name = InetAddress.getLocalHost().getHostAddress() +":"+port ;
			String[] ser_info = {user,host_name,client_Ser};
			//System.out.println(host_name);
			Naming.rebind("rmi://" + host_name + "/" + client_Ser, this);
			board = (IRemoteWhiteboard) Naming.lookup("rmi://" + host_name + "/" + service);
			board.EmptyOrNot(ser_info);
			board.register_listen(ser_info);
			gui.setBoard(board);
			gui.setUser(user);
			gui.getpanel().setBoard(board);
		}catch (NotBoundException e) {
			System.out.println("connection failed");
			
			e.printStackTrace();
			System.exit(-1);
		}catch(RemoteException e) {
			System.out.println("connection failed");
			e.printStackTrace();
			System.exit(-1);
		}catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public String getUser() throws RemoteException{
		return user;
	}
	
}

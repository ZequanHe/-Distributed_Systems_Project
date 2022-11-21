package Server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import RMI.IRemoteClient;
import RMI.IRemoteWhiteboard;

public class server_whiteboard extends UnicastRemoteObject implements IRemoteWhiteboard{
	private ArrayList<Userinfo> userlist;
	private byte[] bt;
	
	protected server_whiteboard() throws RemoteException{
		super();
		userlist = new ArrayList<Userinfo>();
		
	}
	@Override
	public void draw(byte[] bt) throws RemoteException {
		this.bt = bt;
		for (Userinfo user:userlist) {
			try {
				user.getClient().load(bt);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public synchronized void register_listen(String[] info) throws RemoteException {
		try {
			IRemoteClient newuser = (IRemoteClient) Naming.lookup("rmi://" + info[1]+"/"+info[2]);
			userlist.add(new Userinfo(info[0],newuser));
			if(userlist.size() > 1 ) {
				if(determine(userlist.get(userlist.size()-1).getUsername())) {
					userlist.remove(userlist.size()-1);
					newuser.reject("reject your request by manager");
					return;
				}
			}if (bt !=null) {
				newuser.load(bt);
			}
			newuser.ServerMessage(info[0]+": approve your request, welcome. you can chat in chat box now\n");
			update();
			broadcaster(info[0]+ "has join the whiteboard\n");
		}catch (NotBoundException e) {
			e.printStackTrace();
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	private void update() {
		String[] curr_userlist = new String[userlist.size()];
		for (int j = 0; j< curr_userlist.length;j++) {
			curr_userlist[j] = userlist.get(j).getUsername();
		}
		for (Userinfo user:userlist) {
			try {
				user.getClient().update(curr_userlist);
			}catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void remove_user(String username) throws RemoteException {
		int find_user = 0;
		for (int i = 0; i < userlist.size();i++) {
			if(userlist.get(i).getUsername().equals(username)) {
				find_user = i;
			}
		}
		if (find_user == 0 ) {
			userlist.get(0).getClient().infomation("this user do not exist");
		}
		else {
			IRemoteClient user_kick = userlist.get(find_user).getClient();
			String name_user = userlist.get(find_user).getUsername();
			userlist.remove(find_user);
			userlist.get(0).getClient().infomation(name_user +"remove success");
			update();
			Thread thre = new Thread(()->{
				try {
					user_kick.reject("Sorry. you are kicked out by manager ");
				}catch (RemoteException e) {
					
				}
			});
			thre.start();
		}
	}
	
	@Override
	public void broadcaster(String info) throws RemoteException {
		// TODO Auto-generated method stub
		for (Userinfo user:userlist) {
			try {
				user.getClient().ServerMessage(info + "\n");
			}catch (RemoteException e) {
			    e.printStackTrace();
			   }
		}
	}
	@Override
	public void hasSameName(String[] list) throws RemoteException {
		try {
			IRemoteClient newuser = (IRemoteClient) Naming.lookup("rmi://" + list[1] +"/" +list[2]);
			for (Userinfo user:userlist) {
				if(user.getUsername().equals(list[0])) {
					newuser.reject("The username already exist, please use other name");
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void EmptyOrNot(String[] list) throws RemoteException {
		if(userlist.size()>0) {
			try {
				IRemoteClient newusers = (IRemoteClient) Naming.lookup("rmi://" + list[1] +"/" +list[2]);
				newusers.reject("Room already create\n");
			}catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	@Override
	public void exit(String username) throws RemoteException {
		int find_user = 0;
		for(int j = 0; j< userlist.size() ;j++) {
			if(userlist.get(j).getUsername().equals(username)) {
				find_user = j;
			}
		}
		userlist.remove(find_user);
		broadcaster(username+ "leave the room \n");
		update();
	}
	
	@Override
	public boolean check_num_user() throws RemoteException {
		// TODO Auto-generated method stub
		if (userlist.size() == 0) {
			return false;
		}else {
			return true;
		}
	}
	
	@Override
	public void end() throws RemoteException {
		System.exit(-1);
		
	}
	@Override
	public boolean determine(String str) throws RemoteException {
		return userlist.get(0).getClient().determine(str);
	}
}

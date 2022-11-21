package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteWhiteboard extends Remote{
	public void draw(byte[] bt) throws RemoteException;
	public void register_listen(String[] info)throws RemoteException;
	public void remove_user(String username)throws RemoteException;
	public void broadcaster(String info)throws RemoteException;
	public void hasSameName(String[] list)throws RemoteException;
	public void EmptyOrNot(String[] list)throws RemoteException;
	public void exit(String username) throws RemoteException;
	public boolean check_num_user()throws RemoteException;
	public boolean determine(String str)throws RemoteException;
	public void end()throws RemoteException;
}

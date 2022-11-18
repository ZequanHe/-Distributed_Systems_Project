package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteClient extends Remote{
	public void ServerMessage(String mess) throws RemoteException;
	public void update(String[] userlist) throws RemoteException;
	public void load(byte[] bt) throws RemoteException;
	public void reject(String info)throws RemoteException;
	public boolean determine(String str)throws RemoteException;
	public void infomation(String info)throws RemoteException;

}

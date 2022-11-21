package Server;

import RMI.IRemoteClient;

public class Userinfo {
	public String username;
	public IRemoteClient client;
	
	public Userinfo(String username,IRemoteClient client) {
		this.client =client;
		this.username = username;
		
	}
	public String getUsername() {
		return username;
	}
	public IRemoteClient getClient() {
		return client;
	}
}

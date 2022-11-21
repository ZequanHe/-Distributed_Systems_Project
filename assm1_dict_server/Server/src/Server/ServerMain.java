/*He Zequan 1068069*/
package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
	public int port;
	private ServerSocket server;
	private int clientnum = 0;
	private ServerGUI gui;
	private Dictionary dict;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			if(Integer.parseInt(args[0])<0) {
				System.out.println("invalid Port number");
				System.exit(-1);
			}
			ServerMain server_main = new ServerMain(args[0],args[1]);
			server_main.run();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public ServerMain(String port, String dict_addr) {
		this.port = Integer.parseInt(port);
		this.dict = new Dictionary(dict_addr);
		this.server = null;
		this.gui = null;
	}
	
	public void run() {
		try {
			this.server = new ServerSocket(this.port);
			InetAddress ip = InetAddress.getLocalHost();
			System.out.println("Server Running...");
			System.out.println("IP address : " + ip.getHostAddress());
			System.out.println("Port = " + port);	
			System.out.println("Waiting for connection\n");
			
			this.gui = new ServerGUI(InetAddress.getLocalHost().getHostAddress(),String.valueOf(port),dict.getdic_path());
			gui.getFrame().setVisible(true);
			while (true) {
				Socket client_s = server.accept();
				System.out.println("Server: one client connect.");
				ServerRequest request_th = new ServerRequest(client_s,this,dict);
				request_th.start();
				
			}
		} catch (IOException e){
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int getPort() {
		return port;
	}
	
	public void setPort(String ori_port) {
		port = Integer.parseInt(ori_port);
	}
	public void showOnGUI(String content) {
		if (gui != null) {
			gui.getLog().setText(content);
		}
	}
}

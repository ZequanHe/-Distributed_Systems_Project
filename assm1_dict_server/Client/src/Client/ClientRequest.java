/*He Zequan 1068069*/
package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ClientRequest extends Thread{
	private int state;
	private int port;
	private String word;
	private String mean;
	private int request_code;
	private Socket socket;
	private String addr;
	private String[] receive = {"",""};
	private final int SEARCH = 1;
	private final int ADD = 2;
	private final int REMOVE =3;
	private final int UPDATE = 4;
	private final int FAIL = 0;
	private final int SUCCESS = 1;
	
	private JSONObject JSONCreate() {
		JSONObject req = new JSONObject();
		req.put("request", String.valueOf(request_code));
		req.put("word", word);
		req.put("meaning",mean);
		return req;
	}
	
	/**
	 * change string to JSON format, easy to process data later

	 */
	public JSONObject StringToJSON(String str) {
		JSONObject res = null;
		try {
			JSONParser par = new JSONParser();
			res = (JSONObject) par.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	public String[] getReceive() {
		return receive;
	}
	public ClientRequest(String addr, int port, int request_code, String word,String mean) {
		this.addr = addr;
		this.port = port;
		this.request_code = request_code;
		this.word= word;
		this.mean = mean;
		this.socket = null;
		this.state = FAIL;
		
	}
	
	
	
	@Override
	public void run() {
		
		try {
			socket = new Socket(addr,port);
			DataInputStream fileread = new DataInputStream(socket.getInputStream());
			DataOutputStream filewrite = new DataOutputStream(socket.getOutputStream());
			filewrite.writeUTF(JSONCreate().toJSONString());
			filewrite.flush();
			
			String read = fileread.readUTF();
			JSONObject JSONfile = StringToJSON(read);
			state = Integer.parseInt(JSONfile.get("state").toString());
			if (state == SUCCESS) {
				mean = (String) JSONfile.get("meaning");
				
			}
			fileread.close();
			filewrite.close();
			
		}
		catch (ConnectException e) {
			System.out.println("Connect refused");
		}catch (SocketTimeoutException e) {
			System.out.println("Timeout!");
		}catch (IOException e) {
			System.out.println("I/O error");
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (socket!=null) {
				try {
					socket.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		receive[0] = String.valueOf(state);
		receive[1] = mean;
	}
}

/*He Zequan 1068069*/
package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ServerRequest  extends Thread {
	private Socket clients;
	private Dictionary dict;
	private ServerMain server;
	private final int SEARCH = 1;
	private final int ADD = 2;
	private final int REMOVE =3;
	private final int UPDATE = 4;
	private final int FAIL = 0;
	private final int SUCCESS = 1;
	
	public ServerRequest(Socket client,ServerMain server,Dictionary dict) {
		this.clients= client;
		this.dict = dict;
		this.server = server;
	}
	/**
	 * change string to JSON format, easy to process data later

	 */
	public JSONObject StringToJSON(String str) {
		JSONObject req = null;
		try {
			JSONParser par = new JSONParser();
			req = (JSONObject) par.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return req;
	}
	/**
	 * create JSON and send to client later 
	 */
	public JSONObject JSONCreate(int state,String mean) {
		JSONObject res = new JSONObject();
		res.put("state", String.valueOf(state));
		res.put("meaning", mean);
		return res;
	}
	
	@Override
	public void run() {
		try {
			DataInputStream fileread = new DataInputStream(clients.getInputStream());
			DataOutputStream filewrite = new DataOutputStream(clients.getOutputStream());
			JSONObject req = StringToJSON(fileread.readUTF());
			int RequestCode = Integer.parseInt(req.get("request").toString());
			String word =(String) req.get("word");
			int curr_state = FAIL;
			String mean = (String) req.get("meaning");
			if (RequestCode == SEARCH) {
				
				if(dict.word_exist(word)) {
					mean = dict.search(word);
					curr_state = SUCCESS;
					server.showOnGUI("Get SEARCH Request word :"+ word+ " result:QUERY SUCCESS");
				}else {
					curr_state = FAIL;
					server.showOnGUI("Get SEARCH Request word :"+ word+ " result:Word does not Exist");
				}
				filewrite.writeUTF(JSONCreate(curr_state,mean).toJSONString());
				filewrite.flush();
			} else if (RequestCode == ADD) {
				if (!dict.word_exist(word)) {
					dict.add(word, mean);
					curr_state = SUCCESS;
					server.showOnGUI("Get ADD Request  word :"+ word+ " result: Add success");
				} else {
					server.showOnGUI("Get ADD Request word :"+ word+"  result:Add fail");
					curr_state = FAIL;
				}
				filewrite.writeUTF(JSONCreate(curr_state,mean).toJSONString());
				filewrite.flush();
			}else if (RequestCode == REMOVE) {
				server.showOnGUI("Get Request\n   Request: REMOVE \n word :"+ word);
				if (dict.word_exist(word)) {
					dict.delete(word);
					curr_state = SUCCESS;
					server.showOnGUI("Get REMOVE Request  word :"+ word +"result :remove success");
				}else {
					curr_state = FAIL;
					server.showOnGUI("Get REMOVE Request  word :"+ word+" result: remove fail");
				}
				filewrite.writeUTF(JSONCreate(curr_state,mean).toJSONString());
				filewrite.flush();
			} else if (RequestCode == UPDATE) {
				server.showOnGUI("Get Request\n   Request: UPDATE \n word :"+ word);
				if (dict.word_exist(word)) {
					dict.update(word, mean);
					curr_state = SUCCESS;
					server.showOnGUI("Get UPDATE Request  word :"+ word+ " result: update success");
				}else {
					curr_state = FAIL;
					server.showOnGUI("Get UPDATE Request word :"+ word+ " result: update fail");
				}
				filewrite.writeUTF(JSONCreate(curr_state,mean).toJSONString());
				filewrite.flush();
			}else {
				System.out.println("not correct request");
			}
			fileread.close();
			filewrite.close();
			clients.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

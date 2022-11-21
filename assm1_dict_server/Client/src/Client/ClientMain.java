/*He Zequan 1068069*/
package Client;


public class ClientMain {
	private int port;
	private String addr;
	private ClientGUI gui;
	private final int SEARCH = 1;
	private final int ADD = 2;
	private final int REMOVE =3;
	private final int UPDATE = 4;
	private final int FAIL = 0;
	private final int SUCCESS = 1;
	
	public static void main(String[] args) {
		try {
			if(Integer.parseInt(args[1])<0) {
				System.out.println("invalid Port number");
				System.exit(-1);
			}
			ClientMain client_main = new ClientMain(args[0],Integer.parseInt(args[1]));
			client_main.run();
		}catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Lack of Parameters");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ClientMain(String addr,int port) {
		this.port = port;
		this.addr = addr;
		gui = null;
	}
	
	public void run() {
		try {
			this.gui = new ClientGUI(this);
			gui.getFrame().setVisible(true);
		}catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Lack of Parameters");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String[] request(int request_code,String word, String mean) {
		int state = FAIL;
		System.out.println(" Request: ");
		if (request_code == ADD) {
			System.out.println(" ADD function. Word:"+ word +"\n  meanning:"+mean);
		}else if (request_code == SEARCH) {
			System.out.println(" SEARCH function. Word:"+ word);
		}else if (request_code == REMOVE) {
			System.out.println(" REMOVE function. Word:"+ word);
		}else if (request_code == UPDATE) {
			System.out.println(" UPDATE function. Word:"+ word +"meanning:"+mean);
		}else {
			System.out.println(" unknown function");
		}
		
		try {
			System.out.println("Tring to connect to server");
			ClientRequest one_thread = new ClientRequest(addr,port,request_code,word,mean);
			one_thread.start();
			one_thread.join(2000);
			String[] result = one_thread.getReceive();
			state = Integer.parseInt(result[0]);
			mean = result[1];	
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.println("Response:");
		if (state == SUCCESS) {
			System.out.println(" state: success");
		} else {
			System.out.println(" state: fail");
		}
		System.out.println("Meaning:"+mean);
		String[] resultc = {String.valueOf(state), mean};
		return resultc;
	}
	public int add(String word, String mean) {
		String[] result = request(ADD,word,mean);
		return Integer.parseInt(result[0]);
	}
	public String[] search(String word) {
		String[] result = request(SEARCH,word," ");
		return result;
	}
	public int delete(String word) {
		String[] result = request(REMOVE,word,"");
		return Integer.parseInt(result[0]);
	}
	public int update(String word, String mean) {
		String[] result = request(UPDATE,word,mean);
		return Integer.parseInt(result[0]);
	}
}

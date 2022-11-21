/*He Zequan 1068069*/
package Server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerGUI {

	private JFrame frame;
	private JTextField Log;


	/**
	 * Create the application.
	 */
	public JFrame getFrame() {
		return frame;
		
	}
	public JTextField getLog() {
		return Log;
	}
	
	public ServerGUI(String address, String port, String dict_addr) {
		initialize(address,port,dict_addr);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String address, String port, String dict_addr) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel Address = new JLabel("Address:"+ address);
		Address.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		Address.setBounds(6, 17, 330, 29);
		frame.getContentPane().add(Address);
		
		JLabel Port = new JLabel("Port:"+ port);
		Port.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		Port.setBounds(6, 58, 259, 16);
		frame.getContentPane().add(Port);
		
		JLabel Dictionary_add = new JLabel("Dictionary path:"+dict_addr);
		Dictionary_add.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		Dictionary_add.setBounds(6, 100, 438, 16);
		frame.getContentPane().add(Dictionary_add);
		
		Log = new JTextField();
		Log.setBounds(6, 128, 438, 138);
		frame.getContentPane().add(Log);
		Log.setColumns(10);
		
		
	}
}

/*He Zequan 1068069*/
package Client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class ClientGUI {

	private JFrame frame;
	private JTextField Word_f;
	private ClientMain diclient;
	private JTextArea Meaning_f;
	private final int SEARCH = 1;
	private final int ADD = 2;
	private final int REMOVE =3;
	private final int UPDATE = 4;
	private final int FAIL = 0;
	private final int SUCCESS = 1;

	
	public JFrame getFrame() {
		return frame;
	}
	/**
	 * Create the application.
	 */
	public ClientGUI(ClientMain client) {
		diclient = client;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel Word = new JLabel("Word");
		Word.setBounds(6, 6, 61, 16);
		frame.getContentPane().add(Word);
		
		JLabel Mean = new JLabel("Meaning");
		Mean.setBounds(6, 65, 61, 16);
		frame.getContentPane().add(Mean);
		
		JTextArea Meaning_f = new JTextArea();
		Meaning_f.setBounds(6, 93, 438, 103);
		frame.getContentPane().add(Meaning_f);
		
		JButton Search = new JButton("Search");
		Search.setBounds(181, 196, 117, 29);
		frame.getContentPane().add(Search);
		Search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent search) {
				String word = Word_f.getText();
				if (word.equals("")) {
					JOptionPane.showConfirmDialog(frame, "please do not emtpy word box", "Warning",JOptionPane.WARNING_MESSAGE);
				}else {
					String[] result= diclient.search(word);
					int state = Integer.parseInt(result[0]);
					if (state == FAIL) {
						JOptionPane.showConfirmDialog(frame, "Word Does Not Exist", "Warning",JOptionPane.WARNING_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(frame, "Search Success", "Tips", JOptionPane.INFORMATION_MESSAGE);
						Meaning_f.setText(result[1]);
					}
				}
				
				
			}
		});
		
		JButton Add = new JButton("Add");
		Add.setBounds(6, 237, 117, 29);
		frame.getContentPane().add(Add);
		Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent add) {
				String word = Word_f.getText();
				String mean = Meaning_f.getText();
				
				if (mean.equals("")|| word.equals("")) {
					JOptionPane.showConfirmDialog(frame, "please do not emtpy word and meaning box", "Warning",JOptionPane.WARNING_MESSAGE);
				} else {
					int state = diclient.add(word, mean);
					if (state == FAIL) {
						JOptionPane.showConfirmDialog(frame, "Word Exist", "Warning",JOptionPane.WARNING_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(frame, "Add Success", "Tips", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		
		
		
		JButton Delete = new JButton("Delete");
		Delete.setBounds(181, 237, 117, 29);
		frame.getContentPane().add(Delete);
		Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent delete) {
				String word = Word_f.getText();
				if (word.equals("")) {
					JOptionPane.showConfirmDialog(frame, "please do not emtpy word box", "Warning",JOptionPane.WARNING_MESSAGE);
				}else {
					int state = diclient.delete(word);
					if (state == FAIL) {
						JOptionPane.showConfirmDialog(frame, "Word does not Exist", "Warning",JOptionPane.WARNING_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(frame, "delete Success", "Tips", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
			}
		});
		
		
		JButton Update = new JButton("Update");
		Update.setBounds(327, 237, 117, 29);
		frame.getContentPane().add(Update);
		Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent add) {
				String word = Word_f.getText();
				String mean = Meaning_f.getText();
				if (mean.equals("")|| word.equals("")) {
					JOptionPane.showConfirmDialog(frame, "please do not emtpy word and meaning box", "Warning",JOptionPane.WARNING_MESSAGE);
				} else {
					int state = diclient.update(word, mean);
					if (state == FAIL) {
						JOptionPane.showConfirmDialog(frame, "Word does not Exist", "Warning",JOptionPane.WARNING_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(frame, "update Success", "Tips", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
			}
		});
		
		Word_f = new JTextField();
		Word_f.setBounds(6, 34, 438, 26);
		frame.getContentPane().add(Word_f);
		Word_f.setColumns(10);
	}
}

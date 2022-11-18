package JoinWhiteBoard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.rmi.RemoteException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;

import CreateWhiteBoard.paint;
import RMI.IRemoteWhiteboard;

public class Join_gui {

	private JFrame frame;
	private IRemoteWhiteboard board;
	private paint panel;
	private String user;
	private JTextField textField;
	private JFileChooser fileChoose;
	private File file;
	private JPanel content;
	private JTextField textField_msg;
	private JList list;
	private JTextArea textArea_msg;
	private JFileChooser file_select;
	private String file_path;
	

	public paint getpanel() {
		return panel;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public void setBoard(IRemoteWhiteboard board) {
		this.board = board;
	}
	
	public JList getList() {
		return list;
	}
	public JTextArea getTextField_msg() {
		return textArea_msg;
	}


	/**
	 * Launch the application.
	 */
	//public static void main(String[] args) {
	//	EventQueue.invokeLater(new Runnable() {
	//		public void run() {
	//			try {
	//				Join_gui window = new Join_gui();
	//				window.frame.setVisible(true);
	//			} catch (Exception e) {
	//				e.printStackTrace();
	//			}
	//		}
	//	});
	//}

	/**
	 * Create the application.
	 */
	public Join_gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		panel = new paint();
		frame.setBounds(150, 150, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("User");
		
		frame.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				try {
					board.exit(user);
					
				}catch (RemoteException ee) {
					ee.printStackTrace();
				}
			}
		});
		
		
		panel.setBounds(10, 172, 465, 396);
		panel.setBackground(Color.white);
		frame.getContentPane().add(panel);
		
		
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(10, 42, 446, 41);
		frame.getContentPane().add(toolBar);
		
		JButton btnRect = new JButton("Rectangle");
		btnRect.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		toolBar.add(btnRect);
		btnRect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setDraw_type("rect");
			}
		});
		
		JButton btnLine = new JButton("Line");
		btnLine.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		toolBar.add(btnLine);
		btnLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setDraw_type("line");
			}
		});
		
		JButton btnCircle = new JButton("Circle");
		btnCircle.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		toolBar.add(btnCircle);
		btnCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setDraw_type("circle");
			}
		});
		
		JButton btnText = new JButton("Text");
		btnText.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		toolBar.add(btnText);
		btnText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setDraw_type("text");
			}
		});
		
		
		JButton btnErase = new JButton("Eraser");
		btnErase.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		toolBar.add(btnErase);
		btnErase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setDraw_type("earse");
			}
		});
		
		
		JButton btnFree = new JButton("Free draw");
		btnFree.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		toolBar.add(btnFree);
		btnFree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setDraw_type("free");
			}
		});
		
		
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(16, 57, 80, 27);
		//frame.getContentPane().add();
		toolBar.add(comboBox);
		comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"light", "normal", "dark"}));
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stro=(String)comboBox.getSelectedItem();
				Stroke Stroke_se = new BasicStroke(1.0f);
				if(stro.contentEquals("light")) {
					Stroke_se = new BasicStroke(1.0f);
				}
				else if(stro.contentEquals("normal")) {
					Stroke_se = new BasicStroke(2.0f);
				}
				else if(stro.contentEquals("dark")) {
					Stroke_se = new BasicStroke(3.0f);
				}
				panel.setStroke_level(Stroke_se);
			}
		});
		
		JLabel lblColor = new JLabel("Color:");
		lblColor.setBounds(100, 95, 48, 16);
		
		frame.getContentPane().add(lblColor);
		
		JButton btnSe = new JButton("B");
		btnSe.setBackground(Color.BLACK);
		btnSe.setBounds(58, 90, 28, 29);
		btnSe.setBorderPainted(false);
		btnSe.setOpaque(true);
		frame.getContentPane().add(btnSe);
		
		JButton btnblue = new JButton("");
		btnblue.setBounds(160, 90, 28, 29);
		btnblue.setBackground(Color.BLUE);
		btnblue.setBorderPainted(false);
		btnblue.setOpaque(true);
		frame.getContentPane().add(btnblue);
		
		
		btnblue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("blue");
				btnSe.setBackground(Color.BLUE);
				panel.setColor(Color.BLUE);
			}
		});
		
		
		JButton btngreen = new JButton("");
		btngreen.setBackground(Color.GREEN);
		btngreen.setBounds(189, 90, 28, 29);
		btngreen.setBorderPainted(false);
		btngreen.setOpaque(true);
		btngreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("blue");
				btnSe.setBackground(Color.GREEN);
				panel.setColor(Color.GREEN);
			}
		});
		frame.getContentPane().add(btngreen);
		
		
		JButton btnpurple = new JButton("");
		btnpurple.setBackground(Color.MAGENTA);
		btnpurple.setBounds(219, 90, 28, 29);
		btnpurple.setBorderPainted(false);
		btnpurple.setOpaque(true);
		btnpurple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("blue");
				btnSe.setBackground(Color.MAGENTA);
				panel.setColor(Color.MAGENTA);
			}
		});
		frame.getContentPane().add(btnpurple);
		
		JLabel lblcurrent_color = new JLabel("current:");
		lblcurrent_color.setBounds(6, 95, 61, 16);
		frame.getContentPane().add(lblcurrent_color);
		
		JButton btnyellow = new JButton("");
		btnyellow.setBackground(Color.YELLOW);
		btnyellow.setBounds(252, 90, 28, 29);
		btnyellow.setBorderPainted(false);
		btnyellow.setOpaque(true);
		btnyellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("blue");
				btnSe.setBackground(Color.YELLOW);
				panel.setColor(Color.YELLOW);
			}
		});
		frame.getContentPane().add(btnyellow);
		
		JButton btnorange = new JButton("");
		btnorange.setBackground(Color.ORANGE);
		btnorange.setBounds(284, 90, 28, 29);
		btnorange.setBorderPainted(false);
		btnorange.setOpaque(true);
		btnorange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("blue");
				btnSe.setBackground(Color.ORANGE);
				panel.setColor(Color.ORANGE);
			}
		});
		frame.getContentPane().add(btnorange);
		
		JButton btnred = new JButton("");
		btnred.setBackground(Color.RED);
		btnred.setBounds(321, 90, 28, 29);
		btnred.setBorderPainted(false);
		btnred.setOpaque(true);
		btnred.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("blue");
				btnSe.setBackground(Color.RED);
				panel.setColor(Color.RED);
			}
		});
		frame.getContentPane().add(btnred);
		
		JButton btnwhite = new JButton("");
		btnwhite.setBackground(Color.WHITE);
		btnwhite.setBounds(353, 90, 28, 29);
		btnwhite.setBorderPainted(false);
		btnwhite.setOpaque(true);
		btnwhite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("blue");
				btnSe.setBackground(Color.WHITE);
				panel.setColor(Color.WHITE);
			}
		});
		frame.getContentPane().add(btnwhite);
		
		JButton btnblack = new JButton("");
		btnblack.setBackground(Color.BLACK);
		btnblack.setBounds(384, 90, 28, 29);
		btnblack.setBorderPainted(false);
		btnblack.setOpaque(true);
		btnblack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("blue");
				btnSe.setBackground(Color.BLACK);
				panel.setColor(Color.BLACK);
			}
		});
		frame.getContentPane().add(btnblack);
		
		JButton btngray = new JButton("");
		btngray.setBackground(Color.GRAY);
		btngray.setBounds(219, 123, 28, 29);
		btngray.setBorderPainted(false);
		btngray.setOpaque(true);
		btngray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("blue");
				btnSe.setBackground(Color.GRAY);
				panel.setColor(Color.GRAY);
			}
		});
		frame.getContentPane().add(btngray);
		
		JButton btnpink = new JButton("");
		btnpink.setBackground(Color.PINK);
		btnpink.setBounds(252, 123, 28, 29);
		btnpink.setBorderPainted(false);
		btnpink.setOpaque(true);
		btnpink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("blue");
				btnSe.setBackground(Color.PINK);
				panel.setColor(Color.PINK);
			}
		});
		
		frame.getContentPane().add(btnpink);
		
		JButton btncyan = new JButton("");
		btncyan.setBackground(Color.CYAN);
		btncyan.setBounds(284, 123, 28, 29);
		btncyan.setBorderPainted(false);
		btncyan.setOpaque(true);
		btncyan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("blue");
				btnSe.setBackground(Color.CYAN);
				panel.setColor(Color.CYAN);
			}
		});
		frame.getContentPane().add(btncyan);
		
		JButton btnlight_gray = new JButton("");
		btnlight_gray.setBackground(Color.LIGHT_GRAY);
		btnlight_gray.setBounds(321, 123, 28, 29);
		btnlight_gray.setBorderPainted(false);
		btnlight_gray.setOpaque(true);
		btnlight_gray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("blue");
				btnSe.setBackground(Color.LIGHT_GRAY);
				panel.setColor(Color.LIGHT_GRAY);
			}
		});
		frame.getContentPane().add(btnlight_gray);
		
		JButton btndark_gray = new JButton("");
		btndark_gray.setBackground(Color.DARK_GRAY);
		btndark_gray.setBounds(353, 123, 28, 29);
		btndark_gray.setBorderPainted(false);
		btndark_gray.setOpaque(true);
		btndark_gray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("blue");
				btnSe.setBackground(Color.DARK_GRAY);
				panel.setColor(Color.DARK_GRAY);
			}
		});
		
		frame.getContentPane().add(btndark_gray);
		
		JButton btnkhaki = new JButton("");
		btnkhaki.setBackground(new Color(128, 128, 0));
		btnkhaki.setBounds(384, 123, 28, 29);
		btnkhaki.setBorderPainted(false);
		btnkhaki.setOpaque(true);
		btnkhaki.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("blue");
				btnSe.setBackground(new Color(128, 128, 0));
				panel.setColor(new Color(128, 128, 0));
			}
		});
		frame.getContentPane().add(btnkhaki);
		
		JButton btnsky_blue = new JButton("");
		btnsky_blue.setBackground(new Color(0, 191, 255));
		btnsky_blue.setBounds(160, 123, 28, 29);
		btnsky_blue.setBorderPainted(false);
		btnsky_blue.setOpaque(true);
		btnsky_blue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("blue");
				btnSe.setBackground(new Color(0, 191, 255));
				panel.setColor(new Color(0, 191, 255));
			}
		});
		frame.getContentPane().add(btnsky_blue);
		
		JButton btngrass_green = new JButton("");
		btngrass_green.setBackground(new Color(46, 139, 87));
		btngrass_green.setBounds(189, 123, 28, 29);
		btngrass_green.setBorderPainted(false);
		btngrass_green.setOpaque(true);
		btngrass_green.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("blue");
				btnSe.setBackground(new Color(46, 139, 87));
				panel.setColor(new Color(46, 139, 87));
			}
		});
		frame.getContentPane().add(btngrass_green);
		
		JLabel lblUser = new JLabel("User List");
		lblUser.setBounds(478, 35, 61, 16);
		frame.getContentPane().add(lblUser);
		
		JLabel lblChat = new JLabel("Chat");
		lblChat.setBounds(478, 180, 61, 16);
		frame.getContentPane().add(lblChat);
		
		textArea_msg = new JTextArea();
		textArea_msg.setBounds(488, 208, 306, 280);
		textArea_msg.setEditable(false);
		textArea_msg.setLineWrap(true);
		frame.getContentPane().add(textArea_msg);
		
		textField_msg = new JTextField();
		textField_msg.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, Color.BLACK));
		textField_msg.setBounds(488, 512, 241, 29);
		frame.getContentPane().add(textField_msg);
		textField_msg.setColumns(10);
		
		JButton btnsend = new JButton("Send");
		btnsend.setBounds(725, 513, 75, 29);
		btnsend.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String msg = textField_msg.getText();
				try {
					if (!msg.equals("")) {
						board.broadcaster( user +":"+msg);
					}
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(null, "room not exist", "error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
					System.exit(0);
				}
				//textArea_msg.append(Msg+"\n");
				textField_msg.setText(null);
			}
		});
		frame.getContentPane().add(btnsend);
		
		
		list = new JList();
		list.setBounds(478, 54, 306, 116);
		list.setBorder(new EtchedBorder(EtchedBorder.RAISED, null,  Color.BLACK));
		frame.getContentPane().add(list);
		
		
		frame.setVisible(true);
	}

}

package CreateWhiteBoard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import RMI.IRemoteWhiteboard;

public class paint extends JPanel{
	private int x;
	private int y;
	private Color color_def =Color.black;
	private IRemoteWhiteboard board;
	private static ArrayList<Point> point = new ArrayList<Point>();
	private static ArrayList<Shape> shapes = new ArrayList<Shape>();
	private String draw_type = "line";
	private Stroke stroke_level = new BasicStroke(1.0f);
	private BufferedImage img; 
	
	
	public void setDraw_type(String draw_type) {
		this.draw_type = draw_type;
	}
	public void setColor(Color color) {
		this.color_def = color;
	}
	public void setBoard(IRemoteWhiteboard board) {
		this.board = board;
	}
	public void setStroke_level(Stroke stroke_level) {
		this.stroke_level = stroke_level;
	}
	
	public void new_board() {
		shapes = new ArrayList<Shape>();
		img = null;
	}
	
	public BufferedImage img_save() {
		Dimension sizes_img = this.getSize();
		BufferedImage save_img = new BufferedImage(sizes_img.width,sizes_img.height,BufferedImage.TYPE_INT_BGR);	
		Graphics2D graphics = save_img.createGraphics();
		this.paint(graphics);
		graphics.dispose();
		return save_img;
	}
	
	public void img_load(BufferedImage img) {
		new_board();
		repaint();
		this.img = img;
		
	}
	
	public void paint(Graphics graph) {
		super.paint(graph);
		if(img != null) {
			graph.drawImage(img, 0, 0, this);
			
		}
		for (int j = 0;j < shapes.size();j++) {
			if(shapes.get(j) == null) {
				break;
			}
			shapes.get(j).painting(graph);
		}
	}
	
	public void draw(int x1,int y1,int x2,int y2,String draw_type) {
		Graphics2D graph = (Graphics2D)getGraphics();
		graph.setColor(color_def);
		graph.setStroke(stroke_level);
		if(draw_type.equals("line")) {
            shapes.add(new Shape(graph,x1,y1,x2,y2,draw_type,color_def,stroke_level));
            graph.drawLine(x,y, x1, y1);
        }else {
        	int width = Math.abs(x2-x1);
        	int height = Math.abs(y2-y1);
        	if(draw_type.equals("rect")) {
        		shapes.add(new Shape(graph,x1,y1,x2,y2,draw_type,color_def,stroke_level));
        		graph.drawRect(Math.min(x, x1),Math.min(y, y1), Math.abs(x2-x1), Math.abs(y2-y1));
        	}
        	if (draw_type.equals("circle")) {
        		shapes.add(new Shape(graph,x1,y1,x2,y2,draw_type,color_def,stroke_level));
        		graph.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.max(Math.abs(x1 - x2),Math.abs(y1 - y2)), Math.max(Math.abs(x1 - x2),Math.abs(y1 - y2)));
        	}
        	if (draw_type.equals("free")) {
        		ArrayList<Point> points = new ArrayList<Point>(1500);
        		points.addAll(point);
        		shapes.add(new Shape(graph,draw_type,points,color_def,stroke_level));
        	}
        	if (draw_type.equals("erase")) {
        		ArrayList<Point> points = new ArrayList<Point>(1500);
        		points.addAll(point);
        		shapes.add(new Shape(graph,draw_type,points,Color.white,stroke_level));
        	}
        	point.clear();
        }
	}
	
	public void synchronous() {
		try {
			BufferedImage image = img_save();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ImageIO.write(image,"png", output);
			byte[] bt = output.toByteArray();
			board.draw(bt);
		}catch(RemoteException e) {
			JOptionPane.showMessageDialog(null, "error, manager leave", "error", JOptionPane.ERROR_MESSAGE);
		}catch (IOException e) {
			e.printStackTrace();
		} 
		  
	}
	
	public paint() {
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
				
				if(board != null && draw_type.equals("text")) {
					Graphics2D graph = (Graphics2D)getGraphics();
					String text;
					text =JOptionPane.showInputDialog("enter text");
					if(text != null) {
						graph.setColor(color_def);
						graph.drawString(text, x, y);
						shapes.add(new Shape(graph,x,y,text,draw_type,color_def));
						synchronous();
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if(board != null) {
					int curr_x = e.getX();
					int curr_y = e.getY();
					draw(x,y,curr_x,curr_y,draw_type);
					synchronous();
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				if(board != null) {
					int next_x = e.getX();
					int next_y = e.getY();
					int x2;
					int y2;
					Graphics2D graph = (Graphics2D)getGraphics();
					graph.setColor(color_def);
					graph.setStroke(stroke_level);
					if(draw_type.equals("free")) {
						if(point.size() !=0) {
							x2 = point.get(point.size()-1).x;
							y2 = point.get(point.size()-1).y;
							
						}else {
							x2 =x;
							y2 =y;
						}
						graph.drawLine(x2, y2, next_x, next_y);
						point.add(new Point(next_x,next_y));
					
					}else if (draw_type.equals("erase")) {
						if(point.size() !=0) {
							x2 = point.get(point.size()-1).x;
							y2 = point.get(point.size()-1).y;
							
						}else {
							x2 =x;
							y2 =y;
						}
						graph.setColor(Color.white);
						graph.drawLine(x2,y2,next_x, next_y);
						point.add(new Point(next_x,next_y));
						//graph.setColor(new Color(color_def.getRGB()));
					}else {
						if(draw_type.equals("line")) {
							graph.drawLine(x, y, next_x, next_y);
						}else {
							if(draw_type.equals("rect")) {
				        		graph.drawRect(Math.min(x, next_x),Math.min(y, next_y), Math.abs(next_x-x), Math.abs(next_y-y));
				        	}
							if (draw_type.equals("circle")) {
				        		graph.drawOval(Math.min(x, next_x), Math.min(y, next_y), Math.max(Math.abs(next_x-x),Math.abs(next_y-y)), Math.max(Math.abs(next_x-x),Math.abs(next_y-y)));
				        	}
							
						}
						repaint();
					}
					
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	
}

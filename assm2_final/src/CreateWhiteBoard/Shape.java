package CreateWhiteBoard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;

public class Shape {
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private String draw_type;
	private ArrayList<Point> free_draw;
	private Stroke stroke;
	private String input;
	private Color color;
	
	public Shape(Graphics graph, int x1,int y1,int x2,int y2,String draw_type,Color color,Stroke stroke) {
		this.color = color;
		this.draw_type = draw_type;
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.stroke = stroke;
	}
	
	public Shape(Graphics graph, String draw_type,ArrayList<Point> free_draw,Color color,Stroke stroke) {
		this.color = color;
		this.free_draw = free_draw;
		this.draw_type = draw_type;
		this.stroke = stroke;
	}
	
	public Shape(Graphics graph, int x1,int y1,String input,String draw_type, Color color) {
		this.color = color;
		this.draw_type = draw_type;
		this.input=input;
		this.x1 = x1;
		this.y1 = y1;
		
	}
	
	public void painting(Graphics graph) {
		Graphics2D graph2 = (Graphics2D) graph;
		if(draw_type.equals("text")) {
			graph.setColor(color);
			graph.drawString(input, x1, y1);
		}else {
			graph2.setColor(color);
			graph2.setStroke(stroke);
			if (draw_type.equals("line")) {
				graph.drawLine(x1, y1, x2, y2);
			} else if (draw_type.equals("rect")) {
				graph.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
			} else if (draw_type.equals("circle")) {
				graph.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.max(Math.abs(x1 - x2),Math.abs(y1 - y2)), Math.max(Math.abs(x1 - x2),Math.abs(y1 - y2)));
			} else if (draw_type.equals("free")) {
				 for (int i = 1; i < free_draw.size(); i++) {
	                    graph.drawLine(free_draw.get(i - 1).x, free_draw.get(i - 1).y, free_draw.get(i).x, free_draw.get(i).y);
	                }
			} else if (draw_type.equals("erase")) {
				 for (int i = 1; i < free_draw.size(); i++) {
	                    graph.drawLine(free_draw.get(i - 1).x, free_draw.get(i - 1).y, free_draw.get(i).x, free_draw.get(i).y);
	                }
			} 
		}
	}
	public String getDraw_type() {
		return draw_type;
	}
}

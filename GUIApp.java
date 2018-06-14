import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;

import javax.swing.*;
public class GUIApp {

	public static void main(String[] args) {
		JFrame f = new AppFrame("My First App Frame");

	}

}
class AppFrame extends JFrame{
	JPanel pnlText, pnlGraphics;
	
	public AppFrame(String title){
		super(title);
	}
	
	public void frameInit(){
		super.frameInit();
		this.setLayout(new GridLayout(1,2));
		pnlText = new TextPanel(); 
		pnlText.setBorder(BorderFactory.createTitledBorder("Display Pane"));
		//pnlGraphics = new GraphicsPanel();
		this.add(pnlText); //for using text panel
		//this.add(pnlGraphics);
		this.add(createContainer());
		
		this.setSize(450,300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private JPanel createContainer(){
		JPanel container = new JPanel();
		JPanel pnlG = new GraphicsPanel();
		JButton btnChangeColor = new JButton("Change Color");
		container.setLayout(new BorderLayout());
		container.add(pnlG, BorderLayout.CENTER);
		container.add(btnChangeColor, BorderLayout.SOUTH);
		
		btnChangeColor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				pnlG.repaint();
			}
		});
		
		return container;
	}
		
}

class GraphicsPanel extends JPanel{
	public GraphicsPanel(){
		super();
		this.addMouseListener(new MsListener());
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		int blue = (int) (Math.random() * 255);
		g2.setColor(new Color(100, 100, blue));
		Ellipse2D.Double circle = new Ellipse2D.Double(50, 50, 100, 100);
		g2.fill(circle);
	}
	
	class MsListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			GeoShape s = ShapeFactory.createShape("circle", e.getX(), e.getY(), 50); //draws the shape kind mentioned in the brackets
			s.draw(getGraphics());
		}//event handling method
	
	}//event handling class
}

class ShapeFactory{
	static GeoShape createShape(String kind, int x, int y, int size){
		if(kind.equalsIgnoreCase("rect")){
			return new Rect(x, y, 50);
		}
		else if(kind.equalsIgnoreCase("circle")){
			return new Circle(x, y, 50);
		}
		else return null;
	}
}

abstract class GeoShape{
	int x, y, size;
	public GeoShape(int x, int y, int size){
		this.x = x; this.y = y; this.size = size;
	}
	public abstract void draw(Graphics g);
}

class Rect extends GeoShape{
	public Rect(int x, int y, int size) {
		super(x, y, size);
	}

	@Override
	public void draw(Graphics g) {
		g.drawRect(x, y, size, size);
	}
}


class Circle extends GeoShape{


	public Circle(int x, int y, int size) {
		super(x, y, size);
	}

	@Override
	public void draw(Graphics g) {
		g.drawOval(x, y, size, size);
	}
}

class TextPanel extends JPanel{
	JTextArea txtEditor;
	JScrollPane scroll;
	
	JButton btnClear, btnFontColor;
	JPanel pnlButtons;
	
	public TextPanel(){
		super();
		setup();
	}
	
	private void setup(){
		txtEditor = new JTextArea(5, 10);
		scroll = new JScrollPane(txtEditor);
		scroll.setPreferredSize(new Dimension(450, 270));
		
		btnClear = new JButton("Clear");
		btnFontColor = new JButton("Text Color");
		pnlButtons = new JPanel();
		
		this.setLayout(new BorderLayout());
		this.add(scroll, BorderLayout.CENTER); 
		pnlButtons.add(btnClear);
		pnlButtons.add(btnFontColor);
		this.add(pnlButtons, BorderLayout.SOUTH); //puts buttons at south location
		
		btnClear.addActionListener(new ActionListener() { //example of anonymous class			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//((JButton)e.getSource()).getText();
				txtEditor.setText("");
			}
		});  //registration method
		
		btnFontColor.addActionListener(new ColorHandler());
		
		
	}

	class ColorHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			txtEditor.setForeground(new Color(0, 0, 255));
		}
		
	}
}
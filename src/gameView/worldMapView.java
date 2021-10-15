package gameView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class worldMapView extends JFrame {

	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	

	
	
	public JPanel getPanel1() {
		return panel1;
	}
	public JPanel getPanel2() {
		return panel2;
	}
	public JPanel getPanel3() {
		return panel3;
	}
	public worldMapView() {
		//setLayout(new GridLayout(3,0));
		super.setVisible(true);
		setTitle("The Connquerer :: World Map");
		ImageIcon icon=new ImageIcon("clash.jpg");
		setIconImage(icon.getImage());
		getContentPane().setBackground(Color.white);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setExtendedState(MAXIMIZED_BOTH);
		
	
		
		
		
		panel1=new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.setPreferredSize(new Dimension(1920,50));
		//panel1.setBackground(Color.black);
		add(panel1,BorderLayout.NORTH);
		
		panel2=new JPanel();
		panel2.setPreferredSize(new Dimension(this.getWidth(),50));
	//	panel2.setBackground(Color.LIGHT_GRAY);
		add(panel2,BorderLayout.CENTER);
		
		panel3=new JPanel();
		panel3.setLayout(new FlowLayout());
		panel3.setPreferredSize(new Dimension(1920,500));
	//	panel3.setBackground(Color.black);
		add(panel3,BorderLayout.SOUTH);
		
		
		
	
	revalidate();
	repaint();
	
	}
	public static void main(String[] args) {
		worldMapView w=new worldMapView();
	}
}

package gameView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class battleView extends JFrame{
	JPanel panel1;
	 public JPanel panel2;
	JPanel panel3;
	JPanel panel4;

	
public battleView() {
	
	setLayout(null);
			super.setVisible(true);
			setTitle("The Connquerer :: World Map");
			ImageIcon icon=new ImageIcon("clash.jpg");
			setIconImage(icon.getImage());
			getContentPane().setBackground(Color.white);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			setExtendedState(MAXIMIZED_BOTH);
			
			panel1=new JPanel();
			panel1.setLayout(new FlowLayout());
			panel1.setBounds(0, 0, 1920, 50);;
			//panel1.setBackground(Color.red);
			add(panel1);
			
			panel2=new JPanel();
			panel2.setLayout(new GridLayout(0,6));
			panel2.setBounds(0, 50, 1920, 200);;
		    panel2.setBackground(Color.LIGHT_GRAY);
			add(panel2);
			
			panel3=new JPanel();
			panel3.setLayout(new GridLayout(0,6));
			panel3.setBounds(0, 250, 1920, 200);;
		    panel3.setBackground(Color.LIGHT_GRAY);
			add(panel3);
			
			
			panel4=new JPanel();
			panel4.setLayout(new FlowLayout());
			panel4.setBounds(0, 450, 1920, this.getHeight()-450);;
		    panel4.setBackground(Color.LIGHT_GRAY);
			add(panel4);
			
			
			
		
		revalidate();
		repaint();
}
public JPanel getPanel1() {
	return panel1;
}
public JPanel getPanel2() {
	return panel2;
}
public JPanel getPanel3() {
	return panel3;
}
public JPanel getPanel4() {
	return panel4;
}public static void main(String[] args) {
	battleView w=new battleView();
}

}

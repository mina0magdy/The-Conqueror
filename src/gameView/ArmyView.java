package gameView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ArmyView extends JFrame {
JPanel info;
JPanel units;



public ArmyView() {
	//setLayout(new GridLayout(3,0));
			super.setVisible(true);
			setTitle("The Connquerer :: Army View");
			ImageIcon icon=new ImageIcon("clash.jpg");
			setIconImage(icon.getImage());
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setExtendedState(MAXIMIZED_BOTH);
			
			
			
			info=new JPanel();
			info.setLayout(new FlowLayout());
			info.setPreferredSize(new Dimension(1920,50));
			add(info,BorderLayout.NORTH);
			
			units=new JPanel();
			units.setLayout(new GridLayout(0,5));
			units.setPreferredSize(new Dimension(1920,this.getHeight()-500));
			add(units,BorderLayout.CENTER);
			
			
			revalidate();
			repaint();
	
}



public JPanel getInfo() {
	return info;
}



public void setInfo(JPanel info) {
	this.info = info;
}



public JPanel getUnits() {
	return units;
}



public void setUnits(JPanel units) {
	this.units = units;
}
}

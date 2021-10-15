package gameView;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import engine.City;
import engine.Game;
import units.Army;
import units.Unit;

public class unitView extends JFrame {
JLabel label1;
JLabel label2;
	
	
public unitView(Game game,City city,ArrayList<Army> armies,Unit unit) {
	
	setVisible(true);
	setTitle("The Connquerer");
	ImageIcon icon=new ImageIcon("clash.jpg");
	setIconImage(icon.getImage());
	getContentPane().setBackground(Color.black);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	setExtendedState(MAXIMIZED_BOTH);
	
}
}

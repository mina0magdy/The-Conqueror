package gameView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class startGameView extends JFrame {

	JPanel up;
	JPanel down;


	
	public startGameView(){
	super.setVisible(true);
	setTitle("The Connquerer");
	ImageIcon icon=new ImageIcon("clash.jpg");
	setIconImage(icon.getImage());
	getContentPane().setBackground(Color.black);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	setExtendedState(MAXIMIZED_BOTH);
	
	
	
	//start game
		
		JLabel label=new JLabel("Hello Conqueror :D");
		ImageIcon icon1=new ImageIcon("ps.jpg");
		label.setIcon(icon1);
		add(label,BorderLayout.NORTH);
		label.setVerticalTextPosition(JLabel.TOP);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setIconTextGap(10);
		label.setFont(new Font("MV Boli",Font.ITALIC,40));
		label.setForeground(Color.black);
		
		
	
		
		


		


	revalidate();
	repaint();
	}
	public static void main(String[] args) {
		startGameView v=new startGameView();
	}

	
	
	
}

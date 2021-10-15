package gameView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class CityView extends JFrame {
JPanel info;
JPanel buildings;
JPanel armies;


	
	public CityView() {
		super.setVisible(true);
		setTitle("The Connquerer :: City View");
		ImageIcon icon=new ImageIcon("clash.jpg");
		setIconImage(icon.getImage());
		getContentPane().setBackground(Color.white);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setExtendedState(MAXIMIZED_BOTH);
		
		
		info=new JPanel();
		info.setLayout(new FlowLayout());
		info.setPreferredSize(new Dimension(1920,50));
		//info.setBackground(Color.black);
		add(info,BorderLayout.NORTH);
		
		buildings=new JPanel();
		buildings.setLayout(new FlowLayout());
		buildings.setPreferredSize(new Dimension(this.getWidth(),50));
	//	buildings.setBackground(Color.LIGHT_GRAY);
		add(buildings,BorderLayout.CENTER);
		
		armies=new JPanel();
		armies.setLayout(new GridLayout(1,0));
		armies.setPreferredSize(new Dimension(1920,400));
	//	armies.setBackground(Color.black);
		add(armies,BorderLayout.SOUTH);
		
		revalidate();
		repaint();
	}
	public JPanel getInfo() {
		return info;
	}
	public JPanel getBuildings() {
		return buildings;
	}
	public JPanel getArmies() {
		return armies;
	}
	public static void main(String[] args) {
		CityView c=new CityView();
	}
}

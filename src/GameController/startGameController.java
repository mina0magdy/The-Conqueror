package GameController;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import buildings.Farm;
import buildings.Market;
import engine.City;
import engine.Game;
import gameView.startGameView;
import gameView.worldMapView;

public class startGameController implements ActionListener {
Game Game;


//startGame
startGameView startView;
JPanel down;
JTextField textField;
JButton cairo;
JButton rome;
JButton sparta;


//world map
WorldMapController world;




public startGameController() {
	
	//start game
	startView=new startGameView();
	down=new JPanel();
	down.setVisible(true);
	down.setPreferredSize(new Dimension(startView.getWidth(),300));
	down.setBackground(Color.black);
	down.setLayout(new FlowLayout());
	startView.add(down,BorderLayout.SOUTH);
	
	
	//enter name
	textField=new JTextField();
	textField.setText("Enter Your Name");
	textField.setPreferredSize(new Dimension(200,50));
	textField.setFont(new Font("MV Boli",Font.ITALIC,20));
	down.add(textField);
	
	//choose city
	cairo=new JButton("Cairo");
	cairo.setPreferredSize(new Dimension(100,50));
	cairo.addActionListener(this);
	rome=new JButton("Rome");
	rome.setPreferredSize(new Dimension(100,50));
	rome.addActionListener(this);
	sparta=new JButton("Sparta");
	sparta.setPreferredSize(new Dimension(100,50));
	sparta.addActionListener(this);
	
	down.add(cairo);
	down.add(rome);
	down.add(sparta);
	
	startView.revalidate();
	startView.repaint();

}

@Override
public void actionPerformed(ActionEvent e) {
	String text=textField.getText();

	String city="";
	
if(e.getSource()==cairo) {
	try {
		Game=new Game(text, "Cairo");
		city="Cairo";
	} catch (IOException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
	startView.dispose();	
}
if(e.getSource()==rome) {
	try {
		Game=new Game(text,"Rome");
		city="Rome";

	} catch (IOException e1) {
		e1.printStackTrace();
	}
	startView.dispose();

}
if(e.getSource()==sparta) {
	try {
		Game=new Game(text,"Sparta");
		city="Sparta";

	} catch (IOException e1) {
		e1.printStackTrace();
	}
	startView.dispose();
}
 world =new WorldMapController(Game);

}
public Game getGame() {
	return Game;
}

public static void main(String[] args) {
	startGameController control=new startGameController();
}
}

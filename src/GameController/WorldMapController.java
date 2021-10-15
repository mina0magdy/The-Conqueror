package GameController;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import buildings.Market;
import engine.City;
import engine.Game;
import engine.Player;
import exceptions.FriendlyCityException;
import exceptions.FriendlyFireException;
import exceptions.TargetNotReachedException;
import gameView.worldMapView;
import units.*;

public class WorldMapController implements ActionListener{
	worldMapView world;
	JButton cairoView;
	JButton romeView;
	JButton spartaView;
	JTextArea playerInfo;
	JTextArea idleArmy;
	JTextArea marchingArmy;
	JTextArea besiegingArmy;
	Game g;
	JButton endTurn;
	
	JComboBox laySiege;
	JComboBox Attack;
	City selectedCity;
	Army selectedArmy;
	
	
	public WorldMapController(Game game){
		world=new worldMapView();
		g=game;
		selectedArmy=null;
		selectedCity=null;
			
		endTurn=new JButton("End Turn");
		endTurn.setPreferredSize(new Dimension(100,world.getPanel1().getHeight()));
		endTurn.addActionListener(this);
		
		world.getPanel2().setLayout(new FlowLayout());
		world.getPanel3().setLayout(new FlowLayout());
		// player info
		playerInfo=new JTextArea();
		playerInfo.setText("Name:: "+game.getPlayer().getName()+"      "+"Gold:: "+game.getPlayer().getTreasury()+"      "+"Food:: "+game.getPlayer().getFood()+"      "+"TurnsCount:: "+game.getCurrentTurnCount());
		playerInfo.setFont(new Font("MV Boli",Font.ITALIC,25));
		playerInfo.setPreferredSize(new Dimension(world.getPanel1().getWidth()-360,world.getPanel1().getHeight()));
		playerInfo.setEditable(false);
		
		
		String[]lay= {"laySiege"};
		laySiege=new JComboBox(lay);
		laySiege.addActionListener(this);
		laySiege.setPreferredSize(new Dimension(100,world.getPanel1().getHeight()));

		for(Army a:g.getPlayer().getControlledArmies()) {
			String name=a.getCurrentLocation();
			City c=g.getCity(name, g.getAvailableCities());
			ArrayList<String> becity= new ArrayList<>();
			if(!g.getPlayer().getControlledCities().contains(c)&&c!=null&&!c.isUnderSiege()&&!becity.contains(name)) {
				becity.add(name);
				laySiege.addItem(c.getName());
				
				}
				
			}
		
		
		String[]attack= {"Attack"};
		Attack=new JComboBox(attack);
		Attack.setPreferredSize(new Dimension(100,world.getPanel1().getHeight()));
		Attack.addActionListener(this);
		for(Army a:g.getPlayer().getControlledArmies()) {
			String name=a.getCurrentLocation();
			City c=g.getCity(name, g.getAvailableCities());
			if(!g.getPlayer().getControlledCities().contains(c)&&c!=null) {
				Attack.addItem(c.getName());
			}
		}

		
		
		
		 // map view cities
		 cairoView=new JButton("Cairo View");
		 cairoView.setPreferredSize(new Dimension(500,world.getPanel2().getHeight()));
		 cairoView.setEnabled(false);
		 cairoView.addActionListener(this);
		 
		 romeView=new JButton("Rome View");
		 romeView.setPreferredSize(new Dimension(500,world.getPanel2().getHeight()));
		 romeView.setEnabled(false);
		 romeView.addActionListener(this);
		 
		 spartaView=new JButton("Sparta View");
		 spartaView.setPreferredSize(new Dimension(500,world.getPanel2().getHeight()));
		 spartaView.setEnabled(false);
		 spartaView.addActionListener(this);
		 
		 world.getPanel1().add(playerInfo);
		 world.getPanel1().add(endTurn);
		 world.getPanel1().add(laySiege);
		 world.getPanel1().add(Attack);
		 world.getPanel2().add(cairoView);
		 world.getPanel2().add(romeView);
		 world.getPanel2().add(spartaView);
		 
		//intiate text areas
		idleArmy=new JTextArea();
		idleArmy.setPreferredSize(new Dimension(480,world.getPanel3().getHeight()*5));
		idleArmy.setBackground(Color.white);
		JScrollPane scroll1 = new JScrollPane(idleArmy);
		scroll1.setPreferredSize(new Dimension(480,world.getPanel3().getHeight()-20));
		
		marchingArmy=new JTextArea();
		marchingArmy.setPreferredSize(new Dimension(480,world.getPanel3().getHeight()*5));
		marchingArmy.setBackground(Color.white);
		JScrollPane scroll2 = new JScrollPane(marchingArmy);
		scroll2.setPreferredSize(new Dimension(480,world.getPanel3().getHeight()-20));

		
		besiegingArmy=new JTextArea();
		besiegingArmy.setPreferredSize(new Dimension(480,world.getPanel3().getHeight()*5));
		besiegingArmy.setBackground(Color.white);
		JScrollPane scroll3 = new JScrollPane(besiegingArmy);
	    scroll3.setPreferredSize(new Dimension(480,world.getPanel3().getHeight()-20));


		
		String idle="IDLE Armies: "+"\n"+"---------------"+"\n";
		String marching="Marching Armies: "+"\n"+"---------------"+"\n";
		String besieging="Besieging Armies: "+"\n"+"---------------"+"\n";
		int count=1;
		
		for(City c:g.getPlayer().getControlledCities()) {
			idle+=c.getName()+" Defending Army"+"\n"+"\n"+"-------------"+"\n"+c.getDefendingArmy().toString();		}
	for(Army a:game.getPlayer().getControlledArmies()) {
		if(a.getCurrentStatus()==Status.IDLE) {
			idle+="Army "+count+"\n"+"Current Location: "+a.getCurrentLocation()+"\n"+"-------------"+"\n"+a.toString();	
		}
		if(a.getCurrentStatus()==Status.MARCHING) {
			marching+="Army "+count+"\n"+"Target City: "+a.getTarget()+"\n"+"Turns to Arrive: "+a.getDistancetoTarget()+"\n"+"-------------"+"\n"+a.toString();	
		}
		if(a.getCurrentStatus()==Status.BESIEGING) {
			besieging+="Army "+count+"\n"+"Besieged City:"+a.getCurrentLocation()+"\n"+"Turns Under Siege: "+getCity(a.getCurrentLocation(),game.getAvailableCities()).getTurnsUnderSiege()+"\n"+"-------------"+"\n"+a.toString();	
		}
		count++;
	}

	
		
	idleArmy.setText(idle);
	marchingArmy.setText(marching);
	besiegingArmy.setText(besieging);
	idleArmy.setEditable(true);
	marchingArmy.setEditable(false);
	besiegingArmy.setEditable(false);
		
	 world.getPanel3().add(scroll1);
	 world.getPanel3().add(scroll2);
	 world.getPanel3().add(scroll3);

		// enable buttons of controlled cities only	 
		for(City c:game.getPlayer().getControlledCities()) {
			if(c.getName().equals("Cairo")) 
				cairoView.setEnabled(true);
			if(c.getName().equals("Rome")) 
				romeView.setEnabled(true);
			if(c.getName().equals("Sparta")) 
				spartaView.setEnabled(true);
		}

			 
		world.revalidate();
		world.repaint();
		 }
		 
	
public City getCity(String name,ArrayList<City> array) {
	for(City c:array) {
		if(c.getName().equals(name))
			return c;
	}
	return null;
}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==cairoView) {
			world.dispose();
			City c=getCity("Cairo",g.getAvailableCities());
			new CityController(c,g);
			
		}
		if(e.getSource()==romeView) {
			world.dispose();
			City c=getCity("Rome",g.getAvailableCities());
			new CityController(c,g);
			
		}
		if(e.getSource()==spartaView) {
			world.dispose();
			City c=getCity("Sparta",g.getAvailableCities());
			new CityController(c,g);
			
		}
		boolean flag=false;
		for(City c:g.getAvailableCities()) {
			if(c.getTurnsUnderSiege()==2&&!g.getPlayer().getControlledCities().contains(c)) {
				selectedCity=c;
				flag=true;
			}
			else if(!flag)selectedCity=null;
		}
		for(Army a:g.getPlayer().getControlledArmies()) {
			if(selectedCity!=null&&a.getCurrentLocation().equals(selectedCity.getName())&&a.getCurrentStatus()==Status.BESIEGING)
				selectedArmy=a;
		}
		if(e.getSource()==endTurn) {
			for(Army a:g.getPlayer().getControlledArmies()) {
				if(a.equals(null))
					g.getPlayer().getControlledArmies().remove(a);
			}
			g.endTurn();
			world.dispose();
		WorldMapController r=	new WorldMapController(g);
			if(g.getPlayer().getControlledCities().size() == g.getAvailableCities().size() ) {
				JOptionPane.showMessageDialog(null, "Congrats Conqueror ", "The Game", JOptionPane.OK_OPTION);
				r.world.dispose();
				System.exit(0);

			}
			if(g.getCurrentTurnCount()>g.getMaxTurnCount()) {		
				JOptionPane.showMessageDialog(null, "Loser!!!! ", "Game Over", JOptionPane.OK_OPTION);
				r.world.dispose();;	
				System.exit(0);

			}	
			if(selectedCity!=null) {
				System.out.println("auto");
				String[]f= {"Auto Resolve","Manual Attack"};
				boolean flag1=true;
				while(flag1) {
				int option=JOptionPane.showInternalOptionDialog(null,"Choose Action","Choose Action on Army",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,f,0);
				if(option==0) {
					world.dispose();
					try {
						selectedArmy.setCurrentStatus(Status.IDLE);
						g.getCity(selectedCity.getName(), g.getAvailableCities()).setTurnsUnderSiege(-1);;
						g.autoResolve(selectedArmy, selectedCity.getDefendingArmy());
						selectedCity.setUnderSiege(false);
						if(selectedArmy.getUnits().size()>0) {
							JOptionPane.showMessageDialog(null, "You Won The Battle", "Battle Review", JOptionPane.INFORMATION_MESSAGE);
						}
						else
							JOptionPane.showMessageDialog(null, "You Lost The Battle", "Battle Review", JOptionPane.INFORMATION_MESSAGE);
						if(g.getPlayer().getControlledCities().size() == g.getAvailableCities().size() ) {
							JOptionPane.showMessageDialog(null, "Congrats Conqueror ", "The Game", JOptionPane.OK_OPTION);
							world.dispose();
							System.exit(0);

						}
						else if(g.getCurrentTurnCount()>g.getMaxTurnCount()) {		
							JOptionPane.showMessageDialog(null, "Loser!!!! ", "Game Over", JOptionPane.OK_OPTION);
							world.dispose();;	
							System.exit(0);

						}
						world.dispose();		
						if(!g.isGameOver()) {
						world.dispose();
						new WorldMapController(g);
						}
						if(g.isGameOver())
							System.exit(0);
					} catch (FriendlyFireException e1) {
					}
				}
				if(option==1) {
					selectedArmy.setCurrentStatus(Status.IDLE);
					selectedCity.setUnderSiege(false);
					g.getCity(selectedCity.getName(), g.getAvailableCities()).setTurnsUnderSiege(-1);;
					new battleController(selectedArmy,g,"LAST ACTION OCCURED"+"\n"+"**********************"+"\n");
				}
				flag1=false;
				if(option==-1) {
					flag1=true;
				}
			}
			}
		}
		
		if(e.getSource()==laySiege) {
			String s=(String) laySiege.getSelectedItem();
			if(!s.equals("laySiege")) {
				for(Army a:g.getPlayer().getControlledArmies()) {
					if(a.getCurrentLocation().equals(s)) {
						try {
							
							g.getPlayer().laySiege(a, g.getCity(s, g.getAvailableCities()));
							
						} catch (TargetNotReachedException | FriendlyCityException e1) {	
					}
				}
			}
				world.dispose();
				new WorldMapController(g);
		}
	}
		
		if(e.getSource()==Attack) {
			String s=(String) Attack.getSelectedItem();
			if(!s.equals("Attack")) {
				for(Army a:g.getPlayer().getControlledArmies()) {
					if(a.getCurrentLocation().equals(s)) {
						String[] s1= {"Auto Resolve","Manual Attack"};
						int option=JOptionPane.showInternalOptionDialog(null,"Choose Action","Choose Action on Army",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,s1,0);
						if(option==0) {
							//world.dispose();
							
							try {
								a.setCurrentStatus(Status.IDLE);
								g.getCity(s, g.getAvailableCities()).setTurnsUnderSiege(-1);;
								g.autoResolve(a, g.getCity(s, g.getAvailableCities()).getDefendingArmy());
								g.getCity(s, g.getAvailableCities()).setUnderSiege(false);								
								if(a.getUnits().size()>0) {
									//g.occupy(a, a.getCurrentLocation());
									JOptionPane.showMessageDialog(null, "You Won The Battle", "Battle Review", JOptionPane.INFORMATION_MESSAGE);
								}
								else
									JOptionPane.showMessageDialog(null, "You Lost The Battle", "Battle Review", JOptionPane.INFORMATION_MESSAGE);
								world.dispose();
								if(g.getPlayer().getControlledCities().size() == g.getAvailableCities().size() ) {
									JOptionPane.showMessageDialog(null, "Congrats Conqueror ", "The Game", JOptionPane.OK_OPTION);
									world.dispose();
									System.exit(0);

								}
								else if(g.getCurrentTurnCount()>g.getMaxTurnCount()) {		
									JOptionPane.showMessageDialog(null, "Loser!!!! ", "Game Over", JOptionPane.OK_OPTION);
									world.dispose();;	
									System.exit(0);

								}
								
								if(!g.isGameOver())
								new WorldMapController(g);
							}
							catch (FriendlyFireException e1) {
							}
						}
						if(option==1) {
							world.dispose();
							a.setCurrentStatus(Status.IDLE);
							g.getCity(s, g.getAvailableCities()).setTurnsUnderSiege(-1);;
							g.getCity(s, g.getAvailableCities()).setUnderSiege(false);
							new battleController(a,g,"LAST ACTION OCCURED"+"\n"+"**********************"+"\n");
						}
					}
				
				}
			}
			}
		}
}


package GameController;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.xml.crypto.dsig.XMLSignature.SignatureValue;

import engine.*;
import exceptions.BuildingInCoolDownException;
import exceptions.FriendlyFireException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;
import gameView.CityView;
import GameController.*;
import buildings.Building;
import units.Army;
import units.Status;
import buildings.*;
public class CityController implements ActionListener {
	CityView cityView;
	Game g;
	City c;
	JButton Farm;
	JButton Market;
	JButton ArcheryRange;
	JButton Barracks;
	JButton Stable;
	ArrayList<JButton> armyButtons;
	JButton defendingArmy;
	JTextArea playerInfo;
	JButton worldView;
	JComboBox build;
	JButton endTurn;
	ArrayList<Army> armies;
	
	
	City selectedCity;
	Army selectedArmy;

public CityController(City city,Game game) {
	cityView=new CityView();
	g=game;
	c=city;
	armyButtons=new ArrayList<JButton>();
	armies= new ArrayList<Army>();
	
	playerInfo=new JTextArea();
	playerInfo.setText("Name:: "+game.getPlayer().getName()+"      "+"Gold:: "+game.getPlayer().getTreasury()+"      "+"Food:: "+game.getPlayer().getFood()+"      "+"TurnsCount:: "+game.getCurrentTurnCount());
	playerInfo.setFont(new Font("MV Boli",Font.ITALIC,25));
	playerInfo.setPreferredSize(new Dimension(cityView.getInfo().getWidth()-450,cityView.getInfo().getHeight()));
	playerInfo.setEditable(false);
	cityView.getInfo().add(playerInfo);
	
	endTurn=new JButton("End Turn");
	endTurn.setPreferredSize(new Dimension(100,cityView.getInfo().getHeight()));
	endTurn.addActionListener(this);
	
	String[]x= {"Build","Farm 1000$","Market 1500$","ArcheryRange 1500$","Barracks 2000$","Stable 2500$"};
	build=new JComboBox(x);
	build.setPreferredSize(new Dimension(150,cityView.getInfo().getHeight()));
	build.addActionListener(this);
	cityView.getInfo().add(build);
	
	worldView=new JButton("World Map");
	worldView.setPreferredSize(new Dimension(100,cityView.getInfo().getHeight()));
	worldView.addActionListener(this);
	cityView.getInfo().add(worldView);
	
	Farm=new JButton("Farm");
	Farm.setPreferredSize(new Dimension(300,cityView.getBuildings().getHeight()));
	Farm.setEnabled(false);
	Farm.addActionListener(this);
	cityView.getBuildings().add(Farm);
	
	Market=new JButton("Market");
	Market.setPreferredSize(new Dimension(300,cityView.getBuildings().getHeight()));
	Market.addActionListener(this);
	Market.setEnabled(false);
	cityView.getBuildings().add(Market);
	
	ArcheryRange=new JButton("ArcheryRange");
	ArcheryRange.setPreferredSize(new Dimension(300,cityView.getBuildings().getHeight()));
	ArcheryRange.addActionListener(this);
	ArcheryRange.setEnabled(false);
	cityView.getBuildings().add(ArcheryRange);
	
	Barracks=new JButton("Barracks");
	Barracks.setPreferredSize(new Dimension(300,cityView.getBuildings().getHeight()));
	Barracks.addActionListener(this);
	Barracks.setEnabled(false);
	cityView.getBuildings().add(Barracks);
	
	Stable=new JButton("Stable");
	Stable.setPreferredSize(new Dimension(300,cityView.getBuildings().getHeight()));
	Stable.addActionListener(this);
	Stable.setEnabled(false);
	cityView.getBuildings().add(Stable);
	
	
	
	
	defendingArmy=new JButton("Defending Army");
	c.getDefendingArmy().setArmyName(c.getName()+" Defending");
	defendingArmy.addActionListener(this);
	cityView.getArmies().add(defendingArmy);
	
	

	
	for(Building b:city.getEconomicalBuildings()) {
		if(b instanceof Farm) {
			Farm.setEnabled(true);
			Farm.setText("Farm   "+"Level "+b.getLevel());
			build.removeItem("Farm 1000$");
		}
		if(b instanceof Market) {
			Market.setEnabled(true);
			Market.setText("Market   "+"Level "+b.getLevel());
			build.removeItem("Market 1500$");
		}
	}
	for(Building b:city.getMilitaryBuildings()) {
		if(b instanceof ArcheryRange) {
			ArcheryRange.setEnabled(true);
			ArcheryRange.setText("ArcheryRange   "+"Level "+b.getLevel());
			build.removeItem("ArcheryRange 1500$");
		}
		if(b instanceof Barracks) {
			Barracks.setEnabled(true);
			Barracks.setText("Barracks   "+"Level "+b.getLevel());
			build.removeItem("Barracks 2000$");
		}
		if(b instanceof Stable) {
			Stable.setEnabled(true);
			Stable.setText("Stable   "+"Level "+b.getLevel());
			build.removeItem("Stable 2500$");
		}
	}
	
	
	
	int i=1;
	for(Army a:game.getPlayer().getControlledArmies()) {
		if(a.getCurrentLocation().equals(city.getName())) {
			JButton Army=new JButton("Army"+i);
			Army.setActionCommand("Army"+i);
			Army.addActionListener(this);
			armyButtons.add(Army);
			a.setArmyName("Army"+i);
			armies.add(a);
			i++;
		}
	}
	for(JButton b:armyButtons) {
		cityView.getArmies().add(b);
	}
	
	
	cityView.getInfo().add(endTurn);
	cityView.revalidate();
	cityView.repaint();
	
	
}
public static void main(String[] args) throws IOException {
	Game game=new Game("mina","Cairo");
	City c=new City("Cairo");
	Farm f=new Farm();
	c.getEconomicalBuildings().add(f);
	Army a=new Army("Cairo");
	game.getPlayer().getControlledArmies().add(a);
	CityController c1=new CityController(c, game);
}
@Override
public void actionPerformed(ActionEvent e) {

	if(e.getSource()==worldView) {
		cityView.dispose();
		new WorldMapController(g);
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
		cityView.dispose();
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
				cityView.dispose();
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
						cityView.dispose();
						System.exit(0);

					}
					else if(g.getCurrentTurnCount()>g.getMaxTurnCount()) {		
						JOptionPane.showMessageDialog(null, "Loser!!!! ", "Game Over", JOptionPane.OK_OPTION);
						cityView.dispose();;	
						System.exit(0);

					}
					cityView.dispose();		
					if(!g.isGameOver()) {
					cityView.dispose();
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
	if(e.getSource()==build) {
		String s=(String) build.getSelectedItem();
		if(s=="Farm 1000$") {
			try {
				g.getPlayer().build("farm", c.getName());
				cityView.dispose();
				new CityController(c, g);
			} catch (NotEnoughGoldException e1) {
				
				JOptionPane.showMessageDialog(null, "Not Enough Gold","Not Enough Gold" , JOptionPane.WARNING_MESSAGE);
			}
		}
		if(s=="Market 1500$") {
			try {
				g.getPlayer().build("market", c.getName());
				cityView.dispose();
				new CityController(c, g);
			} catch (NotEnoughGoldException e1) {
				
				JOptionPane.showMessageDialog(null, "Not Enough Gold","Not Enough Gold" , JOptionPane.WARNING_MESSAGE);
			}
		}
		if(s=="ArcheryRange 1500$") {
			try {
				g.getPlayer().build("archeryrange", c.getName());
				cityView.dispose();
				new CityController(c, g);
			} catch (NotEnoughGoldException e1) {
				
				JOptionPane.showMessageDialog(null, "Not Enough Gold","Not Enough Gold" , JOptionPane.WARNING_MESSAGE);
			}
		}
		if(s=="Barracks 2000$") {
			try {
				g.getPlayer().build("barracks", c.getName());
				cityView.dispose();
				new CityController(c, g);
			} catch (NotEnoughGoldException e1) {
				
				JOptionPane.showMessageDialog(null, "Not Enough Gold","Not Enough Gold" , JOptionPane.WARNING_MESSAGE);
			}
		}
		if(s=="Stable 2500$") {
			try {
				g.getPlayer().build("stable", c.getName());
				cityView.dispose();
				new CityController(c, g);
			} catch (NotEnoughGoldException e1) {
				
				JOptionPane.showMessageDialog(null, "Not Enough Gold","Not Enough Gold" , JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}
	if(e.getSource()==Farm) {
		
		Farm farm=null;
		for(EconomicBuilding e1:c.getEconomicalBuildings()) {
			if(e1 instanceof Farm) {
				farm=(buildings.Farm) e1;
			}
				
		}
		String[] f= {"Upgrade "+farm.getUpgradeCost()+"$"};

		int option=JOptionPane.showInternalOptionDialog(null,"Choose Action","Choose Action on Farm",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,f,0);

		if(option==0) {
			try {
				g.getPlayer().upgradeBuilding(farm);
				cityView.dispose();
				new CityController(c, g);
			} catch (NotEnoughGoldException e2) {
			    JOptionPane.showMessageDialog(null, "Not Enough Gold","Not Enough Gold" , JOptionPane.WARNING_MESSAGE);
			}
			catch (BuildingInCoolDownException e2) {
				JOptionPane.showMessageDialog(null, "This Building is Cooling Down","This Building is Cooling Down" , JOptionPane.WARNING_MESSAGE);

			}
			catch (MaxLevelException e2) {
				JOptionPane.showMessageDialog(null, "Max Level reached","Max Level reached" , JOptionPane.WARNING_MESSAGE);
			}
			
		}
	}
	if(e.getSource()==Market) {
		Market market=null;
		for(EconomicBuilding e1:c.getEconomicalBuildings()) {
			if(e1 instanceof Market) {
				market=(buildings.Market) e1;
			}
				
		}
		String[] f= {"Upgrade "+market.getUpgradeCost()+"$"};
		int option=JOptionPane.showInternalOptionDialog(null,"Choose Action","Choose Action on Market",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,f,0);

		if(option==0) {
			try {
				g.getPlayer().upgradeBuilding(market);
				cityView.dispose();
				new CityController(c, g);
			} catch (NotEnoughGoldException e2) {
			    JOptionPane.showMessageDialog(null, "Not Enough Gold","Not Enough Gold" , JOptionPane.WARNING_MESSAGE);
			}
			catch (BuildingInCoolDownException e2) {
				JOptionPane.showMessageDialog(null, "This Building is Cooling Down","This Building is Cooling Down" , JOptionPane.WARNING_MESSAGE);

			}
			catch (MaxLevelException e2) {
				JOptionPane.showMessageDialog(null, "Max Level reached","Max Level reached" , JOptionPane.WARNING_MESSAGE);
			}
			
		}
	}
	if(e.getSource()==ArcheryRange) {
		
		ArcheryRange archeryrange=null;
		for(MilitaryBuilding e1:c.getMilitaryBuildings()) {
			if(e1 instanceof ArcheryRange) {
				archeryrange=(buildings.ArcheryRange) e1;
			}
				
		}
		String[] f= {"Upgrade "+archeryrange.getUpgradeCost()+"$","Recruit "+archeryrange.getRecruitmentCost()+"$"};

		int option=JOptionPane.showInternalOptionDialog(null,"Choose Action ","Choose Action on ArcheryRange",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,f,0);

		if(option==0) {
			try {
				g.getPlayer().upgradeBuilding(archeryrange);
				cityView.dispose();
				new CityController(c, g);
			} catch (NotEnoughGoldException e2) {
			    JOptionPane.showMessageDialog(null, "Not Enough Gold","Not Enough Gold" , JOptionPane.WARNING_MESSAGE);
			}
			catch (BuildingInCoolDownException e2) {
				JOptionPane.showMessageDialog(null, "This Building is Cooling Down","This Building is Cooling Down" , JOptionPane.WARNING_MESSAGE);

			}
			catch (MaxLevelException e2) {
				JOptionPane.showMessageDialog(null, "Max Level reached","Max Level reached" , JOptionPane.WARNING_MESSAGE);
			}
			
		}
		
		if(option==1) {
			try {
				g.getPlayer().recruitUnit("Archer", c.getName());
				cityView.dispose();
				new CityController(c, g);
			}
			catch (BuildingInCoolDownException e2) {
				JOptionPane.showMessageDialog(null, "This Building is Cooling Down","This Building is Cooling Down" , JOptionPane.WARNING_MESSAGE);
			}
			catch (MaxRecruitedException e2) {
			    JOptionPane.showMessageDialog(null, "Max Recruit Reached","Max Recruit Reached" , JOptionPane.WARNING_MESSAGE);
			}
			catch (NotEnoughGoldException e2) {
			    JOptionPane.showMessageDialog(null, "Not Enough Gold","Not Enough Gold" , JOptionPane.WARNING_MESSAGE);
			}
		}
	}
if(e.getSource()==Barracks) {
		
		Barracks barracks=null;
		for(MilitaryBuilding e1:c.getMilitaryBuildings()) {
			if(e1 instanceof Barracks) {
				barracks=(buildings.Barracks) e1;
			}
				
		}
		String[] f= {"Upgrade "+barracks.getUpgradeCost()+"$","Recruit "+barracks.getRecruitmentCost()+"$"};

		int option=JOptionPane.showInternalOptionDialog(null,"Choose Action ","Choose Action on Barracks",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,f,0);

		if(option==0) {
			try {
				g.getPlayer().upgradeBuilding(barracks);
				cityView.dispose();
				new CityController(c, g);
			} catch (NotEnoughGoldException e2) {
			    JOptionPane.showMessageDialog(null, "Not Enough Gold","Not Enough Gold" , JOptionPane.WARNING_MESSAGE);
			}
			catch (BuildingInCoolDownException e2) {
				JOptionPane.showMessageDialog(null, "This Building is Cooling Down","This Building is Cooling Down" , JOptionPane.WARNING_MESSAGE);

			}
			catch (MaxLevelException e2) {
				JOptionPane.showMessageDialog(null, "Max Level reached","Max Level reached" , JOptionPane.WARNING_MESSAGE);
			}
			
		}
		if(option==1) {
			try {
				g.getPlayer().recruitUnit("Infantry", c.getName());
				cityView.dispose();
				new CityController(c, g);
			}
			catch (BuildingInCoolDownException e2) {
				JOptionPane.showMessageDialog(null, "This Building is Cooling Down","This Building is Cooling Down" , JOptionPane.WARNING_MESSAGE);
			}
			catch (MaxRecruitedException e2) {
			    JOptionPane.showMessageDialog(null, "Max Recruit Reached","Max Recruit Reached" , JOptionPane.WARNING_MESSAGE);
			}
			catch (NotEnoughGoldException e2) {
			    JOptionPane.showMessageDialog(null, "Not Enough Gold","Not Enough Gold" , JOptionPane.WARNING_MESSAGE);
			}
		}
	}
if(e.getSource()==Stable) {
	
	Stable stable=null;
	for(MilitaryBuilding e1:c.getMilitaryBuildings()) {
		if(e1 instanceof Stable) {
			stable=(buildings.Stable) e1;
		}
			
	}
	String[] f= {"Upgrade "+stable.getUpgradeCost()+"$","Recruit "+stable.getRecruitmentCost()+"$"};

	int option=JOptionPane.showInternalOptionDialog(null,"Choose Action ","Choose Action on Stable",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,f,0);

	if(option==0) {
		try {
			g.getPlayer().upgradeBuilding(stable);
			cityView.dispose();
			new CityController(c, g);
		} catch (NotEnoughGoldException e2) {
		    JOptionPane.showMessageDialog(null, "Not Enough Gold","Not Enough Gold" , JOptionPane.WARNING_MESSAGE);
		}
		catch (BuildingInCoolDownException e2) {
			JOptionPane.showMessageDialog(null, "This Building is Cooling Down","This Building is Cooling Down" , JOptionPane.WARNING_MESSAGE);

		}
		catch (MaxLevelException e2) {
			JOptionPane.showMessageDialog(null, "Max Level reached","Max Level reached" , JOptionPane.WARNING_MESSAGE);
		}
		
	}
	if(option==1) {
		try {
			g.getPlayer().recruitUnit("Cavalry", c.getName());
			cityView.dispose();
			new CityController(c, g);
		}
		catch (BuildingInCoolDownException e2) {
			JOptionPane.showMessageDialog(null, "This Building is Cooling Down","This Building is Cooling Down" , JOptionPane.WARNING_MESSAGE);
		}
		catch (MaxRecruitedException e2) {
		    JOptionPane.showMessageDialog(null, "Max Recruit Reached","Max Recruit Reached" , JOptionPane.WARNING_MESSAGE);
		}
		catch (NotEnoughGoldException e2) {
		    JOptionPane.showMessageDialog(null, "Not Enough Gold","Not Enough Gold" , JOptionPane.WARNING_MESSAGE);
		}
	}
}


if(e.getSource()==defendingArmy) {
	cityView.dispose();
	System.out.println(c.getDefendingArmy().getArmyName());
	new ArmyController(g, c.getDefendingArmy(), c,armies);
}

for(int i=0;i<armies.size();i++) {
	int j=i+1;
	if(e.getActionCommand().equals("Army"+j)) {
		cityView.dispose();
		System.out.println(armies.get(i).getArmyName());
		new ArmyController(g, armies.get(i), c,armies);
		
	}
}
	
	
			
		}

	}
	
	

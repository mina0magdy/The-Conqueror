package GameController;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.LinearGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.DomainCombiner;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import buildings.ArcheryRange;
import engine.City;
import engine.Game;
import engine.LInearArray;
import exceptions.FriendlyCityException;
import exceptions.FriendlyFireException;
import exceptions.MaxCapacityException;
import exceptions.TargetNotReachedException;
import gameView.ArmyView;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Status;
import units.Unit;

public class ArmyController implements ActionListener {

	Game g;
	Army a;
	City c;
	ArmyView view;
	
	JTextArea playerInfo;
	JButton endTurn;
	JComboBox TargetCity;
	JButton CityView;
	JComboBox relocate;
	JButton initiate;
	JComboBox laySiege;
	JButton Attack;
	
	ArrayList<Army> armies;
	ArrayList<JButton> armyUnits;
	
	JButton selectedButton;
	Unit selectedUnit;
	Army selectedArmy;
	
	City selectedCity;
	//Army armyEndTurn;

	
	public ArmyController(Game game,Army army,City city,ArrayList<Army> armies) {
		g=game;
		a=army;
		c=city;
		this.armies=armies;
		armyUnits=new ArrayList<JButton>();
		view=new ArmyView();
		
		// player info
				playerInfo=new JTextArea();
				playerInfo.setText("Name:: "+game.getPlayer().getName()+"      "+"Gold:: "+game.getPlayer().getTreasury()+"      "+"Food:: "+game.getPlayer().getFood()+"      "+"TurnsCount:: "+game.getCurrentTurnCount());
				playerInfo.setFont(new Font("MV Boli",Font.ITALIC,20));
				playerInfo.setPreferredSize(new Dimension(view.getInfo().getWidth()-560,view.getInfo().getHeight()));
				playerInfo.setEditable(false);
			
				
				
				endTurn=new JButton("End Turn");
				endTurn.setPreferredSize(new Dimension(100,view.getInfo().getHeight()));
				endTurn.addActionListener(this);
				
				CityView=new JButton("City View");
				CityView.setPreferredSize(new Dimension(100,view.getInfo().getHeight()));
				CityView.addActionListener(this);
				
				String[] targetCity= {"Target City","Cairo","Rome","Sparta"};
				TargetCity=new JComboBox(targetCity);
				TargetCity.setPreferredSize(new Dimension(100,view.getInfo().getHeight()));
				TargetCity.addActionListener(this);
				if(city.getDefendingArmy().equals(a))
					TargetCity.setEnabled(false);
				for(City c:g.getPlayer().getControlledCities()) {
					TargetCity.removeItem(c.getName());
				}
				
				
				
				LInearArray array=new LInearArray(1000);
				for(Army a:armies) {
					array.insertLast(a.getArmyName());
				}
				//array.insertFirst(c.getName()+" Defending");
				array.insertFirst("Relocate Unit");

				
				
				
				
				relocate=new JComboBox(array.array);
				relocate.setPreferredSize(new Dimension(120,view.getInfo().getHeight()));
				relocate.addActionListener(this);
				relocate.setEnabled(false);
				
				initiate=new JButton("initate Army");
				initiate.setPreferredSize(new Dimension(100,view.getInfo().getHeight()));
				initiate.addActionListener(this);
				
				initiate.setEnabled(false);


				
				
				
				
				
				
				String unitType="";
				int i1=0;
				for(Unit a:army.getUnits()) {
					if(a instanceof Archer)
						unitType="<html>" + "Archers Level"+a.getLevel()+"<br>"+"Soldiers: "+a.getCurrentSoldierCount()+"<br>"+"Max soldier count: "+a.getMaxSoldierCount() + "</html>";
					if(a instanceof Infantry)
						unitType="<html>" + "Infantries Level"+a.getLevel()+"<br>"+"Soldiers: "+a.getCurrentSoldierCount()+"<br>"+"Max soldier count: "+a.getMaxSoldierCount()+ "</html>";
					if(a instanceof Cavalry)
						unitType="<html>"+"Cavalries Level"+a.getLevel()+"<br>"+"Soldiers: "+a.getCurrentSoldierCount()+"<br>"+"Max soldier count: "+a.getMaxSoldierCount()+ "</html>";
					
					JButton unit=new JButton(unitType);
					unit.addActionListener(this);
					unit.setActionCommand(i1+"");
					armyUnits.add(unit);
					i1++;
					
				}
				
				
				for(JButton b: armyUnits) {
					view.getUnits().add(b);
				}
				
				
				
				view.getInfo().add(playerInfo);
				view.getInfo().add(TargetCity);
				view.getInfo().add(CityView);
				view.getInfo().add(endTurn);
				view.getInfo().add(initiate);
				view.getInfo().add(relocate);
				
				view.revalidate();
				view.repaint();

				
		

	}
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		boolean flag=false;
		for(Army a:g.getPlayer().getControlledArmies()) {
			if(a.getCurrentStatus()==Status.MARCHING&&a.getDistancetoTarget()==1) {
				selectedArmy=a;
				flag=true;
			}
		}
	
		boolean flag1=false;
		for(City c:g.getAvailableCities()) {
			if(c.getTurnsUnderSiege()==2&&!g.getPlayer().getControlledCities().contains(c)) {
				selectedCity=c;
				flag1=true;
			}
			else if(!flag1)selectedCity=null;
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
			view.dispose();
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
				boolean flag11=true;
				while(flag11) {
				int option=JOptionPane.showInternalOptionDialog(null,"Choose Action","Choose Action on Army",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,f,0);
				if(option==0) {
					view.dispose();
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
							view.dispose();
							System.exit(0);

						}
						else if(g.getCurrentTurnCount()>g.getMaxTurnCount()) {		
							JOptionPane.showMessageDialog(null, "Loser!!!! ", "Game Over", JOptionPane.OK_OPTION);
							view.dispose();;	
							System.exit(0);

						}
						view.dispose();		
						if(!g.isGameOver()) {
						view.dispose();
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
				flag11=false;
				if(option==-1) {
					flag11=true;
				}
			}
			}
		}	
		if(e.getSource()==CityView) {
			view.dispose();
			new CityController(c, g);
			
		}
		if(e.getSource()==TargetCity) {
			String selectedTarget=(String)TargetCity.getSelectedItem();
			if(selectedTarget.equals("Cairo")) {
				view.dispose();
				g.targetCity(a, "Cairo");
				new ArmyController(g, a, c, armies);
			}
			if(selectedTarget.equals("Rome")) {
				view.dispose();
				g.targetCity(a, "Rome");
				new ArmyController(g, a, c, armies);
			}
			if(selectedTarget.equals("Sparta")) {
				view.dispose();
				g.targetCity(a, "Sparta");
				new ArmyController(g, a, c, armies);
			}
		}
		for(int i=0;i<a.getUnits().size();i++) {
			if(e.getActionCommand().equals(i+"")) {
				 selectedUnit=a.getUnits().get(i);
				 selectedButton=(JButton)(e.getSource());
				 System.out.println("hi");
				 relocate.setEnabled(true);
				// if(!c.getDefendingArmy().equals(a))
				initiate.setEnabled(true);
			}
			
		}
		if(e.getSource()==initiate&&selectedUnit!=null) {
			view.dispose();
			g.getPlayer().initiateArmy(c, selectedUnit);
			new CityController(c, g);
			System.out.println("enter");
		}
		
		
		
		if(e.getSource()==relocate&&selectedUnit!=null) {
			String selected=(String) relocate.getSelectedItem();
			for(Army a1:armies) {
				if(a1.getArmyName().equals(selected)) {
					try {
						a1.relocateUnit(selectedUnit);
						view.dispose();
						new CityController(c, g);
					} catch (MaxCapacityException e1) {
						JOptionPane.showMessageDialog(null, "unaccepted Action", "Max Capacity Reached", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
			
		}
	}

		
		
	public static void main(String[] args) throws IOException {
		Game g=new Game("mina", "Cairo");
		City c=new City("Cairo");
		c.getDefendingArmy().getUnits().add(new Infantry(2, 40, 50, 70, 10));
		c.getDefendingArmy().getUnits().add(new Infantry(2, 40, 50, 70, 10));
		c.getDefendingArmy().getUnits().add(new Infantry(2, 40, 50, 70, 10));
		c.getDefendingArmy().getUnits().add(new Infantry(2, 40, 50, 70, 10));
		c.getDefendingArmy().getUnits().add(new Infantry(2, 40, 50, 70, 10));
		c.getDefendingArmy().getUnits().add(new Infantry(2, 40, 50, 70, 10));
		//ArmyController a=new ArmyController(g, c.getDefendingArmy(), c);
	}


}

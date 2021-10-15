package GameController;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import engine.City;
import engine.Game;
import exceptions.FriendlyFireException;
import gameView.battleView;
import gameView.worldMapView;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Unit;

public class battleController implements ActionListener{
	battleView battle;
	JButton Attack;

	JTextArea playerInfo;
	JTextArea EA;
	JTextArea YA;
	ArrayList<JButton>myUnits;
	ArrayList<JButton>enemyUnits;
	Unit urSel;
	Unit enSel;
	City c;
	JButton world;
	JTextArea myUnitInfo;
	String myUnitInfoString;
	JTextArea enUnitInfo;
	String enUnitInfoString;
	JTextArea battleInfo;
	String battleInfoString;
	String newBattleInfoString;





	Game game;
	Army myArmy; 
	public battleController(Army a,Game g,String infoo) {
		game=g;
		myUnits= new ArrayList<JButton>();
		myArmy=a;
		enemyUnits= new ArrayList<JButton>();
		battle=new battleView();
		urSel=null;
		enSel=null;

		battleInfoString=infoo;
		newBattleInfoString="LAST ACTION OCCURED"+"\n"+"**********************"+"\n";
		
		
		playerInfo=new JTextArea();
		playerInfo.setText("Name:: "+game.getPlayer().getName()+"      "+"Gold:: "+game.getPlayer().getTreasury()+"      "+"Food:: "+game.getPlayer().getFood()+"      "+"TurnsCount:: "+game.getCurrentTurnCount());
		playerInfo.setFont(new Font("MV Boli",Font.ITALIC,25));
		playerInfo.setPreferredSize(new Dimension(battle.getPanel1().getWidth()-360,battle.getPanel1().getHeight()));
		playerInfo.setEditable(false);
		battle.getPanel1().add(playerInfo);
		
		myUnitInfo=new JTextArea();
		myUnitInfoString="your unit"+"\n";
		myUnitInfo.setText(myUnitInfoString);
		myUnitInfo.setFont(new Font("MV Boli",Font.ITALIC,25));
		myUnitInfo.setPreferredSize(new Dimension(400,battle.getPanel4().getHeight()));
		myUnitInfo.setEditable(false);
		battle.getPanel4().add(myUnitInfo);
		
		enUnitInfo=new JTextArea();
		enUnitInfoString="enemy unit"+"\n";
		enUnitInfo.setText(enUnitInfoString);
		enUnitInfo.setFont(new Font("MV Boli",Font.ITALIC,25));
		enUnitInfo.setPreferredSize(new Dimension(400,battle.getPanel4().getHeight()));
		enUnitInfo.setEditable(false);
		battle.getPanel4().add(enUnitInfo);
		
		Attack=new JButton("Attack");
		Attack.setEnabled(false);
		Attack.addActionListener(this);
		battle.getPanel4().add(Attack);


		battleInfo=new JTextArea();
		battleInfo.setText(battleInfoString);
		battleInfo.setFont(new Font("MV Boli",Font.ITALIC,25));
		battleInfo.setPreferredSize(new Dimension(1200,battle.getPanel4().getHeight()));
		battleInfo.setEditable(false);
		JScrollPane scroll1 = new JScrollPane(battleInfo);
		scroll1.setPreferredSize(new Dimension(700,battle.getPanel4().getHeight()-20));
		battle.getPanel4().add(scroll1);
		
		
		YA=new JTextArea();
		YA.setText("Your Army");
		YA.setFont(new Font("MV Boli",Font.ITALIC,25));
		YA.setPreferredSize(new Dimension(battle.getPanel1().getWidth(),battle.getPanel1().getHeight()));
		YA.setEditable(false);
		battle.getPanel2().add(YA);
		
		EA=new JTextArea();
		EA.setText("Enemy Army");
		EA.setFont(new Font("MV Boli",Font.ITALIC,25));
		EA.setPreferredSize(new Dimension(battle.getPanel1().getWidth(),battle.getPanel1().getHeight()));
	    EA.setEditable(false);
		battle.getPanel3().add(EA);
		

		
		
		
		
		//world=new JButton("world view");
		//world.addActionListener(this);
		//battle.getPanel1().add(world);
		//world.setEnabled(false);
		
		
		int i1=0;
		String unitType="";
		if(a!=null) {
		for(Unit u:a.getUnits()) {
			if(u instanceof Archer)
				unitType="<html>" + "Archers Level"+u.getLevel()+"<br>"+"Soldiers: "+u.getCurrentSoldierCount()+"<br>"+"Max soldier count: "+u.getMaxSoldierCount() + "</html>";
			if(u instanceof Infantry)
				unitType="<html>" + "Infantries Level"+u.getLevel()+"<br>"+"Soldiers: "+u.getCurrentSoldierCount()+"<br>"+"Max soldier count: "+u.getMaxSoldierCount()+ "</html>";
			if(u instanceof Cavalry)
				unitType="<html>"+"Cavalries Level"+u.getLevel()+"<br>"+"Soldiers: "+u.getCurrentSoldierCount()+"<br>"+"Max soldier count: "+u.getMaxSoldierCount()+ "</html>";
			
			JButton unit=new JButton(unitType);
			unit.addActionListener(this);
			unit.setActionCommand(i1+"");
			myUnits.add(unit);
			i1++;
			
		}
		

		String cityName=a.getCurrentLocation();
		for(City cc:g.getAvailableCities()) {
			if(cc.getName().equals(cityName))
				c=cc;
		}
	}
int i2=1000;
		for(Unit u:c.getDefendingArmy().getUnits()) {
			if(u instanceof Archer)
				unitType="<html>" + "Archers Level"+u.getLevel()+"<br>"+"Soldiers: "+u.getCurrentSoldierCount()+"<br>"+"Max soldier count: "+u.getMaxSoldierCount() + "</html>";
			if(u instanceof Infantry)
				unitType="<html>" + "Infantries Level"+u.getLevel()+"<br>"+"Soldiers: "+u.getCurrentSoldierCount()+"<br>"+"Max soldier count: "+u.getMaxSoldierCount()+ "</html>";
			if(u instanceof Cavalry)
				unitType="<html>"+"Cavalries Level"+u.getLevel()+"<br>"+"Soldiers: "+u.getCurrentSoldierCount()+"<br>"+"Max soldier count: "+u.getMaxSoldierCount()+ "</html>";
			
			JButton unit=new JButton(unitType);
			unit.addActionListener(this);
			unit.setActionCommand(i2+"");
			enemyUnits.add(unit);
			i2++;
			
		}
		
		
		for(JButton o:myUnits) {
			battle.getPanel2().add(o);
		}
		for(JButton o:enemyUnits) {
			battle.getPanel3().add(o);
		}
		
	
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		for(int i=0;i<myArmy.getUnits().size();i++) {
			String sss="";
			if(e.getActionCommand().equals(i+"")) {

				myUnitInfoString="Your Unit"+"\n";

				urSel=myArmy.getUnits().get(i);
				
				if(urSel instanceof Archer)
					sss="Archers Level"+urSel.getLevel()+"\n"+"Soldiers: "+urSel.getCurrentSoldierCount()+"\n"+"Max soldier count: "+urSel.getMaxSoldierCount() + "\n";
				if(urSel instanceof Infantry)
					sss="Infantries Level"+urSel.getLevel()+"\n"+"Soldiers: "+urSel.getCurrentSoldierCount()+"\n"+"Max soldier count: "+urSel.getMaxSoldierCount()+ "\n";
				if(urSel instanceof Cavalry)
					sss="Cavalries Level"+urSel.getLevel()+"\n"+"Soldiers: "+urSel.getCurrentSoldierCount()+"\n"+"Max soldier count: "+urSel.getMaxSoldierCount()+ "\n";
				myUnitInfoString+=sss;
				myUnitInfo.setText(myUnitInfoString);
			}
		}
int k=0;
		for(int j=1000;j<1000+(c.getDefendingArmy().getUnits().size());j++) {
			String rrr="";
			if(e.getActionCommand().equals(j+"")) {
				enUnitInfoString="Enemy Unit"+"\n";
				enSel=c.getDefendingArmy().getUnits().get(k);
				
				if(enSel instanceof Archer)
					rrr="Archers Level"+enSel.getLevel()+"\n"+"Soldiers: "+enSel.getCurrentSoldierCount()+"\n"+"Max soldier count: "+enSel.getMaxSoldierCount() + "\n";
				if(enSel instanceof Infantry)
					rrr="Infantries Level"+enSel.getLevel()+"\n"+"Soldiers: "+enSel.getCurrentSoldierCount()+"\n"+"Max soldier count: "+enSel.getMaxSoldierCount()+"\n";
				if(enSel instanceof Cavalry)
					rrr="Cavalries Level"+enSel.getLevel()+"\n"+"Soldiers: "+enSel.getCurrentSoldierCount()+"\n"+"Max soldier count: "+enSel.getMaxSoldierCount()+ "\n";
				enUnitInfoString+=rrr;
				enUnitInfo.setText(enUnitInfoString);
			}
			k++;

		}
		if(enSel!=null && urSel!=null) {
			Attack.setEnabled(true);
		}
		if(e.getSource()==Attack && enSel!=null && urSel!=null) {
			try {
				boolean EndGame=false;
				boolean EndBattle=false;
				String urAttacker="";
				String enDefender="";
				
				if(urSel instanceof Archer)
					urAttacker="Archer";
				if(urSel instanceof Infantry)
					urAttacker="Infantry";
				if(urSel instanceof Cavalry)
					urAttacker="Cavalry";
				
				if(enSel instanceof Archer)
					enDefender="Archer";
				if(enSel instanceof Infantry)
					enDefender="Infantry";
				if(enSel instanceof Cavalry)
					enDefender="Cavalry";
				
				
				newBattleInfoString+="Your Attacker::   "+urAttacker+"     Level::  "+urSel.getLevel() +"      Soldiers::  "+urSel.getCurrentSoldierCount()+"\n";
				
				newBattleInfoString+="enemy defender::   "+enDefender+"     Level::  "+enSel.getLevel() +"      Soldiers::  "+enSel.getCurrentSoldierCount()+"\n";



				int sold1=enSel.getCurrentSoldierCount();
				urSel.attack(enSel);
				int sold2=enSel.getCurrentSoldierCount();
				int dam=sold1-sold2;
				newBattleInfoString+="Damage:: "+dam+"\n";


				
				

				
				
			
				if(c.getDefendingArmy().getUnits().isEmpty()) {
					game.occupy(myArmy, c.getName());
					if(game.getPlayer().getControlledCities().size()==3) {
						JOptionPane.showMessageDialog(null, "Congrats Conqueror You won the game", "The Game", JOptionPane.OK_OPTION);
						System.exit(0);
						EndGame=true;
						EndBattle=true;

					}
					else {
						JOptionPane.showMessageDialog(null, "You occupied  "+c.getName(), "The Game", JOptionPane.OK_OPTION);
						EndBattle=true;
					}
				}
				if(EndGame) {
					battle.dispose();
					System.exit(0);
				}
				if(EndBattle&&!EndGame) {
					if(myArmy.getUnits().size()<=0)
						game.getPlayer().getControlledArmies().remove(myArmy);
					battle.dispose();
					new WorldMapController(game);
					return;}

				


				Unit unit1 = c.getDefendingArmy().getUnits().get((int) (Math.random() * c.getDefendingArmy().getUnits().size()));
				Unit unit2 = myArmy.getUnits().get((int) (Math.random() * myArmy.getUnits().size()));
				
				String enAttacker="";
				String urDefender="";
				
				

				if(unit1 instanceof Archer)
					enAttacker="Archer";
				if(unit1 instanceof Infantry)
					enAttacker="Infantry";
				if(unit1 instanceof Cavalry)
					enAttacker="Cavalry";
				
				if(unit2 instanceof Archer)
					urDefender="Archer";
				if(unit2 instanceof Infantry)
					urDefender="Infantry";
				if(unit2 instanceof Cavalry)
					urDefender="Cavalry";
				
				
				newBattleInfoString+="enemy Attacker::   "+enAttacker+"     Level::  "+unit1.getLevel() +"      Soldiers::  "+unit1.getCurrentSoldierCount()+"\n";
				
				newBattleInfoString+="your defender::   "+urDefender+"     Level::  "+unit2.getLevel() +"      Soldiers::  "+unit2.getCurrentSoldierCount()+"\n";



				int sold11=unit2.getCurrentSoldierCount();
				unit1.attack(unit2);
				int sold22=unit2.getCurrentSoldierCount();
				int dam2=sold11-sold22;
				newBattleInfoString+="Damage:: "+dam2+"\n";


				if(myArmy.getUnits().isEmpty()) {
					JOptionPane.showMessageDialog(null, "You lost the battle in  "+c.getName(), "The Game", JOptionPane.OK_OPTION);
					if(myArmy.getUnits().size()<=0)
						game.getPlayer().getControlledArmies().remove(myArmy);
					if(game.getCurrentTurnCount()>game.getMaxTurnCount()) {
						JOptionPane.showMessageDialog(null, "You lost the game","The Game", JOptionPane.OK_OPTION);
						EndGame=true;
					}
					EndBattle=true;
				}
				if(!EndGame) {
					if(!EndBattle) {
						battle.dispose();
						new battleController(myArmy, game,newBattleInfoString);}
					else {
						battle.dispose();
						new WorldMapController(game);
						}
				}
			



				
			} catch (FriendlyFireException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}
	}
}

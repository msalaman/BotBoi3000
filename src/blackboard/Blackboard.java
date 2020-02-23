package blackboard;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import bwapi.Unit;

public class Blackboard {
	private List<Unit> buildings;
	private List<Unit> workers;
	private List<Unit> barracks;
	private List<Unit> commandCenters;
	private Dictionary<String, List<Unit>> army = new Hashtable<String, List<Unit>>();
	private int crystals;
	private int gas;
	private List<Boolean> research;
	private List<Unit> enemyCommandCenters;
	private int enemyUnitCount;
	
    public Blackboard() {
    	List<Unit> marines = new ArrayList<>();
    	army.put("marines", marines);
    	List<Unit> firebats = new ArrayList<>();
    	army.put("firebars", firebats);
    	List<Unit> medics = new ArrayList<>();
    	army.put("medics", medics);
    	List<Unit> ghosts = new ArrayList<>();
    	army.put("ghosts", ghosts);
    	List<Unit> vultures = new ArrayList<>();
    	army.put("vultures", vultures);
    	List<Unit> siegeTanks = new ArrayList<>();
    	army.put("siegeTanks", siegeTanks);
    	List<Unit> goliaths = new ArrayList<>();
    	army.put("goliaths", goliaths);
    	List<Unit> wraiths = new ArrayList<>();
    	army.put("wraiths", wraiths);
    	List<Unit> dropships = new ArrayList<>();
    	army.put("dropships", dropships);
    	List<Unit> scienceVessels = new ArrayList<>();
    	army.put("scienceVessels", scienceVessels);
    	List<Unit> battleCruisers = new ArrayList<>();
    	army.put("battleCruisers", battleCruisers);
    	List<Unit> valkyries = new ArrayList<>();
    	army.put("valkyries", valkyries);
	 }
	
	public void setBuildings(List<Unit> b) {
		buildings = b;
	}
	
	public void addBuilding(Unit b) {
		buildings.add(b);
	}
	
	public void setWorkers(List<Unit> w) {
		workers = w;
	}
	
	public void addWorkers(Unit w) {
		workers.add(w);
	}
	
	public void setBarracks(List<Unit> b) {
		barracks = b;
	}
	
	public void addBarrack(Unit b) {
		barracks.add(b);
	}
	
	public void setCommandCenters(List<Unit> c) {
		commandCenters = c;
	}
	
	public void addCommandCenter(Unit c) {
		commandCenters.add(c);
	}
	
	public void addArmyUnits(String s, List<Unit> u) {
		army.put(s, u);
	}
	
	public void addArmyUnit(String s, Unit u) {
		List<Unit> temp = army.get(s);
		temp.add(u);
		addArmyUnits(s, temp);
	}
	
	public List<Unit> getArmyUnits(String s) {
		return army.get(s);
	}
	
	public void setCrystals(int c) {
		crystals = c;
	}
	
	public int getCrystals() {
		return crystals;
	}
	
	public void setGas(int g) {
		gas = g;
	}
	
	public int getGas() {
		return gas;
	}

	public List<Boolean> getResearch() {
		return research;
	}

	public void setResearch(List<Boolean> research) {
		this.research = research;
	}
	
	public void addResearch(int i, Boolean r) {
		research.remove(i);
		research.add(i, r);
	}

	public List<Unit> getEnemyCommandCenters() {
		return enemyCommandCenters;
	}

	public void setEnemyCommandCenters(List<Unit> enemyCommandCenters) {
		this.enemyCommandCenters = enemyCommandCenters;
	}
	
	public void addEnemyCommandCenter(Unit u) {
		enemyCommandCenters.add(u);
	}

	public int getEnemyUnitCount() {
		return enemyUnitCount;
	}

	public void setEnemyUnitCount(int enemyUnitCount) {
		this.enemyUnitCount = enemyUnitCount;
	}
}

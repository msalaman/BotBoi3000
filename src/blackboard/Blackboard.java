package blackboard;

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
	private Boolean EconTreeCompleted;
	private Boolean StrategyTreeCompleted;
	private Boolean ResearchTreeCompleted;
	
	
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

	public Boolean getEconTreeCompleted() {
		return EconTreeCompleted;
	}

	public void setEconTreeCompleted(Boolean econTreeCompleted) {
		EconTreeCompleted = econTreeCompleted;
	}

	public Boolean getStrategyTreeCompleted() {
		return StrategyTreeCompleted;
	}

	public void setStrategyTreeCompleted(Boolean strategyTreeCompleted) {
		StrategyTreeCompleted = strategyTreeCompleted;
	}

	public Boolean getResearchTreeCompleted() {
		return ResearchTreeCompleted;
	}

	public void setResearchTreeCompleted(Boolean researchTreeCompleted) {
		ResearchTreeCompleted = researchTreeCompleted;
	}
}

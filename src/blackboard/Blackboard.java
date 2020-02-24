package blackboard;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import bwapi.Race;
import bwapi.Unit;
import node.*;

public class Blackboard {
	private List<Unit> buildings;
	private List<Unit> workers;
	private List<Unit> barracks;
	private List<Unit> commandCenters;
	private Dictionary<String, List<Unit>> army = new Hashtable<String, List<Unit>>();
	private int minerals;
	private int gas;
	private int supplyUsed;
	private int supplyTotal;
	private List<Boolean> research;
	private List<Unit> enemyCommandCenters;
	private int enemyUnitCount;
    private Boolean EconTreeCompleted;
	private Boolean StrategyTreeCompleted;
	private Boolean ResearchTreeCompleted;
	private Race enemyRace;
	private Node stratPtr; 
	private Node econPtr;
	private Node stratRoot;
	private Node econRoot;
	
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
	
	public List<Unit> getBuildings() {
		return buildings;
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
	
	public int getMinerals() {
		return minerals;
	}
	
	public void setMinerals(int m) {
		minerals = m;
	}

	public int getGas() {
		return gas;
	}
	
	public void setGas(int g) {
		gas = g;
	}
	

	public int getSupplyUsed() {
		return supplyUsed;
	}

	public void setSupplyUsed(int supplyUsed) {
		this.supplyUsed = supplyUsed;
	}

	public int getSupplyTotal() {
		return supplyTotal;
	}

	public void setSupplyTotal(int supplyTotal) {
		this.supplyTotal = supplyTotal;
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

	public Race getEnemyRace() {
		return enemyRace;
	}

	public void setEnemyRace(Race enemyRace) {
		this.enemyRace = enemyRace;
	}
	public void setStratPtr(Node n) {
		this.stratPtr = n;
	}
	public void setEconPtr(Node n) {
		this.econPtr = n;
	}
	public Node getStratPtr() {
		return stratPtr;
	}
	public Node getEconPtr() {
		return econPtr;
	}
	public void setStratRoot(Node n) {
		this.stratRoot = n;
	}
	public void setEconRoot(Node n) {
		this.econRoot = n;
	}
	public Node getStratRoot() {
		return stratRoot;
	}
	public Node getEconRoot() {
		return econRoot;
	}
}

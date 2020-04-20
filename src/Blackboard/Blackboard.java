package Blackboard;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import Routine.*;
//import TestBot1.Strategy;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Player;
import bwapi.Position;
import bwapi.Race;
import bwapi.Unit;
import bwta.BaseLocation;

public class Blackboard {
	public Mirror mirror;
	public Game game;
	public Player self;
	public List<Unit> buildings = new ArrayList<Unit>();
	public List<Unit> workers = new ArrayList<Unit>();
	public List<Unit> barracks = new ArrayList<Unit>();
	public List<Unit> commandCenters = new ArrayList<Unit>();
	public List<Unit> supplyDepots = new ArrayList<Unit>();
	public List<Unit> academies = new ArrayList<Unit>();
	public List<Unit> refineries = new ArrayList<Unit>();
	public List<BaseLocation> baseLocations = new ArrayList<>();
	public List<BaseLocation> allLocations = new ArrayList<>();
	public Dictionary<String, List<Unit>> army;
	public int minerals;
	public int gas;
	public int supplyUsed;
	public int supplyTotal;
	public int searchingScv;
	public int searchingTimeout;
	public int patrolCount;
	public List<Boolean> research;
	public List<Unit> enemyCommandCenters = new ArrayList<Unit>();
	public Set<Position> enemyBuildingMemory = new HashSet<Position>();
	public Unit searcher;
	public Unit bunker;
	public int enemyUnitCount;
    public Boolean EconTreeCompleted;
	public Boolean StrategyTreeCompleted;
	public Boolean ResearchTreeCompleted;
	public Race enemyRace;
	public Position workerAttacked;
	public Routine stratPtr; 
	public Routine econPtr;
	public Routine stratRoot;
	public Routine econRoot;
	public int Academy;
	public enum Strategy {
		WaitFor50, AttackAtAllCost
	};
	public Strategy selectedStrategy;
	
	
	public Blackboard() {
		Academy = 0;
		army = new Hashtable<String, List<Unit>>();
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
	public Strategy getStrategy(String strat) {
		return Strategy.valueOf(strat);
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
	
	public void setSupplyDepots(List<Unit> s) {
		supplyDepots = s;
	}
	
	public void addSupplyDepots(Unit s) {
		supplyDepots.add(s);
	}
	
	public void setRefineries(List<Unit> r) {
		refineries = r;
	}
	
	public void addRefineries(Unit r) {
		refineries.add(r);
	}
	
	public void setAcademies(List<Unit> a) {
		academies = a;
	}
	
	public void addSupplyAcademy(Unit a) {
		academies.add(a);
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
	public void setStratPtr(Routine n) {
		this.stratPtr = n;
	}
	public void setEconPtr(Routine n) {
		this.econPtr = n;
	}
	public Routine getStratPtr() {
		return stratPtr;
	}
	public Routine getEconPtr() {
		return econPtr;
	}
	public void setStratRoot(Routine n) {
		this.stratRoot = n;
	}
	public void setEconRoot(Routine n) {
		this.econRoot = n;
	}
	public Routine getStratRoot() {
		return stratRoot;
	}
	public Routine getEconRoot() {
		return econRoot;
	}

}

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import Blackboard.*;
import Routine.*;
import bwapi.*;
import bwta.BWTA;
import bwta.BaseLocation;

public class TestBot1 extends DefaultBWListener {

	public Mirror mirror = new Mirror();

	public Game game;

	public Player self;
	public Blackboard blackboard;
	private int frameskip = 0;
	private int cyclesForSearching = 0;
	private int maxCyclesForSearching = 0;
	private int enemies = 0;
	private boolean dontBuild = false;
	private int timeout = 0;
	Unit bunkerBuilder;

	private String debugText = "";


	private Set<Position> enemyBuildingMemory = new HashSet<>();
	
	
	Repeater econRoot;
	Repeater stratRoot;

	

	public void run() {
		mirror.getModule().setEventListener(this);
		mirror.startGame();
	}

	@Override
	public void onUnitCreate(Unit unit) {
		
	}

	@Override
	public void onStart() {
		frameskip = 0;
		cyclesForSearching = 0;
		maxCyclesForSearching = 0;
		enemies = 0;
		dontBuild = false;
		timeout = 0;
		bunkerBuilder = null;
		Unit commandCenter = null;

		game = mirror.getGame();
		self = game.self();
		game.setLocalSpeed(0);
		
		for (Unit myUnit : self.getUnits()) {
			if (myUnit.getType() == UnitType.Terran_Command_Center) {
				commandCenter = myUnit;
			}
		}
		
		/*** Initialize Blackboard information ***/
		blackboard = new Blackboard();
		blackboard.mirror = mirror;
		blackboard.self = self;
		blackboard.game = game;
		blackboard.addCommandCenter(commandCenter);
		blackboard.setEnemyRace(self.getRace());
		blackboard.searcher = null;
		blackboard.searchingScv = 0;
		blackboard.searchingTimeout = 0;
		blackboard.selectedStrategy = blackboard.getStrategy("WaitFor50");
		List<Unit> enemyCommandCenters = new ArrayList<>();
		blackboard.setEnemyCommandCenters(enemyCommandCenters);
		// Use BWTA to analyze map
		// This may take a few minutes if the map is processed first time!

		BWTA.readMap();
		BWTA.analyze();

		int i = 0;
		
		econRoot = new Repeater(new EconRoot());
		econRoot.start();
		stratRoot = new Repeater(new StratRoot());
		stratRoot.start();
		
	}

	@Override
	public void onFrame() {
		// game.setTextSize(10);
		game.drawTextScreen(10, 10, "Playing as " + self.getName() + " - " + self.getRace());
		game.drawTextScreen(10, 20, "Units: " + self.getUnits().size() + "; Enemies: " + enemyBuildingMemory.size());
		game.drawTextScreen(10, 30,
				"Cycles for buildings: " + cyclesForSearching + "; Max cycles: " + maxCyclesForSearching);
		game.drawTextScreen(10, 40, "Elapsed time: " + game.elapsedTime() + "; Strategy: " + blackboard.selectedStrategy);
		game.drawTextScreen(10, 50, debugText);
		game.drawTextScreen(10, 60, "supply: " + self.supplyTotal() + " used: " + self.supplyUsed());
		game.drawTextScreen(140, 60, "enemyCommandCenters Count: " + blackboard.enemyCommandCenters.size());
		if(blackboard != null) {
			game.drawTextScreen(10, 70, "There is a blackboard");
			if(blackboard.self != null) {
				game.drawTextScreen(10, 80, "There is a blackboard.self");
			}
		}
		
		if (maxCyclesForSearching > 300000) {
			dontBuild = true;
		}

		game.setLocalSpeed(0);

		if (maxCyclesForSearching < cyclesForSearching) {
			maxCyclesForSearching = cyclesForSearching;
		}
		cyclesForSearching = 0;

		StringBuilder units = new StringBuilder("My units:\n");
		List<Unit> workers = new ArrayList<>();
		List<Unit> barracks = new ArrayList<>();
		List<Unit> supplyDepots = new ArrayList<>();
		List<Unit> academies = new ArrayList<>();
		List<Unit> refineries = new ArrayList<>();
		List<Unit> buildings  = new ArrayList<>();
		Unit commandCenter = null;
		List<Unit> marines = new ArrayList<>();
		List<Unit> firebats = new ArrayList<>();
		List<Unit> medics = new ArrayList<>();
		List<Unit> ghosts = new ArrayList<>();
		List<Unit> vultures = new ArrayList<>();
		List<Unit> siegeTanks = new ArrayList<>();
		List<Unit> goliaths = new ArrayList<>();
		List<Unit> wraiths = new ArrayList<>();
		List<Unit> dropships = new ArrayList<>();
		List<Unit> scienceVessels = new ArrayList<>();
		List<Unit> battleCruisers = new ArrayList<>();
		List<Unit> valkyries = new ArrayList<>();
		List<BaseLocation> baseLocations = new ArrayList<>();
		List<BaseLocation> allLocations = new ArrayList<>();
		blackboard.bunker = null;
		blackboard.workerAttacked = null;
		

		if (bunkerBuilder != null && bunkerBuilder.exists() == false) {
			bunkerBuilder = null;
		}

		if (blackboard.searcher != null && blackboard.searcher.exists() == false) {
			blackboard.searcher = null;
		}

		if (blackboard.searcher != null) {
			game.drawTextMap(blackboard.searcher.getPosition(), "Mr. Searcher");
		}

		// iterate through my units
		for (Unit myUnit : self.getUnits()) {
			// units.append(myUnit.getType()).append("
			// ").append(myUnit.getTilePosition()).append("\n");

			if (myUnit.getType().isWorker()) {
				workers.add(myUnit);
			}

			else if (myUnit.getType() == UnitType.Terran_Command_Center) {
				buildings.add(myUnit);
				commandCenter = myUnit;
			}

			else if (myUnit.getType() == UnitType.Terran_Barracks && myUnit.isBeingConstructed() == false) {
				buildings.add(myUnit);
				barracks.add(myUnit);
			}

			else if (myUnit.getType() == UnitType.Terran_Marine) {
				marines.add(myUnit);
			}
			else if (myUnit.getType() == UnitType.Terran_Firebat) {
				firebats.add(myUnit);
			}
			else if (myUnit.getType() == UnitType.Terran_Medic) {
				medics.add(myUnit);
			}
			else if (myUnit.getType() == UnitType.Terran_Ghost) {
				ghosts.add(myUnit);
			}
			else if (myUnit.getType() == UnitType.Terran_Vulture) {
				vultures.add(myUnit);
			}
			else if (myUnit.getType() == UnitType.Terran_Siege_Tank_Siege_Mode) {
				siegeTanks.add(myUnit);
			}
			else if (myUnit.getType() == UnitType.Terran_Goliath) {
				goliaths.add(myUnit);
			}
			else if (myUnit.getType() == UnitType.Terran_Wraith) {
				wraiths.add(myUnit);
			}
			else if (myUnit.getType() == UnitType.Terran_Dropship) {
				dropships.add(myUnit);
			}
			else if (myUnit.getType() == UnitType.Terran_Science_Vessel) {
				scienceVessels.add(myUnit);
			}
			else if (myUnit.getType() == UnitType.Terran_Battlecruiser) {
				battleCruisers.add(myUnit);
			}
			else if (myUnit.getType() == UnitType.Terran_Valkyrie) {
				valkyries.add(myUnit);
			}

			else if (myUnit.getType() == UnitType.Terran_Bunker && myUnit.isBeingConstructed() == false) {
				buildings.add(myUnit);
				blackboard.bunker = myUnit;
			}
			
			else if(myUnit.getType() == UnitType.Terran_Supply_Depot) {
				buildings.add(myUnit);
				supplyDepots.add(myUnit);
			}
			
			else if(myUnit.getType() == UnitType.Terran_Academy) {
				buildings.add(myUnit);
				academies.add(myUnit);
			}
			else if(myUnit.getType() == UnitType.Terran_Refinery) {
				buildings.add(myUnit);
				refineries.add(myUnit);
			}
			
			if (myUnit.isUnderAttack() && myUnit.canAttack()) {
				game.setLocalSpeed(1);
				myUnit.attack(myUnit.getPosition());
			}

		}
		blackboard.setBuildings(buildings);
		blackboard.setBarracks(barracks);
		blackboard.setWorkers(workers);
		blackboard.addArmyUnits("marines", marines);
		blackboard.addArmyUnits("firebats", firebats);
		blackboard.addArmyUnits("medics", medics);
		blackboard.addArmyUnits("ghosts", ghosts);
		blackboard.addArmyUnits("vultures", vultures);
		blackboard.addArmyUnits("siegeTanks", siegeTanks);
		blackboard.addArmyUnits("goliaths", goliaths);
		blackboard.addArmyUnits("wraiths", wraiths);
		blackboard.addArmyUnits("dropships", dropships);
		blackboard.addArmyUnits("scienceVessels", scienceVessels);
		blackboard.addArmyUnits("battleCruisers", battleCruisers);
		blackboard.setGas(self.gas());
		blackboard.setMinerals(self.minerals());
		blackboard.setSupplyUsed(self.supplyUsed());
		blackboard.setSupplyTotal(self.supplyTotal());
		blackboard.setEconTreeCompleted(false);
		blackboard.setStrategyTreeCompleted(false);
		blackboard.setResearchTreeCompleted(false);
		blackboard.setSupplyDepots(supplyDepots);
		blackboard.setAcademies(academies);
		blackboard.setRefineries(refineries);
		
		if (econRoot.isRunning()) {
			game.drawTextScreen(10, 100, "econRoot is currently Running");
			econRoot.act(blackboard);
		}
		else if (econRoot.isFailure()) {
			game.drawTextScreen(10, 100, "econRoot has Failed");
			econRoot.start();
		}
		else if (econRoot.isSuccess()) {
			game.drawTextScreen(10, 100, "econRoot has Succeeded.");
			econRoot.start();
		}
		else {
			game.drawTextScreen(10, 100, "econRoot has no state");
			econRoot.start();
		}
		if (stratRoot.isRunning()) {
			game.drawTextScreen(75, 100, "stratRoot is currently Running");
			stratRoot.act(blackboard);
		}
		else if (econRoot.isFailure()) {
			game.drawTextScreen(75, 100, "stratRoot has Failed");
			stratRoot.start();
		}
		else if (econRoot.isSuccess()) {
			game.drawTextScreen(75, 100, "stratRoot has Succeeded.");
			stratRoot.start();
		}
		else {
			game.drawTextScreen(75, 100, "stratRoot has no state");
			stratRoot.start();
		}
		
		for (Unit myUnit : workers) {
			// if it's a worker and it's idle, send it to the closest mineral
			// patch
			if (myUnit.getType().isWorker() && myUnit.isIdle()) {
				boolean skip = false;
				Unit closestMineral = null;

				// find the closest mineral
				for (Unit neutralUnit : game.neutral().getUnits()) {
					if (neutralUnit.getType().isMineralField()) {
						if (closestMineral == null
								|| myUnit.getDistance(neutralUnit) < myUnit.getDistance(closestMineral)) {
							closestMineral = neutralUnit;
						}
					}
				}

				// if a mineral patch was found, send the worker to gather it
				if (closestMineral != null) {
					if (skip == false) {
						myUnit.gather(closestMineral, false);
					}
				}
			}
			
			if (myUnit.isUnderAttack() && myUnit.canAttack()) {
				game.setLocalSpeed(1);
				myUnit.attack(myUnit.getPosition());
			}
			
			if (myUnit.isUnderAttack() && myUnit.isGatheringMinerals()){
				blackboard.workerAttacked = myUnit.getPosition();
			}
		}

		frameskip++;
		if (frameskip == 20) {
			frameskip = 0;
		}

		if (frameskip != 0) {
			return;
		}

		blackboard.searchingTimeout++;

		for (BaseLocation b : BWTA.getBaseLocations()) {
			// If this is a possible start location,
			if (b.isStartLocation()) {
				blackboard.baseLocations.add(b);
			}

			blackboard.allLocations.add(b);
		}
		
		debugText = "Size: " + workers.size() + "; isGathering" + workers.get(7).isGatheringMinerals() + "; location: "
				+ blackboard.baseLocations.size() + "; num: " + blackboard.searchingScv;
		int enemyUnitCount = 0;
		for (Unit u : game.enemy().getUnits()) {
			enemyUnitCount++;
			// if this unit is in fact a building
			if (u.getType().isBuilding()) {
				// check if we have it's position in memory and add it if we
				// don't
				if(u.getType() == UnitType.Terran_Command_Center || u.getType() == UnitType.Zerg_Infested_Command_Center || u.getType() == UnitType.Protoss_Nexus) {
					if(blackboard.enemyCommandCenters.size() == 0) {
						blackboard.addEnemyCommandCenter(u);
					} else {
						for(Unit e : blackboard.enemyCommandCenters) {
							if(e.getID() != u.getID()) {
								blackboard.addEnemyCommandCenter(u);
							}
						}
					}
				}
				if (!enemyBuildingMemory.contains(u.getPosition()))
					enemyBuildingMemory.add(u.getPosition());
			}
		}
		blackboard.enemyBuildingMemory = enemyBuildingMemory;
		blackboard.setEnemyUnitCount(enemyUnitCount);

		// loop over all the positions that we remember
		for (Position p : enemyBuildingMemory) {
			// compute the TilePosition corresponding to our remembered Position
			// p
			TilePosition tileCorrespondingToP = new TilePosition(p.getX() / 32, p.getY() / 32);

			// if that tile is currently visible to us...
			if (game.isVisible(tileCorrespondingToP)) {

				// loop over all the visible enemy buildings and find out if at
				// least
				// one of them is still at that remembered position
				boolean buildingStillThere = false;
				for (Unit u : game.enemy().getUnits()) {
					if ((u.getType().isBuilding()) && (u.getPosition() == p)) {
						buildingStillThere = true;
						break;
					}
				}

				// if there is no more any building, remove that position from
				// our memory
				if (buildingStillThere == false) {
					enemyBuildingMemory.remove(p);
					break;
				}
			}
		}

		// draw my units on screen
		// game.drawTextScreen(10, 25, units.toString());
	}

	public static void main(String[] args) {
		new TestBot1().run();
	}

	// Returns a suitable TilePosition to build a given building type near
	// specified TilePosition aroundTile, or null if not found. (builder
	// parameter is our worker)
	public TilePosition getBuildTile(Unit builder, UnitType buildingType, TilePosition aroundTile) {
		TilePosition ret = null;
		int maxDist = 3;
		int stopDist = 40;

		// Refinery, Assimilator, Extractor
		if (buildingType.isRefinery()) {
			for (Unit n : game.neutral().getUnits()) {
				cyclesForSearching++;
				if ((n.getType() == UnitType.Resource_Vespene_Geyser)
						&& (Math.abs(n.getTilePosition().getX() - aroundTile.getX()) < stopDist)
						&& (Math.abs(n.getTilePosition().getY() - aroundTile.getY()) < stopDist))
					return n.getTilePosition();
			}
		}

		while ((maxDist < stopDist) && (ret == null)) {
			for (int i = aroundTile.getX() - maxDist; i <= aroundTile.getX() + maxDist; i++) {
				for (int j = aroundTile.getY() - maxDist; j <= aroundTile.getY() + maxDist; j++) {
					if (game.canBuildHere(new TilePosition(i, j), buildingType, builder, false)) {
						// units that are blocking the tile
						boolean unitsInWay = false;
						for (Unit u : game.getAllUnits()) {
							cyclesForSearching++;
							if (u.getID() == builder.getID())
								continue;
							if ((Math.abs(u.getTilePosition().getX() - i) < 4)
									&& (Math.abs(u.getTilePosition().getY() - j) < 4))
								unitsInWay = true;
						}
						if (!unitsInWay) {
							cyclesForSearching++;
							return new TilePosition(i, j);
						}
						// creep for Zerg
						if (buildingType.requiresCreep()) {
							boolean creepMissing = false;
							for (int k = i; k <= i + buildingType.tileWidth(); k++) {
								for (int l = j; l <= j + buildingType.tileHeight(); l++) {
									cyclesForSearching++;
									if (!game.hasCreep(k, l))
										creepMissing = true;
									break;
								}
							}
							if (creepMissing)
								continue;
						}
					}
				}
			}
			maxDist += 2;
		}

		if (ret == null)
			game.printf("Unable to find suitable build position for " + buildingType.toString());
		return ret;
	}
}

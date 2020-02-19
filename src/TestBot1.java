import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import bwapi.*;
import bwta.BWTA;
import bwta.BaseLocation;
import java.util.*;

//if this comment shows, it means git is working through eclipse

public class TestBot1 extends DefaultBWListener {

	private Mirror mirror = new Mirror();

	private Game game;

	private Player self;

	private int [][] mapH;
	private int mapHeight;
	private int mapWidth;

	private int frameskip = 0;
	private int cyclesForSearching = 0;
	private int maxCyclesForSearching = 0;
	private int enemies = 0;
	private int searchingScv = 0;
	private int searchingTimeout = 0;
	private boolean dontBuild = false;
	private int timeout = 0;
	Unit bunkerBuilder;
	Unit searcher;

	private String debugText = "";

	private enum Strategy {
		WaitFor50, AttackAtAllCost
	};

	private Strategy selectedStrategy = Strategy.WaitFor50;

	private Set<Position> enemyBuildingMemory = new HashSet<>();

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
		searchingScv = 0;
		searchingTimeout = 0;
		dontBuild = false;
		timeout = 0;
		bunkerBuilder = null;
		searcher = null;

		game = mirror.getGame();
		self = game.self();
		game.setLocalSpeed(0);

		// Use BWTA to analyze map
		// This may take a few minutes if the map is processed first time!

		BWTA.readMap();
		BWTA.analyze();

		mapHeight = game.mapHeight();
		mapWidth = game.mapWidth();
		mapH = new int [mapHeight*4][mapWidth*4];
		
		for (int i = 0; i < mapHeight*4; i++) {
			for (int j = 0; i < mapWidth*4; j++) {
				if (!game.isWalkable(i, j)) {
					mapH[i][j] = 1000;
				}
				else {
					mapH[i][j] = 1;
				}
			}
		}

		int i = 0;
	}

	@Override
	public void onFrame() {
		// game.setTextSize(10);
		game.drawTextScreen(10, 10, "Playing as " + self.getName() + " - " + self.getRace());
		game.drawTextScreen(10, 20, "Units: " + self.getUnits().size() + "; Enemies: " + enemyBuildingMemory.size());
		game.drawTextScreen(10, 30,
				"Cycles for buildings: " + cyclesForSearching + "; Max cycles: " + maxCyclesForSearching);
		game.drawTextScreen(10, 40, "Elapsed time: " + game.elapsedTime() + "; Strategy: " + selectedStrategy);
		game.drawTextScreen(10, 50, debugText);
		game.drawTextScreen(10, 60, "supply: " + self.supplyTotal() + " used: " + self.supplyUsed());
		/*
		 * if (game.elapsedTime() > 2001) { int x = (game.elapsedTime() / 500) %
		 * 2; if (x == 0) { selectedStrategy = Strategy.FindEnemy; } else {
		 * selectedStrategy = Strategy.HugeAttack; } }
		 */

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
		Unit commandCenter = null;
		List<Unit> marines = new ArrayList<>();
		List<BaseLocation> baseLocations = new ArrayList<>();
		List<BaseLocation> allLocations = new ArrayList<>();
		Unit bunker = null;
		Position workerAttacked = null;


		if (bunkerBuilder != null && bunkerBuilder.exists() == false) {
			bunkerBuilder = null;
		}

		if (searcher != null && searcher.exists() == false) {
			searcher = null;
		}

		if (searcher != null) {
			game.drawTextMap(searcher.getPosition(), "Mr. Searcher");
		}

		// iterate through my units
		for (Unit myUnit : self.getUnits()) {
			// units.append(myUnit.getType()).append("
			// ").append(myUnit.getTilePosition()).append("\n");

			if (myUnit.getType().isWorker()) {
				workers.add(myUnit);
			}

			// if there's enough minerals, train an SCV
			if (myUnit.getType() == UnitType.Terran_Command_Center) {
				commandCenter = myUnit;
			}

			if (myUnit.getType() == UnitType.Terran_Barracks && myUnit.isBeingConstructed() == false) {
				barracks.add(myUnit);
			}

			if (myUnit.getType() == UnitType.Terran_Marine) {
				marines.add(myUnit);
			}

			if (myUnit.getType() == UnitType.Terran_Bunker && myUnit.isBeingConstructed() == false) {
				bunker = myUnit;
			}

			if (myUnit.isUnderAttack() && myUnit.canAttack()) {
				game.setLocalSpeed(1);
				myUnit.attack(myUnit.getPosition());
			}

		}

		for (Unit myUnit : workers) {
			// if it's a worker and it's idle, send it to the closest mineral
			// patch
			
			if (myUnit.getType().isWorker() && myUnit.isIdle()) {
				boolean skip = false;
				if (bunker == null && bunkerBuilder != null && myUnit.equals(bunkerBuilder)
						&& barracks.isEmpty() == false) {
					game.drawTextScreen(10, 140, "I've just set skip to true for buker related reasons");
					skip = true;
				}

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
					
					game.drawTextScreen(10, 120, "found a closest mineral field");
					
					Position startingPos = myUnit.getPosition();
					Position targetPos = closestMineral.getPosition();
					
					WalkPosition startingWalk = new WalkPosition(startingPos.getX()/8,startingPos.getY()/8);
					WalkPosition targetWalk = new WalkPosition(targetPos.getX()/8,targetPos.getY()/8);
					
					game.drawTextScreen(10, 190, "startingWalk: " + startingWalk.getX() + ", " + startingWalk.getY());
					game.drawTextScreen(10, 200, "targetWalk: " + targetWalk.getX() + ", " + targetWalk.getY());
					
					int [][] mapHCopy = new int [mapHeight*4][mapWidth*4];
					for (int i = 0; i < mapHeight*4; i++) {
						for (int j = 0; j < mapWidth*4; j++) {
							mapHCopy[i][j] = 1000;
						}
					}
					game.drawTextScreen(350, 120, "past that for loop?");
					
					int [][] mapHVisited = new int [mapHeight*4][mapWidth*4];
					// treat mapHCopy as the node values, and mapH as the edge weight. Update mapHcopy as needed, but leave mapH as is.
					// mapHVisited is used to mark which locations, or "nodes" are visited.
					
					// 1 is the lowest value, 100 is the highest value a risk can take (a unit is ranked by its risk), and 1000 is the 
					// highest value and represents "impassable" squares
					
					mapHVisited[startingWalk.getX()][startingWalk.getY()] = 1;
					mapHCopy[startingWalk.getX()][startingWalk.getY()] = 1;
					int currX = startingWalk.getX();
					int currY = startingWalk.getY();
					int targetX = targetWalk.getX();
					int targetY = targetWalk.getY();
					int breakingPoint = 1000; //change depending on results
					int maxDistance = 0;
					int foundDistance = 0;
					game.drawTextScreen(10, 100, "past the first set of variable declarations");
					//map coordinates to score and parent
					//Map<List<int>, List<List<int, int>, int> possiblePath = new HashMap<List<int>, List<int>>();
					//Map<List<int>, List<List<int, int>, int> chosenPath = new HashMap<List<int>, List<int>>();
					//List<int> startPoint = {currX, currY};
					//List<int> startValue = {0, 
					//possiblePath.put(startPoint);
					//x, all y values
					HashMap<Integer, List<Integer>> chosenPath = new HashMap<>();
					List<Integer> possibleYs = new ArrayList<>();
					game.drawTextScreen(10, 110, "past the Hashmap and list declarations");
					possibleYs.add(currY);
					chosenPath.put(currX, possibleYs);
					game.drawTextScreen(250, 110, "past the adding and putting");
					int NmapHeight = 4 * game.mapHeight();
					int NmapWidth  = 4 * game.mapWidth();
					int counter = 0;
					
					game.drawTextScreen(10, 210, "currX and Y: " + currX + ", " + currY);
					game.drawTextScreen(10, 220, "targetX and Y: " + targetX + ", " + targetY);
					
					game.drawTextScreen(10, 230, "Value of dis<brk = " + (foundDistance < breakingPoint) + " Value of currXY vs tarXY = " + (currX != targetX || currY != targetY));
					
					while(foundDistance < breakingPoint && (currX != targetX || currY != targetY)) {
						//right
						game.drawTextScreen(10, 160, "We've entered the while loop");
						
						//check to see if the target is within 1 sqare of our current location, leave the loop if found
						if (Math.abs(currX-targetX)<=1 && Math.abs(currY-targetY)<=1 ) {
							game.drawTextScreen(10, 70, "found a good path");
							break;
						}
						
						if(NmapWidth > currX+1) {
							//right check
							maxDistance = Math.max(Math.abs((currX+1) - targetX), Math.abs(currY - targetY));
							if (mapHCopy[currX+1][currY] > maxDistance + mapH[currX+1][currY]) {
								mapHCopy[currX+1][currY] = maxDistance + mapH[currX+1][currY];
							}
							//bottom right check
							if(currY+1<NmapHeight) {
								maxDistance = Math.max(Math.abs((currX+1) - targetX), Math.abs((currY+1) - targetY));
								if (mapHCopy[currX+1][currY+1] > maxDistance + mapH[currX+1][currY+1]) {
									mapHCopy[currX+1][currY+1] = maxDistance + mapH[currX+1][currY+1];
								}
							}
							//upper right check
							if(currY-1>=0) {
								maxDistance = Math.max(Math.abs((currX+1) - targetX), Math.abs((currY-1) - targetY));
								if (mapHCopy[currX+1][currY-1] > maxDistance + mapH[currX+1][currY-1]) {
									mapHCopy[currX+1][currY-1] = maxDistance + mapH[currX+1][currY-1];
								}
							}
						}
						//left
						if(currX-1 >= 0) {
							//left check
							maxDistance = Math.max(Math.abs((currX-1) - targetX), Math.abs(currY - targetY));
							if (mapHCopy[currX-1][currY] > maxDistance + mapH[currX-1][currY]) {
								mapHCopy[currX-1][currY] = maxDistance + mapH[currX-1][currY];
							}
							//bottom left check
							if(currY+1<NmapHeight) {
								maxDistance = Math.max(Math.abs((currX-1) - targetX), Math.abs((currY+1) - targetY));
								if (mapHCopy[currX-1][currY+1] > maxDistance + mapH[currX-1][currY+1]) {
									mapHCopy[currX-1][currY+1] = maxDistance + mapH[currX-1][currY+1];
								}
							}
							//upper left check
							if(currY-1>=0) {
								maxDistance = Math.max(Math.abs((currX-1) - targetX), Math.abs((currY-1) - targetY));
								if (mapHCopy[currX-1][currY-1] > maxDistance + mapH[currX-1][currY-1]) {
									mapHCopy[currX-1][currY-1] = maxDistance + mapH[currX-1][currY-1];
								}
							}
						}
						//lower check
						if(currY+1<NmapHeight) {
							maxDistance = Math.max(Math.abs((currX) - targetX), Math.abs((currY+1) - targetY));
							if (mapHCopy[currX][currY+1] > maxDistance + mapH[currX][currY+1]) {
								mapHCopy[currX][currY+1] = maxDistance + mapH[currX][currY+1];
							}
						}
						//upper check
						if(currY-1>=0) {
							maxDistance = Math.max(Math.abs((currX) - targetX), Math.abs((currY-1) - targetY));
							if (mapHCopy[currX][currY-1] > maxDistance + mapH[currX][currY-1]) {
								mapHCopy[currX][currY-1] = maxDistance + mapH[currX][currY-1];
							}
						}
						int leftEdge = currX-1;
						if(leftEdge <0) leftEdge = 0;
						int upperEdge = currY-1;
						if(upperEdge <0) upperEdge = 0;
						int rightEdge = currX+1;
						if(rightEdge <0) rightEdge = NmapWidth;
						int lowerEdge = currY+1;
						if(lowerEdge <0) lowerEdge = NmapHeight;
						int tempX = currX;
						int tempY = currY;
						int tempScore = 100000000;
						for(int i=leftEdge; i<=rightEdge;i++) {
							for(int j=upperEdge; j<=lowerEdge;j++) {
								if(i==currX && j==currY) continue;
								if(!chosenPath.containsKey(i) || (chosenPath.containsKey(i) && !chosenPath.get(i).contains(j))) {
									if(mapHCopy[i][j]<tempScore) {
										tempX = i;
										tempY = j;
										tempScore = mapHCopy[i][j];
									}
								}
							}
						}
						//game.drawTextScreen(10, 150, "We are still in the while loop" + counter);
						//check if currX and currY are already tempX and tempY, in which case no path was found.
						if (currX==tempX) {
							game.drawTextScreen(10, 70, "Couldn't find a good path to resource");
							//game.drawTextMap(myUnit.getPosition(), "Couldn't find a good path to resource ");
							skip = true;
							break;
						}
						else {
							currX=tempX;
							currY=tempY;		
						}
						foundDistance += tempScore;
						if(chosenPath.containsKey(currX)) {
							chosenPath.get(currX).add(currY);
						} else {
							List<Integer> newY = new ArrayList<>();
							chosenPath.put(currX, newY);
						}
						if(foundDistance > breakingPoint) {
							game.drawTextScreen(250, 70, "Passed the breakingPoint, won't gather resource");
							//game.drawTextMap(myUnit.getPosition(), "Passed the breakingPoint, won't gather resource ");
							skip = true;
							break;
						}
						
					}
					
					game.drawTextScreen(10, 250, "About the enter the skip check where skip = " + skip);
					if (skip == false) {
						game.drawTextScreen(200, 250, "Gonna tell it to gather the resource");
						myUnit.gather(closestMineral, false);
					}
				}
			}

			if (myUnit.isUnderAttack() && myUnit.canAttack()) {
				game.setLocalSpeed(1);
				myUnit.attack(myUnit.getPosition());
			}

			if (myUnit.isUnderAttack() && myUnit.isGatheringMinerals()){
				workerAttacked = myUnit.getPosition();
			}
		}

		if (bunkerBuilder == null && workers.size() > 10) {
			bunkerBuilder = workers.get(10);
		}

		if (bunker == null && barracks.size() >= 1 && workers.size() > 10 && dontBuild == false) {
			game.setLocalSpeed(20);

			if (timeout < 200) {
				game.drawTextMap(bunkerBuilder.getPosition(), "Moving to create bunker " + timeout + "/400");
				bunkerBuilder.move(BWTA.getNearestChokepoint(bunkerBuilder.getPosition()).getCenter());
				timeout++;
			} else {
				game.drawTextMap(bunkerBuilder.getPosition(), "Buiding bunker");
				TilePosition buildTile = getBuildTile(bunkerBuilder, UnitType.Terran_Barracks,
						bunkerBuilder.getTilePosition());
				if (buildTile != null) {
					bunkerBuilder.build(UnitType.Terran_Bunker, buildTile);
				}
			}
		} else if (workers.size() > 10) {
			game.setLocalSpeed(10);
			game.drawTextMap(workers.get(10).getPosition(), "He will build bunker");
		}

		if (bunker != null && bunkerBuilder != null && bunkerBuilder.isRepairing() == false) {
			game.drawTextMap(bunkerBuilder.getPosition(), "Reparing bunker");
			bunkerBuilder.repair(bunker);
		}

		if (commandCenter.getTrainingQueue().isEmpty() && workers.size() < 20 && self.minerals() >= 50) {
			commandCenter.build(UnitType.AllUnits.Terran_SCV);
		}

		frameskip++;
		if (frameskip == 20) {
			frameskip = 0;
		}

		if (frameskip != 0) {
			return;
		}

		searchingTimeout++;

		int i = 1;
		for (Unit worker : workers) {
			if (worker.isGatheringMinerals() && dontBuild == false) {
				if (self.minerals() >= 150 * i && barracks.size() < 6) {
					TilePosition buildTile = getBuildTile(worker, UnitType.Terran_Barracks, self.getStartLocation());
					if (buildTile != null) {
						worker.build(UnitType.Terran_Barracks, buildTile);
					}
				}

				if (self.minerals() >= i * 100 && self.supplyUsed() + (self.supplyUsed() / 3) >= self.supplyTotal()
						&& self.supplyTotal() < 400) {
					TilePosition buildTile = getBuildTile(worker, UnitType.Terran_Supply_Depot,
							self.getStartLocation());
					// and, if found, send the worker to build it (and leave
					// others
					// alone - break;)
					if (buildTile != null) {
						worker.build(UnitType.Terran_Supply_Depot, buildTile);
					}
				}
			}

			i++;
		}

		for (Unit barrack : barracks) {
			if (barrack.getTrainingQueue().isEmpty()) {
				barrack.build(UnitType.AllUnits.Terran_Marine);
			}
		}

		for (BaseLocation b : BWTA.getBaseLocations()) {
			// If this is a possible start location,
			if (b.isStartLocation()) {
				baseLocations.add(b);
			}

			allLocations.add(b);
		}

		Random random = new Random();
		int k = 0;
		for (Unit marine : marines) {
			if (marine.isAttacking() == false && marine.isMoving() == false) {
				if (marines.size() > 50 || selectedStrategy == Strategy.AttackAtAllCost) {
					if (marines.size() > 40) {
						selectedStrategy = Strategy.AttackAtAllCost;
					} else {
						selectedStrategy = Strategy.WaitFor50;
					}
					if (enemyBuildingMemory.isEmpty()) {
						marine.attack(allLocations.get(k % allLocations.size()).getPosition());
					} else {
						for (Position p : enemyBuildingMemory) {
							marine.attack(p);
						}
					}

					if (marines.size() > 70) {
						if (k < allLocations.size()) {
							marine.attack(allLocations.get(k).getPosition());
						}
					}
				} else {
					Position newPos;

					if (bunker != null) {
						List<TilePosition> path = BWTA.getShortestPath(bunker.getTilePosition(),
								BWTA.getStartLocation(game.self()).getTilePosition());

						if (path.size() > 1) {
							newPos = path.get(1).toPosition();
						} else {
							newPos = BWTA.getNearestChokepoint(marine.getPosition()).getCenter();
						}
					} else {
						newPos = BWTA.getNearestChokepoint(marine.getPosition()).getCenter();
					}

					marine.attack(newPos);
				}
			}
			k++;

			if (bunker != null && bunker.getLoadedUnits().size() < 4 && k < 5) {
				marine.load(bunker);
			}

			if (workerAttacked != null){
				marine.attack(workerAttacked);
			}
		}

		if (workers.size() > 7 && searcher == null) {
			searcher = workers.get(7);
		}

		if (searcher != null && searcher.isGatheringMinerals() && searchingScv < baseLocations.size()
				&& searchingTimeout % 10 == 0) {
			searcher.move(baseLocations.get(searchingScv).getPosition());
			searchingScv++;
		}

		debugText = "Size: " + workers.size() + "; isGathering" + workers.get(7).isGatheringMinerals() + "; location: "
				+ baseLocations.size() + "; num: " + searchingScv;

		for (Unit u : game.enemy().getUnits()) {
			// if this unit is in fact a building
			if (u.getType().isBuilding()) {
				// check if we have it's position in memory and add it if we
				// don't
				if (!enemyBuildingMemory.contains(u.getPosition()))
					enemyBuildingMemory.add(u.getPosition());
			}
			if (u.getType().canAttack()) {
				Position pTemp = u.getPosition();
				int wTileTempX = pTemp.getX()/8;
				int wTileTempY = pTemp.getY()/8;
				for (i=wTileTempX-2; i<wTileTempX+3; i++) {
					for (int j=wTileTempY; j<wTileTempY+3; j++) {
						if (i<mapWidth*4 && i>=0 && j<mapHeight*4 && j>=0) {
							mapH[i][j] = 500; // make this set the area of a few tiles around it as 30
						}
					}
				}
			}
		}

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

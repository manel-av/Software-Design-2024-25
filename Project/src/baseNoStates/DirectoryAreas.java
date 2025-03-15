package baseNoStates;

import baseNoStates.area.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// DirectoryAreas class manages a hierarchy of areas in a building, organizing spaces and partitions
// and providing access to all doors and areas.
public class DirectoryAreas {
  private static ArrayList<Door> allDoors;
  private static Area rootArea;
  private static DirectoryAreas uniqueInstance = null;
  private static final Logger milestone1Logger = LoggerFactory.getLogger("Milestone1");
  private static final Logger milestone2Logger = LoggerFactory.getLogger("Milestone2");

  private DirectoryAreas() {
    makeAreas();
  }

  public static synchronized DirectoryAreas getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new DirectoryAreas();
      milestone2Logger.info("DirectoryAreas instance correctly instantiated.");
    } else {
      milestone2Logger.warn("Can't create an instance. A DirectoryAreas instance already exists.");
    }
    return uniqueInstance;
  }

  // Initializes the structure of the building, creating spaces, partitions, and doors.
  private void makeAreas() {
    // Create partitions
    Partition building = new Partition("building", null);
    Partition basement = new Partition("basement", building);
    Partition groundFloor = new Partition("ground_floor", building);
    Partition floor1 = new Partition("floor1", building);
    milestone1Logger.info("Building partitions created.");

    // Create individual spaces
    Space stairs = new Space("stairs", building);
    Space exterior = new Space("exterior", building);
    Space parking = new Space("parking", basement);
    Space hall = new Space("hall", groundFloor);
    Space room1 = new Space("room1", groundFloor);
    Space room2 = new Space("room2", groundFloor);
    Space room3 = new Space("room3", floor1);
    Space corridor = new Space("corridor", floor1);
    Space it = new Space("IT", floor1);
    milestone1Logger.info("Building spaces created.");

    // Create doors to connect different spaces.
    final Door d1 = new Door("D1", exterior, parking);
    final Door d2 = new Door("D2", stairs, parking);
    final Door d3 = new Door("D3", exterior, hall);
    final Door d4 = new Door("D4", stairs, hall);
    final Door d5 = new Door("D5", hall, room1);
    final Door d6 = new Door("D6", hall, room2);
    final Door d7 = new Door("D7", stairs, corridor);
    final Door d8 = new Door("D8", corridor, room3);
    final Door d9 = new Door("D9", corridor, it);
    milestone1Logger.info("Building doors created.");

    // Add doors to their respective spaces.
    exterior.addDoor(d1); // exterior -> parking
    parking.addDoor(d1);  // parking -> exterior

    stairs.addDoor(d2);   // stairs -> parking
    parking.addDoor(d2);  // parking -> stairs

    exterior.addDoor(d3); // exterior -> hall
    hall.addDoor(d3);     // hall -> exterior

    stairs.addDoor(d4);   // stairs -> hall
    hall.addDoor(d4);     // hall -> stairs

    hall.addDoor(d5);     // hall -> room1
    room1.addDoor(d5);    // room1 -> hall

    hall.addDoor(d6);     // hall -> room2
    room2.addDoor(d6);    // room2 -> hall

    stairs.addDoor(d7);   // stairs -> corridor
    corridor.addDoor(d7); // corridor -> stairs

    corridor.addDoor(d8); // corridor -> room3
    room3.addDoor(d8);    // room3 -> corridor

    corridor.addDoor(d9); // corridor -> IT
    it.addDoor(d9);       // IT -> corridor
    milestone1Logger.info("Doors associated to spaces.");

    // Add spaces to the corresponding partitions.
    basement.addArea(parking);
    groundFloor.addArea(hall);
    groundFloor.addArea(room1);
    groundFloor.addArea(room2);
    floor1.addArea(room3);
    floor1.addArea(corridor);
    floor1.addArea(it);

    // Add partitions and other spaces to the root building.
    building.addArea(basement);
    building.addArea(groundFloor);
    building.addArea(floor1);
    building.addArea(stairs);
    building.addArea(exterior);
    milestone1Logger.info("Spaces and partitions added to building.");

    // Store all doors and set the root area to represent the building.
    allDoors = new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9));
    rootArea = building;
  }

  // Finds an area by its unique identifier (ID) in the entire building structure.
  public static Area findAreaById(String id) {
    if (id.equals("ROOT")) {
      // Special id that means that the wanted area is the root.
      // This is because the Flutter app client doesn't know the
      // id of the root, differently from the simulator
      return rootArea;
    } else {
      //...
      AreaFinderVisitor finder = new AreaFinderVisitor(id);
      milestone2Logger.debug("Area visitor created");
      rootArea.accept(finder);
      milestone2Logger.debug("Area visitor (finder) accepted");
      return finder.getFoundArea();
    }
  }

  // Finds a door by its unique identifier (ID) from the list of all doors.
  public static Door findDoorById(String id) {
    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        return door;
      }
    }
    return null;
  }

  // Returns the list of all doors in the building.
  public static ArrayList<Door> getAllDoors() {
    return allDoors;
  }
}
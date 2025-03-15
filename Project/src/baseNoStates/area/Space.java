package baseNoStates.area;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// Represents a physical space within the building (e.g., a room or corridor). It is a "leaf"
// in the Composite pattern, as it contains no nested areas but can have doors that connect
// it to other spaces.
public class Space extends Area {
  private final ArrayList<Door> doors;

  public Space(String id, Area root) {
    super(id, root);
    this.doors = new ArrayList<>();
  }

  // Method which adds a door to this space
  public void addDoor(Door door) {
    this.doors.add(door);
  }

  // Implementation of method that allows visitor interaction
  @Override
  public void accept(AreaVisitor visitor) {
    visitor.visit(this); // Allows visitor operations in this space
    for (Door door : doors) {
      door.accept(visitor); // Operation delegation to the connected doors
    }
  }

  public JSONObject toJson(int depth) { // depth not used here
    JSONObject json = new JSONObject();
    json.put("class", "space");
    json.put("id", id);
    JSONArray jsonDoors = new JSONArray();
    for (Door d : doors) {
      jsonDoors.put(d.toJson());
    }
    json.put("access_doors", jsonDoors);
    return json;
  }
}
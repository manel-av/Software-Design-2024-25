package baseNoStates.area;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// Represents a collection of areas organized as a partition within the building's hierarchy.
// Implements the Composite pattern, enabling hierarchical structures where partitions
// contain other areas.
public class Partition extends Area {
  private final ArrayList<Area> areas;

  public Partition(String id, Area root) {
    super(id, root);
    this.areas = new ArrayList<>();
  }

  // Method that allows visitor entrance
  @Override
  public void accept(AreaVisitor visitor) {
    visitor.visit(this);
    // Recursive visits of all areas on this partition
    for (Area area : areas) {
      if (area instanceof Visitable) {
        ((Visitable) area).accept(visitor);
      }
    }
  }

  // Method that adds new areas to this partition
  public void addArea(Area area) {
    this.areas.add(area);
  }

  public JSONObject toJson(int depth) {
    // for depth=1 only the root and children,
    // for recusive = all levels use Integer.MAX_VALUE
    JSONObject json = new JSONObject();
    json.put("class", "partition");
    json.put("id", id);
    JSONArray jsonAreas = new JSONArray();
    if (depth > 0) {
      for (Area a : areas) {
        jsonAreas.put(a.toJson(depth - 1));
      }
      json.put("areas", jsonAreas);
    }
    return json;
  }
}

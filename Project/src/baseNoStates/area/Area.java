package baseNoStates.area;

import org.json.JSONObject;

// Abstract class representing a general area in the building, which could be a space or a
// partition. This class serves as a base in the Composite pattern, allowing `Partition` and
// `Space` to be treated as `Area` instances within a hierarchical structure.
public abstract class Area implements Visitable {
  protected final String id;
  protected final Area rootArea;

  public Area(String id, Area root) {
    this.id = id;
    this.rootArea = root;
  }

  public String getId() {
    return id;
  }

  // Method to allow the entrance of a visitor.
  public abstract void accept(AreaVisitor visitor);

  // Returns the doors that give access to this area.
  //public abstract Door[] getDoorsGivingAccess();

  // Searches for an area by its identifier.
  //public abstract Area findAreaById(String id);

  // Returns the spaces contained within this area.
  // public abstract Space[] getSpaces();

  public abstract JSONObject toJson(int depth);
}
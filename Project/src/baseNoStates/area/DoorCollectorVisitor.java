package baseNoStates.area;

import java.util.HashSet;
import java.util.Set;

// Visitor implementation for collecting all doors in an area structure.
// Operates on doors and recursively collects from nested areas.
public class DoorCollectorVisitor implements AreaVisitor {
  private final Set<Door> doors = new HashSet<>();

  public Set<Door> getDoors() {
    return doors; // Return the unique doors collected.
  }

  @Override
  public void visit(Partition partition) {
    // No direct action for partitions; they delegate traversal to their sub-areas.
  }

  @Override
  public void visit(Space space) {
    // No direct action for spaces; they delegate traversal to their doors.
  }

  @Override
  public void visit(Door door) {
    // Add the door to the set. Duplicates will be ignored.
    doors.add(door);
  }
}

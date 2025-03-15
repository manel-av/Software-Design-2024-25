package baseNoStates.area;

import java.util.ArrayList;
import java.util.List;

// Visitor implementation for collecting all spaces in an area structure.
// Operates on spaces but skips doors.
public class SpaceCollectorVisitor implements AreaVisitor {
  private final List<Space> spaces = new ArrayList<>();

  public List<Space> getSpaces() {
    return spaces; // Returns the list of collected spaces.
  }

  @Override
  public void visit(Partition partition) {
    // No direct action for partitions; they delegate traversal to their sub-areas.
  }

  @Override
  public void visit(Space space) {
    // Adds the space to the collected list.
    spaces.add(space);
  }

  @Override
  public void visit(Door door) {
    // Doors are ignored in this use case.
  }
}
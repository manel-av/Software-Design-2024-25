package baseNoStates.area;

// Visitor implementation for finding an area by its ID.
// Operates on Partitions and Spaces but skips Doors.
public class AreaFinderVisitor implements AreaVisitor {
  private final String targetId;
  private Area foundArea;

  // Initializes with the target ID.
  public AreaFinderVisitor(String targetId) {
    this.targetId = targetId;
  }

  // Returns the area found during the visit.
  public Area getFoundArea() {
    return foundArea;
  }

  @Override
  public void visit(Partition partition) {
    // Checks if the partition's ID matches the target ID.
    if (partition.getId().equals(targetId)) {
      foundArea = partition;
    }
  }

  @Override
  public void visit(Space space) {
    // Checks if the space's ID matches the target ID.
    if (space.getId().equals(targetId)) {
      foundArea = space;
    }
  }

  @Override
  public void visit(Door door) {
    // Doors are not searched by ID in this use case.
  }
}
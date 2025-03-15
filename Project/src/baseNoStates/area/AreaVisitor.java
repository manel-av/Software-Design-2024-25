package baseNoStates.area;

// Interface for a visitor that defines operations for different area types.
// Part of the Visitor design pattern.
public interface AreaVisitor {
  void visit(Partition partition); // Operation for Partition elements.

  void visit(Space space);         // Operation for Space elements.

  void visit(Door door);           // Operation for Door elements.
}

package baseNoStates.area;

// Interface representing a visitable element in the area structure.
// Used in the Visitor design pattern to allow operations on different types of areas.
public interface Visitable {
  // Accepts a visitor to perform operations based on the element type.
  void accept(AreaVisitor visitor);
}

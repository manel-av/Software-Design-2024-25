package baseNoStates.states;

import baseNoStates.area.Door;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// DoorStates defines the state of a door.
// It acts as the base class in the State design pattern, which allows dynamic
// switching between different door states
public abstract class DoorStates {
  protected final Door door;
  protected static final Logger milestone1Logger = LoggerFactory.getLogger("Milestone1");

  public DoorStates(Door door) {
    this.door = door;
  }

  // These methods are the actions that can be made to a door.
  public abstract void lock();

  public abstract void unlock();

  public abstract void open();

  public abstract void close();

  public abstract void unlockShortly();

  public abstract String getStateName();

  public abstract boolean isClosed();
}

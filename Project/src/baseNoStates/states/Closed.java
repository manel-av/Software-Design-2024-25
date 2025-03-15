package baseNoStates.states;

import baseNoStates.area.Door;

public class Closed extends DoorStates {
  public Closed(Door door) {
    super(door);
  }

  @Override
  public void lock() {
    door.setState(new Locked(door));  // Transition to the locked state.
    milestone1Logger.info("Door {} is now locked.", door.getId());
  }

  @Override
  public void unlock() {
    milestone1Logger.warn("Door {} is already unlocked.", door.getId());
  }

  @Override
  public void open() {
    door.setState(new Opened(door));  // Transition to the opened state.
    milestone1Logger.info("Door {} is open.", door.getId());
  }

  @Override
  public void close() {
    milestone1Logger.warn("Door {} is already closed.", door.getId());
  }

  @Override
  public void unlockShortly() {
    milestone1Logger.warn("Door {} is not locked.", door.getId());
  }

  @Override
  public String getStateName() {
    return "unlocked";
  }

  @Override
  public boolean isClosed() {
    return true;
  }
}

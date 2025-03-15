package baseNoStates.states;

import baseNoStates.area.Door;

public class Opened extends DoorStates {
  public Opened(Door door) {
    super(door);
  }

  @Override
  public void lock() {
    milestone1Logger.warn("Door {} can't be locked.", door.getId());
  }

  @Override
  public void unlock() {
    milestone1Logger.warn("Door {} is already unlocked.", door.getId());
  }

  @Override
  public void open() {
    milestone1Logger.warn("Door {} is already opened.", door.getId());
  }

  @Override
  public void close() {
    door.setState(new Closed(door));  // Transition to the closed state.
    milestone1Logger.info("Door {} is now closed.", door.getId());
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
    return false;
  }
}
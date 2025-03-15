package baseNoStates.states;

import baseNoStates.area.Door;

public class Propped extends DoorStates {
  public Propped(Door door) {
    super(door);
  }

  @Override
  public void lock() {
    door.setState(new Locked(door));  // Transition to the locked state.
    milestone1Logger.info("Door {} is now locked.", door.getId());
  }

  @Override
  public void unlock() {
    milestone1Logger.warn("Door {} was already unlocked.", door.getId());
  }

  @Override
  public void open() {
    milestone1Logger.warn("Door {} is already opened.", door.getId());
  }

  @Override
  public void close() {
    door.setState(new Locked(door));  // Transition to the Locked state.
    milestone1Logger.info("Door {} is now closed.", door.getId());
  }

  @Override
  public void unlockShortly() {
    milestone1Logger.warn("Door {} is already unlocked.", door.getId());
  }

  @Override
  public String getStateName() {
    return "propped";
  }

  @Override
  public boolean isClosed() {
    return false;
  }
}
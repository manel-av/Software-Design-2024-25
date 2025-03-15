package baseNoStates.states;

import baseNoStates.area.Door;

public class Locked extends DoorStates {
  public Locked(Door door) {
    super(door);
  }

  @Override
  public void lock() {
    milestone1Logger.warn("Door {} is already locked.", door.getId());
  }

  @Override
  public void unlock() {
    door.setState(new Closed(door));  // Transition to the closed state when unlocked.
    milestone1Logger.info("Door {} is now unlocked.", door.getId());
  }

  @Override
  public void open() {
    milestone1Logger.warn("Door {} can't be opened.", door.getId());
  }

  @Override
  public void close() {
    milestone1Logger.warn("Door {} is already closed.", door.getId());
  }

  @Override
  public void unlockShortly() {
    door.setState(new UnlockedShortly(door));  // Transition to the "unlocked shortly" state.
    milestone1Logger.info("Door {} is temporarily unlocked for a short period.", door.getId());
  }

  @Override
  public String getStateName() {
    return "locked";
  }

  @Override
  public boolean isClosed() {
    return true;
  }
}
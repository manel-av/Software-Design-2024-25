package baseNoStates.states;

import baseNoStates.area.Door;
import java.util.Timer;
import java.util.TimerTask;

public class UnlockedShortly extends DoorStates {
  private boolean doorRemainsOpen = false;

  public UnlockedShortly(Door door) {
    super(door);
    startUnlockShortlyTimer(); // Start the timer when the state is set to UnlockedShortly
  }

  public boolean getDoorTemporalState() {
    return doorRemainsOpen;
  }

  private void startUnlockShortlyTimer() {
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        checkDoorState(); // Check door state after 10 seconds
      }
    }, 10000);  // 10 seconds delay
  }

  private void checkDoorState() {
    if (!doorRemainsOpen) {
      door.setState(new Locked(door));
      milestone1Logger.info("Door {} is now locked after 10 seconds.", door.getId());
    } else {
      door.setState(new Propped(door));
      milestone1Logger.warn("Door {} is now propped open after 10 seconds.", door.getId());
    }
  }

  @Override
  public void lock() {
    door.setState(new Locked(door));  // Transition to the locked state immediately.
    milestone1Logger.info("Door {} is now locked.", door.getId());
  }

  @Override
  public void unlock() {
    milestone1Logger.warn("Door {} was already unlocked.", door.getId());
  }

  @Override
  public void open() {
    this.doorRemainsOpen = true;
    milestone1Logger.info("Door {} is now open.", door.getId());
  }

  @Override
  public void close() {
    this.doorRemainsOpen = false;
    milestone1Logger.info("Door {} is now closed.", door.getId());
  }

  @Override
  public void unlockShortly() {
    milestone1Logger.warn("Door {} is already unlocked.", door.getId());
  }

  @Override
  public String getStateName() {
    return "unlocked_shortly";
  }

  @Override
  public boolean isClosed() {
    return true;
  }
}
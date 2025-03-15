package baseNoStates.area;

import baseNoStates.Actions;
import baseNoStates.requests.RequestReader;
import baseNoStates.states.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Represents a door that connects two areas and can have multiple states (e.g., open, closed,
// locked). The Door class uses the State pattern to change its behavior depending
// on its current state.
public class Door implements Visitable {
  private final String id;
  private DoorStates doorState;
  private final Area fromArea;
  private final Area toArea;
  private static final Logger milestone1Logger = LoggerFactory.getLogger("Milestone1");
  private static final Logger milestone2Logger = LoggerFactory.getLogger("Milestone2");

  // Initializes a door with a unique ID and the areas it connects.
  // The door is initially in a Closed state.
  public Door(String id, Area fromArea, Area toArea) {
    this.id = id;
    this.doorState = new Closed(this);
    this.fromArea = fromArea;
    this.toArea = toArea;
  }

  // Returns the unique identifier of the door.
  public String getId() {
    return id;
  }

  // Returns the name of the current state (e.g., "closed", "locked").
  public String getStateName() {
    return this.getState().getStateName();
  }

  // Returns the current state of the door as a DoorStates object.
  public DoorStates getState() {
    return this.doorState;
  }

  // Changes the door's state to a new state object.
  public void setState(DoorStates state) {
    this.doorState = state;
  }

  public Area getInitialLocation() {
    return this.fromArea;
  }

  public Area getFinalLocation() {
    return this.toArea;
  }

  // Processes a request to perform an action on the door (e.g., open, close, lock).
  // It checks if the request is authorized and performs the corresponding action.
  public void processRequest(RequestReader request) {
    if (request.isAuthorized()) {
      String action = request.getAction();
      milestone2Logger.debug("Action to perform checked");
      doAction(action);  // Executes the requested action on the door.
    } else {
      milestone1Logger.warn("not authorized");
    }
    request.setDoorStateName(getStateName());  // Updates the request with the door's state.
  }

  public void accept(AreaVisitor visitor) {
    visitor.visit(this);
  }


  // Handles actions on the door based on its current state and the requested action.
  // State Pattern: Delegates action to the current state.
  private void doAction(String action) {
    String stateName = this.getStateName();
    DoorStates state = this.getState();
    Area initLocation = getInitialLocation();
    Area finalLocation = getFinalLocation();

    milestone1Logger.info("Performing action '{}' on door {}, located between locations: {} and {}",
            action, this.getId(), initLocation.getId(), finalLocation.getId());

    switch (action) {
      case Actions.OPEN:
        if (isClosed() && !stateName.equals("locked")) {
          state.open();
        } else {
          milestone1Logger.warn("Can't open door {}", id);
        }
        break;

      case Actions.CLOSE:
        if (state instanceof UnlockedShortly) {
          if (((UnlockedShortly) state).getDoorTemporalState()) {
            state.close();
          } else {
            milestone1Logger.warn("Can't close door {} because it's already closed", id);
          }
        } else if (!isClosed()) {
          state.close();
        } else {
          milestone1Logger.warn("Can't close door {}", id);
        }
        break;

      case Actions.LOCK:
        if (isClosed()) {
          state.lock();
        } else {
          milestone1Logger.warn("Can't lock door {}", id);
        }
        break;

      case Actions.UNLOCK:
        if (isClosed()) {
          state.unlock();
        } else {
          milestone1Logger.warn("Can't unlock door {}", id);
        }
        break;

      case Actions.UNLOCK_SHORTLY:
        if (stateName.equals("locked")) {
          state.unlockShortly();
        } else {
          milestone1Logger.warn("Can't shortly unlock door {} because it's not locked", id);
        }
        break;

      default:
        assert false : "Unknown action " + action;
        System.exit(-1);
    }
  }

  // Checks if the door is closed.
  public boolean isClosed() {
    return this.getState().isClosed();
  }

  // Provides a string representation of the door's ID and state for debugging purposes.
  @Override
  public String toString() {
    return "Door{"
            + ", id='" + id + '\''
            + ", closed=" + isClosed()
            + ", state=" + getStateName()
            + "}";
  }

  // Converts the door's information to a JSON object.
  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", id);
    json.put("state", getStateName());
    json.put("closed", isClosed());
    return json;
  }
}
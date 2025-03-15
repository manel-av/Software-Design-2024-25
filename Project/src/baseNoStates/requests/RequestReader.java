package baseNoStates.requests;

import baseNoStates.DirectoryAreas;
import baseNoStates.DirectoryUsersGroup;
import baseNoStates.area.Door;
import baseNoStates.users.Schedule;
import baseNoStates.users.User;
import baseNoStates.users.UserGroup;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestReader implements Request {
  private final String credential; // who
  private final String action;     // what
  private final LocalDateTime now; // when
  private final String doorId;     // where
  private String userName;
  private boolean authorized;
  private final ArrayList<String> reasons; // why not authorized
  private String doorStateName;
  private boolean doorClosed;
  private static final Logger milestone2Logger = LoggerFactory.getLogger("Milestone2");

  public RequestReader(String credential, String action, LocalDateTime now, String doorId) {
    this.credential = credential;
    this.action = action;
    this.doorId = doorId;
    reasons = new ArrayList<>();
    this.now = now;
  }

  public void setDoorStateName(String name) {
    doorStateName = name;
  }

  public String getAction() {
    return action;
  }

  public boolean isAuthorized() {
    return authorized;
  }

  public void addReason(String reason) {
    reasons.add(reason);
  }


  @Override
  public String toString() {
    if (userName == null) {
      userName = "unknown";
    }
    return "Request{"
            + "credential=" + credential
            + ", userName=" + userName
            + ", action=" + action
            + ", now=" + now
            + ", doorID=" + doorId
            + ", closed=" + doorClosed
            + ", authorized=" + authorized
            + ", reasons=" + reasons
            + "}";
  }

  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("authorized", authorized);
    json.put("action", action);
    json.put("doorId", doorId);
    json.put("closed", doorClosed);
    json.put("state", doorStateName);
    json.put("reasons", new JSONArray(reasons));
    return json;
  }

  // see if the request is authorized and put this into the request, then send it to the door.
  // if authorized, perform the action.
  public void process() {
    User user = DirectoryUsersGroup.findUserByCredential(credential);
    Door door = DirectoryAreas.findDoorById(doorId);
    assert door != null : "door " + doorId + " not found";
    authorize(user, door);
    milestone2Logger.debug("User authorization checked");
    // this sets the boolean authorize attribute of the request
    door.processRequest(this);
    milestone2Logger.debug("Process request done");
    // even if not authorized we process the request, so that if desired we could log all
    // the requests made to the server as part of processing the request
    doorClosed = door.isClosed();
  }

  // the result is put into the request object plus, if not authorized, why not,
  // only for testing
  private void authorize(User user, Door door) {
    if (user == null) {
      authorized = false;
      addReason("user doesn't exist");
    } else {
      UserGroup userGroup = DirectoryUsersGroup.findGroupForUser(user);

      if (userGroup != null) {
        // Checks if the action is permitted
        String[] allowedActions = userGroup.getPermittedActions();
        boolean actionAllowed = Arrays.stream(allowedActions)
                .anyMatch(a -> a.equalsIgnoreCase(action));

        // Checks if the action can be performed on this schedule
        Schedule schedule = userGroup.getSchedule();
        boolean withinSchedule = false;
        if (schedule != null) {
          withinSchedule = schedule.isWithinSchedule(now.toLocalDate(), now.toLocalTime());
        }

        // Checks if the user trying to access parking is an employee
        boolean isParking = door.getId().equals("D1")
                && userGroup.getGroupName().equals("Employees");

        if (!actionAllowed) {
          authorized = false;
          addReason("Action not allowed for this user group");
        } else if (!withinSchedule) {
          authorized = false;
          addReason("Access not allowed at this time");
        } else if (isParking) {
          authorized = false;
          addReason("Employees are not allowed in the parking");
        } else {
          authorized = true;
        }
      } else {
        authorized = false;
        addReason("user group not found");
      }
    }
  }
}


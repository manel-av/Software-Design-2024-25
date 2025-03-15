package baseNoStates.requests;

import baseNoStates.Actions;
import baseNoStates.DirectoryAreas;
import baseNoStates.area.Area;
import baseNoStates.area.Door;
import baseNoStates.area.DoorCollectorVisitor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestArea implements Request {
  private final String credential;
  private final String action;
  private final String areaId;
  private final LocalDateTime now;
  private final ArrayList<RequestReader> requests = new ArrayList<>();
  private static final Logger milestone1Logger = LoggerFactory.getLogger("Milestone1");
  private static final Logger milestone2Logger = LoggerFactory.getLogger("Milestone2");

  public RequestArea(String credential, String action, LocalDateTime now, String areaId) {
    this.credential = credential;
    this.areaId = areaId;
    assert action.equals(Actions.LOCK) || action.equals(Actions.UNLOCK)
            : "invalid action " + action + " for an area request";
    this.action = action;
    this.now = now;
  }

  public String getAction() {
    return action;
  }

  @Override
  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("action", action);
    json.put("areaId", areaId);
    JSONArray jsonRequests = new JSONArray();
    for (RequestReader rd : requests) {
      jsonRequests.put(rd.answerToJson());
    }
    json.put("requestsDoors", jsonRequests);
    json.put("requestArea", jsonRequests);
    return json;
  }

  @Override
  public String toString() {
    String requestsDoorsStr;
    if (requests.isEmpty()) {
      requestsDoorsStr = "";
    } else {
      requestsDoorsStr = requests.toString();
    }
    return "Request{"
            + "credential=" + credential
            + ", action=" + action
            + ", now=" + now
            + ", areaId=" + areaId
            + ", requestsDoors=" + requestsDoorsStr
            + "}";
  }

  public void process() {
    // Find the area by its ID.
    Area area = DirectoryAreas.findAreaById(areaId);
    if (area != null) {
      // Use a DoorCollectorVisitor to collect all doors in the area.
      DoorCollectorVisitor doorCollector = new DoorCollectorVisitor();
      milestone2Logger.debug("DoorCollectorVisitor created");
      area.accept(doorCollector); // Tree search using visitor pattern
      milestone2Logger.debug("doorCollector (visitor) accepted");
      Set<Door> doors = doorCollector.getDoors();

      // Process each door to create and execute individual requests.
      milestone1Logger.info("List of doors: {}", doors);
      for (Door door : doors) {
        RequestReader requestReader = new RequestReader(credential, getAction(), now, door.getId());
        requestReader.process();
        requests.add(requestReader);
      }
    }
  }
}

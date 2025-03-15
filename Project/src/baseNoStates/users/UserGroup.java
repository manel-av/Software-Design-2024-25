package baseNoStates.users;

import java.util.ArrayList;

// UserGroup class stores information related to a user group, including users, spaces,
// actions, and schedules.
public class UserGroup {
  private final String groupName;
  private final ArrayList<User> users;
  private final String[] accessibleSpaces;
  private final String[] permittedActions;
  private final Schedule schedule;

  public UserGroup(String groupName, String[] accessibleSpaces,
        String[] permittedActions, Schedule schedule) {
    this.groupName = groupName;
    this.users = new ArrayList<>();
    this.accessibleSpaces = accessibleSpaces;
    this.permittedActions = permittedActions;
    this.schedule = schedule;
  }

  // Adds a user to the group
  public void addUser(User user) {
    this.users.add(user);
  }

  public ArrayList<User> getUsers() {
    return this.users;
  }

  public String[] getPermittedActions() {
    return this.permittedActions;
  }

  public String getGroupName() {
    return this.groupName;
  }

  public Schedule getSchedule() {
    return this.schedule;
  }

  public String getPrivileges() {
    return "\nAccessible Spaces: " + String.join(", ", this.accessibleSpaces)
            + "\nPermitted Actions: " + String.join(", ", this.permittedActions)
            + "\nSchedule: " + this.schedule.toString();
  }
}

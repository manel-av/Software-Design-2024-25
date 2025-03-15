package baseNoStates;

import baseNoStates.users.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// DirectoryUsersGroup class has the responsibility to create all users and groups.
// This class also puts the users on the corresponding group.
public final class DirectoryUsersGroup {
  private static final Logger milestone1Logger = LoggerFactory.getLogger("Milestone1");
  private static final Logger milestone2Logger = LoggerFactory.getLogger("Milestone2");
  private static DirectoryUsersGroup uniqueInstance = null;
  private static final ArrayList<UserGroup> usersGroups = new ArrayList<>();

  private DirectoryUsersGroup() {
    makeUsersGroup();
  }

  public static synchronized DirectoryUsersGroup getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new DirectoryUsersGroup();
      milestone2Logger.info("DirectoryUsersGroup instance correctly instantiated.");
    } else {
      milestone2Logger.warn("Can't create an instance. Other DirectoryUsersGroup instance exists.");
    }
    return uniqueInstance;
  }

  // Initializes and creates predefined user groups with users, spaces, actions, and schedules.
  private void makeUsersGroup() {
    // Group without privileges (temporal group), can't do anything.
    String[] noPrivilegesSpaces = {};
    String[] noPrivilegesActions = {};
    UserGroup noPrivilegesGroup = new UserGroup(
            "No Privileges", noPrivilegesSpaces, noPrivilegesActions, null); // No Schedule
    noPrivilegesGroup.addUser(new User("Bernat", "12345"));
    noPrivilegesGroup.addUser(new User("Blai", "77532"));
    usersGroups.add(noPrivilegesGroup);
    milestone1Logger.info("No privileges group created.");

    // Employees group, can do certain action at a fixed schedule
    String[] employeeSpaces = {"ground floor", "floor1", "exterior", "stairs"};
    String[] employeeActions = {"open", "close", "short unlock"};

    Schedule employeeSchedule = new Schedule(
            LocalTime.of(9, 0), LocalTime.of(17, 0),
            Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY, DayOfWeek.FRIDAY), LocalDate.of(2024, 9, 1),
            LocalDate.of(2025, 3, 1)
    );

    UserGroup employeeGroup = new UserGroup(
            "Employees", employeeSpaces, employeeActions, employeeSchedule);
    employeeGroup.addUser(new User("Ernest", "74984"));
    employeeGroup.addUser(new User("Eulalia", "43295"));
    usersGroups.add(employeeGroup);
    milestone1Logger.info("Employee group created. {}", employeeGroup.getPrivileges());

    // Managers group, they can do anything, but not at any time
    String[] managerSpaces = {"ground floor", "floor1", "exterior", "stairs", "parking"};
    String[] managerActions = {"open", "close", "lock", "unlock", "unlock_shortly"};

    Schedule managerSchedule = new Schedule(
            LocalTime.of(8, 0), LocalTime.of(20, 0),
            Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY),
            LocalDate.of(2024, 9, 1), LocalDate.of(2025, 3, 1)
    );

    UserGroup managerGroup = new UserGroup(
            "Managers", managerSpaces, managerActions, managerSchedule);
    managerGroup.addUser(new User("Manel", "95783"));
    managerGroup.addUser(new User("Marta", "05827"));
    usersGroups.add(managerGroup);
    milestone1Logger.info("Managers group created. {}", managerGroup.getPrivileges());

    // Administrators group, they can do all actions at any time.
    String[] adminActions = {"open", "close", "lock", "unlock", "unlock_shortly"};

    Schedule adminSchedule = new Schedule(
            LocalTime.of(0, 0), LocalTime.of(23, 59),
            Arrays.asList(DayOfWeek.values()), LocalDate.of(2024, 1, 1),
            LocalDate.of(2100, 1, 1)
    );

    UserGroup adminGroup = new UserGroup(
            "Administrators", new String[]{"all areas"}, adminActions, adminSchedule);
    adminGroup.addUser(new User("Ana", "11343"));
    usersGroups.add(adminGroup);
    milestone1Logger.info("Admin group created. {}", adminGroup.getPrivileges());
  }

  // Method to search for a specific user using their credential
  public static User findUserByCredential(String credential) {
    for (UserGroup group : usersGroups) {
      for (User user : group.getUsers()) {
        if (user.getCredential().equals(credential)) {
          return user;
        }
      }
    }
    //System.out.println("User with credential " + credential + " not found");
    milestone1Logger.warn("User with credential {} not found", credential);
    return null;
  }

  // Method to return the group of a user
  public static UserGroup findGroupForUser(User user) {
    for (UserGroup group : usersGroups) {
      if (group.getUsers().contains(user)) {
        return group;
      }
    }
    return null;
  }
}
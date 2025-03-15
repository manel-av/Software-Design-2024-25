package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Before executing enable assertions :
// https://se-education.org/guides/tutorials/intellijUsefulSettings.html
public class Main {
  public static void main(String[] args) {
    Logger milestone1Logger = LoggerFactory.getLogger("Milestone1");

    milestone1Logger.info("Initializing Areas...");
    DirectoryAreas.getInstance();
    //DirectoryAreas.getInstance();
    milestone1Logger.info("Initializing Users...");
    DirectoryUsersGroup.getInstance();
    //DirectoryUsersGroup.getInstance();
    milestone1Logger.info("Initializing Web Server...");
    new WebServer();
  }
}

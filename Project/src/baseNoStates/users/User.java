package baseNoStates.users;

// User class represents an individual user with a name and credential.
public class User {
  private final String name;
  private final String credential;

  public User(String name, String credential) {
    this.name = name;
    this.credential = credential;
  }

  public String getCredential() {
    return this.credential;
  }

  // Provides a string representation of the user.
  @Override
  public String toString() {
    return "User{name=" + this.name + ", credential=" + this.credential + "}";
  }
}

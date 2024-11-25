package lma.librarymanagementapplication;

public abstract class Person {
    private String fullname;
    private String email;
    private String username;
    private String password;
    private String birthdate;
    private String gender;

    public Person() {}

    public Person(String fullname, String email, String username,
                  String password, String birthdate, String gender) {
        this.fullname = fullname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.birthdate = birthdate;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}

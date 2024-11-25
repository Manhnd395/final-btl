package lma.librarymanagementapplication;

public class staffClass extends Person {
    private String staffID;
    private String department;
    private String position;

    public staffClass(String fullname, String email, String username,
                      String password, String birthdate, String gender,
                      String staffID, String department, String position) {
        super(fullname, email, username, password, birthdate, gender);
        this.staffID = staffID;
        this.department = department;
        this.position = position;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}

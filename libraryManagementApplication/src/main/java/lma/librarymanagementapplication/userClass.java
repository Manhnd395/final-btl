package lma.librarymanagementapplication;

public class userClass extends Person {

    private String MSV;


    public userClass(String fullname, String email, String MSV, String username, String password, String birthdate, String gender) {
        super(fullname, email, username, password, birthdate, gender);
        this.MSV = MSV;
    }
    public String getMSV() {
        return MSV;
    }

    public void setMSV(String MSV) {
        this.MSV = MSV;
    }

}
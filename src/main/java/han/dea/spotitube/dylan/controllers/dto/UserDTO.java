package han.dea.spotitube.dylan.controllers.dto;

public class UserDTO {

    private String user, password;
    private int id;
    public UserDTO() {}
    public UserDTO(String user, String password, int id) {
        this.user = user;
        this.password = password;
        this.id = id;
    }
    public UserDTO(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
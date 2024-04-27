package Model;

public class User {
    private String username;
    private String password;
    private int role_ID;
    private int user_ID;


    public User(String username, String password, int role_ID, int user_ID){
        this.username=username;
        this.password=password;
        this.role_ID =role_ID;
        this.user_ID=user_ID;
    }

    public int getRole_ID() {
        return role_ID;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }
}

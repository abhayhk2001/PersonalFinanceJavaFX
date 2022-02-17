package models;

public class LoginUser {
    int userID;
    String UserName;
    String Password;
    public LoginUser(String UserName,String Password) {
        this.UserName = UserName;
        this.Password = Password;
    }
    public LoginUser(int userId,String UserName,String Password) {
        this.userID = userId;
        this.UserName = UserName;
        this.Password = Password;
    }
    public String getUserName(){
        return UserName;
    }
    public String getPassword() {
        return Password;
    }
    public void setId(int userId) {
        this.userID = userId;
    }
}
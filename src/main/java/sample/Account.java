package sample;

public class Account {
    private  String username,password;

    public Account(String username,String password){
        this.username=username;
        this.password=password;
    }
    public void setPassword(String password)  {
        this.password=password;

    }
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }


}

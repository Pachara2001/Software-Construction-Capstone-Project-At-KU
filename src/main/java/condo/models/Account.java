package condo.models;

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

    public boolean entryCheck(String username,String password){
        if(this.username.equalsIgnoreCase(username)) {
            if(this.password.equals(password)) return true;
            else throw new IllegalArgumentException("Wrong password.");
        }
        return false;
    }



}

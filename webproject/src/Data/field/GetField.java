package Data.field;

public class GetField {
    private String username;
    private String password;
    private String avatar;
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getAvatar(){
        return avatar;
    }
    public GetField(String name,String password,String avatar){
        this.username = name;
        this.password = password;
        this.avatar = avatar;
    }
    public GetField(){
    }
}

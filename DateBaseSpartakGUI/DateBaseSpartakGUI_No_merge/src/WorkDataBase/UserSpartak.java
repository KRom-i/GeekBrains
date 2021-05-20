package WorkDataBase;

public class UserSpartak {

    private int id;
    private String name;
    private String login;
    private int password;
    private boolean auth;

    private final String NAME_Class_DB = "User";

    public String nameClassDataBase(){
        return NAME_Class_DB;
    }

    public UserSpartak(int id, String name, String login, int password, boolean auth) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.auth = auth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }
}

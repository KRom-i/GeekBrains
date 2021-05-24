package WorkDataBase;

public class ActionUser {

    private static UserSpartak user;

    public static UserSpartak getUser() {
        return user;
    }

    public static void setUser(UserSpartak user) {
        ActionUser.user = user;
    }
}

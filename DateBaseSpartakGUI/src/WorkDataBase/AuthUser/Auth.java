package WorkDataBase.AuthUser;

import Logger.LOG;
import MySQLDB.ServerMySQL;
import WorkDataBase.AuthUserDateBase;

public class Auth {

    public static void main(String[] args) {

        String str = "limit2301";

        ServerMySQL.getConnection ();

        ServerMySQL.disconnect ();
    }


//    Метод проверяет актывных пользователей и возвращает пользователя




}

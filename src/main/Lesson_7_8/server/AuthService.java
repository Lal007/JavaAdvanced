package main.Lesson_7_8.server;

import java.sql.*;
import java.util.ArrayList;

public class AuthService {
    private static Connection connection;
    private static Statement stmt;

    public static void connect() throws SQLException{
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:DBUsers.db");
            stmt = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static String getNickByLoginAndPass(String login, String pass){
        String sql = String.format("SELECT nickname FROM main where login = '%s' and password = '%s'", login, pass);

        try {
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()){
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getIdByNick(String nick){
        String sql = String.format("SELECT id FROM main WHERE nickname = '%s'", nick);

        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()){
                return Integer.parseInt(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean registerNewUser(String nick, String login, String pass){
        String sql = String.format("INSERT INTO main (login, password, nickname) VALUES ('%s', '%s', '%s')", login, pass, nick);

        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getNickByLoginAndPass(login, pass) != null;
    }

    public static void addUserToBlacklist(int id, String nick){
        String sql = String.format("INSERT INTO black_list (id_user, banned_users) VALUES ('%d', '%s')", id, nick);

        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getBannedUsers(int id){
        String sql = String.format("SELECT banned_users FROM black_list where id_user = %d", id);
        ArrayList<String> bannedUsers = new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                bannedUsers.add(rs.getString(1));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (bannedUsers.size() > 0) {
            return bannedUsers;
        }else {
            return null;
        }
    }
}

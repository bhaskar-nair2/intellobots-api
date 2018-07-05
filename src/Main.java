import java.sql.*;


public class Main {
    public static void main(String[] args) {

        String host = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "password";

        System.out.println("Hello World!");
        apiRef ar=new apiRef(host,user,pass);

    }
}


class apiRef{
    Connection con;
    PreparedStatement pstmt;

    public apiRef(String host, String user, String pass){
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(host,user, pass);
            System.out.println('1');
        } catch (Exception e) {
           System.out.println(e);
        }


    }

    void insert(String[] args){
        try {
            this.pstmt = con.prepareStatement("insert into category(category_name,created_by,mod_by,mod_date) values (?,?,?,?)");
            pstmt.setString(1,args[0]);

        }catch (SQLException e){
            System.out.println(e);
        }
    }

}

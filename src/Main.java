import java.sql.*;


public class Main {
    public static void main(String[] args) {

        String host = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "password";

        System.out.println("Hello World!");
        apiRef ar=new apiRef(host,user,pass);//Declare new instance

        //Insert Into Functions-
        String[] aa={"Tomato","1","7","2018-06-10 10:20:30.4"};
        ar.insertCategory(aa);


    }
}


class apiRef{
//Global Variable
    protected Connection con;
    protected Statement stmt;
    protected PreparedStatement pstmt;
    protected ResultSet rs;

//Constructor
    apiRef(String host, String user, String pass){
        try {
            Class.forName("org.postgresql.Driver");
            this.con = DriverManager.getConnection(host,user, pass);
            System.out.println('1');
        } catch (SQLException e) {
           System.out.println(e.getErrorCode());
           System.out.println(e.getSQLState());
        }
        catch (ClassNotFoundException e){
            e.getCause();
            e.getException();
        }
    }
//Insert Statements
    protected void insertCategory(String[] args){
        try {
            this.pstmt = con.prepareStatement("insert into category(category_name,created_by,mod_by,mod_date) values (?,?,?,?)");
            this.pstmt.setString(1,args[0]);
            this.pstmt.setInt(2,Integer.parseInt(args[1]));
            this.pstmt.setInt(3,Integer.parseInt(args[2]));
            this.pstmt.setTimestamp(4, Timestamp.valueOf(args[3]));
            this.pstmt.execute();
            this.pstmt.close();

        }catch (SQLException e){
            System.out.println(e);
            System.out.println("Parameters should be in the format-[(String category_name, String created_by, String mod_by, String mod_date(YYYY-MM-DD HH-MM-SS-NN)]");
        }
    }

    protected void insertUserDets(String[] args){
        try{
         this.pstmt = con.prepareStatement("insert into userDets(username,pass) values (?,?)");
         this.pstmt.setString(1,args[0]);
         this.pstmt.setString(2,args[1]);
         this.pstmt.execute();
         this.pstmt.close();

        }catch (SQLException e){
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());

        }
    }

    protected void insertProduct(String[] args){
        try{
            this.pstmt = con.prepareStatement("insert into products(category_id,category_name,product_id,product_name) values (?,?,?,?)");
            this.pstmt.setString(1,args[0]);
            this.pstmt.setInt(2,Integer.parseInt(args[1]));
            this.pstmt.setString(3,args[2]);
            this.pstmt.setInt(4,Integer.parseInt(args[3]));
            this.pstmt.execute();
            this.pstmt.close();

        }catch (SQLException e){
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());

        }
    }

    protected void insertCupon(String[] args){
        try{
            this.pstmt = con.prepareStatement("insert into cupons(cupon_id,cupon_name,cupon_value,cupon_type) values (?,?,?,?)");
            this.pstmt.setInt(1,Integer.parseInt(args[0]));
            this.pstmt.setString(2,args[1]);
            this.pstmt.setInt(3,Integer.parseInt(args[2]));
            this.pstmt.setString(4,args[3]);
            this.pstmt.execute();
            this.pstmt.close();

        }catch (SQLException e){
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());

        }
    }

//Select Statements
    protected ResultSet select(String tabel){
        try{
            this.stmt=con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            this.rs=this.stmt.executeQuery("select * from "+tabel);
            this.stmt.close();
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
        }
        return this.rs;
    }

    protected ResultSet select(String tabel,String cond){
        try{
            this.stmt=con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            this.rs=this.stmt.executeQuery("select * from "+tabel+" where "+cond);
            this.stmt.close();
            return rs;
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
        }
        return this.rs;
    }

//Update Statements
    protected void update(String tabel, String param){
        try {
            this.pstmt = con.prepareStatement("update "+tabel+" set "+param);
            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
            System.out.print(e.getSQLState());
        }
    }

    protected void update(String tabel, String param,String cond){
        try {
            this.pstmt = con.prepareStatement("update "+tabel+" set "+param+" where "+cond);
            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
            System.out.print(e.getSQLState());
        }
    }
//Close Call
    protected void closeall(){
        try {
            this.rs.close();
            this.stmt.close();
            this.pstmt.close();
            this.con.close();
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
        }
    }

}

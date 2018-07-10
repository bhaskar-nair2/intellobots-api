import java.sql.*;


public class Main {
    public static void main(String[] args) {

        String host = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "password";

        System.out.println("Hello World!");
        apiRef ar=new apiRef(host,user,pass);//Declare new instance
        Cart cart=new Cart(host,user,pass);

        //Insert Into Functions-
        // String[] aa={"Tomato","1","7","2018-06-10 10:20:30.4"};
       // ar.insertCategory(aa);
       // cart.addToCart(2,2,20,"2018-06-05 05:04:08.4");
        //System.out.println(cart.countCart(1));
        System.out.println(cart.cartDets(1)[0]);

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

class Cart{
    protected Connection con;
    protected Statement stmt;
    protected PreparedStatement pstmt;
    protected ResultSet rs;

    Cart(String host, String user, String pass){
        try{
            Class.forName("org.postgresql.Driver");
            this.con=DriverManager.getConnection(host, user, pass);
        }catch (ClassNotFoundException e){
            System.out.println(e.getException());
        }
        catch (SQLException e){
            System.out.println(e.getSQLState());
        }
    }

    protected void addToCart(int u_id,int prod_id,int quantity,String date){
        try {
            this.pstmt = this.con.prepareStatement("insert into cart(cart_id, item_id, quantity, made_on) VALUES (?,?,?,?)");
            this.pstmt.setInt(1,u_id);
            this.pstmt.setInt(2,prod_id);
            this.pstmt.setInt(3,quantity);
            this.pstmt.setTimestamp(4,Timestamp.valueOf(date));
            this.pstmt.executeUpdate();
            System.out.println("Success!");
        }
        catch (SQLException e){
            System.out.println(e);
        }

    }

    protected int countCart(){
        int count=0;
        try {
            stmt=con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            rs=stmt.executeQuery("SELECT count(*) from cart");
            rs.next();
            count=rs.getInt(1);
        }catch (SQLException e){
            System.out.println(e);
        }
        return count;
    }

    protected int countCart(int u_id){
        int count=0;
        try {
            stmt=con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            rs=stmt.executeQuery("SELECT count(*) from cart where cart_id = "+ u_id);
            rs.next();
            count=rs.getInt(1);
        }catch (SQLException e){
            System.out.println(e);
        }
        return count;
    }

    protected String[][] cartDets(int id){
        String[][] cartDet=null;
        try{
            this.stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            this.rs=this.stmt.executeQuery("SELECT * from cart where cart_id="+id);
            rs.last();
            int rowCt=rs.getRow();
            cartDet=new String[rowCt][5];
            String[] order_dets=new String[5];
            rs.first();
            int ct=0;
            while (rs.next()){
                for(int i=0;i<5;i++){
                    System.out.println(rs.getInt(i+1));
                    order_dets[i]=""+rs.getInt(i+1);
                }
                cartDet[ct++]=order_dets;
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return cartDet;
    }

}
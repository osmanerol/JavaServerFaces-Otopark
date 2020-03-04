
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

@ManagedBean (name="login")

public class Login {
    public String email,password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String passwordDB;

    public String getPasswordDB() {
        return passwordDB;
    }

    public void setPasswordDB(String passwordDB) {
        this.passwordDB = passwordDB;
    }
    
    public String goPage() throws SQLException{
        if(this.email!=null && this.password!=null){
            if(this.email.equals("admin") && this.password.equals("admin")){
                return "admin.xhtml";
            }
            else{
                
                try{
                    Context ctx=new InitialContext();
                    DataSource dataSource=(DataSource)ctx.lookup("jdbc/Otopark");
                }catch(Exception e){
                    e.printStackTrace();
                }
                try {
                    Class.forName("org.apache.derby.jdbc.ClientDriver");
                    Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
                    PreparedStatement ps=connection.prepareStatement("SELECT PASSWORD FROM CUSTOMER WHERE EMAIL=?");
                    ps.setString(1,this.email);
                    ResultSet resultSet=ps.executeQuery();
                    while(resultSet.next()){
                        setPasswordDB(resultSet.getString("PASSWORD"));
                    }
                    
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if(this.password.equals(getPasswordDB())){
                    return "customer.xhtml";
                }
                else{
                    return "login.xhtml";
                }
                
            }
        }
        else{
            return "";
        }
    }
    
    public String getResponse(){
        if (this.email==null && this.password==null) {
            return "<p>Please fill in all fields</p>";
        }
        else{
            return "";
        }
    }
}


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


@ManagedBean (name="contact")

public class Contact {
    
    public String email,address,phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    DataSource dataSource=null;
    
    public Contact(){
        try{
            Context ctx=new InitialContext();
            dataSource=(DataSource)ctx.lookup("jdbc/Otopark");
        }catch(Exception e){
            e.printStackTrace();
        } 
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            PreparedStatement ps =connection.prepareStatement( "SELECT * FROM CONTACT" );
            ResultSet rs=ps.executeQuery();
            rs.next();
            setEmail(rs.getString("EMAIL"));
            setPhone(rs.getString("PHONE"));
            setAddress(rs.getString("ADDRESS"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String update() throws SQLException{
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            PreparedStatement ps =connection.prepareStatement( "UPDATE CONTACT SET EMAIL=? , PHONE=?, ADDRESS=?" );
            ps.setString(1,email);
            ps.setString(2,phone);
            ps.setString(3,address);
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return "admin.xhtml";
    }
}


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author osman
 */
@ManagedBean (name="admin")

public class Admin {
    DataSource dataSource=null;
    
    public Admin(){
        try{
            Context ctx=new InitialContext();
            dataSource=(DataSource)ctx.lookup("jdbc/Otopark");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    List<Customer> list=new ArrayList<Customer>();
    
    public List<Customer> customers() throws SQLException{
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            PreparedStatement ps=connection.prepareStatement("Select * from customer");
            ResultSet rs=ps.executeQuery();
            list.clear();
            while(rs.next()){
                Customer customer=new Customer();
                customer.setId(rs.getInt("ID"));
                customer.setName(rs.getString("NAME"));
                customer.setLastname(rs.getString("LASTNAME"));
                customer.setJob(rs.getString("JOB"));
                customer.setCarBrand(rs.getString("CARBRAND"));
                customer.setCarType(rs.getString("CARTYPE"));
                customer.setPlateCode(rs.getString("PLATECODE"));
                customer.setEmail(rs.getString("EMAIL"));
                customer.setPassword(rs.getString("PASSWORD"));
                list.add(customer);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    public String delete(){
        return "customersForAdmin.xhtml";
    }
    
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void delete2(ActionEvent event) throws SQLException{
        name=(String)event.getComponent().getAttributes().get("name");
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            PreparedStatement ps=connection.prepareStatement("DELETE FROM CUSTOMER WHERE NAME=?");
            ps.setString(1, name);
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

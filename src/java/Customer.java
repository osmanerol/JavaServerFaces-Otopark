
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

@ManagedBean(name="customer")

public class Customer {
    public String name,lastname,job,carBrand,carType,plateCode,email,password;
    public int id;
    public int enterTime,exitTime;

    public int getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(int enterTime) {
        this.enterTime = enterTime;
    }

    public int getExitTime() {
        return exitTime;
    }

    public void setExitTime(int exitTime) {
        this.exitTime = exitTime;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getPlateCode() {
        return plateCode;
    }

    public void setPlateCode(String plateCode) {
        this.plateCode = plateCode;
    }

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
    
    public Customer(){
        try{
            Context ctx=new InitialContext();
            DataSource dataSource=(DataSource)ctx.lookup("jdbc/Otopark");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String receivedEmail;

    public String getReceivedEmail() {
        return receivedEmail;
    }

    public void setReceivedEmail(String receivedEmail) {
        this.receivedEmail = receivedEmail;
    }
    
    public void actList(ActionEvent event){
        receivedEmail=(String)event.getComponent().getAttributes().get("email");
    }
    
    List<Customer> liste=new ArrayList<Customer>();       
         
    public List<Customer> getInfo() throws SQLException{
        try{
           Class.forName("org.apache.derby.jdbc.ClientDriver");
           Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
           PreparedStatement ps=connection.prepareStatement("SELECT * FROM CUSTOMER WHERE EMAIL=?");
           ps.setString(1, receivedEmail);
           ResultSet rs=ps.executeQuery();
           liste.clear();
            rs.next();
            setPlateCode(rs.getString("PLATECODE"));
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
                
                liste.add(customer);
        }catch(Exception e){
            e.printStackTrace();
        }
        return liste;
    }
    
    public List<Customer> history() throws SQLException{
        try{
           
           Class.forName("org.apache.derby.jdbc.ClientDriver");
           Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
           PreparedStatement ps=connection.prepareStatement("SELECT * FROM OTOPARKHISTORY WHERE PLATECODE=?");
           ps.setString(1, plateCode);
           ResultSet rs=ps.executeQuery();
           liste.clear();
           while(rs.next()){
               Customer customer=new Customer();
               customer.setPlateCode(rs.getString("PLATECODE"));
               customer.setEnterTime(rs.getInt("ENTERTIME"));
               customer.setExitTime(rs.getInt("EXITTIME"));
               liste.add(customer);
           }
           
        }catch(Exception e){
            e.printStackTrace();
        }
        return liste;
    }
}

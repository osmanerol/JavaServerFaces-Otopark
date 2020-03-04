
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/


@ManagedBean (name="sign")

public class SignIn {
    public String name,lastname,job,carBrand,carType,plateCode,email,password;

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
    
    CachedRowSet rowSet=null;
    DataSource dataSource;
    
    
    public SignIn(){
        try{
            Context ctx=new InitialContext();
            dataSource=(DataSource)ctx.lookup("jdbc/Otopark");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
   
    public int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
    public String ekle() throws SQLException{
        
        if(name!=null && lastname!=null && carBrand!=null && carType!=null && job!=null && plateCode!=null && email!=null && password!=null){
            try{
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
                PreparedStatement ps =connection.prepareStatement( "INSERT INTO customer(NAME,LASTNAME,JOB,CARBRAND,CARTYPE,PLATECODE,EMAIL,PASSWORD,INOTOPARK) VALUES ( ?, ?, ?, ?,?, ?, ? ,?,0)" );
                
                ps.setString(1, getName());
                ps.setString(2, getLastname());
                ps.setString(3, getJob());
                ps.setString(4, getCarBrand());
                ps.setString(5, getCarType());
                ps.setString(6, getPlateCode());
                ps.setString(7, getEmail());
                ps.setString(8, getPassword());
                
                ps.executeUpdate();
                
                
                
            }catch(Exception e){
                e.printStackTrace();
            }
            return "login.xhtml";
       }
        else{
            return "<p>Please fill in full blanks</p>";
        }
    }
    
    public String ekle2() throws SQLException{
        
        if(name!=null && lastname!=null && carBrand!=null && carType!=null && job!=null && plateCode!=null && email!=null && password!=null){
            try{
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
                PreparedStatement ps =connection.prepareStatement( "INSERT INTO customer(NAME,LASTNAME,JOB,CARBRAND,CARTYPE,PLATECODE,EMAIL,PASSWORD) VALUES ( ?, ?, ?, ?,?, ?, ? ,?)" );
                
                ps.setString(1, getName());
                ps.setString(2, getLastname());
                ps.setString(3, getJob());
                ps.setString(4, getCarBrand());
                ps.setString(5, getCarType());
                ps.setString(6, getPlateCode());
                ps.setString(7, getEmail());
                ps.setString(8, getPassword());
                
                ps.executeUpdate();
                
                /*
                PreparedStatement psp =connection.prepareStatement( "SELECT NUM FROM CAPASITY WHERE CARTYPE=?" );
                psp.setString(1, getCarType());
                ResultSet rs=psp.executeQuery();
                setNum(rs.getInt("NUM"));
                if(num>0){
                    num--;
                    PreparedStatement psp1 =connection.prepareStatement( "UPDATE CAPASITY SET NUM=? WHERE CARTYPE=?" );
                    psp1.setInt(1, num);
                    psp1.setString(2,getCarType());
                    psp1.executeUpdate();
                }
                        */
            }catch(Exception e){
                e.printStackTrace();
            }
            return "inOtopark.xhtml";
       }
        else{
            return "<p>Please fill in full blanks</p>";
        }
    }
}
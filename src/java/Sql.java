
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

@ManagedBean (name="sql")

public class Sql {
    
    public String name,lastname,job,carBrand,carType,plateCode,email,password;
    public int id,price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int enterTime,exitTime;

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

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
    
    public int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
    List<Sql> liste=new ArrayList<Sql>();
    
    public List<Sql> highest() throws SQLException{
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            PreparedStatement ps=connection.prepareStatement("select * from customer JOIN money on customer.platecode=(select money.platecode from money where money.PRICE=(select max(price) from money)) and money.PLATECODE=(select money.platecode from money where money.PRICE=(select max(price) from money)) order by price desc");
            ResultSet rs=ps.executeQuery();
            rs.next();
            liste.clear();
            Sql sql=new Sql();
            sql.setId(rs.getInt("ID"));
            sql.setName(rs.getString("NAME"));
            sql.setLastname(rs.getString("LASTNAME"));
            sql.setJob(rs.getString("JOB"));
            sql.setCarBrand(rs.getString("CARBRAND"));
            sql.setCarType(rs.getString("CARTYPE"));
            sql.setPlateCode(rs.getString("PLATECODE"));
            sql.setEmail(rs.getString("EMAIL"));
            sql.setPassword(rs.getString("PASSWORD"));
            sql.setPrice(rs.getInt("PRICE"));
            liste.add(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
        return liste;
    }
    
    public List<Sql> type() throws SQLException{
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            PreparedStatement ps=connection.prepareStatement("select cartype ,count(*) as aracsayisi from otoparkhistory  group by cartype order by aracsayisi desc");
            ResultSet rs=ps.executeQuery();
            liste.clear();
            while(rs.next()){
                Sql sql=new Sql();
                sql.setCarType(rs.getString("CARTYPE"));
                sql.setNum(rs.getInt("ARACSAYISI"));
                liste.add(sql);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return liste;
    }
    
    public List<Sql> otopark() throws SQLException{
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            PreparedStatement ps=connection.prepareStatement("select customer.name,customer.lastname,inotopark.platecode,inotopark.entertime from inotopark inner join customer on inotopark.platecode=customer.platecode");
            ResultSet rs=ps.executeQuery();
            liste.clear();
            while(rs.next()){
                Sql sql=new Sql();
                sql.setName(rs.getString("NAME"));
                sql.setLastname(rs.getString("LASTNAME"));
                sql.setPlateCode(rs.getString("PLATECODE"));
                sql.setEnterTime(rs.getInt("ENTERTIME"));
                liste.add(sql);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return liste;
    }
    
    public List<Sql> job() throws SQLException{
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            PreparedStatement ps=connection.prepareStatement("select job,sum(exittime-entertime) as totaltime from otoparkhistory group by job order by totaltime desc");
            ResultSet rs=ps.executeQuery();
            liste.clear();
            while(rs.next()){
                Sql sql=new Sql();
                sql.setJob(rs.getString("JOB"));
                sql.setNum(rs.getInt("TOTALTIME"));
                liste.add(sql);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return liste;
    }
    
    public List<Sql> total() throws SQLException{
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            PreparedStatement ps=connection.prepareStatement("select name,lastname,platecode,sum(exittime-entertime) as totaltime from otoparkhistory group by name,lastname,platecode order by totaltime desc");
            ResultSet rs=ps.executeQuery();
            liste.clear();
            while(rs.next()){
                Sql sql=new Sql();
                sql.setName(rs.getString("NAME"));
                sql.setLastname(rs.getString("LASTNAME"));
                sql.setPlateCode(rs.getString("PLATECODE"));
                sql.setNum(rs.getInt("TOTALTIME"));
                liste.add(sql);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return liste;
    }
    
}

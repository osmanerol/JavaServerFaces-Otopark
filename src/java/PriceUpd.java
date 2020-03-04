
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


@ManagedBean (name="priceUpd")

public class PriceUpd {
    public String carType;
    public int cost0_2,cost2_4,cost4_6,cost6_8,cost8_12,cost12_24,costDay,costMounth;

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }


    public int getCost0_2() {
        return cost0_2;
    }

    public void setCost0_2(int cost0_2) {
        this.cost0_2 = cost0_2;
    }

    public int getCost2_4() {
        return cost2_4;
    }

    public void setCost2_4(int cost2_4) {
        this.cost2_4 = cost2_4;
    }

    public int getCost4_6() {
        return cost4_6;
    }

    public void setCost4_6(int cost4_6) {
        this.cost4_6 = cost4_6;
    }

    public int getCost6_8() {
        return cost6_8;
    }

    public void setCost6_8(int cost6_8) {
        this.cost6_8 = cost6_8;
    }

    public int getCost8_12() {
        return cost8_12;
    }

    public void setCost8_12(int cost8_12) {
        this.cost8_12 = cost8_12;
    }

    public int getCost12_24() {
        return cost12_24;
    }

    public void setCost12_24(int cost12_24) {
        this.cost12_24 = cost12_24;
    }

    public int getCostDay() {
        return costDay;
    }

    public void setCostDay(int costDay) {
        this.costDay = costDay;
    }

    public int getCostMounth() {
        return costMounth;
    }

    public void setCostMounth(int costMounth) {
        this.costMounth = costMounth;
    }

    public String updateType;

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }
    
    

    DataSource dataSource;
    
    public void update2(ActionEvent event){
        updateType=(String)event.getComponent().getAttributes().get("name");
        Connection connection=null;
        PreparedStatement ps=null;
        try{
            Context ctx=new InitialContext();
            dataSource=(DataSource)ctx.lookup("jdbc/Otopark");
        }catch(Exception e){
            e.printStackTrace();
        } 
        try{
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            ps=connection.prepareStatement("SELECT * FROM price where CARTYPE=?");
            ps.setString(1, updateType);
            ResultSet resultSet=ps.executeQuery();
            while(resultSet.next()){
                setCarType(resultSet.getString("CARTYPE"));
                setCost0_2(resultSet.getInt("TIME0_2"));
                setCost2_4(resultSet.getInt("TIME2_4"));
                setCost4_6(resultSet.getInt("TIME4_6"));
                setCost6_8(resultSet.getInt("TIME6_8"));
                setCost8_12(resultSet.getInt("TIME8_12"));
                setCost12_24(resultSet.getInt("TIME12_24"));
                setCostDay(resultSet.getInt("DAILY"));
                setCostMounth(resultSet.getInt("MOUNTH"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public PriceUpd(){
        try{
            Context ctx=new InitialContext();
            dataSource=(DataSource)ctx.lookup("jdbc/Otopark");
        }catch(Exception e){
            e.printStackTrace();
        } 
    }
    
    public String update() throws SQLException{
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            PreparedStatement ps =connection.prepareStatement( "UPDATE PRICE SET TIME0_2=? , TIME2_4=? ,TIME4_6=?, TIME6_8=?,TIME8_12=? , TIME12_24=?,DAILY=?,MOUNTH=? WHERE CARTYPE=?" );
            ps.setInt(1, cost0_2);
            ps.setInt(2, cost2_4);
            ps.setInt(3, cost4_6);
            ps.setInt(4, cost6_8);
            ps.setInt(5, cost8_12);
            ps.setInt(6, cost12_24);
            ps.setInt(7, costDay);
            ps.setInt(8, costMounth);
            ps.setString(9, carType);
            
            
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return "editPrice.xhtml";
    }
    
    public String returnPrice(){
        return "index.xhtml";
    }
    
}


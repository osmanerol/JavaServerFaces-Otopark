
import javax.faces.bean.ManagedBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import javax.sql.rowset.CachedRowSet;
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

@ManagedBean(name="prices")

public class Prices {
   
    public String update(){
        return "editPrice2.xhtml";
    }
    
    
    //Show prices
    private List<PriceType> list = new ArrayList<PriceType>();
    Connection connection=null;
    PreparedStatement ps=null;
    
    
    DataSource dataSource;
    
    public Prices(){
        try{
            Context ctx=new InitialContext();
            dataSource=(DataSource)ctx.lookup("jdbc/Otopark");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public List<PriceType> priceTable(){
        try{
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            ps=connection.prepareStatement("SELECT * FROM price");
            ResultSet resultSet=ps.executeQuery();
            list.clear();
            while(resultSet.next()){
                PriceType prices=new PriceType();
                prices.setCarType(resultSet.getString("CARTYPE"));
                prices.setCost0_2(resultSet.getInt("TIME0_2"));
                prices.setCost2_4(resultSet.getInt("TIME2_4"));
                prices.setCost4_6(resultSet.getInt("TIME4_6"));
                prices.setCost6_8(resultSet.getInt("TIME6_8"));
                prices.setCost8_12(resultSet.getInt("TIME8_12"));
                prices.setCost12_24(resultSet.getInt("TIME12_24"));
                prices.setCostDay(resultSet.getInt("DAILY"));
                prices.setCostMounth(resultSet.getInt("MOUNTH"));
                list.add(prices);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    
}


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

@ManagedBean (name="oto")

public class OtoparkHistory {
    List<Customer> list=new ArrayList<Customer>();
    
    public List<Customer> customers() throws SQLException{
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            PreparedStatement ps=connection.prepareStatement("Select * from OTOPARKHISTORY");
            ResultSet rs=ps.executeQuery();
            list.clear();
            while(rs.next()){
                Customer customer=new Customer();
                customer.setName(rs.getString("NAME"));
                customer.setLastname(rs.getString("LASTNAME"));
                customer.setJob(rs.getString("JOB"));
                customer.setCarBrand(rs.getString("CARBRAND"));
                customer.setCarType(rs.getString("CARTYPE"));
                customer.setPlateCode(rs.getString("PLATECODE"));
                customer.setEmail(rs.getString("EMAIL"));
                customer.setPassword(rs.getString("PASSWORD"));
                customer.setEnterTime(rs.getInt("ENTERTIME"));
                customer.setExitTime(rs.getInt("EXITTIME"));
                list.add(customer);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
}


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


@ManagedBean(name="otopark")

public class Otopark {
    
    public int automobile,motorbike,truck;

    public int getAutomobile() {
        return automobile;
    }

    public void setAutomobile(int automobile) {
        this.automobile = automobile;
    }

    public int getMotorbike() {
        return motorbike;
    }

    public void setMotorbike(int motorbike) {
        this.motorbike = motorbike;
    }

    public int getTruck() {
        return truck;
    }

    public void setTruck(int truck) {
        this.truck = truck;
    }
    
    
   
   public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
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
    
    
    public Otopark(){
        try{
            Context ctx=new InitialContext();
            DataSource dataSource=(DataSource)ctx.lookup("jdbc/Otopark");
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            
            PreparedStatement ps=connection.prepareStatement("SELECT NUM FROM CAPASITY WHERE CARTYPE='automobile'");
            ResultSet rs=ps.executeQuery();
            rs.next();
            setAutomobile(rs.getInt("NUM"));
            
            PreparedStatement ps2=connection.prepareStatement("SELECT NUM FROM CAPASITY WHERE CARTYPE='motorbike'");
            ResultSet rs1=ps2.executeQuery();
            rs1.next();
            setMotorbike(rs1.getInt("NUM"));
            
            PreparedStatement ps3=connection.prepareStatement("SELECT NUM FROM CAPASITY WHERE CARTYPE='truck'");
            ResultSet rs2=ps3.executeQuery();
            rs2.next();
            setTruck(rs2.getInt("NUM"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public String add() throws SQLException{
        
        
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            PreparedStatement ps=connection.prepareStatement("INSERT INTO INOTOPARK(PLATECODE,ENTERTIME) VALUES(?,?)");
            ps.setString(1, plateCode);
            ps.setInt(2,enterTime);
            ps.executeUpdate();
            
            PreparedStatement pr=connection.prepareStatement("UPDATE CUSTOMER SET INOTOPARK=1 WHERE PLATECODE=?");
            pr.setString(1, plateCode);
            pr.executeUpdate();
            
            PreparedStatement ps2=connection.prepareStatement("SELECT NUM FROM CAPASITY WHERE CARTYPE=(SELECT CARTYPE FROM CUSTOMER WHERE PLATECODE=?)");
            ps2.setString(1, plateCode);
            ResultSet rs=ps2.executeQuery();
            rs.next();
            num=rs.getInt("NUM");
            if(num>0){
                num--;
                PreparedStatement ps3=connection.prepareStatement("UPDATE CAPASITY SET NUM=? WHERE CARTYPE=(SELECT CARTYPE FROM CUSTOMER WHERE PLATECODE=?)");
                ps3.setInt(1, num);
                ps3.setString(2, plateCode);
                ps3.executeUpdate();
                
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return "admin.xhtml";
    }
    
    public String leavePlate;

    public String getLeavePlate() {
        return leavePlate;
    }

    public void setLeavePlate(String leavePlate) {
        this.leavePlate = leavePlate;
    }
    
    public void actList(ActionEvent event){
        leavePlate=(String)event.getComponent().getAttributes().get("plate");
    }
    
    List<Customer> list=new ArrayList<Customer>();
    
    public List<Customer> leave() throws SQLException{
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            PreparedStatement ps=connection.prepareStatement("SELECT * FROM CUSTOMER WHERE INOTOPARK=1");
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
    
    
    public String plaka;

    public String getPlaka() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }

    public int giris,cikis,gun;
    
    public int getGiris() {
        return giris;
    }

    public void setGiris(int giris) {
        this.giris = giris;
    }

    public int getCikis() {
        return cikis;
    }

    public void setCikis(int cikis) {
        this.cikis = cikis;
    }

    public int getGun() {
        return gun;
    }

    public void setGun(int gun) {
        this.gun = gun;
    }
    
    public String exit() throws SQLException{
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            PreparedStatement ps=connection.prepareStatement("SELECT * FROM INOTOPARK WHERE PLATECODE=?");
            ps.setString(1, leavePlate);
            ResultSet rs=ps.executeQuery();
            rs.next();
            setPlaka(rs.getString("PLATECODE"));
            setGiris(rs.getInt("ENTERTIME"));
            setCikis(rs.getInt("EXITTIME"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return "exit.xhtml";
    }
 
    
    public int cost0_2,cost2_4,cost4_6,cost6_8,cost8_12,cost12_24,costDay,costMounth;

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
    
    public int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    
        
        int num = 0;
    
    public void calculate() throws SQLException{
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            PreparedStatement ps=connection.prepareStatement("SELECT * FROM price WHERE CARTYPE=(SELECT CARTYPE FROM CUSTOMER WHERE PLATECODE=?)");
            ps.setString(1, plaka);
            ResultSet resultSet=ps.executeQuery();
            resultSet.next();
                setCarType(resultSet.getString("CARTYPE"));
                setCost0_2(resultSet.getInt("TIME0_2"));
                setCost2_4(resultSet.getInt("TIME2_4"));
                setCost4_6(resultSet.getInt("TIME4_6"));
                setCost6_8(resultSet.getInt("TIME6_8"));
                setCost8_12(resultSet.getInt("TIME8_12"));
                setCost12_24(resultSet.getInt("TIME12_24"));
                setCostDay(resultSet.getInt("DAILY"));
                setCostMounth(resultSet.getInt("MOUNTH"));
                
                
                
            }catch(Exception e){
                e.printStackTrace();
            }
        
        int hour=getCikis()-getGiris();
        
        if (hour>=0 && hour <=2) {
            num=cost0_2;
        }
        else if(hour>2 && hour<=4){
            num=cost2_4;
        }
        else if(hour>4 && hour<=6){
            num=cost4_6;
        }
        else if(hour>6 && hour<=8){
            num=cost6_8;
        }
        else if(hour>8 && hour<=12){
            num=cost8_12;
        }
        else if(hour>12 && hour<=24){
            num=cost12_24;
        }
        
        //setPrice((gun*costDay)+(hour*num));
        setPrice((gun*costDay)+num);
        try{
            Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            PreparedStatement ps4=connection.prepareStatement("INSERT INTO MONEY(PLATECODE,CARTYPE,PRICE) VALUES(?,(SELECT CARTYPE FROM CUSTOMER WHERE PLATECODE=?),?)");
            ps4.setString(1, plaka);
            ps4.setString(2, plaka);
            ps4.setInt(3,((gun*costDay)+num) );
            ps4.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
    
    public int calculatedPrice(){
        return getPrice();
    }

    public String pay() throws SQLException{
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/Otopark","APP","APP");
            PreparedStatement ps=connection.prepareStatement("SELECT * FROM CUSTOMER WHERE PLATECODE=?");
            ps.setString(1, plaka);
            ResultSet rs=ps.executeQuery();
            rs.next();
                setId(rs.getInt("ID"));
                setName(rs.getString("NAME"));
                setLastname(rs.getString("LASTNAME"));
                setJob(rs.getString("JOB"));
                setCarBrand(rs.getString("CARBRAND"));
                setCarType(rs.getString("CARTYPE"));
                setPlateCode(rs.getString("PLATECODE"));
                setEmail(rs.getString("EMAIL"));
                setPassword(rs.getString("PASSWORD"));
                
            PreparedStatement psp=connection.prepareStatement("INSERT INTO OTOPARKHISTORY(NAME,LASTNAME,JOB,CARBRAND,CARTYPE,PLATECODE,EMAIL,PASSWORD,ENTERTIME,EXITTIME) VALUES(?,?,?,?,?,?,?,?,?,?)");
            psp.setString(1, getName());
            psp.setString(2, getLastname());
            psp.setString(3,getJob());
            psp.setString(4,getCarBrand());
            psp.setString(5,getCarType());
            psp.setString(6,getPlateCode());
            psp.setString(7,getEmail());
            psp.setString(8,getPassword());
            psp.setInt(9,getGiris());
            psp.setInt(10,getCikis());
            psp.executeUpdate();
            
            PreparedStatement psp1=connection.prepareStatement("DELETE FROM INOTOPARK WHERE PLATECODE=?");
            psp1.setString(1, plaka);
            psp1.executeUpdate();
            
            PreparedStatement pr=connection.prepareStatement("UPDATE CUSTOMER SET INOTOPARK=0 WHERE PLATECODE=?");
            pr.setString(1, plaka);
            pr.executeUpdate();
            
            
            PreparedStatement ps2=connection.prepareStatement("SELECT NUM FROM CAPASITY WHERE CARTYPE=(SELECT CARTYPE FROM CUSTOMER WHERE PLATECODE=?)");
            ps2.setString(1, plaka);
            ResultSet rs2=ps2.executeQuery();
            rs2.next();
            num=rs2.getInt("NUM");
            
            num++;
            PreparedStatement ps3=connection.prepareStatement("UPDATE CAPASITY SET NUM=? WHERE CARTYPE=(SELECT CARTYPE FROM CUSTOMER WHERE PLATECODE=?)");
            ps3.setInt(1, num);
            ps3.setString(2, plaka);
            ps3.executeUpdate();
           
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return "admin.xhtml";
    }
    
    public int money;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
    
   
    
}

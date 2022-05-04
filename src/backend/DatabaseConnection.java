package backend;
import java.sql.*;
import java.util.ArrayList;

//CREATE USER 'healthadmin'@'localhost' IDENTIFIED BY 'healthadmin';
public class DatabaseConnection {
    String PASSWORD = "password";
    String USERNAME = "healthuser";
    String DATABASE = "healthsystem";
    Connection conn;

    public DatabaseConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DATABASE + "?useSSL=false", USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Admin CheckLogin(String usernameAuth, String sha256) {
        PreparedStatement auth;
        try {
            auth = conn.prepareStatement("select admin_id, first_name, last_name FROM administration WHERE username = ? and password_hash = ?");
            auth.setString(1, usernameAuth);
            auth.setString(2,sha256);
            ResultSet result = auth.executeQuery();
            if(result.next()) {
                Admin resultObj = new Admin();
                resultObj.login(result.getInt(1), result.getString(2), result.getString(3));
                return resultObj;
            }else {
                conn.close();
                return new Admin();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Admin();
    }

    public ArrayList<Insurance> GetInsuranceObjs(){
        ArrayList<Insurance> insurance = new ArrayList<Insurance>();
        PreparedStatement getIns;
        try{
            getIns = conn.prepareStatement("select * from insurance");
            ResultSet insuranceComp = getIns.executeQuery();
            while(insuranceComp.next()){
                insurance.add(new Insurance(insuranceComp.getString(2), insuranceComp.getString(3), insuranceComp.getString(4), insuranceComp.getInt(5), insuranceComp.getInt(6), insuranceComp.getInt(1)));
            }
            return insurance;
        }catch(SQLException e){

        }

        return null;
    }
    public ArrayList<Insurance> GetProviders(ArrayList<Insurance> ins){
        PreparedStatement getInsProv;
        try{
            getInsProv = conn.prepareStatement(
                    "SELECT p.provider_id, p.first_name, p.last_name, p.credential " +
                            "FROM providers p RIGHT JOIN provider_insurance pi " +
                            "ON p.provider_id = pi.provider_id RIGHT JOIN insurance i " +
                            "ON pi.insurance_id = i.insurance_id " +
                            "WHERE i.insurance_id = ?");
            for(int i=0; i<ins.size(); i++){
                getInsProv.setInt(1, ins.get(i).getInsuranceID());
                ResultSet rs = getInsProv.executeQuery();
                while(rs.next()){
                    ins.get(i).addProvider(new Providers(rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ins;
    }

    public boolean saveNewPatient(Patient pat, Admin admin){
        PreparedStatement saveNewPat;
        try{
            saveNewPat = conn.prepareStatement("INSERT INTO patients(first_name, last_name, date_of_birth, phone, email, ssn, admin_creator, balance) VALUES (?,?,?,?,?,?,?,?);");
            saveNewPat.setString(1, pat.getFname());
            saveNewPat.setString(2, pat.getLname());
            saveNewPat.setDate(3, new java.sql.Date(pat.dob.getTime()));
            saveNewPat.setString(4, pat.getPhone());
            saveNewPat.setString(5, pat.getEmail());
            saveNewPat.setString(6, pat.getSSN());
            saveNewPat.setInt(7, admin.admin_id);
            saveNewPat.setInt(8, 0);
            int rs = saveNewPat.executeUpdate();
            System.out.println("Successful!");
            conn.close();
            if(rs == 1){
                return true;
            }else{
                return false;
            }

        }catch(SQLException e){
            System.out.println("SQL ISSUE");
            e.printStackTrace();
        }
        return false;
    }


    public void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package backend;
import java.util.Date;

public class Patient {
    private String ssn = "", fname, lname, email, phone;
    int operator_creator;
    Date dob;
    Insurance ins;
    public Patient(Admin admin) {
        this.operator_creator = admin.admin_id;
    }

    public void newPatient() {
    }
    public String getSSN(){
        return ssn;
    }
    public void setSSN(String SSN){
        this.ssn = SSN;
    }
    public boolean ssnExists(){ return this.ssn != ""; }
    public void setInsurance(Insurance ins){ this.ins = ins; }
    public Insurance getInsurance(){ return ins; };
    public void setAttr(String fname, String lname, String email, String phone, Date dob){
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
    }
    public String getFname(){ return fname; }
    public String getLname(){ return lname; }
    public String getEmail(){ return email; }
    public String getPhone(){ return phone; }
}

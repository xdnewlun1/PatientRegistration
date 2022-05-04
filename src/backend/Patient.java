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

    public void printPatient(){
        System.out.println("Name: " + fname + " " + lname);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Date of Birth: " + dob);
        System.out.println("Insurance: " + ins.getName());
        System.out.println("SSN: " + ssn);
        System.out.println("Admin Operator ID: " + operator_creator);
    }
}

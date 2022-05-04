package frontend;

import java.security.NoSuchAlgorithmException;

import backend.Admin;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Admin user = new Admin();
        PatientReg reg = new PatientReg(user);
        //LoginWindow start = new LoginWindow(user);
    }
}

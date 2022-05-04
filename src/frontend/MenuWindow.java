package frontend;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

import javax.swing.*;

import backend.Admin;


public class MenuWindow {
    Admin admin;

    public MenuWindow(Admin admin) {
        this.admin = admin;
        if(admin.logged_in) {
            setupJFrame();
        }else {
            System.out.println("Error: User Got Logged Out!");
        }
    }

    public void setupJFrame() {
        //Create the JFrame
        JFrame menu = new JFrame("Home Menu");
        //JLabel for Title & User Name
        JLabel WelcomeL = new JLabel("Welcome, " + admin.first_name + " " + admin.last_name + "!");
        WelcomeL.setBounds(150, 50, 300, 25);
        WelcomeL.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        menu.add(WelcomeL);

        //Add the JButton for New Patient
        JButton patientBut = new JButton("Add New Patient");
        patientBut.setBounds(200, 100, 200, 25);
        menu.add(patientBut);

        //Add the JButton for Insurance Management
        JButton insuranceManage = new JButton("Manage Insurance");
        insuranceManage.setBounds(200, 150, 200, 25);
        menu.add(insuranceManage);

        //Add the JButton for Patient Management
        JButton patientManage = new JButton("Manage Patients");
        patientManage.setBounds(200, 200, 200, 25);
        menu.add(patientManage);

        //Add the JButton for Logout
        JButton logout = new JButton("Logout");
        logout.setBounds(200, 250, 200, 25);
        menu.add(logout);

        //Add the JButton for Exit
        JButton exit = new JButton("Exit");
        exit.setBounds(200, 300, 200, 25);
        menu.add(exit);

        //Set the JFrame Attributes
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setSize(600,400);
        menu.setLayout(null);
        menu.setVisible(true);

        //New Patient EventCreation
        patientBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menu.dispose();
                PatientReg registrate = new PatientReg(admin);
            }
        });

        //Logout EventCreation
        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menu.dispose();
                admin.logout();
                try {
                    LoginWindow login = new LoginWindow(admin);
                } catch (NoSuchAlgorithmException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //Exit EventCreation
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menu.dispose();
                admin = null;
                System.exit(0);
            }
        });
    }
}

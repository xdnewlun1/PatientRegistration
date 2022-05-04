package frontend;
import backend.Admin;
import backend.Authenticate;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import com.mkyong.crypto.hash.*;

import javax.swing.*;

public class LoginWindow {
    Admin admin;

    public LoginWindow(Admin admin) throws NoSuchAlgorithmException {
        this.admin = admin;
        setupJFrame();
    }

    public void setupJFrame() throws NoSuchAlgorithmException{
        //Create JFrame
        JFrame login = new JFrame("Patient Registration");

        //Add the Welcome JLabel
        JLabel welcome = new JLabel("Welcome, please log in!");
        welcome.setBounds(150, 50, 300, 25);
        welcome.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        login.add(welcome);

        //Add the JFrame Button for Login
        JButton submit = new JButton("Submit");
        submit.setBounds(250, 200, 100, 40);
        login.add(submit);

        //Add the JLabel for Username
        JLabel loginJL = new JLabel("Username");
        loginJL.setBounds(200, 75, 200, 25);
        login.add(loginJL);

        //Add the JFrame Text Box for Username
        JTextField loginTF = new JTextField();
        loginTF.setBounds(200, 100, 200, 25);
        login.add(loginTF);

        //Add the JLabel for Password
        JLabel passwordJL = new JLabel("Password");
        passwordJL.setBounds(200, 125, 200, 25);
        login.add(passwordJL);

        //Add the JFrame Text Box for Password
        JPasswordField passwordTF = new JPasswordField();
        passwordTF.setBounds(200, 150, 200, 25);
        login.add(passwordTF);

        //Add the JFrame Label for Failed Login, only shows if it fails
        JLabel failedLogin = new JLabel("<html> <font color='red'>Login Failed! Try Again!</font></html>");
        failedLogin.setBounds(235, 250, 200, 25);

        //Set the JFrame Attributes
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setSize(600,400);
        login.setLayout(null);
        login.setVisible(true);
        login.getRootPane().setDefaultButton(submit);

        //Create Event Listeners
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //String username = loginTF.getText();
                String username = "xdnewlun";
                char[] passwordChar = passwordTF.getPassword();
                //String passwordStr = new String(passwordChar);
                String passwordStr = "Jashon123";
                passwordChar = null;
                String EncryptedPassword = ShaUtils.getSha(passwordStr);
                passwordStr = null;
                Admin result = Authenticate.LoginAuthenticate(username, EncryptedPassword);
                if(result.logged_in) {
                    System.out.println(result.first_name + " " + result.last_name + " is logged in!");
                    admin = result;
                    login.dispose();
                    MenuWindow main = new MenuWindow(admin);
                }else {
                    login.add(failedLogin);
                    login.revalidate();
                    login.repaint();
                }
            }
        });
    }
}

package frontend;
import backend.*;
import org.apache.commons.lang3.StringUtils;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.util.Locale;

public class PatientReg {
    Patient newPat;
    Admin admin;
    JButton insuranceSel = null;
    JLabel curSelIns = null;
    Insurance SelectedIns;
    ArrayList<Insurance> ins;
    private int currentIndex;
    private JScrollPane scrollPane;
    JFrame reg, insSel;

    public PatientReg(Admin admin) {
        this.admin = admin;
        newPat = new Patient(admin);
        try {
            setupJFrame();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setupJFrame() throws ParseException {
        //Init the JFrame
        reg = new JFrame("New Patient Registration");

        //Window Title JLabel
        JLabel Welcome = new JLabel("New Patient Registration");
        Welcome.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        Welcome.setBounds(225, 75, 300, 50);
        reg.add(Welcome);

        //Back Button
        JButton backBut = new JButton("<-- Back");
        backBut.setBounds(10, 10, 100, 20);
        reg.add(backBut);

        //First Name Text Box
        JTextField fnameTF = new JTextField();
        fnameTF.setBounds(200, 150, 150, 25);
        reg.add(fnameTF);

        //First Name Label
        JLabel fnameL = new JLabel("First Name");
        fnameL.setBounds(200, 125, 150, 25);
        reg.add(fnameL);

        //Last Name Text Box
        JTextField lnameTF = new JTextField();
        lnameTF.setBounds(400, 150, 150, 25);
        reg.add(lnameTF);

        //Phone Text Box
        JLabel lnameL = new JLabel("Last Name");
        lnameL.setBounds(400, 125, 150, 25);
        reg.add(lnameL);

        //Email Text Box
        JTextField emailTF = new JTextField();
        emailTF.setBounds(200, 225, 350, 25);
        reg.add(emailTF);

        //Email Label
        JLabel emailL = new JLabel("Email");
        emailL.setBounds(200, 200, 100, 25);
        reg.add(emailL);

        //Email Not Valid Error Label
        JLabel emailWarnL = new JLabel("");
        emailWarnL.setBounds(200, 250, 300, 25);
        reg.add(emailWarnL);

        //SSN Text Box
        MaskFormatter ssnFormatter = new MaskFormatter("###-##-####");
        JFormattedTextField ssnField = new JFormattedTextField(ssnFormatter);
        ssnFormatter.setPlaceholderCharacter('#');
        ssnField.setBounds(200, 300, 100, 25);
        reg.add(ssnField);

        //SSN Label
        JLabel ssnL = new JLabel("SSN");
        ssnL.setBounds(200, 275, 100, 25);
        reg.add(ssnL);

        //SSN Warning Label
        JLabel ssnWarnL = new JLabel("<html><font color='red'>Not a Valid SSN</font></html>");
        ssnWarnL.setBounds(200, 325, 150, 25);

        //Phone Text Box
        MaskFormatter phoneFormatter = new MaskFormatter("(###) ###-####");
        JFormattedTextField phoneTF = new JFormattedTextField(phoneFormatter);
        phoneFormatter.setPlaceholderCharacter('#');
        phoneTF.setBounds(400, 300, 125, 25);
        reg.add(phoneTF);

        //Phone Label
        JLabel phoneL = new JLabel("Phone");
        phoneL.setBounds(400, 275, 100, 25);
        reg.add(phoneL);

        //Date of Birth Text Box
        MaskFormatter dobFormatter = new MaskFormatter("##/##/####");
        JFormattedTextField dob = new JFormattedTextField(dobFormatter);
        dobFormatter.setPlaceholderCharacter('*');
        dob.setBounds(200, 375, 125, 25);
        reg.add(dob);

        //Date of Birth Label
        JLabel dobL = new JLabel("Date of Birth (mm/dd/yyyy)");
        dobL.setBounds(200, 350, 200, 25);
        reg.add(dobL);

        //Date of Birth Error
        JLabel dobErrL = new JLabel("");
        dobErrL.setBounds(200, 400, 125, 25);
        reg.add(dobErrL);

        /* Insurance Selection Option
         *  Opens New window for selection with return for Ins Object
         *  Will get all Insurance Input, and make a list with Ins and Providers
         *  Button will disable once insurance is set; back is only way to re-enable
         */
        insuranceSel = new JButton("Insurance...");
        insuranceSel.setBounds(400, 375, 125, 25);
        reg.add(insuranceSel);

        //Submit Button (has action listener to check for completness)
        JButton submit = new JButton("Submit");
        submit.setBounds(200, 425, 325, 25);
        reg.add(submit);

        //JLabel for current Ins
        curSelIns = new JLabel();
        curSelIns.setBounds(400, 400, 300, 25);

        //Jlabel for Submite Warning
        JLabel notFilledError = new JLabel("");
        notFilledError.setBounds(200, 450, 300, 25);
        reg.add(notFilledError);

        //Setup JFrame Attributes
        reg.setSize(800, 600);
        reg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        reg.setLayout(null);
        reg.setResizable(false);
        reg.setVisible(true);

        //Email Event Handler to Ensure Correct Email
        emailTF.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                System.out.println("email fg");
            }

            public void focusLost(FocusEvent e) {
                if (emailTF.getText().contains("@")) {
                    if (emailWarnL.isVisible()) {
                        emailWarnL.setText("");
                        reg.revalidate();
                        reg.repaint();
                    }
                } else {
                    emailWarnL.setText("<html> <font color='red'>Not A Valid Email</font></html>");
                    reg.revalidate();
                    reg.repaint();
                }
            }
        });

        //Insurance event handler
        insuranceSel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setupInsJFrame();
            }
        });

        //Date of Birth Handler for Correct date
        dob.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            public void focusLost(FocusEvent e) {
                if (returnDate(dob.getText()) == null) {
                    dobErrL.setText("<html><font color='red'>Date of Birth is Invalid</font></html>");
                    reg.revalidate();
                    reg.repaint();
                } else {
                    dobErrL.setText("");
                    reg.revalidate();
                    reg.repaint();
                }
            }
        });

        //SSN Event Handler for Security
        ssnField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (!newPat.getSSN().equals("")) {
                    ssnField.setText(newPat.getSSN());
                }
            }

            public void focusLost(FocusEvent e) {
                if (cleanString(ssnField.getText()).length() == 9) {
                    newPat.setSSN(ssnField.getText());
                    ssnField.setText("");
                } else {
                    newPat.setSSN("");
                    reg.add(ssnWarnL);
                    reg.revalidate();
                    reg.repaint();
                }
            }
        });

        //Back Button
        backBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.dispose();
                MenuWindow menu = new MenuWindow(admin);
            }
        });

        //Submit Button
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String phone = cleanString(phoneTF.getText());
                Date confDOB = returnDate(dob.getText());
                if (notEmpty(fnameTF) && notEmpty(lnameTF) && notEmpty(emailTF) && emailWarnL.getText().equals("") && newPat.ssnExists() && !phone.equals("") && confDOB != null && newPat.getInsurance() != null) {
                    newPat.setAttr(fnameTF.getText(), lnameTF.getText(), emailTF.getText(), phone, confDOB);
                    newPat.printPatient();
                } else {
                    System.out.println("Failed");
                    notFilledError.setText("<html><font color='red'>Not All Fields Filled</font></html>");
                    reg.revalidate();
                    reg.repaint();
                }
            }
        });
    }

    public boolean notEmpty(JTextField text) {
        if (!text.getText().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public String cleanString(String str) {
        return str.replaceAll("[\\s()#-]", "");
    }

    public Date returnDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy", Locale.US);
        Date date = null;
        try {
            date = formatter.parse(str);
        } catch (ParseException e) {
        }
        return date;
    }

    /***********************************
     * This begins the Insurance Selection JFrame Popup
     *
     */
    public void getInsInfo(){
        DatabaseConnection conn = new DatabaseConnection();
        ArrayList<Insurance> ins = conn.GetInsuranceObjs();
        conn.GetProviders(ins);
        this.ins = ins;
        currentIndex = ins.size();
    }


    public void setupInsJFrame(){
        getInsInfo();
        insSel = new JFrame(); //Init JFrame
        ArrayList<JButton> selectInsur = new ArrayList<>();
        ArrayList<JLabel> selectInsurIdent = new ArrayList<>();
        Font Big = new Font(Font.SANS_SERIF, Font.BOLD, 24);

        //JLabel Insurance
        JLabel insuranceLabel = new JLabel("Known Insurance");
        insuranceLabel.setBounds(100, 200, 250, 25);
        insuranceLabel.setFont(Big);
        insSel.add(insuranceLabel);

        //JLabel Providers
        JLabel providersLabel = new JLabel("Providers");
        providersLabel.setBounds(400, 200, 250, 25);
        providersLabel.setFont(Big);
        insSel.add(providersLabel);

        //JLabel Insurance Info
        JLabel insurInfoLabel = new JLabel("Insurance Details");
        insurInfoLabel.setBounds(700, 200, 250, 25);
        insurInfoLabel.setFont(Big);
        insSel.add(insurInfoLabel);

        //JLabel Submit Button
        JButton submit = new JButton("Submit");
        submit.setBounds(400, 550, 550, 25);
        insSel.add(submit);

        //JList Init
        for (int i = (0); i < ins.size(); i++) {
            System.out.println(i);
            selectInsur.add(new JButton(ins.get(i).getName()));
            selectInsur.get((i)).setBounds(100, (250 + 50 * (i)), 250, 25);
            selectInsurIdent.add(new JLabel(""));
            selectInsurIdent.get((i)).setBounds(75, (250 + 50 * (i)), 25, 25);
            selectInsur.get((i)).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Object source = e.getSource();
                    JButton sourceBut = (JButton) source;
                    String buttonName = sourceBut.getText();
                    if (currentIndex != ins.size()) {
                        selectInsurIdent.get(currentIndex).setText("");
                    }
                    int buttonIndex = 0;
                    for (int i = 0; i < ins.size(); i++) {
                        if (ins.get(i).getName().equals(buttonName)) {
                            buttonIndex = i;
                            currentIndex = i;
                            addProviders(buttonIndex, insSel);
                            break;
                        }
                    }
                    selectInsurIdent.get(currentIndex).setText(">>>");
                }
            });
            insSel.add(selectInsur.get(i));
            insSel.add(selectInsurIdent.get(i));
        }

        //Submit Button
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //First Check if Insurance is Selected
                if(currentIndex != ins.size()){
                    newPat.setInsurance(ins.get(currentIndex));
                    insSel.dispose();
                    curSelIns.setText("Selected: " + newPat.getInsurance().getName());
                    reg.add(curSelIns);
                    reg.revalidate();
                    reg.repaint();
                }
            }
        });

        //JFrame Attributes
        insSel.setSize(1000, 800);
        insSel.setResizable(true);
        insSel.setLayout(null);
        insSel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        insSel.setVisible(true);
    }

    public void addProviders(int selInd, JFrame insSel){
        JTextArea providers = new JTextArea();
        ArrayList<Providers> provideList = ins.get(selInd).getProviders();
        for(int i=0;i<provideList.size();i++){
            providers.setText(providers.getText() + provideList.get(i).getName() + "\n");
        }
        providers.setBounds(400, 250, 250, 250);
        providers.setEditable(false);
        JScrollPane pane = new JScrollPane();
        pane.getViewport().setView(providers);
        pane.setBounds(400, 250, 250, 250);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        insSel.add(pane);
        addInformation(selInd, insSel);
    }
    public void addInformation(int selInd, JFrame insSel){
        JTextArea insInfo = new JTextArea();
        Insurance selIns = ins.get(selInd);
        String[] infoToAdd = new String[]{
                "Name: ", selIns.getName() + "\n",
                "Ins ID: ", selIns.getInsuranceID() + "\n",
                "Phone: ", selIns.getPhone() + "\n",
                "Address: ", selIns.getAddress() + "\n",
                "Copay: ", selIns.getCopay() + "\n",
                "Deductible: ", selIns.getDeductable() + "\n"
        };
        for(int i=0;i<infoToAdd.length;i++){
            String working = infoToAdd[i];
            if(i % 2 == 0){
                working = StringUtils.rightPad(infoToAdd[i], 15, " ");
            }
            insInfo.setText(insInfo.getText() + working);
        }
        insInfo.setBounds(700, 250, 250, 250);
        insInfo.setEditable(false);
        JScrollPane pane = new JScrollPane();
        pane.getViewport().setView(insInfo);
        pane.setBounds(700, 250, 250, 250);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        insSel.add(pane);
    }
}

/*
TODO: Add Back Button EventHandler
TextFields:
    -First Name
    -Last Name
    -Phone
    -Email
    -SSN (Make Password-esqu)
Date Input:
    -Date of Birth
        -JFormattedTextField
            -new Date
Insurance Input Box
 */
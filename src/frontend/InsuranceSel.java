package frontend;
import backend.Admin;
import backend.DatabaseConnection;
import backend.Insurance;
import backend.Providers;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InsuranceSel {
    private JScrollPane scrollPane;
    private ArrayList<Insurance> ins;
    private int currentIndex;

    public InsuranceSel(Admin admin) {
        //TODO: Need to connect and pull all Insurance comapanies and entries in P_I to get provider id's
        //Will return ArrayList of Insurance
        DatabaseConnection db = new DatabaseConnection();
        ArrayList<Insurance> ins = db.GetInsuranceObjs();
        db.GetProviders(ins);
        this.ins = ins;
        currentIndex = ins.size();
        setupJFrame();

    }


    public void setupJFrame(){
        JFrame insSel = new JFrame(); //Init JFrame
        ArrayList<JButton> selectInsur = new ArrayList<>();
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
        for(int i=0;i<ins.size();i++){
            selectInsur.add(new JButton(ins.get(i).getName()));
            selectInsur.get(i).setBounds(100, (250+50*i), 250, 25);
            selectInsur.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Object source = e.getSource();
                    JButton sourceBut = (JButton) source;
                    String buttonName = sourceBut.getText();
                    int buttonIndex = 0;
                    for(int i=0;i<ins.size();i++){
                        if(ins.get(i).getName().equals(buttonName)){
                            buttonIndex = i;
                            currentIndex = i;
                            addProviders(buttonIndex, insSel);
                            break;
                        }
                    }
                }
            });
                    insSel.add(selectInsur.get(i));
        }

        //Submit Button
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //First Check if Insurance is Selected
                if(currentIndex != ins.size()){
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
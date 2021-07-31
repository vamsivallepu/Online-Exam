import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

class StudentLogin extends JFrame implements ActionListener {

    Container container = getContentPane();
    JLabel rollNo = new JLabel("ROLL NUMBER");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField rollTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton resetButton = new JButton("RESET");
    JCheckBox showPassword = new JCheckBox("Show Password");

    StudentLogin() {
        setTitle("Student Login");
        setSize(370, 500);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        container.setLayout(null);
        container.setBackground(new Color(205, 214, 221));

        rollNo.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 210, 100, 30);
        rollTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 210, 150, 30);
        showPassword.setBounds(150, 255, 150, 30);
        loginButton.setBounds(50, 300, 100, 30);
        resetButton.setBounds(200, 300, 100, 30);

        container.add(rollNo);
        container.add(passwordLabel);
        container.add(rollTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);

        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);

        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Coding Part of LOGIN button
        if (e.getSource() == loginButton) {
            Connection conn=null;
            String roll;
            String pwdText;
            roll = rollTextField.getText();
            pwdText = passwordField.getText();
            ResultSet res = null;
            try {
                conn = DriverManager
                        .getConnection("jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6428491","sql6428491","k6GmYjpDhE");
                PreparedStatement statement = conn
                        .prepareStatement("select name, rollNo, year, branch, password from student where rollNo =?");
                statement.setString(1, roll);
                res = statement.executeQuery();
                String name=null, rollNo=null, year=null, branch=null, pass=null;
                while(res.next()){
                    name = res.getString("name");
                    rollNo = res.getString("rollNo");
                    year = res.getString("year");
                    branch = res.getString("branch");
                    pass = res.getString("password");
//                System.out.println(username + " " + pass);
                }
                if (roll.equalsIgnoreCase(rollNo) && pwdText.equalsIgnoreCase(pass)) {
//                    System.out.println("Login Successful");
                    new StudentPanel(rollNo, name, branch, year);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Username or Password");
                }
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            finally {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        }
        // Coding Part of RESET button
        if (e.getSource() == resetButton) {
            rollTextField.setText("");
            passwordField.setText("");
        }
        // Coding Part of showPassword JCheckBox
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }

        }
    }

    public static void main(String[] args) {
        new StudentLogin();
    }

}

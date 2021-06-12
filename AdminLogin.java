import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

class AdminLogin extends JFrame implements ActionListener {

    Container container = getContentPane();
    JLabel userLabel = new JLabel("USERNAME");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton resetButton = new JButton("RESET");
    JCheckBox showPassword = new JCheckBox("Show Password");

    AdminLogin() {
        setTitle("AdminLogin");
        setSize(370, 500);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        container.setLayout(null);
        container.setBackground(new Color(205, 214, 221));

        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 255, 150, 30);
        loginButton.setBounds(50, 300, 100, 30);
        resetButton.setBounds(200, 300, 100, 30);

        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
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
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = passwordField.getText();
            ResultSet res = null;
            try {
                conn = DriverManager
                        .getConnection("jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6417854","sql6417854","sWNAI8YF3C");
                PreparedStatement statement = conn
                        .prepareStatement("select name, username, password, email from admin where username =?");
                statement.setString(1, userText);
                res = statement.executeQuery();
                String name=null, username = null, pass=null, email=null;
                while(res.next()){
                    name = res.getString("name");
                    username = res.getString("username");
                    pass = res.getString("password");
                    email = res.getString("email");
                }
//                System.out.println(username + " " + pass);
                if (userText.equalsIgnoreCase(username) && pwdText.equalsIgnoreCase(pass)) {
                    new AdminPanel(name, username, email).setStudent();
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
            userTextField.setText("");
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
        new AdminLogin();
    }

}

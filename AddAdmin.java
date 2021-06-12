import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddAdmin extends JFrame implements ActionListener {
    Container container = getContentPane();
    JLabel name = new JLabel("NAME");
    JLabel email = new JLabel("EMAIL");
    JLabel username = new JLabel("USERNAME");
    JLabel password = new JLabel("PASSWORD");
    JTextField nameTextField = new JTextField();
    JTextField emailTextField = new JTextField();
    JTextField usernameTextField = new JTextField();
    JPasswordField passwordTextField = new JPasswordField();
    JButton submit = new JButton("SUBMIT");
    JButton resetButton = new JButton("RESET");
    JCheckBox showPassword = new JCheckBox("Show Password");
    public AddAdmin(){
        setTitle("Add Admin");
        setSize(380, 500);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        container.setLayout(null);


        name.setBounds(50, 100, 100, 30);
        nameTextField.setBounds(150, 100, 150, 30);
        email.setBounds(50, 150, 100, 30);
        emailTextField.setBounds(150, 150, 150, 30);
        username.setBounds(50,200,100,30);
        usernameTextField.setBounds(150, 200, 150, 30);
        password.setBounds(50, 250, 100, 30);
        passwordTextField.setBounds(150, 250, 150, 30);
        showPassword.setBounds(150, 300, 150, 30);
        submit.setBounds(50, 350, 100, 30);
        resetButton.setBounds(200, 350, 100, 30);

        container.add(name);
        container.add(email);
        container.add(nameTextField);
        container.add(emailTextField);
        container.add(username);
        container.add(usernameTextField);
        container.add(password);
        container.add(passwordTextField);
        container.add(submit);
        container.add(resetButton);
        container.add(showPassword);

        submit.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            try {
                Connection conn = DriverManager
                        .getConnection("jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6417854","sql6417854","sWNAI8YF3C");
                PreparedStatement statement = conn
                        .prepareStatement("insert into admin values(?,?,?,?);");
                statement.setString(1, nameTextField.getText());
                statement.setString(2, usernameTextField.getText());
                statement.setString(3, passwordTextField.getText());
                statement.setString(4, emailTextField.getText());
                statement.executeUpdate();
                conn.close();
                this.dispose();
                JOptionPane.showMessageDialog(this, "Admin Added Successfully");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (e.getSource() == resetButton) {
            nameTextField.setText("");
            emailTextField.setText("");
            usernameTextField.setText("");
            passwordTextField.setText("");
        }
        // Coding Part of showPassword JCheckBox
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordTextField.setEchoChar((char) 0);
            } else {
                passwordTextField.setEchoChar('*');
            }

        }
    }

    public static void main(String[] args) {
        new AddAdmin();
    }
}

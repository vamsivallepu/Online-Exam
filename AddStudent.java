import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
import javax.xml.transform.Result;

public class AddStudent extends JFrame implements ActionListener {
	Container container = getContentPane();
	JLabel name = new JLabel("NAME");
	JLabel rollNo = new JLabel("ROLL NO.");
	JLabel year = new JLabel("YEAR");
	JLabel branch = new JLabel("BRANCH");
	JLabel userName = new JLabel("USER NAME");
	JLabel passwordLabel = new JLabel("PASSWORD");
	JTextField nameTextField = new JTextField();
	JTextField rollNoTextField = new JTextField();
	String years[]={"1", "2", "3", "4"};
	JComboBox yearTextField = new JComboBox(years);
	String branches[]={"CIVIL", "CSE", "ECE", "EEE", "IT", "MECH"};
	JComboBox branchTextField=new JComboBox(branches);
	JTextField userNameTextField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JButton submit = new JButton("SUBMIT");
	JButton resetButton = new JButton("RESET");
	JCheckBox showPassword = new JCheckBox("Show Password");
	AdminPanel admin;
	int i=0;
	AddStudent(AdminPanel admin) {
		this.admin=admin;
		setTitle("Add Student");
		setSize(370, 600);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		container.setLayout(null);

		name.setBounds(50, 100, 100, 30);
		nameTextField.setBounds(150, 100, 150, 30);
		rollNo.setBounds(50, 150, 100, 30);
		rollNoTextField.setBounds(150, 150, 150, 30);
		year.setBounds(50,200,100,30);
		yearTextField.setBounds(150, 200, 150, 30);
		branch.setBounds(50, 250, 100, 30);
		branchTextField.setBounds(150, 250, 150, 30);
//		userName.setBounds(50, 300, 100, 30);
//		userNameTextField.setBounds(150, 300, 150, 30);
		passwordLabel.setBounds(50, 300, 150, 30);
		passwordField.setBounds(150, 300, 150, 30);
		showPassword.setBounds(150, 350, 150, 30);
		submit.setBounds(50, 400, 100, 30);
		resetButton.setBounds(200, 400, 100, 30);

		container.add(name);
		container.add(rollNo);
		container.add(nameTextField);
		container.add(rollNoTextField);
		container.add(year);
		container.add(yearTextField);
		container.add(branch);
		container.add(branchTextField);
//		container.add(userName);
//		container.add(userNameTextField);
		container.add(passwordLabel);
		container.add(passwordField);
		container.add(showPassword);
		container.add(submit);
		container.add(resetButton);

		submit.addActionListener(this);
		resetButton.addActionListener(this);
		showPassword.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// TODO Auto-generated method stub
		if(e.getSource()==submit){
//			System.out.println(Integer.parseInt((String) yearTextField.getSelectedItem()));
			try{
				Connection conn= DriverManager
						.getConnection("jdbc:sqlite:/C:\\Users\\vamsivallepu\\Downloads\\Micro-Project-master\\Quiz\\src\\test.db");
				PreparedStatement statement = conn
						.prepareStatement("insert into students(name, rollNo, year, branch, password) values(?,?,?,?,?);");
				statement.setString(1, nameTextField.getText());
				statement.setString(2,rollNoTextField.getText());
				statement.setInt(3, Integer.parseInt((String) yearTextField.getSelectedItem()));
				statement.setString(4, (String) branchTextField.getSelectedItem());
//				statement.setString(5, userNameTextField.getText());
				statement.setString(5, passwordField.getText());
				statement.executeUpdate();
				conn.close();
				admin.setStudent();
				this.dispose();
				JOptionPane.showMessageDialog(this, "Student Added Successfully");
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}

		}
		if (e.getSource() == resetButton) {
			nameTextField.setText("");
			rollNoTextField.setText("");
			yearTextField.setSelectedIndex(0);
			branchTextField.setSelectedIndex(0);
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
}

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class AdminPanel implements ActionListener {
	JFrame frame = new JFrame("Administrator");
	JPanel AdminInfo;
	String name, username, email;
	Connection conn;
	JPanel dataPanel;
	JPanel studentPanel;
	DefaultTableModel tableModel;
	JTable table;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem addStudent,addAdmin, close;
	JButton test;
	int row=0;

	public AdminPanel(){

	}
	public AdminPanel(String name, String username, String email) {
		AdminInfo = new JPanel();
		dataPanel = new JPanel();
		studentPanel = new JPanel();
		addStudent=new JMenuItem("Add Student");
		addAdmin=new JMenuItem("Add Admin");
		close=new JMenuItem("Close");
		menu = new JMenu("Options");
		menuBar= new JMenuBar();

		menu.setFont(new Font("Times New Roman", Font.BOLD, 18));
		addStudent.setFont(new Font("Consolas", Font.PLAIN, 15));
		addAdmin.setFont(new Font("Consolas", Font.PLAIN, 15));
		close.setFont(new Font("Consolas", Font.PLAIN, 15));

		menu.add(addStudent);
		menu.add(addAdmin);
		menu.add(close);
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);

		addStudent.addActionListener(this);
		addAdmin.addActionListener(this);
		close.addActionListener(this);



		table = new JTable();
		tableModel = new DefaultTableModel() {

			@Override
			public boolean isCellEditable(int row, int column) {
				//all cells false
				return false;
			}
		};
		table.setModel(tableModel);
		table.setFont(new Font("Sans Serif", Font.BOLD, 18));
		table.setRowHeight(25);
		tableModel.addColumn("Roll no.");
		tableModel.addColumn("Name");
		tableModel.addColumn("Year");
		tableModel.addColumn("Branch");
		tableModel.addColumn("Marks");
//		tableModel.insertRow(0, new Object[] { "Roll No.","Name", , "CSE", 10});
//		tableModel.insertRow(0, new Object[] { "19BQ1A05N5", "Vamsi Krishna", 1, "CSE", 10 });

		this.name = name;
		this.username = username;
		this.email = email;

		AdminInfo.setLayout(new FlowLayout());
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(4, 1));

		JLabel nm = new JLabel("Name      : " + name);
		nm.setFont(new Font("Consolas", Font.BOLD, 20));

		JLabel userNm = new JLabel("User Name : " + username);
		userNm.setFont(new Font("Consolas", Font.BOLD, 20));

		JLabel mailId = new JLabel("Email     : " + email);
		mailId.setFont(new Font("Consolas", Font.BOLD, 20));

		test = new JButton("New test");
		test.setFont(new Font("consolas", Font.BOLD, 20));
		test.addActionListener(this);

		dataPanel.add(nm);
		dataPanel.add(userNm);
		dataPanel.add(mailId);
		dataPanel.add(test);
		AdminInfo.add(dataPanel);

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(AdminInfo, BorderLayout.NORTH);
		studentPanel.setLayout(new BorderLayout());
		JLabel tle = new JLabel("Student Details");
		tle.setFont(new Font("San Serif", Font.BOLD, 20));
//		tle.setBounds(700, 0, 1536, 20);
		studentPanel.add(tle,BorderLayout.NORTH);
		studentPanel.add(new JScrollPane(table), BorderLayout.CENTER);
//		dataPanel.setBackground(new Color(93, 93, 129));
		frame.add(studentPanel);
//		frame.setSize(500, 300);
		frame.setVisible(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	public void setStudent(){
		try {
			while(tableModel.getRowCount() > 0)
			{
				tableModel.removeRow(0);
			}
			row=0;
			Connection conn= DriverManager
					.getConnection("jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6428491","sql6428491","k6GmYjpDhE");
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select rollNo, name, year, branch, marks from student order by rollNo");
			while(rs.next()){
				tableModel.insertRow(row++, new Object[] { rs.getString("rollNo"), rs.getString("name"), rs.getString("year"), rs.getString("branch"), rs.getInt("marks") });

			}

			conn.close();
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==addStudent){
			new AddStudent(this);
		}
		else if(e.getSource()==addAdmin){
			new AddAdmin();
		}
		else if(e.getSource()==close){
			frame.dispose();
		}
		else if(e.getSource()==test){
			try {
				clearTable();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
			new AddQuestion();
		}

	}

	private void clearTable() throws SQLException {
		Connection conn= DriverManager
				.getConnection("jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6428491","sql6428491","k6GmYjpDhE");
		Statement stmt=conn.createStatement();
		stmt.executeUpdate("delete from questions");

		Statement stmt2=conn.createStatement();
		stmt.executeUpdate("update student set marks=NULL, attempted=NULL");
		conn.close();
	}

	public static void main(String[] args) {
		new AdminPanel("Vamsi Krishna", "vamsi99", "vallepu670@gmail.com").setStudent();
	}

}


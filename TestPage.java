import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TestPage implements ActionListener{
	JFrame frame= new JFrame("Test Page");
	JPanel details;
	Connection conn;
	JPanel main;
	JButton next;
	String rollno;
	int res,n;
	StudentPanel student;
	ArrayList<String> id, question, option1, option2, option3, option4, ans;
	Iterator idi, it, o1, o2, o3, o4, ai;
	JTextArea quest;
	JRadioButton opt1, opt2, opt3, opt4;
	ButtonGroup bg;
	public  TestPage(JPanel details, int r, String rollNo, StudentPanel student) throws SQLException {
		Container pane=frame.getContentPane();
//		conn= DriverManager.getConnection("jdbc:sqlite:/C:\\Users\\vamsivallepu\\Downloads\\Micro-Project-master\\Quiz\\src\\test.db");
		pane.setLayout(null);
		this.details=details;
		this.rollno=rollNo;
		this.student=student;
		id=new ArrayList<>();
		question=new ArrayList<>();
		option1=new ArrayList<>();
		option2=new ArrayList<>();
		option3=new ArrayList<>();
		option4=new ArrayList<>();
		ans=new ArrayList<>();

		res=0;
		details.setBounds(20,20, 400, 100);
		Timer timer=new Timer();
		TimerTask task=new TimerTask() {
			int seconds=59;
			int minutes=r-1;
			JLabel tmr=new JLabel();
			@Override
			public void run() {
				if(minutes==0 && seconds==0){
					tmr.setText("Time Out");
					timer.cancel();
					frame.dispose();
					JOptionPane.showMessageDialog(frame, "Time Out !!"+"\n"+"Your Score is :"+String.valueOf(res));
					PreparedStatement statement=null;
					try {

						conn= DriverManager.getConnection("jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6428491","sql6428491","k6GmYjpDhE");
						statement = conn
								.prepareStatement("update student set marks=?, attempted=? where rollNo=?");
						statement.setString(1, String.valueOf(res));
						statement.setString(2, "yes");
						statement.setString(3, rollno);
						statement.executeUpdate();
					} catch (SQLException throwables) {
						throwables.printStackTrace();
					} finally {
						try {
							conn.close();
//							statement.close();
						} catch (SQLException throwables) {
							throwables.printStackTrace();
						}

					}
				}
				if (seconds==0){
					minutes--;
					seconds=59;
				}
				else{
//					System.out.println(seconds+" seconds");
					tmr.setText("Time left - "+String.format("%02d", minutes)+" : "+String.format("%02d", seconds));
					seconds--;
				}
				tmr.setBounds(1275, 30, 250, 70);
				tmr.setFont(new Font("sans serif", Font.BOLD, 25));
				pane.add(tmr);
				SwingUtilities.updateComponentTreeUI(frame);
			}
		};
		timer.scheduleAtFixedRate(task, 0, 1000);

		conn= DriverManager.getConnection("jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6428491","sql6428491","k6GmYjpDhE");
		Statement statement=conn.createStatement();
		ResultSet rs=statement.executeQuery("select * from questions");
		while(rs.next()){
			id.add(rs.getString("id"));
			question.add(rs.getString("question"));
			option1.add(rs.getString("opt1"));
			option2.add(rs.getString("opt2"));
			option3.add(rs.getString("opt3"));
			option4.add(rs.getString("opt4"));
			ans.add(rs.getString("answer"));
		}
		conn.close();
		statement.close();
		rs.close();

		idi=id.iterator();
		it=question.iterator();
		o1=option1.iterator();
		o4=option4.iterator();
		o2=option2.iterator();
		o3=option3.iterator();
		ai=ans.iterator();

		n=question.size();
		main=new JPanel();
		main.setLayout(null);
		quest= new JTextArea(3,1);
		bg=new ButtonGroup();
		opt1=new JRadioButton();
		opt2=new JRadioButton();
		opt3=new JRadioButton();
		opt4=new JRadioButton();
		bg.add(opt1);
		bg.add(opt2);
		bg.add(opt3);
		bg.add(opt4);
		quest.setFont(new Font("Consolas", Font.BOLD, 33));
		opt1.setFont(new Font("Consolas", Font.BOLD, 30));
		opt2.setFont(new Font("Consolas", Font.BOLD, 30));
		opt3.setFont(new Font("Consolas", Font.BOLD, 30));
		opt4.setFont(new Font("Consolas", Font.BOLD, 30));

		quest.setBounds(30,20, 1490, 100);
		opt1.setBounds(30, 120, 500, 40);
		opt2.setBounds(550, 120, 500, 40);
		opt3.setBounds(30, 190, 500, 40);
		opt4.setBounds(550, 190, 500, 40);

		quest.setLineWrap(true);
		quest.setWrapStyleWord(true);
		quest.setEditable(false);

		quest.setBackground(new Color(72, 169, 166));
		opt1.setBackground(new Color(72, 169, 166));
		opt2.setBackground(new Color(72, 169, 166));
		opt3.setBackground(new Color(72, 169, 166));
		opt4.setBackground(new Color(72, 169, 166));
//		System.out.println(it.next());

		nextQuestion();
		n--;

		main.setBounds(20, 275, 1500, 300);
		main.setBackground(new Color(72, 169, 166));
		main.setBorder(new LineBorder(Color.black, 4));

		next=new JButton("NEXT-->");
		next.setFont(new Font("Consolas", Font.ITALIC, 25));
		next.setBackground(Color.PINK);
		next.setBounds(1000, 600, 130, 50);
		next.addActionListener(this);
		pane.add(details);
		pane.add(main);
		pane.add(next);
		frame.setAlwaysOnTop(true);

		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.setSize(new Dimension(1920, 1080));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		frame.setVisible(true);
	}
	void nextQuestion(){
		quest.setText(String.valueOf(idi.next())+". "+(String) it.next());
		main.add(quest);

		opt1.setText((String) o1.next());
		main.add(opt1);

		opt2.setText((String) o2.next());
		main.add(opt2);

		opt3.setText((String) o3.next());
		main.add(opt3);

		opt4.setText((String) o4.next());
		main.add(opt4);

	}
	private void submit() throws SQLException {
		frame.dispose();
		JOptionPane.showMessageDialog(frame, "Test Submitted Successfully."+"\n"+"YOUR SCORE :"+String.valueOf(res));
		Connection co= DriverManager.getConnection("jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6428491","sql6428491","k6GmYjpDhE");
		PreparedStatement statement = co
				.prepareStatement("update student set marks=?, attempted=? where rollNo=?");
		statement.setString(1, String.valueOf(res));
		statement.setString(2, "yes");
		statement.setString(3, rollno);
		statement.executeUpdate();
        student.updateStatus(res);
        co.close();
        statement.close();

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==next){
			String ca= (String) ai.next();
//			System.out.println(ca.equals(opt1.getText()));
			if(opt1.isSelected() && ca.equals(opt1.getText()) || opt2.isSelected() && ca.equals(opt2.getText()) || opt3.isSelected() && ca.equals(opt3.getText()) || opt4.isSelected() && ca.equals(opt4.getText()))
				res++;
			bg.clearSelection();
			if(n>1){
				nextQuestion();
				n--;
			}
			else if(n==1){
				nextQuestion();
				next.setText("Submit");
				n--;
			}
			else{
				try {
					submit();
				} catch (SQLException throwable) {
					throwable.printStackTrace();
				}
			}
		}

	}
}
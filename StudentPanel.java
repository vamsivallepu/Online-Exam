import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class StudentPanel implements ActionListener {
    JFrame frame = new JFrame("Student Dasboard");
    JPanel details;
    JPanel exam;
    String name, rollNo, branch, year;
    static String status;
    JButton logout, start;
    int noOfrows;
    JLabel state;
    Connection conn;
    Image img = Toolkit.getDefaultToolkit().getImage("C:\\Users\\vamsivallepu\\Downloads\\exam3.jpg");
    public StudentPanel(String rollNo, String name, String branch, String year) throws SQLException {
        frame.setContentPane(new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0,frame.getWidth(), frame.getHeight(), null);
            }
        });
//        frame.setContentPane(new JLabel(new ImageIcon("C:\\Users\\vamsivallepu\\Downloads\\exam2.jpg")));
//        pane.setBackground(Color.gray);
        Container pane=frame.getContentPane();
        pane.setLayout(null);
        this.name=name;
        this.rollNo=rollNo;
        this.branch=branch;
        this.year=year;

        details= new JPanel();
        JLabel rn = new JLabel("  Roll No. : " + rollNo);
        rn.setFont(new Font("Consolas", Font.BOLD, 20));

        JLabel nm = new JLabel("  Name     : " + name);
        nm.setFont(new Font("Consolas", Font.BOLD, 20));

        JLabel brnc = new JLabel("  Branch   : " + branch);
        brnc.setFont(new Font("Consolas", Font.BOLD, 20));

        JLabel yr = new JLabel("  Year     : " + year);
        yr.setFont(new Font("Consolas", Font.BOLD, 20));

        details.setBounds(20, 20, 400,100);
        details.setLayout(new GridLayout(4,1));
        Border border0= BorderFactory.createDashedBorder(null);
        details.setBorder(border0);
        details.setOpaque(false);

        details.add(rn);
        details.add(nm);
        details.add(brnc);
        details.add(yr);

        logout = new JButton("Logout");
//        .setBorder(emptyBorder);
        logout.setBackground(Color.red);
        logout.setForeground(Color.white);
        logout.setFocusPainted(false);
        logout.setFont(new Font("Consolas", Font.PLAIN, 18));
        logout.setActionCommand("logout");
        logout.addActionListener(this);

        logout.setBounds(1400, 30, 100,35);

        JLabel title = new JLabel("YOUR DASHBOARD");
        title.setForeground(new Color(20, 54, 66));
        title.setFont(new Font("sans serif", Font.PLAIN, 50));
        title.setBounds(570, 30, 600, 70);

        conn= DriverManager.getConnection("jdbc:sqlite:/C:\\Users\\vamsivallepu\\Downloads\\Micro-Project-master\\Quiz\\src\\test.db");
        Statement statement=conn.createStatement();
        ResultSet rs=statement.executeQuery("select * from questions");
        if(!rs.next()){
            JLabel noTest=new JLabel("❌ NO TEST AVAILABLE RIGHT NOW ❌");
            JLabel noTest1 = new JLabel("PLEASE CHECK BACK LATER.");
            noTest.setFont(new Font("Monospace", Font.PLAIN, 30));
            noTest.setBounds(520,710, 1000, 60);
            noTest.setForeground(Color.red);
            pane.add(noTest);
            noTest1.setFont(new Font("Monospace", Font.PLAIN, 30));
            noTest1.setBounds(590,760, 1000, 60);
            pane.add(noTest1);
//            rs.beforeFirst();
        }
        else{
            rs=statement.executeQuery("select * from questions");
            noOfrows=0;
            while(rs.next()){
                noOfrows++;
            }
            rs.close();
            statement.close();
            conn.close();

            conn= DriverManager.getConnection("jdbc:sqlite:/C:\\Users\\vamsivallepu\\Downloads\\Micro-Project-master\\Quiz\\src\\test.db");
            PreparedStatement stmt= conn.prepareStatement("select marks, attempted from students where rollNo=?");
            stmt.setString(1, rollNo);
            ResultSet result=stmt.executeQuery();
            String isAttempted="no";
            isAttempted=result.getString("attempted");
            int marks=result.getInt("marks");
            conn.close();
            stmt.close();
            result.close();
            start=new JButton();
            if(Objects.isNull(isAttempted)){
                status = "Not Attempted";
                start.setText("START EXAM");
                start.setBackground(new Color(32, 191, 85));
                start.addActionListener(this);
            }
            else if(isAttempted.equals("yes")){
                status=String.valueOf(marks)+" marks";
                start.setText("Exam Over");
                start.setBackground(Color.black);
                start.setEnabled(false);
            }

            Border border= BorderFactory.createDashedBorder(null);
            exam=new JPanel();
            exam.setBorder(border);
            JLabel tle=new JLabel("  Multiple Choice Test");
            JLabel nq=new JLabel("  No. of Questions : "+String.valueOf(noOfrows));
            JLabel time=new JLabel("  Test Time        : "+String.valueOf(noOfrows)+" minutes.");

            state = new JLabel("  Status           : "+status);

            tle.setFont(new Font("consolas", Font.BOLD, 40));
//            tle.setBackground(Color.white);
            nq.setFont(new Font("consolas", Font.PLAIN, 25));
            time.setFont(new Font("consolas", Font.PLAIN, 25));
            state.setFont(new Font("consolas", Font.PLAIN, 25));
            start.setFont(new Font("monospace", Font.CENTER_BASELINE, 30));

            JLabel atb=new JLabel("✨ALL THE BEST👍");
            atb.setFont(new Font("Roboto Mono", Font.HANGING_BASELINE, 40));
            atb.setBounds(670, 900, 600,70);
            atb.setForeground(Color.blue);
            pane.add(atb);

            exam.setLayout(new GridLayout(5,1));
            exam.add(tle);
            exam.add(nq);
            exam.add(time);
            exam.add(state);
            exam.add(start);

            exam.setBackground(new Color(255,255,255));
//            exam.setOpaque(false);
            exam.setBounds(525,500, 520, 260);
            pane.add(exam);
        }

        pane.add(logout);
        pane.add(details);
        pane.add(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(new Dimension(1920, 1080));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
     void updateStatus(int marks){
        status=String.valueOf(marks)+" marks";
        state.setText("  Status           : "+status);
        exam.remove(state);
        exam.add(state, 3);
        start.setText("Exam Over");
        start.setBackground(Color.black);
        start.setEnabled(false);
        SwingUtilities.updateComponentTreeUI(exam);
    }

//    public static void main(String[] args) throws SQLException {
//        new StudentPanel("19BQ1A05N5", "VAMSI KRISHNA", "CSE", "2");
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==logout){
            frame.dispose();
            JOptionPane.showMessageDialog(frame, "Logged Out Successfully ✅");
        }
        if(e.getSource()==start){
            try {
                new TestPage(details, noOfrows, rollNo, this);
//                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}



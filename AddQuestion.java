import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddQuestion implements ActionListener {
    JFrame frame=new JFrame("Add Question");
    JLabel question, option1, option2, option3, option4, answer;
    JTextField opt1Field, opt2Field, opt3Field, opt4Field, ansField;
    JButton ques;
    Question q;
    JButton done, addAnother;
    Connection conn;
    int id;
    public AddQuestion(){
        Container pane=frame.getContentPane();
        pane.setLayout(null);

        question = new JLabel("Question   : ");
        option1 = new JLabel("Option 1   : ");
        option2 = new JLabel("Option 2   : ");
        option3 = new JLabel("Option 3   : ");
        option4 = new JLabel("Option 4   : ");
        answer =  new JLabel("Answer     : ");

        ques = new JButton("Add Question");
        opt1Field = new JTextField();
        opt2Field = new JTextField();
        opt3Field = new JTextField();
        opt4Field = new JTextField();
        ansField = new JTextField();

        done=new JButton("Done");
        addAnother = new JButton("Add Another");

        question.setFont(new Font("Consolas", Font.PLAIN, 25));
        option1.setFont(new Font("Consolas", Font.PLAIN, 25));
        option2.setFont(new Font("Consolas", Font.PLAIN, 25));
        option3.setFont(new Font("Consolas", Font.PLAIN, 25));
        option4.setFont(new Font("Consolas", Font.PLAIN, 25));
        answer.setFont(new Font("Consolas", Font.PLAIN, 25));

        opt1Field.setFont(new Font("Consolas", Font.PLAIN, 20));
        opt2Field.setFont(new Font("Consolas", Font.PLAIN, 20));
        opt3Field.setFont(new Font("Consolas", Font.PLAIN, 20));
        opt4Field.setFont(new Font("Consolas", Font.PLAIN, 20));
        ansField.setFont(new Font("Consolas", Font.PLAIN, 20));

        question.setBounds(50, 70, 200, 30);
        option1.setBounds(50, 120, 200, 30);
        option2.setBounds(50, 170, 200, 30);
        option3.setBounds(50, 220, 200, 30);
        option4.setBounds(50, 270, 200, 30);
        answer.setBounds(50, 320, 200, 30);

        ques.setBounds(350, 70, 150, 30);
        opt1Field.setBounds(250, 120, 350, 30);
        opt2Field.setBounds(250, 170, 350, 30);
        opt3Field.setBounds(250, 220, 350, 30);
        opt4Field.setBounds(250, 270, 350, 30);
        ansField.setBounds(250, 320, 350, 30);

        done.setBounds(100, 370, 100, 30);
        addAnother.setBounds(350, 370, 150, 30);

        ques.addActionListener(this);
        done.addActionListener(this);
        addAnother.addActionListener(this);

        pane.add(question);
        pane.add(option1);
        pane.add(option2);
        pane.add(option3);
        pane.add(option4);
        pane.add(answer);
        pane.add(ques);
        pane.add(opt1Field);
        pane.add(opt2Field);
        pane.add(opt3Field);
        pane.add(opt4Field);
        pane.add(ansField);
        pane.add(done);
        pane.add(addAnother);

        id=1;
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.add(scroll);
        frame.setVisible(true);
        frame.setSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ques){
            q=new Question(ques);
        }
        else if(e.getSource()==addAnother){
//            System.out.println(q.getQuestion(ques));
            try {
                String x=q.getQuestion();
                saveToDb(x, opt1Field.getText(), opt2Field.getText(), opt3Field.getText(), opt4Field.getText(), ansField.getText());
                ques.setText("Add Question");
                ques.setEnabled(true);
                opt1Field.setText("");
                opt2Field.setText("");
                opt3Field.setText("");
                opt4Field.setText("");
                ansField.setText("");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else if(e.getSource()==done){
            String x=q.getQuestion();
            try {
                saveToDb(x, opt1Field.getText(), opt2Field.getText(), opt3Field.getText(), opt4Field.getText(), ansField.getText());
                frame.dispose();
                JOptionPane.showMessageDialog(frame,"Question/s Added Successfully ✅");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    void saveToDb(String question, String option1, String option2, String option3,String option4,String answer) throws SQLException {
        conn= DriverManager.getConnection("jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6417854","sql6417854","sWNAI8YF3C");
        PreparedStatement statement= conn.prepareStatement("insert into questions values(?,?,?,?,?,?,?);");
        statement.setInt(1, id++);
        statement.setString(2, question);
        statement.setString(3, option1);
        statement.setString(4, option2);
        statement.setString(5, option3);
        statement.setString(6, option4);
        statement.setString(7, answer);
        statement.executeUpdate();
    }



    public static void main(String[] args) {
        new AddQuestion();
    }
}

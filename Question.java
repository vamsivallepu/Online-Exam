import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Question implements ActionListener {
    JFrame frame;
    JTextArea tArea;
    JButton save;
    JButton ques;
    public Question(JButton ques){
        this.ques=ques;
        frame=new JFrame("Question");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(400, 350);

        tArea = new JTextArea(5, 5);
        tArea.setFont(new Font("consolas", Font.LAYOUT_LEFT_TO_RIGHT, 20));
        tArea.setLineWrap(true);
        tArea.setWrapStyleWord(true);
        JScrollPane scrollableTextArea = new JScrollPane(tArea);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.getContentPane().add(scrollableTextArea);

        save =new JButton("DONE");
        save.setSize(5,2);
        save.addActionListener(this);

        frame.add(save, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
    }
    String getQuestion(){

        return tArea.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==save){
            frame.dispose();
            ques.setText("Question Added");
            ques.setEnabled(false);
        }
    }
}

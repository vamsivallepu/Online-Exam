import javax.print.attribute.standard.JobName;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Index implements ActionListener {
    JFrame frame=new JFrame("WELCOME");
    JButton admin, student;
    Image img = Toolkit.getDefaultToolkit().getImage("C:\\Users\\vamsivallepu\\Downloads\\indexback.jpg");
    public Index() throws IOException {
//        frame.setLayout(null);
        frame.setContentPane(new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, null);
            }
        });
        Container pane=frame.getContentPane();
        pane.setLayout(null);
        Border emptyBorder = BorderFactory.createEmptyBorder();

        admin=new JButton("Admin");
        student= new JButton("Student");

        admin.setBorder(emptyBorder);
        admin.setBackground(Color.gray);
        admin.setForeground(Color.white);
        admin.setFocusPainted(false);
        admin.setFont(new Font("Consolas", Font.PLAIN, 25));
        admin.setActionCommand("admin");
        admin.addActionListener(this);

        student.setBorder(emptyBorder);
        student.setBackground(Color.gray);
        student.setForeground(Color.white);
        student.setFocusPainted(false);
        student.setFont(new Font("Consolas", Font.PLAIN, 25));
        student.setActionCommand("student");
        student.addActionListener(this);

        admin.setBounds(75, 55, 125,45);
        student.setBounds(210, 55, 125,45);

        pane.add(admin);
        pane.add(student);
//        frame.pack();
        frame.setSize(new Dimension(1920, 1080));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new Index();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==admin){
//            System.out.println("Admin");
            new AdminLogin();
        }
        else if(e.getSource()==student){
            new StudentLogin();
        }
    }
}

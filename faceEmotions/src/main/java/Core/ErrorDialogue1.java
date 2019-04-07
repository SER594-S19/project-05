package Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorDialogue1 extends JFrame{
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JButton button = new JButton("close");

    public void show(String error, String solution)
    {
        frame = new JFrame();
        frame.setSize(500,500);

        JLabel contentPane = new JLabel();
//        panel.setLayout(null);

        Image img = new ImageIcon(this.getClass().getResource("/res/Scoob.jpg")).getImage();
        contentPane.setIcon(new ImageIcon(img));



        JLabel filler1 = new JLabel("Sorry to interrupt, " + error +", " + solution );
        //filler1.setHorizontalAlignment(JLabel.LEFT);


        frame.setContentPane(contentPane);
        frame.setLayout(new GridBagLayout());

//        filler1.setBounds(25,25,350,200);
//        button.setBounds(300,200,75,25);
        panel.add(filler1, BorderLayout.EAST);
        panel.add(button, BorderLayout.SOUTH);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);


        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.dispose();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

    private static void main(String[] args){
        ErrorDialogue1 ed = new ErrorDialogue1();
        ed.show("ERROR", "SOLUTION");
    }

}

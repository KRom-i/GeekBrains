import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.Semaphore;

public class MyJFrame extends JFrame {

    private int colorInt = 0;
    private Semaphore semaphore = new Semaphore(1);
    private Color color;
    private  JPanel jPanel;
    private JButton[] jButtonLove = new JButton[1510];

    public MyJFrame() {

        setTitle("My JFRAME TEST");
        setSize(400, 400);
        jPanel = new JPanel();
        jPanel.setBackground(Color.white);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        for(int i = 0; i < jButtonLove.length; i++) {
            jButtonLove[i] = new JButton();
            jButtonLove[i].setText(" ");
            jPanel.add(jButtonLove[i]);
        }

        add(jPanel);
        setVisible(true);

    }


    public void colorButton(String msg){

        boolean colorUpButton = false;
        switch(msg) {
            case "1":
                MyJFrame.this.color = Color.GREEN;
                colorUpButton = true;
                break;
            case "2":
                MyJFrame.this.color = Color.BLUE;
                colorUpButton = true;
                break;
            case "3":
                MyJFrame.this.color = Color.RED;
                colorUpButton = true;
                break;
            case "4":
                MyJFrame.this.color = Color.BLACK;
                colorUpButton = true;
                break;
            case "5":
                MyJFrame.this.color = Color.white;
                colorUpButton = true;
                break;
            case "6":
                MyJFrame.this.color = Color.ORANGE;
                colorUpButton = true;
                break;
            case "7":
                MyJFrame.this.color = Color.MAGENTA;
                colorUpButton = true;
                break;
            case "8":
                MyJFrame.this.color = Color.cyan;
                colorUpButton = true;
                break;
            case "9":
                MyJFrame.this.color = Color.DARK_GRAY;
                colorUpButton = true;
                break;
            case "10":
                MyJFrame.this.color = Color.LIGHT_GRAY;
                colorUpButton = true;
                break;
            case "11":
                MyJFrame.this.color = Color.PINK;
                colorUpButton = true;
        }

        if (colorUpButton){
            for(int i = 0; i < jButtonLove.length; i++) {

                jButtonLove[i].setBackground(color);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
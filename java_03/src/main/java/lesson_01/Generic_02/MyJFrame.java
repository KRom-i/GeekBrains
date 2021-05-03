package lesson_01.Generic_02;

import javax.swing.*;
import java.awt.*;

public class MyJFrame extends JFrame {

    public MyJFrame() throws HeadlessException {
        setTitle("MyJFreme");
//        Bounds (грантцы)
        setBounds(100,100,100,100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

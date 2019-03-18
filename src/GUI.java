import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private JPanel rootPanel;
    private JPanel bgPanel;
    private JTextPane timerPane;
    private JLabel logo;
    private JButton startStopButton;
    private JButton pauseButton;
    
    public GUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        
        super("StopWatch v 0.2");
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setContentPane(rootPanel);
        
        timerPane.setText("00:00:000");
        
        //focus text in JTexPane of timer
        StyledDocument doc = timerPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    
    
        //in the end
        setVisible(true);
        //add an instance of StopWatch
        StopWatch sw = new StopWatch();
        //-------START/STOP BUTTON-------
        startStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startStopButton.getText() == "START") {
                    startStopButton.setText("STOP");
                    sw.start();
                } else if (startStopButton.getText() == "STOP"){
                    startStopButton.setText("START");
                }
                
            }
        });
        //---------PAUSE BUTTON---------
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
            }
        });
    }
    
    private void createUIComponents() {
        logo = new JLabel(new ImageIcon("resources/522.jpg"));
    }
    
    public void setTimerPane(int min, int sec, int ms) {
        String minToSet;
        String secToSet;
        String msToSet;
        
        //-------Minutes-------
        if (min < 10) {
            minToSet = "0" + min;
        } else {
            minToSet = Integer.toString(min);
        }
        //-------Seconds-------
        if (sec < 10) {
            secToSet = "0" + sec;
        } else {
            secToSet = Integer.toString(sec);
        }
        //-----Milliseconds-----
        if (ms < 10) {
            msToSet = "00" + ms;
        } else if (ms < 100) {
            msToSet = "0" + ms;
        } else {
            msToSet = Integer.toString(ms);
        }
        String timerPaneText = minToSet + ":" + secToSet + ":" + msToSet;
        timerPane.setText(timerPaneText);
    }
}

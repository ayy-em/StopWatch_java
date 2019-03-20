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
    private JButton startPauseButton;
    private JButton stopButton;

    private long timerTime;
    
    private StopWatch sw;
    
    public GUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        
        super("StopWatch v 0.2");
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setContentPane(rootPanel);
        
        //timerPane.setText("00:00:000");
        
        //center text in JTexPane of timer
        StyledDocument doc = timerPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        setStoppedState();

        //-------START/PAUSE BUTTON-------
        startPauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (sw == null) {
                    sw = new StopWatchForGUI();
                    sw.execute();
                    setActiveState();
                } else if (sw.isPaused()) {
                    sw.setPaused(false);
                    setActiveState();
                } else {
                    sw.setPaused(true);
                    setPausedState();
                }
                /*
                if (startPauseButton.getText() == "START") {
                    startPauseButton.setText("PAUSE");
                    sw.start();
                } else if (startPauseButton.getText() == "PAUSE"){
                    startPauseButton.setText("START");
                    sw.interrupt();
                }
                */
            }
            
        });
        //---------STOP BUTTON---------
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sw != null){
                    sw.cancel(true);
                    sw = null;
                }
                setStoppedState();
            }
        });
    }
    
    private void createUIComponents() {
        logo = new JLabel(new ImageIcon("resources/522.jpg"));
    }


    private class StopWatchForGUI extends StopWatch {
        @Override
        protected void process(long value) {
            if(sw != null)
                setTimerPane(value);
        }
    }

    private void setStoppedState() {
        startPauseButton.setText("GO");
        setTimerPane(timerTime);
    }

    private void setPausedState() {
        startPauseButton.setText("GO");
        stopButton.setEnabled(true);
    }

    private void setActiveState() {
        startPauseButton.setText("PAUSE");
    }

    public void setTimerPane(long timerTime) {
    
        String minToSet;
        String secToSet;
        String msToSet;
        if (timerTime < 999) {
            minToSet = "00";
            secToSet = "00";
            int msToSetInt = (int) timerTime;
            msToSet = java.lang.String.format("%03d",msToSetInt);
        } else if (timerTime < 59999) {
            minToSet = "00";
            int seconds = (int) timerTime/1000;
            secToSet = java.lang.String.format("%02d",seconds);
            int tempSec = (int) timerTime - seconds*1000;
            msToSet = java.lang.String.format("%03d",tempSec);
        } else {
            int minutes = (int) timerTime/60000;
            minToSet = java.lang.String.format("%02d",minutes);
            int seconds = (int) (timerTime - minutes*60000)/1000;
            secToSet = java.lang.String.format("%02d",seconds);
            int tempMs = (int) timerTime - minutes*60000 - seconds*1000;
            msToSet = java.lang.String.format("%03d",tempMs);
        }
        String timerPaneText = minToSet + ":" + secToSet + ":" + msToSet;
        timerPane.setText(timerPaneText);
        
        /* old method
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
        //end of old method*/
    }
}

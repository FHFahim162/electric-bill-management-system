import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashPage extends JFrame {
    private Timer timer;

    public SplashPage() {
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(600, 404);
        setLocationRelativeTo(null); 

        
        JLabel splashLabel = new JLabel(new ImageIcon("images\\photo1.jpg"));
		
       
		
		
        add(splashLabel);

        
        int duration = 3000; 
        timer = new Timer(duration, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
				new MainFrame().setVisible(true);
			
            }
        });
        timer.setRepeats(false); 

       
        setVisible(true);

        
        timer.start();
    }
   public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SplashPage();
            }
        });
    }
}
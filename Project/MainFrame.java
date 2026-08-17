import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;



public class MainFrame extends JFrame {
	

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout()) {
           
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                ImageIcon imageIcon = new ImageIcon("images\\fg.jpg");
                if (imageIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    Image image = imageIcon.getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                } else {
                    System.err.println("Image not found or not loaded");
                }
            }
        };
        mainPanel.setBackground(new Color(173, 216, 230)); 

        JPanel boxPanel = createBoxPanel();
        mainPanel.add(boxPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setVisible(true);
    }

   private JPanel createBoxPanel() {
    JPanel boxPanel = new JPanel() {
       
		
		
		protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            int width = getWidth();
            int height = getHeight();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            
            Color boxColor = new Color(255, 255, 255, 150);
            GradientPaint gradientPaint = new GradientPaint(
                    0, 0, boxColor,
                    0, height, new Color(255, 255, 255, 200)
            );
            g2d.setPaint(gradientPaint);
            g2d.fillRoundRect(0, 0, width, height, 20, 20);
            g2d.dispose();
        }
    };
	JTextField txtUsername = new JTextField();
    JPasswordField txtPassword = new JPasswordField();
    boxPanel.setLayout(new GridBagLayout());
    boxPanel.setPreferredSize(new Dimension(500, 400));
    boxPanel.setOpaque(false);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;

    LoginOverlay loginPanel = new LoginOverlay();
    boxPanel.add(loginPanel, gbc);
	
	

    return boxPanel;
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}

class LoginOverlay extends JPanel {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chRememberMe;
    private JButton cmdLogin;
	private boolean initialized = false;

    public LoginOverlay() {
        init();
    }
private void init() {
	
	if (initialized) {
            return;
        }
    setLayout(new BorderLayout(20, 20));
    setBackground(Color.WHITE);

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(35, 45, 30, 45));
    panel.setBackground(Color.WHITE);

    txtUsername = new JTextField();
    txtPassword = new JPasswordField();
    chRememberMe = new JCheckBox("Remember me");
    cmdLogin = new JButton("Login");
	
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;  
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(0, 0, 8, 0);  

    JLabel lbTitle = new JLabel("Welcome back!");
    lbTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
    lbTitle.setForeground(new Color(26, 162, 96));
    panel.add(lbTitle, gbc);

    
    gbc.gridy++;
    JLabel description = new JLabel("Please sign in to access your account");
    gbc.insets = new Insets(0, 0, 8, 0);  
    panel.add(description, gbc);

   
    gbc.gridy++;
    gbc.gridwidth = 2;  //  two columns
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(0, 0, 8, 0);  

    txtUsername.setPreferredSize(new Dimension(300, 30));
    
    txtUsername.setText("Enter your username ");
    txtUsername.setForeground(Color.GRAY);
    panel.add(txtUsername, gbc);

  
    gbc.gridy++;
    txtPassword.setPreferredSize(new Dimension(300, 30));
	JToggleButton showHideButton = new JToggleButton(new ImageIcon("images\\eyeicon1.png"));
	showHideButton .setBackground(Color.WHITE);

        showHideButton.setFocusPainted(false);
        showHideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle password visibility
                if (showHideButton.isSelected()) {
                    txtPassword.setEchoChar((char) 0);  
                } else {
                    txtPassword.setEchoChar('\u2022');  
				
                }
            }
        });
		
   
    txtPassword.setText("Enter your password");
    txtPassword.setForeground(Color.GRAY);
    panel.add(txtPassword, gbc);
	 
	JPanel passwordPanel = new JPanel();
    passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
    passwordPanel.add(txtPassword);
    passwordPanel.add(Box.createRigidArea(new Dimension(0, 0)));  // Add space 
    passwordPanel.add(showHideButton);

    
    
    gbc.gridy++;
    gbc.gridwidth = 2;  
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(0, 0, 8, 0);  // Bottom margin
    panel.add(passwordPanel, gbc); 

    
    gbc.gridy++;
    gbc.gridwidth = 1;  
    gbc.anchor = GridBagConstraints.WEST;
    panel.add(chRememberMe, gbc);
	chRememberMe.setBackground(Color.WHITE);

    
    gbc.gridy++;
    gbc.gridwidth = 2;  
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10, 0, 0, 0);  

    cmdLogin.setPreferredSize(new Dimension(300, 30));
    cmdLogin.setBackground(new Color(128, 0, 128));
    cmdLogin.setForeground(Color.WHITE);
    panel.add(cmdLogin, gbc);

    add(panel, BorderLayout.CENTER);

        JPanel signupPanel = createSignupPanel();
        add(panel, BorderLayout.CENTER);
        add(signupPanel, BorderLayout.SOUTH);
    
	cmdLogin.addActionListener(new ActionListener() {
        
        public void actionPerformed(ActionEvent e) {
            handleLogin();
        }
    });

    
    SwingUtilities.invokeLater(() -> {
        Window window = SwingUtilities.getWindowAncestor(LoginOverlay.this);
        if (window != null) {
            window.addWindowListener(new WindowAdapter() {
                
                public void windowClosing(WindowEvent e) {
                    showExitConfirmationDialog();
                }
            });
        }
    });
	initialized = true;
}



private void handleLogin() {
    String username = txtUsername.getText();
    String password = new String(txtPassword.getPassword());

    if (username.equals("admin") && password.equals("1234")) {
        JOptionPane.showMessageDialog(this, "Welcome, Admin!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
        openAdminDashboard();
    } else {
        List<DashboardApp.User> users = readUserData();
        boolean userFound = false;

        for (DashboardApp.User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                if (isUserApproved(username)) {
                    JOptionPane.showMessageDialog(this, "Welcome, " + username + "!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                    openUserDashboard();
                    userFound = true;
                    break;
                }
				else {
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(LoginOverlay.this), "Your account is pending approval.", "Warning", JOptionPane.WARNING_MESSAGE);

                    userFound = true;
                    break;
                }
            }
		}
            if (!userFound) {
                JOptionPane.showMessageDialog(this, "Username and password don't match", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openAdminDashboard() {
         DashboardApp dashboard = new DashboardApp();
         
		 SwingUtilities.getWindowAncestor(LoginOverlay.this).dispose();
	
    }

    private void openUserDashboard() {
        UserDashboard dashboard1 = new UserDashboard();
		SwingUtilities.getWindowAncestor(LoginOverlay.this).dispose();
    }

    private List<DashboardApp.User> readUserData() {
    List<DashboardApp.User> userList = new ArrayList<>();

    Path filePath = Paths.get("user_data.txt");

    try {
        List<String> lines = Files.readAllLines(filePath);

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 8) {
                String firstName = parts[0];
                String lastName = parts[1];
                String username = parts[2];
                String password = parts[3];
                String meterId = parts[4];
                String email = parts[5];
                String mobileNumber = parts[6];
                boolean approved = Boolean.parseBoolean(parts[7]);
                    DashboardApp.User user = new DashboardApp.User(firstName, lastName, username, password, meterId, email, mobileNumber, approved);
                userList.add(user);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userList;
    }

        
    

    private void showExitConfirmationDialog() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to close the window?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
	
	private boolean isUserApproved(String username) {
    List<DashboardApp.User> users = readUserData();

    for (DashboardApp.User user : users) {
        if (user.getUsername().equals(username)) {
            return user.isApproved();
        }
    }

    return false;
}


    private JPanel createSignupPanel() {
        JPanel signupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		signupPanel.setBackground(Color.WHITE);
        JButton cmdRegister = new JButton("<html><u>Sign up</u></html>");
        cmdRegister.setContentAreaFilled(false);
        cmdRegister.setBorderPainted(false);
        cmdRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdRegister.addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
                
				
				SwingUtilities.getWindowAncestor(LoginOverlay.this).dispose();
				RegFrame regFrame = new RegFrame(); 
    
    
    regFrame.setTitle("Registration Form");
    regFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    regFrame.setSize(1200, 700); 
    regFrame.setLocationRelativeTo(null); 
    
    regFrame.setVisible(true); 
            }
        });

        JLabel label = new JLabel("Don't have an account ?");
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(label);
        panel.add(cmdRegister);
		panel.setBackground(Color.WHITE);
        signupPanel.add(panel);

        return signupPanel;
    }
private class User {
        private String firstName;
        private String lastName;
        private String username;
        private String password;
        private String meterId;
        private String email;
        private String mobileNumber;
		private boolean approved;

        public User(String firstName, String lastName, String username, String password, String meterId, String email, String mobileNumber, boolean approved) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.password = password;
            this.meterId = meterId;
            this.email = email;
            this.mobileNumber = mobileNumber;
			this.approved = approved;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getMeterId() {
            return meterId;
        }

        public String getEmail() {
            return email;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }
		  public boolean isApproved() {
            return approved;
        }
    }
}



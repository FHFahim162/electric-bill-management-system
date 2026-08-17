import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class RegFrame extends JFrame {

    public RegFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout()) {
            
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                ImageIcon imageIcon = new ImageIcon("C:\\Users\\sifat\\Music\\dg.jpg");
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
    boxPanel.setLayout(new GridBagLayout());
    boxPanel.setPreferredSize(new Dimension(500, 400));
    boxPanel.setOpaque(false);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;

    Register registerPanel = new Register();
    boxPanel.add(registerPanel, gbc);

    return boxPanel;
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegFrame());
    }
}



class Register extends JPanel {
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JRadioButton jrMale;
    private JRadioButton jrFemale;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JTextField txtMeterId;
    private JTextField txtEmail;
    private JTextField txtMobileNumber;
    private ButtonGroup groupGender;
    private JButton cmdRegister;

    private File userDataFile;

    public Register() {
        init();
        userDataFile = new File("user_data.txt");
		
		
		
		
    }

    private void init() {
        setLayout(new BorderLayout(20, 20));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		JPanel panel = new JPanel(new BorderLayout(0, 20));
             panel.setBackground(Color.WHITE);
        // Title Label
        JLabel lbTitle = new JLabel("Welcome to Signup Page");
        lbTitle.setFont(new Font("sansSerif", Font.BOLD, 20));
        lbTitle.setForeground(new Color(26, 162, 96));
        lbTitle.setHorizontalAlignment(JLabel.CENTER);

        panel.add(lbTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        txtFirstName = createTextField("First name");
        txtLastName = createTextField("Last name");

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Full Name"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(txtFirstName, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(txtLastName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Gender"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        jrMale = new JRadioButton("Male");
        jrFemale = new JRadioButton("Female");
        groupGender = new ButtonGroup();
        groupGender.add(jrMale);
        groupGender.add(jrFemale);
        jrMale.setSelected(true);
        JPanel genderPanel = new JPanel();
        genderPanel.add(jrMale);
        genderPanel.add(jrFemale);
        formPanel.add(genderPanel, gbc);
		genderPanel.setBackground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 5;
        formPanel.add(new JSeparator(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Username or Email"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        txtUsername = createTextField("Enter your username or email");
        formPanel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Password"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        txtPassword = createPasswordField("Enter your password");
        formPanel.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Confirm Password"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        txtConfirmPassword = createPasswordField("Re-enter your password");
        formPanel.add(txtConfirmPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Meter ID"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 4;
        txtMeterId = createTextField("Enter meter ID");
        formPanel.add(txtMeterId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Email"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 4;
        txtEmail = createTextField("Enter email");
        formPanel.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Mobile Number"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 4;
        txtMobileNumber = createTextField("Enter mobile number");
        formPanel.add(txtMobileNumber, gbc);


        

        cmdRegister = new JButton("Sign Up");
		cmdRegister.setPreferredSize(new Dimension(300, 30));
		cmdRegister.setBackground(new Color(128, 0, 128));
		cmdRegister.setForeground(Color.WHITE);
        cmdRegister.addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
                handleRegistration();
            }
        });

        

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 5;
        gbc.insets = new Insets(20, 0, 0, 0);
        formPanel.add(cmdRegister, gbc);

        
		 JPanel loginPanel = createLoginPanel();

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(loginPanel, BorderLayout.SOUTH);

        add(panel);
    }
	
	private JTextField createTextField(String placeholder) {
    JTextField textField = new JTextField(placeholder);
	textField.setPreferredSize(new Dimension(300, 30));
    textField.setForeground(Color.GRAY);

    textField.addFocusListener(new FocusListener() {
       
        public void focusGained(FocusEvent e) {
            if (textField.getText().equals(placeholder)) {
                textField.setText("");
                textField.setForeground(Color.BLACK);
            }
        }

      
        public void focusLost(FocusEvent e) {
            if (textField.getText().isEmpty()) {
                textField.setText(placeholder);
                textField.setForeground(Color.GRAY);
            }
        }
    });

    return textField;
}
	
	
	
	

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 30));
        passwordField.setEchoChar((char) 0);
        passwordField.setText(placeholder);
        passwordField.setForeground(Color.GRAY);
        return passwordField;
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JButton cmdLogin = new JButton("<html><a href=\"#\">Sign in here</a></html>");
        cmdLogin.setBorder(BorderFactory.createEmptyBorder());
        cmdLogin.setContentAreaFilled(false);
        cmdLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdLogin.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
				
				SwingUtilities.getWindowAncestor(Register.this).dispose();
				MainFrame mainFrame = new MainFrame();  
				 
    
    
    mainFrame.setTitle("Login Form");
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    mainFrame.setSize(1200, 700); 
    mainFrame.setLocationRelativeTo(null); 
    
    mainFrame.setVisible(true);
            }
        });
        JLabel label = new JLabel("Already have an account ?");
        label.setForeground(Color.GRAY);
        loginPanel.add(label);
        loginPanel.add(cmdLogin);
		loginPanel.setBackground(Color.WHITE);
        return loginPanel;
    }

    private void handleRegistration() {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        String meterId = txtMeterId.getText();
        String email = txtEmail.getText();
        String mobileNumber = txtMobileNumber.getText();

        if (isEmptyField(firstName) || isEmptyField(lastName) || isEmptyField(username)
                || isEmptyField(password) || isEmptyField(confirmPassword)
                || isEmptyField(meterId) || isEmptyField(email) || isEmptyField(mobileNumber)) {
            JOptionPane.showMessageDialog(this, "Please fill in all the information.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Password and Confirm Password do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<User> users = readUserData();

        if (isUserDuplicate(users, username, email, meterId)) {
            JOptionPane.showMessageDialog(this, "User already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User newUser = new User(firstName, lastName, username, password, meterId, email, mobileNumber);
        users.add(newUser);
        writeUserData(users);

        JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        
    }

    private boolean isEmptyField(String value) {
        return value.trim().isEmpty();
    }

    private List<User> readUserData() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(userDataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 7) {
                    User user = new User(userData[0], userData[1], userData[2], userData[3], userData[4], userData[5], userData[6]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    private void writeUserData(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userDataFile))) {
            for (User user : users) {
                String userData = String.join(",", user.getFirstName(), user.getLastName(), user.getUsername(),
                        user.getPassword(), user.getMeterId(), user.getEmail(), user.getMobileNumber());
                writer.write(userData);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isUserDuplicate(List<User> users, String username, String email, String meterId) {
        for (User user : users) {
            if (user.getUsername().equals(username) || user.getEmail().equals(email) || user.getMeterId().equals(meterId)) {
                return true;
            }
        }
        return false;
    }

    private class User {
        private String firstName;
        private String lastName;
        private String username;
        private String password;
        private String meterId;
        private String email;
        private String mobileNumber;

        public User(String firstName, String lastName, String username, String password, String meterId, String email, String mobileNumber) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.password = password;
            this.meterId = meterId;
            this.email = email;
            this.mobileNumber = mobileNumber;
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
    }
}




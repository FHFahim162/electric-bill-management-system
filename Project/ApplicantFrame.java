import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ApplicantFrame extends JFrame {

    public ApplicantFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                ImageIcon imageIcon = new ImageIcon("images\\dg.jpg");
                if (imageIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    Image image = imageIcon.getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                } else {
                    System.err.println("Image not found or not loaded");
                }
            }
        };
        mainPanel.setBackground(new Color(173, 216, 230));

        JPanel boxPanel = createApplicantPanel();
        mainPanel.add(boxPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setVisible(true);
    }

    private JPanel createApplicantPanel() {
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
        boxPanel.setPreferredSize(new Dimension(500, 600));
        boxPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        ApplicantForm applicantForm = new ApplicantForm(this);
        boxPanel.add(applicantForm, gbc);

        return boxPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ApplicantFrame());
    }
}

class ApplicantForm extends JPanel {
    // Fields for applicant information
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtNID;
    private JTextField txtMobileNumber;
    private JTextField txtPresentAddress;
    private JTextField txtCity;
    private JTextField txtHouseNo;
    private JTextField txtPostalCode;
    private JComboBox<String> cmbConnectionType;
    private JRadioButton jrMale;
    private JRadioButton jrFemale;
    private ButtonGroup groupGender;
    private JButton cmdSubmit;
	private JButton backButton;
	

    private File applicantDataFile;
    private ApplicantFrame parentFrame; 

    public ApplicantForm(ApplicantFrame parentFrame) {
        this.parentFrame = parentFrame;
        init();
        applicantDataFile = new File("applicant.txt");
    }

    

    private void init() {
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(Color.WHITE);

        JLabel lbTitle = new JLabel("Add Customar Form");
        lbTitle.setFont(new Font("sansSerif", Font.BOLD, 20));
        lbTitle.setForeground(new Color(26, 162, 96));
        lbTitle.setHorizontalAlignment(JLabel.CENTER);

        panel.add(lbTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        txtFirstName = createTextField("First name");
        txtLastName = createTextField("Last name");
        txtNID = createTextField("National ID");
        txtMobileNumber = createTextField("Mobile number");
        txtPresentAddress = createTextField("Present address");
        txtCity = createTextField("City");
        txtHouseNo = createTextField("House number");
        txtPostalCode = createTextField("Postal code");

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
        formPanel.add(new JLabel("Mobile Number"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        formPanel.add(txtMobileNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Present Address"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        formPanel.add(txtPresentAddress, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("City"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        formPanel.add(txtCity, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("House Number"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        formPanel.add(txtHouseNo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Postal Code"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        formPanel.add(txtPostalCode, gbc);

        // Gender
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Gender"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
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

        // Connection Type
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Connection Type"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 4;
        cmbConnectionType = new JComboBox<>(new String[]{"Residential", "Commercial", "Industrial"});
        formPanel.add(cmbConnectionType, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("National ID"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 4;
        formPanel.add(txtNID, gbc);


        

        cmdSubmit = new JButton("add");
        cmdSubmit.setPreferredSize(new Dimension(300, 30));
        cmdSubmit.setBackground(new Color(128, 0, 128));
        cmdSubmit.setForeground(Color.WHITE);
        cmdSubmit.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                handleSubmission();
            }
        });
		 backButton = new JButton("Back");
        backButton.setBackground(new Color(0xFF8A14));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
               
                parentFrame.dispose(); 
				new DashboardApp();
            }
        });
		
		gbc.gridx = 0;  
        gbc.gridy = 9;  
        gbc.gridwidth = 1; 
        gbc.insets = new Insets(20, 10, 0, 0); 
        formPanel.add(backButton, gbc);
    

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 5;
        gbc.insets = new Insets(20, 0, 0, 0);
        formPanel.add(cmdSubmit, gbc);

        panel.add(formPanel, BorderLayout.CENTER);

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

	

    
	private void writeApplicantData(String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(applicantDataFile, true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving applicant data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

   private void handleSubmission() {
   
    String firstName = txtFirstName.getText();
    String lastName = txtLastName.getText();
    String nid = txtNID.getText();
    String mobileNumber = txtMobileNumber.getText();
    String presentAddress = txtPresentAddress.getText();
    String city = txtCity.getText();
    String houseNo = txtHouseNo.getText();
    String postalCode = txtPostalCode.getText();
    String connectionType = (String) cmbConnectionType.getSelectedItem();
    String gender = jrMale.isSelected() ? "Male" : "Female";

   
    if (isEmptyField(firstName) || isEmptyField(lastName) || isEmptyField(nid)
            || isEmptyField(mobileNumber) || isEmptyField(presentAddress) || isEmptyField(city)
            || isEmptyField(houseNo) || isEmptyField(postalCode)) {
        JOptionPane.showMessageDialog(this, "Please fill in all the information.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    
    if (!isNumeric(mobileNumber) || !isNumeric(houseNo) || !isNumeric(postalCode)) {
        JOptionPane.showMessageDialog(this, "Mobile number, House number, and Postal code must be numeric.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    
    String applicantData = String.join(",", firstName, lastName, nid, mobileNumber, presentAddress,
            city, houseNo, postalCode, connectionType, gender);

    
    writeApplicantData(applicantData);

    JOptionPane.showMessageDialog(this, "add successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
}

private boolean isNumeric(String str) {
    return str.matches("\\d+");
}
 

	
	

   

    private boolean isEmptyField(String value) {
        return value.trim().isEmpty();
    }
}

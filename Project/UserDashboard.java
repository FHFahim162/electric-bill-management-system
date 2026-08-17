import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;



public class UserDashboard extends JFrame {

    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private JLabel profileLabel;
	private JTextField yearField;
    private JTextField dateField;
    private JTextField meterIdField;
    private JTextArea invoiceArea;

    public UserDashboard() {
        setTitle("User Dashboard");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        createSidebar();

        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBounds(232, 0, 968, 700);
        contentPanel.setLayout(null);
		contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);

        
        yearField = createTextField("Year");
        contentPanel.add(yearField, gbc);

        gbc.gridy++;
        dateField = createTextField("Date");
        contentPanel.add(dateField, gbc);

        gbc.gridy++;
        meterIdField = createTextField("Meter ID");
        contentPanel.add(meterIdField, gbc);

        
        JButton getInvoiceButton = createButton("Get Invoice");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        contentPanel.add(getInvoiceButton, gbc);

        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        invoiceArea = new JTextArea();
        contentPanel.add(invoiceArea, gbc);
    
		
		

      

        add(contentPanel);
        setVisible(true);
    }

    private JButton createTransparentButton(String text) {
        JButton button = createButton(text);
        button.setOpaque(false);
        return button;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(46, 49, 146));
        button.addActionListener(e -> handleSidebarButtonClick(text));
        return button;
    }
	
	private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField(20);
        textField.setBorder(BorderFactory.createCompoundBorder(
                textField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        textField.setFont(new Font("SansSerif", Font.PLAIN, 12));
        textField.setForeground(Color.BLACK);
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        textField.setPreferredSize(new Dimension(200, 25));
        textField.setText(placeholder);

        
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });

        return textField;
    }


    private void handleSidebarButtonClick(String buttonName) {
        switch (buttonName) {
            case "Choose Picture":
                chooseProfileImage();
                break;
            case "Update Profile":
                updateprofile();
                break;
            case "Pay Bill":
                paybill();
                break;
            case "Logout":
                logout();
                break;
            default:
                
        }
    }




    private void createSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(137, 59, 255));
        sidebarPanel.setLayout(null);
        sidebarPanel.setBounds(0, 0, 232, 700);

        JPanel profilePanel = new JPanel();
        profilePanel.setBounds(50, 10, 132, 132);
        profilePanel.setBackground(new Color(46, 49, 146));
        profilePanel.setLayout(new BorderLayout());

        profileLabel = new JLabel("Profile Pic");
        profileLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profileLabel.setVerticalAlignment(SwingConstants.CENTER);
        profileLabel.setForeground(Color.WHITE);
        profilePanel.add(profileLabel);

        JButton choosePictureButton = createTransparentButton("Choose Picture");
        choosePictureButton.setBounds(0, 200, 232, 40);

        JButton updateProfileButton = createTransparentButton("Update Profile");
        JButton payBillButton = createTransparentButton("Pay Bill");
        JButton logoutButton = createTransparentButton("Logout");

        updateProfileButton.setBounds(0, 250, 232, 40);
        payBillButton.setBounds(0, 300, 232, 40);
        logoutButton.setBounds(0, 350, 232, 40);

        sidebarPanel.add(profilePanel);
        sidebarPanel.add(choosePictureButton);
        sidebarPanel.add(updateProfileButton);
        sidebarPanel.add(payBillButton);
        sidebarPanel.add(logoutButton);

        
        

        add(sidebarPanel);
    }

    private void chooseProfileImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
            profileLabel.setIcon(imageIcon);
            profileLabel.setText("");
        }
    }
	
private void getInvoice() {
    
    String year = yearField.getText();
    String date = dateField.getText();
    String meterId = meterIdField.getText();

    
    if (year.isBlank() || date.isBlank() || meterId.isBlank()) {
        JOptionPane.showMessageDialog(this, "Please provide valid input for Year, Date, and Meter ID.",
                "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    
    String key = year + "-" + date + " " + meterId;

    System.out.println("Searching for key: " + key);

    
    BufferedReader br = null;
    try {
        br = new BufferedReader(new FileReader("invoice.txt"));
        String line;
        boolean found = false;

        while ((line = br.readLine()) != null) {
            
            if (line.contains(key)) {
                
                displayInvoiceDetailsInWindow(line);
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Invoice not found for the specified details.",
                    "Invoice Not Found", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (IOException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error reading invoice file.", "File Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
private void displayInvoiceDetailsInWindow(String line) {
    StringBuilder invoiceDetails = new StringBuilder();
    invoiceDetails.append("Invoice Details:\n");

    String[] details = line.split(":");
    for (String detail : details) {
        invoiceDetails.append(detail.trim()).append("\n");
    }

    
    int option = JOptionPane.showOptionDialog(this, invoiceDetails.toString(),
            "Invoice Details", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
            null, new Object[]{"Save Invoice", "Close"}, "Save Invoice");

    if (option == JOptionPane.YES_OPTION) {
        saveInvoiceToFile(invoiceDetails.toString());
    }
}

private void saveInvoiceToFile(String invoiceDetails) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Invoice As");
    
    int userSelection = fileChooser.showSaveDialog(this);
    
    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();
        try (PrintWriter writer = new PrintWriter(fileToSave)) {
            writer.println(invoiceDetails);
            JOptionPane.showMessageDialog(this, "Invoice saved successfully.",
                    "Save Invoice", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving invoice to file.",
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}



    private void logout() {
        dispose();
        new MainFrame(); 
    }
	private void updateprofile(){
		dispose();
	new UpdateProfile();
	}
	private void paybill(){
		dispose();
		new PayBill();
	}

    public static void main(String[] args) {
        new UserDashboard();
    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DashboardApp extends JFrame {

    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private JLabel profileLabel;
    private int approvedUserCount = 0;
    private JLabel registeredUsersLabel;
    private BillInvoicePanel billInvoicePanel;
    private List<User> users;

    public DashboardApp() {
        setTitle("Dashboard App");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        createSidebar();

        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBounds(232, 0, 968, 700);
        contentPanel.setLayout(null);

        registeredUsersLabel = createColoredBox("Registered Users: 0", 20, 20, 232, 80, new Color(212, 98, 255));
        contentPanel.add(registeredUsersLabel);

        loadUserData();
        createContent();
        setVisible(true);
    }

    private void loadUserData() {
        users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("user_data.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 8) {
                    User newUser = new User(userData[0].trim(), userData[1].trim(), userData[2].trim(),
                            userData[3].trim(), userData[4].trim(), userData[5].trim(), userData[6].trim(),
                            Boolean.parseBoolean(userData[7].trim()));
                    users.add(newUser);
                    if (newUser.isApproved()) {
                        approvedUserCount++;
                    }
                }
            }
            registeredUsersLabel.setText("Registered Users: " + approvedUserCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class User {
        private String firstName;
        private String lastName;
        private String username;
        private String password;
        private String meterId;
        private String email;
        private String mobileNumber;
        private boolean approved;

        public User(String firstName, String lastName, String username, String password, String meterId, String email,
                    String mobileNumber, boolean approved) {
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

        public void setApproved(boolean approved) {
            this.approved = approved;
        }
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

    private void handleSidebarButtonClick(String buttonName) {
        switch (buttonName) {
            case "Home":
                createContent();
                break;
            
            case "Add Customer":
                addcustomar();
                break;
            case "Customer Details":
                userdetails();
                break;
            case "Logout":
                logout();
                break;
            case "Bill Invoice":
                openBillInvoiceWindow();
                break;
            default:
                
        }
    }

    private JButton createTransparentButton(String text) {
        JButton button = createButton(text);
        button.setOpaque(false);
        return button;
    }

    private JPanel createCardPanel(String content) {
        JPanel cardPanel = new JPanel();
        JLabel label = new JLabel(content);
        cardPanel.add(label);
        return cardPanel;
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

        JButton chooseImageButton = createButton("Choose Image");
        chooseImageButton.setBounds(50, 150, 132, 30);
        chooseImageButton.addActionListener(e -> chooseProfileImage());

        JButton homeButton = createTransparentButton("Home");
   
        JButton addCustomerButton = createTransparentButton("Add Customer");
        JButton customerDetailsButton = createTransparentButton("Customer Details");
        JButton logoutButton = createTransparentButton("Logout");
        JButton billInvoiceButton = createTransparentButton("Bill Invoice");

        homeButton.setBounds(0, 200, 232, 40);
       
        addCustomerButton.setBounds(0, 250, 232, 40);
        customerDetailsButton.setBounds(0, 300, 232, 40);
        logoutButton.setBounds(0, 350, 232, 40);
        billInvoiceButton.setBounds(0, 400, 232, 40);

        sidebarPanel.add(profilePanel);
        sidebarPanel.add(chooseImageButton);
        sidebarPanel.add(homeButton);
        sidebarPanel.add(addCustomerButton);
        sidebarPanel.add(customerDetailsButton);
        sidebarPanel.add(logoutButton);
        sidebarPanel.add(billInvoiceButton);

        homeButton.addActionListener(e -> handleSidebarButtonClick("Home"));
        
        
        
        
        

        add(sidebarPanel);
    }

    private void createContent() {
        JLabel totalCustomersLabel = createColoredBox("Total Customers: 100", 272, 20, 232, 80, new Color(212, 98, 255));

        String[] columnNames = {"First Name", "Last Name", "Username", "Password", "Meter ID", "Email ID", "Mobile Number", "Approval Status"};
        Object[][] data = new Object[users.size()][8];

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            data[i][0] = user.getFirstName();
            data[i][1] = user.getLastName();
            data[i][2] = user.getUsername();
            data[i][3] = user.getPassword();
            data[i][4] = user.getMeterId();
            data[i][5] = user.getEmail();
            data[i][6] = user.getMobileNumber();
            data[i][7] = user.isApproved() ? "Approved" : "Pending";
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model) {
            
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                if (convertColumnIndexToModel(column) == 7) {
                    String status = (String) getModel().getValueAt(row, 7);
                    if ("Approved".equals(status)) {
                        component.setBackground(new Color(0, 191, 255));
                    } else {
                        component.setBackground(Color.YELLOW);
                    }
                } else {
                    component.setBackground(Color.WHITE);
                }
                return component;
            }
        };

        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 120, 928, 350);

        JButton approveButton = createColoredButton("Approve", 20, 480, 100, 30, new Color(0, 191, 255));

        JButton disapproveButton = createColoredButton("Disapprove", 130, 480, 120, 30, new Color(247, 93, 89));

        approveButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                User user = users.get(selectedRow);
                if (!user.isApproved()) {
                    user.setApproved(true);
                    approvedUserCount++;
                    model.setValueAt("Approved", selectedRow, 7);
                    updateUserDataFile();
                }
            }
        });

        disapproveButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                User user = users.get(selectedRow);
                if (user.isApproved()) {
                    user.setApproved(false);
                    approvedUserCount--;
                    model.setValueAt("Pending", selectedRow, 7);
                    updateUserDataFile();
                }
            }
        });

        

        contentPanel.add(registeredUsersLabel);
        contentPanel.add(totalCustomersLabel);
        contentPanel.add(scrollPane);
        contentPanel.add(approveButton);
        contentPanel.add(disapproveButton);
       

        add(contentPanel);
    }

    private void updateUserDataFile() {
        try (FileWriter writer = new FileWriter("user_data.txt")) {
            approvedUserCount = 0;
            for (User user : users) {
                writer.write(user.getFirstName() + "," + user.getLastName() + "," + user.getUsername() + "," +
                        user.getPassword() + "," + user.getMeterId() + "," + user.getEmail() + "," +
                        user.getMobileNumber() + "," + user.isApproved() + "\n");

                if (user.isApproved()) {
                    approvedUserCount++;
                }
            }
            registeredUsersLabel.setText("Registered Users: " + approvedUserCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	



    private JLabel createColoredBox(String text, int x, int y, int width, int height, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setBounds(x, y, width, height);
        label.setBorder(BorderFactory.createEmptyBorder());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setBackground(color);
        label.setOpaque(true);
        contentPanel.add(label);
        return label;
    }

    private JButton createColoredButton(String text, int x, int y, int width, int height, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 12));
        button.setBounds(x, y, width, height);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        contentPanel.add(button);
        return button;
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
	
	
	 private void openBillInvoiceWindow() {
        setVisible(false);
        JFrame invoiceFrame = new JFrame("Modern Invoice");
        invoiceFrame.setSize(1200, 700);

        BillInvoicePanel invoicePanel = new BillInvoicePanel();
        invoiceFrame.add(invoicePanel);
        invoiceFrame.setLocationRelativeTo(null);
        invoiceFrame.setVisible(true);
        invoiceFrame.setLocationRelativeTo(this);
        invoiceFrame.setVisible(true);
        invoiceFrame.addWindowListener(new WindowAdapter() {
            
            public void windowClosed(WindowEvent e) {
                setVisible(true);
            }
        });
    }
	 private void logout() {
    
    dispose();
    
	new MainFrame();
}
private void addcustomar(){
	dispose();
	new ApplicantFrame();
}
  private void userdetails(){
	  dispose();
      
   new UserDetailsPanel();
  }
	
	


    public static void main(String[] args) {
        new DashboardApp();
    }
}

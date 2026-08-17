import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UpdateProfile extends JFrame {
    private JTextField currentPasswordField, newUsernameField, newPasswordField;
    private JButton updateButton,backButton;

    public UpdateProfile() {
        setTitle("Update Profile");
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Update Profile");
        titleLabel.setForeground(new Color(34, 139, 34)); // Chrome Green color
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Current Password:"), gbc);
        gbc.gridx++;
        currentPasswordField = new JPasswordField();
        currentPasswordField.setPreferredSize(new Dimension(300, 40));
        panel.add(currentPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("New Username:"), gbc);
        gbc.gridx++;
        newUsernameField = new JTextField();
        newUsernameField.setPreferredSize(new Dimension(300, 40));
        panel.add(newUsernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("New Password:"), gbc);
        gbc.gridx++;
        newPasswordField = new JPasswordField();
        newPasswordField.setPreferredSize(new Dimension(300, 40));
        panel.add(newPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        updateButton = new JButton("Update Profile");
        updateButton.setPreferredSize(new Dimension(300, 40));
        updateButton.setBackground(Color.decode("#14B1FF"));
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProfile();
            }
        });
         panel.add(updateButton, gbc);
		   gbc.gridy++;
        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(300, 40));
		updateButton.setBackground(Color.decode("#FF14B1"));
        backButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UserDashboard();
            }
        });
        panel.add(backButton, gbc);

        setContentPane(panel);
        setSize(1200, 700);  
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateProfile() {
        String currentPassword = currentPasswordField.getText();
        String newUsername = newUsernameField.getText();
        String newPassword = newPasswordField.getText();

        if (currentPassword.isEmpty() || newUsername.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("user_data.txt"));
            StringBuilder fileContent = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                String[] user_data = line.split(",");
                String storedUsername = user_data[2];
                String storedPassword = user_data[3];

                if (storedUsername.equals(newUsername)) {
                    JOptionPane.showMessageDialog(this, "Username already exists. Choose a different username.", "Error", JOptionPane.ERROR_MESSAGE);
                    br.close();
                    return;
                }

                if (storedPassword.equals(currentPassword)) {
                    
                    user_data[2] = newUsername;
                    user_data[3] = newPassword;
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect current password", "Error", JOptionPane.ERROR_MESSAGE);
                    br.close();
                    return;
                }

                fileContent.append(String.join(",", user_data)).append("\n");
            }

            br.close();

            
            BufferedWriter bw = new BufferedWriter(new FileWriter("user_data.txt"));
            bw.write(fileContent.toString());
            bw.close();

            JOptionPane.showMessageDialog(this, "Profile updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating profile", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            
            public void run() {
                new UpdateProfile();
            }
        });
    }
}

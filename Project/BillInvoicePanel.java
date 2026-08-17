import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class BillInvoicePanel extends JPanel {

    private JTextField nameField;
    private JTextField meterIdField;
    private JTextField unitConsumptionField;
    private JTextField lastReadingField;
    private JTextField presentReadingField;
    private JTextField unitPerTkField;
    private JTextField amountField;
    private JTextField totalAmountWithVatField;
    private JTextField latePaymentField;

    public BillInvoicePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Title
        JLabel titleLabel = new JLabel("Electricity Bill Invoice");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Invoice Details
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBackground(Color.white);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 10, 20);

        addDetail(detailsPanel, gbc, "Name:", nameField = new JTextField(15));
        addDetail(detailsPanel, gbc, "Meter ID:", meterIdField = new JTextField(15));
        addDetail(detailsPanel, gbc, "Unit Consumption:", unitConsumptionField = new JTextField(15));
        addDetail(detailsPanel, gbc, "Last Reading:", lastReadingField = new JTextField(15));
        addDetail(detailsPanel, gbc, "Present Reading:", presentReadingField = new JTextField(15));
        addDetail(detailsPanel, gbc, "Unit Price (Tk):", unitPerTkField = new JTextField(15));
        addDetail(detailsPanel, gbc, "Amount:", amountField = new JTextField(15));
        addDetail(detailsPanel, gbc, "Total Amount with VAT & SD:", totalAmountWithVatField = new JTextField(15));
        addDetail(detailsPanel, gbc, "Late Payment Charge (3% extra):", latePaymentField = new JTextField(15));
        addDetail(detailsPanel, gbc, "Date:", new JLabel(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));


        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = createColoredButton("Back", new Color(138, 43, 226)); // Purple color
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window window = SwingUtilities.getWindowAncestor(BillInvoicePanel.this);
                if (window != null) {
                    window.dispose();
                }
            }
        });

        JButton generateInvoiceButton = createColoredButton("Generate Invoice", new Color(0, 191, 255));
        generateInvoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateInvoice();
            }
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(generateInvoiceButton);

        add(detailsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void addDetail(JPanel panel, GridBagConstraints gbc, String label, JComponent component) {
        gbc.gridx = 0;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);

        gbc.gridy++;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
    }

    private JButton createColoredButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        return button;
    }

    private void generateInvoice() {
        if (fieldsAreEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calculate and display invoice details
        double unitConsumption = Double.parseDouble(unitConsumptionField.getText());
        double unitPerTk = Double.parseDouble(unitPerTkField.getText());
        double lastReading = Double.parseDouble(lastReadingField.getText());
        double presentReading = Double.parseDouble(presentReadingField.getText());

        double amount = (presentReading - lastReading) * unitPerTk;
        double totalAmountWithVat = amount * 1.15; 
        double latePaymentCharge = totalAmountWithVat * 0.03; 

        amountField.setText(String.valueOf(amount));
        totalAmountWithVatField.setText(String.valueOf(totalAmountWithVat));
        latePaymentField.setText(String.valueOf(latePaymentCharge));

        
        saveInvoiceToFile(amount, totalAmountWithVat, latePaymentCharge);

        
        displayInvoiceFrame();
    }

    private boolean fieldsAreEmpty() {
        return nameField.getText().isEmpty()
                || meterIdField.getText().isEmpty()
                || unitConsumptionField.getText().isEmpty()
                || lastReadingField.getText().isEmpty()
                || presentReadingField.getText().isEmpty()
                || unitPerTkField.getText().isEmpty();
    }

    private void saveInvoiceToFile(double amount, double totalAmountWithVat, double latePaymentCharge) {
        try (FileWriter writer = new FileWriter("invoice.txt")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = formatter.format(new Date());

            writer.write("Electricity Bill Invoice\n");
            writer.write("Date: " + formattedDateTime + "\n\n");

            writer.write("Name: " + nameField.getText() + "\n");
            writer.write("Meter ID: " + meterIdField.getText() + "\n");
            writer.write("Unit Consumption:" + unitConsumptionField.getText() + "\n");
            writer.write("Last Reading:" + lastReadingField.getText() + "\n");
            writer.write("Present Reading:" + presentReadingField.getText() + "\n");
            writer.write("Unit Price (Tk):" + unitPerTkField.getText() + "\n");
            writer.write("\nInvoice Details:\n");
            writer.write("Amount: " + amount + "\n");
            writer.write("Total Amount with VAT: " + totalAmountWithVat + "\n");
            writer.write("Late Payment Charge: " + latePaymentCharge + "\n");

            
            JOptionPane.showMessageDialog(this, "Invoice saved", "Invoice Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving invoice", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayInvoiceFrame() {
        JFrame invoiceFrame = new JFrame("Invoice Preview");
        invoiceFrame.setSize(600, 400);

        JTextArea invoiceTextArea = new JTextArea();
        invoiceTextArea.setEditable(false);

        
        try (BufferedReader br = new BufferedReader(new FileReader("invoice.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                invoiceTextArea.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(invoiceTextArea);
        invoiceFrame.add(scrollPane);

        JButton saveButton = new JButton("Save Invoice");
        saveButton.addActionListener(new ActionListener() {
         
            public void actionPerformed(ActionEvent e) {
                
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showSaveDialog(BillInvoicePanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        Files.copy(Paths.get("invoice.txt"), Paths.get(selectedFile.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
                        JOptionPane.showMessageDialog(BillInvoicePanel.this, "Invoice saved to " + selectedFile.getAbsolutePath(), "Invoice Saved", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(BillInvoicePanel.this, "Error saving invoice", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        invoiceFrame.add(saveButton, BorderLayout.SOUTH);

        invoiceFrame.setLocationRelativeTo(null);
        invoiceFrame.setVisible(true);

        
        invoiceFrame.addWindowListener(new WindowAdapter() {
            
            public void windowClosed(WindowEvent e) {
                
            }
        });
    }

    private void deleteInvoiceFile() {
     
        File invoiceFile = new File("invoice.txt");
        if (invoiceFile.exists()) {
            invoiceFile.delete();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bill Invoice");
            frame.setSize(1200, 700);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            BillInvoicePanel invoicePanel = new BillInvoicePanel();
            frame.add(invoicePanel);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PayBill extends JFrame {
    private JTextField meterIdField;
    private JButton checkBillButton, payWithCardButton, payWithBkashButton,backButton;
    private JTextArea billDetailsArea;
	
	
	  public PayBill() {
        setTitle("Pay Bill");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = createInputPanel();
        JPanel displayPanel = createDisplayPanel();

        add(inputPanel, BorderLayout.NORTH);
        add(displayPanel, BorderLayout.CENTER);

        setSize(1200, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel meterIdLabel = new JLabel("Enter Meter ID:");
        meterIdField = new JTextField(10);
        checkBillButton = new JButton("Check Bill");
        payWithCardButton = new JButton("Pay with Card");
        payWithBkashButton = new JButton("Pay with Bkash");
		backButton = new JButton("Back");

        inputPanel.add(meterIdLabel);
        inputPanel.add(meterIdField);
        inputPanel.add(checkBillButton);
        inputPanel.add(payWithCardButton);
        inputPanel.add(payWithBkashButton);
		inputPanel.add(backButton); 

        checkBillButton.addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
                checkBill();
            }
        });

        payWithCardButton.addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
                showCardPaymentDialog();
            }
        });

        payWithBkashButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                showBkashPaymentDialog();
            }
        });
		 backButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                dispose();  
                new UserDashboard();  
            }
        });

        return inputPanel;
    }

    private JPanel createDisplayPanel() {
        JPanel displayPanel = new JPanel(new BorderLayout());

        billDetailsArea = new JTextArea();
        billDetailsArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(billDetailsArea);
        displayPanel.add(scrollPane, BorderLayout.CENTER);

        return displayPanel;
    }

   private void checkBill() {
    String meterId = meterIdField.getText();

    if (!meterId.isEmpty()) {
        double billAmount = getCurrentMonthBill(meterId);

        if (billAmount > 0 && !isAlreadyPaid(meterId)) {
            billDetailsArea.setText("Meter ID: " + meterId + "\nBill Amount: $" + billAmount);
        } else if (isAlreadyPaid(meterId)) {
            showMessage("Bill already paid for this month.");
        } else {
            showMessage("No pending bill to pay.");
        }
    } else {
        showMessage("Please enter Meter ID.");
    }
}

private double getCurrentMonthBill(String meterId) {
        try (BufferedReader br = new BufferedReader(new FileReader("invoice.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Meter ID: " + meterId)) {
                    for (int i = 0; i < 6; i++) {
                        br.readLine();
                    }
                    String amountLine = br.readLine();
                    return Double.parseDouble(amountLine.split(":")[1].trim());
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

private void showBkashPaymentDialog() {
    JDialog dialog = new JDialog(this, "Bkash Payment", true);
    dialog.setLayout(new BorderLayout());

    JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

    panel.add(new JLabel("Bkash Number:"));
    JTextField bkashNumberField = new JTextField();
    panel.add(bkashNumberField);

    panel.add(new JLabel("Bkash Password:"));
    JPasswordField bkashPasswordField = new JPasswordField();
    panel.add(bkashPasswordField);

    JButton payButton = new JButton("Pay");
    payButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String bkashNumber = bkashNumberField.getText();
            String bkashPassword = new String(bkashPasswordField.getPassword());

            if (!bkashNumber.isEmpty() && !bkashPassword.isEmpty()) {
                dialog.dispose();
                payWithBkash(bkashNumber, bkashPassword);
            } else {
                showMessage("Bkash payment canceled or invalid information provided.");
            }
        }
    });

    JButton cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(new ActionListener() {
       
        public void actionPerformed(ActionEvent e) {
            dialog.dispose();
            showMessage("Bkash payment canceled.");
        }
    });

    panel.add(payButton);
    panel.add(cancelButton);

    dialog.add(panel, BorderLayout.CENTER);
    dialog.setSize(500, 400);
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
}

private void showCardPaymentDialog() {
    JDialog dialog = new JDialog(this, "Card Payment", true);
    dialog.setLayout(new BorderLayout());

    JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

    panel.add(new JLabel("Card Number:"));
    JTextField cardNumberField = new JTextField();
    panel.add(cardNumberField);

    panel.add(new JLabel("Card Password:"));
    JPasswordField cardPasswordField = new JPasswordField();
    panel.add(cardPasswordField);

    JButton payButton = new JButton("Pay");
    payButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String cardNumber = cardNumberField.getText();
            String cardPassword = new String(cardPasswordField.getPassword());

            if (!cardNumber.isEmpty() && !cardPassword.isEmpty()) {
                dialog.dispose();
                payWithCard(cardNumber, cardPassword);
            } else {
                showMessage("Card payment canceled or invalid information provided.");
            }
        }
    });

    JButton cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(new ActionListener() {
       
        public void actionPerformed(ActionEvent e) {
            dialog.dispose();
            showMessage("Card payment canceled.");
        }
    });

    panel.add(payButton);
    panel.add(cancelButton);

    dialog.add(panel, BorderLayout.CENTER);
    dialog.setSize(500, 400);
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
}

private void payWithBkash(String bkashNumber, String bkashPassword) {
    String meterId = meterIdField.getText();

    if (!isAlreadyPaid(meterId)) {
        double billAmount = getCurrentMonthBill(meterId);

        if (billAmount > 0) {
            showMessage("Payment successful with Bkash. Amount: $" + billAmount);
            savePaymentDetails(meterId, billAmount);
        } else {
            showMessage("No pending bill to pay.");
        }
    } else {
        showMessage("Bill already paid for this month.");
    }
}

private void payWithCard(String cardNumber, String cardPassword) {
    String meterId = meterIdField.getText();

    if (!isAlreadyPaid(meterId)) {
        double billAmount = getCurrentMonthBill(meterId);

        if (billAmount > 0) {
            showMessage("Payment successful with Card. Amount: $" + billAmount);
            savePaymentDetails(meterId, billAmount);
        } else {
            showMessage("No pending bill to pay.");
        }
    } else {
        showMessage("Bill already paid for this month.");
    }
}
private boolean isAlreadyPaid(String meterId) {
        try (BufferedReader br = new BufferedReader(new FileReader("pay.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Meter ID: " + meterId + ", Status: Paid")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Payment Status", JOptionPane.INFORMATION_MESSAGE);
    }

    private void savePaymentDetails(String meterId, double amount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pay.txt", true))) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = now.format(formatter);

            writer.write("Meter ID: " + meterId + ", Amount: $" + amount + ", Date: " + formattedDate + ", Status: Paid");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Error saving payment details.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            
            public void run() {
                new PayBill();
            }
        });
    }
}
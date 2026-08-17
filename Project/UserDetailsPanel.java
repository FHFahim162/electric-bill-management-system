import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserDetailsPanel extends JFrame {

    private JTable userDetailsTable;
    private JButton backButton;
    private JButton removeButton;
    private JButton saveButton;

    public UserDetailsPanel() {
        initializeUI();
        loadDataFromFile();
    }

    private void initializeUI() {
        setTitle("User Details Panel");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        userDetailsTable = createModernJTable();
        JScrollPane scrollPane = new JScrollPane(userDetailsTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        backButton = createButton("Back");
        removeButton = createButton("Remove");
        saveButton = createButton("Save");

        buttonPanel.add(backButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
				new DashboardApp();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedRow();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDataToFile();
            }
        });

        backButton.setBackground(Color.decode("#14B1FF"));
        removeButton.setBackground(Color.decode("#FF14B1"));
        saveButton.setBackground(Color.decode("#B1FF14"));

        backButton.setForeground(Color.WHITE);
        removeButton.setForeground(Color.WHITE);
        saveButton.setForeground(Color.WHITE);

        backButton.setPreferredSize(new Dimension(300, 30));
        removeButton.setPreferredSize(new Dimension(300, 30));
        saveButton.setPreferredSize(new Dimension(300, 30));

        backButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        removeButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        saveButton.setFont(new Font("SansSerif", Font.BOLD, 12));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBackground(Color.decode("#B1FF14"));
        userDetailsTable.setDefaultRenderer(Object.class, renderer);

        setSize(1200, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JTable createModernJTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Mobile Number");
        model.addColumn("Present Address");
        model.addColumn("City");
        model.addColumn("House No");
        model.addColumn("Postal Code");
        model.addColumn("NID No");
        model.addColumn("Residential");
        model.addColumn("Gender");

        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Allow cell editing
                return true;
            }
        };

        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        return table;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(300, 30));
        button.setFont(new Font("SansSerif", Font.BOLD, 12));

        return button;
    }

    private void loadDataFromFile() {
        DefaultTableModel model = (DefaultTableModel) userDetailsTable.getModel();
        try (BufferedReader br = new BufferedReader(new FileReader("applicant.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDataToFile() {
        DefaultTableModel model = (DefaultTableModel) userDetailsTable.getModel();
        try (FileWriter writer = new FileWriter("applicant.txt")) {
            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    writer.write(model.getValueAt(row, col).toString());
                    if (col < model.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeSelectedRow() {
        DefaultTableModel model = (DefaultTableModel) userDetailsTable.getModel();
        int selectedRow = userDetailsTable.getSelectedRow();

        if (selectedRow != -1) {
            model.removeRow(selectedRow);
            saveDataToFile(); 
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserDetailsPanel();
            }
        });
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class EmployeeManagementSystem {
    private static ArrayList<Employee> employees = new ArrayList<>();
    private static DefaultTableModel tableModel;
    private static int nextEmployeeId = 1; // Auto-assigned Employee ID

    public static void main(String[] args) {

        //Theme for better UI
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        // Create the Frame for Login
        JFrame frame = new JFrame("Employee Management System - Login");
        
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create a Panel for the Form
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Username Label and Field
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(15);
        usernameField.setPreferredSize(new Dimension(250, 40));

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setPreferredSize(new Dimension(250, 40));

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30));

        // Add components to the panel using GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        // Add the panel to the frame
        frame.add(panel, BorderLayout.CENTER);

        // Add action listener to the login button
        loginButton.addActionListener(e -> {
            // Get input values
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Hardcoded validation
            if (username.equals("Admin") && password.equals("Admin")) {
                JOptionPane.showMessageDialog(frame, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose(); // Close the login screen
                // Proceed to the next screen (e.g., Employee Management System Main Screen)
                showEmployeeManagementScreen();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Center the frame and make it visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        
    }

    // Method to show the main Employee Management System Screen
    private static void showEmployeeManagementScreen() {
        addDefaultEmployees();
        // Create the main frame
        JFrame mainFrame = new JFrame("Employee Management System");
        mainFrame.setSize(900, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        // Create the Table for Employee List
        String[] columns = {"ID", "Name", "Position", "Department", "Age", "Salary", "Years of Service"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable employeeTable = new JTable(tableModel);
        employeeTable.setFont(new Font("Arial", Font.PLAIN, 12));
        mainFrame.add(new JScrollPane(employeeTable), BorderLayout.CENTER);

        refreshEmployeeTable();

        // Create a Panel for the Buttons (Add, Edit, Delete)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField searchField = new JTextField(20);
        JButton clearButton = new JButton("X");
        JButton searchButton = new JButton("Search");
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);

        JButton addButton = new JButton("Add Employee");
        addButton.setPreferredSize(new Dimension(125, 50));
        JButton editButton = new JButton("Edit Employee");
        editButton.setPreferredSize(new Dimension(125, 50));
        JButton deleteButton = new JButton("Delete Employee");
        deleteButton.setPreferredSize(new Dimension(125, 50));
        JButton sortButton = new JButton("Sort Employees");
        sortButton.setPreferredSize(new Dimension(125, 50));
        JButton filterButton = new JButton("Filter Department");
        filterButton.setPreferredSize(new Dimension(125, 50));

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(filterButton);

        mainFrame.add(buttonPanel, BorderLayout.SOUTH);
        mainFrame.add(searchPanel, BorderLayout.NORTH);
        

        // Action listeners for the buttons

        // Add Employee Action
        addButton.addActionListener(e -> {
            addEmployeeDialog(mainFrame);
        });

        // Edit Employee Action
        editButton.addActionListener(e -> {
            editEmployeeDialog(employeeTable);
        });

        // Delete Employee Action
        deleteButton.addActionListener(e -> {
            deleteEmployee(employeeTable);
        });

        // Show the sorting options in a new dialog
        sortButton.addActionListener(e -> {
            showSortingOptionsDialog(mainFrame);
        });

        filterButton.addActionListener(e -> {
            showFilterOptionsDialog(mainFrame);
        });

        clearButton.addActionListener(e -> {
            searchField.setText(""); // Clear the search field
            EmployeeSorter.sortByDefault(employees); // Reset the table from default
            refreshEmployeeTable();  // Reset the table to show all employees
            
        });

        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            if (!query.isEmpty()) {
                EmployeeSearch.searchEmployee(employees, tableModel, query);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please enter a search query.",
                                              "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Show the frame
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    private static void addDefaultEmployees() {
        employees.add(new Employee(nextEmployeeId++, "Juan A. Santos", "Manager", "HR", 35, 50000, 7));
        employees.add(new Employee(nextEmployeeId++, "Maria B. Reyes", "HR Specialist", "HR", 28, 55000, 4));
        employees.add(new Employee(nextEmployeeId++, "Jose C. Perez", "Accountant", "Finance", 40, 45000, 10));
        employees.add(new Employee(nextEmployeeId++, "Myrna S. Flores", "Marketing Specialist", "Marketing", 30, 55000, 5));
        employees.add(new Employee(nextEmployeeId++, "Ricardo D. Cruz", "Graphic Designer", "Marketing", 25, 40000, 2));
        employees.add(new Employee(nextEmployeeId++, "Lucia E. Mercado", "HR Specialist", "HR", 32, 52000, 6));
        employees.add(new Employee(nextEmployeeId++, "Pedro F. Aquino", "Software Developer", "IT", 27, 62000, 4));
        employees.add(new Employee(nextEmployeeId++, "Ana G. Torres", "Accountant", "Finance", 38, 48000, 8));
        employees.add(new Employee(nextEmployeeId++, "Luis H. Bautista", "Sales Manager", "Marketing", 42, 70000, 12));
        employees.add(new Employee(nextEmployeeId++, "Elena I. Villanueva", "IT Support Specialist", "IT", 29, 35000, 3));
        
        
    }

    // Add Employee Dialog Method
    private static void addEmployeeDialog(JFrame parentFrame) {
        JDialog addDialog = new JDialog(parentFrame, "Add Employee", true);
        addDialog.setSize(400, 400);
        addDialog.setLayout(new GridLayout(8, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel positionLabel = new JLabel("Position:");
        JTextField positionField = new JTextField();
        JLabel departmentLabel = new JLabel("Department:");
        JComboBox<String> departmentField = new JComboBox<>(new String[]{"HR", "Finance", "IT", "Marketing"});
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        JLabel salaryLabel = new JLabel("Salary:");
        JTextField salaryField = new JTextField();
        JLabel yearsOfServiceLabel = new JLabel("Years of Service:");
        JTextField yearsOfServiceField = new JTextField();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        addDialog.add(nameLabel);
        addDialog.add(nameField);
        addDialog.add(positionLabel);
        addDialog.add(positionField);
        addDialog.add(departmentLabel);
        addDialog.add(departmentField);
        addDialog.add(ageLabel);
        addDialog.add(ageField);
        addDialog.add(salaryLabel);
        addDialog.add(salaryField);
        addDialog.add(yearsOfServiceLabel);
        addDialog.add(yearsOfServiceField);
        addDialog.add(saveButton);
        addDialog.add(cancelButton);

        // Save Employee Action
        saveButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String position = positionField.getText();
                String department = (String) departmentField.getSelectedItem();
                int age = Integer.parseInt(ageField.getText());
                double salary = Double.parseDouble(salaryField.getText());
                int yearsOfService = Integer.parseInt(yearsOfServiceField.getText());

                Employee employee = new Employee(nextEmployeeId++, name, position, department, age, salary, yearsOfService);
                employees.add(employee);
                tableModel.addRow(employee.toTableRow());
                addDialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addDialog, "Invalid input. Please check the details and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Cancel Action
        cancelButton.addActionListener(e -> addDialog.dispose());

        addDialog.setLocationRelativeTo(parentFrame);
        addDialog.setVisible(true);
    }

    // Edit Employee Dialog Method
    private static void editEmployeeDialog(JTable employeeTable) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(employeeTable, "Please select an employee to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Employee employee = employees.get(selectedRow);

        JDialog editDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(employeeTable), "Edit Employee", true);
        editDialog.setSize(400, 400);
        editDialog.setLayout(new GridLayout(8, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(employee.getName());
        JLabel positionLabel = new JLabel("Position:");
        JTextField positionField = new JTextField(employee.getPosition());
        JLabel departmentLabel = new JLabel("Department:");
        JComboBox<String> departmentField = new JComboBox<>(new String[]{"HR", "Finance", "IT", "Marketing"});
        departmentField.setSelectedItem(employee.getDepartment());
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(String.valueOf(employee.getAge()));
        JLabel salaryLabel = new JLabel("Salary:");
        JTextField salaryField = new JTextField(String.valueOf(employee.getSalary()));
        JLabel yearsOfServiceLabel = new JLabel("Years of Service:");
        JTextField yearsOfServiceField = new JTextField(String.valueOf(employee.getYearsOfService()));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        editDialog.add(nameLabel);
        editDialog.add(nameField);
        editDialog.add(positionLabel);
        editDialog.add(positionField);
        editDialog.add(departmentLabel);
        editDialog.add(departmentField);
        editDialog.add(ageLabel);
        editDialog.add(ageField);
        editDialog.add(salaryLabel);
        editDialog.add(salaryField);
        editDialog.add(yearsOfServiceLabel);
        editDialog.add(yearsOfServiceField);
        editDialog.add(saveButton);
        editDialog.add(cancelButton);

        // Save Employee Action
        saveButton.addActionListener(e -> {
            try {
                employee.setName(nameField.getText());
                employee.setPosition(positionField.getText());
                employee.setDepartment((String) departmentField.getSelectedItem());
                employee.setAge(Integer.parseInt(ageField.getText()));
                employee.setSalary(Double.parseDouble(salaryField.getText()));
                employee.setYearsOfService(Integer.parseInt(yearsOfServiceField.getText()));

                // Update the table
                tableModel.setValueAt(employee.getName(), selectedRow, 1);
                tableModel.setValueAt(employee.getPosition(), selectedRow, 2);
                tableModel.setValueAt(employee.getDepartment(), selectedRow, 3);
                tableModel.setValueAt(employee.getAge(), selectedRow, 4);
                tableModel.setValueAt(employee.getSalary(), selectedRow, 5);
                tableModel.setValueAt(employee.getYearsOfService(), selectedRow, 6);

                editDialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editDialog, "Invalid input. Please check the details and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Cancel Action
        cancelButton.addActionListener(e -> editDialog.dispose());

        editDialog.setLocationRelativeTo(employeeTable);
        editDialog.setVisible(true);
    }

    // Delete Employee Method
    private static void deleteEmployee(JTable employeeTable) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(employeeTable, "Please select an employee to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(employeeTable, "Are you sure you want to delete this employee?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            employees.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        }
    }
    private static void showSortingOptionsDialog(JFrame parentFrame) {
        JDialog sortingDialog = new JDialog(parentFrame, "Sort Employees", true);
        sortingDialog.setSize(300, 200);
        sortingDialog.setLayout(new GridLayout(3, 2));
    
        JLabel sortByLabel = new JLabel("Sort By:");
        JComboBox<String> sortByComboBox = new JComboBox<>(new String[]{"Name","Age", "Salary", "Years of Service"});
        JButton sortButton = new JButton("Sort");
    
        sortingDialog.add(sortByLabel);
        sortingDialog.add(sortByComboBox);
        sortingDialog.add(sortButton);
    
        // Sort button action
        sortButton.addActionListener(e -> {
            String selectedOption = (String) sortByComboBox.getSelectedItem();
            
            switch (selectedOption) {
                case "Name":
                    EmployeeSorter.sortByName(employees);
                    break;
                case "Age":
                    EmployeeSorter.sortByAge(employees);
                    break;
                case "Salary":
                    EmployeeSorter.sortBySalary(employees);
                    break;
                case "Years of Service":
                    EmployeeSorter.sortByYearsOfService(employees);
                    break;
                default:
                    break;
            }
    
            // Refresh the table after sorting
            refreshEmployeeTable();
            sortingDialog.dispose();
        });
    
        sortingDialog.setLocationRelativeTo(parentFrame);
        sortingDialog.setVisible(true);
    }
    
    // Method to refresh the employee table
    private static void refreshEmployeeTable() {
        tableModel.setRowCount(0); // Clear all rows
        
        // Add sorted employees back to the table
        for (Employee employee : employees) {
            tableModel.addRow(employee.toTableRow());
        }
        
        // Notify the table model that the data has changed
        tableModel.fireTableDataChanged();
    }
    private static void showFilterOptionsDialog(JFrame parentFrame) {
        JDialog filterDialog = new JDialog(parentFrame, "Filter Employees", true);
        filterDialog.setSize(300, 200);
        filterDialog.setLayout(new GridLayout(3, 2));
    
        JLabel departmentLabel = new JLabel("Select Department:");
        JComboBox<String> departmentComboBox = new JComboBox<>(new String[]{"HR", "Finance", "IT", "Marketing"});
        JButton filterButton = new JButton("Filter");
    
        filterDialog.add(departmentLabel);
        filterDialog.add(departmentComboBox);
        filterDialog.add(filterButton);
    
        // Filter button action
        filterButton.addActionListener(e -> {
            String selectedDepartment = (String) departmentComboBox.getSelectedItem();
    
            // Filter employees based on the selected department
            EmployeeFilter.filterAndDisplayEmployees(selectedDepartment, employees, tableModel);
    
            // Close the filter dialog after filtering
            filterDialog.dispose();
        });
    
        filterDialog.setLocationRelativeTo(parentFrame); // Center the dialog relative to the parent frame
        filterDialog.setVisible(true);
    }
    
}
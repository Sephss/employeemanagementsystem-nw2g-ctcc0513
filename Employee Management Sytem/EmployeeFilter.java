import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFilter {

    // Method to filter employees by department (e.g., HR)
    public static List<Employee> filterEmployeesByDepartment(String department, List<Employee> employees) {
        List<Employee> filteredEmployees = new ArrayList<>();

        // Loop through all employees and filter based on department
        for (Employee employee : employees) {
            if (employee.getDepartment().equals(department)) {
                filteredEmployees.add(employee);
            }
        }

        return filteredEmployees;  // Return the filtered list
    }

    // Method to update the table after filtering
    public static void filterAndDisplayEmployees(String department, List<Employee> employees, DefaultTableModel tableModel) {
        // Get filtered employees by department
        List<Employee> filteredEmployees = filterEmployeesByDepartment(department, employees);

        // Clear existing rows in the table
        tableModel.setRowCount(0);

        if (filteredEmployees.isEmpty()) {
            // If no employees match the filter, display a message
            JOptionPane.showMessageDialog(null, "No employees found in the " + department + " department.", "No Results", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            // Add the filtered employees to the table
            for (Employee employee : filteredEmployees) {
                tableModel.addRow(employee.toTableRow());
            }
        }

        
    }
}

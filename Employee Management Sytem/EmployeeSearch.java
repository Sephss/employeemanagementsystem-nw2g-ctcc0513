import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class EmployeeSearch {

    // Method to perform search on employees
    public static void searchEmployee(ArrayList<Employee> employees, DefaultTableModel tableModel, String query) {
        // Clear the table before showing search results
        tableModel.setRowCount(0);

        boolean found = false;

        // Linear search through the ArrayList
        for (Employee employee : employees) {
            if (employee.getName().toLowerCase().contains(query.toLowerCase()) ||
                String.valueOf(employee.getId()).equals(query)) {
                // Add matching employee to the table
                tableModel.addRow(employee.toTableRow());
                found = true;
            }
        }

        // If no results are found, notify the user
        if (!found) {
            JOptionPane.showMessageDialog(null, "No employees found for the query: " + query, 
                                          "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

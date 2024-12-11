import java.util.ArrayList;
import java.util.Comparator;

public class EmployeeSorter {
    private static ArrayList<Employee> originalOrder;

    // Sort by Name (Alphabetical Order)
    public static void sortByName(ArrayList<Employee> employees) {
        employees.sort(Comparator.comparing(Employee::getName));
    }

    // Sort by Salary (Ascending)
    public static void sortBySalary(ArrayList<Employee> employees) {
        employees.sort(Comparator.comparingDouble(Employee::getSalary));
    }

    // Sort by Years of Service (Ascending)
    public static void sortByYearsOfService(ArrayList<Employee> employees) {
        employees.sort(Comparator.comparingInt(Employee::getYearsOfService));
    }
    // Sort by Age (Ascending)
    public static void sortByAge(ArrayList<Employee> employees) {
        employees.sort(Comparator.comparingInt(Employee::getAge));
    }
    // When Clear button is klicked this part will work so the employee data will be
    // back on its original position
    public static void sortByDefault(ArrayList<Employee> employees) {
        employees.sort(Comparator.comparingInt(Employee::getId));
    }
   
   
}

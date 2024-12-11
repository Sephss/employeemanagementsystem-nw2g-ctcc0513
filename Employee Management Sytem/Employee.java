class Employee {
    private int id;
    private String name;
    private String position;
    private String department;
    private int age;
    private double salary;
    private int yearsOfService;

    public Employee(int id, String name, String position, String department, int age, double salary, int yearsOfService) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.department = department;
        this.age = age;
        this.salary = salary;
        this.yearsOfService = yearsOfService;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public int getYearsOfService() {
        return yearsOfService;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setYearsOfService(int yearsOfService) {
        this.yearsOfService = yearsOfService;
    }

    // Convert Employee to Table Row Object
    public Object[] toTableRow() {
        return new Object[]{id, name, position, department, age, salary, yearsOfService};
    }
}
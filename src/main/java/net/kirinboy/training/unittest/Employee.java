package net.kirinboy.training.unittest;

public class Employee {
    private long id;
    private String name;

    public Employee(long employeeId, String name) {
        this.id = employeeId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

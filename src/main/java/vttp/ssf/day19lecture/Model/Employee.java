package vttp.ssf.day19lecture.Model;

import java.io.Serializable;

public class Employee implements Serializable { // For errors where payload is not serialised.
    
    private String name;
    private Long employeeId;


    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Long getEmployeeId() { return employeeId; }

    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    @Override
    public String toString() {
        return "Employee [name=" + name + ", employeeId=" + employeeId + "]";
    }

}

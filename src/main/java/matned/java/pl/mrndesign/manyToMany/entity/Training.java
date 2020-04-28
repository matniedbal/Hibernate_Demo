package matned.java.pl.mrndesign.manyToMany.entity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name= "training")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_training")
    private int idTraining;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(name = "employee_training", joinColumns = @JoinColumn (name = "id_training"), inverseJoinColumns = @JoinColumn(name = "id_employee"))
    private List<Employee> employees;

    public Training() {
    }

    public Training(String name) {
        this.name = name;
    }

    public int getIdTraining() {
        return idTraining;
    }

    public void setIdTraining(int idTraining) {
        this.idTraining = idTraining;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employee employee){
        if(employees == null) employees = new LinkedList<>();
        employees.add(employee);
    }

    @Override
    public String toString() {
        return "Training{" +
                "idTraining=" + idTraining +
                ", name='" + name + '\'' +
                '}';
    }
}

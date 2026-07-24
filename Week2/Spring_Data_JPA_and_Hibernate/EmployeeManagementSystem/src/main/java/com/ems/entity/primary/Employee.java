package com.ems.entity.primary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "employees")
@DynamicInsert // Optimize SQL statements by including only non-null columns (Exercise 10)
@DynamicUpdate // Optimize SQL statements by including only modified columns (Exercise 10)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "department", callSuper = true)
@EqualsAndHashCode(exclude = "department", callSuper = false)
@NamedQueries({
    @NamedQuery(
        name = "Employee.findByEmailNamed",
        query = "SELECT e FROM Employee e WHERE e.email = :email"
    ),
    @NamedQuery(
        name = "Employee.findByDepartmentNameNamed",
        query = "SELECT e FROM Employee e WHERE e.department.name = :deptName"
    )
})
public class Employee extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonBackReference // Avoid infinite recursion during JSON serialization
    private Department department;
}

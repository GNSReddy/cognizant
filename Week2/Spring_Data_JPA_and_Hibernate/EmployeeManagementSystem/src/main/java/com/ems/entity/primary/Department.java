package com.ems.entity.primary;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "employees", callSuper = true)
@EqualsAndHashCode(exclude = "employees", callSuper = false)
public class Department extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @BatchSize(size = 10) // Optimize loading of the collection to prevent N+1 queries (Exercise 10)
    @JsonManagedReference // Avoid infinite recursion during JSON serialization
    @Builder.Default
    private List<Employee> employees = new ArrayList<>();
}

package vendor_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vendor_management.entity.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {

    List<Employee> findByActiveTrue();
    @Query("""
    SELECT e
    FROM Employee e
    WHERE e.id NOT IN (
        SELECT d.employee.id
        FROM Delivery d
        WHERE d.employee IS NOT NULL
    )
""")
    List<Employee> findUnassignedEmployees();

    @Query("""
SELECT e FROM Employee e
WHERE e.id NOT IN (
    SELECT d.employee.id
    FROM Delivery d
    WHERE d.employee IS NOT NULL
)
""")
    List<Employee> findAvailableEmployees();


}
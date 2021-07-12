package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Employee;

import java.util.List;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Long> {

    //TODO Get a list of employees receiving a salary greater than that of the boss
    @Query(
            value = "select e.* from employee e where (select salary from employee where id = e.boss_id) < salary",
            nativeQuery = true)
    List<Employee> findAllWhereSalaryGreaterThatBoss();

    //TODO Get a list of employees receiving the maximum salary in their department
    @Query(
            value = "select e1.* from employee e1 inner join" +
                    "(select e.department_id, max(e.salary) as maxSalary from employee e group by e.department_id)" +
                    "groupped on e1.salary = groupped.maxSalary and e1.department_id = groupped.department_id;",
            nativeQuery = true)
    List<Employee> findAllByMaxSalary();

    //TODO Get a list of employees who do not have boss in the same department
    @Query(
            value = "select e.* from employee e where boss_id is null",
            nativeQuery = true)
    List<Employee> findAllWithoutBoss();
}

package vttp.ssf.day19lecture.Repository;

import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import vttp.ssf.day19lecture.Model.Employee;

@Repository
public class EmployeeRepository {

    private String hashReference = "employees";
    
    @Resource(name = "redisEmployeeTemplate") // Similar to @Autowired.
    private HashOperations<String, String, Employee> hashOps;

    // If you are using ListOperations to access redis data in a list.
    // private ListOperations<String, Employee> listOps;

    public void saveRecord(Employee e) {
        hashOps.put(hashReference, e.getEmployeeId().toString(), e);
    }

    public Employee getRecord(Integer id) {
        Employee e = hashOps.get(hashReference, String.valueOf(id));
        return e;
    }

    public Map<String, Employee> getAll() {
        Map<String, Employee> map = hashOps.entries(hashReference);
        return map;

    }
}

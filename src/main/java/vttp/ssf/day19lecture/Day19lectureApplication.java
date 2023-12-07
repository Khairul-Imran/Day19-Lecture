package vttp.ssf.day19lecture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.ssf.day19lecture.Model.Employee;
import vttp.ssf.day19lecture.Repository.EmployeeRepository;

@SpringBootApplication
public class Day19lectureApplication implements CommandLineRunner { // Must know how to do.

	@Autowired
	EmployeeRepository employeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(Day19lectureApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// To be read.
		String filePathName = "/Users/khairulimran/data/employees.json"; // We are hard coding this, but you can pass it as an argument too.

		String filePathName2 = "/Users/khairulimran/data/employee2.json";

		// Read the file, and make it into a string.
		File file = new File(filePathName);

		if (!file.exists()) {
			System.err.print("File not found.");
			System.exit(1);
		}

		InputStream is = new FileInputStream(file);
		// BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream())); From old practice. - just for reference.

		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String line = "";
			while((line = br.readLine()) != null) { // Read the file until it is complete.
				resultStringBuilder.append(line);
			}
		}

		String data = resultStringBuilder.toString();
		System.out.println(data);

		// Parsing the data we read.
		JSONParser jsonParser = new JSONParser();
		Object object = jsonParser.parse(data);

		JSONArray jsonArray = (JSONArray) object; // Casting the object into a Json array.
		System.out.println("Json Array Size: " + jsonArray.size());
		System.out.println("Json Array Object: " +jsonArray);

		List<Employee> employees = new ArrayList<>();

		jsonArray.forEach(emp -> {
			// System.out.println(emp);

			// Call the parseEmploeeObject method.
			Employee employee1 = parseEmployeeObject((JSONObject) emp);
			employees.add(employee1);
		});

		System.out.println("List of employees: " + employees);

		for (Employee employee : employees) {
			employeeRepository.saveRecord(employee);
		}

		// Some issue here.
		Employee retrievedEmployee = employeeRepository.getRecord(12345);
		System.out.println("Retrieved employee: " + retrievedEmployee);

	}

	private Employee parseEmployeeObject(JSONObject jsonEmployee) { // When parsing, you need the key -> "employee".
		JSONObject jsonEmployeeObject = (JSONObject) jsonEmployee.get("employee"); // Get the key.
		
		Employee employee = new Employee();
		employee.setEmployeeId((Long)jsonEmployeeObject.get("employeeId"));
		employee.setName((String)jsonEmployeeObject.get("employeeName"));

		System.out.println(jsonEmployeeObject);
		System.out.println(employee);

		return employee;
	}

}

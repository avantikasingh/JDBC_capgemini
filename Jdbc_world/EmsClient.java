/**
 * 
 */
package com.cg.jdbc.ems.client;

import java.math.BigDecimal;
import java.util.List;

import com.cg.jdbc.ems.dao.EmployeeDao;
import com.cg.jdbc.ems.dao.IEmployeeDao;
import com.cg.jdbc.ems.exception.EmployeeException;
import com.cg.jdbc.ems.model.Employee;

/**
 * @author rvikash
 *
 */
public class EmsClient {
	// obj of Dao
	private static IEmployeeDao employeeDao;
	static {
		employeeDao= new EmployeeDao();
	}
	/**
	 * @param args
	 * @throws EmployeeException 
	 */
	public static void main(String[] args) throws EmployeeException {
		Employee employee = new Employee();
		employee.setEmpName("Tara");employee.setEmpSal(BigDecimal.valueOf(7000.0));
//adding the emp obj by calling dao layer method
		employee = employeeDao.addEmployee(employee);
		if(employee!= null) System.out.println("Employee Added successfully :"+employee);
		else System.out.println("Employee NOT Added :"+employee);
		
//listing all emp obj by calling dao layer method
		List<Employee> empList = employeeDao.listAllEmployees();
		if(empList!= null)
			empList.forEach(System.out::println);
		else
			System.out.println("No Record Found!!");
	}

}

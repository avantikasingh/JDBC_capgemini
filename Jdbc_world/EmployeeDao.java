/**
 * 
 */
package com.cg.jdbc.ems.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cg.jdbc.ems.exception.EmployeeException;
import com.cg.jdbc.ems.exception.MyException;
import com.cg.jdbc.ems.model.Employee;
import com.cg.jdbc.ems.util.DBUtil;

/**
 * @author rvikash
 *
 */
public class EmployeeDao implements IEmployeeDao {
	//prep -work 1- Connection
	private static Connection connection;
	private PreparedStatement ps;
	private ResultSet rs;
	
	static {
		try {
			connection= DBUtil.getConnection();
		} catch (MyException e) {
			System.out.print("Connection Not Obtained at EmployeeDao");
		}
	}
	

	/* (non-Javadoc)
	 * @see com.cg.jdbc.ems.dao.IEmployeeDao#addEmployee(com.cg.jdbc.ems.model.Employee)
	 */
	@Override
	public Employee addEmployee(Employee employee) throws EmployeeException {
		String sql ="insert into my_emp(emp_name,emp_sal) values(?,?)";		
		try {
		//step1 : obtain ps
			ps= connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		//step 2: set the ps placeholder values
			ps.setString(1, employee.getEmpName());
			ps.setBigDecimal(2, employee.getEmpSal());			
		//step 3: execute Query (for DML we have executeUpdate method )
			int noOfRec = ps.executeUpdate();
		//getting the auto-generated value
			BigInteger generatedId = BigInteger.valueOf(0L);
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				generatedId = BigInteger.valueOf(rs.getLong(1));
				 System.out.println("Auto generated Id " + generatedId);
			}
		//setting the auto-generated Id to current emp obj
			employee.setEmpId(generatedId);
		} catch (SQLException e) {
			System.out.println(" Error at addEmployee Dao method : "+e);
		}finally {
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println(" Error at addEmployee Dao method : "+e);
				}
			}
		}
		return employee;
	}

	/* (non-Javadoc)
	 * @see com.cg.jdbc.ems.dao.IEmployeeDao#listAllEmployees()
	 */
	@Override
	public List<Employee> listAllEmployees() throws EmployeeException {
		String sql ="select * from my_emp";
		List<Employee> empList = new ArrayList<Employee>();	
		try {
			ps= connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			//for select queries we have executeQuery method which returns ResultSet
			rs= ps.executeQuery();
			while (rs.next()) {
				//create employee obj
				Employee emp = new Employee();
				//get the value from rs and set to emp obj
				emp.setEmpId(BigInteger.valueOf(rs.getLong(1)));
				emp.setEmpName(rs.getString("emp_name"));
				emp.setEmpSal(rs.getBigDecimal("emp_sal"));				
				//add the emp obj to empList
				empList.add(emp);
				
			}
		} catch (SQLException e) {
			System.out.println(" Error at addEmployee Dao method : "+e);
		}finally {
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println(" Error at addEmployee Dao method : "+e);
				}
			}
		}
		return empList;
	}

}

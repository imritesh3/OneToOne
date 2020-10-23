package one2one;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Client {
	
private static SessionFactory factory;
	
	public static void getSessionFactory() {
		
		try {
			Configuration conf = new Configuration().configure();
			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(conf.getProperties());
			factory = conf.buildSessionFactory(builder.build());
		}
		catch(Throwable ex) {
			System.err.println("Failed to create sessionFactory object:"+ex);
			throw new ExceptionInInitializerError(ex);
		}
		
	}
	
	public static void main(String args[]) throws InterruptedException{
		getSessionFactory();
		
		Client client_1 = new Client();
		
		Address add1 = new Address("James Street","Secunderabad","Telangana","500003");
		Employee emp1 = new Employee(1,"Ritesh","Yadav",10000);
		emp1.setAddress(add1);
		client_1.insertData(emp1,add1);
		
		Address add2 = new Address("Dombivilli","Pune","Maharasthra","200098");
		Employee emp2 = new Employee(2,"Varun","Yadav",20000);
		emp2.setAddress(add2);
		client_1.insertData(emp2,add2);
		
		client_1.displayRecords();
	}
	
	public void insertData(Employee empObj , Address addObj) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		session.save(empObj);
		session.save(addObj);
		tx.commit();
		System.out.println("Employee and Address are inserted in the table");
		session.close();
	}
	
	public void displayRecords()throws InterruptedException{
		Session session = factory.openSession();
		List employees = session.createQuery("FROM Employee").list();
		
		for(Iterator iterator = employees.iterator();iterator.hasNext();) {
			Employee employee = (Employee) iterator.next();
			System.out.println("First Name: "+employee.getFirstName());
			System.out.println("Last Name: "+employee.getLastName());
			System.out.println("Salary: "+employee.getSalary());
			
			Address add = employee.getAddress();
			System.out.println("Address ");
			System.out.println("\t Street: "+add.getStreet());
			System.out.println("\t City: "+add.getCity());
			System.out.println("\t State: "+add.getState());
			System.out.println("\t Zipcode: "+add.getZipcode());
			session.close();

		}
	}

}
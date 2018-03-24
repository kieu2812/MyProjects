package demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import demo.entity.Course;
import demo.entity.Instructor;
import demo.entity.InstructorDetail;

public class FecthJoinDemo {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.addAnnotatedClass(Course.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {			
			// start a transaction
			session.beginTransaction();
						
			int idInstructor =1;
			//get instructor from database
			//OPTION 2: Hibernate Query with HQL
			Query<Instructor> query =
					session.createQuery("Select i from Instructor i "
					+ "JOIN FETCH i.courses "
					+ "where i.id=:theInstructorId", Instructor.class);
			
			//set parameter on query
			query.setParameter("theInstructorId", idInstructor);
			
			//execute query and get instructor
			Instructor tempInstructor = query.getSingleResult();
			
			
			System.out.println("luv2code: Instructor: " + tempInstructor);
			
				
			
			// commit transaction
			session.getTransaction().commit();
			session.close();
			
			
			//get course from instructor after close session 
			System.out.println("luv2code: Course: " + tempInstructor.getCourses());
			
			System.out.println("luv2code: Done!");
		}
		finally {
			
			factory.close();
		}
	}

}






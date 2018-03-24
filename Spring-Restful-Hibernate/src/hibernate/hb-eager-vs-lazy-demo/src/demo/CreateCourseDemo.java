package demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import demo.entity.Course;
import demo.entity.Instructor;
import demo.entity.InstructorDetail;

public class CreateCourseDemo {

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
			Instructor tempInstructor = session.get(Instructor.class, idInstructor);
			
			// create the objects
			Course course1 = new Course("Java Spring framework hibernate");
			Course course3 = new Course("Java Spring framework MVC");
			Course course2 = new Course("Security spring framework");
			
			// associate the objects
			tempInstructor.add(course1);
			tempInstructor.add(course2);
			tempInstructor.add(course3);
			
			// save the course
			session.save(course1);
			session.save(course2);
			session.save(course3);
			
			System.out.println("Saving instructor: " + tempInstructor);
			
			
			
			
			
			// commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");
		}
		finally {
			session.close();
			factory.close();
		}
	}

}






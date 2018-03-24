package demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import demo.entity.Course;
import demo.entity.Instructor;
import demo.entity.InstructorDetail;

public class GetCourseInstructorDemo {

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
			

			System.out.println("Instructor: " + tempInstructor);
			//get course from instructor
			System.out.println("Course: " + tempInstructor.getCourses());
			
			
			
			
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






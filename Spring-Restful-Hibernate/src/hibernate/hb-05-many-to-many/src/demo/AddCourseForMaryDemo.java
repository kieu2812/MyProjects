package demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import demo.entity.Course;
import demo.entity.Instructor;
import demo.entity.InstructorDetail;
import demo.entity.Review;
import demo.entity.Student;

public class AddCourseForMaryDemo {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.addAnnotatedClass(Course.class)
								.addAnnotatedClass(Review.class)
								.addAnnotatedClass(Student.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {			
			// start a transaction
			session.beginTransaction();
			// create the objects
			Course course1 = new Course("Hibernate mapping");

			// save the course
			System.out.println("Saving course: " + course1);

			session.save(course1);

			System.out.println("Saved the course");
			
			//create the student
			Student st1 = new Student("JOhn","Doe","johndoe@luv2code.com");
			Student st2 = new Student("Mary","Public","marypublic@luv2code.com");
			

			course1.addStudent(st1);
			course1.addStudent(st2);

			//save the student

			System.out.println("Saving student: " + st1 );
			System.out.println("Saving student: " + st2 );
			session.save(st1);
			session.save(st2);
			
			System.out.println("Saved the student");

					
			
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






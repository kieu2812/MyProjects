package demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import demo.entity.Course;
import demo.entity.Instructor;
import demo.entity.InstructorDetail;
import demo.entity.Review;
import demo.entity.Student;

public class CreateCourseAndStudentDemo {

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
			
			//get the studnet mary from database
			int theId=2;
			Student st1= session.get(Student.class,theId);
			
			System.out.println("Loaded Student " +st1);
			System.out.println("With Courses: " +st1.getCourses());
			// create the objects
			Course course1 = new Course("Rubik's cube - how to speed cube");
			Course course2 = new Course("Game Development");
			
			//add strudent to the course
			
			st1.addCourse(course1);
			st1.addCourse(course2);
			
		
			//save the student
			session.save(st1);
			session.save(course1);
			session.save(course2);
			
			System.out.println("Saving student: " + course1 );
			System.out.println("Saving student: " + course2);
			
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






package demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import demo.entity.Course;
import demo.entity.Instructor;
import demo.entity.InstructorDetail;

public class DeleteCourseDemo {

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
						
			int idCourse =14;
			//get instructor from database
			Course course = session.get(Course.class, idCourse);
			
			if(course!=null){
				System.out.println("Course: " + course);
				
				session.delete(course);
				System.out.println("Done!");

			}
			else{
				System.out.println("Can't find that course");
			}
			// commit transaction
			session.getTransaction().commit();
			
		}
		finally {
			session.close();
			factory.close();
		}
	}

}






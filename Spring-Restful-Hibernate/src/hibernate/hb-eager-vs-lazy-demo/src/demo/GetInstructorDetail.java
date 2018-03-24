package demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import demo.entity.Instructor;
import demo.entity.InstructorDetail;

public class GetInstructorDetail {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {			
			
			// start a transaction
			session.beginTransaction();
						
			int theId=34;
			InstructorDetail insDetail = session.get(InstructorDetail.class,theId);
			
			System.out.println("Instructor detail: " + insDetail);
			
			System.out.println("Instructor : " + insDetail.getInstructor());
			
			
			
			// commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally {
			//handle connection leak issue
			session.close();
			factory.close();
		}
	}

}






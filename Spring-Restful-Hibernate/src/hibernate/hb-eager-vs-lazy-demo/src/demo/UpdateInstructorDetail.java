package demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import demo.entity.Instructor;
import demo.entity.InstructorDetail;

public class UpdateInstructorDetail {

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
						
			int theId=4;
			InstructorDetail insDetail = session.get(InstructorDetail.class,theId);
			
			System.out.println("Instructor detail: " + insDetail);
			
			Instructor ins = insDetail.getInstructor();
			System.out.println("Instructor : " +ins);
			
			
			ins.setEmail("kieu2812@gmail.com");
			
			session.save(insDetail);
			
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






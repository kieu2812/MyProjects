package Tasks;

import java.util.Arrays;

public class TaskMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String [] tasks = null;
		try {
			
			for (int i = 0; i < args.length; i++) {
				if ((args[i].equals("-tasks") || args[i].equals("--tasks")) && (i < args.length - 1)) {
					tasks = args[i+1].split(",|;|\\|");
				}				
			}
			
			System.out.println("task(s): " + Arrays.toString(tasks));
			
			TaskBase task = null;
			if (tasks != null && tasks.length > 0) {
				for (int i = 0; i < tasks.length; i++) {
					if (tasks[i].equalsIgnoreCase("Ebay")) {
						task = new EbayByJSoup();						
					} else if (tasks[i].equalsIgnoreCase("Amazon")) {
						task = new AmazonJSoup();
					}
					task.pullData();
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

}

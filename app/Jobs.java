import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;

class Jobs{

    public static List<Job> jobs;
    public static List<User> applicants;
	public static HashMap<Integer,Integer> applications;

    public static synchronized void readJobs(){
        if(null == jobs){
            jobs = new ArrayList<Job>();
            String File = ("openjobs.csv");
            Scanner scan = new Scanner (Jobs.class.getResourceAsStream(File));
            String line;
            //while((line = scan.nextLine()) != null){
			while( scan.hasNext()){
				line = scan.nextLine();
                String[] tokens = line.split(",");
                jobs.add(new Job(Integer.parseInt(tokens[0]), tokens[1], tokens[2], Float.parseFloat(tokens[3]), tokens[4], Integer.parseInt(tokens[5]),Boolean.parseBoolean(tokens[6])));
                //System.out.println(tokens[0]);
            }

            scan.close();
        }
        
    }

	public static synchronized void readApplications(){
        if(null == applications){
            applications = new HashMap<Integer,Integer>();
            String File = ("applicantsToJobs.csv");
            Scanner scan = new Scanner (Jobs.class.getResourceAsStream(File));
            String line;
            //while((line = scan.nextLine()) != null){
			while( scan.hasNext()){
				line = scan.nextLine();
                String[] tokens = line.split(",");
                applications.put(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]));
                System.out.println(tokens[0] + tokens[1]);
            }

            scan.close();
        }
        
    }

	public static HashMap<Integer,Integer> getApplications(){
		return applications;
	}

    public static List<Job> getJobs(){
    	return jobs;
    }

	public static Job getJob(int id){		
		Job job = new Job(0,"i","i",0.0f,"i",0,false);
		readJobs();
		jobs = getJobs();
		for (Job j:jobs){
				if (j.getID() == id){
					job = j;
				}
			}
			return job;
	}

    public static void viewJobs(){
    	System.out.println("----JOB MENU----");
    	System.out.println("'exit' to exit the program     'back' to go back to the main menu     'refresh' to refresh the list     or type a job ID to apply for a job");
    	
    	for (Job job: jobs){
    		int id = job.getID();
    		String startTime = job.getStartTime();
    		String endTime = job.getEndTime();
    		Float wage = job.getWage();
    		String location = job.getLocation();
    		String creatorName = "";
			boolean accepted = job.getAccepted();
    		List<Employer> employers = Employers.getEmployers();
    		for (Employer employer: employers){
    			if (job.getCreatorID() == employer.getID()){
    				creatorName = employer.getName();
    			}
    		}
    		System.out.println("Job ID: " + id + ", Employer: " + creatorName + ", Start Time: " + startTime + ", End Time: " + endTime + ", Wage: £" + wage + ", Location: " + location + ", Accepted: " + accepted);
    	}
    	String menuOption = Main.askUser();
    	jobMenu(menuOption);
    }

    public static void refreshJobs(){
    	if (null != jobs){
    		jobs = null;
    	}
    	readJobs();
    }

    private static void jobMenu(String option){
		switch(option){
			case "exit":
				System.exit(0);
				break;
			case "back":
				//Main.promptUserInput();
				Main.goThroughMenu();
				break;
			case "refresh":
				refreshJobs();
				viewJobs();
				break;
			default:
				try{
					applyForJob(option);
				} catch (NumberFormatException e){
			//Main.promptUserInput();
			System.out.println("Invalid Input, going back to main menu");
			Main.goThroughMenu();
				}

		}

	}

	public static void applyForJob(String jobID){
		for (Job job: jobs){
				if (Integer.parseInt(jobID) == job.getID()){
					applicants = job.getApplicants();
					
					User currentUser = Main.returnCurrentUser();
					//System.out.println(currentUser.getFirstName());
					applicants.add(new User(currentUser.getID(),currentUser.getEmail(),currentUser.getPassword(),currentUser.getFirstName(),currentUser.getLastName(),currentUser.getBio(),currentUser.getRating()));
					writeApplicantsToFile(Integer.parseInt(jobID),currentUser.getID());
					System.out.println("You have applied for the job: " + job.getID());
					//Unneccesary
					System.out.println("The current users who have applied for this job are: ");
					for (User applicant: applicants){
						System.out.println(applicant.getFirstName());
					}
					//String newMenuOption = Main.askUser();
					//Main.menu(newMenuOption);
					Main.goThroughMenu();

				}
			}
	}

	public static List<User> getApplicants(){

		return applicants;
	}

	public static void writeApplicantsToFile(int jobID, int userID){
		try{
    		//write account to members.csv file
	    	FileWriter fw = new FileWriter("applicantsToJobs.csv",true);
	    	BufferedWriter bw = new BufferedWriter(fw);
	    	PrintWriter pw = new PrintWriter(bw);

	    	pw.println(userID+","+jobID);
	    	pw.flush();
	    	pw.close();
	    	
	    } catch(Exception f){
	    	System.out.println("file not found baby");
	    }
	}

/*
    public static String greetUsers(String email, String password){
    	String greeting = "";
    	for (User user: users){
    		//System.out.println("UserID: " + user.getID() + ", Email: " + user.getEmail());
    		if (email.equals(user.getEmail()) && password.equals(user.getPassword())){
    			greeting = "Hello, " + user.getFirstName() + " " + user.getLastName();
    			//String next = Main.userInput("What would you like to do?");
    			//String greeting2 = 
    		}
    	}
    	return greeting;
    }

    public static synchronized boolean find(String email, String password){
		if(null == users){
			throw new IllegalStateException("user list is not initialised");
		}

		return users.stream()
			.filter(u -> u.getEmail().equals(email))
			.filter(u -> u.getPassword().equals(password))
			.findFirst()
			.isPresent();
	}*/
}
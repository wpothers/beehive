import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.Math;



//JSON DATA STRUCTURES!!!!!!!!!!!!!
class Employers{


    public static List<Employer> employers;
	public static List<User> applicants;

    public static synchronized void readEmployers(){
    	if (null != employers){
    		employers = null;
    	}
        if(null == employers){
            employers = new ArrayList<Employer>();
            String File = ("employers.csv");
            Scanner scan = new Scanner (Employers.class.getResourceAsStream(File));
            String line;
            //while((line = scan.nextLine()) != null){
			while( scan.hasNext()){
				line = scan.nextLine();
                String[] tokens = line.split(",");
                employers.add(new Employer(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3], tokens[4], Float.parseFloat(tokens[5])));
                //System.out.println(tokens[0]);
            }

            scan.close();
        }
        
    }



    public static List<Employer> getEmployers(){
        return employers;
    }

    public static synchronized boolean find(String email, String password){
        if(null == employers){
            throw new IllegalStateException("employer list is not initialised");
        }

        return employers.stream()
            .filter(u -> u.getEmail().equals(email))
            .filter(u -> u.getPassword().equals(password))
            .findFirst()
            .isPresent();
    }

    public static String greetEmployers(String email, String password){
        String greeting = "";
        for (Employer employer: employers){
            //System.out.println("UserID: " + user.getID() + ", Email: " + user.getEmail());
            if (email.equals(employer.getEmail()) && password.equals(employer.getPassword())){
                greeting = "Hello, " + employer.getName()  + "!";
                //String next = Main.userInput("What would you like to do?");
                //String greeting2 = 
            }
        }
        return greeting;
    }


     public static void viewProfile(){
        //get current logged in user
        Employer currentEmployer = EmployerMain.returnCurrentEmployer();
        //set 
        int id = currentEmployer.getID();
        String name = currentEmployer.getName();
        String location = currentEmployer.getLocation();
        float rating = currentEmployer.getRating();
        System.out.println("Employer ID: " + id + ", Name: " + name + ", Rating: "+rating +", Location: " +location);
    }



    public static void createAccount(){
    	//generate random four digit ID
    	int id = (int)Math.floor(Math.random()*(9999-1000+1)+1000);
    	//ask user to create account
    	String email = EmployerMain.employerInput("Please type an email address");
    	String password = EmployerMain.employerInput("Please type a password");
    	String name = EmployerMain.employerInput("Please type the name of your business");
    	String location = EmployerMain.employerInput("Please type a the location of your business");
    	//Set default rating value
    	float rating = 4.6f;
    	try{
    		//write account to employers.csv file
	    	FileWriter fw = new FileWriter("employers.csv",true);
	    	BufferedWriter bw = new BufferedWriter(fw);
	    	PrintWriter pw = new PrintWriter(bw);

	    	pw.println(id+","+email+","+password+","+name+","+location+","+rating);
	    	pw.flush();
	    	pw.close();
	    	//refresh users list
	    	readEmployers();
	    	System.out.println("Account created, returning to the login screen");
	    } catch(Exception f){
	    	System.out.println("file not found baby");
	    }
    	EmployerMain.startScreen();
    }


    public static void getCreatedJobs(Employer creator){
        System.out.println("----JOB MENU----");
        System.out.println("'exit' to exit the program     'back' to go back to the main menu     or type a job ID to see applicants");
        
        List<Job> jobs = Jobs.getJobs();
		
        List<Job> listings = creator.getListings();
		listings.clear();
        for(Job job: jobs){
            if (job.getCreatorID() == creator.getID()){
                listings.add(new Job(job.getID(),job.getStartTime(),job.getEndTime(),job.getWage(),job.getLocation(),job.getCreatorID(),job.getAccepted()));
            }
        }
        if (listings.size() > 0){
            for(Job listing: listings){
                System.out.println("Job ID: " + listing.getID() +", Start Time: " + listing.getStartTime() + ", End Time: " + listing.getEndTime() + ", Wage: £" + listing.getWage() + ", Location: " + listing.getLocation() + ", Creator ID: " + listing.getCreatorID());
            }
        } else {
            System.out.println("You haven't created any!!");
        }

        String menuOption = EmployerMain.askEmployer();
        employerJobMenu(menuOption);
    }

    private static void employerJobMenu(String option){
        switch(option){
            case "exit":
                System.exit(0);
                break;
            case "back":
                //Main.promptUserInput();
                EmployerMain.goThroughMenu();
                break;
            default:
                try{
                    System.out.println("The current users who have applied for this job are: ");
                    //need to put job ID in getApplicants(id) cause how the hell is it gonna work otherwise
					
					viewApplicants(option);
					System.out.println("Applicant Menu");
					System.out.println("'exit' to exit the app     'back' to return to the main menu     type the ID of the user you want to choose in order to start the job");
					String applicantMenuOption = EmployerMain.askEmployer();
					applicantMenu(applicantMenuOption);

                } catch (NumberFormatException e){
            //Main.promptUserInput();
            System.out.println("Invalid Input, going back to main menu");
            EmployerMain.goThroughMenu();
                }

        }

    }

	public static void applicantMenu(String option){
		
		switch(option){
			case "exit":
				System.exit(0);
				break;
			case "back":
				EmployerMain.goThroughMenu();
				break;
			default:
				try{
					chooseApplicant(option);

				} catch(Exception e){
					System.out.println("Invalid Input, going back to main menu");
					EmployerMain.goThroughMenu();
				}
		}
	}
	
	public static void viewApplicants(String jobID){
		//applicants = new ArrayList<User>();
            String File = ("applicantsToJobs.csv");
            Scanner scan = new Scanner (Employers.class.getResourceAsStream(File));
            String line;
            //while((line = scan.nextLine()) != null){
			try{
				while( scan.hasNext()){
					line = scan.nextLine();
					String[] tokens = line.split(",");
					String jobIDInFile = tokens[1];
					int userID = Integer.parseInt(tokens[0]);
					//System.out.println(jobIDInFile + userID);
					if (jobIDInFile.equals(jobID)){
						User applicant = Main.getCurrentUser(userID);
						System.out.println("User ID: " + applicant.getID() + ", Name: " + applicant.getFirstName());
					} 
					//System.out.println(tokens[0]);
				}
			} catch(Exception e){
				System.out.println("No one has applied yet");
			}

            scan.close();
	}

	public static void chooseApplicant(String userID){
		for (User applicant: Users.getUsers()){
			if (Integer.parseInt(userID) == applicant.getID()){
				System.out.println("User " + applicant.getFirstName() + " has been chosen for the job!");
			}
		}
	}


	 public static void createJob(){
    	//generate random four digit ID
		Employer currentEmployer = EmployerMain.returnCurrentEmployer();
    	int id = (int)Math.floor(Math.random()*(9999-1000+1)+1000);
    	//ask user to create account
    	String startTime = EmployerMain.employerInput("Please type a start time");
    	String endTime = EmployerMain.employerInput("Please type an end time");
    	float wage = Float.parseFloat(EmployerMain.employerInput("Please type the wage you will pay"));
		String location = currentEmployer.getLocation();
		int creatorID = currentEmployer.getID();
		boolean accepted = false;
    	try{
    		//write account to openjobs.csv file
	    	FileWriter fw = new FileWriter("openjobs.csv",true);
	    	BufferedWriter bw = new BufferedWriter(fw);
	    	PrintWriter pw = new PrintWriter(bw);

	    	pw.println(id+","+startTime+","+endTime+","+wage+","+location+","+creatorID+","+accepted);
	    	pw.flush();
	    	pw.close();
	    	//refresh users list
	    	Jobs.readJobs();
	    	System.out.println("Job created, returning to the menu");
	    } catch(Exception f){
	    	System.out.println("file not found baby");
	    }
    	EmployerMain.goThroughMenu();
    }
   




}
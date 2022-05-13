import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.Math;
import java.util.HashMap;


class Users{


    public static List<User> users;
	public static HashMap<Integer,Integer> applications;


    public static synchronized void readUsers(){
    	if (null != users){
    		users = null;
    	}
        if(null == users){
            users = new ArrayList<User>();
            String File = ("members.csv");
            Scanner scan = new Scanner (Users.class.getResourceAsStream(File));
            String line;
            //while((line = scan.nextLine()) != null){
			while( scan.hasNext()){
				line = scan.nextLine();
                String[] tokens = line.split(",");
                users.add(new User(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], Float.parseFloat(tokens[6])));
                //System.out.println(tokens[0]);
            }

            scan.close();
        }
        
    }

    public static void createAccount(){
    	//generate random four digit ID
    	int id = (int)Math.floor(Math.random()*(9999-1000+1)+1000);
    	//ask user to create account
    	String email = Main.userInput("Please type an email address");
    	String password = Main.userInput("Please type a password");
    	String firstName = Main.userInput("Please type a first name");
    	String lastName = Main.userInput("Please type a last name");
    	String bio = Main.userInput("Please enter a short bio");
    	//Set default rating value
    	float rating = 4.6f;
    	try{
    		//write account to members.csv file
	    	FileWriter fw = new FileWriter("members.csv",true);
	    	BufferedWriter bw = new BufferedWriter(fw);
	    	PrintWriter pw = new PrintWriter(bw);

	    	pw.println(id+","+email+","+password+","+firstName+","+lastName+","+bio+","+rating);
	    	pw.flush();
	    	pw.close();
	    	//refresh users list
	    	readUsers();
	    	System.out.println("Account created, returning to the login screen");
	    } catch(Exception f){
	    	System.out.println("file not found baby");
	    }
    	Main.startScreen();
    }

    public static void viewProfile(){
    	//get current logged in user
    	User currentUser = Main.returnCurrentUser();
    	//set 
    	int id = currentUser.getID();
		String firstName = currentUser.getFirstName();
		String lastName = currentUser.getLastName();
		String bio = currentUser.getBio();
		float rating = currentUser.getRating();
		System.out.println("User ID: " + id + ", Name: " + firstName + " " + lastName+ ", Rating: "+rating +", Bio: " +bio);
    }

	public static void viewApplications(){
		//get current logged in User
		User currentUser = Main.returnCurrentUser();
		applications = Jobs.getApplications();
		System.out.println("You have applied for: ");
		for (int userIDInFile : applications.keySet()){
			if (userIDInFile == currentUser.getID()){
				try{
					Job job = Jobs.getJob(applications.get(userIDInFile));
					int id = job.getID();
    				String startTime = job.getStartTime();
    				String endTime = job.getEndTime();
    				Float wage = job.getWage();
    				String location = job.getLocation();
    				String creatorName = "";
    				List<Employer> employers = Employers.getEmployers();
    				for (Employer employer: employers){
    					if (job.getCreatorID() == employer.getID()){
    						creatorName = employer.getName();
    					}
    				}
    				System.out.println("Job ID: " + id + ", Employer: " + creatorName + ", Start Time: " + startTime + ", End Time: " + endTime + ", Wage: £" + wage + ", Location: " + location);
    			
				} catch(Exception e){
					System.out.println("Job not found!!");
				}
			}
		}
		
	}


    public static String greetUsers(String email, String password){
    	String greeting = "";
    	for (User user: users){
    		//System.out.println("UserID: " + user.getID() + ", Email: " + user.getEmail());
    		if (email.equals(user.getEmail()) && password.equals(user.getPassword())){
    			greeting = "Hello, " + user.getFirstName() + " " + user.getLastName() + "!";
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
	}

	public static List<User> getUsers(){
    	return users;
    }

}
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.Console;

public class EmployerMain {
	
	//private static Scanner x;
	private static Employer currentEmployer;
	
	public static void main(String[] args){
		//String username = "Josh";
		//String password = "yoyyo";
		//int id = 13242;
		String filepath = "employers.csv";

		//Initialise user list based off of csv file
		
		Jobs.readJobs();
		//User currentUser;
		//Prompt email password entry

		System.out.println("Hello welcome to our app!");
		
		startScreen();


		//currentUser = getCurrentUser(promptUserInput());
		//goThroughMenu();


	}

public static void startScreen(){
		System.out.println("You can either create an account, login, or exit");
		String startOption = askEmployer();
		switch(startOption){
			case "exit":
				System.exit(0);
				break;
			case "login":
				Employers.readEmployers();
				currentEmployer = getCurrentEmployer(promptEmployerInput());
				goThroughMenu();
				break;
			case "create account":
				System.out.println("create account");
				Employers.createAccount();
				Employers.readEmployers();
				break;
			default:
				System.out.println("Invalid input, please enter a valid option");
				startScreen();
		}

	}


	public static String askEmployer(){
		String next = "";
		next = employerInput("What would you like to do?");
		return next;
	}

	public static Employer getCurrentEmployer(int id){		
		Employer currentUser = new Employer(0,"i","i","i","i",4.6f);
		for (Employer u:Employers.employers){
				if (u.getID() == id){
					currentEmployer = u;
				}
			}
			return currentEmployer;
	}

	public static int promptEmployerInput(){
		String filepath = "employers.csv";
		//User currentUser = new User(0,"i","i","i","i");
		int id = 0;
		Console console = System.console();
		//Ask for email and password
		String email = employerInput("Enter email: ");
		char[] passwordArray = console.readPassword("Enter password: ");
		String password = new String(passwordArray);
		//Verify username and password is in our list
		if (Employers.find(email,password) == true){
			System.out.println("Employer exists");
			//Greet user
			System.out.println(Employers.greetEmployers(email,password));
			//currentUser = getCurrentUser(email,password);
			//Prompt user for a menu option
			//next = userInput("What would you like to do?");
			for (Employer u:Employers.employers){
				if (u.getEmail().equals(email) && u.getPassword().equals(password)){
					id = u.getID();
				}
			}
		}
		else if (Employers.find(email,password) == false){
			System.out.println("Email/password does not match");
			promptEmployerInput();
		} return id;
		
	}

	public static String employerInput(String prompt){
		Scanner employerInputed = new Scanner(System.in);
		System.out.println(prompt);
		System.out.print("     ");
		String input = employerInputed.nextLine();
		return input;
	}

	public static void goThroughMenu(){
		
		System.out.println("----MAIN MENU----");
		System.out.println("'exit' to exit the program     'logout' to log out of account     'view jobs' to view your jobs     'view profile' to view your profile     'create job' to create a job");
		String menuOption = askEmployer();
		//Open option menu
		menu(menuOption);
	}

	public static void menu(String option){
		switch(option){
			case "exit":
				System.exit(0);
				break;
			case "logout":
				//currentUser = getCurrentUser(promptUserInput());
				//goThroughMenu();
				startScreen();
				break;
			case "view jobs":
				//Jobs.viewJobs();
				//VIEW YOUR JOBS
				Employers.getCreatedJobs(returnCurrentEmployer());
				goThroughMenu();
				break;
			case "view profile":
				Employers.viewProfile();
				goThroughMenu();
				break;
			case "create job":
				//CREATE A JOB
				Employers.createJob();
				Jobs.readJobs();
				break;
			default:
				System.out.println("Invalid input, please enter a valid option");
				goThroughMenu();
		}
	}

	public static Employer returnCurrentEmployer(){
		return currentEmployer;
	}
}

















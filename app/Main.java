import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.Console;

public class Main {
	
	private static Scanner x;
	private static User currentUser;
	
	public static void main(String[] args){
		//String username = "Josh";
		//String password = "yoyyo";
		//int id = 13242;
		String filepath = "members.csv";

		//Initialise user list based off of csv file
		
		Jobs.readJobs();
		Employers.readEmployers();
		//User currentUser;
		//Prompt email password entry

		System.out.println("Hello welcome to our app!");
		
		startScreen();


		//currentUser = getCurrentUser(promptUserInput());
		//goThroughMenu();


	}


	public static void startScreen(){
		System.out.println("You can either create an account, login, or exit");
		String startOption = askUser();
		switch(startOption){
			case "exit":
				System.exit(0);
				break;
			case "login":
				Users.readUsers();
				currentUser = getCurrentUser(promptUserInput());
				goThroughMenu();
				break;
			case "create account":
				System.out.println("create account");
				Users.createAccount();
				Users.readUsers();
				break;
			default:
				System.out.println("Invalid input, please enter a valid option");
				startScreen();
		}

	}

	public static void goThroughMenu(){
		
		System.out.println("----MAIN MENU----");
		System.out.println("'exit' to exit the program     'logout' to log out of account     'view jobs' to view available jobs     'view profile' to view your profile     'view applications' to view jobs you've applied for" );
		String menuOption = askUser();
		//Open option menu
		menu(menuOption);
	}

	public static User returnCurrentUser(){
		return currentUser;
	}

	public static int promptUserInput(){
		String filepath = "members.csv";
		//User currentUser = new User(0,"i","i","i","i");
		int id = 0;
		Console console = System.console();
		//Ask for email and password
		String email = userInput("Enter email: ");
		char[] passwordArray = console.readPassword("Enter password: ");
		String password = new String(passwordArray);
		//Verify username and password is in our list
		if (Users.find(email,password) == true){
			System.out.println("User exists");
			//Greet user
			System.out.println(Users.greetUsers(email,password));
			//currentUser = getCurrentUser(email,password);
			//Prompt user for a menu option
			//next = userInput("What would you like to do?");
			for (User u:Users.users){
				if (u.getEmail().equals(email) && u.getPassword().equals(password)){
					id = u.getID();
				}
			}
		}
		else if (Users.find(email,password) == false){
			System.out.println("Email/password does not match");
			promptUserInput();
		} return id;
		
	}

	public static User getCurrentUser(int id){		
		User currentUser = new User(0,"i","i","i","i","i",4.6f);
		Users.readUsers();
		List<User> users = Users.getUsers();
		for (User u:Users.users){
				if (u.getID() == id){
					currentUser = u;
				}
			}
			return currentUser;
	}

	public static String askUser(){
		String next = "";
		next = userInput("What would you like to do?");
		return next;
	}

	public static String userInput(String prompt){
		Scanner userInputed = new Scanner(System.in);
		System.out.println(prompt);
		System.out.print("     ");
		String input = userInputed.nextLine();
		return input;
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
				Jobs.viewJobs();
				break;
			case "view profile":
				Users.viewProfile();
				goThroughMenu();
				break;
			case "view applications":
				Jobs.readApplications();
				Users.viewApplications();
				goThroughMenu();
				break;
			default:
				System.out.println("Invalid input, please enter a valid option");
				goThroughMenu();
		}
	}

}
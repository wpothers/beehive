
class User{
	private int id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String bio;
	private float rating;

	public User(int id, String email, String password, String firstName, String lastName, String bio, float rating){
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.bio = bio;
		this.rating = rating;
	}

	public int getID(){
		return id;
	}

	public String getEmail(){
		return email;
	}

	public String getPassword(){
		return password;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getLastName(){
		return lastName;
	}
	public String getBio(){
		return bio;
	}
	public float getRating(){
		return rating;
	}
}
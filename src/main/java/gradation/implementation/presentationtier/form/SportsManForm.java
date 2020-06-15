package gradation.implementation.presentationtier.form;

import gradation.implementation.datatier.entities.SportsMan;

import javax.validation.constraints.*;


/*
@SamePassword
 */
public class SportsManForm {

	private Long id;

	@Size(max=30, message="Maximum 30 characters")
	@Size(min=8, message="Minimum 8 characters")
	@NotBlank(message = "Enter a firstname")
	private String firstname;

	@Size(max=30, message="Maximum 30 characters")
	@Size(min=5, message="Minimum 5 characters")
	@NotBlank(message = "Enter a lastname")
	private String lastname;

	@Size(max=150, message="Maximum 150 characters")
	private String description;

	@Size(max=40, message="Maximum 40 characters")
	@NotBlank(message = "Enter a mail")
	@Email
	private String mail;

	@NotBlank(message = "You have to create a password!!")
	@Size(min=8, max=60, message="Between 8 and 60 characters")
	private String password;

	@NotBlank(message = "You have to validate the password")
	private String confirmPassword;

	@NotBlank(message = "You have to give a date of Birth")
	private String dateofBirth;

	@DecimalMax(value ="120.0", inclusive = true , message = "must be lower than 120.0")
	@DecimalMin(value ="40.0", inclusive = false, message = "must be highter than 40.0")
	@Positive(message = "You have to put a valid value (positive)")
	@Digits(integer=3, fraction=1, message = "only one decimal")
	private Float weight;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getDateofBirth() {
		return dateofBirth;
	}

	public void setDateofBirth(String dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public SportsManForm() {
	}

	public SportsManForm(SportsMan sportsMan) {
		this.id = sportsMan.getId();
		this.firstname = sportsMan.getFirstName();
		this.lastname = sportsMan.getLastName();
		this.description = sportsMan.getDescription();
		this.mail = sportsMan.getEmail();
		this.weight = sportsMan.getWeight();
		this.dateofBirth = sportsMan.getDateOfBirth().toString();
		this.password = sportsMan.getPassword();
		this.confirmPassword = sportsMan.getPassword();
	}


}

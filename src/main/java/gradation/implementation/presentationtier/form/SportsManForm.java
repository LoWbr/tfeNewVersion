package gradation.implementation.presentationtier.form;

import gradation.implementation.datatier.entities.SportsMan;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


/*
@SamePassword
 */
public class SportsManForm {

	private Long id;

	@NotBlank(message = "Enter a firstname")
	private String firstname;

	@NotBlank(message = "Enter a lastname")
	private String lastname;

	@Size(max=150, message="Maximum 150 characters")
	private String description;

	@NotBlank(message = "Enter a mail")
	@Email
	private String mail;

	@NotBlank(message = "You have to create a password!!")
	@Size(min=8, max=60, message="More than 8 characters")
	private String password;

	@NotBlank(message = "You have to validate the password")
	private String confirmPassword;

	@NotBlank(message = "You have to give a date of Birth")
	private String dateofBirth;

	@Positive(message = "You have to put a valid value (positive)")
	//    @NotNull(message = "Enter your weight")
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

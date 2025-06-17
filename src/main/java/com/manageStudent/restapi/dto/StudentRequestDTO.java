package com.manageStudent.restapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDTO {

	@NotBlank(message = "Name is required")
	@Size(min = 2, max = 50, message = "Must more that 2 letters")
	@Pattern(regexp = "^[A-Za-z ]+$", message = "Name contains only A-Z, a-z and space")
	private String studentName;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email")
	private String studentEmail;
	
	@NotBlank(message = "Contact is required")
	@Pattern(regexp = "^[0-9]{10}$", message = "Number should be 10 digit")
	private String studentContact;
	
	@NotNull(message = "Marks is required")
	@Min(value = 0, message = "Marks should at least 0")
	@Max(value = 100, message = "Marks should be less or equal to 100")
	private double studentMarks;
}

package ru.filatov.springrest.restApp.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PersonDTO {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be in range 2-100 symbols")
    private String name;

    @NotEmpty(message = " Last Name should not be empty")
    @Size(min = 2, max = 100, message = "Last Name should be in range 2-100 symbols")
    private String lastName;

    @Min(value = 0, message = "Age should be greater than 0 !")
    @Max(value = 100, message = "Age should be less than 100 !")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

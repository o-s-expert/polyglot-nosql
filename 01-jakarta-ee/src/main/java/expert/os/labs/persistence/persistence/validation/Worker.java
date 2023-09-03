package expert.os.labs.persistence.persistence.validation;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.joda.money.Money;

import java.util.Objects;

public class Worker {
    @NotBlank(message = "Nickname cannot be blank")
    private String nickname;

    @NotBlank(message = "Name cannot be blank")
    private String name;


    @AssertTrue
    private boolean working;

    @Size(min = 10, max = 200, message
            = "Bio must be between 10 and 200 characters")
    private String bio;

    @Min(value = 18, message = "A worker should not be less than 18")
    @Max(value = 80, message = "A worker should not be greater than 80")
    private int age;

    @Email(message = "Email should be valid")
    private String email;

    @SalaryConstraint(message = "Salary should be greater than zero")
    private Money salary;

    Worker(String nickname, String name, boolean working,
                  String bio, int age,
                  String email, Money salary) {
        this.nickname = nickname;
        this.name = name;
        this.working = working;
        this.bio = bio;
        this.age = age;
        this.email = email;
        this.salary = salary;
    }

    public String nickname() {
        return nickname;
    }

    public String name() {
        return name;
    }

    public boolean working() {
        return working;
    }

    public String bio() {
        return bio;
    }

    public int age() {
        return age;
    }

    public String email() {
        return email;
    }

    public Money salary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", working=" + working +
                ", bio='" + bio + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Worker worker = (Worker) o;
        return Objects.equals(nickname, worker.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

    public static WorkerBuilder builder() {
        return new WorkerBuilder();
    }
}

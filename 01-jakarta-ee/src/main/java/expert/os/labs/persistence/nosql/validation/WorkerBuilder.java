package expert.os.labs.persistence.nosql.validation;

import org.joda.money.Money;

public class WorkerBuilder {
    private String nickname;
    private String name;
    private boolean working;
    private String bio;
    private int age;
    private String email;

    private Money salary;

    WorkerBuilder() {
    }

    public WorkerBuilder nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public WorkerBuilder name(String name) {
        this.name = name;
        return this;
    }

    public WorkerBuilder working(boolean working) {
        this.working = working;
        return this;
    }

    public WorkerBuilder bio(String bio) {
        this.bio = bio;
        return this;
    }

    public WorkerBuilder age(int age) {
        this.age = age;
        return this;
    }

    public WorkerBuilder email(String email) {
        this.email = email;
        return this;
    }

    public WorkerBuilder salary(Money salary) {
        this.salary = salary;
        return this;
    }

    public Worker build() {
        return new Worker(nickname, name, working, bio, age, email, salary);
    }
}
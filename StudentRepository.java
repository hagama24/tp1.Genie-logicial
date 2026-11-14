
package repository;

import model.StudentAccount;
import java.util.*;

public class StudentRepository {

    // Singleton pattern
    private static StudentRepository instance;

    private List<StudentAccount> accounts = new ArrayList<>();

    private StudentRepository() {}

    public static StudentRepository getInstance() {
        if (instance == null)
            instance = new StudentRepository();

        return instance;
    }

    public void add(StudentAccount s) {

        for (StudentAccount a : accounts) {
            if (a.getId().equals(s.getId()))
                throw new IllegalArgumentException(
                        "id already exists: " + s.getId());
        }

        accounts.add(s);
    }

    public List<StudentAccount> getAll() {
        return Collections.unmodifiableList(accounts);
    }

    public int count() {
        return accounts.size();
    }
}

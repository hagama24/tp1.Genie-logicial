package app;
import model.StudentAccount;
import repository.StudentRepository;
import audit.AuditLogger;

public class Main {

    public static void main(String[] args) {

        StudentRepository repo = StudentRepository.getInstance();
        AuditLogger log = AuditLogger.getInstance();

        // 1) Builder: create students
        StudentAccount s1 = new StudentAccount.Builder(
                "2024001", "Amine Kaci", "amine@univ.dz")
                .level("L3")
                .major("Informatique")
                .addCourse("Java")
                .build();

        StudentAccount s2 = new StudentAccount.Builder(
                "2024002", "Sara Haddad", "sara@univ.dz")
                .level("L3")
                .major("Informatique")
                .addCourse("Java")
                .build();

        repo.add(s1);
        repo.add(s2);

        log.log("CREATED", "2024001 - Amine Kaci");
        log.log("CREATED", "2024002 - Sara Haddad");

        // Test invalid accounts
        test("[invalid email]", () ->
                new StudentAccount.Builder("10", "Test", "test.com").build());

        test("[blank id]", () ->
                new StudentAccount.Builder("", "Test", "test@univ.dz").build());

        test("[invalid level]", () ->
                new StudentAccount.Builder("11", "Test", "test@univ.dz")
                        .level("L9").build());

        // 2) Singleton
        System.out.println("\n=== 3) Singleton ===");

        System.out.println("Same repository? " +
                (repo == StudentRepository.getInstance()));

        System.out.println("Same logger? " +
                (log == AuditLogger.getInstance()));

        test("[duplicate id]", () -> repo.add(s1));
        log.log("REJECTED", "duplicate id: 2024001");

        // 3) Prototype
        System.out.println("\n=== 4) Prototype: L3 Informatique batch ===");

        StudentAccount template = new StudentAccount.Builder(
                "TEMPLATE", "Template", "template@univ.dz")
                .level("L3")
                .major("Informatique")
                .addCourse("Design Patterns")
                .build();

        StudentAccount s3 = template.copy(
                "2024101", "malak haga", "malak@univ.dz");

        StudentAccount s4 = template.copy(
                "2024102", "Nadia Brahimi", "nadia@univ.dz");

        StudentAccount s5 = template.copy(
                "2024103", "Walid Messaoud", "walid@univ.dz");

        repo.add(s3);
        repo.add(s4);
        repo.add(s5);

        log.log("CREATED", "2024101 - haga malak");
        log.log("CREATED", "2024102 - Nadia Brahimi");
        log.log("CREATED", "2024103 - Walid Messaoud");

        System.out.println(s3);

        System.out.println("Different objects? " + (s3 != s4));

        System.out.println("Independent course lists? " +
                (s3.getCourses() != s4.getCourses()));

        System.out.println("Same courses as template? " +
                s3.getCourses().equals(template.getCourses()));

        // 4) Repository
        System.out.println("\nAccounts in repository: " + repo.count());

        // 5) Audit Log
        System.out.println("\n=== 5) Audit log ===");
        log.showLogs();
    }

    static void test(String label, Runnable action) {
        try {
            action.run();
        } catch (Exception e) {
            System.out.println(label + " rejected: " + e.getMessage());
        }
    }
}

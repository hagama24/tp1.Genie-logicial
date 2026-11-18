
package model;

import java.util.*;

public class StudentAccount {

    private String id, fullName, email, phone, level, major;
    private boolean scholarship;
    private List<String> courses;
// Builder
    private StudentAccount(Builder b) {
        id = b.id;
        fullName = b.fullName;
        email = b.email;
        phone = b.phone;
        level = b.level;
        major = b.major;
        scholarship = b.scholarship;
        courses = new ArrayList<>(b.courses);
    }

    public String getId() {
        return id;
    }

    public List<String> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    // Prototype: copy account and change student identity
    public StudentAccount copy(String id, String name, String email) {
        return new Builder(id, name, email)
                .phone(phone)
                .level(level)
                .major(major)
                .scholarship(scholarship)
                .courses(courses)
                .build();
    }

    @Override
    public String toString() {
        return "StudentAccount{id=" + id +
                ", name=" + fullName +
                ", email=" + email +
                ", phone=" + phone +
                ", level=" + level +
                ", major=" + major +
                ", scholarship=" + scholarship +
                ", courses=" + courses + "}";
    }

    // Builder pattern
    public static class Builder {

        private String id, fullName, email;
        private String phone = null;
        private String level = "L1";
        private String major = null;
        private boolean scholarship = false;
        private List<String> courses = new ArrayList<>();

        public Builder(String id, String fullName, String email) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
        }

        public Builder phone(String v) {
            phone = v;
            return this;
        }

        public Builder level(String v) {
            level = v;
            return this;
        }

        public Builder major(String v) {
            major = v;
            return this;
        }

        public Builder scholarship(boolean v) {
            scholarship = v;
            return this;
        }

        public Builder addCourse(String v) {
            courses.add(v);
            return this;
        }

        public Builder courses(List<String> v) {
            courses = new ArrayList<>(v);
            return this;
        }
//انشاء الحساب النهائي و التحقق من المعلومات 
        public StudentAccount build() {

            if (id == null || id.trim().isEmpty())
                throw new IllegalArgumentException("id is required");

            if (fullName == null || fullName.trim().isEmpty())
                throw new IllegalArgumentException("name is required");

            if (email == null || !email.contains("@"))
                throw new IllegalArgumentException("email must contain @");

            if (!Arrays.asList("L1", "L2", "L3", "M1", "M2").contains(level))
                throw new IllegalArgumentException("invalid level: " + level);

            return new StudentAccount(this);
        }
    }
}

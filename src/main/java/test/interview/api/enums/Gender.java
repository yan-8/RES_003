package test.interview.api.enums;

public enum Gender {
    MALE("male"),
    FEMALE("female");

    String genderName;

    Gender(String genderName) {
        this.genderName = genderName;
    }

    public String getGenderName() {
        return genderName;
    }
}

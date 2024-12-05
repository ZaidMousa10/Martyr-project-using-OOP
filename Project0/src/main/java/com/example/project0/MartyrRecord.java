package com.example.project0;
public class MartyrRecord implements Comparable<MartyrRecord> {
    private String name;
    private int age;
    private String eventLocation;
    private String dateOfDeath;
    private String gender;

    public MartyrRecord(String name, int age, String eventLocation, String dateOfDeath, String gender) {
        this.name = name;
        this.age = age;
        this.eventLocation = eventLocation;
        this.dateOfDeath = dateOfDeath;
        this.gender = gender;
    }

    @Override
    public int compareTo(MartyrRecord other) {
        return this.name.compareTo(other.name);
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getEventLocation() {
        return eventLocation;
    }
    public String getDateOfDeath() {
        return dateOfDeath;
    }
    public String getGender() {
        return gender;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
    public void setDateOfDeath(String dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }


    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Location: " + eventLocation + ", Date of Death: " + dateOfDeath + ", Gender: " + gender;
    }

}

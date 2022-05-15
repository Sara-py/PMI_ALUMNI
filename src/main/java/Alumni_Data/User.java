package Alumni_Data;     // Name of the file(aka Package) rest of the classes are created in.

public class User {                  //this class named user is describing the Alumni's Data
    private String name;                // Here we are defining each data entered by user, whether it should be string or float, or any other type
    private int age;
    private String email_address;
    private String profession;
    private final Faculty faculty;


    public User() {                                                            // this is a kind of an example
        this("John Doe", 2000, "Baker Street", "Actor", Faculty.ARTS);
    }

    public User(String name, int age, String email_address, String profession) {               //Here we are explaining if the data should be the one specified by the user
        this.name = name;                                                                      //or it should be the one belonged in one of the enum or other class
        this.age = age;
        this.email_address = email_address;
        this.profession = profession;
        faculty = Faculty.ARTS;
    }

    public User(String name, int age, String email_address, String profession, Faculty faculty) {  //here we did the same in all, except for the faculty, in which we defined it to be an element from enum
        this.name = name;
        this.age = age;
        this.email_address = email_address;
        this.profession = profession;
        this.faculty = faculty;
    }

    public Faculty getFaculty() {
        return faculty;
    }                                      // here we use get method to return the element at a given index from the specified Array.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override                 //@ is an java annotation that means the method is overriding the parent class.
    public String toString() {
        return name + " (" + age + ", " + email_address + ", "
                + profession + ", " + faculty.toString() + ")\n";
    }
}

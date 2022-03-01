package sample;

public class patient {
    public String name;
    public String username;
    public String sex;
    public String age;
    public String password;
    public String address;
    public String contactNumber;
    public String area;
    public String thana;
    public String district;

    public patient(String name, String username, String password, String sex, String age, String address,
                   String contactNumber, String area, String thana, String district) {
        this.name = name;
        this.username = username;
        this.sex = sex;
        this.age = age;
        this.password = password;
        this.address = address;
        this.contactNumber = contactNumber;
        this.area = area;
        this.thana = thana;
        this.district = district;
    }
    public patient(){}


}

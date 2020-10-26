package definitions;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private String username, password, firstName, lastName, email, phoneNumber, biography, personalQuestionAnswer;
    private Role role = Role.USER;
    private int personalQuestionNumber, id, followersNum, followingNum, twitsNum;
    private Image image;
    private Date date = new Date();
    private String followStatus;
    private ArrayList<Twit> twits = new ArrayList<>();
    private ArrayList<Twit> showTwits = new ArrayList<>();
    private ArrayList<User> following = new ArrayList<>();
    private ArrayList<User> followers = new ArrayList<>();
    private ArrayList<User> blocked = new ArrayList<>();

    public User(String username, String firstName, String lastName, String email, String phoneNumber, String biography, Role role, Image image) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.biography = biography;
        this.role = role;
        this.image = image;
    }

    public User(String username, String firstName, String lastName, String email, String phoneNumber, String biography) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.biography = biography;
        setDefaultPicture();
    }

    public User(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        setDefaultPicture();
    }

    public User(String username, String password, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        setDefaultPicture();
    }

    private void setDefaultPicture() {
        setImage(new Image(User.class.getResource("/images/profile.png").toString()));
    }

    public ArrayList<Twit> getShowTwits() {
        return showTwits;
    }

    public void setShowTwits(ArrayList<Twit> showTwits) {
        this.showTwits = showTwits;
    }

    public void addTwit(Twit twit) {
        showTwits.add(0, twit);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPersonalQuestionAnswer() {
        return personalQuestionAnswer;
    }

    public void setPersonalQuestionAnswer(String personalQuestionAnswer) {
        this.personalQuestionAnswer = personalQuestionAnswer;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getPersonalQuestionNumber() {
        return personalQuestionNumber;
    }

    public void setPersonalQuestionNumber(int personalQuestionNumber) {
        this.personalQuestionNumber = personalQuestionNumber;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("ID: ").append(id);
        s.append("\nName: ").append(firstName);
        s.append("\nLast Name: ").append(lastName);
        return s.toString();
    }

    public int getRoleIndex() {
        if (role == Role.USER)
            return 0;
        else if (role == Role.ANALYSER)
            return 1;
        else
            return 2;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getFollowersNum() {
        return followersNum;
    }

    public void setFollowersNum(int followersNum) {
        this.followersNum = followersNum;
    }

    public int getFollowingNum() {
        return followingNum;
    }

    public void setFollowingNum(int followingNum) {
        this.followingNum = followingNum;
    }

    public ArrayList<Twit> getTwits() {
        return twits;
    }

    public void setTwits(ArrayList<Twit> twits) {
        this.twits = twits;
    }

    public int getTwitsNum() {
        return twitsNum;
    }

    public void setTwitsNum(int twitsNum) {
        this.twitsNum = twitsNum;
    }

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }
}

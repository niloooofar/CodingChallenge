package cirtual.com.cirtualcodingchallenge.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    public static final int FRIEND_TYPE = 0;
    public static final int BEST_FRIEND_TYPE = 1;

    public int type;

    private String id;
    private String name;
    private String birthday;
    private String email;
    private String description;
    private boolean isBestFriend;
    private String imageUrl;

    public Person(Parcel in) {
        id = in.readString();
        name = in.readString();
        birthday = in.readString();
        email = in.readString();
        description = in.readString();
        isBestFriend = in.readByte() != 0;
        imageUrl = in.readString();
    }

    public Person() {

    }

    public Person(int type, String id, String name, String birthday, String email, String description, boolean isBestFriend, String imageUrl) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.description = description;
        this.isBestFriend = isBestFriend;
        this.imageUrl = imageUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBestFriend() {
        return isBestFriend;
    }

    public void setBestFriend(boolean bestFriend) {
        isBestFriend = bestFriend;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(birthday);
        dest.writeString(email);
        dest.writeString(description);
        dest.writeByte((byte) (isBestFriend ? 1 : 0));
        dest.writeString(imageUrl);
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}

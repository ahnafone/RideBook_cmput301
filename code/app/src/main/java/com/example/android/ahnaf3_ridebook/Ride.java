package com.example.android.ahnaf3_ridebook;


import android.os.Parcel;
import android.os.Parcelable;

//Each ride is saved as a Ride object
public class Ride implements Parcelable {

    private float distance, aveSpeed, aveCadence;
    private String time, date, comments;


    public Ride() { }

    //toString function
    public String toString() {
        return ( distance + " km cycled\n"
                + "Average Speed: " + aveSpeed + " km/h\n"
                + "Average Cadence: " + aveCadence + "cycles/min\n"
                + date + " (" + time + ")");
    }
    //toData function
    public String toData() {
        return ( distance + "&" + time + "&" + date + "&" + aveSpeed + "&" + aveCadence + "&" + comments );
    }


    //gettters
    public float getDistance() {
        return distance;
    }
    public String getTime() {
        return time;
    }
    public String getDate() {
        return date;
    }
    public float getAveSpeed() {
        return aveSpeed;
    }
    public float getAveCadence() {
        return aveCadence;
    }
    public String getComments() {
        return comments;
    }

    //setters
    public void setDistance(float distance) {
        this.distance = distance;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setAveSpeed(float aveSpeed) {
        this.aveSpeed = aveSpeed;
    }
    public void setAveCadence(float aveCadence) {
        this.aveCadence = aveCadence;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    protected Ride(Parcel in) {
        distance = in.readFloat();
        aveSpeed = in.readFloat();
        aveCadence = in.readFloat();
        time = in.readString();
        date = in.readString();
        comments = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(distance);
        dest.writeFloat(aveSpeed);
        dest.writeFloat(aveCadence);
        dest.writeString(time);
        dest.writeString(date);
        dest.writeString(comments);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ride> CREATOR = new Parcelable.Creator<Ride>() {
        @Override
        public Ride createFromParcel(Parcel in) {
            return new Ride(in);
        }

        @Override
        public Ride[] newArray(int size) {
            return new Ride[size];
        }
    };
}
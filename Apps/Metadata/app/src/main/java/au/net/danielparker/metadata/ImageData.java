package au.net.danielparker.metadata;

import android.media.Rating;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by danielparker on 14/09/14.
 */
public class ImageData implements Parcelable{
    private int imageId;
    private String name;
    private Uri url;
    private ArrayList<String> keyWords = new ArrayList<String>();
    private Date date = Calendar.getInstance().getTime();
    private String sourceEmail;
    private Boolean share = false;
    private Rating rating = Rating.newUnratedRating(Rating.RATING_5_STARS);

    public static final Parcelable.Creator<ImageData> CREATOR
            = new Parcelable.Creator<ImageData>() {
        public ImageData createFromParcel(Parcel in) {
            return new ImageData(in);
        }

        public ImageData[] newArray(int size) {
            return new ImageData[size];
        }
    };

    public ImageData(Parcel in) {
        this.imageId = in.readInt();
        this.name = in.readString();
        this.url = Uri.parse(in.readString());
        this.keyWords = in.readArrayList(String.class.getClassLoader());
        this.date = new Date(in.readLong());
        this.sourceEmail = in.readString();
        this.share = (Boolean)in.readValue(Boolean.class.getClassLoader());
        this.rating = (Rating)in.readValue(Rating.class.getClassLoader());
    }

    public ImageData(String name, String sourceEmail, int imageId) throws NameEmptyException, EmailEmptyException {
        setName(name);
        setSourceEmail(sourceEmail);
        setImageId(imageId);
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(imageId);
        out.writeString(name);
        out.writeString(url.toString());
        out.writeList(keyWords);
        out.writeLong(date.getTime());
        out.writeString(sourceEmail);
        out.writeValue(share);
        out.writeValue(rating);
    }

    public int describeContents() {
        return 0;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Boolean getShare() {
        return share;
    }

    public void setShare(Boolean share) {
        this.share = share;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws NameEmptyException{
        if (name.isEmpty()) {
            throw new NameEmptyException();
        }
        this.name = name;
    }

    public Uri getUrl() {
        return url;
    }

    public void setUrl(Uri url) {
        this.url = url;
    }

    public ArrayList<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(ArrayList<String> keyWords) {
        this.keyWords = keyWords;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSourceEmail() {
        return sourceEmail;
    }

    public void setSourceEmail(String sourceEmail) throws EmailEmptyException{
        if (sourceEmail.toString().isEmpty()) {
            throw new EmailEmptyException();
        }
        this.sourceEmail = sourceEmail;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public static class NameEmptyException extends Exception {
        public NameEmptyException() {

        }
    }

    public static class EmailEmptyException extends Exception {
        public EmailEmptyException() {

        }
    }
}

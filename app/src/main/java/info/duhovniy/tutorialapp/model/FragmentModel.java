package info.duhovniy.tutorialapp.model;

import android.os.Parcel;
import android.os.Parcelable;


public class FragmentModel implements Parcelable {

    private String type;
    private long id;
    private String title;
    private String previewDescription;
    private String detailDescription;
    private String image;

    public FragmentModel() {
    }

    protected FragmentModel(Parcel in) {
        type = in.readString();
        id = in.readLong();
        title = in.readString();
        previewDescription = in.readString();
        detailDescription = in.readString();
        image = in.readString();
    }

    public static final Creator<FragmentModel> CREATOR = new Creator<FragmentModel>() {
        @Override
        public FragmentModel createFromParcel(Parcel in) {
            return new FragmentModel(in);
        }

        @Override
        public FragmentModel[] newArray(int size) {
            return new FragmentModel[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreviewDescription() {
        return previewDescription;
    }

    public void setPreviewDescription(String previewDescription) {
        this.previewDescription = previewDescription;
    }

    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(previewDescription);
        dest.writeString(detailDescription);
        dest.writeString(image);
    }
}

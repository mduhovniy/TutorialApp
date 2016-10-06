package info.duhovniy.tutorialapp.model;

import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import info.duhovniy.tutorialapp.R;


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

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
        int dpHeight = displayMetrics.heightPixels;
        int dpWidth = displayMetrics.widthPixels;
        int size = (dpHeight > dpWidth) ? dpWidth : dpHeight;

        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .resize(size, size)
                .centerCrop()
                .into(view);
    }

    public void onClickZoom(View view) {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

}

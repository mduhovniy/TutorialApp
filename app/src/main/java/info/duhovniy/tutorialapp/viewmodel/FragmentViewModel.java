package info.duhovniy.tutorialapp.viewmodel;


import android.databinding.BindingAdapter;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import info.duhovniy.tutorialapp.R;
import info.duhovniy.tutorialapp.model.FragmentModel;

public class FragmentViewModel {

    private FragmentModel mFragment;
    private FullScreenListener mFullScreenListener;

    public FragmentViewModel(FullScreenListener fullScreenListener, FragmentModel fm) {
        mFullScreenListener = fullScreenListener;
        mFragment = fm;
    }

    public FragmentModel getFragmentModel() {
        return mFragment;
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
        mFullScreenListener.onFullScreen(getFragmentModel());
    }

    public void destroy() {
        mFragment = null;
        mFullScreenListener = null;
    }

    public interface FullScreenListener {
        void onFullScreen(FragmentModel fragmentModel);
    }
}

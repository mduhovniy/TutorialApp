package info.duhovniy.tutorialapp.view;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import info.duhovniy.tutorialapp.R;
import info.duhovniy.tutorialapp.databinding.ActivityFullscreenBinding;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    public static final String TITLE = "FullscreenActivity.Header";
    public static final String IMAGE = "FullscreenActivity.Image";
    public static final String TAG = "FullscreenActivity";
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        image = getIntent().getStringExtra(IMAGE);
        String title = getIntent().getStringExtra(TITLE);
        super.onCreate(savedInstanceState);

        ActivityFullscreenBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_fullscreen);
        binding.setViewModel(this);

        if (title != null && getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);

        toggleHideyBar();
    }

    /**
     * Detects and toggles immersive mode (also known as "hidey bar" mode).
     */
    public void toggleHideyBar() {

        // Delete comments, if you need to hide also an ActionBar!
//        if (getSupportActionBar() != null)
//            getSupportActionBar().hide();

        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i(TAG, "Turning immersive mode mode off. ");
        } else {
            Log.i(TAG, "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    public String getImage() {
        return image;
    }

    @BindingAdapter({"bigImageUrl"})
    public static void loadBigImage(ImageView view, String imageUrl) {
        DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
        int dpHeight = displayMetrics.heightPixels;
        int dpWidth = displayMetrics.widthPixels;

        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .resize(dpWidth, dpHeight)
                .centerCrop()
                .into(view);
    }
}

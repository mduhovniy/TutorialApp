package info.duhovniy.tutorialapp.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import info.duhovniy.tutorialapp.MyApplication;
import info.duhovniy.tutorialapp.R;
import info.duhovniy.tutorialapp.model.FragmentModel;


public class MainViewModel {

    private List<FragmentModel> mFragments;
    private Context mContext;
    private DataListener mDataListener;

    public ObservableInt placeholdersVisibility;

    public MainViewModel(Context context, DataListener dataListener) {
        mContext = context;
        mDataListener = dataListener;
        mFragments = MyApplication.get(mContext).restoreFragments();
        placeholdersVisibility = new ObservableInt();
        if (mFragments.isEmpty())
            placeholdersVisibility.set(View.VISIBLE);
        else
            placeholdersVisibility.set(View.INVISIBLE);
    }

    // TODO universal LoadManager with async and multiple model option
    private boolean loadFragmentsFromFile(String fileName) {
        Gson gson = new Gson();

        try {
            StringBuilder buf = new StringBuilder();
            InputStream json = mContext.getAssets().open(fileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }

            in.close();

            FragmentModel[] tmp = gson.fromJson(String.valueOf(buf), FragmentModel[].class);
            mFragments = Arrays.asList(tmp);

        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean isFetched = false;

        if (!mFragments.isEmpty()) {
            isFetched = true;

            MyApplication.get(mContext).storeFragments(mFragments);

            placeholdersVisibility.set(View.INVISIBLE);

            if (mDataListener != null)
                mDataListener.onFragmentsChanged();
        }
        return isFetched;
    }

    public FragmentModel getFragmentModel(int pos) {
        return mFragments.get(pos);
    }

    public void destroy() {
        mFragments = null;
        mContext = null;
        mDataListener = null;
    }

    public int size() {
        return mFragments.size();
    }

    public void onClickFetch(View view) {
        if (loadFragmentsFromFile(mContext.getString(R.string.json_static_source)))
            Snackbar.make((View) view.getParent(), R.string.fetched_snackbar, Snackbar.LENGTH_LONG).show();
        else
            Snackbar.make((View) view.getParent(), R.string.not_fetched_snackbar, Snackbar.LENGTH_LONG).show();
    }

    public interface DataListener {
        void onFragmentsChanged();
    }
}

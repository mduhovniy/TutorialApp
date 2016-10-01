package info.duhovniy.tutorialapp.viewmodel;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import info.duhovniy.tutorialapp.MyApplication;
import info.duhovniy.tutorialapp.R;
import info.duhovniy.tutorialapp.model.FragmentModel;


public class MainViewModel {

    private List<FragmentModel> fragments;
    private Context mContext;
    private DataListener mDataListener;

    public MainViewModel(Context context, DataListener dataListener) {
        mContext = context;
        mDataListener = dataListener;
        fragments = MyApplication.get(mContext).restoreFragments();
    }

    public void loadFragmentsFromFile() {
        Gson gson = new Gson();

        try {
            StringBuilder buf = new StringBuilder();
            InputStream json = mContext.getAssets().open(mContext.getString(R.string.json_static_source));
            BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }

            in.close();

            FragmentModel[] tmp = gson.fromJson(String.valueOf(buf), FragmentModel[].class);
            fragments = Arrays.asList(tmp);

        } catch (IOException e) {
            e.printStackTrace();
        }

        MyApplication.get(mContext).storeFragments(fragments);

        if (mDataListener != null)
            mDataListener.onFragmentsChanged();
    }

    public List<FragmentModel> getFragments() {
        return fragments;
    }

    public void destroy() {
        fragments = null;
        mContext = null;
        mDataListener = null;
    }

    public interface DataListener {
        void onFragmentsChanged();
    }
}

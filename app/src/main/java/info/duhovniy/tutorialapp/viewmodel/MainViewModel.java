package info.duhovniy.tutorialapp.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
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

    public MainViewModel(Context context, DataListener dataListener) {
        mContext = context;
        mDataListener = dataListener;
        mFragments = MyApplication.get(mContext).restoreFragments();
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
            mFragments = Arrays.asList(tmp);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mFragments != null) {
            MyApplication.get(mContext).storeFragments(mFragments);

            if (mDataListener != null)
                mDataListener.onFragmentsChanged();
        }
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

    public interface DataListener {
        void onFragmentsChanged();
    }
}

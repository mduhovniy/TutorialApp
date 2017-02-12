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
import info.duhovniy.tutorialapp.model.ApiCallable;
import info.duhovniy.tutorialapp.model.FragmentModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainViewModel {

    private List<FragmentModel> mFragments;
    private Context mContext;
    private DataListener mDataListener;

    public final ObservableInt placeholdersVisibility;

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
    private void loadFragmentsFromApi() {
        ApiCallable apiCallable = MyApplication.get(mContext).getApiCallable();

        Call<List<FragmentModel>> call = apiCallable.getAllFragments();
        call.enqueue(new Callback<List<FragmentModel>>() {
            @Override
            public void onResponse(Call<List<FragmentModel>> call, Response<List<FragmentModel>> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    mFragments = response.body();

                    placeholdersVisibility.set(View.INVISIBLE);

                    if (mDataListener != null)
                        mDataListener.onFragmentsChanged(false);
                }
            }

            @Override
            public void onFailure(Call<List<FragmentModel>> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }

    public void loadFragmentsFromFile(String fileName) {
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

        if (!mFragments.isEmpty()) {
            placeholdersVisibility.set(View.INVISIBLE);

            if (mDataListener != null)
                mDataListener.onFragmentsChanged(true);
        }
    }

    public FragmentModel getFragmentModel(int pos) {
        return mFragments.get(pos);
    }

    public void saveState() {
        if (mFragments != null)
            MyApplication.get(mContext).storeFragments(mFragments);
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
        loadFragmentsFromApi();
    }

    public interface DataListener {
        void onFragmentsChanged(boolean isLocal);
    }
}

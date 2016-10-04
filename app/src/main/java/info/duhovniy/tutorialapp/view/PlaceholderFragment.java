package info.duhovniy.tutorialapp.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.duhovniy.tutorialapp.R;
import info.duhovniy.tutorialapp.databinding.FragmentImageBinding;
import info.duhovniy.tutorialapp.databinding.FragmentTextBinding;
import info.duhovniy.tutorialapp.model.FragmentModel;

/**
 * A placeholder fragment containing creation of 2 types of new Fragment.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION = "section";

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     */
    public static PlaceholderFragment newInstance(FragmentModel fragmentModel) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        if (fragmentModel != null) {
            Bundle args = new Bundle();
            args.putParcelable(ARG_SECTION, fragmentModel);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentModel fm = getArguments().getParcelable(ARG_SECTION);
        View rootView;
        if (fm.getType() == null) {
            rootView = inflater.inflate(R.layout.fragment_empty, container, false);
            return rootView;
        }
        switch (fm.getType()) {
            case "text":
                FragmentTextBinding textBinding = DataBindingUtil.inflate(
                        inflater, R.layout.fragment_text, container, false);
                rootView = textBinding.getRoot();
                textBinding.setData(fm);
                break;
            case "image":
                FragmentImageBinding imageBinding = DataBindingUtil.inflate(
                        inflater, R.layout.fragment_image, container, false);
                rootView = imageBinding.getRoot();
                imageBinding.setData(fm);
                break;
            default:
                rootView = inflater.inflate(R.layout.fragment_empty, container, false);
        }
        return rootView;
    }
}
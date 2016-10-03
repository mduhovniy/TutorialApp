package info.duhovniy.tutorialapp.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.duhovniy.tutorialapp.R;
import info.duhovniy.tutorialapp.model.FragmentModel;

/**
 * A placeholder fragment containing a simple view.
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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(fm.getTitle());    // TODO replace with Data Binding
        return rootView;
    }
}
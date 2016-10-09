package info.duhovniy.tutorialapp.view;


import android.content.Intent;
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
import info.duhovniy.tutorialapp.viewmodel.FragmentViewModel;


public class PlaceholderFragment extends Fragment implements FragmentViewModel.FullScreenListener {

    private static final String ARG_SECTION = "section";
    private FragmentViewModel fragmentViewModel = null;

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given FragmentModel
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
        fragmentViewModel = new FragmentViewModel(this, fm);
        switch (fm.getType()) {
            case "text":
                FragmentTextBinding textBinding = DataBindingUtil.inflate(
                        inflater, R.layout.fragment_text, container, false);
                rootView = textBinding.getRoot();
                textBinding.setViewModel(fragmentViewModel);
                break;
            case "image":
                FragmentImageBinding imageBinding = DataBindingUtil.inflate(
                        inflater, R.layout.fragment_image, container, false);
                rootView = imageBinding.getRoot();
                imageBinding.setViewModel(fragmentViewModel);
                break;
            default:
                rootView = inflater.inflate(R.layout.fragment_empty, container, false);
        }
        return rootView;
    }

    @Override
    public void onFullScreen(FragmentModel fragmentModel) {
        Intent intent = new Intent(getContext(), FullscreenActivity.class);
        intent.putExtra(FullscreenActivity.EXTRA_IMAGE, fragmentModel.getImage());
        getActivity().startActivity(intent);
    }

    @Override
    public void onDestroy() {
        fragmentViewModel.destroy();
        super.onDestroy();
    }
}
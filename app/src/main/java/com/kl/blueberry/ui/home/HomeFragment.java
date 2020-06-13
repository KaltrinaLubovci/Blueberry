package com.kl.blueberry.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.kl.blueberry.R;
import com.kl.blueberry.databinding.HomeFragmentBinding;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {

    private HomeFragmentBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        binding.setViewModel(new HomeViewModel());

        return binding.getRoot();
    }


}

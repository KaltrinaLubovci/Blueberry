package com.kl.blueberry.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.kl.blueberry.R;
import com.kl.blueberry.data.AppPreferences;
import com.kl.blueberry.databinding.ProfileFragmentBinding;

import org.jetbrains.annotations.NotNull;

public class ProfileFragment extends Fragment {

    private ProfileFragmentBinding binding;
    AppPreferences appPreferences;

    public ProfileFragment(AppPreferences appPreferences) {
        this.appPreferences = appPreferences;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false);
        binding.setViewModel(new ProfileViewModel());
        fillPersonalData();
        return binding.getRoot();
    }

    private void fillPersonalData(){
        binding.tvFullName.setText(appPreferences.getFullName());
        binding.tvUsername.setText(appPreferences.getUsername());
        binding.tvEmail.setText(appPreferences.getEmail());
        binding.tvPassword.setText(appPreferences.getPassword());
    }


}

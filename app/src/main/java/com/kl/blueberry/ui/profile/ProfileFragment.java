package com.kl.blueberry.ui.profile;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.kl.blueberry.R;
import com.kl.blueberry.data.AppPreferences;
import com.kl.blueberry.databinding.ProfileFragmentBinding;
import com.kl.blueberry.events.ShowToastEvent;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class ProfileFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    private ProfileFragmentBinding binding;
    private AppPreferences appPreferences;
    private Bitmap resizedBitmap;
    File resizedFile;
    private Image image;
    private CircleImageView ivProfile;

    public ProfileFragment(AppPreferences appPreferences) {
        this.appPreferences = appPreferences;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false);
        binding.setViewModel(new ProfileViewModel());
        ivProfile = binding.civProfilePicture;
        fillPersonalData();
        onClicks();
        return binding.getRoot();
    }

    private void fillPersonalData() {
        binding.tvFullName.setText(appPreferences.getFullName());
        binding.tvUsername.setText(appPreferences.getUsername());
        binding.tvEmail.setText(appPreferences.getEmail());
        binding.tvPassword.setText(appPreferences.getPassword());
        if (appPreferences.getImagePath() != null) {
            Glide.with(this).load(appPreferences.getImagePath()).into(ivProfile);
        }
    }

    private void onClicks() {
        binding.civProfilePicture.setOnClickListener(onClick -> {
            checkPermissions();
        });
    }

    private void checkPermissions() {
        String[] perms = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        if (EasyPermissions.hasPermissions(getContext(), perms[0]) && EasyPermissions.hasPermissions(
                getContext(),
                perms[1]
        ) && EasyPermissions.hasPermissions(getContext(), perms[2])
        ) {
            EventBus.getDefault().post(new ShowToastEvent("Permissions granted!"));
            ImagePicker.create(ProfileFragment.this).single().returnMode(ReturnMode.ALL).start();
        } else {
            EasyPermissions.requestPermissions(
                    this, "We need permissions for Camera/Storage",
                    123, perms[0], perms[1], perms[2]
            );
        }

    }

    private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private File convertBitmapToFile() {
        File f = new File(getActivity().getCacheDir(), "bayyinah_images" + System.currentTimeMillis() + ".png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = resizedBitmap;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0/*ignored for PNG*/, bos);

        byte[] bitmapData = bos.toByteArray();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        resizedFile = f;

        return f;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        ImagePicker.create(ProfileFragment.this).single().returnMode(ReturnMode.ALL).start();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String[] perms = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        if (EasyPermissions.hasPermissions(getContext(), perms[0]) && EasyPermissions.hasPermissions(
                getContext(),
                perms[1]
        ) && EasyPermissions.hasPermissions(getContext(), perms[2])
        ) {

            if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {

                image = ImagePicker.getFirstImageOrNull(data);

                if (ivProfile.getDrawable() != null) {
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeFile(image.getPath(), bmOptions);

                    resizedBitmap = getResizedBitmap(bitmap, 1000);
                    resizedBitmap =
                            binding.getViewModel().getRotateImage(image.getPath(), resizedBitmap);
                    convertBitmapToFile();

                    Glide.with(this).load(image.getPath()).into(ivProfile);
                    appPreferences.setImagePath(image.getPath());
                }
                Log.d("MainActivity", "onActivityResult image.getPath():" + image.getPath());
                return;
            }
        } else {

            EventBus.getDefault().post(new ShowToastEvent("We need permissions for this app to work properly!"));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

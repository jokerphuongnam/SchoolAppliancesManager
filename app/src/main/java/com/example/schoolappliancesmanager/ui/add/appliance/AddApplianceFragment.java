package com.example.schoolappliancesmanager.ui.add.appliance;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.FragmentAddApplianceBinding;
import com.example.schoolappliancesmanager.model.database.domain.Appliance;
import com.example.schoolappliancesmanager.ui.add.AddViewModel;
import com.example.schoolappliancesmanager.ui.base.BaseFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

import static com.example.schoolappliancesmanager.ui.main.MainActivity.DATA;

@AndroidEntryPoint
public class AddApplianceFragment extends BaseFragment<FragmentAddApplianceBinding, AddApplianceViewModel> {
    public AddApplianceFragment() {
        super(R.layout.fragment_add_appliance);
    }

    @Override
    public AddApplianceViewModel setUpViewModel() {
        return new ViewModelProvider(this).get(AddApplianceViewModel.class);
    }

    private AddViewModel activityViewModel;

    private void setUpActivityViewModel() {
        activityViewModel = new ViewModelProvider(requireActivity()).get(AddViewModel.class);
    }

    private Uri image = null;
    private boolean hasCam = false;
    private boolean isChange = false;


    private final ActivityResultLauncher<Intent> imageChoose = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (result) -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Uri selectedImageUri = result.getData().getData();
            binding.image.setImageURI(selectedImageUri);
            binding.imageLayout.setDisplayedChild(1);
            image = selectedImageUri;
            isChange = true;
        }
    });

    private final ActivityResultLauncher<Intent> takePhotoFromCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (result) -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Bundle extras = result.getData().getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            binding.image.setImageBitmap(imageBitmap);
            binding.imageLayout.setDisplayedChild(1);
            hasCam = true;
            isChange = true;
        }
    });

    @Override
    public void createView() {
        setUpActivityViewModel();
        binding.imageLayout.setMeasureAllChildren(false);
        viewModel.initAppliance((Appliance) getActivity().getIntent().getSerializableExtra(DATA));
        viewModel.getSuccess().observe(getViewLifecycleOwner(), (success) -> {
            getActivity().finish();
        });
        if (!viewModel.getAppliance().getDirImage().isEmpty()) {
            binding.imageLayout.setDisplayedChild(1);
            Uri uri =  Uri.parse(viewModel.getAppliance().getDirImage());
            binding.image.setImageURI(uri);
        }
        binding.setAppliance(viewModel.getAppliance());
        binding.success.setOnClickListener((v) -> {
            if (binding.getAppliance().getApplianceName().isEmpty()) {
                binding.applianceNameError.setVisibility(View.VISIBLE);
            } else {
                if(isChange){
                    try {
                        Uri imageUri = null;
                        if (image != null) {
                            imageUri = image;
                        } else if (hasCam) {
                            imageUri = createPublicImage(((BitmapDrawable)binding.image.getDrawable()).getBitmap());
                        }
                        if (imageUri != null) {
                            Uri oldUri = Uri.parse(viewModel.getAppliance().getDirImage());
                            deleteImage(oldUri);
                            String path = saveImagePrivate(imageUri).toString();
                            binding.getAppliance().setDirImage(path);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                binding.applianceNameError.setVisibility(View.GONE);
                Appliance appliance = binding.getAppliance();
                int index = binding.spinner.getSelectedItemPosition();
                appliance.setStatus(Appliance.Status.values()[index]);
                switch (activityViewModel.getTypeAction()) {
                    case EDIT:
                        viewModel.editAppliance(appliance);
                        break;
                    case ADD:
                        viewModel.addAppliance(appliance);
                        break;
                }
            }
        });
        binding.addImage.setOnClickListener((v) -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            imageChoose.launch(intent);
        });
        binding.openCamera.setOnClickListener((v) -> {
            takePhotoFromCamera.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
        });
        binding.deleteImage.setOnClickListener((v) -> {
            binding.imageLayout.setDisplayedChild(0);
            binding.image.setImageBitmap(null);
            image = null;
            hasCam = false;
        });
    }

    @NonNull
    private String fileName() {
        return String.valueOf(Calendar.getInstance().getTimeInMillis());
    }

    @NonNull
    private Uri saveImagePrivate(@NonNull Uri selectedImageUri) {
        FileOutputStream fos = null;
        InputStream iStream = null;
        String name = fileName();
        try {
            iStream = requireActivity().getContentResolver().openInputStream(selectedImageUri);
            byte[] inputData = getBytes(iStream);
            fos = requireActivity().openFileOutput(name, Context.MODE_PRIVATE);
            fos.write(inputData);
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            try {
                fos.close();
                iStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File file = new File(requireActivity().getFilesDir(), name);
        return Uri.fromFile(file);
    }

    public byte[] getBytes(@NonNull InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void deleteImage(@NonNull Uri uri){
        File myFile = new File(uri.toString());
        if(myFile.exists()){
            myFile.delete();
        }
    }

    private Uri createPublicImage(Bitmap bitmap) throws IOException {
        OutputStream fos;
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = requireActivity().getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName() + ".jpeg");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
        } else {
            String imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            File image = new File(imagesDir, fileName() + ".jpg");
            fos = new FileOutputStream(image);
            imageUri = Uri.fromFile(image);
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        Objects.requireNonNull(fos).close();
        return imageUri;
    }
}

package com.example.schoolappliancesmanager.binding;

import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.example.schoolappliancesmanager.R;

public class ImageBinding {
    @BindingAdapter("path_image")
    public static void setImageByPath(ImageView imageView, String path){
        if(path == null || path.isEmpty()){
                imageView.setImageResource(R.drawable.ic_empty);
        }else{
            Uri uri =  Uri.parse(path);
            imageView.setImageURI(uri);
        }
    }
}

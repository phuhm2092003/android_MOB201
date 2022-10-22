package fpt.edu.schoolproject.ui.mxh;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

import fpt.edu.schoolproject.notification.MyNotification;
import fpt.edu.schoolproject.R;

public class XaHoiFragment extends Fragment {
    TextView tvimage;
    ImageView imgShare;
    EditText edtContent;
    Button btnShare;
    Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_xa_hoi, container, false);

        edtContent = view.findViewById(R.id.edtContent);
        btnShare = view.findViewById(R.id.btnShare);

        tvimage = view.findViewById(R.id.txtImage);
        imgShare = view.findViewById(R.id.imgShare);
        LinearLayout linearShare = view.findViewById(R.id.linearShare);

        linearShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);

                chooseImage.launch(i);
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if(!wifi.isConnected()){
                    MyNotification.checkSDK(getContext());
                    MyNotification.getNotification(getContext(), "Vui lòng kết nối wifi để chỉa sẻ đến mọi người");
                    Toast.makeText(getContext(), "Mất kết nối", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, edtContent.getText().toString());
                    intent.putExtra(Intent.EXTRA_SUBJECT, "HoMinhPhu chia sẻ");

                    if(bitmap != null){
                        Uri uri = getmageToShare(bitmap);
                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        intent.setType("image/png");
                    }else{
                        intent.setType("text/plain");
                    }

                    startActivity(Intent.createChooser(intent, "Chia sẻ nội dung thông qua"));
                }

            }
        });


        return view;
    }

    ActivityResultLauncher<Intent> chooseImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri selectedImageUri = Objects.requireNonNull(data).getData();
                        if (null != selectedImageUri) {
                            imgShare.setImageURI(selectedImageUri);
                            tvimage.setText("Lựa chọn lại hình ảnh");
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgShare.getDrawable();
                            bitmap = bitmapDrawable.getBitmap();
                        }
                    }
                }
            });
    private Uri getmageToShare(Bitmap bitmap) {
        File imagefolder = new File(requireContext().getCacheDir(), "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
            File file = new File(imagefolder, "shared_image.png");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(requireContext(), "fpt.edu.schoolproject.fileprovider", file);
        } catch (Exception e) {
            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }

}

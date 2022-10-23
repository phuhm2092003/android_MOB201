package fpt.edu.schoolproject.ui.khoahoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.Objects;

import fpt.edu.schoolproject.LoginActivity;
import fpt.edu.schoolproject.R;

public class KhoaHocFragment extends Fragment {
    CardView cardRegister, cardRegistered;
    VideoView videoView;
    androidx.appcompat.widget.Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_khoa_hoc, container, false);
        initView(view);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        cardRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangKyKhoaHocActivity.class);
                startActivity(intent);
            }
        });
        cardRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), KhoaHocDaDangKyActivity.class);
                startActivity(intent);
            }
        });
        loadVideo();
        return view;
    }

    private void loadVideo() {
        Uri uri = Uri.parse("android.resource://"+requireContext().getPackageName()+"/"+R.raw.video);
        videoView.setVideoURI(uri);
//        videoView.setMediaController(new MediaController(getContext()));
        videoView.start();
    }

    private void initView(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.tbKhoaHoc);
        cardRegister = view.findViewById(R.id.cardDangKy);
        cardRegistered = view.findViewById(R.id.cardDaDangKy);
        videoView = view.findViewById(R.id.videoView);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadVideo();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_logout, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_log_out){
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setMessage("Bạn có muốn đăng xuất");
            builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            });
            builder.setNegativeButton("Huỷ ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }
        return super.onOptionsItemSelected(item);
    }
}
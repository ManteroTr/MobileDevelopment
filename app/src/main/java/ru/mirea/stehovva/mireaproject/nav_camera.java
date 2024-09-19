package ru.mirea.stehovva.mireaproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nav_camera#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav_camera extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private	static	final	int	REQUEST_CODE_PERMISSION	=	100;
    private	boolean	isWork	=	false;

    ImageView img1;

    private Uri imageUri;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public nav_camera() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav_camera.
     */
    // TODO: Rename and change types and number of parameters
    public static nav_camera newInstance(String param1, String param2) {
        nav_camera fragment = new nav_camera();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nav_camera, container, false);
        int	cameraPermissionStatus	= ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.CAMERA);
        int	storagePermissionStatus	= ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if	(cameraPermissionStatus	==	PackageManager.PERMISSION_GRANTED && storagePermissionStatus
                ==	PackageManager.PERMISSION_GRANTED)	{
            isWork	= true;
        }	else {
            ActivityCompat.requestPermissions(getActivity(), new	String[] {android.Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }

        img1 = v.findViewById(R.id.imageView2);


        ActivityResultCallback<ActivityResult> callback	= new ActivityResultCallback<ActivityResult>()	{
            @Override
            public	void onActivityResult(ActivityResult result)	{
                if	(result.getResultCode()	== Activity.RESULT_OK)	{
                    Intent data	= result.getData();
                    img1.setImageURI(imageUri);
                }
            }
        };
        ActivityResultLauncher<Intent> cameraActivityResultLauncher	= registerForActivityResult(
                new	ActivityResultContracts.StartActivityForResult(), callback);

        img1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if	(isWork) {
                    try {
                        File photoFile = createImageFile();
                        String authorities = getActivity().getApplicationContext().getPackageName() + ".fileprovider";
                        imageUri = FileProvider.getUriForFile(getActivity(), authorities, photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,	imageUri);
                        cameraActivityResultLauncher.launch(cameraIntent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return v;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "IMAGE_" +	timeStamp +	"_";
        File storageDirectory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDirectory);
    }
}
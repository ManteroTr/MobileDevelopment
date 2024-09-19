package ru.mirea.stehovva.mireaproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link workWithFiles#newInstance} factory method to
 * create an instance of this fragment.
 */
public class workWithFiles extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_PICK_FILE = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FloatingActionButton floatingActionButton;
    private	static final int REQUEST_CODE_PERMISSION = 200;
    Button convertButton;
    Uri fileUri;
    TextView fileName;
    String FileName;
    String shortFileName;

    public workWithFiles() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment workWithFiles.
     */
    // TODO: Rename and change types and number of parameters
    public static workWithFiles newInstance(String param1, String param2) {
        workWithFiles fragment = new workWithFiles();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_with_files, container, false);

        floatingActionButton = view.findViewById(R.id.floatingButton);
        floatingActionButton.setOnClickListener(v -> SelectFile());
        convertButton = view.findViewById(R.id.button3);
        convertButton.setOnClickListener(v -> Convert());
        convertButton.setEnabled(false);

        fileName = view.findViewById(R.id.textView7);

        int storageWritePermissionStatus = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.
                WRITE_EXTERNAL_STORAGE);
        int storageReadPermissionStatus = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.
                READ_EXTERNAL_STORAGE);
        if(storageReadPermissionStatus != PackageManager.PERMISSION_GRANTED ||
                storageWritePermissionStatus != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }

        return view;
    }

    public void Convert(){
        String[] splitname = FileName.split("\\.");
        String[] shortSplitName = Arrays.copyOf(splitname, splitname.length-1);
        shortFileName = String.join(".", shortSplitName);
        ConvertToDocx(fileUri);
    }

    public void SelectFile(){
        Intent chooseFile = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        chooseFile.setType("*/*");
        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        fileActivityResultLauncher.launch(chooseFile);

    }

    ActivityResultLauncher<Intent> fileActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getResultCode()== getActivity().RESULT_OK) {
                        fileUri = result.getData().getData();
                        fileName.setText("Выбранный файл: " + getFileNameFromUri(fileUri));
                        convertButton.setEnabled(true);
                    }
                }
            }

    );

    private void ConvertToDocx(Uri uri){
        try{
            InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
            if (inputStream != null){
                XWPFDocument document = new XWPFDocument();
                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                run.setText(new String(buffer));
                String outputPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" +shortFileName+".docx";
                OutputStream out = new FileOutputStream(outputPath);
                document.write(out);
                out.close();
                Toast.makeText(requireContext(), "Файл успешно конвертирован", Toast.LENGTH_SHORT).show();
            }
        }catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Ошибка конвертации файла", Toast.LENGTH_SHORT).show();
        }

    }

    private String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (displayNameIndex != -1) {
                        result = cursor.getString(displayNameIndex);
                        FileName = cursor.getString(displayNameIndex);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }


}
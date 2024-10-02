package com.mati_tech.masa_english_learning.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.mati_tech.masa_english_learning.viewmodel.MaterialViewModel

import com.mati_tech.masa_english_learning.R
import com.mati_tech.masa_english_learning.models.Material

class AddMaterialActivity : AppCompatActivity() {
    private lateinit var editTextFileName: EditText
    private lateinit var editTextFileDescription: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonChooseFile: Button
    private lateinit var textViewFilePath: TextView
    private lateinit var fileUri: Uri
    private lateinit var materialViewModel: MaterialViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_material)

        editTextFileName = findViewById(R.id.edit_text_filename)
        editTextFileDescription = findViewById(R.id.edit_text_file_description)
        buttonAdd = findViewById(R.id.button_add_material)
        buttonChooseFile = findViewById(R.id.button_choose_file)
        textViewFilePath = findViewById(R.id.text_view_file_path)
        materialViewModel = ViewModelProvider(this)[MaterialViewModel::class.java]
        requestRuntimePermission()
        buttonChooseFile.setOnClickListener { openFilePicker() }

        buttonAdd.setOnClickListener { saveMaterial() }
    }

    //    private void requestStoragePermissions() {
    //        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
    //                != PackageManager.PERMISSION_GRANTED) {
    //            ActivityCompat.requestPermissions(this,
    //                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
    //                    1);
    //        }
    //    }
    private fun requestRuntimePermission() {
        if (ActivityCompat.checkSelfPermission(
                this@AddMaterialActivity,
                PERMISSION_READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@AddMaterialActivity,
                PERMISSION_READ_EXTERNAL_STORAGE
            )
        ) {
            val builder = AlertDialog.Builder(this@AddMaterialActivity)
            builder.setMessage("This app requires reading external storage")
                .setCancelable(false)
                .setPositiveButton("Ok") { dialog: DialogInterface, _: Int ->
                    ActivityCompat.requestPermissions(
                        this@AddMaterialActivity,
                        arrayOf(PERMISSION_READ_EXTERNAL_STORAGE),
                        PERMISSION_REQ_CODE
                    )
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel",
                    (DialogInterface.OnClickListener { dialog: DialogInterface, _: Int -> dialog.dismiss() })
                )
            builder.show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(PERMISSION_READ_EXTERNAL_STORAGE),
                PERMISSION_REQ_CODE
            )
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.setType("*/*")
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "application/pdf"))
        filePickerLauncher.launch(intent)
    }

    @SuppressLint("WrongConstant")
    private val filePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            if (data != null) {
                fileUri = data.data!!
                textViewFilePath.text = fileUri.toString()

                // Take persistable URI permission
                val takeFlags =
                    data.flags and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                contentResolver.takePersistableUriPermission(fileUri, takeFlags)
            }
        }
    }

    private fun saveMaterial() {
        val fileName = editTextFileName.text.toString().trim { it <= ' ' }
        val fileDescription = editTextFileDescription.text.toString().trim { it <= ' ' }
        if (fileName.isEmpty() || fileDescription.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields and choose a file", Toast.LENGTH_SHORT)
                .show()
            return
        }
        // Convert Uri to a file path or store the Uri directly in the database
        val filePath = fileUri.toString()

        val material = Material(fileName, fileDescription, filePath)
        materialViewModel.insert(material)
        Toast.makeText(this, "Material added", Toast.LENGTH_SHORT).show()
        finish()
    }

    //    @Override
    //    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    //        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    //        if (requestCode == 1) {
    //            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
    //                // Permission granted
    //            } else {
    //                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
    //            }
    //        }
    //    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQ_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@AddMaterialActivity, "Permission granted!", Toast.LENGTH_SHORT)
                    .show()
            } else if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    this@AddMaterialActivity,
                    PERMISSION_READ_EXTERNAL_STORAGE
                )
            ) {
                val builder = AlertDialog.Builder(this@AddMaterialActivity)
                builder.setMessage("This feature is unavailable because you have denied the permission for it, Please allow the permission from the setting to proceed")
                    .setTitle("Permission Required")
                    .setCancelable(false)
                    .setNegativeButton(
                        "Cancel"
                    ) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
                    .setPositiveButton("Settings") { dialog: DialogInterface, _: Int ->
                        val intent =
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri =
                            Uri.fromParts("package", packageName, null)
                        intent.setData(uri)
                        startActivity(intent)
                        dialog.dismiss()
                    }
                //                builder.show();
            } else {
                requestRuntimePermission()
            }
        }
    }

    companion object {
        private const val PERMISSION_READ_EXTERNAL_STORAGE =
            Manifest.permission.READ_EXTERNAL_STORAGE
        private const val PERMISSION_REQ_CODE = 100
    }
} //
//package com.example.masa_english_school.ui.activities;
//
//import android.Manifest;
//import android.content.ContentResolver;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.provider.Settings;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.example.masa_english_school.R;
//import com.example.masa_english_school.models.Material;
//import com.mati_tech.masa_english_learning.viewmodel.MaterialViewModel;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//public class addMaterialActivity extends AppCompatActivity {
//    private static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
//    private static final String PERMISSION_READ_MEDIA_STORAGE = Manifest.permission.READ_MEDIA_IMAGES;
//
//    private static final int PERMISSION_REQ_CODE = 100;
//    private EditText editTextFileName;
//    private EditText editTextFileDescription;
//    private Button buttonAdd;
//    private Button buttonChooseFile;
//    private TextView textViewFilePath;
//    private Uri fileUri;
//    private MaterialViewModel materialViewModel;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_material);
//
//        editTextFileName = findViewById(R.id.edit_text_filename);
//        editTextFileDescription = findViewById(R.id.edit_text_file_description);
//        buttonAdd = findViewById(R.id.button_add_material);
//        buttonChooseFile = findViewById(R.id.button_choose_file);
//        textViewFilePath = findViewById(R.id.text_view_file_path);
//        materialViewModel = new ViewModelProvider(this).get(MaterialViewModel.class);
//
//        buttonChooseFile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requestRuntimePermission();
//                openFilePicker();
//
//            }
//        });
//
//        buttonAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveMaterial();
//            }
//        });
//
//        // Request storage permissions
//
//    }
//
//    private void requestRuntimePermission() {
//        if (ActivityCompat.checkSelfPermission(addMaterialActivity.this, PERMISSION_READ_MEDIA_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
//
//        }else if (ActivityCompat.shouldShowRequestPermissionRationale(addMaterialActivity.this, PERMISSION_READ_MEDIA_STORAGE)) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(addMaterialActivity.this);
//            builder.setMessage("This app requires reading external storage")
//                    .setCancelable(false)
//                    .setPositiveButton("Ok", (dialog, which) -> {
//                        ActivityCompat.requestPermissions(addMaterialActivity.this, new String[]{PERMISSION_READ_MEDIA_STORAGE}, PERMISSION_REQ_CODE);
//                        dialog.dismiss();
//                    })
//                    .setNegativeButton("Cancel", ((dialog, which) -> dialog.dismiss()));
//            builder.show();
//        } else {
//            ActivityCompat.requestPermissions(this, new String[]{PERMISSION_READ_MEDIA_STORAGE}, PERMISSION_REQ_CODE);
//        }
//    }
//
//    private void openFilePicker() {
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.setType("*/*");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "application/pdf"});
//        filePickerLauncher.launch(intent);
//    }
//
//    private final ActivityResultLauncher<Intent> filePickerLauncher =
//            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//                if (result.getResultCode() == RESULT_OK) {
//                    Intent data = result.getData();
//                    if (data != null) {
//                        fileUri = data.getData();
//                        textViewFilePath.setText(fileUri.toString());
//                    }
//                }
//            });
//
//    private void saveMaterial() {
//        String fileName = editTextFileName.getText().toString().trim();
//        String fileDescription = editTextFileDescription.getText().toString().trim();
//
//        if (fileName.isEmpty() || fileDescription.isEmpty() || fileUri == null) {
//            Toast.makeText(this, "Please fill out all fields and choose a file", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Get the file content as byte array
//        byte[] fileContent = getFileContentFromUri(fileUri);
//
//        if (fileContent == null) {
//            Toast.makeText(this, "Failed to read file content", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Material material = new Material(fileName, fileDescription, fileUri.toString(), fileContent);
//        materialViewModel.insert(material);
//        Toast.makeText(this, "Material added", Toast.LENGTH_SHORT).show();
//        finish();
//    }
//
//    private byte[] getFileContentFromUri(Uri uri) {
//        try {
//            ContentResolver contentResolver = getContentResolver();
//            InputStream inputStream = contentResolver.openInputStream(uri);
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int len;
//            while ((len = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, len);
//            }
//            return outputStream.toByteArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PERMISSION_REQ_CODE  ) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(addMaterialActivity.this, "Permission granted!", Toast.LENGTH_SHORT).show();
//            } else if (!ActivityCompat.shouldShowRequestPermissionRationale(addMaterialActivity.this, PERMISSION_READ_MEDIA_STORAGE)) {
//
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(addMaterialActivity.this);
//                builder.setMessage("This feature is unavailable because you have denied the permission for it, Please allow the permission from the setting to proceed")
//                        .setTitle("Permission Required")
//                        .setCancelable(false)
//                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
//                        .setPositiveButton("Settings", (dialog, which) -> {
//                            Intent intent  = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                            Uri uri = Uri.fromParts("package", getPackageName(), null);
//                            intent.setData(uri);
//                            startActivity(intent);
//
//                            dialog.dismiss();
//                        });
//                builder.show();
//            }
//            else {
//                requestRuntimePermission();
//            }
//        }
//    }
//}
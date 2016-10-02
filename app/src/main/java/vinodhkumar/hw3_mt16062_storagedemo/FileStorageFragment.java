package vinodhkumar.hw3_mt16062_storagedemo;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by VinodhKumar on 29/09/16.
 */

public class FileStorageFragment extends Fragment {
    private Button mFileStorageSubmitButton,mReadFileButton;
    private RadioGroup mPersistentGroup, mStorageGroup,mReadWriteOptionGroup,mReadStorageTypeGroup,mReadExternalStorageTypeGroup;
    private RadioButton mPersistentYes, mPersistentNo, mIsInternal, mIsExternal,mReadOption,mWriteOption,mReadInternalOption,mReadExternalOption,mReadExternalPrivateOption,mReadExternalPublicOption;
    private EditText mFileName,mFileData,mReadFileName;
    private TextView mReadFileData;
    FileOutputStream outputStream;
    FileInputStream inputStream;

    public FileStorageFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.fragment_filestorage, container, false);
        getActivity().findViewById(R.id.appDescription).setVisibility(View.GONE);
        mFileStorageSubmitButton = (Button)rootView.findViewById(R.id.submitFileButton);
        mReadFileButton=(Button)rootView.findViewById(R.id.readFileButton);
        mPersistentGroup = (RadioGroup)rootView.findViewById(R.id.isPersistent);
        mStorageGroup = (RadioGroup)rootView.findViewById(R.id.storageType);
        mReadWriteOptionGroup = (RadioGroup)rootView.findViewById(R.id.readWriteType);
        mReadStorageTypeGroup = (RadioGroup)rootView.findViewById(R.id.readStorageTypeGroup);
        mReadExternalStorageTypeGroup=(RadioGroup)rootView.findViewById(R.id.readExternalStorageTypeGroup);
        mReadInternalOption = (RadioButton)rootView.findViewById(R.id.readInternalOption);
        mReadExternalOption = (RadioButton)rootView.findViewById(R.id.readExternalOption);
        mReadOption = (RadioButton)rootView.findViewById(R.id.readOption);
        mWriteOption = (RadioButton) rootView.findViewById(R.id.writeOption);
        mIsInternal = (RadioButton)rootView.findViewById(R.id.isInternal);
        mIsExternal = (RadioButton) rootView.findViewById(R.id.isExternal);
        mFileName = (EditText) rootView.findViewById(R.id.fileName);
        mReadFileName = (EditText)rootView.findViewById(R.id.readFileName);
        mFileData = (EditText) rootView.findViewById(R.id.fileData);
        mReadFileData = (TextView)rootView.findViewById(R.id.readFileData);
        //final String data;
        mPersistentYes = (RadioButton)rootView.findViewById(R.id.persistentYes);
        mPersistentNo = (RadioButton)rootView.findViewById(R.id.persistentNo);
        mReadExternalPrivateOption = (RadioButton)rootView.findViewById(R.id.readExternalPrivateOption);
        mReadExternalPublicOption = (RadioButton)rootView.findViewById(R.id.readExternalPublicOption);



        mReadStorageTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(mReadExternalOption.isChecked() == true){
                    mReadExternalStorageTypeGroup.setVisibility(View.VISIBLE);
                }
                else{
                    mReadExternalStorageTypeGroup.setVisibility(View.GONE);
                }

            }
        });

        mStorageGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(mIsInternal.isChecked() == true){
                    rootView.findViewById(R.id.persistentRadio).setVisibility(View.GONE);
                }
                else{
                    rootView.findViewById(R.id.persistentRadio).setVisibility(View.VISIBLE);
                }

            }
        });

        mReadWriteOptionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(mReadOption.isChecked() == true){
                    rootView.findViewById(R.id.writeLayout).setVisibility(View.GONE);
                    rootView.findViewById(R.id.readLayout).setVisibility(View.VISIBLE);
                }
                else{
                    rootView.findViewById(R.id.readLayout).setVisibility(View.GONE);
                    rootView.findViewById(R.id.writeLayout).setVisibility(View.VISIBLE);
                }

            }
        });

        mFileStorageSubmitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mIsInternal.isChecked() == true){
                    try {
                        outputStream =  getActivity().getApplicationContext().openFileOutput(mFileName.getText().toString(),Context.MODE_PRIVATE);
                        outputStream.write(mFileData.getText().toString().getBytes());
                        outputStream.close();
                        Toast.makeText(getActivity().getApplicationContext(), "File Saved: "+getActivity().getApplicationContext().getFilesDir().toString()+"/"+mFileName.getText().toString(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    if (Environment.MEDIA_UNMOUNTED.equals(Environment.getExternalStorageState())){
                        Toast.makeText(getActivity().getApplicationContext(), "No external storage detected!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (!isExternalStorageWritable()){
                            Toast.makeText(getActivity().getApplicationContext(), "External Storage not writable!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(mPersistentYes.isChecked()==true){
//                                File path = Environment.getExternalStoragePublicDirectory(
//                                        Environment.DIRECTORY_DOCUMENTS);
                                //File file = new File(Environment.getExternalStorageDirectory(), mFileName.getText().toString());
                                File file = new File(Environment.getExternalStorageDirectory(), mFileName.getText().toString());
                                try {
                                    //Environment.getExternalStorageDirectory().mkdirs();
                                    //file.mkdirs();
                                        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                                            if (!file.createNewFile()) {
                                                Log.i("Test", "This file is already exist: " + file.getAbsolutePath());
                                            }
                                        }
                                    // file.createNewFile();
                                    FileOutputStream os = new FileOutputStream(file);
                                    os.write(mFileData.getText().toString().getBytes());
                                    os.close();
                                    Toast.makeText(getActivity().getApplicationContext(), "Saved in "+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                                }catch(IOException e){
                                    Toast.makeText(getActivity().getApplicationContext(), "Error in creating "+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                                    Log.w("ExternalStorage", "Error writing " + file, e);
                                }

                            }
                            else{
                                File file = new File(getActivity().getApplicationContext().getExternalFilesDir(null).getAbsolutePath(), mFileName.getText().toString() );
                                try {
                                    file.createNewFile();
                                    //file.mkdirs();
                                    FileOutputStream os = new FileOutputStream(file);
                                    os.write(mFileData.getText().toString().getBytes());
                                    os.close();
                                    Toast.makeText(getActivity().getApplicationContext(), "Saved in "+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                                }catch(IOException e){
                                    Toast.makeText(getActivity().getApplicationContext(), "Error in creating "+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                                    Log.w("ExternalStorage", "Error writing " + file, e);
                                }

                            }


                        }
                    }

                }

            }
        });

        mReadFileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mReadInternalOption.isChecked()==true) {
                    try {
                        inputStream = getActivity().getApplicationContext().openFileInput(mReadFileName.getText().toString());
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        mReadFileData.setText(bufferedReader.readLine());
                        Toast.makeText(getActivity().getApplicationContext(), "Read from: "+getActivity().getApplicationContext().getFilesDir().toString()+"/"+mReadFileName.getText().toString(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), "File not found!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else{
                    if(!isExternalStorageReadable()){
                        Toast.makeText(getActivity().getApplicationContext(), "External Storage not writable!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (mReadExternalPublicOption.isChecked() == true) {
                            File file = new File(Environment.getExternalStorageDirectory(), mReadFileName.getText().toString());
                            try {
                                //Environment.getExternalStorageDirectory().mkdirs();
                                //file.mkdirs();
                                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE);
                                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
//                                if (!file.createNewFile()) {
//                                    Log.i("Test", "This file is already exist: " + file.getAbsolutePath());
//                                }

                                    // file.createNewFile();
                                    FileInputStream is = new FileInputStream(file);
                                    InputStreamReader inputStreamReader = new InputStreamReader(is);
                                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                    mReadFileData.setText(bufferedReader.readLine());
                                    Toast.makeText(getActivity().getApplicationContext(), "Read from: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                Toast.makeText(getActivity().getApplicationContext(), "File not found: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                                Log.w("ExternalStorage", "Error Reading " + file, e);
                            }
                        } else {
                            File file = new File(getActivity().getApplicationContext().getExternalFilesDir(null).getAbsolutePath(), mReadFileName.getText().toString());
                            try {

                                FileInputStream is = new FileInputStream(file);
                                InputStreamReader inputStreamReader = new InputStreamReader(is);
                                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                mReadFileData.setText(bufferedReader.readLine());
                                Toast.makeText(getActivity().getApplicationContext(), "Read from: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();

                            } catch (IOException e) {
                                Toast.makeText(getActivity().getApplicationContext(), "File not found: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                                Log.w("ExternalStorage", "Error Reading " + file, e);

                            }
                        }
                    }
                }
            }
        });

        return rootView;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}

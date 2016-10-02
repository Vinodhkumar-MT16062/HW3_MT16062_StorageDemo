package vinodhkumar.hw3_mt16062_storagedemo;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by VinodhKumar on 29/09/16.
 */

public class DatabaseStorageFragment extends Fragment {
    RadioGroup mDbOptions;
    RadioButton mAddRowOption,mUpdateRowOption,mDeleteRowOption;
    Button mAddButton,mSearchButton,mUpdateButton,mDeleteButton;
    EditText mAddRollNo,mUpdateRollNo,mSearchRollNo,mDeleteRollNo,mUpdateName,mAddName;
    RadioButton mSearchOption;

    DBHelper mydb;

    public DatabaseStorageFragment(){

    }

//    @Override
//    public void onActivityCreated(){
//
//    }

    //@Override
//    public void onActivityCreated(Bundle b){
//        super.onActivityCreated(b);
//        ((MainActivity)this.getActivity()).passInstance().findViewById(R.id.appDescription).setVisibility(View.GONE);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.fragment_databasestorage, container, false);
        getActivity().findViewById(R.id.appDescription).setVisibility(View.GONE);

        mDbOptions = (RadioGroup)rootView.findViewById(R.id.dbOptions);
        mAddRowOption = (RadioButton) rootView.findViewById(R.id.addRowOption);
        mUpdateRowOption = (RadioButton)rootView.findViewById(R.id.updateRowOption);
        mDeleteRowOption = (RadioButton)rootView.findViewById(R.id.deleteRowOption);

        mSearchOption = (RadioButton) rootView.findViewById(R.id.searchCheckBox);

        mAddButton = (Button)rootView.findViewById(R.id.addButton);
        mUpdateButton = (Button)rootView.findViewById(R.id.updateButton);
        mSearchButton = (Button)rootView.findViewById(R.id.searchButton);
        mDeleteButton = (Button)rootView.findViewById(R.id.deleteButton);

        mAddName = (EditText)rootView.findViewById(R.id.addName);
        mAddRollNo = (EditText)rootView.findViewById(R.id.addRollNo);
        mUpdateRollNo = (EditText)rootView.findViewById(R.id.searchRollNo);
        mSearchRollNo =(EditText)rootView.findViewById(R.id.searchRollNo);
        mDeleteRollNo = (EditText)rootView.findViewById(R.id.deleteRollNo);
        mUpdateName = (EditText)rootView.findViewById(R.id.updateName);


        mydb = new DBHelper(getActivity().getApplicationContext());

        mDbOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup,int i){
                if(mAddRowOption.isChecked()==true){
                    rootView.findViewById(R.id.addLayout).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.updateLayout).setVisibility(View.GONE);
                    mUpdateName.setText("");
                    mUpdateRollNo.setText("");
                    rootView.findViewById(R.id.deleteLayout).setVisibility(View.GONE);
                    mDeleteRollNo.setText("");
                }
                else if(mUpdateRowOption.isChecked()==true){
                    rootView.findViewById(R.id.addLayout).setVisibility(View.GONE);
                    mAddName.setText("");
                    mAddRollNo.setText("");
                    rootView.findViewById(R.id.updateLayout).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.deleteLayout).setVisibility(View.GONE);
                    mDeleteRollNo.setText("");
                }
                else{
                    rootView.findViewById(R.id.addLayout).setVisibility(View.GONE);
                    mAddName.setText("");
                    mAddRollNo.setText("");
                    rootView.findViewById(R.id.updateLayout).setVisibility(View.GONE);
                    mUpdateName.setText("");
                    mUpdateRollNo.setText("");
                    rootView.findViewById(R.id.deleteLayout).setVisibility(View.VISIBLE);
                }
            }
        });


        mAddButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try {
                        mydb.insertInfo(Integer.parseInt(mAddRollNo.getText().toString()),mAddName.getText().toString());
                    //Toast.makeText(getActivity().getApplicationContext(), "Row Inserted!", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Boolean isAvailable = true;
                Cursor res;
                try {
                    res = mydb.getInfo(Integer.parseInt(mSearchRollNo.getText().toString()));
                    res.moveToFirst();
                    String name = res.getString(res.getColumnIndex("name"));
                    mUpdateName.setText(name);
                }catch (Exception e){
                    mUpdateName.setText("");
                    isAvailable = false;
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }
                if(isAvailable) {
                    mSearchOption.setChecked(false);
                    mUpdateButton.setVisibility(View.VISIBLE);
                    mSearchButton.setVisibility(View.GONE);
                    mUpdateName.setFocusableInTouchMode(true);
                    mUpdateName.setFocusable(true);
                    mSearchRollNo.setFocusable(false);
                }
            }
        });

        mUpdateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try {
                    mydb.updateInfo(Integer.parseInt(mUpdateRollNo.getText().toString()), mUpdateName.getText().toString());
                    //Toast.makeText(getActivity().getApplicationContext(), "Row Updated!", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try {
                    if(mydb.deleteInfo(Integer.parseInt(mDeleteRollNo.getText().toString()))>0){
                        Toast.makeText(getActivity().getApplicationContext(), "Row Deleted!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(), "Record not found!", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSearchOption.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mUpdateButton.setVisibility(View.GONE);
                mSearchButton.setVisibility(View.VISIBLE);
                mUpdateName.setFocusable(false);
                mSearchRollNo.setFocusableInTouchMode(true);
                mSearchRollNo.setFocusable(true);
                mSearchRollNo.setText("");
                mUpdateName.setText("");

            }
        });


        return rootView;
    }
}

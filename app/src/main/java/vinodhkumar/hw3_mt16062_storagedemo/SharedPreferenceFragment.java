package vinodhkumar.hw3_mt16062_storagedemo;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by VinodhKumar on 29/09/16.
 */

public class SharedPreferenceFragment extends Fragment {
    public static final String PREFS_NAME = "SHARED_PREFERENCE";
    private TextView mSharedPreferenceVal;
    private Button mSubmitButton;
    private EditText mNewSharedPreferenceVal;
    public SharedPreferenceFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);




//        if (pref == null){
//            pref.edit().putInt("value",0);
//            pref.edit().commit();
//        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_sharedpreference, container, false);
        getActivity().findViewById(R.id.appDescription).setVisibility(View.GONE);
        //View activityAppDescription =  (TextView)((MainActivity)getActivity()).findViewById(R.id.appDescription);

        final SharedPreferences pref = this.getActivity().getSharedPreferences(PREFS_NAME,0);
        final SharedPreferences.Editor editor = pref.edit();

        mSharedPreferenceVal = (TextView) rootView.findViewById(R.id.sharedPreferenceVal);
        mSubmitButton = (Button) rootView.findViewById(R.id.submitButton);
        mNewSharedPreferenceVal = (EditText) rootView.findViewById(R.id.newSharedPrefValue);

        mSharedPreferenceVal.setText(pref.getString("value","0"));

       //View activityAppDescription =  (TextView)((MainActivity)getActivity()).findViewById(R.id.appDescription);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mSharedPreferenceVal.setText(mNewSharedPreferenceVal.getText());
                editor.putString("value",mNewSharedPreferenceVal.getText().toString());
                editor.commit();
                mSharedPreferenceVal.setText(pref.getString("value","0"));
            }
        });

        return rootView;
    }
}
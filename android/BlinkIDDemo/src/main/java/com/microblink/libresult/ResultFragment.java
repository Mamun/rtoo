package com.microblink.libresult;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.app.Singleton;
import com.google.gson.Gson;
import com.microblink.libresult.extract.BaseRecognitionResultExtractor;
import com.microblink.libresult.extract.germany.GermanIDFrontSideRecognitionResultExtractor;
import com.microblink.libresult.extract.germany.GermanIDMRZSideRecognitionResultExtractor;
import com.microblink.libresult.extract.IBaseRecognitionResultExtractor;
import com.microblink.libresult.extract.RecognitionResultEntry;
import com.microblink.locale.LanguageUtils;
import com.microblink.recognizers.BaseRecognitionResult;
import com.microblink.recognizers.blinkid.germany.front.GermanIDFrontSideRecognitionResult;
import com.microblink.recognizers.blinkid.germany.mrz.GermanIDMRZSideRecognitionResult;
import com.microblink.blinkid.R;
import java.util.List;

/**
 * Created by igor on 12/2/14.
 */
public class ResultFragment extends Fragment {

    private static final String DATA = "DATA";
    public static final String RESULT_PARCELABLE = "RESULT_PARCELABLE";

    IBaseRecognitionResultExtractor mResultExtractor = null;
    List<RecognitionResultEntry> extractedData;
    private BaseRecognitionResult mData = null;

    private AbsListView mListView;
    private ListAdapter mAdapter;

    // newInstance constructor for creating fragment with arguments
    public static ResultFragment newInstance(Parcelable result) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putParcelable(RESULT_PARCELABLE, result);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("InlinedApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        LanguageUtils.setLanguageConfiguration(getResources());
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(DATA)) {
            mData = savedInstanceState.getParcelable(DATA);
        }
        if (mData == null) {
            Bundle extras = getArguments();
            if (extras != null) {
                mData =  extras.getParcelable(RESULT_PARCELABLE);
            }
        }

        // CroatianIDBackSideRecognitionResult extends MRTDRecognitionResult so we first need
        // to check for CroatianIDBackSideRecognitionResult and then for MRTDRecognitionResult
       if (mData instanceof GermanIDMRZSideRecognitionResult) {
            mResultExtractor = new GermanIDMRZSideRecognitionResultExtractor(getActivity());
        } else if (mData instanceof GermanIDFrontSideRecognitionResult) {
            mResultExtractor = new GermanIDFrontSideRecognitionResultExtractor(getActivity());
        }


         else {
            mResultExtractor = new BaseRecognitionResultExtractor(getActivity());
        }

        // Extract data from BaseRecognitionResult
        extractedData = mResultExtractor.extractData(mData);



        Gson gson = new Gson();
        Log.i("extract data" , gson.toJson(extractedData) );


        String jsValue =  "javascript:scanResponse('" + gson.toJson(extractedData)  + "');void(0)";

        Log.i( "extract data" , jsValue );
     //   Singleton.getInstance().getWebView().loadUrl("javascript:scanResponse();void(0)");
        Singleton.getInstance().getWebView().loadUrl(jsValue);



        if (extractedData.size() < 1) {
            Toast.makeText(getActivity(), "Result list is empty", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        // Create adapter which will be used to populate ListView.
        mAdapter = new ResultEntryAdapter(getActivity(), R.layout.result_entry, extractedData);

        // Hide virtual keyboard
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView listView = new ListView(getActivity());

        listView.setAdapter(mAdapter);
        // some id is required so that android can save listview's scroll state
        // when activity goes to background
        // the id does not need to be unique
        listView.setId(android.R.id.text2);
        return listView;
    }

    @Override
    public void onResume() {
        LanguageUtils.setLanguageConfiguration(getResources());
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mData != null) {
            outState.putParcelable(DATA, mData);
        }
        super.onSaveInstanceState(outState);
    }
}

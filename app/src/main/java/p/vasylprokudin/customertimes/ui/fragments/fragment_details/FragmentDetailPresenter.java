package p.vasylprokudin.customertimes.ui.fragments.fragment_details;


import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import p.vasylprokudin.customertimes.data.database.MainDataDB;

@InjectViewState
public class FragmentDetailPresenter extends MvpPresenter<FragmentDetailsView> {

    public FragmentDetailPresenter(MainDataDB mainDataDB) {
        getViewState().initViews();
        getViewState().setupToolbar();
        getViewState().setData(mainDataDB);
    }

    public static String getFormattedCreatedDate(String created_date){
        String finalDateString = null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(created_date);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfnewformat =
                    new SimpleDateFormat("yyyy - MM - dd");
            finalDateString = sdfnewformat.format(convertedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return finalDateString;
    }

    public static String getFormattedCreatedTime(String created_time){
        String finalDateString = null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(created_time);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfnewformat =
                    new SimpleDateFormat("HH : mm : ss");
            finalDateString = sdfnewformat.format(convertedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return finalDateString;
    }
}

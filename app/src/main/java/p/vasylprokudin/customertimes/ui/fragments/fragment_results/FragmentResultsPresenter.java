package p.vasylprokudin.customertimes.ui.fragments.fragment_results;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import p.vasylprokudin.customertimes.R;
import p.vasylprokudin.customertimes.data.ApiService;
import p.vasylprokudin.customertimes.data.RetrofitClient;
import p.vasylprokudin.customertimes.data.database.DatabaseHelper;
import p.vasylprokudin.customertimes.data.network.DescribeResponse;
import p.vasylprokudin.customertimes.data.database.MainDataDB;
import p.vasylprokudin.customertimes.data.network.QueryResponse;
import retrofit2.Retrofit;

@InjectViewState
public class FragmentResultsPresenter extends MvpPresenter<FragmentResultsView> {

    private ApiService apiService;
    private DatabaseHelper myDb;
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Retrofit retrofit;
    private static int delayTime = 500;

    //Variables for pagination
    private int previousTotal = 0;
    private boolean isLoading = true;
    private static int PAGE_SIZE = 40;
    private int position = 0;
    private static int FIRST_LOAD = 0;
    private static int LOAD_MORE = 1;


    public FragmentResultsPresenter(Context context) {
        getViewState().playLottieAnimation();
        getViewState().initViews();
        getViewState().setupToolbar();
        fetchDescribeDataFromApi();
        this.context = context;
    }

    private void fetchDescribeDataFromApi() {
        retrofit = RetrofitClient.getInstance();
        apiService = retrofit.create(ApiService.class);

        compositeDisposable.add(apiService.getColumnNames()
                .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<DescribeResponse>() {
            @Override
            public void accept(DescribeResponse response) throws Exception {
                createTableAccount(response);
            }
        }));

    }

    private void fetchQueryDataFromApi() {
        retrofit = RetrofitClient.getInstance();
        apiService = retrofit.create(ApiService.class);

        compositeDisposable.add(apiService.getQueryData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<QueryResponse>() {
                    @Override
                    public void accept(QueryResponse queryResponse) throws Exception {
                        if (myDb.isInsertedDataIfColumnInTableExists(queryResponse)){
                            String message = context.getString(R.string.successfull_inserting);
                            getViewState().hideLottieAnimation();
                            getViewState().showMessage(message);

                            getResultsFromDB(FIRST_LOAD);
                        }
                        else {
                            String message = context.getString(R.string.error_loading_data);
                            getViewState().hideLottieAnimation();
                            getViewState().showMessage(message);
                        }
                    }
                }));
    }

    private void getResultsFromDB(final int type) {
        getViewState().showProgressBar();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> columnsList = new ArrayList<>();
                columnsList = myDb.getColumnNames();
                List<MainDataDB> mainDataList = new ArrayList<>();
                Cursor cursor = myDb.getAllData();
                if (cursor != null){
                    if (cursor.moveToPosition(position)){
                        position = position + PAGE_SIZE;
                        while (cursor.moveToNext() && cursor.getPosition()!= position){
                            MainDataDB mainDataDB = new MainDataDB();
                            for (int i = 0; i < columnsList.size(); i ++){
                                String column = columnsList.get(i);
                                int columnIndex = cursor.getColumnIndex(column);
                                String content = cursor.getString(columnIndex);
                                setMainDataDB(mainDataDB, columnsList.get(i), content);
                            }
                            mainDataList.add(mainDataDB);
                        }
                    }
                    if (type == FIRST_LOAD){
                        getViewState().showResults(mainDataList);
                    }
                    else {
                        getViewState().showMoreResults(mainDataList);
                    }
                    position = position + PAGE_SIZE;
                }
                else {
                    getViewState().showMessage(context.getString(R.string.no_data));
                    getViewState().hideProgressBar();
                }
            }
        }, delayTime);
    }

    public void onScroll(int dy, int visibleItemCount, int totalItemCount, int pastVisibleItems) {
        //user scrolls up
        if (dy>0){
            if (isLoading){
                if (totalItemCount > previousTotal){
                    isLoading = false;
                    previousTotal = totalItemCount;
                }
            }
            int viewThreshold = PAGE_SIZE;
            if (!isLoading && (totalItemCount-visibleItemCount)<=
                    pastVisibleItems + viewThreshold){
                getResultsFromDB(LOAD_MORE);
                isLoading = true;
            }
        }
    }

    private void createTableAccount(DescribeResponse response) {
        myDb = new DatabaseHelper(context, response);

        if(myDb.isTableExists()){
            if (myDb.isEmptyTable()){
                fetchQueryDataFromApi();
            }
            else {
                getViewState().hideLottieAnimation();
                getResultsFromDB(FIRST_LOAD);
            }
        }
    }

    public void onItemClick(MainDataDB mainData) {
        getViewState().showFragmentDetails(mainData);
    }

    public static boolean setMainDataDB(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static <V> V getMainData(Object object, String fieldName) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return (V) field.get(object);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return null;
    }

    public void onMoreDataLoaded() {
        getViewState().hideProgressBar();
    }


    public void clearDisposable() {
        compositeDisposable.clear();
    }
}

package p.vasylprokudin.customertimes.ui.fragments.fragment_results;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import p.vasylprokudin.customertimes.data.database.MainDataDB;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface FragmentResultsView extends MvpView {

    void setupToolbar();

    void showMessage(String string);

    void hideLottieAnimation();

    void playLottieAnimation();

    void initViews();

    void showResults(List<MainDataDB> mainDataList);

    void showMoreResults(List<MainDataDB> mainDataList);

    void showProgressBar();

    void hideProgressBar();

    @StateStrategyType(SkipStrategy.class)
    void showFragmentDetails(MainDataDB mainData);

}


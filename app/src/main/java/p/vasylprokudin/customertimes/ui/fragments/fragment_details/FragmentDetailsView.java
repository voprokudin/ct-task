package p.vasylprokudin.customertimes.ui.fragments.fragment_details;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import p.vasylprokudin.customertimes.data.database.MainDataDB;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface FragmentDetailsView extends MvpView {

    void initViews();

    void setupToolbar();

    void setData(MainDataDB mainDataDB);
}

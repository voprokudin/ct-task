package p.vasylprokudin.customertimes.ui;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityView> {

    public MainActivityPresenter() {
        getViewState().initViews();
        getViewState().showFragmentResults();
    }
}

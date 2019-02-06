package p.vasylprokudin.customertimes.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import p.vasylprokudin.customertimes.R;
import p.vasylprokudin.customertimes.ui.fragments.fragment_results.FragmentResults;

public class MainActivity extends MvpAppCompatActivity implements MainActivityView {

    @InjectPresenter
    MainActivityPresenter mPresenter;
    @BindView(R.id.layout_stub)
    ViewStub layoutStub;
    @BindView(R.id.screen_area)
    FrameLayout screenArea;
    @BindString(R.string.results)
    String results;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void showFragmentResults() {
        FragmentResults fragmentResults = new FragmentResults();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.screen_area, fragmentResults);
        ft.commit();
    }
}

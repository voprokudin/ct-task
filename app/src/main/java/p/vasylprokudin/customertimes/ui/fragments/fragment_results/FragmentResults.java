package p.vasylprokudin.customertimes.ui.fragments.fragment_results;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import p.vasylprokudin.customertimes.R;
import p.vasylprokudin.customertimes.data.database.MainDataDB;
import p.vasylprokudin.customertimes.ui.MainActivity;
import p.vasylprokudin.customertimes.ui.fragments.fragment_details.FragmentDetails;

public class FragmentResults extends MvpAppCompatFragment implements FragmentResultsView, AdapterResults.OnItemClicked {

    @BindString(R.string.results)
    String results;
    Unbinder unbinder;

    @InjectPresenter
    FragmentResultsPresenter mPresenter;
    @BindView(R.id.lottie_loading)
    LottieAnimationView lottieAnimation;
    @BindView(R.id.recycler_view_results)
    RecyclerView recyclerViewResults;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private static final String TAG = "details";
    private AdapterResults adapter;
    private FragmentManager fragmentManager;
    //Variables for pagination
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    @ProvidePresenter
    FragmentResultsPresenter providePresenter() {
        return new FragmentResultsPresenter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_results, container, false);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void setupToolbar() {
        LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.linear_layout_toolbar);
        ll.removeAllViews(); // remove previous view, add 2nd layout
        View toolbar_view = LayoutInflater.from(getActivity()).inflate(R.layout.toolbar_main, ll, false);
        ll.addView(toolbar_view);
        Toolbar toolbar = (Toolbar) toolbar_view.findViewById(R.id.toolbar);
        toolbar.setTitle(results);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);

    }

    @Override
    public void showMessage(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void hideLottieAnimation() {
        if (lottieAnimation.isAnimating()) {
            lottieAnimation.cancelAnimation();
        }
        lottieAnimation.setVisibility(View.GONE);
    }

    @Override
    public void playLottieAnimation() {
        lottieAnimation.playAnimation();
    }

    @Override
    public void initViews() {
        recyclerViewResults.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewResults.setLayoutManager(layoutManager);

        recyclerViewResults.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                mPresenter.onScroll(dy, visibleItemCount, totalItemCount, pastVisibleItems);
            }
        });

        fragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public void showResults(List<MainDataDB> mainDataList) {
        adapter = new AdapterResults(this, mainDataList);
        recyclerViewResults.setAdapter(adapter);
        mPresenter.onMoreDataLoaded();

    }

    @Override
    public void showMoreResults(List<MainDataDB> mainDataList) {
        adapter.loadMoreData(mainDataList);
        mPresenter.onMoreDataLoaded();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showFragmentDetails(MainDataDB mainData) {
        FragmentDetails fragment_details = new FragmentDetails();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit, R.anim.slide_right_enter, R.anim.slide_right_exit);
        Bundle bundle = new Bundle();
        bundle.putParcelable(TAG, mainData);
        fragment_details.setArguments(bundle);
        ft.replace(R.id.screen_area, fragment_details);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(MainDataDB mainData) {
        mPresenter.onItemClick(mainData);
    }

    @Override
    public void onStop() {
        mPresenter.clearDisposable();
        super.onStop();
    }
}

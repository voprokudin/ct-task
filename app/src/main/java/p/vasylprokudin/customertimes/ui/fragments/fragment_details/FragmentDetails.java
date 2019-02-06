package p.vasylprokudin.customertimes.ui.fragments.fragment_details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.Objects;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import p.vasylprokudin.customertimes.R;
import p.vasylprokudin.customertimes.data.database.MainDataDB;
import p.vasylprokudin.customertimes.ui.MainActivity;

public class FragmentDetails extends MvpAppCompatFragment implements FragmentDetailsView {

    private static final String TAG = "details";
    @BindView(R.id.tv_details_currency)
    TextView tvDetailsCurrency;
    Unbinder unbinder;
    @BindView(R.id.tv_details_name)
    TextView tvDetailsName;
    @BindView(R.id.tv_details_created_date)
    TextView tvDetailsCreatedDate;
    @BindView(R.id.tv_details_created_time)
    TextView tvDetailsCreatedTime;
    @BindView(R.id.tv_details_deleted)
    TextView tvDetailsDeleted;
    @BindView(R.id.tv_details_id)
    TextView tvDetailsId;

    private FragmentManager fragmentManager;

    @BindString(R.string.details)
    String details;
    @BindString(R.string.name)
    String name;
    @BindString(R.string.deleted_status)
    String deleted_status;
    @BindString(R.string.id)
    String id;

    @InjectPresenter
    FragmentDetailPresenter mPresenter;

    @ProvidePresenter
    FragmentDetailPresenter provideFragmentDetailPresenter() {
        MainDataDB results = Objects.requireNonNull(getArguments()).getParcelable(TAG);
        return new FragmentDetailPresenter(Objects.requireNonNull(results));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_details, container, false);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }


    @Override
    public void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public void setupToolbar() {
        LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.linear_layout_toolbar);
        ll.removeAllViews(); // remove previous view, add 2nd layout
        View toolbar_view = LayoutInflater.from(getActivity()).inflate(R.layout.toolbar_title_center, ll, false);
        ll.addView(toolbar_view);

        ImageView ivBack = (ImageView) toolbar_view.findViewById(R.id.ivToolbarBack);
        TextView tvToolbarTitleCenter = (TextView) toolbar_view.findViewById(R.id.tvToolbarTitleCenter);
        Toolbar toolbar = (Toolbar) toolbar_view.findViewById(R.id.toolbarTitleCenter);

        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        tvToolbarTitleCenter.setText(details);
        View.OnClickListener listenerBack = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        };
        ivBack.setOnClickListener(listenerBack);
    }

    @Override
    public void setData(MainDataDB mainDataDB) {
        String content_name = mainDataDB.getName();
        String content_currency = mainDataDB.getCurrencyIsoCode();
        String created_date = mainDataDB.getCreatedDate();
        String content_created_date = FragmentDetailPresenter.getFormattedCreatedDate(created_date);
        String content_created_time = FragmentDetailPresenter.getFormattedCreatedTime(created_date);
        String content_deleted = mainDataDB.getIsDeleted();
        String content_id = mainDataDB.getId();

        tvDetailsCurrency.setText(content_currency);
        tvDetailsName.setText(String.format("%s %s", name, content_name));
        tvDetailsCreatedDate.setText(content_created_date);
        tvDetailsCreatedTime.setText(content_created_time);
        tvDetailsDeleted.setText(String.format("%s %s", deleted_status, content_deleted));
        tvDetailsId.setText(String.format("%s %s", id, content_id));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

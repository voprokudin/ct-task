package p.vasylprokudin.customertimes.ui.fragments.fragment_results;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import p.vasylprokudin.customertimes.R;
import p.vasylprokudin.customertimes.data.database.MainDataDB;
import p.vasylprokudin.customertimes.ui.fragments.fragment_details.FragmentDetailPresenter;

public class AdapterResults extends RecyclerView.Adapter<AdapterResults.ViewHolder> {

    private OnItemClicked onClick;
    private Context context = null;
    private List<MainDataDB> mainDataList;

    public AdapterResults(OnItemClicked onClick, List<MainDataDB> mainDataList) {
        this.onClick = onClick;
        this.mainDataList = mainDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context)
                .inflate(R.layout.single_item_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final MainDataDB mainData = mainDataList.get(i);
        String currency = mainData.getCurrencyIsoCode();
        String name = mainData.getName();
        String id = mainData.getId();
        String created_date = mainData.getCreatedDate();
        String formatted_created_date = FragmentDetailPresenter.getFormattedCreatedDate(created_date);

        viewHolder.tvCurrency.setText(currency);
        viewHolder.tvName.setText(name);
        viewHolder.tvId.setText(id);
        viewHolder.tvCreatedDate.setText(formatted_created_date);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(mainData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_id)
        TextView tvId;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_currency)
        TextView tvCurrency;
        @BindView(R.id.tv_created_date)
        TextView tvCreatedDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void loadMoreData(List<MainDataDB> result){
        mainDataList.addAll(result);
        notifyDataSetChanged();
    }

    public interface OnItemClicked {
        void onItemClick(MainDataDB mainData);
    }

}

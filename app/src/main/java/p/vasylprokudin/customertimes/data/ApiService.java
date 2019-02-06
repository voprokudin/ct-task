package p.vasylprokudin.customertimes.data;

import io.reactivex.Observable;
import p.vasylprokudin.customertimes.data.network.DescribeResponse;
import p.vasylprokudin.customertimes.data.network.QueryResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("desc")
    Observable<DescribeResponse> getColumnNames();

    @GET("query")
    Observable<QueryResponse> getQueryData();

}

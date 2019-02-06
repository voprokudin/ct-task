package p.vasylprokudin.customertimes.data.network;

import java.util.List;

public class QueryResponse {

    private List<MainData> records;

    public List<MainData> getRecords() {
        return records;
    }

    public void setRecords(List<MainData> records) {
        this.records = records;
    }
}

package p.vasylprokudin.customertimes.data.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DescribeResponse {

    @SerializedName("fields")
    private List<ColumnName> columnNames;

    public List<ColumnName> getColumnNames() {
        return columnNames;
    }


    public void setColumnNames(List<ColumnName> columnNames) {
        this.columnNames = columnNames;
    }
}

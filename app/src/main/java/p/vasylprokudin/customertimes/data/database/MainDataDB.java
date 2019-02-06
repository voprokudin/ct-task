package p.vasylprokudin.customertimes.data.database;

import android.os.Parcel;
import android.os.Parcelable;

public class MainDataDB implements Parcelable {

    public String AccountId__c;
    public String CreatedById;
    public String CreatedDate;
    public String CurrencyIsoCode;
    public String EndDate__c;
    public String Id;
    public String LastModifiedById;
    public String LastModifiedDate;
    public String Name;
    public String OwnerId;
    public String RecordTypeId;
    public String StartDate__c;
    public String Status__c;
    public String Subject__c;
    public String SystemModstamp;

    public String IsApproved__c;
    public String IsDeleted;
    public String IsDone__c;
    public String IsLocked__c;

    public MainDataDB() {
    }

    protected MainDataDB(Parcel in) {
        AccountId__c = in.readString();
        CreatedById = in.readString();
        CreatedDate = in.readString();
        CurrencyIsoCode = in.readString();
        EndDate__c = in.readString();
        Id = in.readString();
        LastModifiedById = in.readString();
        LastModifiedDate = in.readString();
        Name = in.readString();
        OwnerId = in.readString();
        RecordTypeId = in.readString();
        StartDate__c = in.readString();
        Status__c = in.readString();
        Subject__c = in.readString();
        SystemModstamp = in.readString();
        IsApproved__c = in.readString();
        IsDeleted = in.readString();
        IsDone__c = in.readString();
        IsLocked__c = in.readString();
    }

    public static final Creator<MainDataDB> CREATOR = new Creator<MainDataDB>() {
        @Override
        public MainDataDB createFromParcel(Parcel in) {
            return new MainDataDB(in);
        }

        @Override
        public MainDataDB[] newArray(int size) {
            return new MainDataDB[size];
        }
    };

    public void setAccountId__c(String accountId__c) {
        AccountId__c = accountId__c;
    }

    public void setCreatedById(String createdById) {
        CreatedById = createdById;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public void setCurrencyIsoCode(String currencyIsoCode) {
        CurrencyIsoCode = currencyIsoCode;
    }

    public void setEndDate__c(String endDate__c) {
        EndDate__c = endDate__c;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setLastModifiedById(String lastModifiedById) {
        LastModifiedById = lastModifiedById;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        LastModifiedDate = lastModifiedDate;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }

    public void setRecordTypeId(String recordTypeId) {
        RecordTypeId = recordTypeId;
    }

    public void setStartDate__c(String startDate__c) {
        StartDate__c = startDate__c;
    }

    public void setStatus__c(String status__c) {
        Status__c = status__c;
    }

    public void setSubject__c(String subject__c) {
        Subject__c = subject__c;
    }

    public void setSystemModstamp(String systemModstamp) {
        SystemModstamp = systemModstamp;
    }

    public void setIsApproved__c(String isApproved__c) {
        IsApproved__c = isApproved__c;
    }

    public void setIsDeleted(String isDeleted) {
        IsDeleted = isDeleted;
    }

    public void setIsDone__c(String isDone__c) {
        IsDone__c = isDone__c;
    }

    public void setIsLocked__c(String isLocked__c) {
        IsLocked__c = isLocked__c;
    }

    public String getAccountId__c() {
        return AccountId__c;
    }

    public String getCreatedById() {
        return CreatedById;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public String getCurrencyIsoCode() {
        return CurrencyIsoCode;
    }

    public String getEndDate__c() {
        return EndDate__c;
    }

    public String getId() {
        return Id;
    }

    public String getLastModifiedById() {
        return LastModifiedById;
    }

    public String getLastModifiedDate() {
        return LastModifiedDate;
    }

    public String getName() {
        return Name;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public String getRecordTypeId() {
        return RecordTypeId;
    }

    public String getStartDate__c() {
        return StartDate__c;
    }

    public String getStatus__c() {
        return Status__c;
    }

    public String getSubject__c() {
        return Subject__c;
    }

    public String getSystemModstamp() {
        return SystemModstamp;
    }

    public String getIsApproved__c() {
        return IsApproved__c;
    }

    public String getIsDeleted() {
        return IsDeleted;
    }

    public String getIsDone__c() {
        return IsDone__c;
    }

    public String getIsLocked__c() {
        return IsLocked__c;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(AccountId__c);
        parcel.writeString(CreatedById);
        parcel.writeString(CreatedDate);
        parcel.writeString(CurrencyIsoCode);
        parcel.writeString(EndDate__c);
        parcel.writeString(Id);
        parcel.writeString(LastModifiedById);
        parcel.writeString(LastModifiedDate);
        parcel.writeString(Name);
        parcel.writeString(OwnerId);
        parcel.writeString(RecordTypeId);
        parcel.writeString(StartDate__c);
        parcel.writeString(Status__c);
        parcel.writeString(Subject__c);
        parcel.writeString(SystemModstamp);
        parcel.writeString(IsApproved__c);
        parcel.writeString(IsDeleted);
        parcel.writeString(IsDone__c);
        parcel.writeString(IsLocked__c);
    }
}

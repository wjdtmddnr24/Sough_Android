package moe.chocola.sough.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("work")
    @Expose
    private Workspace work=null;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Workspace getWork() {
        return work;
    }

    public void setWork(Workspace work) {
        this.work = work;
    }
}

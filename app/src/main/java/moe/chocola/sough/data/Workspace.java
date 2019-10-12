package moe.chocola.sough.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Workspace {

    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
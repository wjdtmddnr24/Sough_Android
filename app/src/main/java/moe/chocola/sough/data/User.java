package moe.chocola.sough.data;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("workspace")
    @Expose
    private List<Workspace> workspace = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<Workspace> getWorkspace() {
        return workspace;
    }

    public void setWorkspace(List<Workspace> workspace) {
        this.workspace = workspace;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}


/*
{
  "_id": "5d9c2f7754a7036d137557ec",
  "id": "wjdtmddnr24",
  "created": "2019-10-08T06:40:55.182Z",
  "workspace": [
    {
      "created": "2019-10-08T06:40:55.183Z",
      "_id": "5d9c2f7754a7036d137557ed",
      "title": "first",
      "content": "first\ncontent"
    },
    {
      "created": "2019-10-08T07:11:00.288Z",
      "_id": "5d9c368454a7036d137557ee",
      "title": "second",
      "content": "second\ncontent"
    }
  ],
  "__v": 1
}
 */
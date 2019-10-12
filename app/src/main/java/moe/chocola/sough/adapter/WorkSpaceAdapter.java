package moe.chocola.sough.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import moe.chocola.sough.R;
import moe.chocola.sough.data.User;
import moe.chocola.sough.data.Workspace;

public class WorkSpaceAdapter extends RecyclerView.Adapter<WorkSpaceAdapter.ViewHolder> {
    private List<Workspace> workspaces;
    private Context context;

    public WorkSpaceAdapter(List<Workspace> workspaces, Context context) {
        this.workspaces = workspaces;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_work, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Workspace workspace = getUser(position);
        holder.title.setText(workspace.getTitle());
        holder.content.setText(workspace.getContent());
    }

    private Workspace getUser(int position) {
        return workspaces.get(position);
    }

    public void updateWorkspaces(List<Workspace> workspaces) {
        this.workspaces = workspaces;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return workspaces.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.content = itemView.findViewById(R.id.content);
        }
    }


}

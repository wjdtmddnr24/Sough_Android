package moe.chocola.sough.activity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import moe.chocola.sough.R;
import moe.chocola.sough.adapter.WorkSpaceAdapter;
import moe.chocola.sough.data.User;
import moe.chocola.sough.data.Workspace;
import moe.chocola.sough.util.SoughService;
import moe.chocola.sough.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkSpaceActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private WorkSpaceAdapter adapter;
    private SoughService soughService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_space);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("작업공간 보기");

        soughService = Util.getSoughService();

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new WorkSpaceAdapter(new ArrayList<Workspace>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        getUser();
    }

    public void getUser() {
        soughService.getUser(Util.getUserWorkspaceName(this)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    adapter.updateWorkspaces(response.body().getWorkspace());
                } else {
                    Toast.makeText(WorkSpaceActivity.this, "Error : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(WorkSpaceActivity.this, "에러발생!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

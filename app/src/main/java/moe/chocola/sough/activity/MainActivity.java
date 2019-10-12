package moe.chocola.sough.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import moe.chocola.sough.R;
import moe.chocola.sough.util.Util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int QR_SCAN = 0;
    public static final int QR_WORK_ADD = 1;

    public static int mode = QR_SCAN;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "스캔이 취소되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                String resultContent = result.getContents();
                Intent intent = new Intent(MainActivity.this, ScanQRActivity.class);
                intent.putExtra("content", resultContent);
                intent.putExtra("mode", mode);
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView userWorkspace = findViewById(R.id.user_workspace);
        userWorkspace.setText(Util.getUserWorkspaceName(this).isEmpty() ? "이곳을 클릭해서 \n작업공간 이름을 설정하세요." : String.format("나의 작업공간\n%s", Util.getUserWorkspaceName(this)));

        findViewById(R.id.card_workspace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("작업공간 이름 설정");
                View viewInflated = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.dialog_setuser, (ViewGroup) v.getRootView(), false);
                final EditText input = viewInflated.findViewById(R.id.input);
                builder.setView(viewInflated);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String name = input.getText().toString();
                        Util.setUserWorkspaceName(MainActivity.this, name);
                        userWorkspace.setText(Util.getUserWorkspaceName(MainActivity.this).isEmpty() ? "이곳을 클릭해서 \n작업공간 이름을 설정하세요." : String.format("나의 작업공간\n%s", Util.getUserWorkspaceName(MainActivity.this)));
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        findViewById(R.id.card_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.getUserWorkspaceName(MainActivity.this).isEmpty()) {
                    Toast.makeText(MainActivity.this, "먼저 작업공간을 설정하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, WorkSpaceActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.card_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = QR_SCAN;
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(true);
                integrator.setOrientationLocked(true);
                integrator.setCaptureActivity(CaptureActivity.class);
                integrator.initiateScan();
            }
        });

        findViewById(R.id.card_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.getUserWorkspaceName(MainActivity.this).isEmpty()) {
                    Toast.makeText(MainActivity.this, "먼저 작업공간을 설정하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                mode = QR_WORK_ADD;
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(true);
                integrator.setOrientationLocked(true);
                integrator.setCaptureActivity(CaptureActivity.class);
                integrator.initiateScan();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}

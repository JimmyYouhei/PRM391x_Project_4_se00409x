package vn.org.quantestyoutube2.prm391x_project_4_se00409x;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.CommandAndInterface.Command;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.CommandAndInterface.SetupRecyclerView;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Database.VideoRoom;


public class FirstVideoScreen extends AppCompatActivity {
    private static final String TAG = "FirstVideoScreen";

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        Command.setupToolbar(this);


        recyclerView = findViewById(R.id.firstScreenRecyclerView);

        SetupRecyclerView setupRecyclerView = new SetupRecyclerView();
        setupRecyclerView.execute(recyclerView , null);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Command.createMenu(this , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.videoHistory:
                Intent toVideoHistory = new Intent(this , VideoHistory.class);
                toVideoHistory.putExtra(SignIn.USERNAME_KEY , getIntent().getStringExtra(SignIn.USERNAME_KEY));
                startActivity(toVideoHistory);
            break;

            case R.id.accountLogOut:
                Command.logOut(this);
                break;

            case R.id.searchVideo:
                Intent toSearch = new Intent(this , SearchVideo.class);
                toSearch.putExtra(SignIn.USERNAME_KEY , getIntent().getStringExtra(SignIn.USERNAME_KEY));
                startActivity(toSearch);
                break;

        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onDestroy() {
        VideoRoom.destroyInstance();
        super.onDestroy();
    }
}

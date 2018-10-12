package vn.org.quantestyoutube2.prm391x_project_4_se00409x;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.CommandAndInterface.Command;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.CommandAndInterface.SetupRecyclerView;

public class SearchVideo extends AppCompatActivity {
    private EditText txtSearchText;
    private Button btnSearch;
    private RecyclerView searchResultRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_video);
        txtSearchText = findViewById(R.id.txtSearchText);
        btnSearch = findViewById(R.id.btnSearch);
        searchResultRecyclerView = findViewById(R.id.searchRecyclerView);

        Command.setupToolbar(this);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetupRecyclerView setupSearchRecyclerView = new SetupRecyclerView();
                setupSearchRecyclerView.execute(searchResultRecyclerView , txtSearchText.getText().toString().trim());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Command.createMenu(this , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.accountLogOut:
                Command.logOut(this);
                break;

            case R.id.searchVideo:
                Toast.makeText(this, "You are at it", Toast.LENGTH_LONG).show();
                break;

            case R.id.videoHistory:
                Intent toVideoHistory = new Intent(this , FirstVideoScreen.class);
                toVideoHistory.putExtra(SignIn.USERNAME_KEY , getIntent().getStringExtra(SignIn.USERNAME_KEY));
                toVideoHistory.putExtra(FirstVideoScreen.REQUEST_HISTORY , FirstVideoScreen.REQUEST_HISTORY);
                startActivity(toVideoHistory);
        }
        return super.onOptionsItemSelected(item);
    }
}

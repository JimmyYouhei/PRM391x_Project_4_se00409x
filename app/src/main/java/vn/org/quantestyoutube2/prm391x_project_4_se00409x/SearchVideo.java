/*
 * MIT License
 *
 * Copyright (c) 2019.  Jimmy Youhei (Quan Nguyen)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.command_and_interface.Command;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.command_and_interface.SetupRecyclerView;

// Activity to help to search video with keywords
public class SearchVideo extends AppCompatActivity {
    private EditText txtSearchText;
    private Button btnSearch;
    private RecyclerView searchResultRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_video);
        //tie View to its place in xml
        txtSearchText = findViewById(R.id.txtSearchText);
        btnSearch = findViewById(R.id.btnSearch);
        searchResultRecyclerView = findViewById(R.id.searchRecyclerView);

        // setup an reusable toolbar
        Command.setupToolbar(this);

        // on search button click: search video according to the keyword and setup RecyclerView , still max 10 videos
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetupRecyclerView setupSearchRecyclerView = new SetupRecyclerView();
                setupSearchRecyclerView.execute(searchResultRecyclerView , txtSearchText.getText().toString().trim());
            }
        });
    }

    // make the 3 dot Option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Command.createMenu(this , menu);
        return super.onCreateOptionsMenu(menu);
    }

    //when Menu Item is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // click log out will go to Sign In activity
        switch (item.getItemId()){
            case R.id.accountLogOut:
                Command.logOut(this);
                break;

                // click the Search Video make Toast to notify that they are already at it
            case R.id.searchVideo:
                Toast.makeText(this, "You are at it", Toast.LENGTH_LONG).show();
                break;

                // case click video history go to the First Video Screen but with REQUEST_HISTORY to know what to show
                // carry over the usernname in the intent
            case R.id.videoHistory:
                Intent toVideoHistory = new Intent(this , FirstVideoScreen.class);
                toVideoHistory.putExtra(SignIn.USERNAME_KEY , getIntent().getStringExtra(SignIn.USERNAME_KEY));
                toVideoHistory.putExtra(FirstVideoScreen.REQUEST_HISTORY , FirstVideoScreen.REQUEST_HISTORY);
                startActivity(toVideoHistory);
                overridePendingTransition(R.animator.push_left_in , R.animator.push_left_out);
        }
        return super.onOptionsItemSelected(item);
    }
}

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.command_and_interface.Command;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.database.DatabaseSource;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.entity.UserAccount;

// the Sign In activity
public class SignIn extends AppCompatActivity {
    private static final String TAG = "SignIn";

    public static final int SIGN_UP_REQUEST_CODE = 1000;
    public static final String USERNAME_KEY = "username , key";

    private EditText txtUsername;
    private EditText txtPassword;
    private TextView btnToSignUp;
    private Button btnSignIn;

    

    // DatabaseSource object include SQLite and manage it
    private DatabaseSource databaseSource;
    public static List<UserAccount> userAccountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUsername = findViewById(R.id.txtUsernameSignIn);
        txtPassword = findViewById(R.id.txtPasswordSignIn);
        btnToSignUp = findViewById(R.id.btnToSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);

        // make new SQLite or acquired old one if available
        databaseSource = new DatabaseSource(this);

        // check whether SQLite Database exist if not initialize the List else transfer database to List
        if (databaseSource.countAccount() == 0){
            Log.d(TAG, "onCreate: no Account");
            userAccountList = new ArrayList<>();
        } else {
            userAccountList = databaseSource.getAllAccount();
            Log.d(TAG, "onCreate: "+ userAccountList);
        }

        // Make Text View Sign Up like a button and assign listener
        btnToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make Intent to go to Sign Up for result
                Intent toSignUpActivity = new Intent(SignIn.this , SignUp.class);
                startActivityForResult(toSignUpActivity, SIGN_UP_REQUEST_CODE);
                overridePendingTransition(R.animator.push_left_in , R.animator.push_left_out);
            }
        });

        // case click on Sign In button
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // case username empty make Toast to notify user
                if (txtUsername.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignIn.this, "Username cannot be empty", Toast.LENGTH_LONG).show();
                    // case username not exist make Toast to notify user
                } else if (!Command.isUsernameAlreadyExist(userAccountList , txtUsername.getText().toString().trim())){
                    Toast.makeText(SignIn.this,
                            "Username is not exist please create new Username ", Toast.LENGTH_LONG).show();
                    // case username exist but no password make Toast to notify user
                } else {
                    if (txtPassword.getText().toString().trim().isEmpty()){
                        Toast.makeText(SignIn.this, "Passowrd cannot be empty", Toast.LENGTH_LONG).show();
                        // case username exist but password is wrong make Toast to notify user
                    }else if (!Command.isPasswordCorrect(userAccountList , txtUsername.getText().toString().trim(),
                            txtPassword.getText().toString().trim())){
                        Toast.makeText(SignIn.this, "Password is incorrect", Toast.LENGTH_LONG).show();
                        // case both is ok go to First Video Screen
                    } else {
                        Toast.makeText(SignIn.this, "Welcome", Toast.LENGTH_LONG).show();
                        Intent toVideoList = new Intent(SignIn.this , FirstVideoScreen.class);
                        toVideoList.putExtra(USERNAME_KEY , txtUsername.getText().toString().trim());

                        // clear username and password when user logout these field will be empty
                        txtPassword.setText("");
                        txtUsername.setText("");
                        startActivity(toVideoList);
                        overridePendingTransition(R.animator.push_left_in , R.animator.push_left_out);
                    }
                }
            }
        });

    }

    // close SQLite to avoid leak
    @Override
    protected void onPause() {
        super.onPause();
        databaseSource.close();
    }

    // resume SQLite
    @Override
    protected void onResume() {
        super.onResume();
        databaseSource.open();
    }

}

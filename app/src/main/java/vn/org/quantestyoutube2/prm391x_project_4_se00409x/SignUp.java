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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.command_and_interface.Command;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.database.DatabaseSource;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.entity.UserAccount;

public class SignUp extends AppCompatActivity {
    private static final String TAG = "SignUp";

    private EditText txtUsername;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private TextView btnToSignIn;
    private Button btnSignUp;
    private Intent intentFromSignIn;

    private DatabaseSource databaseSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // make new SQLite or acquired old one if available
        databaseSource = new DatabaseSource(this);

        // get intent detail from Sign In
        intentFromSignIn = getIntent();

        // tie View to its place
        txtUsername = findViewById(R.id.txtUsernameSignUp);
        txtPassword = findViewById(R.id.txtPasswordSignUp);
        txtConfirmPassword = findViewById(R.id.txtConfirmSignUp);
        btnToSignIn = findViewById(R.id.btnToSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        // case click on the to Sign In , back to Sign In activity
        btnToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
                overridePendingTransition(R.animator.push_left_in,R.animator.push_left_out);
            }
        });

        // case click on Sign Up Button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if username is empty make Toast to notify
                if (txtUsername.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUp.this, "Username is a must", Toast.LENGTH_LONG).show();
                    // if username is already exist make Toast to notify
                } else if (Command.isUsernameAlreadyExist(SignIn.userAccountList , txtUsername.getText().toString().trim())){
                    Toast.makeText(SignUp.this, "Username already exist", Toast.LENGTH_LONG).show();
                } else {
                    // if username is not duplicate but no passowrd make Toast to notify
                    if (txtPassword.getText().toString().trim().isEmpty() ){
                        Toast.makeText(SignUp.this, "Username must have a Passowrd", Toast.LENGTH_LONG).show();
                        // if username is not duplicate but password and confirm password does not match make Toast to notify user
                    }else if (!txtPassword.getText().toString().trim().equals(txtConfirmPassword.getText().toString().trim())){
                        Toast.makeText(SignUp.this, "Password does not match ", Toast.LENGTH_LONG).show();
                        // if everything ok make new account and add to both the Sign In List and Sign In SQLite database
                    } else {
                        UserAccount account = new UserAccount(txtUsername.getText().toString().trim() ,
                                txtPassword.getText().toString().trim());
                        SignIn.userAccountList.add(account);
                        databaseSource.insert(account);
                        Toast.makeText(SignUp.this, "User created", Toast.LENGTH_LONG).show();
                        setResult(RESULT_OK);
                        finish();
                        overridePendingTransition(R.animator.push_left_in , R.animator.push_left_out);
                    }
                }
            }
        });

    }

    // close database to avoid leak
    @Override
    protected void onPause() {
        databaseSource.close();
        super.onPause();
    }

    // reopen database
    @Override
    protected void onResume() {
        databaseSource.open();
        super.onResume();
    }
}

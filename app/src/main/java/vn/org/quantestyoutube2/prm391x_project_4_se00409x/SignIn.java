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

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.CommandAndInterface.Command;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Database.DatabaseSource;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Entity.UserAccount;

public class SignIn extends AppCompatActivity {
    private static final String TAG = "SignIn";

    public static final int SIGN_UP_REQUEST_CODE = 1000;
    public static final String USERNAME_KEY = "username , key";

    private EditText txtUsername;
    private EditText txtPassword;
    private TextView btnToSignUp;
    private Button btnSignIn;

    public static DatabaseSource databaseSource;
    public static List<UserAccount> userAccountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUsername = findViewById(R.id.txtUsernameSignIn);
        txtPassword = findViewById(R.id.txtPasswordSignIn);
        btnToSignUp = findViewById(R.id.btnToSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);

        databaseSource = new DatabaseSource(this);
        if (databaseSource.countAccount() == 0){
            Log.d(TAG, "onCreate: no Account");
            userAccountList = new ArrayList<>();
        } else {
            userAccountList = databaseSource.getAllAccount();
            Log.d(TAG, "onCreate: "+ userAccountList);
        }

        btnToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSignUpActivity = new Intent(SignIn.this , SignUp.class);
                startActivityForResult(toSignUpActivity, SIGN_UP_REQUEST_CODE);
                overridePendingTransition(R.animator.push_left_in , R.animator.push_left_out);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtUsername.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignIn.this, "Username cannot be empty", Toast.LENGTH_LONG).show();
                } else if (!Command.isUsernameAlreadyExist(userAccountList , txtUsername.getText().toString().trim())){
                    Toast.makeText(SignIn.this,
                            "Username is not exist please create new Username ", Toast.LENGTH_LONG).show();
                } else {
                    if (txtPassword.getText().toString().trim().isEmpty()){
                        Toast.makeText(SignIn.this, "Passowrd cannot be empty", Toast.LENGTH_SHORT).show();
                    }else if (!Command.isPasswordCorrect(userAccountList , txtUsername.getText().toString().trim(),
                            txtPassword.getText().toString().trim())){
                        Toast.makeText(SignIn.this, "Password is incorrect", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SignIn.this, "Welcome", Toast.LENGTH_LONG).show();
                        Intent toVideoList = new Intent(SignIn.this , FirstVideoScreen.class);
                        toVideoList.putExtra(USERNAME_KEY , txtUsername.getText().toString().trim());

                        txtPassword.setText("");
                        txtUsername.setText("");
                        startActivity(toVideoList);
                    }
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseSource.open();
    }

}

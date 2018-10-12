package vn.org.quantestyoutube2.prm391x_project_4_se00409x;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.CommandAndInterface.Command;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Entity.UserAccount;

public class SignUp extends AppCompatActivity {
    private static final String TAG = "SignUp";

    private EditText txtUsername;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private TextView btnToSignIn;
    private Button btnSignUp;
    private Intent intentFromSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SignIn.databaseSource.open();

        intentFromSignIn = getIntent();
        txtUsername = findViewById(R.id.txtUsernameSignUp);
        txtPassword = findViewById(R.id.txtPasswordSignUp);
        txtConfirmPassword = findViewById(R.id.txtConfirmSignUp);
        btnToSignIn = findViewById(R.id.btnToSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
                overridePendingTransition(R.animator.push_left_in,R.animator.push_left_out);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtUsername.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUp.this, "Username is a must", Toast.LENGTH_LONG).show();
                } else if (Command.isUsernameAlreadyExist(SignIn.userAccountList , txtUsername.getText().toString().trim())){
                    Toast.makeText(SignUp.this, "Username already exist", Toast.LENGTH_LONG).show();
                } else {
                    if (txtPassword.getText().toString().trim().isEmpty() ){
                        Toast.makeText(SignUp.this, "Username must have a Passowrd", Toast.LENGTH_LONG).show();
                    }else if (!txtPassword.getText().toString().trim().equals(txtConfirmPassword.getText().toString().trim())){
                        Toast.makeText(SignUp.this, "Password does not match ", Toast.LENGTH_LONG).show();
                    } else {
                        UserAccount account = new UserAccount(txtUsername.getText().toString().trim() ,
                                txtPassword.getText().toString().trim());
                        SignIn.userAccountList.add(account);
                        SignIn.databaseSource.insert(account);
                        Toast.makeText(SignUp.this, "User created", Toast.LENGTH_LONG).show();
                        setResult(RESULT_OK);
                        finish();
                        overridePendingTransition(R.animator.push_left_in , R.animator.push_left_out);
                    }
                }
            }
        });

    }

    @Override
    protected void onPause() {
        SignIn.databaseSource.close();
        super.onPause();
    }

    @Override
    protected void onResume() {
        SignIn.databaseSource.open();
        super.onResume();
    }
}

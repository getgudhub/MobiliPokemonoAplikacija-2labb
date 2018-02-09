package com.byethost12.kitm.mobiliaplikacija;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_register);
                setTitle(R.string.register_label);

                final EditText etUsername = (EditText) findViewById(R.id.username);
                final EditText etPassword = (EditText) findViewById(R.id.password);
                final EditText etPasswordR = (EditText) findViewById(R.id.passwordR);
                final EditText etEmail = (EditText) findViewById(R.id.email);
                Button btnRegister = (Button) findViewById(R.id.btnRegister);
                final DatabaseSQLite dbSQLite = new DatabaseSQLite(this);

                btnRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (etUsername.getText().toString().equals("") || !Validation.isValidCredentials(etUsername.getText().toString())) {
                            etUsername.requestFocus();
                            etUsername.setError(getResources().getString(R.string.invalid_username));
                        }else if(etPassword.getText().toString().equals("") || !Validation.isValidCredentials(etPassword.getText().toString())) {
                            etPassword.requestFocus();
                            etPassword.setError(getResources().getString(R.string.invalid_password));
                        }else if(!etPasswordR.getText().toString().equals(etPassword.getText().toString())) {
                            etPasswordR.requestFocus();
                            etPasswordR.setError(getResources().getString(R.string.invalid_passwordR));
                        }else if(etEmail.getText().toString().equals("") || !Validation.isValidEmail(etEmail.getText().toString())) {
                            etEmail.requestFocus();
                            etEmail.setError(getResources().getString(R.string.invalid_email));
                        }else {
                            User user = new User("1", etUsername.getText().toString(),
                                    etPassword.getText().toString(), etEmail.getText().toString());
                            dbSQLite.addUser(user);

                            Toast.makeText(RegisterActivity.this,
                                    "Thank you for registering "+ etUsername.getText().toString(),
                                    Toast.LENGTH_LONG).show();

                            Intent toLoginActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(toLoginActivity);

                        }
                    }
                });
            }

}

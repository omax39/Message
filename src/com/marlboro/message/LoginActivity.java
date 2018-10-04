package com.marlboro.message;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseUser;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
public class LoginActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Parse.initialize(this, "SXPQAIpmThuHSnwWHUAkzsT8c5Q7pTZ19RKd4VLw", "pjTt7WrB1IKRaVxiB93tSq5pBcMWmGB7Ge7dexjw");
		final Intent intent=new Intent(LoginActivity.this, GenActivity.class);
		final EditText elo=(EditText)findViewById(R.id.editlo);
		final EditText epa=(EditText)findViewById(R.id.editpa);
		final MD5 md5= new MD5();
		Button butlo=(Button)findViewById(R.id.buttonlog);
		butlo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ParseUser.logInInBackground(elo.getText().toString().trim(), md5.toHexString(md5.computeMD5(epa.getText().toString().trim().getBytes())), new LogInCallback() {
					  public void done(ParseUser user, ParseException e) {
					    if (user != null) {
					      startActivity(intent);
					      } else {
					    	  AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
					    		builder.setTitle("Ошибка")
					    				.setMessage("Неправильные логин и пароль!")
					    				.setCancelable(false)
					    				.setNegativeButton("ОК",
					    						new DialogInterface.OnClickListener() {
					    							public void onClick(DialogInterface dialog, int id) {
					    								dialog.cancel();
					    							}
					    						});
					    		AlertDialog alert = builder.create();
					    		alert.show();
					    		Log.e("tag",e.getLocalizedMessage());
					      // Signup failed. Look at the ParseException to see what happened.
					    }
					  }
					});
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

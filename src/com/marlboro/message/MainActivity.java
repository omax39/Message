package com.marlboro.message;



import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

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

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	Parse.initialize(this, "SXPQAIpmThuHSnwWHUAkzsT8c5Q7pTZ19RKd4VLw", "pjTt7WrB1IKRaVxiB93tSq5pBcMWmGB7Ge7dexjw");
	final EditText el=(EditText)findViewById(R.id.editl);
	final EditText ep=(EditText)findViewById(R.id.editp);
	final MD5 md5= new MD5();
	Button butr=(Button)findViewById(R.id.buttonreg);
	Button butl=(Button)findViewById(R.id.buttonlogin);
	final Intent intent=new Intent(MainActivity.this, GenActivity.class);	
	final Intent intent1=new Intent(MainActivity.this, LoginActivity.class);
	butr.setOnClickListener(new View.OnClickListener() {
	
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String ell=el.getText().toString().trim();
			String epp=ep.getText().toString().trim();
			ParseUser user = new ParseUser();
			user.setUsername(ell);
			user.setPassword(md5.toHexString(md5.computeMD5(epp.getBytes())));
			user.signUpInBackground(new SignUpCallback() {
				  public void done(ParseException e) {
				    if (e == null) {
				      startActivity(intent);
				    } else {
				    	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
				    }
				  }
				});
		}
	});
		butl.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(intent1);
			}
		});
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
		  startActivity(intent);
		} else {
		  // show the signup or login screen
		}
	
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

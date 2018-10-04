package com.marlboro.message;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.ParseException;

import android.R.anim;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.internal.widget.AdapterViewCompat.OnItemClickListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("unused")
public class GenActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gen);
		Parse.initialize(this, "SXPQAIpmThuHSnwWHUAkzsT8c5Q7pTZ19RKd4VLw", "pjTt7WrB1IKRaVxiB93tSq5pBcMWmGB7Ge7dexjw");
		
		
		
		final ListView listviewuser=(ListView)findViewById(R.id.listViewuser);
		final UserAdapter adapter= new UserAdapter(this); 
		listviewuser.setAdapter(adapter);
		
		listviewuser.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent1=new Intent(GenActivity.this, MessActivity.class);
			    intent1.putExtra("username", adapter.getItem(position).getObjectId());

			    startActivity(intent1);
			}
		});
		
		
	}
	
	
                
        
        
	
		
	private static long back_pressed;
	public void finish() {
		if (back_pressed + 2000 > System.currentTimeMillis()) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		} else

			Toast.makeText(getBaseContext(), getResources().getString(R.string.backpress),
					Toast.LENGTH_SHORT).show();
		back_pressed = System.currentTimeMillis();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gen, menu);
		return true;
	}

	public void onExitloginMenuClick(MenuItem item){
		Intent intent=new Intent(GenActivity.this, MainActivity.class);
		ParseUser.logOut();
		startActivity(intent);
		
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

package com.marlboro.message;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.acl.LastOwnerException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MessActivity extends ActionBarActivity {
	List<ParseObject> listob;
	List<ParseObject> listob1;
    boolean status;

    Date date;
    Calendar c;
    String userId;
    String time;
	int ooo;
	String omar;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mess);
		Parse.initialize(this, "SXPQAIpmThuHSnwWHUAkzsT8c5Q7pTZ19RKd4VLw", "pjTt7WrB1IKRaVxiB93tSq5pBcMWmGB7Ge7dexjw");
		userId = getIntent().getStringExtra("username");
    	final ParseUser currentUser = ParseUser.getCurrentUser();
     	listob = new ArrayList<ParseObject>();
     	listob1 = new ArrayList<ParseObject>();
     	final AES aes= new AES();
		Timer myTimer = new Timer(); // Создаем таймер
     	c = Calendar.getInstance();
     	ParseObject oo=new ParseObject("Mess");
     	date = c.getTime();
     	omar = "omar";
		final ParseUser us = (ParseUser) ParseUser.createWithoutData("_User", userId);
		final EditText editText = (EditText) findViewById(R.id.editmess);
		final ListView listmess=(ListView)findViewById(R.id.listmess);
		final ProgressBar pr = (ProgressBar)findViewById(R.id.progressBar1);
     	
		class MessAdaptr extends ArrayAdapter<ParseObject> {

    		public MessAdaptr(Context context) {
    			super(context, R.layout.listviewmess, listob);
    			
    		}
    		
    		@SuppressLint("ResourceAsColor")
			public View getView(final int position, View convertView, ViewGroup parent) {
    			if (convertView == null) {
    				convertView = LayoutInflater.from(getContext())
    						.inflate(R.layout.listviewmess, null);
    			}
    			final TextView text1 = (TextView) convertView.findViewById(R.id.textmess1);
                final TextView text2 = (TextView) convertView.findViewById(R.id.textmess2);
                final TextView text11 = (TextView) convertView.findViewById(R.id.textdata1);
                final TextView text22 = (TextView) convertView.findViewById(R.id.textdata2);
                
                if (listob.get(position).getString("t").equals(userId)){
                	try {
                		String dada = aes.decrypt(listob.get(position).getString("Messag").toString().trim(), omar);
                		text1.setText(dada);
    		        } catch (Throwable t) {
    		            t.printStackTrace();
    		        }
    					
    					text11.setText(listob.get(position).getCreatedAt().getHours()+":"
    	            			+listob.get(position).getCreatedAt().getMinutes()+"  "
    							+listob.get(position).getCreatedAt().getDate()+"."+
    							(listob.get(position).getCreatedAt().getMonth()+1));
    					text2.setText("");
    					text22.setText("");
    			}
    			else { 
					
        					text1.setText("");	
        					text11.setText("");

        					text22.setText(listob.get(position).getCreatedAt().getHours()+":"
        	            			+listob.get(position).getCreatedAt().getMinutes()+"  "
        							+listob.get(position).getCreatedAt().getDate()+"."+
        							(listob.get(position).getCreatedAt().getMonth()+1));
        					try {
                        		String dada = aes.decrypt(listob.get(position).getString("Messag").toString().trim(), omar);
                        		text2.setText(dada);
            		        } catch (Throwable t) {
            		            t.printStackTrace();
            		        }
        					

    			
    			}
                listob.get(position).saveEventually();

    			return convertView;
    		}
    	}
		
		final MessAdaptr adapter = new MessAdaptr(this);
		ScheduledExecutorService service1 = Executors.newSingleThreadScheduledExecutor();
		service1.scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(isOnline()){ 			pr.setVisibility(View.VISIBLE);
				}else{
				    		pr.setVisibility(View.VISIBLE);
				    	}
		          					
			} 
			
		},1, 1, TimeUnit.SECONDS);
		
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Mess");
        	query.whereEqualTo("from", currentUser);
        	query.whereEqualTo("to", us);
        	query.findInBackground(new FindCallback<ParseObject>() {
				
				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					// TODO Auto-generated method stub
					
				}
			});
        	ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Mess");
        	query1.whereEqualTo("from", us);
        	query1.whereEqualTo("to", currentUser);
        	query1.findInBackground(new FindCallback<ParseObject>() {
				
				@Override
				public void done(List<ParseObject> objects1, ParseException e) {
					
					
				}
			});
        	List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        	queries.add(query);
        	queries.add(query1);
        	 
        	final ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
        	mainQuery.orderByAscending("createdAt");
        	mainQuery.findInBackground(new FindCallback<ParseObject>() {
        	  public void done(List<ParseObject> obj, ParseException e) {
        		  
        		  listob.addAll(obj);
        		  adapter.notifyDataSetChanged();
        		  listmess.post(new Runnable() {
      			    @Override
      			    public void run() {
      			        listmess.setSelection(listob.size()-1);
      			        View v = listmess.getChildAt(listob.size()-1);
      			        if (v != null) {
      			            v.requestFocus();
      			        }
      			        
      			    }
      			});
        	  }
        	});
        	
        
        	Log.i("TAG", ooo+"");

    			listmess.setAdapter(adapter);
    			
    			ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    			service.scheduleWithFixedDelay(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Log.i("taggg", "dgdfg");
						
				            	
								mainQuery.orderByAscending("createdAt");
					        	mainQuery.findInBackground(new FindCallback<ParseObject>() {
					        	  public void done(List<ParseObject> obj, ParseException e) {
					        		  adapter.clear();
					        		  listob.addAll(obj);
					        		  adapter.notifyDataSetChanged();
					        	  }
					        	});
				          					
					} 
    				
    			},5, 5, TimeUnit.SECONDS);
    			
    			listmess.setOnItemLongClickListener(new OnItemLongClickListener() {
    			
					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, final int position, long id) {
						if (haveNetworkConnection() != true) { pr.setVisibility(View.VISIBLE);					
						} else { 
							pr.setVisibility(View.INVISIBLE);
						}
						// TODO Auto-generated method stub
						AlertDialog.Builder builder = new AlertDialog.Builder(MessActivity.this);
						builder.setPositiveButton("Удалить",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										
										listob.get(position).deleteInBackground();
										
										mainQuery.findInBackground(new FindCallback<ParseObject>() {
								        	  public void done(List<ParseObject> obj, ParseException e) {
								        		  adapter.clear();
								        		  listob.addAll(obj);
								        		  adapter.notifyDataSetChanged();
								        	  }
								        	});

										
									}
								});
						
						// выводим диалоговое окно
						builder.show();

						return true;
					}
				});
    			
    			
    			
    			Button butmess=(Button)findViewById(R.id.butmess);
    			butmess.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (haveNetworkConnection() != true) { pr.setVisibility(View.VISIBLE);					
						} else { 
							pr.setVisibility(View.INVISIBLE);
						}
						String emess= editText.getText().toString().trim();
						ParseObject messs = new ParseObject("Mess");
						try {
							messs.put("Messag", aes.encrypt(emess, omar));
	    		        } catch (Throwable t) {
	    		            t.printStackTrace();
	    		        }
						
						messs.put("from", currentUser);
						messs.put("to", us);
						messs.put("fr", currentUser.getObjectId().toString());
						messs.put("t", userId);
						messs.saveInBackground(new SaveCallback() {
							
							@Override
							public void done(ParseException e) {
								if (e == null) {
									adapter.clear();
									mainQuery.orderByAscending("createdAt");
						        	mainQuery.findInBackground(new FindCallback<ParseObject>() {
						        	  public void done(List<ParseObject> obj, ParseException e) {
						        		  
						        		  listob.addAll(obj);
						        		  adapter.notifyDataSetChanged();
						        		  listmess.post(new Runnable() {
						        			    @Override
						        			    public void run() {
						        			        listmess.setSelection(listob.size()-1);
						        			        View v = listmess.getChildAt(listob.size()-1);
						        			        if (v != null) {
						        			            v.requestFocus();
						        			        }
						        			        
						        			    }
						        			});

						        	  }
						        	});
									editText.setText("");
						        } else {
						            // Отказ
						        	AlertDialog.Builder builder = new AlertDialog.Builder(MessActivity.this);
						    		builder.setTitle("Ошибка")
						    				.setMessage("Сообщение не отправленно!")
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
    			
    			
	}
	boolean haveNetworkConnection() {
	    boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;
	 
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo) {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	            if (ni.isConnected())
	                haveConnectedWifi = true;
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	            if (ni.isConnected())
	                haveConnectedMobile = true;
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	}
	 boolean isOnline() {
         
         ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo nInfo = cm.getActiveNetworkInfo();
         if (nInfo != null && nInfo.isConnected()) {
             status = true;
             return true;
         }
         else {
             status = false;
             return false;
         }
         
         
     }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mess, menu);
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

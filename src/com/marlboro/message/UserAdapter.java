package com.marlboro.message;
 
 
 
import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
 
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
 
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils.Permissions.User;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
 
 
 
@SuppressWarnings("unused")
public class UserAdapter extends ParseQueryAdapter<ParseUser> {
       
        
        public UserAdapter(Context context) {
                super(context, new ParseQueryAdapter.QueryFactory<ParseUser>() {
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        public ParseQuery<ParseUser> create() {
                        	ParseQuery<ParseUser> query = ParseUser.getQuery();
                        	query.whereNotEqualTo("username", currentUser.getUsername());
                        	    return query;
                        	    
                        	
                        }
                });
                
        }
 
        @Override
        public View getItemView(ParseUser user, View v, final ViewGroup parent) {
                if (v == null) {
                        v = View.inflate(getContext(), R.layout.listviewuser, null);
                }
                super.getItemView(user, v, parent);
                final TextView titleTextView = (TextView) v.findViewById(R.id.textuser1);
                titleTextView.setSelected(true);
                titleTextView.setText(user.getUsername());
                return v;
                
                }

		
		
		
		
                
                
}
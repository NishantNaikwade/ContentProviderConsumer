package com.example.contentproviderconsumer;

import android.support.v7.app.AppCompatActivity;
import android.app.DownloadManager.Query;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener{

	public static final String COL_ID = "_ID";
	public static final String COL_BOOK_NAME = "BOOK_NAME";
	public static final String COL_PUBLISHER = "PUBLISHER";
	public static final String COL_PUBLISHING_YEAR = "PUBLISHING_YEAR";
	public static final String AUTHOR = "AUTHOR";
	public static final String TAG = "MainActivity Consumer";
	static final String PROVIDER_NAME = "com.example.contentproviderapp.BooksContentProvider";
	static final String URL = "content://" + PROVIDER_NAME + "/BOOK";
	
	private Button getBookList;
	private ListView bookList;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i(TAG, "::Inside OnCreate::");
		getBookList = (Button)findViewById(R.id.fetchBookList);
		getBookList.setOnClickListener(this);
		bookList = (ListView)findViewById(R.id.bookList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
	
	private class FetchBooksAsyncTask extends AsyncTask<Void, Void, Cursor>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected Cursor doInBackground(Void... params) {
			Log.i(TAG, "::Inside doInBackground::");
			 Uri books = Uri.parse(URL);
			return getContentResolver().query(books, null, null, null, COL_BOOK_NAME);
		}
		
		@Override
		protected void onPostExecute(Cursor result) {
			super.onPostExecute(result);
			Log.i(TAG, "::Inside onPostExecute::");
			if (result != null && result.moveToFirst()) {
				do {
					Toast.makeText(
							MainActivity.this,
							result.getString(result.getColumnIndex(COL_ID))
									+ ", "
									+ result.getString(result
											.getColumnIndex(COL_BOOK_NAME))
									+ ", "
									+ result.getString(result
											.getColumnIndex(COL_PUBLISHER))
									+ ", "
									+ result.getString(result
											.getColumnIndex(COL_PUBLISHING_YEAR)),
							Toast.LENGTH_SHORT).show();
				} while (result.moveToNext());
			}else{
				Toast.makeText(MainActivity.this, "Something went Wrong!!!", Toast.LENGTH_SHORT).show();
			}
		}
		
	}

	@Override
	public void onClick(View v) {
		new FetchBooksAsyncTask().execute(); 
	}
}

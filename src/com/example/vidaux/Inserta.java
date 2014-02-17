package com.example.vidaux;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class Inserta extends Activity implements OnClickListener {
	
	EditText etnombre, etnumero, etcorreo;
	ImageButton btiGuarda, btiBusca;
	
	private DataBaseManager database;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.insertanumero);
		
		database = DataBaseManager.instance();

	
		etnombre = (EditText) findViewById(R.id.Editnombre);
		etnumero = (EditText) findViewById(R.id.editnumero);
		btiGuarda = (ImageButton) findViewById(R.id.btiGuardar);
		btiBusca = (ImageButton) findViewById(R.id.btiBuscar);
		//etcorreo = (EditText) findViewById(R.id.EditTextCorreo);

		
		btiGuarda.setOnClickListener(this);
		btiBusca.setOnClickListener(this);
	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
	
			
		case R.id.btiBuscar:
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
			startActivityForResult(intent, 1);
			break;

				
		case R.id.btiGuardar:
			saveState();
			
			finish();
			break;
		}

	}
	public void saveState() 
    {
 
		
		String name = etnombre.getText().toString();
		String num = etnumero.getText().toString();
		String correo = ""; //etcorreo.getText().toString();
		
		
		try {
			ContentValues valores = new ContentValues();
			valores.put("nombre", name);
			valores.put("numero", num);
			valores.put("correo", correo);
			
			database.insert("circulo", valores);

			Toast.makeText(Inserta.this,
					getResources().getString(R.string.inser_exitosa),
					Toast.LENGTH_LONG).show();

			finish();
		
	} catch (Exception hg) {
			hg.printStackTrace();

			Toast.makeText(Inserta.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
		}

		}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			Uri uri = data.getData();

			if (uri != null) {
				Cursor c = null;
				try {
					c = getContentResolver()
							.query(uri,
									new String[] {
											ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
											ContactsContract.CommonDataKinds.Phone.NUMBER,
											 },
									null, null, null);

					if (c != null && c.moveToFirst()) {
						String name = c.getString(0);
						String number = c.getString(1);
						showSelectedNumber(name, number);
					}
				} finally {
					if (c != null) {
						c.close();
					}
				}
			}
		}
	}

	public void showSelectedNumber(String name, String number) {
		etnombre.setText(name);
		etnumero.setText(number);
		
	}

	
}

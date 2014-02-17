package com.example.vidaux;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	Button b, btRojo, btAmarillo, btVerde, btSUME;
	Intent callIntent;
	String APLAFA_Num;
	String pass;
	String User_Num;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		obtener_numero();
	}

	// private DBAdapter mDbHelper;
	DataBaseManager database;

	private Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		obtener_numero();

		btRojo = (Button) findViewById(R.id.btRojo);
		btAmarillo = (Button) findViewById(R.id.btAmarillo);
		btVerde = (Button) findViewById(R.id.btVerde);
		btSUME = (Button) findViewById(R.id.btSUME);

		btAmarillo.setBackgroundResource(R.drawable.botama);
		btVerde.setBackgroundResource(R.drawable.botverd);
		btRojo.setBackgroundResource(R.drawable.botroj);

		btRojo.setOnClickListener(this);
		btAmarillo.setOnClickListener(this);
		btVerde.setOnClickListener(this);
		btSUME.setOnClickListener(this);

		User_Num = getPhoneNumber();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_circulo:
			Intent i = new Intent(this, Inserta.class);
			startActivity(i);
			return true;
		case R.id.menu_directorio:
			AlertDialog alertDialog;
			alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Lineas de Ayuda");
			alertDialog
					.setMessage("Telefonos de Ayuda\nPolicia Nacional 104\nBomberos  103\nMIDES 147\n"
							+ User_Num);
			alertDialog.show();
			return true;
		
		case R.id.menu_ver_circulo:
			Intent g = new Intent(this, VerCirculo.class);
			startActivity(g);
			return true;

		case R.id.menu_config:
			LayoutInflater li = LayoutInflater.from(this);
			View prompt = li.inflate(R.layout.dialogo_layout, null);
			
			final EditText nombreUsuario = (EditText) prompt.findViewById(R.id.nombre_usuario);
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder.setView(prompt);
			// Mostramos el mensaje del cuadro de dialogo
			alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
										String codigo = nombreUsuario.getText().toString();
									if(codigo.equals(pass)){
										Intent il = new Intent(MainActivity.this, NuevoNumero.class );
										startActivity(il);
									}else{
										nombreUsuario.setText("");
										Toast.makeText(MainActivity.this, "Contrse�a Incorrecta", Toast.LENGTH_LONG).show();
									}
									
							}
							})
					.setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// Cancelamos el cuadro de dialogo
									dialog.cancel();
								}
							});

			// Creamos un AlertDialog y lo mostramos
			AlertDialog alertDialog2 = alertDialogBuilder.create();
			alertDialog2.setTitle("Opciones de Administrador");
			alertDialog2.show();

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btVerde:
			alertar("Alerta Verde Indica conflictos ocasionales");
			enviarsms(APLAFA_Num, "VidAux\nAlerta Verde. Remitente: "
					+ User_Num);
			break;

		case R.id.btRojo:
			alertar("Alerta Roja Indica violencia bajo lesiones");
			enviarsms(APLAFA_Num, "VidAux\nAlerta Roja. Remitente: " + User_Num);
			break;

		case R.id.btAmarillo:
			alertar("Alerta Amarilla Indica acci�n bajo amenaza");
			enviarsms(APLAFA_Num, "VidAux\nAlerta Amarilla. Remitente: "
					+ User_Num);
			break;

		case R.id.btSUME:
			call();
			break;

		}

	}

	public void call() {
		try {
			callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:*312"));
			startActivity(callIntent);
		} catch (ActivityNotFoundException v) {
			Log.e("dialing-example", "Call failed", v);
		}
	}

	public void alertar(String msg) {
		try {

			database = DataBaseManager.instance();

			// mDbHelper = new DBAdapter(this);
			// mDbHelper.open();

			cursor = database.select("SELECT * FROM circulo");
			// mDbHelper.fetchAll();

			if (cursor != null && cursor.moveToFirst()) {
				do {
					String number = cursor.getString(2);
					enviarsms(number, msg);
				} while (cursor.moveToNext());

			}

		} catch (Exception hj) {
			Toast.makeText(this, hj.toString(), Toast.LENGTH_LONG).show();
		}

	}

	private void enviarsms(String numero, String mensaje) {
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(numero, null, mensaje, null, null);
		Toast.makeText(this, "Enviado a:" + numero, Toast.LENGTH_LONG).show();

	}

	private String getPhoneNumber() {
		TelephonyManager mTelephonyManager;
		mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyManager.getLine1Number();
	}

	public void obtener_numero() {
		DataBaseManager database = DataBaseManager.instance();
		Cursor datos = database.select("select * from aplafa");

		try {
			if (datos == null) {
				datos.close();
			} else {
				datos.moveToFirst();
			}

			APLAFA_Num = datos.getString(1);
			pass = datos.getString(2);
			datos.close();

		} catch (Exception hj) {
			Toast.makeText(this, hj.toString(), Toast.LENGTH_LONG).show();

		}

	}

}

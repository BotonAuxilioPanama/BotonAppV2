package com.aplafa.vidaux;



import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class NuevoNumero extends Activity {
	EditText TaNueva;
	
	TextView TaActual;
	ImageButton btiGuardar;

	String nueva;
	String tasa;
	DataBaseManager database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.nuevo_numero);

		TaNueva = (EditText) findViewById(R.id.etTaNueva);
		TaActual = (TextView) findViewById(R.id.tvTaActual);
		btiGuardar = (ImageButton) findViewById(R.id.btiGuardarN);

		database = DataBaseManager.instance();
		Cursor curTasa = database.select("select numero from aplafa");
		tasa = obtenerValor(curTasa);

		TaActual.setText(tasa.toString());
		
		btiGuardar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if (!TaNueva.getText().equals("")) {
						nueva = TaNueva.getText().toString();

						ContentValues valores = new ContentValues();
						valores.put("numero", nueva);

						database.update("aplafa", valores, "_id = 1");

						Toast.makeText(
								NuevoNumero.this,
								getResources()
										.getString(R.string.inser_exitosa),
								Toast.LENGTH_LONG).show();

						finish();
					} else {
						Toast.makeText(NuevoNumero.this,
								getResources().getString(R.string.intoduce_numero),
								Toast.LENGTH_LONG).show();
					}

				} catch (Exception e) {

					Toast.makeText(NuevoNumero.this,
							getResources().getString(R.string.error),
							Toast.LENGTH_LONG).show();
				}
			}
		});

	
	}

	public String obtenerValor(Cursor c) {
		String val;

		try {
			c.moveToFirst();
			val = c.getString(0);
			c.close();
			return val;

		} catch (Exception hj) {
			 Toast.makeText(this,getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
			return "";
		}

	}

}

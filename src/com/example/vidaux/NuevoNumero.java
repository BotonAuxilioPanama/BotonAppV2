package com.example.vidaux;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NuevoNumero extends  Activity{
	EditText TaNueva;
	Button Guardar;
	TextView TaActual;
		
	String nueva;
	String tasa;
	DataBaseManager database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.nuevo_numero);
		
		TaNueva = (EditText)findViewById(R.id.etTaNueva);
		Guardar=(Button) findViewById(R.id.btGuardar);
		TaActual = (TextView) findViewById(R.id.tvTaActual);
		
		
		database = DataBaseManager.instance();  
		Cursor curTasa = database.select("select numero from aplafa"); 
        tasa = obtenerValor(curTasa);
        
        TaActual.setText(tasa.toString());
        
		Guardar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		try{
			if (!TaNueva.getText().equals("")){
				nueva =TaNueva.getText().toString();
					
				
				ContentValues valores = new ContentValues();
				valores.put("numero", nueva);
				
				database.update("aplafa", valores, "_id = 1" );
				
				
				Toast.makeText(NuevoNumero.this, "Se ha guardado el nuevo numero", Toast.LENGTH_LONG).show();
				
				finish();
			}else{
				Toast.makeText(NuevoNumero.this, "Debes introducir un valor Numérico", Toast.LENGTH_LONG).show();
			}
			
		}catch(Exception e ){
		
			Toast.makeText(NuevoNumero.this, "Ha ocurrido un error", Toast.LENGTH_LONG).show();
		}
			}
		});
		
	}
	 public String obtenerValor(Cursor c){
			String val;
		   
		     try {
		    	 if (c == null){
		    	     	c.close();
		    	     }else
		    	     {
		    	     	c.moveToFirst();
		    	     }
		    
		    	 val = c.getString(0); 
		    	 c.close();
		    	 return val;
		    			 
		     }
		     catch(Exception hj){
		     	 //Toast.makeText(this,hj.toString(), Toast.LENGTH_LONG).show();
		     	 return "";
		     	}	
		     
		}

}

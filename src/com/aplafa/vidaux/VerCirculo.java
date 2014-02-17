package com.aplafa.vidaux;



import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class VerCirculo extends ListActivity {
	
	private Cursor cursor;
	DataBaseManager database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lista();
	}

	@Override
	// crea un menu que aparece cuando se presiona largo tiempo el item
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) { // long press
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.ver_circulo, menu);

	}

	public void lista() {
		try {

			database = DataBaseManager.instance();

			cursor = database.select("SELECT * FROM circulo");

			cursor.moveToFirst();

			String[] origen = new String[] { cursor.getColumnName(1),
					cursor.getColumnName(2) };
			int[] destino = new int[] { R.id.nombre_circulo_tv,
					R.id.numero_circulo_tv };

				SimpleCursorAdapter adapterQuery = new SimpleCursorAdapter(this,
					R.layout.ver_circulo_layout, cursor, origen, destino, 0);

			ListView listView = getListView();
			listView.setAdapter(adapterQuery);
			registerForContextMenu(listView);

		} catch (Exception hj) {
			Toast.makeText(this,
					getResources().getString(R.string.error),
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	// segun el elemento seleccionado en el menu de contexto se realiza una
	// accion
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.eliminar_menu:
			database.delete("circulo", "_id= " + String.valueOf(info.id));
			lista();

			return true;

		}
		return super.onContextItemSelected(item);
	}

}// fin de clase

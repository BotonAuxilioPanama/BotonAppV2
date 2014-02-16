package DB.src;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DataBaseHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "datosboton";
 	private static final int DATABASE_VERSION = 1;
 
	//Consulta para crear la base de datos
	private static final String DATABASE_CREATE = "create table circulo (_id integer primary key autoincrement, "+ "nombre text not null, numero text not null, correo text not null);";
 
	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
 
	// Este m�todo se llama al momento en el que se crea la BD
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
 
	// M�todo que se llama cada vez que se actualiza la BD
	// Sirve para manejar las versiones de la misma
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,int newVersion) {
		Log.w(DataBaseHelper.class.getName(),"Upgrading database from version " + oldVersion + " to "+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS todo");
		onCreate(database);
	}
	
	
	
}
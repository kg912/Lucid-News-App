package tk.lucidna.lucid;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by karan on 3/25/16.
 */
public class Database {
    private static final String FILE_NAME = "lucid.dat";
    private static final String FILE_MANIFEST = "manifesto.dat";
    public static void write(Context context, ArrayList<NewsObject> list) throws IOException {
        FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(list);
        os.close();
        fos.close();
    }

    public static ArrayList<NewsObject> read(Context context){
        FileInputStream fis = null;
        ArrayList<NewsObject> no = null;
        try {
            fis = context.openFileInput(FILE_NAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            no = (ArrayList<NewsObject>) is.readObject();
            is.close();
            fis.close();
            return no;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return no;
    }

    public static void manifestWrite(Context context, ArrayList<Integer> iList) throws IOException {
        FileOutputStream fos = context.openFileOutput(FILE_MANIFEST, Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(iList);
        os.close();
        fos.close();
    }

    public static ArrayList<Integer> manifestRead(Context context)  {
        FileInputStream fis = null;
        ArrayList<Integer> no = null;
        try {
            fis = context.openFileInput(FILE_MANIFEST);
            ObjectInputStream is = new ObjectInputStream(fis);
            no = (ArrayList<Integer>) is.readObject();
            is.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            return no;
        }
    }
}

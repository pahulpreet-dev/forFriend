package singh.jaskirat.smartpantry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CheckPantry extends AppCompatActivity {

    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_pantry);
        initComponents();

        final int[] pantryImages = {R.drawable.ginger
                , R.drawable.onions
                , R.drawable.garlic
                , R.drawable.potato
                , R.drawable.redchilli
                , R.drawable.greenchilly};
        String[] g = {};

        gridView.setAdapter(new PantryAdapter(CheckPantry.this, g, pantryImages));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(CheckPantry.this, "Hi " + pantryImages[i], Toast.LENGTH_SHORT).show();
            }
        });

    }

    private class PantryAdapter extends BaseAdapter {

        private Context mContext;
        private String[] g;
        private int[] pantryImages;

        public PantryAdapter(Context c,String[] g,int[] pantryImages ) {
            mContext = c;
            this.pantryImages = pantryImages;
            //this.web = web;
        }

        @Override
        public int getCount() {
            return pantryImages.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.layout_gridview_cp, null);

            TextView gTv = v.findViewById(R.id.textViewCP);
            ImageView pantryIv = v.findViewById(R.id.imageViewCP);

            gTv.setText("G");
            pantryIv.setImageResource(pantryImages[position]);

            return v;
        }
    }

    private void initComponents() {
        gridView = findViewById(R.id.gridViewCP);
    }
}

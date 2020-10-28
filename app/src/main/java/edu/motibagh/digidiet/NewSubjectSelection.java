package edu.motibagh.digidiet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class NewSubjectSelection extends AppCompatActivity {
    GridView gridView;

    String textArr[]={
            "Dwiwedi",
            "Kabir",
            "Mahadevi",
            "MahatamaGandhi",
            "Nirala",
            "Ramdhari",
            "Subhadra"

    };


    Integer imageArr[]={
            R.drawable.english,
            R.drawable.english,
            R.drawable.english,
            R.drawable.english,
            R.drawable.english,
            R.drawable.english,
            R.drawable.english

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_subject_selection);
        gridView = (GridView) findViewById(R.id.mygrid);

        NewSubjectSelectionGridView adapter=new NewSubjectSelectionGridView(this,textArr,imageArr);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String str = "";

                str = textArr[position];

                Toast.makeText(NewSubjectSelection.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
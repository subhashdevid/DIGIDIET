package edu.motibagh.digidiet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class SubjectSelection extends AppCompatActivity {
    GridView gridView;
    public Integer[] images = {R.drawable.eng, R.drawable.et, R.drawable.comingsoon,
            R.drawable.comingsoon, R.drawable.comingsoon, R.drawable.comingsoon};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_selection);
        ImageView educationTechnology = (ImageView) findViewById(R.id.imageView1);
        ImageView english = (ImageView) findViewById(R.id.imageView2);

        educationTechnology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityChangeIntent = new Intent(SubjectSelection.this, SubjectResources.class);
                activityChangeIntent.putExtra("subject", "et");
                SubjectSelection.this.startActivity(activityChangeIntent);
            }
        });
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityChangeIntent = new Intent(SubjectSelection.this, SubjectResources.class);
                activityChangeIntent.putExtra("subject", "english");
                SubjectSelection.this.startActivity(activityChangeIntent);
            }
        });
    }

}
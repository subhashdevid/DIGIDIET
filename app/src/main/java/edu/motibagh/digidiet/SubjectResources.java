package edu.motibagh.digidiet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SubjectResources extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_resources);

        Button ebook=(Button)findViewById(R.id.button1);
        Button notes=(Button)findViewById(R.id.button2);
        Button practicepaper=(Button)findViewById(R.id.button3);
        Button assignment=(Button)findViewById(R.id.button4);

        final String subject = getIntent().getStringExtra("subject");

        ebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityChangeIntent = new Intent(SubjectResources.this, MainActivity.class);
                activityChangeIntent.putExtra("type", "book");
                activityChangeIntent.putExtra("subject", subject);
                SubjectResources.this.startActivity(activityChangeIntent);
            }
        });
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityChangeIntent = new Intent(SubjectResources.this, MainActivity.class);
                activityChangeIntent.putExtra("type", "notes");
                activityChangeIntent.putExtra("subject", subject);
                SubjectResources.this.startActivity(activityChangeIntent);
                Intent intent=new Intent(getApplicationContext(),PdfViewer.class);
                intent.putExtra("class7", "class7");
                startActivity(intent);
            }
        });


    }
}
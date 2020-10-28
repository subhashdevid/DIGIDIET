package edu.motibagh.digidiet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.motibagh.digidiet.Database.MyDatabase;

public class SelectionOption extends AppCompatActivity {
    GridView gridView;

    ArrayList<Firebasecrud> mTargetData = new ArrayList<>();
    Firebasecrud firebasecrud= new Firebasecrud();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        fetchDatafromFirebase();
    }


    private void initView() {
        gridView = (GridView) findViewById(R.id.gridLayout);
        setContentView(R.layout.activity_selection_option);
        ImageView academics = (ImageView) findViewById(R.id.imageView1);
        ImageView announcement = (ImageView) findViewById(R.id.imageView2);

        academics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityChangeIntent = new Intent(SelectionOption.this, SubjectSelection.class);
                activityChangeIntent.putExtra("subject", "et");
                SelectionOption.this.startActivity(activityChangeIntent);
            }
        });

    }

    private void fetchDatafromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                mTargetData.clear();
                for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot : snapshot1.getChildren()) {
                        Firebasecrud target = snapshot.getValue(Firebasecrud.class);
                        mTargetData.add(target);
                    }
                    Log.e("TAG", "Data received:" + mTargetData.size());
                }

                if (mTargetData.size() > 0) {
                    setDataInDB();
                }

                }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "fetchData onCancelled", databaseError.toException());
                //
            }
        };
        myRef.addListenerForSingleValueEvent(postListener);
    }


    private void setDataInDB() {
//        new MyDatabase(this).clearTableData();
        for (Firebasecrud record : mTargetData) {
            Firebasecrud bookRecord = new Firebasecrud(record.fileId, record.subject, record.url, record.isStoredinDB, record.filelocalPath, record.author, record.semester, record.type, record.title);
            new MyDatabase(this).addRecord(bookRecord);
        }
    }


}
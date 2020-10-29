package edu.motibagh.digidiet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import edu.motibagh.digidiet.Database.MyDatabase;
import edu.motibagh.digidiet.Interfaces.DownloadManagerCallback;
import edu.motibagh.digidiet.Interfaces.MyAdapterInterfaceListner;

public class MainActivity extends AppCompatActivity implements MyAdapterInterfaceListner,DownloadManagerCallback {
    ArrayList<Firebasecrud> listData = new ArrayList<>();
    Firebasecrud firebasecrud= new Firebasecrud();
    private RecyclerView mRecyclerView;
    private TargetDataAdapter mAdapter;
    DownloadManagerCallback downloadManagerCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadManagerCallback = this;

        updateUI();
        initView();
    }
    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        setUpRecyclerAdapter();


    }


    private void  setUpRecyclerAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycleview_divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new TargetDataAdapter(listData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Firebasecrud model = listData.get( position );
                        String stored = model.getIsStoredinDB();
                        if (stored.equals("y")){

                            String filename = model.fileId +".pdf";

                            String fileePath = Environment.getExternalStorageDirectory().toString() + "/"+filename;

                            viewPDF(filename,model);
                            File internalFile = new File(MainActivity.this.getExternalFilesDir("").getPath());
//
//                            Intent activityChangeIntent = new Intent(MainActivity.this, PdfViewer.class);
//                            activityChangeIntent.putExtra("filepath", fileePath);
//                            MainActivity.this.startActivity(activityChangeIntent);
                        }else{
                            new MainActivityDataManager(MainActivity.this).setUpDownloadManager(MainActivity.this, model, position, downloadManagerCallback);
                        }
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }

    public void viewPDF( String filename, Firebasecrud model)
    {
        File internalFile = new File(MainActivity.this.getExternalFilesDir("").getPath());

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + filename);  // -> filename = maven.pdf
        Uri path = Uri.parse(model.filelocalPath);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try{
            startActivity(pdfIntent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(MainActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewPdf(Uri file) {
        Intent intent;
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(file, "application/pdf");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // No application to view, ask to download one
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Application Found");
            builder.setMessage("Download one from Android Market?");
            builder.setPositiveButton("Yes, Please",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                            marketIntent
                                    .setData(Uri
                                            .parse("market://details?id=com.adobe.reader"));
                            startActivity(marketIntent);
                        }
                    });
            builder.setNegativeButton("No, Thanks", null);
            builder.create().show();
        }
    }


    //fetch DB data from anywhere in APP

    public ArrayList<Firebasecrud> fetchAllDataFromDB(){
        ArrayList<Firebasecrud> list = (ArrayList<Firebasecrud>) new MyDatabase( this ).getAllRecord();
        return list;
    }


    public void updateUI() {
        final String subject = getIntent().getStringExtra("subject");
        final String type = getIntent().getStringExtra("type");
        listData.clear();
        ArrayList<Firebasecrud> dblist = new ArrayList<>();
        dblist = fetchAllDataFromDB();

        for (Firebasecrud target : dblist) {
            if(target.getSubject().equals(subject) && target.getType().equals(type)) {
                listData.add(target);
            }
        }
    }

    public void updateDataInList(){

        final String subject = getIntent().getStringExtra("subject");
        final String type = getIntent().getStringExtra("type");
        listData.clear();
        ArrayList<Firebasecrud> dblist = new ArrayList<>();
        dblist = new MainActivityDataManager(MainActivity.this).fetchAllDataFromDB();

        for (Firebasecrud target : dblist) {
            if(target.getSubject().equals(subject) && target.getType().equals(type)) {
                listData.add(target);
            }
        }


        setUpRecyclerAdapter();
    }


    @Override
    public void downloadCallback(int cellposition) {
        updateDataInList();
    }

    @Override
    public void imageButtonViewOnClick(View v, int position) {
    }


//    private void fetchData() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference();
//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                // Get Post object and use the values to update the UI
//                mTargetData.clear();
//                for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
//                    for (DataSnapshot snapshot : snapshot1.getChildren()) {
//                        Firebasecrud target = snapshot.getValue(Firebasecrud.class);
////                        if(target.getSubject().equals(subject)&& target.getType().equals(type)) {
//                            mTargetData.add(target);
////                        }
//                    }
//                    mAdapter.notifyDataSetChanged();
//                    Log.e("TAG", "Data received:" + mTargetData.size());
//                }
////                firebasecrud.setSubject(subject);
////                firebasecrud.setType(type);
////                mTargetData.add(firebasecrud);
//
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w("TAG", "fetchData onCancelled", databaseError.toException());
//                //
//            }
//        };
//        myRef.addListenerForSingleValueEvent(postListener);
//    }
}
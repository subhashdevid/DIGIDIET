package edu.motibagh.digidiet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

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
                            //  model.filepath // local path
//                            PDF URI to display
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
        listData.clear();
        listData = new MainActivityDataManager(MainActivity.this).fetchAllDataFromDB();
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
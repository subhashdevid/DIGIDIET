package edu.motibagh.digidiet;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;

import edu.motibagh.digidiet.Database.MyDatabase;
import edu.motibagh.digidiet.DownloadManager.DefaultRetryPolicy;
import edu.motibagh.digidiet.DownloadManager.DownloadManager;
import edu.motibagh.digidiet.DownloadManager.DownloadRequest;
import edu.motibagh.digidiet.DownloadManager.DownloadStatusListenerV1;
import edu.motibagh.digidiet.DownloadManager.RetryPolicy;
import edu.motibagh.digidiet.DownloadManager.ThinDownloadManager;
import edu.motibagh.digidiet.Interfaces.DownloadManagerCallback;

public class MainActivityDataManager {

    int downloadId3;
    private ThinDownloadManager downloadManager;
    private static final int DOWNLOAD_THREAD_POOL_SIZE = 4;
    MyDownloadDownloadStatusListenerV1
            myDownloadStatusListener ;
    public DownloadManagerCallback downloadManagerCallback;
    public Firebasecrud record;


    ArrayList<String> fileArray;

    DownloadRequest downloadRequest3;
    Context context;

    String fileTitle,fileUrl;
    int fileId;
    int positionOfCell;

ProgressDialog dialog;


public MainActivityDataManager(Context context){
    this.context = context;
    myDownloadStatusListener = new MyDownloadDownloadStatusListenerV1(context);

}

// adding  download manager Third Party  -------

    public void setUpDownloadManager(Context context, Firebasecrud model, int position, DownloadManagerCallback downloadManagerCallback){

        dialog = new ProgressDialog( context );
        dialog.setMessage( "Please wait..." );
        dialog.setCancelable( false );
        dialog.show();
        RetryPolicy retryPolicy = new DefaultRetryPolicy();
        File filesDir = context.getExternalFilesDir("");
        //interface setup
        positionOfCell = position;
        this.downloadManagerCallback = downloadManagerCallback;
        record = model;

        downloadManager = new ThinDownloadManager(DOWNLOAD_THREAD_POOL_SIZE);
        String FILE3 =  "http://androhub.com/demo/demo.mp3"; //model.getUrl();
        Uri downloadUri = Uri.parse(FILE3);
//        Uri destinationUri = Uri.parse(filesDir+"/test_song.mp3");
        String fileName = String.valueOf(model.getFileId());
        Uri destinationUri = Uri.parse(filesDir+"/"+fileName +".rs");
        downloadRequest3 = new DownloadRequest(downloadUri)
                .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                .setDownloadContext("Download3")
                .setStatusListener(myDownloadStatusListener);

        if (downloadManager.query(downloadId3) == DownloadManager.STATUS_NOT_FOUND) {
            downloadId3 = downloadManager.add(downloadRequest3);
        }

    }
//---- ---- ---- ---- ---- ---- -------- ---- -------- ---- -------- ---- -------- ---- -------- ---- -------- ---- -------- ---- --------

    //fetch DB data from anywhere in APP
    public ArrayList<Firebasecrud> fetchAllDataFromDB(){
        ArrayList<Firebasecrud> list = (ArrayList<Firebasecrud>) new MyDatabase( context ).getAllRecord();
        return list;
}
    private void showInternalFilesDir() {
        File internalFile = new File(context.getExternalFilesDir("").getPath());
        File files[] = internalFile.listFiles();
        StringBuilder contentText = new StringBuilder();
        if( files.length == 0 ) {
            //contentText = new StringBuilder("No Files Found");
            Toast.makeText(context, "No Files Found...", Toast.LENGTH_SHORT).show();

        }



        fileArray = new ArrayList<String>();
        fileArray.clear();
        for (File file : files) {
            contentText.append(file.getName()).append(" ").append(file.length()).append(" \n\n ");
            fileArray.add( file.getAbsolutePath());
        }

        if(fileArray.size() > 0){
            //local file path
        String filepath = fileArray.get( fileArray.size()-1);
         new MyDatabase( context ).updateRecord(filepath,record.fileId,"y");
         downloadManagerCallback.downloadCallback( positionOfCell);
         Toast.makeText(context, "Record Saved Successfully...", Toast.LENGTH_LONG).show();

}

        dialog.dismiss();

    }



    class MyDownloadDownloadStatusListenerV1 implements DownloadStatusListenerV1 {

    Context listnerContext;

    public MyDownloadDownloadStatusListenerV1(Context context){
        listnerContext = context;
    }
        @Override
        public void onDownloadComplete(DownloadRequest request) {

            final int id = request.getDownloadId();
            if (id == downloadId3) {
                Toast.makeText(listnerContext, "Completed", Toast.LENGTH_SHORT ).show();
                showInternalFilesDir();
            }
        }



        @Override
        public void onDownloadFailed(DownloadRequest request, int errorCode, String errorMessage) {
            final int id = request.getDownloadId();
            if (id == downloadId3) {
                // mProgress3Txt.setText("Download3 id: "+id+" Failed: ErrorCode "+errorCode+", "+errorMessage);
                //mProgress3.setProgress(0);

                if (id == downloadId3) {
                   Toast.makeText( context, "ErrorCode" + errorCode, Toast.LENGTH_SHORT ).show();

                dialog.dismiss();
                }
            }
        }

        @Override
        public void onProgress(DownloadRequest request, long totalBytes, long downloadedBytes, int progress) {
            int id = request.getDownloadId();

            System.out.println("######## onProgress ###### "+id+" : "+totalBytes+" : "+downloadedBytes+" : "+progress);
            if (id == downloadId3) {
                // mProgress3Txt.setText("Download3 id: "+id+", "+progress+"%"+"  "+getBytesDownloaded(progress,totalBytes));
                //  mProgress3.setProgress(progress);

                // Toast.makeText( MainActivity.this, "Progress"+getBytesDownloaded(progress,totalBytes), Toast.LENGTH_SHORT ).show();
            }
        }
    }

    private String getBytesDownloaded(int progress, long totalBytes) {
        //Greater than 1 MB
        long bytesCompleted = (progress * totalBytes)/100;
        if (totalBytes >= 1000000) {
            return (""+(String.format("%.1f", (float)bytesCompleted/1000000))+ "/"+ ( String.format("%.1f", (float)totalBytes/1000000)) + "MB");
        } if (totalBytes >= 1000) {
            return (""+(String.format("%.1f", (float)bytesCompleted/1000))+ "/"+ ( String.format("%.1f", (float)totalBytes/1000)) + "Kb");

        } else {
            return ( ""+bytesCompleted+"/"+totalBytes );
        }
    }



}

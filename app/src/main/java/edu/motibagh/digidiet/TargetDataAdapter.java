package edu.motibagh.digidiet;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TargetDataAdapter extends RecyclerView.Adapter<TargetDataAdapter.TargetViewHolder>{
    ArrayList<Firebasecrud> targetsArrayList;
    public TargetDataAdapter(){}
    public TargetDataAdapter(ArrayList<Firebasecrud> mTargetData) {
        targetsArrayList = mTargetData;
    }
    @NonNull
    @Override
    public TargetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_player_list_cell,viewGroup,false);
        return new TargetViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull TargetViewHolder viewHolder, int i) {

        Firebasecrud model =  targetsArrayList.get(i);
            viewHolder.title.setText(model.title);
            viewHolder.subject.setText("Subject :" + targetsArrayList.get(i).subject);
            viewHolder.author.setText("Author :" + targetsArrayList.get(i).author);
            viewHolder.semester.setText("Semester :" + targetsArrayList.get(i).semester + " sem");
            viewHolder.type.setText("Type :" + targetsArrayList.get(i).type);

            String stored = model.getIsStoredinDB();

        if (stored.equals("y")){
            viewHolder.imageButton.setVisibility(View.GONE );
        }
        else {
            viewHolder.imageButton.setVisibility(View.VISIBLE );
            viewHolder.imageButton.setImageResource(R.drawable.downloadicon );

        }

        }
//    }
    @Override
    public int getItemCount() {
        if(targetsArrayList == null)
            return 0;
        return targetsArrayList.size();
    }
    public static class TargetViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView author;
        protected TextView type;
        protected TextView semester;
        protected TextView subject;
        protected ImageView playerImageview;
        protected ImageButton imageButton;


        public TargetViewHolder(@NonNull View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.title);
            subject= (TextView) itemView.findViewById(R.id.subject);
            semester= (TextView) itemView.findViewById(R.id.semester);
            author= (TextView) itemView.findViewById(R.id.author);
            type= (TextView) itemView.findViewById(R.id.type);
            imageButton= (ImageButton) itemView.findViewById(R.id.imageDownloadBtn);
            playerImageview= (ImageView) itemView.findViewById(R.id.imageView);

        }
    }
}
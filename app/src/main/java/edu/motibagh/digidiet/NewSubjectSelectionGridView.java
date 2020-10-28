package edu.motibagh.digidiet;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class NewSubjectSelectionGridView extends ArrayAdapter<String> {

    String text[];
    Integer image[];
    Activity activity;


    public NewSubjectSelectionGridView(Activity activity,String text[],Integer image[]) {
        super(activity, R.layout.new_subject_selection_grid, text);

        this.text = text;
        this.image = image;
        this.activity = activity;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = activity.getLayoutInflater();//to access any layout in activity

        View v = inflater.inflate(R.layout.new_subject_selection_grid, null);//view is the parent class of all component

        ImageView imageView = (ImageView) v.findViewById(R.id.grid_image);
        TextView textView = (TextView) v.findViewById(R.id.mytext);
        imageView.setImageResource(image[position]);
        textView.setText(text[position]);



        return  v;
    }
}

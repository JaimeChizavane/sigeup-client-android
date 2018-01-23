package mz.ac.sigeup.sigeup_navigationview.helper;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


import mz.ac.sigeup.sigeup_navigationview.PortalFragment;
import mz.ac.sigeup.sigeup_navigationview.R;

/**
 * Created by jaimechizavane on 1/11/18.
 */

public class SwipeListAdapter extends BaseAdapter {


    private Activity activity;

    //Just to replace with a Fragment instead Activity
    private PortalFragment fragment;

    private LayoutInflater inflater;

    private List<Grade> gradeList;


    private String[] bgColors;



    /*public SwipeListAdapter(Activity activity, List<Grade> gradeList) {
        this.activity = activity;
        this.gradeList = gradeList;
        bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.grade_serial_bg);
    } */

    public SwipeListAdapter(PortalFragment fragment, List<Grade> gradeList) {
        this.fragment = fragment;
        this.gradeList = gradeList;
        bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.grade_serial_bg);
    }


    @Override
    public int getCount() {
        return gradeList.size();
    }


    @Override
    public Object getItem(int location) {
        return gradeList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.portal_rows, null);

        TextView serial = (TextView) convertView.findViewById(R.id.nome_cadeira);
        TextView title = (TextView) convertView.findViewById(R.id.nota);

        serial.setText(String.valueOf(gradeList.get(position).teste1));
        title.setText(String.valueOf(gradeList.get(position).teste2));

        String color = bgColors[position % bgColors.length];
        serial.setBackgroundColor(Color.parseColor(color));

        return convertView;
    }
}

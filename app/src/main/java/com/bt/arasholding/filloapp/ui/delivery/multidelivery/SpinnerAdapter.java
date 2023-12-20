package com.bt.arasholding.filloapp.ui.delivery.multidelivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.network.model.AtfUndeliverableReasonModel;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter {
    private AppCompatActivity activity;
    private List<AtfUndeliverableReasonModel> data;
    private Context context;
    AtfUndeliverableReasonModel model = null;
    LayoutInflater inflater;


    public SpinnerAdapter(@NonNull Context context, int resource, List<AtfUndeliverableReasonModel> objects) {
        super(context,resource,objects);
        this.data = objects;
        this.context = context;
//        model = (SpinnerModel) data.get(0);
        inflater = (LayoutInflater.from(context));
//        Log.d("text",model.getText());
//        Log.d("value",model.getValue()+"");
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position){
        AtfUndeliverableReasonModel obj = data.get(position);
        return obj.getSebepTanimi();
    }

    public AtfUndeliverableReasonModel getItemObj(int position){
        AtfUndeliverableReasonModel obj = data.get(position);
        return obj;
    }
//    @Override
//    public Object getItem(int position) {
//        SpinnerModel obj = data.get(position);
//        return obj.getText();
//    }

    @Override
    public long getItemId(int position) {
        AtfUndeliverableReasonModel obj = data.get(position);
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.spinner_row, parent, false);

        model = (AtfUndeliverableReasonModel) data.get(position);

        TextView label = (TextView) convertView.findViewById(R.id.codes);
        //TextView sub = (TextView) convertView.findViewById(R.id.sub);

        if (position == 0) {
            label.setText(model.getSebepTanimi());
            //sub.setText("");
        } else {
            // Set values for spinner each row
            label.setText(model.getSebepTanimi());
            //sub.setText(teslimDurumu.getIslemKodu()+"");
        }

        return convertView;
    }
}

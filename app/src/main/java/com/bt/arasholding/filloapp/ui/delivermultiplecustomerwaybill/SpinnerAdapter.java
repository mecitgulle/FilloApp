package com.bt.arasholding.filloapp.ui.delivermultiplecustomerwaybill;

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
import com.bt.arasholding.filloapp.data.network.model.CustomerResponseModel;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter {
    private AppCompatActivity activity;
    private List<CustomerResponseModel> data;
    private Context context;
    CustomerResponseModel model = null;
    LayoutInflater inflater;


    public SpinnerAdapter(@NonNull Context context, int resource, List<CustomerResponseModel> objects) {
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
        CustomerResponseModel obj = data.get(position);
        return obj.getDESCTRIPTION();
    }

    public CustomerResponseModel getItemObj(int position){
        CustomerResponseModel obj = data.get(position);
        return obj;
    }
//    @Override
//    public Object getItem(int position) {
//        SpinnerModel obj = data.get(position);
//        return obj.getText();
//    }

    @Override
    public long getItemId(int position) {
        CustomerResponseModel obj = data.get(position);
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

        model = (CustomerResponseModel) data.get(position);

        TextView label = (TextView) convertView.findViewById(R.id.codes);
        //TextView sub = (TextView) convertView.findViewById(R.id.sub);

        if (position == 0) {
            label.setText(model.getDESCTRIPTION());
            //sub.setText("");
        } else {
            // Set values for spinner each row
            label.setText(model.getDESCTRIPTION());
            //sub.setText(teslimDurumu.getIslemKodu()+"");
        }

        return convertView;
    }
}
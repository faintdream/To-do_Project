package com.akashdubey.todolist;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by FLAdmin on 1/25/2018.
 */

public class ModifyTask extends AppCompatDialogFragment  {
    EditText mTitle;
    EditText mDesc;
    DatePicker mdatePicker;
    String tmpTitle;
    String tmpDesc;
    MainActivity m;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener=(ModifyTaskListner) context;
    }

    ModifyTaskListner listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);

        final AlertDialog.Builder mBuilder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.addtask_view,null);
        mBuilder.setView(view);
        mTitle=(EditText)view.findViewById(R.id.titleTV);
        mDesc=(EditText)view.findViewById(R.id.descTV);
        mdatePicker=(DatePicker)view.findViewById(R.id.dateDP);
        Button cancel=(Button)view.findViewById(R.id.cancelBtn);
        Button save=(Button)view.findViewById(R.id.saveBtn);
        m= new MainActivity();
        mBuilder.create();

        mTitle.setText(m.mainTitle);
        mDesc.setText(m.mainDesc);
        Toast.makeText(getContext(), ""+m.mainTitle, Toast.LENGTH_SHORT).show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer year=mdatePicker.getYear();
                Integer month=mdatePicker.getMonth()+1;
                Integer day=mdatePicker.getDayOfMonth();
                String tmpDate=year.toString()+"-"+month.toString()+"-"+day.toString();
                listener.ModifyTask(mTitle.getText().toString(),
                        mDesc.getText().toString(),tmpDate);
                dismiss();
            }
        });

        return mBuilder.show();



    }

//    @Override
//    public void SendCursorInfo(Cursor c1) {
//        tmpTitle=c1.getString(1);
//        tmpDesc=c1.getString(2);
////        //datpicker is left


//    }


public interface ModifyTaskListner{
        void ModifyTask(String title, String desc, String date);

}

public interface ModifyTaskCursorListener{
    void SendCursorInfo(Cursor c1);
}
}


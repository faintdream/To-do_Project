package com.akashdubey.todolist;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

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
        listener=(ModifyTaskListener) context;
    }

    ModifyTaskListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
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
        String [] split=m.mainDate.split("-");
        int year=Integer.valueOf(split[0]);
        int month=(Integer.valueOf(split[1])-1);
        int day=Integer.valueOf(split[2]);
        mdatePicker.updateDate(year,month,day);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTitle.length()>4 && mDesc.length()>4) {
                    Integer year = mdatePicker.getYear();
                    Integer month = mdatePicker.getMonth() + 1;
                    Integer day = mdatePicker.getDayOfMonth();
                    String tmpDate = year.toString() + "-" + month.toString() + "-" + day.toString();
                    listener.ModifyTask(m.mainId, mTitle.getText().toString(),
                            mDesc.getText().toString(), tmpDate);
                    dismiss();
                }else{
                    Toast.makeText(getContext(), "Either Title or Desc. less than 5 characters, please fix", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return mBuilder.show();
    }

    public interface ModifyTaskListener {
        void ModifyTask(String id,String title, String desc, String date);

    }

    public interface ModifyTaskCursorListener{
        void SendCursorInfo(Cursor c1);
    }
}


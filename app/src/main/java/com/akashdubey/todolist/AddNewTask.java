package com.akashdubey.todolist;

/*
This class handles actions needed for adding new task


 */
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


public class AddNewTask extends AppCompatDialogFragment{
    AddNewTaskListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener=(AddNewTaskListener)context;
    }


    //initializing the dialog controls and handling various onClick events.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder mBuilder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.addtask_view,null);
        mBuilder.setView(view);
        final EditText mTitle=(EditText)view.findViewById(R.id.titleTV);
        final EditText mDesc=(EditText)view.findViewById(R.id.descTV);
        final DatePicker mdatePicker=(DatePicker)view.findViewById(R.id.dateDP);
        Button cancel=(Button)view.findViewById(R.id.cancelBtn);
        Button save=(Button)view.findViewById(R.id.saveBtn);
        mBuilder.create();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTitle.length()>4 && mDesc.length()>4){
                    Integer year=mdatePicker.getYear();
                    Integer month=mdatePicker.getMonth()+1;
                    Integer day=mdatePicker.getDayOfMonth();
                    String tmpDate=year.toString()+"-"+month.toString()+"-"+day.toString();
                    listener.InsertData(mTitle.getText().toString(),
                     mDesc.getText().toString(),tmpDate);
                    dismiss();
                }else{
                    Toast.makeText(getContext(), "Either Title or Desc. less than 5 characters, please fix", Toast.LENGTH_SHORT).show();

                }
            }
        });

        return mBuilder.show();
    }


    //listener for handling new task add
    public interface AddNewTaskListener{
        void InsertData(String title, String desc, String date);
    }
}

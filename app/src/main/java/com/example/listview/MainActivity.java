package com.example.listview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.listview.model.languageinfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ListView todolist;
    ArrayList<languageinfo> arrayList ;
    String name,desc,releasedate;Integer id,tmpPosition = -9;
    Button button;
    EditText txtlangname,txtdesc,txtdate,txtID;
    AlertDialog alertDialog;
    Button btnsave,btncancel,btndelete;
    languageinfo tmplanguageinfo;
    languageadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todolist = (ListView) findViewById(R.id.lstToDo);
        button  = (Button)findViewById(R.id.fab);
        GenerateList();
        /*
        String[] namelist = {"Java","C#","Android","PHP"};
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_item,namelist);

        todolist = (ListView)findViewById(R.id.lstToDo);
        todolist.setAdapter(adapter);

         */
         adapter = new languageadapter(this,arrayList);
        todolist.setAdapter(adapter);


        todolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tmplanguageinfo = arrayList.get(i);
                tmpPosition = i;
                //showPopupDialog(tmpLanguageInfo);
                showPopUpDialog(arrayList.get(i));
                Toast.makeText(MainActivity.this,arrayList.get(i).getName(),Toast.LENGTH_LONG).show();
            }
        });

        button.setOnClickListener(this);

    }

    private void showPopUpDialog(languageinfo info)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v  = inflater.inflate(R.layout.language_view,null);
        builder.setView(v);
        builder.setCancelable(false);
        alertDialog = builder.create();

        alertDialog.show();

        txtlangname = (EditText) v.findViewById(R.id.txtLangName);
        txtdesc = (EditText) v.findViewById(R.id.txtDescription);
        txtdate = (EditText) v.findViewById(R.id.txtReleaseDate);
        txtID = (EditText) v.findViewById(R.id.txtid);

         btnsave = (Button) v.findViewById(R.id.btnsave);
         btncancel = (Button) v.findViewById(R.id.btncancel);
         btndelete = (Button) v.findViewById(R.id.btndelete);


        if(info != null)
        {
            txtID.setText(String.valueOf(info.getID()));
            txtlangname.setText(info.getName());
            txtdesc.setText(info.getDescription());
            txtdate.setText(new SimpleDateFormat("yyyy-MM-dd").format(info.getReleasedate()));
        }

        btnsave.setOnClickListener(this);
        btncancel.setOnClickListener(this);
        btndelete.setOnClickListener(this);


    }

    private void GenerateList()
    {
        arrayList=  new ArrayList<>();

        for(int i=0;i<15;i++)
        {
            languageinfo info = new languageinfo();
            info.setID(i+1);
            info.setName("Language " + (i+1));
            Date d = new Date();
            info.setReleasedate(d);
            info.setDescription(info.getName() + " " + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            arrayList.add(info);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnsave:
                String name = txtlangname.getText().toString();
                String desc = txtdesc.getText().toString();
                String date = txtdate.getText().toString();

                int id = Integer.parseInt(txtID.getText().toString());

                if(arrayList == null || arrayList.size() == 0)
                {
                    id = 1;
                }
                else if(id<0)
                {
                    id = id + arrayList.get(arrayList.size()-1).getID();
                }


                languageinfo info = new languageinfo();
                if(tmpPosition >= 0 )
                {
                    info =arrayList.get(tmpPosition);
                }
                info.setID(id);
                info.setName(name);
                info.setDescription(desc);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    info.setReleasedate(df.parse(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(tmpPosition < 0 )
                {
                    arrayList.add(info);
                }
                adapter.notifyDataSetChanged();
                alertDialog.hide();
                tmpPosition = -9;
                ;break;

            case R.id.btncancel:
                tmpPosition = -9;
                alertDialog.cancel();
                ;break;

            case R.id.fab:
                tmpPosition = -9;
                showPopUpDialog(null);
                break;

            case R.id.btndelete:
                int deletedid = Integer.parseInt(txtID.getText().toString());
                if(deletedid<0)
                {
                    alertDialog.hide();
                    tmpPosition = -9;
                    return;
                }
                else
                {
                    if(tmpPosition >= 0 )
                    {
                        arrayList.remove(arrayList.get(tmpPosition));
                        adapter.notifyDataSetChanged();
                    }
                }
                alertDialog.hide();
                tmpPosition = -9;
                break;
        }

    }
}

package org.o7planning.hello;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editN, editP;
    Button btnAddData;
    Button btnViewAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editN = (EditText)findViewById(R.id.editTextSurname);
        editP = (EditText)findViewById(R.id.editTextName);
        btnAddData = (Button)findViewById(R.id.buttonAdd);
        btnViewAll = (Button)findViewById(R.id.buttonView);
        AddData();
        viewAll();
    }

    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editN.getText().toString(),
                                editP.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void viewAll(){
        btnViewAll.setOnClickListener(
                new View.OnClickListener(){

                    @SuppressLint("ResourceType")
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0){
                            //show MSG
                            showMessage("Error","Nothing Found");
                            return; }


                        StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()){
                        buffer.append("Id : "+ getResources().getString(0)+"\n");
                        buffer.append("Nom : "+ getResources().getString(1)+"\n");
                        buffer.append("Prenom : "+ getResources().getString(2)+"\n\n");
                    }


                    //show all data
                    showMessage("Data",buffer.toString());
                }
                }
        );

    }
    public void showMessage(String title,String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
      getMenuInflater().inflate(R.menu.menu_main, menu);
      return true;
    }
    }

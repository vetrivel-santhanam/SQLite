package com.brilliantbirdtech.sqlite;
import android.os.Bundle;
import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

// Issue fix:
// extends AppCompatActivity is must. don't use other activities.
// Build Configuration: app

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editAadhar,editName,editAddress,editMobile;
    Button btnAddData;
    Button btnViewAll;
    Button btnDelete;
    Button btnViewUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editAadhar = (EditText)findViewById(R.id.AadharText);
        editName = (EditText)findViewById(R.id.NameText);
        editAddress = (EditText)findViewById(R.id.AddressText);
        editMobile = (EditText)findViewById(R.id.MobileText);
        btnAddData = (Button)findViewById(R.id.AddList);
        btnViewAll = (Button)findViewById(R.id.ViewList);
        btnViewUpdate= (Button)findViewById(R.id.UpdateList);
        btnDelete= (Button)findViewById(R.id.DeleteList);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }

    @Override
    protected void onDestroy() {

        deleteDatabase(myDb.getDatabaseName());
        super.onDestroy();
    }

    private void ResetData() {
        editAadhar.setText("");
        editName.setText("");
        editAddress.setText("");
        editMobile.setText("");
    }
    private void Toast(String toast ) {
        Toast.makeText(MainActivity.this,toast,Toast.LENGTH_LONG).show();
    }
    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editAadhar.getText().toString());
                        if(deletedRows > 0) {
                            Toast("Data Deleted");
                            ResetData();
                        } else {
                            Toast("Data not Deleted");
                        }

                    }
                }
        );
    }
    public void UpdateData() {
        btnViewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editAadhar.getText().toString(),
                                editName.getText().toString(),
                                editAddress.getText().toString(),editMobile.getText().toString());
                        if(isUpdate == true) {
                            Toast("Data Updated");
                            ResetData();
                        }
                        else {
                            Toast("Data not Updated");
                        }
                    }
                }
        );
    }
    public  void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean isInserted = myDb.insertData(editAadhar.getText().toString(),
                                editName.getText().toString(),
                                editAddress.getText().toString(),
                                editMobile.getText().toString() );
                        if(isInserted == true) {
                            Toast("Data Inserted");
                            ResetData();
                        }
                        else
                            Toast("Data not Inserted");
                    }
                }
        );
    }

    public void viewAll() {
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Aadhar :"+ res.getString(0)+"\n");
                            buffer.append("Name :"+ res.getString(1)+"\n");
                            buffer.append("Address :"+ res.getString(2)+"\n");
                            buffer.append("Mobile :"+ res.getString(3)+"\n\n");
                        }

                        // Show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}

package com.finalproject.ncovitrackerproject;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.finalproject.ncovitrackerproject.Adapters.HealthDeclarationAdapter;
import com.finalproject.ncovitrackerproject.Models.HealthDeclaration;
import com.finalproject.ncovitrackerproject.Shared_Preferences.DataLocalManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Declaration extends AppCompatActivity {
    TextView txtThanNhiet;
    EditText edThanNhiet ,edDiChuyen;
    CheckBox ckSot, ckHo, ckKhoTho, ckDauNguoi, ckSucKhoeTot;
    Button btnGui;
    ListView lvLichSu;
    ArrayList<HealthDeclaration> ArrayListkhaiBaoHangNgay = new ArrayList<>();
    HealthDeclarationAdapter khaiBaoAdapter;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    DatabaseReference mData;
    String dataString= DataLocalManager.getUserID();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_declaration);
        getSupportActionBar().setTitle("Khai báo y tế");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getView();
        setCheckbox();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mData = FirebaseDatabase.getInstance().getReference("");
        user = FirebaseAuth.getInstance().getCurrentUser();

        khaiBaoAdapter = new HealthDeclarationAdapter(this, R.layout.history, ArrayListkhaiBaoHangNgay);
        lvLichSu.setAdapter(khaiBaoAdapter);
        mData.child("Khai báo").child(dataString).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded( DataSnapshot snapshot,String previousChildName) {
                HealthDeclaration healthDeclaration = snapshot.getValue(HealthDeclaration.class);
                ArrayListkhaiBaoHangNgay.add(healthDeclaration);
                khaiBaoAdapter.notifyDataSetChanged();
            }


            @Override
            public void onChildChanged( DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onCancelled( DatabaseError error) {

            }


        });
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String strDate = dateFormat.format(new Date());
                String strTime = timeFormat.format(new Date());
                String suckhoe = "";
                if (ckSot.isChecked()) {
                    if (suckhoe.length() != 0) {
                        suckhoe += " - " + ckSot.getText();
                    } else {
                        suckhoe += ckSot.getText();
                    }
                }
                if (ckHo.isChecked()) {
                    if (suckhoe.length() != 0) {
                        suckhoe += " - " + ckHo.getText();
                    } else {
                        suckhoe += ckHo.getText();
                    }
                }
                if (ckDauNguoi.isChecked()) {
                    if (suckhoe.length() != 0) {
                        suckhoe += " - " + ckDauNguoi.getText();
                    } else {
                        suckhoe += ckDauNguoi.getText();
                    }
                }
                if (ckKhoTho.isChecked()) {
                    if (suckhoe.length() != 0) {
                        suckhoe += " - " + ckKhoTho.getText();
                    } else {
                        suckhoe += ckKhoTho.getText();
                    }
                }

                if (!ckSucKhoeTot.isChecked() && !ckSot.isChecked() && !ckKhoTho.isChecked() &&
                        !ckDauNguoi.isChecked() && !ckHo.isChecked() && !ckKhoTho.isChecked() ||
                        edThanNhiet.getText().toString().length() == 0) {
                    Toast.makeText(Declaration.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    double thannhiet = Double.parseDouble(edThanNhiet.getText().toString().trim());
                    if (36 >= thannhiet || thannhiet >= 41) {
                        Toast.makeText(Declaration.this, "Thân nhiệt từ 36 đến 40 độ C", Toast.LENGTH_SHORT).show();
                    } else {
                        if (ckSucKhoeTot.isChecked()) {

                            suckhoe += ckSucKhoeTot.getText();
                            HealthDeclaration khaiBaoHangNgay1 = new HealthDeclaration(strDate, "Sức khỏe: \n" +
                                    "\tThân Nhiệt: " + edThanNhiet.getText() + "\n\tTình trạng: " + suckhoe, strTime, R.drawable.ic_baseline_favorite,"\n\t Đã đến :"+""+edDiChuyen.getText().toString());

                            mData.child("Khai báo").child(dataString).push().setValue(khaiBaoHangNgay1);
                            mData.child("Khai báo").child(dataString).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                                    khaiBaoAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onChildChanged( DataSnapshot snapshot,  String previousChildName) {

                                }

                                @Override
                                public void onChildRemoved( DataSnapshot snapshot) {

                                }

                                @Override
                                public void onChildMoved( DataSnapshot snapshot,  String previousChildName) {

                                }

                                @Override
                                public void onCancelled( DatabaseError error) {

                                }
                            });
                        } else {
                            HealthDeclaration khaiBaoHangNgay1 = new HealthDeclaration(strDate, "Sức khỏe: \n" +
                                    "\tThân Nhiệt: " + edThanNhiet.getText() + "\n\tTình trạng: " + suckhoe, strTime, R.drawable.ic_baseline_event_busy,"\n\t Đã đến :"+""+edDiChuyen.getText().toString());

                            mData.child("Khai báo").child(dataString).push().setValue(khaiBaoHangNgay1);
                            mData.child("Khai báo").child(dataString).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                                    khaiBaoAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onChildChanged( DataSnapshot snapshot,  String previousChildName) {

                                }

                                @Override
                                public void onChildRemoved( DataSnapshot snapshot) {

                                }

                                @Override
                                public void onChildMoved( DataSnapshot snapshot,  String previousChildName) {

                                }

                                @Override
                                public void onCancelled( DatabaseError error) {

                                }
                            });
                            Canhbao();
                        }
                        edThanNhiet.setText("");
                        edDiChuyen.setText("");
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    private void getView() {
        txtThanNhiet = (TextView) findViewById(R.id.txtThanNhiet);
        edThanNhiet = (EditText) findViewById(R.id.edThanNhiet);
        edDiChuyen= (EditText)findViewById(R.id.dichuyen);
        ckSot = (CheckBox) findViewById(R.id.ckSot);
        ckHo = (CheckBox) findViewById(R.id.ckHo);
        ckKhoTho = (CheckBox) findViewById(R.id.ckKhoTho);
        ckDauNguoi = (CheckBox) findViewById(R.id.ckDaunguoi);
        ckSucKhoeTot = (CheckBox) findViewById(R.id.ckSuckhoeTot);
        btnGui = (Button) findViewById(R.id.btnGui);
        lvLichSu = (ListView) findViewById(R.id.ListKhaiBao);
    }

    private void setCheckbox() {
        ckSucKhoeTot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ckSot.setChecked(false);
                    ckHo.setChecked(false);
                    ckKhoTho.setChecked(false);
                    ckDauNguoi.setChecked(false);
                }
            }
        });
        ckHo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ckSucKhoeTot.setChecked(false);
                }
            }
        });
        ckDauNguoi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ckSucKhoeTot.setChecked(false);
                }
            }
        });
        ckSot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ckSucKhoeTot.setChecked(false);
                }
            }
        });
        ckKhoTho.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ckSucKhoeTot.setChecked(false);
                }
            }
        });
    }

    private void Canhbao() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        ////Thiết lập tiêu đề
        b.setTitle("Cảnh báo");
        b.setMessage("Sức khỏe của bạn hiện đang không tốt, vui lòng đi kiểm tra sức khỏe");
        //// Nút Ok
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog al = b.create();
        ///Hiển thị
        al.show();
    }
}
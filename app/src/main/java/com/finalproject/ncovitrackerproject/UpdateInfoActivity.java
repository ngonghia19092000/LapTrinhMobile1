package com.finalproject.ncovitrackerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.finalproject.ncovitrackerproject.Constants;
import com.finalproject.ncovitrackerproject.Models.Member;
import com.finalproject.ncovitrackerproject.Shared_Preferences.DataLocalManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateInfoActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference DataTT;
    FirebaseAuth mAuth;

    EditText edHoten, edCMND, edNgaySinh, edDiachi;
    Button btnCapNhat;
    RadioButton rdNam, rdNu;
    String email;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        getView();
        getSupportActionBar().setTitle("Cập nhật thông tin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        DataTT = firebaseDatabase.getReference("Thông tin cá nhân");
        user = FirebaseAuth.getInstance().getCurrentUser();
email = DataLocalManager.getEmailLogin();
        DataTT.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    if (ds.child("email").getValue().equals(email)){
                        edHoten.setText(ds.child("fullName").getValue(String.class));
                        edCMND.setText(ds.child("cmnd").getValue(String.class));
                        edCMND.setFocusable(false);
                        edDiachi.setText(ds.child("address").getValue(String.class));
                        edNgaySinh.setText(ds.child("birtthday").getValue(String.class));
                        String gioitinh = ds.child("gender").getValue(String.class);
                        if (gioitinh.equals("Nam")) {
                            rdNam.setChecked(true);
                        }
                        if (gioitinh.equals("Nữ")) {
                            rdNu.setChecked(true);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = edHoten.getText().toString();
                String diachi = edDiachi.getText().toString();
                String ngaysinh = edNgaySinh.getText().toString();
                String gioitinh = null;
                if(rdNam.isChecked()){
                    gioitinh = "Nam";
                }
                if (rdNu.isChecked()){
                    gioitinh = "Nữ";
                }
                final String cmnd = edCMND.getText().toString();
                String email1 = email;
                final Member member = new Member(hoten, gioitinh, diachi, ngaysinh, cmnd, email1);
                DataTT.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()){
                            if (ds.child("email").getValue().equals(email)){
                                DataTT.child(cmnd).setValue(member);
                            };
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                //Tạo đối tượng
                AlertDialog.Builder b = new AlertDialog.Builder(UpdateInfoActivity.this);
//Thiết lập tiêu đề
                b.setMessage("Cập nhật thông tin thành công.");
// Nút Ok
                b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAndRemoveTask();
                    }
                });
                AlertDialog al = b.create();
//Hiển thị
                al.show();

            }
        });
    }

    private void getView() {
        edHoten = (EditText) findViewById(R.id.edtHoTenCN);
        edCMND = (EditText) findViewById(R.id.edtCMNDCN);
        edNgaySinh = (EditText) findViewById(R.id.edtNgaySinhCN);
        edDiachi = (EditText) findViewById(R.id.edtDiaChiCN);
        btnCapNhat = (Button) findViewById(R.id.btnCapNhat);
        rdNam = (RadioButton) findViewById(R.id.rbNamCN);
        rdNu = (RadioButton) findViewById(R.id.rbNuCN);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}
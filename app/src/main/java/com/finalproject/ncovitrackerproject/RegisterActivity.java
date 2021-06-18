package com.finalproject.ncovitrackerproject;

import android.content.Intent;
import android.os.Bundle;

import com.finalproject.ncovitrackerproject.Models.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.finalproject.ncovitrackerproject.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    EditText edHoten, edCMND, edTaikhoan, edMatkhau1, edMakhau2, edNgaysinh, edDiachi;
    RadioButton rdNam, rdNu;
    RadioGroup rdGioiTinh;
    Button btnDangKy;
    ArrayList<Member> thanhViens;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference DataTT;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getView();
        mAuth = FirebaseAuth.getInstance();
        DataTT = FirebaseDatabase.getInstance().getReference();
        edNgaysinh.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String hoten = edHoten.getText().toString();
                final String cmnd = edCMND.getText().toString();
                final String diachi = edDiachi.getText().toString();
                final String ngaysinh = edNgaysinh.getText().toString();
                final String email = edTaikhoan.getText().toString();
                String matkhau1 = edMatkhau1.getText().toString().trim();
                String matkhau2 = edMakhau2.getText().toString().trim();
                final String gioitinh;
                if (rdNam.isChecked()) {
                    gioitinh = "Nam";
                } else {
                    gioitinh = "Nữ";
                }

                if (hoten.length() == 0 || cmnd.length() == 0 || diachi.length() == 0 ||
                        ngaysinh.length() == 0 || email.length() == 0 || gioitinh.length() == 0 ||
                        matkhau1.length() == 0 || matkhau2.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (matkhau1.equals(matkhau2)) {
                        Member member = new Member(hoten, gioitinh, diachi, ngaysinh, cmnd, email);
                        DataTT.child("Thông tin cá nhân").child(cmnd).setValue(member);
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        DangKy();
                        Intent intentCapNhat = new Intent(RegisterActivity.this, LoginActivity.class);
                        intentCapNhat.putExtra("taikhoandk", email);
                        intentCapNhat.putExtra("matkhaudk", matkhau1);
                        startActivity(intentCapNhat);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Mật khẩu không giống nhau", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void getView(){
        edHoten = (EditText) findViewById(R.id.edtHoTen);
        edCMND = (EditText) findViewById(R.id.edtCMND);
        edTaikhoan = (EditText) findViewById(R.id.edtTaiKhoan);
        edMatkhau1 = (EditText) findViewById(R.id.edtMatKhau1);
        edMakhau2 = (EditText) findViewById(R.id.edtMatKhau2) ;
        edNgaysinh = (EditText) findViewById(R.id.editTextDate);
        edDiachi = (EditText) findViewById(R.id.edtDiaChi);
        rdNam = (RadioButton) findViewById(R.id.rbNam);
        rdNu = (RadioButton) findViewById(R.id.rbNu);
        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        rdGioiTinh = (RadioGroup) findViewById(R.id.rbGioiTinh);
    }

    private void DangKy(){
        String email = edTaikhoan.getText().toString();
        String password = edMatkhau1.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            Intent intentDangNhap = new Intent(RegisterActivity.this, LoginActivity.class);
                            intentDangNhap.putExtra("taikhoandk",edTaikhoan.getText().toString());
                            intentDangNhap.putExtra("matkhaudk",edMatkhau1.getText().toString());
                            startActivity(intentDangNhap);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}
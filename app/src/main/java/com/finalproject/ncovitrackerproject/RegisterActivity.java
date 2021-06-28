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

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

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
        getSupportActionBar().setTitle("Đăng ký tài khoản");
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

                if (hoten.isEmpty()  || cmnd.isEmpty() || diachi.isEmpty() ||
                        ngaysinh.isEmpty()|| email.isEmpty() || gioitinh.isEmpty() ||
                        matkhau1.isEmpty() || matkhau2.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else if(hoten.matches("^[0-9]")) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng kiểm tra lại Họ Tên", Toast.LENGTH_SHORT).show();
                }
                else if(!(cmnd.length()==9||cmnd.length()==12)) {
                    Toast.makeText(RegisterActivity.this, "Số CMND/CCCD nhập sai", Toast.LENGTH_SHORT).show();
                }
                else if(((Pattern.compile(Constants.EMAIL_PATTERN)).matcher(email)).matches()==false) {
                    Toast.makeText(RegisterActivity.this, "Email không đúng! Kiểm tra lại email của bạn", Toast.LENGTH_SHORT).show();
                } else if (!(matkhau1.length()>=6&&matkhau1.length()<=18 && matkhau2.length()>=6 && matkhau2.length()<=18)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu bao gồm ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                } else if (isValidFormat("dd/MM/yyyy",ngaysinh)==false) {
                    Toast.makeText(RegisterActivity.this, "Ngày sinh không hợp lệ ", Toast.LENGTH_SHORT).show();
                }
                else {
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
                            Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
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

    public static boolean isValidFormat(String format, String value) {

        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
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
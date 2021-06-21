package com.finalproject.ncovitrackerproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.finalproject.ncovitrackerproject.Shared_Preferences.DataLocalManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    TextView txtDangky;
    EditText edTaiKhoandn, edMatKhaudn;
    Button btnDangNhap;

    private FirebaseAuth mAuth;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getView();
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();

        String taikhoandn = intent.getStringExtra("taikhoandk");
        String matkhaudn = intent.getStringExtra("matkhaudk");
        String email = DataLocalManager.getEmailLogin();
        //Kiểm tra email
        boolean check = ((Pattern.compile(EMAIL_PATTERN)).matcher(email)).matches();
        if (check == true) {
            edTaiKhoandn.setText(email);
        } else {
            edTaiKhoandn.setText(taikhoandn);
        }

        edMatKhaudn.setText(matkhaudn);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edMatKhaudn.getText().toString().length() == 0 || edMatKhaudn.getText().toString().length() == 0) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Login();
                }
            }
        });
//      //  Tạo đối tượng
//        AlertDialog.Builder b = new AlertDialog.Builder(this);
////Thiết lập tiêu đề
//        b.setTitle("Xác nhận");
//        b.setMessage("Bạn có đồng ý thoát chương trình không?");
//// Nút Ok
//        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                finish();
//            }
//        });
////Nút Cancel
//        b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.cancel();
//            }
//        });
////Tạo dialog
//        AlertDialog al = b.create();
////Hiển thị
//        al.show();
    }

    private void getView() {
        txtDangky = (TextView) findViewById(R.id.txtDangKyinDN);
        edTaiKhoandn = (EditText) findViewById(R.id.edtTaiKhoanDN);
        edMatKhaudn = (EditText) findViewById(R.id.edtMatKhauDN);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
    }
//Chuyển đến trang đăng ký
    public void Register(View view) {
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }

//Method đăng nhập
    private void Login() {
        String email = edTaiKhoandn.getText().toString().trim();
        String password = edMatKhaudn.getText().toString().trim();
        DataLocalManager.setUserID(mAuth.getUid());
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                    Intent intenthome = new Intent(LoginActivity.this, MainActivity.class);
                  //Lưu email để đăng nhập cho lần kế tiếp
                    DataLocalManager.setEmailLogin(email.trim());
                  //App đã từng được chạy trên thiết bị
                    DataLocalManager.setFirstInstallApp(true);
                    startActivity(intenthome);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
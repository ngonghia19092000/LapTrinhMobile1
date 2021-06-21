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
import android.widget.TextView;
import android.widget.Toast;

import com.finalproject.ncovitrackerproject.Shared_Preferences.DataLocalManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {
    TextView tvEmail;
    EditText edMKhientai, edMK1, edMK2;
    FirebaseUser user;
    private FirebaseAuth mAuth;
    Button btnDoiMK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().setTitle("Thay đổi mật khẩu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getView();
        tvEmail.setText(DataLocalManager.getEmailLogin());
        user = FirebaseAuth.getInstance().getCurrentUser();
        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doiMK();

            }
        });
    }

    private void getView() {

        edMKhientai = (EditText) findViewById(R.id.edMatkhaudoi);
        edMK1 = (EditText) findViewById(R.id.edMatkhaudoi1);
        edMK2 = (EditText) findViewById(R.id.edMatkhaudoi2);
        btnDoiMK = (Button) findViewById(R.id.btnDoiMK);
        tvEmail = (TextView) findViewById(R.id.textviewEmailChangePw);
    }

    private void doiMK() {
        String matkhaucu = edMKhientai.getText().toString();
        final String matkhaumoi1 = edMK1.getText().toString();
        final String matkhaumoi2 = edMK2.getText().toString();

        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email, matkhaucu);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (matkhaumoi1.equals(matkhaumoi2)) {
                        user.updatePassword(matkhaumoi1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (!task.isSuccessful()) {
                                    Toast.makeText(ChangePasswordActivity.this, "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                } else {
                                    //Tạo đối tượng
                                    AlertDialog.Builder b = new AlertDialog.Builder(ChangePasswordActivity.this);
//Thiết lập tiêu đề
                                    b.setMessage("Thay đổi mật khẩu thành công.");
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
                            }
                        });
                    } else
                        Toast.makeText(ChangePasswordActivity.this, "Mật khẩu mới không trùng khớp,kiểm tra lại mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Bạn đã nhập sai mật khẩu cũ", Toast.LENGTH_SHORT).show();
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
}
package singh.jaskirat.smartpantry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    EditText emailEt, passwordEt, confirmPassEt;
    Button signupBtn;
    TextView loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initializeComponents();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
            signupBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validate()) {
                        Map<String, Object> user = new HashMap<>();
                        user.put("email", emailEt.getText().toString());
                        user.put("password", passwordEt.getText().toString());
                        db.collection("users")
                                .add(user)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d("TAAAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                        Toast.makeText(SignupActivity.this, "Please login to continue", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("TAAAG", "Error adding document", e);
                                    }
                                });
                    }
                }
            });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

    }

    private boolean validate() {
        String email = emailEt.getText().toString();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (!pat.matcher(email).matches()) {
            emailEt.setError("Enter valid email");
            return false;
        } else if (passwordEt.getText().toString().length() <= 5) {
            passwordEt.setError("Password must be of length 5");
            return false;
        } else if (confirmPassEt.getText().toString().length() < 1) {
            confirmPassEt.setError("Confirm password");
            return false;
        } else if (!confirmPassEt.getText().toString().equals(passwordEt.getText().toString())) {
            confirmPassEt.setError("Password do not match");
            return false;
        }
        return true;
    }
    private void initializeComponents() {
        emailEt = findViewById(R.id.emailBtnSU);
        passwordEt = findViewById(R.id.passwordBtnSU);
        confirmPassEt = findViewById(R.id.confirmPassBtnSU);
        loginBtn = findViewById(R.id.loginBtnSU);
        signupBtn = findViewById(R.id.signupBtnSU);
    }
}

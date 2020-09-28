package singh.jaskirat.smartpantry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class SignupActivity extends AppCompatActivity {

    EditText emailEt, passwordEt;
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
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }

    private void initializeComponents() {
        emailEt = findViewById(R.id.emailBtnSU);
        passwordEt = findViewById(R.id.passwordBtnSU);
        loginBtn = findViewById(R.id.loginBtnSU);
        signupBtn = findViewById(R.id.signupBtnSU);
    }
}

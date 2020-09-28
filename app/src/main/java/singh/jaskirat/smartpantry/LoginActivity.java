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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    EditText emailEt, passwordEt;
    Button loginBtn;
    TextView signupBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeComponents();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users")
                        .whereEqualTo("email", emailEt.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("TAAAG", document.getId() + " => " + document.get("email"));
                                        if(document.get("password").toString()
                                                .equals(passwordEt.getText().toString())) {
                                            Toast.makeText(LoginActivity.this, "LOGGED IN", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    Log.w("TAAAG", "Error getting documents.", task.getException());
                                }
                            }
                        });
            }
        });


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    private void initializeComponents() {
        emailEt = findViewById(R.id.emailBtnLA);
        passwordEt = findViewById(R.id.passwordBtnLA);
        loginBtn = findViewById(R.id.loginBtnLA);
        signupBtn = findViewById(R.id.signupBtnLA);
    }
}

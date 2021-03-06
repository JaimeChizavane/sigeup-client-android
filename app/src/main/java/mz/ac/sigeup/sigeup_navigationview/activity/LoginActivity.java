package mz.ac.sigeup.sigeup_navigationview.activity;

/**
 * Created by jaimechizavane on 9/19/17.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


import com.android.volley.toolbox.Volley;
import mz.ac.sigeup.sigeup_navigationview.R;
import mz.ac.sigeup.sigeup_navigationview.app.AppConfig;
import mz.ac.sigeup.sigeup_navigationview.app.AppController;
import mz.ac.sigeup.sigeup_navigationview.helper.SQLiteHandler;
import mz.ac.sigeup.sigeup_navigationview.helper.SessionManager;
import mz.ac.sigeup.sigeup_navigationview.MainActivity;

public class LoginActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button btnLogin;

    private EditText inputUserID;
    private EditText inputPassword;

    private TextView studantName;
    private TextView studantEmail;

    private Button btnClear;

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    //private RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        inputUserID = (EditText) findViewById(R.id.editText_userid);
        inputPassword = (EditText) findViewById(R.id.passwordLogin);
        btnLogin = (Button) findViewById(R.id.buttonAuthenticar);
        btnClear = (Button) findViewById(R.id.buttonClear);
        studantName = (TextView) findViewById(R.id.studentNametextView);
        studantEmail = (TextView) findViewById(R.id.studentEmailtextView);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String username = inputUserID.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!username.isEmpty() && !password.isEmpty()) {

                    //Just testing if the user parsing is correct
                    Toast.makeText(getApplicationContext(), username, Toast.LENGTH_LONG);


                    // login user
                    checkLogin(username /*, password*/);

                    //studantName.setText(username);
                    //studantEmail.setText(email);

                    Intent intent = new Intent(LoginActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    /*// Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Por favor insira as credenciais, certifique que elas estao correctas!", Toast.LENGTH_LONG)
                            .show();
*/
                    Snackbar.make(view, "Por favor insira as credenciais, certifique que elas estao correctas!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

        });

        // Clear button Click Event
        btnClear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                inputUserID.setText("");
                inputPassword.setText("");

            }

        });
    }

    private void checkLogin(final String username /* , final String password*/) {


        // Tag used to cancel the request
        String tag_string_req = "req_login";
        final String result = "estudante";

        pDialog.setMessage("Entrando ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.GET,
                AppConfig.URL_USUARIO+username, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + toString());
                hideDialog();

                try
                {

                    JSONObject jObj = new JSONObject(response);

                    JSONArray cast = jObj.getJSONArray(result);


                    if (cast.length() > 0) {

                        session.setLogin(true);

                        jObj = cast.getJSONObject(0);

                        String id = jObj.getString("id");
                        String username = jObj.getString("username");
                        String email = jObj.getString("email");
                        //String uid = jObj.getString("id");
                        String name = jObj.getString("nome");
                        String birthDate = jObj.getString("data_nascimento");
                        String gender = jObj.getString("genero");
                        String profile = jObj.getString("perfil_id");
                        String confirmation = jObj.getString("confirmado");
                        String token = jObj.getString("token");

                        String password = "";

                        // Inserting row in users table

                        //db.addUser(id, username, name, email, password, birthDate, profile, confirmation, gender, token);

                        /*studantName.setText(name);
                        studantEmail.setText(email);*/

                        Toast.makeText(getApplicationContext(), name + email, Toast.LENGTH_LONG).show();


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = "Objecto não encontrado";
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);


                return params;
            }

        };


        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
//
        pDialog.dismiss();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }




}

package com.example.complaint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class complaint extends AppCompatActivity {
    EditText name,problem,landmark;
    TextView problemtype,location,district,panchayat,ward;
    Spinner spinner1,spinner2,spinner3,spinner4,spinner5;
    Button submit;
    FirebaseAuth mauth;
    FirebaseUser muser;
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        spinner1=findViewById(R.id.spin1);
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.language,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        mauth=FirebaseAuth.getInstance();
        muser=mauth.getCurrentUser();
        final String ph=muser.getPhoneNumber();
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text=parent.getItemAtPosition(position).toString();
                if(text.equals("ENGLISH"))
                {
                    name.setHint("Name of the complainant");
                    problem.setHint("Problem Description(if others)");
                    //landmark.setHint("Landmark");

                    problemtype.setText("Problem Type :");
                    location.setText("Location Details");
                    district.setText(" District            :");
                    panchayat.setText("Panchayat             :");
                    ward.setText("Ward no             :");
                    ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(complaint.this,R.array.problems,android.R.layout.simple_spinner_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);


                    ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource(complaint.this,R.array.district,android.R.layout.simple_spinner_item);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner3.setAdapter(adapter3);
                    spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String text=parent.getItemAtPosition(position).toString();
                            if(text.equals("Thrissur")) {

                                ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(complaint.this, R.array.panchayat1, android.R.layout.simple_spinner_item);
                                adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner4.setAdapter(adapter4);
                                spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String text=parent.getItemAtPosition(position).toString();
                                        if(text.equals("avinissery"))
                                        {

                                            ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(complaint.this, R.array.ward1, android.R.layout.simple_spinner_item);
                                            adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinner5.setAdapter(adapter5);
                                        }
                                        else
                                        {

                                            ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(complaint.this, R.array.ward2, android.R.layout.simple_spinner_item);
                                            adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinner5.setAdapter(adapter5);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                            else
                            {

                                ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(complaint.this, R.array.panchayat2, android.R.layout.simple_spinner_item);
                                adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner4.setAdapter(adapter4);
                                spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String text=parent.getItemAtPosition(position).toString();
                                        if(text.equals("kochi"))
                                        {

                                            ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(complaint.this, R.array.ward3, android.R.layout.simple_spinner_item);
                                            adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinner5.setAdapter(adapter5);
                                        }
                                        else
                                        {

                                            ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(complaint.this, R.array.ward4, android.R.layout.simple_spinner_item);
                                            adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinner5.setAdapter(adapter5);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                }
                else
                {
                    name.setHint("പരാതിക്കാരന്റെ പേര്");
                    problem.setHint("പ്രശ്നവിശദീകരണം(മറ്റുള്ളവ ആണെങ്കിൽ ) ");
                    landmark.setHint("അടയാളം");
                    problemtype.setText("പ്രശ്നത്തിന്റെ ഇനം :");
                    location.setText("സ്ഥലവിവരങ്ങൾ");
                    district.setText("ജില്ല             :");
                    panchayat.setText("പഞ്ചായത്ത്             :");
                    ward.setText("വാർഡ് നമ്പർ             :");
                    ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(complaint.this,R.array.problemsmlm,android.R.layout.simple_spinner_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);


                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String text=parent.getItemAtPosition(position).toString();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource(complaint.this,R.array.districtmlm,android.R.layout.simple_spinner_item);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner3.setAdapter(adapter3);
                    spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String text=parent.getItemAtPosition(position).toString();
                            if(text.equals("തൃശൂർ")) {

                                ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(complaint.this, R.array.panchayat1mlm, android.R.layout.simple_spinner_item);
                                adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner4.setAdapter(adapter4);
                                spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String text=parent.getItemAtPosition(position).toString();
                                        if(text.equals("അവിണിശ്ശേരി"))
                                        {

                                            ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(complaint.this, R.array.ward1, android.R.layout.simple_spinner_item);
                                            adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinner5.setAdapter(adapter5);
                                        }
                                        else
                                        {

                                            ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(complaint.this, R.array.ward2, android.R.layout.simple_spinner_item);
                                            adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinner5.setAdapter(adapter5);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                            else
                            {

                                ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(complaint.this, R.array.panchayat2mlm, android.R.layout.simple_spinner_item);
                                adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner4.setAdapter(adapter4);
                                spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String text=parent.getItemAtPosition(position).toString();
                                        if(text.equals("കൊച്ചി"))
                                        {

                                            ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(complaint.this, R.array.ward3, android.R.layout.simple_spinner_item);
                                            adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinner5.setAdapter(adapter5);
                                        }
                                        else
                                        {

                                            ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(complaint.this, R.array.ward4, android.R.layout.simple_spinner_item);
                                            adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinner5.setAdapter(adapter5);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        name=findViewById(R.id.name);
        problem=findViewById(R.id.problem);
        landmark=findViewById(R.id.landmark);
        problemtype=findViewById(R.id.problem_type);
        location=findViewById(R.id.location);
        district=findViewById(R.id.district);
        panchayat=findViewById(R.id.panchayat);
        ward=findViewById(R.id.ward);
        spinner2=findViewById(R.id.spin2);
        spinner3=findViewById(R.id.spin3);
        spinner4=findViewById(R.id.spin4);
        spinner5=findViewById(R.id.spin5);
        submit=findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
  


            }
        });














    }
}



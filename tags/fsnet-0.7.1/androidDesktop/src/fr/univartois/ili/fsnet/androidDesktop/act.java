package fr.univartois.ili.fsnet.androidDesktop;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class act extends Activity {

	private EditText login;
	private EditText password;
	private EditText fsnetAdr;
	private EditText webAdr;
	private Spinner language;
	private EditText alert;
	private Button test;
	private Button validate;
	private Button cancel;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initControls();
	}

	private void initControls() {
		login = (EditText) findViewById(R.id.login);
		password = (EditText) findViewById(R.id.password);
		fsnetAdr = (EditText) findViewById(R.id.fsnetAdr);
		webAdr = (EditText) findViewById(R.id.webAdr);
		language = (Spinner) findViewById(R.id.language);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.languages, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		language.setAdapter(adapter);
		alert = (EditText) findViewById(R.id.alert);
		test = (Button) findViewById(R.id.btnTest);
		validate = (Button) findViewById(R.id.btnValidate);
		cancel = (Button) findViewById(R.id.btnCancel);
		test.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				test();
			}
		});
		validate.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				connect();
			}
		});
		cancel.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				reset();
			}
		});
	}

	private void test() {
	}

	private void reset() {
		login.setText("");
		password.setText("");
		fsnetAdr.setText("");
		webAdr.setText("");
		language.setId(0);
		alert.setText("");
	}

	private void connect() {
	}

}
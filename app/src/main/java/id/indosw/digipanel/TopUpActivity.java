package id.indosw.digipanel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import id.indosw.jsonview.lib.JsonViewLayout;
import id.indosw.networking.JavaNetRequest;
import id.indosw.networking.JavaNetRequestController;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SuppressWarnings("SpellCheckingInspection")
public class TopUpActivity extends AppCompatActivity {
	private final Timer _timer = new Timer();
	
	private String endPointUrl = "";
	private String cmdString = "";
	private String buildSignString = "";
	private String filterResponse = "";
	private HashMap<String, Object> newMapHistory = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> listMapProduct = new ArrayList<>();
	private final ArrayList<String> listCommandPricelist = new ArrayList<>();

	private RecyclerView recyclerview1;
	private ProgressBar progressbar1;
	private JsonViewLayout jsonView;
	private TextView textview1;
	private TextView textview2;
	private MaterialButton submitTopUp;
	private TextView textview3;
	private TextView refID;
	private TextInputEditText noHP;
	private TextInputEditText skuCode;
	private LinearLayout linearResponView;

	private SharedPreferences spf;
	private JavaNetRequest callTopUp;
	private JavaNetRequest.RequestListener _callTopUp_request_listener;
	private TimerTask timeOut;
	private Calendar cal = Calendar.getInstance();
	private SharedPreferences historyTransaksi;
	private final Intent iRefresh = new Intent();
	private final Intent iBack = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.top_up);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		/**
		LinearLayout linear1 = findViewById(R.id.linear1);
		LinearLayout linear2 = findViewById(R.id.linear2);
		LinearLayout linear3 = findViewById(R.id.linear3);
		CardView cardTopUpForm = findViewById(R.id.cardTopUpForm);
		LinearLayout linear5 = findViewById(R.id.linear5);
		LinearLayout linear6 = findViewById(R.id.linear6);
		TextInputLayout textinputlayout1 = findViewById(R.id.textinputlayout1);
		TextInputLayout textinputlayout2 = findViewById(R.id.textinputlayout2);
		ImageView refreshRefID = findViewById(R.id.refreshRefID);
		ImageView copyRefID = findViewById(R.id.copyRefID);
		 */

		recyclerview1 = findViewById(R.id.recyclerview1);
		progressbar1 = findViewById(R.id.progressbar1);
		jsonView = findViewById(R.id.jsonView);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		submitTopUp = findViewById(R.id.submitTopUp);
		textview3 = findViewById(R.id.textview3);
		refID = findViewById(R.id.refID);
		noHP = findViewById(R.id.noHP);
		skuCode = findViewById(R.id.skuCode);
		linearResponView = findViewById(R.id.linearResponView);
		MaterialButton refreshResult = findViewById(R.id.refreshResult);
		spf = getSharedPreferences("spf", Activity.MODE_PRIVATE);

		historyTransaksi = getSharedPreferences("historyTransaksi", Activity.MODE_PRIVATE);

		callTopUp = new JavaNetRequest(this);
		
		submitTopUp.setOnClickListener(_view -> {
			progressbar1.setVisibility(View.VISIBLE);
			callTopUp.startRequestNetwork(JavaNetRequestController.GET, "https://kopimanis.my.id/digiflazz/api-topuplive.php?sku_code=".concat(skuCode.getText().toString().concat("&customer_no=".concat(noHP.getText().toString().concat("&ref_id=".concat(refID.getText().toString()))))), "topup", _callTopUp_request_listener);
			newMapHistory.put("sku", skuCode.getText().toString());
			newMapHistory.put("nohp", noHP.getText().toString());
			newMapHistory.put("ref", refID.getText().toString());
			historyTransaksi.edit().putString("data", new Gson().toJson(newMapHistory)).apply();
		});
		
		refreshResult.setOnClickListener(_view -> {
			iRefresh.setClass(getApplicationContext(), TopUpActivity.class);
			iRefresh.putExtra("refresh", "ya");
			startActivity(iRefresh);
			finish();
		});
		
		_callTopUp_request_listener = new JavaNetRequest.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _response = _param2;
                timeOut = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(() -> {
							progressbar1.setVisibility(View.GONE);
							linearResponView.setVisibility(View.VISIBLE);
							jsonView.bindJson(_response);
						});
					}
				};
				_timer.schedule(timeOut, 2000);
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {

            }
		};
	}
	
	private void initializeLogic() {
		recyclerview1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
		recyclerview1.setHasFixedSize(true);
		_CustomFontBlock();
		_initListCommand();
		progressbar1.setVisibility(View.GONE);
		linearResponView.setVisibility(View.GONE);
		_getPriceListData();
		if (getIntent().getStringExtra("refresh").equals("ya")) {
			newMapHistory = new Gson().fromJson(historyTransaksi.getString("data", ""), new TypeToken<HashMap<String, Object>>(){}.getType());
			progressbar1.setVisibility(View.VISIBLE);
			callTopUp.startRequestNetwork(JavaNetRequestController.GET, "https://kopimanis.my.id/digiflazz/api-topuplive.php?sku_code=".concat(newMapHistory.get("sku").toString().concat("&customer_no=".concat(newMapHistory.get("nohp").toString().concat("&ref_id=".concat(newMapHistory.get("ref").toString()))))), "refresh", _callTopUp_request_listener);
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
    }
	
	
	@Override
	public void onBackPressed() {
		iBack.setClass(getApplicationContext(), MainActivity.class);
		startActivity(iBack);
		finish();
	}
	public void _getPriceListData () {
		endPointUrl = listCommandPricelist.get(0);
		cmdString = listCommandPricelist.get(1);
		buildSignString = listCommandPricelist.get(3);
		try{
			runGetPricelistData();
		}catch(Exception e){
			e.printStackTrace();
		}
	}


    void runGetPricelistData() {
		OkHttpClient cli = new OkHttpClient().newBuilder().build();
        RequestBody rb = new MultipartBody.Builder().setType(MultipartBody.FORM)
		.addFormDataPart("url_host" , ConfigApi.SERVER_H2H.concat(endPointUrl))
		.addFormDataPart("username" , spf.getString("username", ""))
		.addFormDataPart("key" , spf.getString("dev_key", ""))
		.addFormDataPart("cmd" , cmdString)
		.addFormDataPart("str_buildsign" , buildSignString)
		.build();

		Request req = new Request.Builder()
		.url(ConfigApi.BACKEND_URL)
		.method("POST", rb)
		.addHeader("Content-Type", "application/x-www-form-urlencoded")
		.build();
		cli.newCall(req).enqueue(new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {
				call.cancel();
			}
			@Override
			public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
				final String responseString = response.body().string();
				TopUpActivity.this.runOnUiThread(() -> {
					filterResponse = responseString.replace("{\"data\":", "").replace("]}", "]");
					listMapProduct = new Gson().fromJson(filterResponse, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
					recyclerview1.setAdapter(new Recyclerview1Adapter(listMapProduct));
				});
			}

		});
	}


    public void _initListCommand () {
		listCommandPricelist.add("price-list");
		listCommandPricelist.add("prepaid");
		listCommandPricelist.add("pasca");
		listCommandPricelist.add("pricelist");
	}
	
	
	public String _currencyFormat (final String _amount) {
		DecimalFormat formatter = new DecimalFormat("###,###,##0");
		return formatter.format(Double.parseDouble(_amount));
	}
	
	
	public void _CustomFontBlock () {
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/titillium.ttf"), Typeface.BOLD);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/titillium.ttf"), Typeface.BOLD);
		noHP.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/titillium.ttf"), Typeface.BOLD);
		skuCode.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/titillium.ttf"), Typeface.BOLD);
		textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/titillium.ttf"), Typeface.BOLD);
		refID.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/titillium.ttf"), Typeface.BOLD);
		submitTopUp.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/googlesans_bold.ttf"), Typeface.BOLD);
	}
	
	
	public class Recyclerview1Adapter extends Adapter<Recyclerview1Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@NotNull
        @Override
		public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			@SuppressLint("InflateParams")
            View _v = _inflater.inflate(R.layout.price_list, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@SuppressLint({"SetTextI18n", "SimpleDateFormat"})
        @Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;

            /*final LinearLayout linear1 = _view.findViewById(R.id.linear1);
            final LinearLayout linear4 = _view.findViewById(R.id.linear4);
            final LinearLayout linear2 = _view.findViewById(R.id.linear2);
            final LinearLayout linear3 = _view.findViewById(R.id.linear3);
            final ImageView imageview1 = _view.findViewById(R.id.imageview1);
             */

			final CardView cardBase = _view.findViewById(R.id.cardBase);
			final TextView productName = _view.findViewById(R.id.productName);
			final TextView categoryProduct = _view.findViewById(R.id.categoryProduct);
			final TextView textview5 = _view.findViewById(R.id.textview5);
			final TextView skuProduct = _view.findViewById(R.id.skuProduct);
			final TextView textview6 = _view.findViewById(R.id.textview6);
			final TextView priceProduct = _view.findViewById(R.id.priceProduct);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			productName.setText(_data.get(_position).get("product_name").toString());
			categoryProduct.setText(_data.get(_position).get("category").toString());
			skuProduct.setText(_data.get(_position).get("buyer_sku_code").toString());
			priceProduct.setText(_currencyFormat(_data.get(_position).get("price").toString()));
			productName.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/hammersmithone_reg.ttf"), Typeface.NORMAL);
			categoryProduct.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/titillium.ttf"), Typeface.BOLD);
			skuProduct.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/googlesans_bold.ttf"), Typeface.NORMAL);
			priceProduct.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/muli_regular.ttf"), Typeface.BOLD);
			textview5.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/googlesans_bold.ttf"), Typeface.NORMAL);
			textview6.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/googlesans_bold.ttf"), Typeface.NORMAL);
			cardBase.setOnClickListener(_view1 -> {
				cal = Calendar.getInstance();
				refID.setText("TRX".concat(new SimpleDateFormat("ddMMyyyyHHmms").format(cal.getTime())));
				skuCode.setText(_data.get(_position).get("buyer_sku_code").toString());
			});
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
		
	}
}

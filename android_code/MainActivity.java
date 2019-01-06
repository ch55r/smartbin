package kr.co.sora.sbapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends BaseActivity {

    TextView log;

    int num=0;
    private static String TAG = "hi_MainActivity";
    private static final String TAG_JSON = "result";
    private static final String TAG_dis = "distance";
    private static final String TAG_wei = "weight";
    private static final String TAG_disV = "distance_value";
    private static final String TAG_weiV = "weight_value";
    String mJsonString;


    @Override
    protected void onResume(){
        this.overridePendingTransition(0,0);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //변수 초기화
        log = (TextView) findViewById(R.id.log);
        name[0] = (TextView) findViewById(R.id.name1);
        name[1] = (TextView) findViewById(R.id.name2);
        name[2] = (TextView) findViewById(R.id.name3);
        name[3] = (TextView) findViewById(R.id.name4);
        name[4] = (TextView) findViewById(R.id.name5);
        name[5] = (TextView) findViewById(R.id.name6);
        name[6] = (TextView) findViewById(R.id.name7);
        name[7] = (TextView) findViewById(R.id.name8);
        name[8] = (TextView) findViewById(R.id.name9);

        state[0] = (TextView) findViewById(R.id.state1);
        state[1] = (TextView) findViewById(R.id.state2);
        state[2] = (TextView) findViewById(R.id.state3);
        state[3] = (TextView) findViewById(R.id.state4);
        state[4] = (TextView) findViewById(R.id.state5);
        state[5] = (TextView) findViewById(R.id.state6);
        state[6] = (TextView) findViewById(R.id.state7);
        state[7] = (TextView) findViewById(R.id.state8);
        state[8] = (TextView) findViewById(R.id.state9);

        bin[0] = (Button) findViewById(R.id.bin1);
        bin[1] = (Button) findViewById(R.id.bin2);
        bin[2] = (Button) findViewById(R.id.bin3);
        bin[3] = (Button) findViewById(R.id.bin4);
        bin[4] = (Button) findViewById(R.id.bin5);
        bin[5] = (Button) findViewById(R.id.bin6);
        bin[6] = (Button) findViewById(R.id.bin7);
        bin[7] = (Button) findViewById(R.id.bin8);
        bin[8] = (Button) findViewById(R.id.bin9);




        String add1 = "http://192.168.43.58/send.php";

        add[0]=add1;
        bin_name[0]="공대3호관 스마트빈";
            GetData task = new GetData();
            task.execute(add[0]);

//        insertData(0,"fdsf","공대3 화상강의실",11,1100);
 //       insertData(1,"fdsf","공대3 13419",10,700);
  //      insertData(2,"fdsf","공대3 13420",18,1600);
   //     insertData(3,"fdsf","공대3 여화장실 4F",23,800);
    //    insertData(4,"fdsf","공대3 남화장실 4F",16,1700);
     //   insertData(5,"fdsf","공대3 134131",23,1950);
      //  insertData(6,"fdsf","공대3 13104",20,1500);
 //       insertData(7,"fdsf","공대3 여화장실 1F",24,1800);
  //      insertData(8,"fdsf","공대3 남화장실 1F",13,900);

        sorting();
        layout_visible();

    }

    public void insertData(int i, String addid, String name, double distance, double weight)
    {
        add[i]=addid;
        bin_name[i]=name;
        data[i][0]=distance;
        data[i][1]=weight;
        data[i][2]=distance/25;
        data[i][3]=weight/2000;
        data[i][4]=data[i][2]*data[i][3];

    }
    public void retryCk(View v)
    {
        Intent redo=new Intent(this,MainActivity.class);
        redo.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(redo);

    }

    public void addCk(View v)
    {
        Intent intent01=new Intent(this,addActivity.class);
        startActivity(intent01);
    }

    public void layout_visible() {
        for (int i = 0; i < 9; i++) {
            if (add[i]!=null) {
                state[i].setVisibility(View.VISIBLE);
                bin[i].setVisibility(View.VISIBLE);
                name[i].setVisibility(View.VISIBLE);
                name[i].setText(bin_name[i]);
                String stateSt=String.format("%.1f",data[i][4]*100);
                state[i].setText(stateSt+" %");
                if(data[i][4]>0.80)
                {
                    state[i].setBackgroundColor(Color.rgb(255,124,124));
                    bin[i].setBackground(ContextCompat.getDrawable(this, R.drawable.full_bin));
                }
                else if(data[i][4]>0.4)
                {state[i].setBackgroundColor(Color.rgb(194,236,255));bin[i].setBackground(ContextCompat.getDrawable(this, R.drawable.empty_bin));}
                else {state[i].setBackgroundColor(Color.rgb(124,255,124));bin[i].setBackground(ContextCompat.getDrawable(this, R.drawable.empty_bin));}
            }
        }
    }

    public void sorting() {
        int cn = 0;
        double temp[] = new double[5];
        String nametemp;
        for (int i = 0; i < 9; i++) {
            if (add[i] == null) {
                cn=i;
                 break;
            }

            cn=9;

        }
        for (int k = 0; k < cn; k++) {
            for (int j = 1; j < cn; j++) {
                if (data[j][4] > data[j - 1][4]) {
                    nametemp=bin_name[j];
                    bin_name[j]=bin_name[j-1];
                    bin_name[j-1]=nametemp;
                    nametemp=add[j];
                    add[j]=add[j-1];
                    add[j-1]=nametemp;
                    for (int i = 0; i < 5; i++) {
                        temp[i] = data[j][i];
                        data[j][i] = data[j - 1][i];
                        data[j - 1][i] = temp[i];
                    }
                }
            }
        }
    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            log.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null) {

                log.setText(errorString);
            } else {

                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];


            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(0);
                httpURLConnection.setConnectTimeout(0);
                httpURLConnection.connect();
                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }

        }
    }

    private void showResult() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);
                for (int j=0;j<9;j++) {
                    if (data[j][0]==0) {
                        num = j;
                        data[num][0] = item.getDouble(TAG_dis);
                        data[num][1] = item.getDouble(TAG_wei);
                        data[num][2] = item.getDouble(TAG_disV);
                        data[num][3] = item.getDouble(TAG_weiV);
                        data[num][4] = data[num][2] * data[num][3];
   //                     Toast.makeText(this,Double.toString(data[num][0]),Toast.LENGTH_SHORT).show();

                        sorting();
                        layout_visible();
                    }
                }
            }
            //함수

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }


    }

    public void but1(View v)
    {
        dialog(0);
    }
    public void but2(View v)
    {
        dialog(1);
    }
    public void but3(View v)
    {
        dialog(2);
    }
    public void but4(View v)
    {
        dialog(3);
    }
    public void but5(View v)
    {
        dialog(4);
    }
    public void but6(View v)
    {
        dialog(5);
    }
    public void but7(View v)
    {
        dialog(6);
    }
    public void but8(View v)
    {
        dialog(7);
    }
    public void but9(View v)
    {
        dialog(8);
    }

    public void dialog(int row)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        // 제목셋팅
        alertDialogBuilder.setTitle(" [ "+bin_name[row]+" ] 의 SMART BIN 정보");

        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("\n쓰레기 높이 : " + data[row][0] + " cm\n쓰레기 무게 : " + data[row][1] + "g\n\n")
                .setCancelable(false)
                .setNegativeButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        // 다이얼로그 보여주기
        alertDialog.show();
    }
}
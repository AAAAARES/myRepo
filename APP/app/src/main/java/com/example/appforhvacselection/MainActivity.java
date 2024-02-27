package com.example.appforhvacselection;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private static final String[] input = {"city", "houseType", "floor", "direction", "room", "window"};
    private static final String[] city = {"Changsha", "Chongqing", "Guangzhou", "Shanghai", "Wuhan"};
    private static final String[] houseType = {"1_BEDROOM", "2_BEDROOM", "3_BEDROOM", "4_BEDROOM",};
    private static final String[] floor = {"Ground", "Middle", "Top"};
    private static final String[] direction = {"East", "Middle", "West"};
    private static final String[] room1 = {"LIVING ROOM", "BEDROOM01"};
    private static final String[] room2 = {"LIVING ROOM", "BEDROOM01", "BEDROOM02"};
    private static final String[] room3 = {"LIVING ROOM", "BEDROOM01", "BEDROOM02", "BEDROOM03"};
    private static final String[] room4 = {"LIVING ROOM", "BEDROOM01", "BEDROOM02", "BEDROOM03", "BEDROOM04"};
    private static final String[] window = {"Standard", "French"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner1 = findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                input[0] = city[arg2];
                //Toast.makeText(getApplicationContext(), input[2], Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        Spinner spinner2 = this.findViewById(R.id.spinner2);
        Spinner spinner5 = this.findViewById(R.id.spinner5);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.houseType, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter1);
        ImageView imageView = findViewById(R.id.image);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                input[1] = houseType[arg2];

                Spinner s = (Spinner) arg0;
                String type = (String) s.getItemAtPosition(arg2);
                ArrayAdapter<CharSequence> adapter2 = null;
                switch (type) {
                    case "一室一厅":
                        adapter2 = ArrayAdapter.createFromResource(
                                MainActivity.this, R.array.room1,
                                android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        imageView.setImageResource(R.drawable.house1);
                        break;
                    case "二室一厅":
                        adapter2 = ArrayAdapter.createFromResource(
                                MainActivity.this, R.array.room2,
                                android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        imageView.setImageResource(R.drawable.house2);
                        break;
                    case "三室二厅":
                        adapter2 = ArrayAdapter.createFromResource(
                                MainActivity.this, R.array.room3,
                                android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        imageView.setImageResource(R.drawable.house3);
                        break;
                    case "四室二厅":
                        adapter2 = ArrayAdapter.createFromResource(
                                MainActivity.this, R.array.room4,
                                android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        imageView.setImageResource(R.drawable.house4);
                        break;
                }
                spinner5.setAdapter(adapter2);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        Spinner spinner3 = findViewById(R.id.spinner3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                input[2] = floor[arg2];
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        Spinner spinner4 = findViewById(R.id.spinner4);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                input[3] = direction[arg2];
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                switch (arg2) {
                    case 0:
                        input[4] = room1[arg2];
                        break;
                    case 1:
                        input[4] = room2[arg2];
                        break;
                    case 2:
                        input[4] = room3[arg2];
                        break;
                    case 3:
                        input[4] = room4[arg2];
                    case 4:
                        input[4] = room4[arg2];
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        Spinner spinner6 = findViewById(R.id.spinner6);
        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                input[5] = window[arg2];
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(getAssets().open("Results.json"), StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//buffer：缓冲区
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                inputStreamReader.close();
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                //把results文件保存到jsonArray里面
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                //获取面积area
                EditText editText = findViewById(R.id.editText);
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入房间面积", Toast.LENGTH_SHORT).show();
                } else {
                    double area = Double.parseDouble(editText.getText().toString());//parseDouble:转换为double类型
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        if (object.getString("City").equals(input[0]) &
                                object.getString("House type").equals(input[1]) &
                                object.getString("Floor").equals(input[2]) &
                                object.getString("Direction").equals(input[3]) &
                                object.getString("Room").equals(input[4]) &
                                object.getString("Window").equals(input[5])) {
/*                            //调试日志
                            Log.i("", "Match found!!!");
                            for (String s : input) {
                                Log.i("test1", "input[]:" + s);
                            }
                            Log.i("", "Cooling " + object.getDouble("Unit Cooling Capacity [W/m2]"));
                            Log.i("", "Heating " + object.getDouble("Unit Heating Capacity [W/m2]"));*/
                            double Cooling = object.getDouble("Unit Cooling Capacity [W/m2]");
                            double Heating = object.getDouble("Unit Heating Capacity [W/m2]");
                            double double1 = area * Cooling;
                            int int1 = (int) double1;
                            double double2 = area * Heating;
                            int int2 = (int) double2;
                            //输出整数 对应strings里的%1$d
                            TextView cooling = findViewById(R.id.output1_2);
                            cooling.setText(String.format(getResources().getString(R.string.output1_2), int1));
                            TextView heating = findViewById(R.id.output2_2);
                            heating.setText(String.format(getResources().getString(R.string.output2_2), int2));
/*                            //输出小数 对应strings里的%1$f
                            TextView cooling = findViewById(R.id.output1_2);
                            cooling.setText(String.format(getResources().getString(R.string.output1_2), double1));
                            TextView heating = findViewById(R.id.output2_2);
                            heating.setText(String.format(getResources().getString(R.string.output2_2), double2));*/
                        }
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner1:
                input[0] = city[position];
                break;
            case R.id.spinner3:
                input[2] = floor[position];
                break;
            case R.id.spinner4:
                input[3] = direction[position];
                break;
            case R.id.spinner6:
                input[5] = window[position];
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }
}

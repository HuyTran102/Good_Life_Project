package com.example.goodlife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;

public class HomePage extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private Button drawerMenuButton;
    private LinearLayout nutritionalStatusButton, physicalButton, dietaryButton, tempMenuButton, recommendMenuButton, waterDemandButton;
    private ProgressBar weightProgressBar, heightProgressBar, kcaloProgressBar;
    private TextView weightProgressText, heightProgressText, kcaloProgressText, weightView, heightView, kcaloView;
    private double actualWeight, actualHeight, usedEnergy, addEnergy, actualEnergy, recommendWeight, recommendHeight, recommendEnergy, statusWeight, statusHeight, statusEnergy;
    private int weight, height, kcalo;
    private String weight_status, height_status, energy_status;
    private String name;
    public Boolean ok1 = false, ok2 = false, ok3 = false;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String filename = "Storage.txt";
    File myInternalFile;
    private String filepath = "Super_mystery_folder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Make status bar fully transparent
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));

        // Set the layout to extend into the status bar
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        nutritionalStatusButton = findViewById(R.id.nutritional_status_button);
        physicalButton = findViewById(R.id.physical_button);
        dietaryButton = findViewById(R.id.dietary_button);
        tempMenuButton = findViewById(R.id.temp_menu_button);
        recommendMenuButton = findViewById(R.id.recommend_menu_button);
        waterDemandButton = findViewById(R.id.water_demand_button);
        weightView = findViewById(R.id.weight_view);
        heightView = findViewById(R.id.height_view);
        kcaloView = findViewById(R.id.kcalo_view);
        weightProgressBar = findViewById(R.id.weight_progres_bar);
        weightProgressText = findViewById(R.id.weight_progres_text);
        heightProgressBar = findViewById(R.id.height_progres_bar);
        heightProgressText = findViewById(R.id.height_progres_text);
        kcaloProgressBar = findViewById(R.id.kcalo_progres_bar);
        kcaloProgressText = findViewById(R.id.kcalo_progres_text);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerMenuButton = findViewById(R.id.drawer_menu_button);

        drawerLayout.bringToFront();
        navigationView.bringToFront();
        drawerMenuButton.bringToFront();

        drawerMenuButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        ContextWrapper contextWrapper = new ContextWrapper(
                getApplicationContext());
        File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, filename);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                drawerMenuButton.setAlpha(0f);
                drawerMenuButton.setVisibility(View.GONE);
                drawerMenuButton.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                drawerMenuButton.setAlpha(1f);
                drawerMenuButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.user_information) {
                    Intent intent = new Intent(HomePage.this, UserInformation.class);
                    startActivity(intent);
                    finish();
                } else if (id == R.id.notification) {
                    Intent intent = new Intent(HomePage.this, Notification.class);
                    startActivity(intent);
                    finish();
                } else if (id == R.id.tracking_diagram) {
                    Intent intent = new Intent(HomePage.this, TrackingDiagram.class);
                    startActivity(intent);
                    finish();
                } else if (id == R.id.about_us) {
                    Intent intent = new Intent(HomePage.this, AboutUs.class);
                    startActivity(intent);
                    finish();
                } else if (id == R.id.app_ranking) {
                    Intent intent = new Intent(HomePage.this, AppRanking.class);
                    startActivity(intent);
                    finish();
                } else if (id == R.id.question_and_answer) {
                    Intent intent = new Intent(HomePage.this, QuestionAndAnswer.class);
                    startActivity(intent);
                    finish();
                } else if (id == R.id.contact_with_nutritionist) {
                    Intent intent = new Intent(HomePage.this, ContactWithNutritionist.class);
                    startActivity(intent);
                    finish();
                } else if (id == R.id.contact_support_team) {
                    Intent intent = new Intent(HomePage.this, ContactSupportTeam.class);
                    startActivity(intent);
                    finish();
                } else if (id == R.id.logout) {
                    try {
                        String data = null + "\n" + "null" + "\n" +"false";
                        FileOutputStream fos = new FileOutputStream(myInternalFile);
                        fos.write(data.getBytes());
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(HomePage.this, "Đăng xuất", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HomePage.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        SharedPreferences waterVal = getSharedPreferences("Water", Context.MODE_PRIVATE);

        int value = waterVal.getInt("Val", 0);

        if(value != 0) {
            scheduleNotification(6, 0, 1, value);
            scheduleNotification(9, 0, 2, value);
            scheduleNotification(11, 0, 3, value);
            scheduleNotification(14, 0, 4, value);
            scheduleNotification(18, 0, 5, value);
        }

        SharedPreferences sp = getSharedPreferences("Data", Context.MODE_PRIVATE);

        name = sp.getString("Name", null);

        setNullValue();

        LoadDataFireBase();

        nutritionalStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, CalculateNutritionalStatus.class);
                startActivity(intent);
                finish();
            }
        });

        physicalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Physical.class);
                startActivity(intent);
                finish();
            }
        });

        dietaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Dietary.class);
                startActivity(intent);
                finish();
            }
        });

        tempMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, TemplateMenuView.class);
                startActivity(intent);
                finish();
            }
        });

        recommendMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, RecommendMenu.class);
                startActivity(intent);
                finish();
            }
        });

        waterDemandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, WaterDemand.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void scheduleNotification(int hour, int minute, int id, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("EXTRA_VALUE", value);
        intent.putExtra("EXTRA_ID", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    // Load Data to Recycle Item
    public void LoadDataFireBase() {
        // Task 1: Lấy dữ liệu từ Firestore cho "Dinh dưỡng"
        Task<QuerySnapshot> nutritionTask = firebaseFirestore.collection("GoodLife")
                .document(name).collection("Dinh dưỡng")
                .get();

        // Task 2: Lấy dữ liệu từ Firebase Realtime Database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
        Query userDatabase = reference.orderByChild("name").equalTo(name);
        TaskCompletionSource<Void> realtimeDatabaseTask = new TaskCompletionSource<>();

        userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String gender = snapshot.child(name).child("gender").getValue(String.class);
                String date = snapshot.child(name).child("date_of_birth").getValue(String.class);

                String[] birth = date.split("/");
                String year_of_birth = birth[2];

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);

                int age = year - Integer.parseInt(year_of_birth);

                if (gender.equals("Nam")) {
                    if (10 <= age && age <= 11) {
                        recommendEnergy = 1900;
                    } else if (12 <= age && age <= 14) {
                        recommendEnergy = 2200;
                    } else {
                        recommendEnergy = 2500;
                    }
                } else {
                    if (10 <= age && age <= 11) {
                        recommendEnergy = 1750;
                    } else if (12 <= age && age <= 14) {
                        recommendEnergy = 2050;
                    } else {
                        recommendEnergy = 2100;
                    }
                }

                // Hoàn thành task khi dữ liệu từ Realtime Database được lấy
                realtimeDatabaseTask.setResult(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                realtimeDatabaseTask.setException(error.toException());
            }
        });

        // Get the current date
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1; // tháng 1 bắt đầu từ 0 trong Calendar
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // Task 3: Lấy dữ liệu từ Firestore cho "Hoạt động thể lực"
        Task<QuerySnapshot> activityTask = firebaseFirestore.collection("GoodLife")
                .document(name).collection("Hoạt động thể lực")
                .whereEqualTo("year", String.valueOf(year))
                .whereEqualTo("month", String.valueOf(month))
                .whereEqualTo("day", String.valueOf(day))
                .get();

        // Task 4: Lấy dữ liệu từ Firestore cho "Nhật kí"
        Task<QuerySnapshot> diaryTask = firebaseFirestore.collection("GoodLife")
                .document(name)
                .collection("Nhật kí")
                .whereEqualTo("year", String.valueOf(year))
                .whereEqualTo("month", String.valueOf(month))
                .whereEqualTo("day", String.valueOf(day))
                .get();

        // Chờ cho tất cả các Task hoàn thành
        Tasks.whenAll(nutritionTask, realtimeDatabaseTask.getTask(), activityTask, diaryTask)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Task 1: Xử lý kết quả của Nutrition task
                        QuerySnapshot nutritionResult = nutritionTask.getResult();
                        if (nutritionResult != null) {
                            for (QueryDocumentSnapshot document : nutritionResult) {
                                if (document.getString("userHeight") != null
                                        && document.getString("userWeight") != null
                                        && document.getString("userRecommendHeight") != null
                                        && document.getString("userRecommendWeight") != null) {
                                    try {
                                        actualHeight = Double.parseDouble(document.getString("userHeight"));
                                    } catch (Exception e) {
                                        actualHeight = 0.0;
                                    }

                                    try {
                                        actualWeight = Double.parseDouble(document.getString("userWeight"));
                                    } catch (Exception e) {
                                        actualWeight = 0.0;
                                    }

                                    try {
                                        recommendHeight = Double.parseDouble(document.getString("userRecommendHeight"));
                                    } catch (Exception e) {
                                        recommendHeight = 0.0;
                                    }

                                    try {
                                        recommendWeight = Double.parseDouble(document.getString("userRecommendWeight"));
                                    } catch (Exception e) {
                                        recommendWeight = 0.0;
                                    }
                                }
                            }
                        }

                        recommendEnergy = recommendWeight * 24 * 1.5;

                        // Toast.makeText(this, " " + recommendWeight + " ", Toast.LENGTH_SHORT).show();

                        // Task 3: Xử lý kết quả của Activity task
                        QuerySnapshot activityResult = activityTask.getResult();
                        if (activityResult != null) {
                            double total_sum = 0;
                            for (QueryDocumentSnapshot document : activityResult) {
                                String amount = document.getString("userUsedEnergy");
                                if (amount != null && !amount.isEmpty()) {
                                    try {
                                        total_sum += Double.parseDouble(amount);
                                    } catch (NumberFormatException e) {
                                        Log.w("Firestore", "Error parsing used energy", e);
                                    }
                                }
                            }
                            usedEnergy = total_sum;

                            recommendEnergy = recommendWeight * 24 * 1.5 + usedEnergy;
                        }

                        // Task 4: Xử lý kết quả của Diary task
                        QuerySnapshot diaryResult = diaryTask.getResult();
                        if (diaryResult != null) {
                            double total_sum = 0;
                            for (QueryDocumentSnapshot document : diaryResult) {
                                String amount = document.getString("kcal");
                                if (amount != null && !amount.isEmpty()) {
                                    try {
                                        total_sum += Double.parseDouble(amount);
                                    } catch (NumberFormatException e) {
                                        Log.w("Firestore", "Error parsing diary energy", e);
                                    }
                                }
                            }
                            addEnergy = total_sum;
                        }
                        setWeightAndHeight();
                        Log.d("Firestore", "All tasks completed successfully");
                    } else {
                        Log.w("Firestore", "Error completing tasks", task.getException());
                    }
                });
    }


    public void setWeightAndHeight() {
        actualHeight *= 100;

        DecimalFormat decimalFormat = new DecimalFormat("0.0");

        if (recommendWeight == actualWeight) {
            statusWeight = 0;
            weight_status = "Bình thường";
        } else if (actualWeight > recommendWeight) {
            statusWeight = (actualWeight - recommendWeight);
            weight_status = "Thừa " + decimalFormat.format(statusWeight) + " (kg)";

        } else if (actualWeight < recommendWeight) {
            statusWeight = (recommendWeight - actualWeight);
            weight_status = "Thiếu " + decimalFormat.format(statusWeight) + " (kg)";
        } else {
            recommendWeight = 0;
            actualWeight = 0;
            statusWeight = 0;
            weight_status = "";
        }

        if (recommendHeight == actualHeight){
            statusHeight = 0;
            height_status = "Bình thường";
        } else if(actualHeight > recommendHeight) {
            statusHeight = (actualHeight - recommendHeight);
            height_status = "Thừa " + decimalFormat.format(statusHeight) + " (cm)";
        } else if(actualHeight < recommendHeight){
            statusHeight = (recommendHeight - actualHeight);
            height_status = "Thiếu " + decimalFormat.format(statusHeight) + " (cm)";
        } else {
            recommendHeight = -1;
            actualHeight = 0;
            statusHeight = 0;
            height_status = "";
        }

        actualEnergy = addEnergy;

        if (recommendEnergy == actualEnergy) {
            statusEnergy = 0;
            energy_status = "Bình thường";
        } else if (actualEnergy > recommendEnergy) {
            statusEnergy = (actualEnergy - recommendEnergy);
            energy_status = "Thừa " + decimalFormat.format(statusEnergy) + " (kcal)";
        } else if (actualEnergy < recommendEnergy) {
            statusEnergy = (recommendEnergy - actualEnergy);
            energy_status = "Thiếu " + decimalFormat.format(statusEnergy) + " (kcal)";
        } else{
            recommendEnergy = 0;
            actualEnergy = 0;
            statusEnergy = 0;
            energy_status = "";
        }

        if (recommendEnergy == 0 || actualEnergy == 0) {
            statusEnergy = 0;
            energy_status = "";
        }
        decimalFormat = new DecimalFormat("0");

        final Handler weight_handler = new Handler();

        weightProgressBar.setMax((int) recommendWeight);

        DecimalFormat finalDecimalFormat = decimalFormat;
        weight_handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (weight <= actualWeight) {
                        weightProgressText.setText(String.valueOf(weight + "\n" + finalDecimalFormat.format(recommendWeight)));
                    weightProgressBar.setProgress(weight);
                    weight++;
                    weight_handler.postDelayed(this, 35);
                } else {
                    weight_handler.removeCallbacks(this);
                }
            }
        }, 35);

        weightView.setText(weight_status);

        final Handler height_handler = new Handler();

        heightProgressBar.setMax((int) recommendHeight + 1);

        DecimalFormat finalDecimalFormat1 = decimalFormat;
        height_handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(height <= actualHeight) {
                    if(recommendHeight == 0) {
                        heightProgressText.setText(String.valueOf(height + "\n" + finalDecimalFormat.format(actualHeight)));
                    } else {
                        heightProgressText.setText(String.valueOf(height + "\n" + finalDecimalFormat.format(recommendHeight)));
                    }
                    heightProgressBar.setProgress(height);
                    height++;
                    height_handler.postDelayed(this, 35);
                } else {
                    height_handler.removeCallbacks(this);
                }
            }
        }, 35);

        heightView.setText(height_status);

        final Handler kcalo_handler = new Handler();

        kcaloProgressBar.setMax((int) recommendEnergy + 1);

        kcalo_handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (kcalo <= actualEnergy) {
                        kcaloProgressText.setText(String.valueOf(kcalo + "\n" + finalDecimalFormat.format(recommendEnergy)));
                    kcaloProgressBar.setProgress(kcalo);
                    kcalo++;
                    kcalo_handler.postDelayed(this, 0);
                } else {
                    kcalo_handler.removeCallbacks(this);
                }
            }
        }, 0);

        kcaloView.setText(energy_status);

    }

    public void setNullValue() {

        final Handler weight_handler = new Handler();

        weight_handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (weight <= actualWeight) {
                    if (recommendWeight == 0) {
                        weightProgressText.setText(String.valueOf(weight + "\n" + 0));
                    } else {
                        weightProgressText.setText(String.valueOf(weight + "\n" + 0));
                    }
                    weightProgressBar.setProgress(weight);
                    weight++;
                    weight_handler.postDelayed(this, 35);
                } else {
                    weight_handler.removeCallbacks(this);
                }
            }
        }, 35);

        weightView.setText(weight_status);

        final Handler height_handler = new Handler();
        height_handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (height <= 0) {
                    if (recommendHeight == 0) {
                        heightProgressText.setText(String.valueOf(height + "\n" + 0));
                    } else {
                        heightProgressText.setText(String.valueOf(height + "\n" + 0));
                    }
                    heightProgressBar.setProgress(height);
                    height++;
                    height_handler.postDelayed(this, 35);
                } else {
                    height_handler.removeCallbacks(this);
                }
            }
        }, 35);

        final Handler kcalo_handler = new Handler();

        kcaloProgressBar.setMax(0);

        kcalo_handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (kcalo <= 0) {
                    if (recommendEnergy == 0) {
                        kcaloProgressText.setText(String.valueOf(kcalo + "\n" + 0));
                    } else {
                        kcaloProgressText.setText(String.valueOf(kcalo + "\n" + 0));
                    }
                    kcaloProgressBar.setProgress(kcalo);
                    kcalo++;
                    kcalo_handler.postDelayed(this, 0);
                } else {
                    kcalo_handler.removeCallbacks(this);
                }
            }
        }, 0);
    }


    // Create notification

    public void showNotification(Context context, String title, String message, Intent intent, int reqCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}
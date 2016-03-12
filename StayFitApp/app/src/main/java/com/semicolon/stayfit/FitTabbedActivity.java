package com.semicolon.stayfit;

import android.animation.ObjectAnimator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;
import com.semicolon.stayfit.common.Constants;
import com.semicolon.stayfit.common.Display;
import com.semicolon.stayfit.common.FitnessRecommendation;
import com.semicolon.stayfit.common.NetworkCall;
import com.semicolon.stayfit.common.UserEmailFetcher;
import com.semicolon.stayfit.service.Client;
import com.semicolon.stayfit.service.History;
import com.semicolon.stayfit.service.Recording;
import com.semicolon.stayfit.service.Sensors;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FitTabbedActivity extends AppCompatActivity implements StepCountFragment.StepCountFragmentInteractionListener,
        DistanceCoveredFragment.DistanceCoveredFragmentInteractionListener,
        CalorieFragment.CalorieFragmentInteractionListener,FoodCalorieFragment.FoodCalInteractionListener{
    private static final String TAG = FitTabbedActivity.class.getName();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private Client client;
    private Sensors sensors;
    private Recording recording;
    private TabLayout tabLayout;
    private History history;
    private Display display = new Display(FitTabbedActivity.class.getName()) {
        @Override
        public void show(String message) {
            log(message);
        }
    };
    private FitnessRecommendation recommendation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fit_tabbed);
        // Permit to send REST service calls.
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout= (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();
        display.show("client initialization");
        client = new Client(this,
                new Client.Connection() {
                    @Override
                    public void onConnected() {
                        display.show("client connected");
//                we can call specific api only after GoogleApiClient connection succeeded

//                        sensors demo
                        initSensors();
                        display.show("list datasources");
                        sensors.listDatasourcesAndSubscribe();

//                        recording demo
                        //pagerAdapter.getItem(FitPagerAdapter.FragmentIndex.RECORDING);
                        recording = new Recording(client.getClient(), new Display(Recording.class.getName()) {
                            @Override
                            public void show(String msg) {
                                log(msg);

                                //add(FitPagerAdapter.FragmentIndex.RECORDING, msg);
//                                InMemoryLog.getInstance().add(FitPagerAdapter.FragmentIndex.RECORDING, msg);
                            }
                        });
                        recording.subscribe();
                        recording.listSubscriptions();

                        String email = UserEmailFetcher.getEmail(getApplicationContext());
//                        history demo
                        history = new History(client.getClient(), new Display(History.class.getName()) {
                            @Override
                            public void show(String msg) {
                                log(msg);

                                //add(FitPagerAdapter.FragmentIndex.HISTORY, msg);
//                                InMemoryLog.getInstance().add(FitPagerAdapter.FragmentIndex.HISTORY, msg);
                            }
                        }, email);

                        new AsyncTask<Void, Void, FitnessRecommendation>() {

                            @Override
                            protected FitnessRecommendation doInBackground(Void... params) {
                                return getRecommendation();
                            }

                            @Override
                            protected void onPostExecute(FitnessRecommendation recommendation) {
                                FitTabbedActivity.this.recommendation = recommendation;
                            }
                        }.execute();

                        // TODO to be removed and moved to service
                        history.readDailyData();
                        startAlert();
                    }
                },
                new Display(Client.class.getName()) {
                    @Override
                    public void show(String msg) {
                        log(msg);
                    }
                });
    }

    private FitnessRecommendation getRecommendation() {
        FitnessRecommendation recommendation = null;
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(NetworkCall.baseUrl + "recommendedData/" + UserEmailFetcher.getEmail(getApplicationContext()) + "/"));

            // receive response as inputStream
            InputStream responseStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            Log.d(TAG, out.toString());
            JSONObject object = new JSONObject(out.toString());
            recommendation = new FitnessRecommendation();
            recommendation.setEmail(object.get("empEmail").toString());
            recommendation.setDistanceInMtr(Double.valueOf(object.get("distanceInMtr").toString()));
            recommendation.setCalories(Double.valueOf(object.get("calories").toString()).longValue());
            recommendation.setStepsCount(Double.valueOf(object.get("stepsCount").toString()).longValue());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recommendation;
    }

    private void initSensors() {
        display.show("init sensors");
        sensors = new Sensors(client.getClient(),
                new Sensors.DatasourcesListener() {
                    @Override
                    public void onDatasourcesListed() {
                        display.show("datasources listed");
                        ArrayList<String> datasources = sensors.getDatasources();
                        for (String d:datasources) {
                            display.show(d);
                        }

                        //clear(FitPagerAdapter.FragmentIndex.DATASOURCES);
                        //addAll(FitPagerAdapter.FragmentIndex.DATASOURCES, datasources);
                    }
                },
                new Display(Sensors.class.getName()) {
                    @Override
                    public void show(String msg) {
                        log(msg);
                        //add(FitPagerAdapter.FragmentIndex.SENSORS, msg);
                    }
                });
    }

    public void startAlert() {

        Intent intent = new Intent(this, FitnessBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis() , Constants.ONE_DAY_IN_MILLI_SECONDS, pendingIntent);
        //Toast.makeText(this, "Alarm set in " + 1 + " day", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        display.show("Client connect()");
        client.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        display.show("Unsubscribed...");
        if (sensors != null)
            sensors.unsubscribe();
        if (recording != null)
            recording.unsubscribe();

        display.show("Client disconnect()...");
        if (client != null)
            client.disconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        display.show("onActivityResult");
        client.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fit_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.log_out){
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("StayFitApp", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("email");
            editor.commit();

            Intent intent = new Intent(FitTabbedActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

            client.revokeAuth();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_fit_tabbed, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /*private void add(int fragId, String msg) {
        InMemoryLog.getInstance().add(fragId, msg);
        Intent intent = new Intent();
        intent.setAction(Constants.Action.ADD);
        intent.putExtra(Constants.FRAG_ID, fragId);
        intent.putExtra(Constants.DATA, msg);
        sendBroadcast(intent);
    }

    private void addAll(int fragId, ArrayList<String> data) {
        InMemoryLog.getInstance().addAll(fragId, data);
        Intent intent = new Intent();
        intent.setAction(Constants.Action.ADD_ALL);
        intent.putExtra(Constants.FRAG_ID, fragId);
        intent.putStringArrayListExtra(Constants.DATA, data);
        sendBroadcast(intent);
    }

    private void clear(int fragId) {
        InMemoryLog.getInstance().clear(fragId);
        Intent intent = new Intent();
        intent.setAction(Constants.Action.CLEAR);
        intent.putExtra(Constants.FRAG_ID, fragId);
        sendBroadcast(intent);
    }*/

    private int currentProgress = 0;
    private int finalProgress;

    @Override
    public void onFragmentInteraction(final View view, int layoutId) {
        Calendar startTime = Calendar.getInstance();
        startTime.setTimeInMillis(System.currentTimeMillis());
        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);

        Calendar endTime = Calendar.getInstance();
        endTime.setTimeInMillis(System.currentTimeMillis());
        //DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        //TextView currentDate = (TextView) view.findViewById(R.id.currentDate);
        //currentDate.setText(dateFormat.format(startTime.getTime()) + "-" + dateFormat.format(endTime.getTime()));
        //currentDate.setText(dateFormat.format(startTime.getTime()));

        new FitnessHistoryGetterTask(view, layoutId, startTime, endTime).execute();
    }
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("STEPS");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_step, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("DISTANCE");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_dist, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("CALORIES");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cal, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("FOOD CAL");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_food_cal, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);
    }
    public class FitnessHistoryGetterTask extends AsyncTask<Void, Void, Boolean> {
        private int fragmentId;
        private Calendar startTime;
        private Calendar endTime;
        private TextView value;
        private DecimalFormat df = new DecimalFormat( "###0.00" );
        private ProgressBar progressBar;

        FitnessHistoryGetterTask(View view, int fragmentId, Calendar startTime, Calendar endTime) {
            this.fragmentId = fragmentId;
            this.startTime = startTime;
            this.endTime = endTime;

            switch (fragmentId) {
                case SectionsPagerAdapter.FragmentIndex.STEP_COUNT: value = (TextView) view.findViewById(R.id.stepCountValue);
                    break;
                case SectionsPagerAdapter.FragmentIndex.DISTANCE_COVERED: value = (TextView) view.findViewById(R.id.distanceCoveredValue);
                    break;
                case SectionsPagerAdapter.FragmentIndex.CALORIES_EXPENDED: value = (TextView) view.findViewById(R.id.caloriesValue);
                    break;
                default: value = null;
            }

            if(value != null) {
                progressBar = (ProgressBar) view.findViewById(R.id.circularProgressBar);
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            int waitCounter = 1;
            while(history == null && waitCounter++ < 20) {
                try {
                    //Log.d(TAG, "Waiting for history to initialize...");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Log.e(TAG, "Exception ", e);
                }
            }

            if(history == null || value == null) {
                return false;
            }

            //Log.d(TAG, "History initialized... " + fragmentId);

            DataReadRequest readRequest;

            switch (fragmentId) {
                case SectionsPagerAdapter.FragmentIndex.STEP_COUNT: readRequest = getStepCountDataReadRequest();
                    break;
                case SectionsPagerAdapter.FragmentIndex.DISTANCE_COVERED: readRequest = getDistanceCoveredDataReadRequest();
                    break;
                case SectionsPagerAdapter.FragmentIndex.CALORIES_EXPENDED: readRequest = getCalorieExpendedDataReadRequest();
                    break;
                default: readRequest = null;
            }
            history.readDataReadRequest(readRequest, new ResultCallback<DataReadResult>() {
                @Override
                public void onResult(DataReadResult dataReadResult) {
                    double readFieldValue = 0l;
                    if (dataReadResult.getBuckets().size() > 0) {
                        for (Bucket bucket : dataReadResult.getBuckets()) {
                            List<DataSet> dataSets = bucket.getDataSets();
                            for (DataSet dataSet : dataSets) {
                                if (!dataSet.getDataPoints().isEmpty())
                                    //display.show("dataSet.dataType: " + dataSet.getDataType().getName());

                                    for (DataPoint dp : dataSet.getDataPoints()) {
                                        for(Field field : dp.getDataType().getFields()) {
                                            if(dp.getValue(field) != null) {
                                                readFieldValue += Double.valueOf(dp.getValue(field).toString());
                                            }
                                        }
                                    }
                            }
                        }
                    }

                    final double fieldValue = readFieldValue;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            switch (fragmentId) {
                                case SectionsPagerAdapter.FragmentIndex.STEP_COUNT:
                                    value.setText("" + (Double.valueOf(fieldValue).longValue()));
                                    long reqStepsCount = recommendation != null ? recommendation.getStepsCount() : (Double.valueOf(fieldValue).longValue());
                                    finalProgress = (int) ((Double.valueOf(fieldValue).intValue()) * 100 / reqStepsCount);
                                    value.setText(value.getText() + "/" + reqStepsCount);
                                    break;
                                case SectionsPagerAdapter.FragmentIndex.DISTANCE_COVERED:
                                    value.setText("" + df.format(fieldValue));
                                    Double reqDistanceCovered = recommendation != null ? recommendation.getDistanceInMtr() : (Double.valueOf(fieldValue).doubleValue());
                                    finalProgress = (int) ((Double.valueOf(fieldValue).doubleValue()) * 100 / reqDistanceCovered);
                                    value.setText(value.getText() + "/" + reqDistanceCovered.longValue());
                                    break;
                                case SectionsPagerAdapter.FragmentIndex.CALORIES_EXPENDED:
                                    value.setText("" + (Double.valueOf(fieldValue).longValue()));
                                    long reqCalorieConsume = recommendation != null ? recommendation.getCalories() : (Double.valueOf(fieldValue).longValue());
                                    finalProgress = (int) ((Double.valueOf(fieldValue).longValue()) * 100 / reqCalorieConsume);
                                    value.setText(value.getText() + "/" + reqCalorieConsume);
                                    break;
                            }
                            Log.d(TAG, "Value: " + value.getText() + " - Progress: " + finalProgress);
                            mSectionsPagerAdapter.notifyDataSetChanged();
                            setupTabIcons();
                            ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, finalProgress);
                            progressAnimator.setDuration(2000);
                            progressAnimator.setInterpolator(new DecelerateInterpolator());
                            progressAnimator.start();
                        }
                    }, 2000);

                    //Log.d(TAG, "Total Steps: " + fieldValue);
                }
            });
            return true;
        }

        private DataReadRequest getStepCountDataReadRequest() {
            return new DataReadRequest.Builder()
                    .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                    .bucketByTime(1, TimeUnit.DAYS)
                    .setTimeRange(startTime.getTimeInMillis(), endTime.getTimeInMillis(), TimeUnit.MILLISECONDS)
                    .build();
        }

        private DataReadRequest getDistanceCoveredDataReadRequest() {
            return new DataReadRequest.Builder()
                    .aggregate(DataType.TYPE_DISTANCE_DELTA, DataType.AGGREGATE_DISTANCE_DELTA)
                    .bucketByTime(1, TimeUnit.DAYS)
                    .setTimeRange(startTime.getTimeInMillis(), endTime.getTimeInMillis(), TimeUnit.MILLISECONDS)
                    .build();
        }

        private DataReadRequest getCalorieExpendedDataReadRequest() {
            return new DataReadRequest.Builder()
                    .aggregate(DataType.TYPE_CALORIES_EXPENDED, DataType.AGGREGATE_CALORIES_EXPENDED)
                    .bucketByTime(1, TimeUnit.DAYS)
                    .setTimeRange(startTime.getTimeInMillis(), endTime.getTimeInMillis(), TimeUnit.MILLISECONDS)
                    .build();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(!success) {
                Log.e(TAG, "History was not initialized....");
            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}

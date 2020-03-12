package eb.egonb.hellohandlerandtask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import eb.egonb.hellohandlerandtask.util.ProgressHandler;
import eb.egonb.hellohandlerandtask.util.ProgressTask;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private ProgressBar pb;
    private Button btn, btnStartTask;

    private boolean isRunning = false;
    private ProgressHandler mHandler;

    private View.OnClickListener startListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!isRunning){
                startThread();
            }
        }
    };

    private View.OnClickListener startTaskListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ProgressTask task = new ProgressTask(pb, btnStartTask);
            task.execute();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv_result);
        pb = findViewById(R.id.pb_progress);
        btn = findViewById(R.id.btn_start);
        btnStartTask = findViewById(R.id.btn_start_task);

        mHandler = new ProgressHandler(pb, tv);
        btn.setOnClickListener(startListener);
        btnStartTask.setOnClickListener(startTaskListener);
    }

    private void startThread(){
        Thread bgThread = new Thread(new Runnable() {
            @Override
            public void run() {
                isRunning = true;
                for(int i = 0; i < 100; i++){

                    //NIET LEGIT GEBRUIKEN
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = new Message();
                    msg.arg1 = i;

                    mHandler.sendMessage(msg);
                }
                isRunning = false;
            }
        });
        bgThread.start();
    }
}

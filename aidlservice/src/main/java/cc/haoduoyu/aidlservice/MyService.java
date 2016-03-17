package cc.haoduoyu.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cc.haoduoyu.demoapp.aidl.IMyService;
import cc.haoduoyu.demoapp.aidl.Student;

/**
 * Created by XP on 2016/3/17.
 */
public class MyService extends Service {

    private static final String PACKAGE = "cc.haoduoyu.demoapp";

    private boolean mCanRun = true;
    private List<Student> mStudents = new ArrayList<>();

    private final IMyService.Stub mBinder = new IMyService.Stub() {
        @Override
        public List<Student> getStudent() throws RemoteException {
            synchronized (mStudents) {
                return mStudents;
            }
        }

        @Override
        public void addStudent(Student student) throws RemoteException {
            synchronized (mStudents) {
                if (!mStudents.contains(student)) {
                    mStudents.add(student);
                }
            }
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String packageName = null;
            String[] packages = MyService.this.getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
                Log.d("MyService: ", packageName);
            }
            Log.d("MyService", "onTransact: " + packageName);
            if (!PACKAGE.equals(packageName)) {
                return false;
            }

            return super.onTransact(code, data, reply, flags);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        Thread thread = new Thread(null, new ServiceWorker(), "BackgroundService");
        thread.start();

        synchronized (mStudents) {
            for (int i = 1; i < 6; i++) {
                Student student = new Student();
                student.name = "student#" + i;
                student.age = i * 5;
                mStudents.add(student);
                Log.d("MyService", "add " + i);
            }
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MyService", String.format("on bind,intent = %s", intent.toString()));
        Toast.makeText(getApplicationContext(), "服务已启动", Toast.LENGTH_SHORT).show();
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mCanRun = false;
        super.onDestroy();
    }

    class ServiceWorker implements Runnable {
        long counter = 0;

        @Override
        public void run() {
            // do background processing here.....
            while (mCanRun) {
                Log.d("scott", "" + counter);
                counter++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

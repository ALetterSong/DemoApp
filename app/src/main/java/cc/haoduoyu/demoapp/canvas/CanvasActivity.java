package cc.haoduoyu.demoapp.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by XP on 2016/1/30.
 */
public class CanvasActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CanvasView view = new CanvasView(this);
        setContentView(view);
    }


    class CanvasView extends View {

        RectF mArcRectF;
        RectF mOvalRectF;
        RectF mRectF;
        RectF mRectFA;


        Paint mPointPaint;
        Paint mLinePaint;
        Paint mCirclePaint;
        Paint mArcPaint;
        Paint mRectPaint;
        Paint mRectPaintA;
        Paint mOvalPaint;
        Paint mTextPaint;

        public CanvasView(Context context) {
            super(context);
            initPaint();
        }

        private void initPaint() {

            mPointPaint = new Paint();
            mPointPaint.setColor(Color.RED);
            mPointPaint.setStrokeWidth(30);

            mLinePaint = new Paint();
            mLinePaint.setColor(Color.YELLOW);
            mLinePaint.setStrokeWidth(10);

            mCirclePaint = new Paint();
            mCirclePaint.setColor(Color.GRAY);

            mArcPaint = new Paint();
            mArcPaint.setColor(Color.GREEN);

            mRectPaint = new Paint();
            mRectPaint.setAntiAlias(false);
            mRectPaint.setColor(Color.MAGENTA);

            mRectPaintA = new Paint();
            mRectPaintA.setAntiAlias(true);//抗锯齿
            mRectPaintA.setColor(Color.MAGENTA);

            mOvalPaint = new Paint();

            mTextPaint = new Paint();
            mTextPaint.setTextSize(60);

            mArcRectF = new RectF(0, 30, 300, 300);
            mOvalRectF = new RectF(0, 100, 300, 500);
            mRectF = new RectF(0, 50, 100, 150);
            mRectFA = new RectF(50, 190, 100, 220);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.save();//保存Canvas的状态

            canvas.drawARGB(58, 68, 78, 88);

            canvas.drawPoint(600, 500, mPointPaint);//RED

            canvas.drawLine(0, 0, 0, 100, mLinePaint);//YELLOW
            canvas.drawLine(100, 0, 0, 0, mLinePaint);

            canvas.drawCircle(50, 200, 50, mCirclePaint);//GRAY
            canvas.drawCircle(300, 300, 100, mCirclePaint);

            canvas.translate(200, 320);//GREEN(0, 30, 300, 300)
            canvas.drawArc(mArcRectF, 90, 120, true, mArcPaint);
            canvas.drawArc(mArcRectF, 0, 90, false, mArcPaint);//是否使用圆心

            canvas.translate(200, 350);//MAGENTA
            canvas.drawRect(mRectF, mRectPaint);//(0, 50, 100, 150)
            canvas.drawRect(mRectFA, mRectPaintA);//(50, 190, 100, 220)

            canvas.translate(300, 350);//ltrb(0, 100, 300, 500)
            canvas.drawOval(mOvalRectF, mOvalPaint);

            canvas.restore();//恢复Canvas保存的状态

            //start: The index of the first character in text to draw
            //end: (end - 1) is the index of the last character in text to draw
            canvas.drawText("Canvas", 1, 5, 100, 100, mTextPaint);
            canvas.drawText("Canvas真有趣", 20, 60, mTextPaint);

        }
    }

}

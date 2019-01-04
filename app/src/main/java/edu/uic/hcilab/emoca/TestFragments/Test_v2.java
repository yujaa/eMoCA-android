package edu.uic.hcilab.emoca.TestFragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import edu.uic.hcilab.emoca.AudioPlayer;
import edu.uic.hcilab.emoca.R;
import edu.uic.hcilab.emoca.WriteSDcard;

public class Test_v2 extends android.app.Fragment {

    DrawingView dv ;
    AudioPlayer ap;
    WriteSDcard wr;
    private Paint mPaint;

    public Test_v2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ap = new AudioPlayer(this.getActivity().getApplicationContext());
        wr = new WriteSDcard();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_v2, container, false);
        dv = new DrawingView(getActivity());
        FrameLayout v = (FrameLayout) view.findViewById(R.id.v2_canvas);
        v.addView(dv);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(8);

        //audio
        View[] viewArr = {view.findViewById(R.id.v2_rect)};
        int[] startTimeArr = {500};
        int[] endTimeArr = {1500};
        ap.audioAnimationPlayer(R.raw.v2, viewArr, startTimeArr, endTimeArr);
        wr.writeToSDFile(this.getContext(), "V2:audio start");

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ap.stop();
    }

    public class DrawingView extends View {

        public int width;
        public  int height;
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;

        public DrawingView(Context c) {
            super(c);
            context=c;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.BLUE);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath( mPath,  mPaint);
            canvas.drawPath( circlePath,  circlePaint);
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y, int tool, float pressure) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
            wr.writeToSDFile(this.getContext(), "V2:touch down:"+tool+":"+pressure+":"+mX+","+mY);
        }

        private void touch_move(float x, float y, int tool, float pressure) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                mX = x;
                mY = y;

                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
                wr.writeToSDFile(this.getContext(), "V2:touch move:"+tool+":"+pressure+":"+mX+","+mY);
            }
        }

        private void touch_up(int tool, float pressure) {
            mPath.lineTo(mX, mY);
            circlePath.reset();
            // commit the path to our offscreen
            mCanvas.drawPath(mPath,  mPaint);

            // kill this so we don't double draw
            mPath.reset();
            wr.writeToSDFile(this.getContext(), "V2:touch up:"+tool+":"+pressure+":"+mX+","+mY);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y, event.getToolType(0), event.getPressure(0));
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y, event.getToolType(0), event.getPressure(0));
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up(event.getToolType(0), event.getPressure(0));
                    invalidate();
                    break;
            }
            return true;
        }
    }
}

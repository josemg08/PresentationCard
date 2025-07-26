package com.example.presentationcard.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class FlappyBirdView extends View {
    // Bird properties
    private float birdX, birdY;
    private float birdRadius = 40f;
    private float birdVelocity = 0f;
    private final float gravity = 1.2f;
    private final float flapPower = -18f;
    private Paint birdPaint;

    // Pipe properties
    private static class Pipe {
        float x;
        float gapY;
        static final float width = 120f;
        static final float gapHeight = 350f;
        Pipe(float x, float gapY) {
            this.x = x;
            this.gapY = gapY;
        }
    }
    private List<Pipe> pipes = new ArrayList<>();
    private final float pipeSpeed = 8f;
    private final int pipeInterval = 1200; // ms
    private long lastPipeTime = 0;
    private Paint pipePaint;
    private Paint pipeBorderPaint;
    private Random random = new Random();

    // Game state
    private boolean gameOver = false;
    private boolean started = false;
    private int score = 0;
    private Paint scorePaint;

    // Handler for game loop
    private Handler handler = new Handler(Looper.getMainLooper());
    private final int frameRate = 1000 / 60; // 60 FPS
    private Runnable gameLoop = new Runnable() {
        @Override
        public void run() {
            update();
            invalidate();
            handler.postDelayed(this, frameRate);
        }
    };

    public FlappyBirdView(Context context) {
        super(context);
        init();
    }
    public FlappyBirdView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public FlappyBirdView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        birdPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        birdPaint.setColor(Color.YELLOW);
        birdPaint.setStyle(Paint.Style.FILL);

        pipePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pipePaint.setColor(Color.rgb(76, 175, 80)); // Green
        pipePaint.setStyle(Paint.Style.FILL);

        pipeBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pipeBorderPaint.setColor(Color.rgb(56, 142, 60)); // Darker green
        pipeBorderPaint.setStyle(Paint.Style.STROKE);
        pipeBorderPaint.setStrokeWidth(8f);

        scorePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(100f);
        scorePaint.setTextAlign(Paint.Align.CENTER);
        setFocusable(true);
        setClickable(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        resetGame();
    }

    private void resetGame() {
        handler.removeCallbacks(gameLoop); // Stop any running game loop
        birdX = getWidth() / 3f;
        birdY = getHeight() / 2f;
        birdVelocity = 0f; // Always reset velocity
        pipes.clear();
        lastPipeTime = System.currentTimeMillis();
        score = 0;
        gameOver = false;
        started = false;
        invalidate();
    }

    private void update() {
        if (!started || gameOver) {
            handler.removeCallbacks(gameLoop); // Stop loop if not running
            return;
        }
        // Bird physics
        birdVelocity += gravity;
        birdY += birdVelocity;
        // Pipes
        long now = System.currentTimeMillis();
        if (now - lastPipeTime > pipeInterval) {
            float gapY = birdRadius * 2 + random.nextInt((int)(getHeight() - Pipe.gapHeight - birdRadius * 4));
            pipes.add(new Pipe(getWidth(), gapY));
            lastPipeTime = now;
        }
        Iterator<Pipe> it = pipes.iterator();
        while (it.hasNext()) {
            Pipe pipe = it.next();
            pipe.x -= pipeSpeed;
            // Remove off-screen pipes
            if (pipe.x + Pipe.width < 0) {
                it.remove();
                score++;
            }
        }
        // Collision detection
        for (Pipe pipe : pipes) {
            // Top pipe
            RectF topRect = new RectF(pipe.x, 0, pipe.x + Pipe.width, pipe.gapY);
            // Bottom pipe
            RectF bottomRect = new RectF(pipe.x, pipe.gapY + Pipe.gapHeight, pipe.x + Pipe.width, getHeight());
            if (circleIntersectsRect(birdX, birdY, birdRadius, topRect) || circleIntersectsRect(birdX, birdY, birdRadius, bottomRect)) {
                gameOver = true;
            }
        }
        // Out of bounds
        if (birdY - birdRadius < 0 || birdY + birdRadius > getHeight()) {
            gameOver = true;
        }
    }

    private boolean circleIntersectsRect(float cx, float cy, float radius, RectF rect) {
        float closestX = Math.max(rect.left, Math.min(cx, rect.right));
        float closestY = Math.max(rect.top, Math.min(cy, rect.bottom));
        float dx = cx - closestX;
        float dy = cy - closestY;
        return (dx * dx + dy * dy) < (radius * radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Background
        canvas.drawColor(Color.rgb(135, 206, 250)); // Sky blue
        // Draw pipes
        for (Pipe pipe : pipes) {
            // Top pipe
            RectF topRect = new RectF(pipe.x, 0, pipe.x + Pipe.width, pipe.gapY);
            canvas.drawRect(topRect, pipePaint);
            canvas.drawRect(topRect, pipeBorderPaint);
            // Bottom pipe
            RectF bottomRect = new RectF(pipe.x, pipe.gapY + Pipe.gapHeight, pipe.x + Pipe.width, getHeight());
            canvas.drawRect(bottomRect, pipePaint);
            canvas.drawRect(bottomRect, pipeBorderPaint);
        }
        // Draw bird
        canvas.drawCircle(birdX, birdY, birdRadius, birdPaint);
        // Draw score
        canvas.drawText(String.valueOf(score), getWidth() / 2f, 120f, scorePaint);
        // Draw game over
        if (gameOver) {
            Paint overPaint = new Paint(scorePaint);
            overPaint.setColor(Color.RED);
            overPaint.setTextSize(80f);
            canvas.drawText("Game Over! Tap to restart", getWidth() / 2f, getHeight() / 2f, overPaint);
        } else if (!started) {
            Paint startPaint = new Paint(scorePaint);
            startPaint.setColor(Color.DKGRAY);
            startPaint.setTextSize(70f);
            canvas.drawText("Tap to start", getWidth() / 2f, getHeight() / 2f, startPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!started) {
                started = true;
                birdVelocity = 0f; // Ensure velocity is 0 at start
                handler.removeCallbacks(gameLoop); // Prevent multiple loops
                handler.post(gameLoop);
            } else if (gameOver) {
                resetGame();
            } else {
                birdVelocity = flapPower;
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(gameLoop);
    }
}

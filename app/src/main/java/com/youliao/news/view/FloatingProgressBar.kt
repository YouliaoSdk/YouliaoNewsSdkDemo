package com.youliao.news.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.youliao.news.R
import kotlin.math.min

class FloatingProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    internal companion object {
        private var ONE_DURATION = 4000
        private var DEFAULT_PROGRESS_COLOR = Color.BLUE
        private var DEFAULT_BACKGROUND_COLOR = Color.GRAY

        /*circle_progress member*/
        var DEFAULT_STROKE_WIDTH = 0
    }

    /*common member*/
    private var mWidth = 0
    private var mHeight = 0
    private var mProgressColor = 0
    private var mBackgroundColor = 0
    var isAnimating = false
    var maxValue = 1000
    var mValue = 0
    private lateinit var mAnimator: ValueAnimator
    private val mBackgroundPaint = Paint()
    private val mPaint = Paint()
    private val mArcOval = RectF()
    private var mStrokeWidth = 0
    private var mCircleRadius = 0
    private lateinit var mCenterPoint: Point

    /**
     * 开启动画
     */
    var isOpenAnim = true

    init {
        DEFAULT_STROKE_WIDTH = FloatingUtils.dp2px(4f)
        val array =
            context.obtainStyledAttributes(attrs, R.styleable.FloatingProgressBar)
        mProgressColor = array.getColor(
            R.styleable.FloatingProgressBar_progress_color,
            DEFAULT_PROGRESS_COLOR
        )
        mBackgroundColor = array.getColor(
            R.styleable.FloatingProgressBar_progress_background_color,
            DEFAULT_BACKGROUND_COLOR
        )
        mStrokeWidth = array.getDimensionPixelSize(
            R.styleable.FloatingProgressBar_progress_stroke_width,
            DEFAULT_STROKE_WIDTH
        )
        array.recycle()
        configPaint()
    }

    private fun configShape() {
        mCircleRadius = (min(mWidth, mHeight) - mStrokeWidth - 1) / 2
        mCenterPoint = Point(mWidth / 2, mHeight / 2)
    }

    private fun configPaint() {
        mPaint.color = mProgressColor
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mStrokeWidth.toFloat()
        mPaint.isAntiAlias = true
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.shader = null

        mBackgroundPaint.color = mBackgroundColor
        mBackgroundPaint.style = Paint.Style.STROKE
        mBackgroundPaint.strokeWidth = mStrokeWidth.toFloat()
        mBackgroundPaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        drawCircle(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth - paddingLeft - paddingRight
        mHeight = measuredHeight - paddingTop - paddingBottom
        configShape()
        setMeasuredDimension(mWidth, mHeight)
    }

    private fun drawCircle(canvas: Canvas) {
        canvas.drawCircle(
            mCenterPoint.x.toFloat(), mCenterPoint.y.toFloat(),
            mCircleRadius.toFloat(), mBackgroundPaint
        )
        mArcOval.left = mCenterPoint.x - mCircleRadius.toFloat()
        mArcOval.right = mCenterPoint.x + mCircleRadius.toFloat()
        mArcOval.top = mCenterPoint.y - mCircleRadius.toFloat()
        mArcOval.bottom = mCenterPoint.y + mCircleRadius.toFloat()
        canvas.drawArc(mArcOval, 270f, 360 * mValue / maxValue.toFloat(), false, mPaint)
    }

    fun setProgress(progress: Int, animated: Boolean) {
        if (progress > maxValue || progress < 0) {
            return
        }
        if (isAnimating) {
            isAnimating = false
            mAnimator.cancel()
        }
        val oldValue = mValue
        mValue = progress / 100 * maxValue
        if (animated) {
            startProgressAnimation(oldValue, progress)
        } else {
            invalidate()
        }
    }

    private fun startProgressAnimation(start: Int, end: Int) {
        mAnimator = ValueAnimator.ofInt(start, end)
        mAnimator.duration = ONE_DURATION.toLong()
        //匀速插值器
        mAnimator.interpolator = LinearInterpolator()
        mAnimator.addUpdateListener { animation ->
            mValue = animation.animatedValue as Int
            invalidate()
            if (mValue == maxValue) {
                if (progressFinishListener != null && isOpenAnim) {
                    isOpenAnim = false
                    progressFinishListener?.onFinishListener(true)
                }
            } else if (mValue == end) {
                if (progressFinishListener != null && isOpenAnim) {
                    progressFinishListener?.onFinishListener(false)
                }
            }
        }
        mAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                isAnimating = true
            }

            override fun onAnimationEnd(animation: Animator) {
                isAnimating = false
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        mAnimator.start()
    }

    fun setOneDuration(totalDuration: Int) {
        ONE_DURATION = totalDuration
    }

    /**
     * 结束监听接口
     */
    private var progressFinishListener: ProgressFinishListener? = null
    fun setProgressFinishListener(finishListener: ProgressFinishListener?) {
        this.progressFinishListener = finishListener
    }

    interface ProgressFinishListener {
        /**
         * 结束监听
         */
        fun onFinishListener(state: Boolean)
    }
}
package com.youliao.news.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout
import com.youliao.news.R

class RoundFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var mPath: Path
    private var mPaint: Paint
    private var mRectF: RectF
    private var mRadius = 0f

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundFrameLayout)
        mRadius = typedArray.getDimension(R.styleable.RoundFrameLayout_radius, 0f)
        typedArray.recycle()
        mPath = Path()
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mRectF = RectF()
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRectF.set(0f, 0f, w.toFloat(), h.toFloat())
    }

    override fun dispatchDraw(canvas: Canvas) {
        if (Build.VERSION.SDK_INT >= 28) {
            canvas.save()
            canvas.clipPath(genPath())
            super.dispatchDraw(canvas)
            canvas.restore()
        } else {
            canvas.saveLayer(mRectF, null, Canvas.ALL_SAVE_FLAG)
            super.dispatchDraw(canvas)
            canvas.drawPath(genPath(), mPaint)
            canvas.restore()
        }
    }

    private fun genPath(): Path {
        mPath.reset()
        mPath.addRoundRect(mRectF, mRadius, mRadius, Path.Direction.CW)
        return mPath
    }
}
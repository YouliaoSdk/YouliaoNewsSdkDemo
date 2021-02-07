package com.youliao.news.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.youliao.news.R
import com.youliao.news.view.FloatingProgressBar.ProgressFinishListener
import kotlin.math.ceil

class FloatingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private var screenWidth = 0
    private var screenHeight = 0
    private var screenWidthHalf = 0
    private var circleImageView: ImageView
    var progressBar: FloatingProgressBar

    /**
     * 积分文本
     */
    private var mIntegral: TextView

    /**
     * 动画时间
     */
    private var lastX = 0
    private var lastY = 0
    private var isDrag = false
    private var floatProgressState = false

    init {
        LayoutInflater.from(context).inflate(R.layout.floating_button_layout, this, true)
        progressBar = findViewById(R.id.float_progress_bar)
        circleImageView = findViewById(R.id.float_progress_icon)
        mIntegral = findViewById(R.id.floating_integral)
        val display = FloatingUtils.getDisplayMetrics()
        screenWidth = display.widthPixels
        screenWidthHalf = screenWidth / 2
        screenHeight = display.heightPixels
        this.setOnClickListener(this)
        initListener()
    }

    /**
     * 事件监听
     */
    private fun initListener() {
        progressBar.setProgressFinishListener(object : ProgressFinishListener {
            override fun onFinishListener(state: Boolean) {
                if (state) {
                    finishAnimation()
                    finishListener?.onFinishListener()
                } else {
                    if (floatProgressState) {
                        setProgress()
                    }
                }
            }
        })
    }

    /**
     * 结束动画效果
     */
    private fun finishAnimation() {
        //1、中间图片向下平移 2、中间积分由小到大显示出现来，然后积分由大到小消失，中间图片向上平移出现
        val translationY = circleImageView.translationY
        val startAnimator =
            ObjectAnimator.ofFloat(circleImageView, "translationY", translationY, 140f)
        startAnimator.duration = 500
        startAnimator.start()
        startAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                mIntegral.visibility = View.VISIBLE
                val objectAnimator1 =
                    ObjectAnimator.ofFloat(
                        mIntegral, "scaleX", 0f, 1f, 1f, 1f, 1f, 1f, 0f
                    )
                val objectAnimator2 =
                    ObjectAnimator.ofFloat(
                        mIntegral, "scaleY", 0f, 1f, 1f, 1f, 1f, 1f, 0f
                    )
                //动画集合
                val set = AnimatorSet()
                //添加动画
                set.play(objectAnimator1).with(objectAnimator2)
                //设置时间等
                set.duration = 2000
                set.start()
                set.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        progressBar.isOpenAnim = true
                        progressBar.setProgress(0, false)
                        val translationY = circleImageView.translationY
                        val translationYAnimator =
                            ObjectAnimator.ofFloat(
                                circleImageView, "translationY", translationY, 0f
                            )
                        translationYAnimator.duration = 800
                        translationYAnimator.start()
                        translationYAnimator.addListener(finishAnimator)
                    }
                })
            }
        })
    }

    val finishAnimator = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            if (this@FloatingButton.isShown)
                this@FloatingButton.visibility = View.GONE
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val rawX = event.rawX.toInt()
        val rawY = event.rawY.toInt()
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                isDrag = false
                lastX = rawX
                lastY = rawY
            }
            MotionEvent.ACTION_MOVE -> {
                isDrag = true
                //计算手指移动了多少
                val dx = rawX - lastX
                val dy = rawY - lastY
                val x = x + dx
                val y = y + dy
                setX(x)
                setY(y)
                lastX = rawX
                lastY = rawY
            }
            MotionEvent.ACTION_UP -> if (isDrag) {
                //恢复按压效果
                isPressed = false
                val oaX: ObjectAnimator = if (rawX >= screenWidthHalf) {
                    ObjectAnimator.ofFloat(this, "x", x, screenWidth - width.toFloat() - 20f)
                } else {
                    ObjectAnimator.ofFloat(this, "x", x, 20f)
                }
                oaX.interpolator = OvershootInterpolator()
                oaX.duration = 500
                oaX.start()
                if (rawY >= (screenHeight - height)) {
                    val oaY = ObjectAnimator.ofFloat(
                        this, "y", y, (screenHeight - height.toFloat())
                    )
                    oaY.interpolator = OvershootInterpolator()
                    oaY.duration = 500
                    oaY.start()
                }
                if (220f >= (y - height)) {
                    val oaY = ObjectAnimator.ofFloat(this, "y", y, 220f)
                    oaY.interpolator = OvershootInterpolator()
                    oaY.duration = 500
                    oaY.start()
                }

            }
        }
        //如果是拖拽则消耗事件，否则正常传递即可。
        return isDrag || super.onTouchEvent(event)
    }

    /**
     * 设置当前进度
     * @param progress
     * @param animated 是否
     */
    fun setCurrentProgress(progress: Int, animated: Boolean = false) {
        progressBar.setProgress(progress, animated)
    }

    val currentPosition: Int
        get() = progressBar.mValue

    /**
     * 获取进度条是否在滚动中
     * @return
     */
    val isProgressAnimating: Boolean
        get() = progressBar.isAnimating

    /**
     * 进度完成监听接口
     */
    private var finishListener: FinishListener? = null
    fun setFinishListener(finishListener: FinishListener?) {
        this.finishListener = finishListener
    }

    fun scrollDetected() {
        // 1. 当进度动画还在进行中时，设置floatProgressState = true，以便动画结束时直接进行下一阶段动画
        // 2. 当没有动画时（进度停止时），开始下一阶段的动画
        if (!floatProgressState) {
            floatProgressState = true
            if (!isProgressAnimating) {
                setProgress()
            }
        }
    }

    /**
     * 设置环形进度
     */
    fun setProgress() {
        floatProgressState = false
        //3、每次进度的大小
        val size = (ceil((progressBar.maxValue.toFloat() / 5))).toInt()
        var progress = currentPosition + size
        if (progress > progressBar.maxValue) {
            progress = progressBar.maxValue
        }
        //设置当前进度
        setCurrentProgress(progress, true)
    }

    override fun onClick(v: View) {
    }
}

interface FinishListener {
    /**
     * 进度完成监听
     */
    fun onFinishListener()
}
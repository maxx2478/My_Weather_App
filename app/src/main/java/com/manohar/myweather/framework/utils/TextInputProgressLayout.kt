package com.manohar.myweather.framework.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.DynamicDrawableSpan
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.material.textfield.TextInputLayout
import com.manohar.myweather.R

class TextInputProgressLayout : TextInputLayout {

    private var circularProgressDrawable: CircularProgressDrawable? = null
    private var isShowingProgress = false

    private var isRetryButtonShown = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    fun init() {
        circularProgressDrawable =
            CircularProgressDrawable(context).mutate() as CircularProgressDrawable
        circularProgressDrawable!!.setColorSchemeColors(getColorByAttrId(R.attr.colorPrimary))
        circularProgressDrawable!!.strokeCap = Paint.Cap.ROUND
        circularProgressDrawable!!.setStyle(CircularProgressDrawable.DEFAULT)
        val size =
            (circularProgressDrawable!!.centerRadius + circularProgressDrawable!!.strokeWidth).toInt() * 2
        circularProgressDrawable!!.setBounds(0, 0, size, size)
    }

    private var prevDrawable: Drawable? = null
    private var prevIconMode: Int? = null

    fun showProgress(show: Boolean?) {

        if (show == null) {
            return
        }

        if (show == isShowingProgress) {
            Log.i(TAG, "showProgress: same state returning back")
            return
        }


        Log.i(TAG, "showProgress: $show")

        isShowingProgress = show

        if (show == true) {

            prevDrawable = endIconDrawable
            prevIconMode = endIconMode
            val progressSpanString = SpannableString(" ")
            val drawableSpan: DynamicDrawableSpan = object : DynamicDrawableSpan() {
                override fun getDrawable(): Drawable {
                    return circularProgressDrawable!!
                }
            }
            circularProgressDrawable!!.callback = progressCallback
            circularProgressDrawable!!.start()
            progressSpanString.setSpan(drawableSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            endIconDrawable = circularProgressDrawable
            endIconMode = END_ICON_CUSTOM

        } else {
            circularProgressDrawable!!.stop()
            circularProgressDrawable!!.callback = null
            prevIconMode?.let {
                endIconMode = it
            }
            prevDrawable?.let {
                endIconDrawable = it
            }
        }
    }


    fun showRetryButton(show: Boolean?) {

        if (show == null) {
            return
        }

        if (show == isRetryButtonShown) {
            Log.i(TAG, "showRetryButton: same retry state returning back")
            return
        }

        isRetryButtonShown = show

        Log.i(TAG, "showRetryButton: $show")

        if (show == true) {

            prevIconMode = endIconMode
            prevDrawable = endIconDrawable

            endIconMode = END_ICON_CUSTOM
            endIconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_launcher_background)

        } else {
            prevIconMode?.let {
                endIconMode = it
                endIconDrawable = prevDrawable
            }
        }
    }


    /**
     * This callback is use to call material button onDraw() method by calling invalidate() of MaterialButton
     * inside invalidateDrawable() method of progress drawable
     */
    private val progressCallback: Drawable.Callback = object : Drawable.Callback {
        override fun invalidateDrawable(who: Drawable) {
            this@TextInputProgressLayout.invalidate()
        }

        override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {}
        override fun unscheduleDrawable(who: Drawable, what: Runnable) {}
    }


    private fun getColorByAttrId(attrId: Int): Int {
        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(attrId, typedValue, true)
        return typedValue.data
    }

    companion object {
        private val TAG = TextInputProgressLayout::class.java.simpleName

        @JvmStatic
        @BindingAdapter("app:showInputLayoutProgress")
        fun showProgress(
            inputLayout: TextInputProgressLayout,
            show: Boolean
        ) {
            inputLayout.showProgress(show)
        }

        @JvmStatic
        @BindingAdapter(
            value = ["app:showInputLayoutRetryButton", "app:onInputLayoutRetryClick"],
            requireAll = false
        )
        fun showRetryButton(
            inputLayout: TextInputProgressLayout,
            show: Boolean,
            onInputLayoutRetryClick: OnRetryClickListener?
        ) {
            inputLayout.showRetryButton(show)
            if (show) {
                inputLayout.setEndIconOnClickListener {
                    Log.i(TAG, "onRetryClick: ")
                    onInputLayoutRetryClick?.onRetryClick()
                }
            } else {
                inputLayout.setEndIconOnClickListener(null)
                Log.i(TAG, "onRetryClick: retry button not shown, not attaching click listener")
            }
        }


        interface OnRetryClickListener {
            fun onRetryClick()
        }

    }
}
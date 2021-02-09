package project.xinyuan.sales.helper

import android.text.Editable
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.Log
import android.widget.EditText
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.util.*

class NumberTextWatcher(et: EditText, locale: Locale?, numDecimals: Int) : TextWatcher {
    private val numDecimals: Int
    private val groupingSep: String
    private val decimalSep: String
    private val nonUsFormat: Boolean
    private val df: DecimalFormat
    private val dfnd: DecimalFormat
    private var hasFractionalPart: Boolean
    private val et: EditText
    private var value: String?
    private fun replicate(ch: Char, n: Int): String {
        return String(CharArray(n)).replace("\u0000", "" + ch)
    }

    override fun afterTextChanged(s: Editable) {
        Log.d(TAG, "afterTextChanged")
        et.removeTextChangedListener(this)
        try {
            val inilen: Int
            val endlen: Int
            inilen = et.text.length
            var v = value!!.replace(groupingSep, "")
            val n = df.parse(v)
            val cp = et.selectionStart
            if (hasFractionalPart) {
                val decPos = v.indexOf(decimalSep) + 1
                val decLen = v.length - decPos
                if (decLen > numDecimals) {
                    v = v.substring(0, decPos + numDecimals)
                }
                var trz = countTrailingZeros(v)
                val fmt = StringBuilder(df.format(n))
                while (trz-- > 0) {
                    fmt.append("0")
                }
                et.setText(fmt.toString())
            } else {
                et.setText(dfnd.format(n))
            }
            endlen = et.text.length
            val sel = cp + (endlen - inilen)
            if (sel > 0 && sel <= et.text.length) {
                et.setSelection(sel)
            } else {
                // place cursor at the end?
                et.setSelection(et.text.length - 1)
            }
        } catch (nfe: NumberFormatException) {
            // do nothing?
        } catch (nfe: ParseException) {
        }
        et.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        Log.d(TAG, "beforeTextChanged")
        value = et.text.toString()
    }

    private fun countTrailingZeros(str: String): Int {
        var count = 0
        for (i in str.length - 1 downTo 0) {
            val ch = str[i]
            if ('0' == ch) {
                count++
            } else {
                break
            }
        }
        return count
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        Log.d(TAG, "onTextChanged")
        val newValue = s.toString()
        var change = newValue.substring(start, start + count)
        val prefix = value!!.substring(0, start)
        val suffix = value!!.substring(start + before)
        if ("." == change && nonUsFormat) {
            change = decimalSep
        }
        value = prefix + change + suffix
        hasFractionalPart = value!!.contains(decimalSep)
        Log.d(TAG, "VALUE: $value")
    }

    companion object {
        private const val TAG = "NumberTextWatcher"
    }

    init {
        et.keyListener = DigitsKeyListener.getInstance("0123456789.,")
        this.numDecimals = numDecimals
        val symbols = DecimalFormatSymbols(locale)
        val gs = symbols.groupingSeparator
        val ds = symbols.decimalSeparator
        groupingSep = gs.toString()
        decimalSep = ds.toString()
        val patternInt = "#,###"
        dfnd = DecimalFormat(patternInt, symbols)
        val patternDec = patternInt + "." + replicate('#', numDecimals)
        df = DecimalFormat(patternDec, symbols)
        df.isDecimalSeparatorAlwaysShown = true
        df.roundingMode = RoundingMode.DOWN
        this.et = et
        hasFractionalPart = false
        nonUsFormat = decimalSep != "."
        value = null
    }
}
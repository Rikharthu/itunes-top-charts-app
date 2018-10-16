package com.rikharthu.itunestopcharts.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import com.rikharthu.itunestopcharts.R
import kotlinx.android.synthetic.main.dialog_track_count_chooser.view.*

class TrackCountDialog : DialogFragment() {

    var listener: OnTrackCountSelectedListener? = null

    private var count: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_track_count_chooser, container, false).apply {
            applyBtn.setOnClickListener {
                listener?.onTrackCountSelected(count)
                dismiss()
            }
            cancelBtn.setOnClickListener {
                listener?.onCancelled()
                dismiss()
            }
            trackCountSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    trackCountTv.text = progress.toString()
                    count = progress
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                    // Do nothing.
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                    // Do nothing.
                }
            })
        }
    }


    interface OnTrackCountSelectedListener {
        fun onTrackCountSelected(count: Int)

        fun onCancelled()
    }
}
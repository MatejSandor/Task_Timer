package com.sandor.tasktimer

import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

private const val TAG = "AppDialog"

class AppDialog : AppCompatDialogFragment() {

    internal interface DialogEvents {
        fun onPositiveDialogResult(dialogId: Int, args: Bundle)
        fun onNegativeDialogResult(dialogId: Int, args: Bundle)
        fun onDialogCancelled(dialogId: Int)
    }
    
}
package jp.zuikou.system.redditprojectsample1.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import jp.zuikou.system.redditprojectsample1.R
import jp.zuikou.system.redditprojectsample1.extension.load

class ImagePreviewDialogFragment: DialogFragment() {
    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(width, height)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.image_preview, container, false)
        val imagePreview: ImageView =  view.findViewById(R.id.imagePreview)
        if (arguments != null && arguments?.getString("imageUrl","")!!.isNotBlank()){
            imagePreview.load(arguments?.getString("imageUrl","")!!, 0, 0)
        }

        return view
    }
}
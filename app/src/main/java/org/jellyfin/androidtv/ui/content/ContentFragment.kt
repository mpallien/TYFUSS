package org.jellyfin.androidtv.ui.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.jellyfin.androidtv.R
import org.jellyfin.androidtv.ui.content.ContentAdapter.OnItemFocusListener
import org.jellyfin.sdk.model.api.BaseItemDto

class ContentFragment : Fragment() {

    private lateinit var adapter: ContentAdapter
    private lateinit var recyclerView: RecyclerView

    // preview panel
    private lateinit var previewPoster: ImageView
    private lateinit var previewDescription: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_content_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.content_recycler)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        previewPoster = view.findViewById(R.id.preview_poster)
        previewDescription = view.findViewById(R.id.preview_description)

        adapter = ContentAdapter(object : OnItemFocusListener {
            override fun onItemFocused(item: BaseItemDto) {
                updatePreview(item)
            }
        })

        recyclerView.adapter = adapter
    }

    private fun updatePreview(item: BaseItemDto) {
        // Load poster
        item.imageTags?.primary?.let { tag ->
            previewPoster.load(item.getImageUrl(tag)) {
                placeholder(R.drawable.default_video_poster)
                error(R.drawable.default_video_poster)
            }
        } ?: run {
            previewPoster.setImageResource(R.drawable.default_video_poster)
        }

        // Show description
        previewDescription.text = item.overview ?: ""
    }
}

package com.example.waterreminder.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.waterreminder.R
import com.example.waterreminder.data.local.model.HydrationTip
import com.bumptech.glide.Glide

class TipsAdapter(
    private val tips: List<HydrationTip>
) : RecyclerView.Adapter<TipsAdapter.TipsViewHolder>() {

    class TipsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.ivTipImage)
        val title: TextView = view.findViewById(R.id.tvTitle)
        val description: TextView = view.findViewById(R.id.tvDescription)
        val tipType: TextView = view.findViewById(R.id.tvTipType)
        val shareIcon: ImageView = view.findViewById(R.id.btnShare)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tips, parent, false)
        return TipsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TipsViewHolder, position: Int) {
        val tip = tips[position]

        holder.title.text = tip.title
        holder.description.text = tip.description
        holder.tipType.text = tip.tipType

        // Load image (optional - use default drawable)
        Glide.with(holder.itemView.context)
            .load(R.drawable.ic_water) // or load dynamic link from tip.link if it's an image
            .into(holder.image)

        // Share tip
        holder.shareIcon.setOnClickListener {
            val context = holder.itemView.context
            val shareText = "${tip.title}\n${tip.description}\n${tip.link ?: ""}"
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share tip via"))
        }
    }

    override fun getItemCount(): Int = tips.size
}

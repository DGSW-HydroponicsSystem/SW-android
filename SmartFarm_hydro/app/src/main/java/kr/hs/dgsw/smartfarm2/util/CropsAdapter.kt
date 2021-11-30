package kr.hs.dgsw.smartfarm2.util

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.hs.dgsw.smartfarm2.R
import kr.hs.dgsw.smartfarm2.databinding.CropsItemBinding
import kr.hs.dgsw.smartfarm2.network.Constants
import kr.hs.dgsw.smartfarm2.network.model.response.C
import kr.hs.dgsw.smartfarm2.network.model.response.Crops
import kr.hs.dgsw.smartfarm2.viewmodel.CropsViewModel

class CropsAdapter : RecyclerView.Adapter<CropsAdapter.ViewHolder>() {
    lateinit var binding: CropsItemBinding
    lateinit var viewModel: CropsViewModel
    lateinit var items: List<Crops>
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.crops_item, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(binding: CropsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(crops: Crops) {
            Log.e("c", "${crops.name} ${crops.pk}")
            binding.cropsName.text = crops.name
            Glide.with(context)
                .load("${Constants.HOST}${crops.image}")
                .into(binding.cropsImage)
            binding.cardView.setOnClickListener {
                viewModel.updateCrops(crops.pk)
            }
        }
    }
}
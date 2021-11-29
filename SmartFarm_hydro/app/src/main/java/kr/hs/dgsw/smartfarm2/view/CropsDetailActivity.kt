package kr.hs.dgsw.smartfarm2.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kr.hs.dgsw.smartfarm2.R
import kr.hs.dgsw.smartfarm2.databinding.ActivityCropsDetailBinding
import kr.hs.dgsw.smartfarm2.viewmodel.CropsDetailViewModel

class CropsDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityCropsDetailBinding
    lateinit var viewModel: CropsDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_crops_detail)
        viewModel = ViewModelProvider(this).get(CropsDetailViewModel::class.java)

        binding.vm = viewModel
        observeViewModel()
    }

    private fun observeViewModel() {
        with(viewModel) {
            cropSuccess.observe(this@CropsDetailActivity, Observer {
                binding.cropName.text = it.name
                binding.cropContent.text = it.content

                Glide.with(this@CropsDetailActivity)
                    .load(it.imageUrl)
                    .into(binding.cropImage)
            })

            cropError.observe(this@CropsDetailActivity, Observer {
                Toast.makeText(this@CropsDetailActivity, "농작물을 가져오는 중 문제가 발생했습니다.", Toast.LENGTH_SHORT).show()
            })

            backBtn.observe(this@CropsDetailActivity, Observer {
                finish()
            })
        }
    }
}
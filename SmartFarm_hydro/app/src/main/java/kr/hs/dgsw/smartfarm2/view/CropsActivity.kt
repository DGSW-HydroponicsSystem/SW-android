package kr.hs.dgsw.smartfarm2.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.smartfarm2.R
import kr.hs.dgsw.smartfarm2.databinding.ActivityCropsBinding
import kr.hs.dgsw.smartfarm2.util.CropsAdapter
import kr.hs.dgsw.smartfarm2.viewmodel.CropsViewModel

class CropsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCropsBinding
    lateinit var viewModel: CropsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_crops)
        viewModel = ViewModelProvider(this).get(CropsViewModel::class.java)

        binding.vm = viewModel
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getAllCrops()

        with(viewModel) {
            cropsSuccess.observe(this@CropsActivity, Observer {
                val adapter = CropsAdapter()
                adapter.context = this@CropsActivity
                adapter.viewModel = viewModel
                adapter.items = it.crops

                binding.cropsRecycler.adapter = adapter
            })

            cropsError.observe(this@CropsActivity, Observer {
                Toast.makeText(this@CropsActivity,
                    "농작물 리스트를 불러오지 못했습니다. 다시 시도해주세요",
                    Toast.LENGTH_SHORT).show()
            })

            updateSuccess.observe(this@CropsActivity, Observer {
                val intent = Intent(this@CropsActivity, CropsDetailActivity::class.java)
                startActivity(intent)
            })

            updateError.observe(this@CropsActivity, Observer {
                Log.e("dsaf", it.toString())
                Toast.makeText(this@CropsActivity,
                    "오류가 발생했습니다. 다시 시도해주세요",
                    Toast.LENGTH_SHORT).show()
            })

            backBtn.observe(this@CropsActivity, Observer {
                finish()
            })
        }
    }
}
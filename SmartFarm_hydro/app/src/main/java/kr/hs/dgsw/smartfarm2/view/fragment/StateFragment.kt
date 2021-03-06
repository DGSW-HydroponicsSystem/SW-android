package kr.hs.dgsw.smartfarm2.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.smartfarm2.R
import kr.hs.dgsw.smartfarm2.databinding.FragmentStateBinding
import kr.hs.dgsw.smartfarm2.viewmodel.StateViewModel
import kotlin.math.roundToInt

class StateFragment : Fragment() {

    lateinit var binding: FragmentStateBinding
    lateinit var viewModel: StateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_state, container, false)
        viewModel = ViewModelProvider(this).get(StateViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        binding.fm = this
        binding.executePendingBindings()
        observeViewModel()
    }

    private fun observeViewModel() {
        binding.refreshControlView1.setOnRefreshListener {
            viewModel.getAllSensor()
            binding.refreshControlView1.isRefreshing = false
        }

        with(viewModel) {
            getSensorSuccess.observe(viewLifecycleOwner, Observer {
                binding.humidityProgress1.progress = it.data.humidity1.value
                binding.humidityTvProgress1.text = "${it.data.humidity1.value}%"
                binding.humidityProgress2.progress = it.data.humidity2.value
                binding.humidityTvProgress2.text = "${it.data.humidity2.value}%"

                binding.tempProgress1.progress = it.data.temp1.value
                binding.tempTvProgress1.text = "${it.data.temp1.value}???"
                binding.tempProgress2.progress = it.data.temp2.value
                binding.tempTvProgress2.text = "${it.data.temp2.value}???"

                binding.waterTempProgress1.progress = it.data.waterTemp1.value
                binding.waterTempTvProgress1.text = "${it.data.waterTemp1.value}???"
                binding.waterTempProgress2.progress = it.data.waterTemp2.value
                binding.waterTempTvProgress2.text = "${it.data.waterTemp2.value}???"

                binding.waterLevelProgress1.progress = it.data.waterLevel.value * 20
                when (it.data.waterLevel.value) {
                    1 -> binding.waterLevelTvProgress1.text = "???"
                    2 -> binding.waterLevelTvProgress1.text = "???"
                    3 -> binding.waterLevelTvProgress1.text = "???"
                    4 -> binding.waterLevelTvProgress1.text = "??????"
                    else -> binding.waterLevelTvProgress1.text = "??????"
                }

                binding.sunlightProgress1.progress = it.data.sunlight1.value
                binding.sunlightTvProgress1.text = "${it.data.sunlight1.value}%"
                binding.sunlightProgress2.progress = it.data.sunlight2.value
                binding.sunlightTvProgress2.text = "${it.data.sunlight2.value}%"

                binding.phProgress1.progress = it.data.waterPh.value.roundToInt()
                binding.phTvProgress1.text = "${it.data.waterPh.value}pH"

            })

            getSensorError.observe(viewLifecycleOwner, Observer {
                Log.e("dfa", it.toString())
                it.printStackTrace()

                binding.humidityProgress1.progress = 0
                binding.humidityTvProgress1.text = "${0}%"
                binding.humidityProgress2.progress = 0
                binding.humidityTvProgress2.text = "${0}%"

                binding.tempProgress1.progress = 0
                binding.tempTvProgress1.text = "${0}???"
                binding.tempProgress2.progress = 0
                binding.tempTvProgress2.text = "${0}???"


                binding.waterTempProgress1.progress = 0
                binding.waterTempTvProgress1.text = "0???"
                binding.waterTempProgress2.progress = 0
                binding.waterTempTvProgress2.text = "0???"

                binding.waterLevelProgress1.progress = 0
                binding.waterLevelTvProgress1.text = "??????"

                binding.sunlightProgress1.progress = 0
                binding.sunlightTvProgress1.text = "0%"
                binding.sunlightProgress2.progress = 0
                binding.sunlightTvProgress2.text = "0%"

                binding.phProgress1.progress = 0
                binding.phTvProgress1.text = "0pH"

                Toast.makeText(requireActivity(), "??????????????? ?????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
            })
        }
    }
}
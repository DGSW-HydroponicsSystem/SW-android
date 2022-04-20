package kr.hs.dgsw.smartfarm2.view.fagment

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
        binding.refreshControlView.setOnRefreshListener {
            viewModel.getAllSensor()
        }

        with(viewModel) {
            getSensorSuccess.observe(viewLifecycleOwner, Observer {
                binding.humidityProgress1.progress = it.data.humidity1.value
                binding.humidityTvProgress1.text = "${it.data.humidity1.value}%"
                binding.humidityProgress2.progress = it.data.humidity2.value
                binding.humidityTvProgress2.text = "${it.data.humidity2.value}%"

                binding.tempProgress1.progress = it.data.temp1.value
                binding.tempTvProgress1.text = "${it.data.temp1.value}℃"
                binding.tempProgress2.progress = it.data.temp2.value
                binding.tempTvProgress2.text = "${it.data.temp2.value}℃"

                binding.waterTempProgress1.progress = it.data.waterTemp.status
                binding.waterTempTvProgress1.text = "${it.data.waterTemp.status}℃"

                binding.waterLevelProgress1.progress = it.data.waterLevel.status
                binding.waterLevelTvProgress1.text = "${it.data.waterLevel.status}L"

                binding.sunlightProgress1.progress = it.data.sunlight.status
                binding.sunlightTvProgress1.text = "${it.data.sunlight.status}%"

                binding.phProgress1.progress = it.data.waterPh.status.roundToInt()
                binding.phTvProgress1.text = "${it.data.waterPh.status.roundToInt()}pH"

            })

            getSensorError.observe(viewLifecycleOwner, Observer {
                Log.e("dfa", it.toString())
                it.printStackTrace()

                binding.humidityProgress1.progress = 0
                binding.humidityTvProgress1.text = "${0}%"
                binding.humidityProgress2.progress = 0
                binding.humidityTvProgress2.text = "${0}%"

                binding.tempProgress1.progress = 0
                binding.tempTvProgress1.text = "${0}℃"
                binding.tempProgress2.progress = 0
                binding.tempTvProgress2.text = "${0}℃"


                binding.waterTempProgress1.progress = 0
                binding.waterTempTvProgress1.text = "0℃"

                binding.waterLevelProgress1.progress = 0
                binding.waterLevelTvProgress1.text = "0L"

                binding.sunlightProgress1.progress = 0
                binding.sunlightTvProgress1.text = "0%"

                binding.phProgress1.progress = 0
                binding.phTvProgress1.text = "0pH"

                Toast.makeText(requireActivity(), "서버로부터 값을 전달받지 못했습니다.", Toast.LENGTH_SHORT).show()
            })
        }
    }
}
package kr.hs.dgsw.smartfarm2.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kr.hs.dgsw.smartfarm2.network.model.response.GetAllSensor
import kr.hs.dgsw.smartfarm2.network.model.response.Response
import kr.hs.dgsw.smartfarm2.repository.SensorRepository
import kr.hs.dgsw.smartfarm2.util.SingleLiveEvent

class MainViewModel : ViewModel() {
    val disposable = CompositeDisposable()

    val cropsTipBtn = SingleLiveEvent<Any>()

    fun onClickCropsTipBtn(){
        cropsTipBtn.call()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
package kr.hs.dgsw.smartfarm2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kr.hs.dgsw.smartfarm2.network.model.response.*
import kr.hs.dgsw.smartfarm2.repository.SensorRepository
import kr.hs.dgsw.smartfarm2.util.SingleLiveEvent

class ControlViewModel : ViewModel() {
    val repository = SensorRepository()
    val disposable = CompositeDisposable()


    val ledOffImage = SingleLiveEvent<Any>()
    val ledOnImage = SingleLiveEvent<Any>()
    val ledBtn = SingleLiveEvent<Any>()

    val pumpOffImage = SingleLiveEvent<Any>()
    val pumpOnImage = SingleLiveEvent<Any>()
    val pumpbtn = SingleLiveEvent<Any>()

    val pumpOffImage2 = SingleLiveEvent<Any>()
    val pumpOnImage2 = SingleLiveEvent<Any>()
    val pumpbtn2 = SingleLiveEvent<Any>()

    val fanOffImage = SingleLiveEvent<Any>()
    val fanOnImage = SingleLiveEvent<Any>()
    val fanBtn = SingleLiveEvent<Any>()


    val ledControlResult = MutableLiveData<Boolean>()
    val pumpControlResult = MutableLiveData<Boolean>()
    val fanControlResult = MutableLiveData<Boolean>()
    val controlErrorEvent = MutableLiveData<Throwable>()

    val getLedResult = MutableLiveData<Led>()
    val getPumpResult = MutableLiveData<Water>()
    val getFanResult = MutableLiveData<Fan>()
    val getAllResult = MutableLiveData<Module>()
    val stateErrorEvent = MutableLiveData<Throwable>()

    init {
        getAllStatus()
    }

    fun onClickLedOffImage() {
        ledOffImage.call()
    }

    fun onClickLedOnImage() {
        ledOnImage.call()
    }

    fun onClickPumpOnImage() {
        pumpOnImage.call()
    }

    fun onClickPumpOffImage() {
        pumpOffImage.call()
    }

    fun onClickPumpOnImage2() {
        pumpOnImage2.call()
    }

    fun onClickPumpOffImage2() {
        pumpOffImage2.call()
    }

    fun onClickFanOnImage() {
        fanOnImage.call()
    }

    fun onClickFanOffImage() {
        fanOffImage.call()
    }

    fun getAllStatus() {
        addDisposable(repository.getAllModule(), object : DisposableSingleObserver<Response<Module>>() {
            override fun onSuccess(t: Response<Module>) {
                getAllResult.value = t.data
            }

            override fun onError(e: Throwable) {
                stateErrorEvent.value = e
                e.printStackTrace()
            }
        })
    }

    fun controlLed(params: HashMap<String?, Boolean?>) {
        addDisposable(repository.controlLed(params), object : DisposableSingleObserver<Boolean>() {
            override fun onSuccess(t: Boolean) {
                ledControlResult.value = t
            }

            override fun onError(e: Throwable) {
                controlErrorEvent.value = e
                e.printStackTrace()
            }
        })
    }

    fun controlFan(params: HashMap<String?, Boolean?>) {
        addDisposable(repository.controlFan(params), object : DisposableSingleObserver<Boolean>() {
            override fun onSuccess(t: Boolean) {
                fanControlResult.value = t
            }

            override fun onError(e: Throwable) {
                controlErrorEvent.value = e
                e.printStackTrace()
            }
        })
    }

    fun controlPump(status: HashMap<String?, Boolean?>, device: HashMap<String?, Int?>) {
        addDisposable(repository.controlPump(status, device),
            object : DisposableSingleObserver<Boolean>() {
                override fun onSuccess(t: Boolean) {
                    pumpControlResult.value = t
                }

                override fun onError(e: Throwable) {
                    controlErrorEvent.value = e
                    e.printStackTrace()
                }
            })
    }

    fun getLedState() {
        addDisposable(repository.getLedState(), object : DisposableSingleObserver<Response<Led>>() {
            override fun onSuccess(t: Response<Led>) {
                getLedResult.value = t.data
            }

            override fun onError(e: Throwable) {
                stateErrorEvent.value = e
            }
        })
    }

    fun getPumpState() {
        addDisposable(repository.getWaterState(), object : DisposableSingleObserver<Response<Water>>() {
            override fun onSuccess(t: Response<Water>) {
                getPumpResult.value = t.data
            }

            override fun onError(e: Throwable) {
                stateErrorEvent.value = e
            }
        })
    }

    fun getFanState() {
        addDisposable(repository.getFanState(), object : DisposableSingleObserver<Response<Fan>>() {
            override fun onSuccess(t: Response<Fan>) {
                getFanResult.value = t.data
            }

            override fun onError(e: Throwable) {
                stateErrorEvent.value = e
            }
        })
    }

    fun addDisposable(single: Single<*>, observer: DisposableSingleObserver<*>) {
        disposable.add(
            single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer as SingleObserver<Any>) as Disposable
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}
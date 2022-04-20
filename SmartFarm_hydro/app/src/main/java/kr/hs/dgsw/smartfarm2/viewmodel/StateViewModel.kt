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
import kr.hs.dgsw.smartfarm2.network.model.response.GetAllSensor
import kr.hs.dgsw.smartfarm2.network.model.response.Response
import kr.hs.dgsw.smartfarm2.repository.SensorRepository

class StateViewModel : ViewModel() {
    val repository = SensorRepository()
    val disposable = CompositeDisposable()


    val getSensorSuccess = MutableLiveData<Response<GetAllSensor>>()
    val getSensorError = MutableLiveData<Throwable>()

    init {
        getAllSensor()
    }

    fun getAllSensor() {
        addDisposable(repository.getAllSensor(), object : DisposableSingleObserver<Response<GetAllSensor>>() {
            override fun onSuccess(t: Response<GetAllSensor>) {
                getSensorSuccess.value = t
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                getSensorError.value = e
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
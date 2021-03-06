package dev.lucasnlm.arch.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import dev.lucasnlm.arch.core.presenter.BasePresenter
import dev.lucasnlm.arch.core.view.BaseView
import javax.inject.Inject

abstract class BaseFragment<U : BaseView, T : BasePresenter<U>> : DaggerFragment() {

    @Inject
    lateinit var mvpPresenter: T

    @Inject
    lateinit var mvpView: U

    protected abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(layoutRes, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mvpPresenter.onAttach(mvpView)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mvpView.onViewCreated(view)

        // It will call onCreate() in onViewCreated to make any view is null.
        mvpPresenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        mvpPresenter.onDestroy()
    }
}

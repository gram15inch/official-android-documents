package com.example.cupcake

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import com.example.cupcake.model.OrderViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep
import kotlin.concurrent.thread


class ViewModelTests {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

     lateinit var viewModel : OrderViewModel
    @Before
    fun before(){
        viewModel = OrderViewModel()
    }

    @Test
    fun quantity_twelve_cupcakes(){
        var notify = false
        viewModel.quantity.observeForever { notify = !notify}
        viewModel.setQuantity(12)
        assertEquals(12, viewModel.quantity.value)
        sleep(1)
        assertEquals(false, notify)
        viewModel.setQuantity(6)
        assertEquals(6, viewModel.quantity.value)
        sleep(1)
        assertEquals(true, notify)
    }

    @Test
    fun price_twelve_cupcakes(){
        viewModel.price.observeForever {}
        viewModel.setQuantity(12)
        assertEquals("₩24", viewModel.price.value)

    }


}
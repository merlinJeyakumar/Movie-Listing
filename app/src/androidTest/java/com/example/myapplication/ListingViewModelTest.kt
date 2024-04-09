package com.example.myapplication

import android.content.Context
import android.view.Menu
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myapplication.domain.api.IListingsApi
import com.example.myapplication.ui.listing.ListingFragment
import com.example.myapplication.ui.listing.ListingViewModel
import com.example.myapplication.ui.listing.adapter.ListingAdapter
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ListingFragmentTest {


    private lateinit var fragment: ListingFragment
    private lateinit var listingViewModel: ListingViewModel
    val targetContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var listingApi: IListingsApi

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Before
    fun launch() {
        launchFragmentInHiltContainer<ListingFragment>() {
            fragment = this as ListingFragment
            listingViewModel = viewModels<ListingViewModel>().value
        }
    }

    @Test
    fun testFragmentLaunch() {
        onView(withId(R.id.listingRecyclerView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testAdapter() {
        val listingAdapter =
            ((fragment.view!!.findViewById<RecyclerView>(R.id.listingRecyclerView)).adapter as ListingAdapter)

        assert(listingAdapter != null)
    }

    @Test
    fun searchTest() {
        val menu =
            ((fragment.view!!.findViewById<MaterialToolbar>(R.id.toolbar)).menu as Menu)
        assert(menu != null)
    }
}
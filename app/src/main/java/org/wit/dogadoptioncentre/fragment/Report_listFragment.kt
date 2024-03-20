package org.wit.dogadoptioncentre.fragment

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.wit.dogadoptioncentre.R
import org.wit.dogadoptioncentre.ViewModel.ReportViewModelContent
import org.wit.dogadoptioncentre.adapters.AdoptionAdapters
import org.wit.dogadoptioncentre.adapters.AdoptionClickListener
import org.wit.dogadoptioncentre.databinding.FragmentReportListBinding
import org.wit.dogadoptioncentre.main.AdoptionXApp
import org.wit.dogadoptioncentre.models.AdoptionModel

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import org.wit.dogadoptioncentre.utils.*

class Report_listFragment : Fragment() , AdoptionClickListener {

    lateinit var app: AdoptionXApp
    private var _fragBinding: FragmentReportListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var reportViewModelContent: ReportViewModelContent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.app = activity?.application as AdoptionXApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    /* This is the code for the recycler view. */
    ): View? {
        _fragBinding = FragmentReportListBinding.inflate(inflater, container, false)
        val root = fragBinding.root

//        loader = createLoader(requireActivity())

        /* This is the code for the recycler view. */
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)

        activity?.title = getString(R.string.action_adoption_report)



        fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        reportViewModelContent = ViewModelProvider(this).get(ReportViewModelContent::class.java)

//        showLoader(loader,"Downloading adoptions")
        reportViewModelContent.observableAdoptionsList.observe(viewLifecycleOwner, Observer {
                adoptions ->
            adoptions?.let { render (adoptions as ArrayList<AdoptionModel>)}
//            hideLoader(loader)
            checkSwipeRefresh()
        })
        /* This is the code for the floating action button. */
        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = Report_listFragmentDirections.actionReportListFragmentToAdoptionHomeFragment()
            findNavController().navigate(action)
        }


        /* This is the code for the swipe to delete function. */
        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = fragBinding.recyclerView.adapter as AdoptionAdapters
                adapter.removeAt(viewHolder.adapterPosition)
                reportViewModelContent.delete(reportViewModelContent.liveFirebaseUser.value?.uid!!, (viewHolder.itemView.tag as AdoptionModel).uid!!)




            }
        }
        /* This is the code for the swipe to delete function. */
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                onBookingClick(viewHolder.itemView.tag as AdoptionModel)
          onAdoptionClick(viewHolder.itemView.tag as AdoptionModel)

            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)


        /*fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        fragBinding.recyclerView.adapter = AdoptionAdapters(app.adoptionStore.findAll())*/
        return root
    }

    /**
     * The above function is used to render the data from the database to the recycler view.
     *
     * @param adoptionRender ArrayList<AdoptionModel>
     */
    private fun render(adoptionRender: ArrayList<AdoptionModel>) {
        fragBinding.recyclerView.adapter = AdoptionAdapters(adoptionRender,this)
        if (adoptionRender.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.adoptionNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.adoptionNotFound.visibility = View.GONE
        }
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            Report_listFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onResume() {
        super.onResume()
        reportViewModelContent.load()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }









    /**
     * Initialize the contents of the Fragment host's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called [.setHasOptionsMenu].  See
     * [Activity.onCreateOptionsMenu]
     * for more information.
     *
     * @param menu The options menu in which you place your items.
     *
     * @see .setHasOptionsMenu
     *
     * @see .onPrepareOptionsMenu
     *
     * @see .onOptionsItemSelected
     */

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_report,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     *
     *
     * Derived classes should call through to the base class for it to
     * perform the default menu handling.
     *
     * @param item The menu item that was selected.
     *
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     *
     * @see .onCreateOptionsMenu
     */

    /**
     * If the user clicks on an item in the menu, and that item has an associated action, then perform
     * that action
     *
     * @param item The menu item that was selected.
     * @return The return value is a boolean.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    /**
     * It navigates to the adoption detail fragment when the adoption is clicked.
     *
     * @param adoptions AdoptionModel - This is the adoption object that was clicked.
     */
    override fun onAdoptionClick(adoptions: AdoptionModel) {
       val action = Report_listFragmentDirections.actionReportListFragmentToAdoptionDetailFragment(adoptions.id)
       findNavController().navigate(action)

    }


    /* This is the code for the swipe to refresh function. */
    fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
//            showLoader(loader,"Downloading Adoptions")
            //Retrieve Adoptions List again here

        }
    }

    /**
     * It checks if the swipe refresh is refreshing and if it is, it sets it to false.
     */
    fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }


}

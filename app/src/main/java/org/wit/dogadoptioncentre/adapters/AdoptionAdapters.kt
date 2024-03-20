package org.wit.dogadoptioncentre.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.dogadoptioncentre.R
import org.wit.dogadoptioncentre.databinding.ViewcardAdoptionBinding
import org.wit.dogadoptioncentre.models.AdoptionModel
/* This class is an interface that defines a function that takes an AdoptionModel as a parameter and
returns nothing */
interface AdoptionClickListener {
    fun onAdoptionClick(adoptions: AdoptionModel)
}

/* This is the constructor for the AdoptionAdapters class. It takes two parameters, the first is an
ArrayList of AdoptionModel objects and the second is a listener. It extends the RecyclerView.Adapter
class and uses the MainHolder class as the view holder. */
class AdoptionAdapters constructor(private var adoptions: ArrayList<AdoptionModel>,
                                   private val listener: AdoptionClickListener)
    : RecyclerView.Adapter<AdoptionAdapters.MainHolder>()
{

    /**
     * This function is called when the RecyclerView needs a new ViewHolder to represent an item
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an
     * adapter position.
     * @param viewType The type of the view that will be created by onCreateViewHolder() for different
     * item in the list.
     * @return A MainHolder object.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val ad_binding = ViewcardAdoptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return MainHolder(ad_binding)
    }

    /**
     * The function removes an item from the list and notifies the adapter that the item has been
     * removed
     *
     * @param position The position of the item in the list.
     */
    fun removeAt(position: Int) {
        adoptions.removeAt(position)
        notifyItemRemoved(position)
    }


    /**
     * The function takes in a MainHolder and an Int, and returns nothing
     *
     * @param holder MainHolder - this is the view holder that will be used to display the data.
     * @param position Int - The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val adopt = adoptions[holder.adapterPosition]
        holder.bind(adopt, listener)
    }

    /**
     * It returns the size of the adoptions list.
     */
    override fun getItemCount(): Int=adoptions.size

   /* The MainHolder class is an inner class of the MainAdapter class. It is a subclass of
   RecyclerView.ViewHolder. It has a constructor that takes a ViewcardAdoptionBinding object as a
   parameter. It has a bind method that takes an AdoptionModel object and an AdoptionClickListener
   object as parameters */
   inner class MainHolder(val binding: ViewcardAdoptionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(adoptionModel: AdoptionModel, listener: AdoptionClickListener){

//            binding.nameOfPet1.text = adoptionModel.dogName
//            binding.nameOfBreed2.text = adoptionModel.dogBreed
//            binding.nameOfLocation3.text =adoptionModel.dogLocation
//            binding.ratingBar.rating = adoptionModel.ratingbar
//            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
//
            binding.adoption =adoptionModel
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onAdoptionClick(adoptionModel) }
            binding.executePendingBindings()




        }

    }



}
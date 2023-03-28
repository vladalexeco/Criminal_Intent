package ru.vladalexeco.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "CrimeListFragment"

class CrimeListFragment: Fragment() {

    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = null

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this)[CrimeListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total crimes ${crimeListViewModel.crimes.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.framgment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }

    private fun updateUI() {
        val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    private inner class CrimeAdapter(var crimes: List<Crime>): RecyclerView.Adapter<CrimeHolder>() {

//        private val TYPE_1 = 1
//        private val TYPE_2 = 2

//        override fun getItemViewType(position: Int): Int {
//            return if (crimes[position].requiresPolice) TYPE_1 else TYPE_2
//        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
//            val view = when (viewType) {
//                TYPE_1 -> LayoutInflater.from(parent.context).inflate(R.layout.list_item_crime_police, parent, false)
//                TYPE_2 -> LayoutInflater.from(parent.context).inflate(R.layout.list_item_crime, parent, false)
//                else -> throw IllegalArgumentException("Invalid view type")
//            }
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)

//            when (holder.itemViewType) {
//                TYPE_1 -> {
//                    if (holder.buttonCallPolice != null) {
//                        holder.buttonCallPolice.setOnClickListener {
//                            Toast.makeText(context, "${crime.title} with pos $position", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//                TYPE_2 -> {
//                    // Настройка виджетов для второго типа view
//                    // ...
//                }
//            }

        }

        override fun getItemCount(): Int {
            return crimes.size
        }

    }

    private inner class CrimeHolder(view:View): RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var crime: Crime

        val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
//        val buttonCallPolice: Button? = itemView.findViewById(R.id.buttonCallPolice)
        val isSolvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        init {
            itemView.setOnClickListener(this)
        }
        
        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = crime.title
            dateTextView.text = crime.date.toString()
            isSolvedImageView.visibility = if (crime.isSolved) View.VISIBLE else View.GONE
        }

        override fun onClick(p0: View?) {
            Toast.makeText(context, "${crime.title} is pressed", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}
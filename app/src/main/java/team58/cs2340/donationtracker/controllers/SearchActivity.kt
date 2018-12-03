package team58.cs2340.donationtracker.controllers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

import me.xdrop.fuzzywuzzy.FuzzySearch
import team58.cs2340.donationtracker.models.Category
import team58.cs2340.donationtracker.models.Donation
import team58.cs2340.donationtracker.models.Location
import team58.cs2340.donationtracker.models.LocationsLocal
import team58.cs2340.donationtracker.R
import java.util.*

/**
 * Class for search activity
 */
class SearchActivity : AppCompatActivity() {

    private var locationSpinner: Spinner? = null
    private var categorySpinner: Spinner? = null
    private var searchTxt: EditText? = null
    private var donationList: ListView? = null
    private var nfMessageText: TextView? = null

    private var db: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_donations)

        locationSpinner = findViewById(R.id.locationSpinner)
        categorySpinner = findViewById(R.id.categorySpinner)
        searchTxt = findViewById(R.id.searchText)
        donationList = findViewById(R.id.donationSearchList)
        nfMessageText = findViewById(R.id.nothingFoundText)
        nfMessageText!!.visibility = View.GONE

        db = FirebaseFirestore.getInstance()
        val locationManager = LocationsLocal.getInstance()
        val locations = ArrayList<Location>()
        locations.add(LocationsLocal.getDefaultAllLocation())
        locations.addAll(locationManager.locations)
        val categories = ArrayList<String>()
        categories.add("All Categories")
        for (c in Category.values()) {
            categories.add(c.toString())
        }

        val locationAdapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, locations)
        locationSpinner!!.adapter = locationAdapter
        val categoryAdapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, categories)
        categorySpinner!!.adapter = categoryAdapter

    }

    /**
     * Function called when search button is clicked
     * @param view view of the search screen
     */
    fun onSearchClick(view: View) {
        nfMessageText!!.visibility = View.GONE
        val location = (locationSpinner!!.selectedItem as Location).name
        val category = categorySpinner!!.selectedItem as String
        val searchQuery = searchTxt!!.text.toString()

        //final ArrayList<Donation> donationsFiltered = new ArrayList<>();
        //final DonationNameToStringFunc dnFunc = new DonationNameToStringFunc();
        val donationsQueryResults = ArrayList<Donation>()

        if ("All Locations" == location && "All Categories" == category) {
            db!!.collection("donations")
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (d in Objects.requireNonNull<QuerySnapshot>(
                                    task.result)) {
                                if (FuzzySearch.weightedRatio(searchQuery,
                                                d.getString("name")) >= THRESHOLD_RATIO) {
                                    val donation = Donation(Objects.requireNonNull<Date>(
                                            d.getDate("timestamp")),
                                            d.getString("name"),
                                            d.getString("location"),
                                            java.lang.Double.parseDouble(d.getString("value")),
                                            d.getString("shortDescription"),
                                            d.getString("fullDescription"),
                                            Category.fromString(d.getString("category")!!),
                                            d.getString("comment"))
                                    donationsQueryResults.add(donation)
                                }

                                /*Donation donation = new Donation(d.getDate("timestamp"),
                                     d.getString("name"),
                                            d.getString("location"),
                                             Double.parseDouble(d.getString("value")),
                                             d.getString("shortDescription"),
                                            d.getString("fullDescription"),
                                            Category.fromString(d.getString("category")),
                                            d.getString("comment"));
                                    donationsFiltered.add(donation);*/
                            }
                            /*List<BoundExtractedResult<Donation>> matches =
                                FuzzySearch.extractTop(searchQuery, donationsFiltered, dnFunc, 5);
                                for (BoundExtractedResult result : matches) {
                                    donationsQueryResults.add((Donation) result.getReferent());
                                }*/
                            if (donationsQueryResults.isEmpty()) {
                                nfMessageText!!.visibility = View.VISIBLE
                            }
                            val donationAdapter = DonationListAdapter(
                                    this@SearchActivity, donationsQueryResults)
                            donationList!!.adapter = donationAdapter

                            donationList!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                                val intent = Intent(
                                        this@SearchActivity,
                                        DonationDetailsActivity::class.java)
                                intent.putExtra("donation",
                                        donationsQueryResults[position])
                                startActivity(intent)
                            }
                        } else {
                            Toast.makeText(this@SearchActivity,
                                    Objects.requireNonNull<Exception>(task.exception).message,
                                    Toast.LENGTH_SHORT).show()
                        }
                    }

        } else if ("All Locations" == location) {
            db!!.collection("donations")
                    .whereEqualTo("category", category)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (d in Objects.requireNonNull<QuerySnapshot>(
                                    task.result)) {
                                if (FuzzySearch.weightedRatio(searchQuery,
                                                d.getString("name")) >= THRESHOLD_RATIO) {
                                    val donation = Donation(Objects.requireNonNull<Date>(
                                            d.getDate("timestamp")),
                                            d.getString("name"),
                                            d.getString("location"),
                                            java.lang.Double.parseDouble(d.getString("value")),
                                            d.getString("shortDescription"),
                                            d.getString("fullDescription"),
                                            Category.fromString(d.getString("category")!!),
                                            d.getString("comment"))
                                    donationsQueryResults.add(donation)
                                }

                                /*Donation donation = new Donation(d.getDate("timestamp"),
                                    d.getString("name"),
                                            d.getString("location"),
                                            Double.parseDouble(d.getString("value")),
                                            d.getString("shortDescription"),
                                            d.getString("fullDescription"),
                                            Category.fromString(d.getString("category")),
                                            d.getString("comment"));
                                    donationsFiltered.add(donation);*/
                            }
                            /*List<BoundExtractedResult<Donation>> matches =
                                FuzzySearch.extractTop(searchQuery, donationsFiltered, dnFunc, 5);
                                for (BoundExtractedResult result : matches) {
                                    donationsQueryResults.add((Donation) result.getReferent());
                                }*/
                            if (donationsQueryResults.isEmpty()) {
                                nfMessageText!!.visibility = View.VISIBLE
                            }
                            val donationAdapter = DonationListAdapter(
                                    this@SearchActivity, donationsQueryResults)
                            donationList!!.adapter = donationAdapter

                            donationList!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                                val intent = Intent(
                                        this@SearchActivity,
                                        DonationDetailsActivity::class.java)
                                intent.putExtra("donation",
                                        donationsQueryResults[position])
                                startActivity(intent)
                            }
                        } else {
                            Toast.makeText(this@SearchActivity,
                                    Objects.requireNonNull<Exception>(task.exception).message,
                                    Toast.LENGTH_SHORT).show()
                        }
                    }

        } else if ("All Categories" == category) {
            db!!.collection("donations")
                    .whereEqualTo("location", location)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (d in Objects.requireNonNull<QuerySnapshot>(
                                    task.result)) {
                                if (FuzzySearch.weightedRatio(searchQuery,
                                                d.getString("name")) >= THRESHOLD_RATIO) {
                                    val donation = Donation(Objects.requireNonNull<Date>(
                                            d.getDate("timestamp")),
                                            d.getString("name"),
                                            d.getString("location"),
                                            java.lang.Double.parseDouble(d.getString("value")),
                                            d.getString("shortDescription"),
                                            d.getString("fullDescription"),
                                            Category.fromString(d.getString("category")!!),
                                            d.getString("comment"))
                                    donationsQueryResults.add(donation)
                                }

                                /*Donation donation = new Donation(d.getDate("timestamp"),
                                    d.getString("name"),
                                            d.getString("location"),
                                            Double.parseDouble(d.getString("value")),
                                            d.getString("shortDescription"),
                                            d.getString("fullDescription"),
                                            Category.fromString(d.getString("category")),
                                            d.getString("comment"));
                                    donationsFiltered.add(donation);*/
                            }
                            /*List<BoundExtractedResult<Donation>> matches =
                                FuzzySearch.extractTop(searchQuery, donationsFiltered, dnFunc, 5);
                                for (BoundExtractedResult result : matches) {
                                    donationsQueryResults.add((Donation) result.getReferent());
                                }*/
                            if (donationsQueryResults.isEmpty()) {
                                nfMessageText!!.visibility = View.VISIBLE
                            }
                            val donationAdapter = DonationListAdapter(
                                    this@SearchActivity, donationsQueryResults)
                            donationList!!.adapter = donationAdapter

                            donationList!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                                val intent = Intent(
                                        this@SearchActivity,
                                        DonationDetailsActivity::class.java)
                                intent.putExtra("donation",
                                        donationsQueryResults[position])
                                startActivity(intent)
                            }
                        } else {
                            Toast.makeText(this@SearchActivity,
                                    Objects.requireNonNull<Exception>(task.exception).message,
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        } else {
            db!!.collection("donations")
                    .whereEqualTo("location", location)
                    .whereEqualTo("category", category)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (d in Objects.requireNonNull<QuerySnapshot>(
                                    task.result)) {
                                if (FuzzySearch.weightedRatio(searchQuery,
                                                d.getString("name")) >= THRESHOLD_RATIO) {
                                    val donation = Donation(
                                            Objects.requireNonNull<Date>(d.getDate(
                                                    "timestamp")),
                                            d.getString("name"),
                                            d.getString("location"),
                                            java.lang.Double.parseDouble(d.getString("value")),
                                            d.getString("shortDescription"),
                                            d.getString("fullDescription"),
                                            Category.fromString(d.getString("category")!!),
                                            d.getString("comment"))
                                    donationsQueryResults.add(donation)
                                }

                                /*Donation donation = new Donation(d.getDate("timestamp"),
                                    d.getString("name"),
                                            d.getString("location"),
                                             Double.parseDouble(d.getString("value")),
                                             d.getString("shortDescription"),
                                            d.getString("fullDescription"),
                                            Category.fromString(d.getString("category")),
                                            d.getString("comment"));
                                    donationsFiltered.add(donation);*/
                            }
                            /*List<BoundExtractedResult<Donation>> matches =
                                FuzzySearch.extractTop(searchQuery, donationsFiltered, dnFunc, 5);
                                for (BoundExtractedResult result : matches) {
                                    donationsQueryResults.add((Donation) result.getReferent());
                                }*/
                            if (donationsQueryResults.isEmpty()) {
                                nfMessageText!!.visibility = View.VISIBLE
                            }
                            val donationAdapter = DonationListAdapter(
                                    this@SearchActivity, donationsQueryResults)
                            donationList!!.adapter = donationAdapter

                            donationList!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                                val intent = Intent(
                                        this@SearchActivity,
                                        DonationDetailsActivity::class.java)
                                intent.putExtra("donation",
                                        donationsQueryResults[position])
                                startActivity(intent)
                            }
                        } else {
                            Toast.makeText(this@SearchActivity,
                                    Objects.requireNonNull<Exception>(task.exception).message,
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        }
    }

    companion object {

        private val THRESHOLD_RATIO = 60
    }
    /*class DonationNameToStringFunc implements ToStringFunction<Donation> {
        @Override
        public String apply(Donation item) {
            return item.getName();
        }
    }*/
}

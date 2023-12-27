import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ctis.eventeaze.MainActivity
import com.ctis.eventeaze.R
import com.ctis.eventeaze.backgroundservice.EventWorker
import com.ctis.eventeaze.sys.EventSys
import com.google.android.material.snackbar.Snackbar

class EventsRecyclerViewAdapter(
    private val context: Context
) :
    RecyclerView.Adapter<EventsRecyclerViewAdapter.RecyclerViewItemHolder>() {
    lateinit var workManager: WorkManager
    lateinit var workRequest: OneTimeWorkRequest
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerViewItemHolder {
        val inflator = LayoutInflater.from(viewGroup.context)
        val itemView: View = inflator.inflate(R.layout.item_layout, viewGroup, false)
        return RecyclerViewItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewItemHolder, position: Int) {
        val event = EventSys.events[position]

        holder.tvItemName.text = event.name ?: "N/A"
        holder.tvItemDate.text = event.date ?: "N/A"
        holder.tvItemLocation.text = event.location ?: "N/A"
        holder.tvItemType.text = event.eventType ?: "N/A"

        // Set image drawable based on item type
        if (event.eventType == "THEATRE") {
            holder.imgEvent.setImageDrawable(getDrawableByName(holder.itemView.context, "theater"))
        } else if (event.eventType == "CONCERT") {
            holder.imgEvent.setImageDrawable(getDrawableByName(holder.itemView.context, "concert"))
        } else if (event.eventType == "MOVIES") {
            holder.imgEvent.setImageDrawable(getDrawableByName(holder.itemView.context, "movies"))
        } else if (event.eventType == "FESTIVAL") {
            holder.imgEvent.setImageDrawable(getDrawableByName(holder.itemView.context, "festival"))
        }

        holder.btnFav.setOnClickListener{
            //worker example to insert data to our database.
            //will run this code everytime a user favorites a event for that spesific event!
            workRequest = OneTimeWorkRequest.Builder(EventWorker::class.java)
                .setInputData(
                    Data.Builder()
                        .putString("eventName", event.name)
                        .putString("eventDate", event.date)
                        .putString("eventLocation", event.location)
                        .putString("eventType", event.eventType).build()
                )
                .build()
            workManager = WorkManager.getInstance(context)

            workManager.getWorkInfoByIdLiveData(workRequest.id).observe(context as MainActivity,
                Observer { workInfo ->
                    if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                        val resultData: Data = workInfo.outputData//get output of worker
//                         Snackbar.make(binding.btnSaveToDatabase, "SUCCEEDED " + resultData.getString("result"), Snackbar.LENGTH_LONG ).show()
                    }
                })
            workManager.enqueue(workRequest)
        }

        holder.parentLayout.setOnClickListener {

        }
    }

    private fun getDrawableByName(context: Context, drawableName: String): Drawable? {
        val resourceId = context.resources.getIdentifier(
            drawableName,
            "drawable",
            context.packageName
        )

        return if (resourceId != 0) {
            ContextCompat.getDrawable(context, resourceId)
        } else {
            // Handle the case where the drawable was not found
            null
        }
    }
    override fun getItemCount(): Int {
        return EventSys.events.size
    }
    inner class RecyclerViewItemHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvItemName: TextView
        var tvItemLocation: TextView
        var tvItemDate: TextView
        var tvItemType: TextView
        var imgEvent: ImageView
        var btnFav: ImageButton
        var parentLayout: ConstraintLayout

        init {
            tvItemName = itemView.findViewById(R.id.tvItemName)
            tvItemDate = itemView.findViewById(R.id.tvItemDate)
            tvItemLocation = itemView.findViewById(R.id.tvItemLocation)
            tvItemType = itemView.findViewById(R.id.tvItemType)
            imgEvent = itemView.findViewById(R.id.imgEvent)
            btnFav = itemView.findViewById(R.id.btnFav)
            parentLayout = itemView.findViewById(R.id.itemConstraint)
        }
    }


}

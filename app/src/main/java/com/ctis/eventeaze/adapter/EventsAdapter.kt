import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ctis.eventeaze.R
import com.ctis.eventeaze.db.Event

class EventsAdapter(private val events: List<Event>) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventName: TextView = view.findViewById(R.id.eventNameTextView)
        val eventDate: TextView = view.findViewById(R.id.eventDateTextView)
        val eventLocation: TextView = view.findViewById(R.id.eventLocationTextView)
        val eventType: TextView = view.findViewById(R.id.eventTypeTextView)
        // Add other view references as needed.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.eventName.text = event.name ?: "N/A"
        holder.eventDate.text = event.date ?: "N/A"
        holder.eventLocation.text = event.location ?: "N/A"
        holder.eventType.text = event.eventType ?: "N/A"
        // Bind other event data to the views here
    }

    override fun getItemCount() = events.size
}

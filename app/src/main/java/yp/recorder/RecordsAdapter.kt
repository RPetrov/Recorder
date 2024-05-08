package yp.recorder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class RecordsViewHolder(view: View): RecyclerView.ViewHolder(view){

    fun bind(name:String){

        itemView.findViewById<TextView>(R.id.recordName).setText(name)
    }

    companion object{

        fun create(parent: ViewGroup):RecordsViewHolder{

            return RecordsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.record_item,parent, false))
        }
    }
}

class RecordsAdapter(val records:Array<File>) : RecyclerView.Adapter<RecordsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordsViewHolder {
        return RecordsViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return records.size;
    }

    override fun onBindViewHolder(holder: RecordsViewHolder, position: Int) {

        holder.bind(records.get(position).name)
    }

}
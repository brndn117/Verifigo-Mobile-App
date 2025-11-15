package com.example.verifigo.ui.buyer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.verifigo.R
import com.example.verifigo.model.Vehicle

class VehicleAdapter(
    private var vehicles: List<Vehicle>,
    private val onItemClick: (Vehicle) -> Unit
) : RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    inner class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage: ImageView = itemView.findViewById(R.id.ivVehicleImage)
        val tvTitle: TextView = itemView.findViewById(R.id.tvVehicleTitle)
        val tvPrice: TextView = itemView.findViewById(R.id.tvVehiclePrice)

        fun bind(vehicle: Vehicle) {
            tvTitle.text = "${vehicle.make} ${vehicle.model} ${vehicle.year}"
            // Format price nicely
            tvPrice.text = "KSH ${"%,.0f".format(vehicle.price)}"

            // Load image using Glide
            Glide.with(itemView.context)
                .load(vehicle.imageUrl)
                // Assuming you have this drawable for a placeholder/error image
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(ivImage)

            itemView.setOnClickListener {
                onItemClick(vehicle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vehicle, parent, false)
        return VehicleViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(vehicles[position])
    }

    override fun getItemCount() = vehicles.size

    fun updateList(newVehicles: List<Vehicle>) {
        vehicles = newVehicles
        notifyDataSetChanged()
    }
}
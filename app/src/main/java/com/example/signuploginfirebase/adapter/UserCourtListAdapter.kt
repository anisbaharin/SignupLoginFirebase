package com.example.signuploginfirebase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.signuploginfirebase.Court
import com.example.signuploginfirebase.databinding.UserCourtItemviewBinding
import com.example.signuploginfirebase.util.CustomDiffUtil

class UserCourtListAdapter(private val onCourtItemClick: OnCourtItemClickListener) : RecyclerView.Adapter<UserCourtListAdapter.CourtListViewHolder>() {

    private var courtList: List<Court> = listOf()

    inner class CourtListViewHolder(val binding: UserCourtItemviewBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourtListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserCourtItemviewBinding.inflate(inflater, parent, false)
        return CourtListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return courtList.size
    }

    override fun onBindViewHolder(holder: CourtListViewHolder, position: Int) {
        val currentItem = courtList[position]

        holder.binding.apply {
            courtNameTV.text = currentItem.courtName
            courtNumberTV.text = currentItem.courtNumber

            // Load the courtImage using Glide library
            Glide.with(root.context)
                    .load(currentItem.imageUrl)
                    .into(courtImageViewTV)

            // Set a click listener to handle item clicks
            root.setOnClickListener {
                onCourtItemClick.onItemClick(currentItem)
            }
        }
    }

    fun submitList(newList: List<Court>) {
        val diffUtil = CustomDiffUtil(courtList, newList)
        val result = DiffUtil.calculateDiff(diffUtil)
        courtList = newList
        result.dispatchUpdatesTo(this)
    }

    // Interface to handle item clicks
    interface OnCourtItemClickListener {
        fun onItemClick(clickedCourt: Court)
    }
}

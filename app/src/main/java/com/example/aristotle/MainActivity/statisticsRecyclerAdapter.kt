package com.example.aristotle.MainActivity

class statisticsRecyclerAdapter {
}

class UsersRecyclerViewAdapter(private var usersData: List<User>) : RecyclerView.Adapter<UsersRecyclerViewAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.UserItemName)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerviewitem_user, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val fullName = usersData[position].firstName + " " + usersData[position].lastName

        holder.name.text = fullName
    }

    override fun getItemCount() = usersData.size

    fun updateList(newUserList: List<User>) {
        usersData = newUserList
        notifyDataSetChanged()
    }
}
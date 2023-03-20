package com.example.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Entre parenteses é o construtor
// Classe com a lógica para o tipo de view Recycler
class TodoAdapter (private val todos: MutableList<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>()
{
    // Aqui defino a view do item_todo.xml com comportamento de RecyclerView, no qual só renderiza os itens que aparecem na tela
    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // Aqui eu pego item_todo.xml e gero a classe do tipo RecyclerView
    // Defino a view de cada elemento, que no caso é cada Todo
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            // Transforma o xml em view
            // LayoutInflater pega a referência da view (xml)
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    // Funções para adicionar/remover e notify para renderizar
    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteTodo() {
        todos.removeAll { todo -> todo.isChecked }
        notifyDataSetChanged()
    }

    // ?? Rever essa parte
    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if(isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    // Set do conteúdo (data)
    // Defino o counteúdo de cada Todo
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentTodo = todos[position]
        val tvTodoTitle = holder.itemView.findViewById<TextView>(R.id.tvTodoTitle)
        val cbDone = holder.itemView.findViewById<CheckBox>(R.id.cbDone)
        holder.itemView.apply {
            // Precis achar a view pelo id 'findViewById'
            // https://www.reddit.com/r/androiddev/comments/ksklzv/recyclerview_adapter_is_not_finding_views_kotlin/
            // https://stackoverflow.com/questions/73593827/not-able-to-access-a-textview-by-findviewbyid-method-in-my-created-kotlin-file-f
            tvTodoTitle.text = currentTodo.title
            cbDone.isChecked = currentTodo.isChecked
            toggleStrikeThrough(tvTodoTitle, currentTodo.isChecked)
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(tvTodoTitle, isChecked)
                currentTodo.isChecked = !currentTodo.isChecked
            }
        }
    }

    // Retorna o tamanho da lista de todos, para que o Recycler saiba o quanto renderizar
    override fun getItemCount(): Int {
        return todos.size
    }
}
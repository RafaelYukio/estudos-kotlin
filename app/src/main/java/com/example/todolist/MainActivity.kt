package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Activity são as pages
// Views são os componentes (layouts)
class MainActivity : AppCompatActivity() {
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Instancia nosso adaptador que é a lista de Todos
        todoAdapter = TodoAdapter(mutableListOf())

        // Para refenciar view pelo id aqui
        // https://stackoverflow.com/questions/59900450/how-to-get-view-from-main-activity
        // Aqui são todos os componentes do activity_main.xml que é por padrão a view inicial
        var rvTodoItems = this.findViewById<RecyclerView>(R.id.rvTodoItems)
        var etTodoTitle = this.findViewById<EditText>(R.id.etTodoTitle)
        var btnAddTodo = this.findViewById<Button>(R.id.btnAddTodo)
        var btnDeleteTodo = this.findViewById<Button>(R.id.btnDeleteTodo)

        // Seta a classe lógica da Recycler para a view to tipo Recycler
        rvTodoItems.adapter = todoAdapter
        // ?? Define como a lista aparece?
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        // Aqui é o botão de adicionar Todo da tela princial
        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            if(todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle)
                todoAdapter.addTodo(todo)
                etTodoTitle.text.clear()
            }
        }

        btnDeleteTodo.setOnClickListener {
            todoAdapter.deleteTodo()
        }
    }
}
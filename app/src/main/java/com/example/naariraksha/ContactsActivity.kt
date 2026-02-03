package com.example.naariraksha

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naariraksha.data.AppDatabase
import com.example.naariraksha.data.Contact
import com.example.naariraksha.databinding.ActivityContactsBinding
import kotlinx.coroutines.launch

class ContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactsBinding
    private lateinit var database: AppDatabase
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)
        
        setupRecyclerView()

        binding.fabAddContact.setOnClickListener {
            showAddContactDialog()
        }

        lifecycleScope.launch {
            database.contactDao().getAllContacts().collect { contacts ->
                adapter.submitList(contacts)
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = ContactAdapter { contact ->
            lifecycleScope.launch {
                database.contactDao().deleteContact(contact)
            }
        }
        binding.rvContacts.layoutManager = LinearLayoutManager(this)
        binding.rvContacts.adapter = adapter
    }

    private fun showAddContactDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_contact, null)
        val etName = dialogView.findViewById<EditText>(R.id.etContactName)
        val etPhone = dialogView.findViewById<EditText>(R.id.etContactPhone)

        AlertDialog.Builder(this)
            .setTitle("Add Emergency Contact")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = etName.text.toString()
                val phone = etPhone.text.toString()
                if (name.isNotEmpty() && phone.isNotEmpty()) {
                    lifecycleScope.launch {
                        database.contactDao().insertContact(Contact(name = name, phoneNumber = phone))
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}

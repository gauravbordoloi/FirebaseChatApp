package me.gauravbordoloi.chatapp.ui.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_chat.*
import me.gauravbordoloi.chatapp.AppController
import me.gauravbordoloi.chatapp.R
import me.gauravbordoloi.chatapp.data.model.Chat
import me.gauravbordoloi.chatapp.ui.adapter.Adapter
import me.gauravbordoloi.chatapp.ui.viewmodel.ChatViewModel
import me.gauravbordoloi.chatapp.util.Const
import me.gauravbordoloi.chatapp.util.Helper
import me.gauravbordoloi.chatapp.util.ItemDecoration

class ChatFragment : Fragment() {

    private var root: View? = null

    private val model: ChatViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_chat, container, false)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val adapter = Adapter()
        recyclerView.addItemDecoration(ItemDecoration(requireContext(), 16))
        recyclerView.adapter = adapter

        model.getChats().observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                Snackbar.make(view, "No previous chats found", Snackbar.LENGTH_SHORT).show()
            } else {
                adapter.setChats(it)
                recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
            }
        })

        model.observeChat().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.addChat(it)
                recyclerView.scrollToPosition(adapter.itemCount - 1)
            }
        })

        inputText.setEndIconOnClickListener {
            val text = inputText.editText?.text.toString().trim()
            if (text.isEmpty()) {
                return@setEndIconOnClickListener
            }
            model.sendChat(text)
            inputText.editText?.text = null
            Helper.hideSoftKeyboard(it)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_chat, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_logout) {
            AppController.sharedPref.clear()
            findNavController().navigate(ChatFragmentDirections.actionChatFragmentToUserFragment())
        }
        return true
    }

}
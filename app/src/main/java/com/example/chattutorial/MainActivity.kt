package com.example.chattutorial

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.chattutorial.databinding.ActivityMainBinding
import com.getstream.sdk.chat.ChatUI
import com.getstream.sdk.chat.viewmodel.channels.ChannelsViewModel
import com.getstream.sdk.chat.viewmodel.channels.bindView
import com.getstream.sdk.chat.viewmodel.factory.ChannelsViewModelFactory
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.livedata.ChatDomain

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Step 1 - Set up the client for API calls, the domain for offline storage and the UI components
        //gzuh3rrrsr7q
        val client =
            ChatClient.Builder("7frq34jz6kbg", applicationContext).logLevel(ChatLogLevel.ALL)
                .build()
        val domain = ChatDomain.Builder(client, applicationContext).build()
        ChatUI.Builder(applicationContext).build()

        // Step 2 - Authenticate and connect the user
        val user = User("idchdsz123").apply {
            extraData["name"] = "chdsz123"
            extraData["image"] = "https://i.pinimg.com/236x/c8/45/d8/c845d809f5873ed29d82511e9c342ba2--film-anime-manga-anime.jpg"
        }

        client.setUser(
            user = user,
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiaWRjaGRzejEyMyJ9.kca73eq1W6MKUQgnc1Q1UCtNUZ5cHRdkw64xcX-1BkI="
        )
        //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiaWRjaGRzejEyMyJ9.kK81KN2mRL2KD-8_tXFNUVYaZ9fL0MUqvWHOW4rTXZU=

        // Step 3 - Set the channel list filter and order
        // This can be read as requiring only channels whose "type" is "messaging" AND
        // whose "members" include our "user.id"
        val filter = Filters.and(
            Filters.eq("type", "mobile"),
//            Filters.`in`("members", listOf(user.id))
        )
        val viewModelFactory = ChannelsViewModelFactory(filter, ChannelsViewModel.DEFAULT_SORT)
        val viewModel: ChannelsViewModel by viewModels { viewModelFactory }

        // Step 4 - Connect the ChannelsViewModel to the ChannelsView, loose coupling makes it easy to customize
        viewModel.bindView(findViewById(R.id.channelsView), this)
        binding.channelsView.setOnChannelClickListener { channel ->
            startActivity(ChannelActivity.newIntent(this, channel))
        }
    }
}

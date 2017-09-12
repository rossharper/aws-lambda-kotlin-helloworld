package net.rossharper.awshelloworld

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okio.Okio
import java.io.InputStream
import java.io.OutputStream

data class HandlerInput(val name: String)

sealed class HandlerOutput {
    data class Greeting(val greeting: String) : HandlerOutput()
    data class Error(val error: String) : HandlerOutput()
}

class Main {
    fun handler(input: InputStream, output: OutputStream): Unit {
        val moshi = Moshi.Builder().build()
        val inputAdapter : JsonAdapter<HandlerInput> = moshi.adapter(HandlerInput::class.java)
        val handlerInput = inputAdapter.fromJson(Okio.buffer(Okio.source(input)))

        if(handlerInput == null) {
            respond(HandlerOutput.Error("Bad Request"), output)
        } else {
            val greeting = "Hello, ${handlerInput.name}. Welcome to my Kotlin AWS SuperLambBanana"
            respond(HandlerOutput.Greeting(greeting), output)
        }
    }

    inline fun <reified T : HandlerOutput> respond(response: T, output: OutputStream) {
        val bufferedSink = Okio.buffer(Okio.sink(output))
        Moshi.Builder()
                .build()
                .adapter<T>(T::class.java)
                .toJson(bufferedSink, response)
        bufferedSink.emit()
    }
}

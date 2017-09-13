package net.rossharper.awshelloworld

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okio.Okio
import java.io.InputStream
import java.io.OutputStream

data class HandlerInput(val name: String)

sealed class HandlerOutput
data class Greeting(val greeting: String) : HandlerOutput()
data class Error(val error: String) : HandlerOutput()

class Main {
    fun handler(input: InputStream, output: OutputStream): Unit {
        val handlerInput = input.parse()

        if(handlerInput == null) {
            output with Error("Bad Request")
        } else {
            output with Greeting("Hello, ${handlerInput.name}. Welcome to my Kotlin AWS SuperLambBanana")
        }
    }
}

private fun InputStream.parse(): HandlerInput? {
    val moshi = Moshi.Builder().build()
    val inputAdapter : JsonAdapter<HandlerInput> = moshi.adapter(HandlerInput::class.java)
    return inputAdapter.fromJson(Okio.buffer(Okio.source(this)))
}

private inline infix fun <reified T : HandlerOutput> OutputStream.with(response: T) {
    val bufferedSink = Okio.buffer(Okio.sink(this))
    Moshi.Builder()
            .build()
            .adapter(T::class.java)
            .toJson(bufferedSink, response)
    bufferedSink.emit()
}


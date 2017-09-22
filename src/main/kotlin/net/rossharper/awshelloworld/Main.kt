package net.rossharper.awshelloworld

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okio.Okio
import java.io.InputStream
import java.io.OutputStream

data class Request(val body: String)
data class Response(val statusCode: Int, val body: String)

data class HandlerInput(val name: String)

sealed class HandlerOutput
data class Greeting(val greeting: String) : HandlerOutput()
data class Error(val error: String) : HandlerOutput()

class Main {
    fun handler(input: InputStream, output: OutputStream): Unit {

        val request = input.parseRequest()

        if(request !is Request) {
            output with Error("Bad Request: failed to parse request")
            return
        }

        val handlerInput = request.parseBody()

        if(handlerInput !is HandlerInput) {
            output with Error("Bad Request: failed to parse request body")
            return
        }

        output with Greeting("Hello, ${handlerInput.name}. Welcome to my Kotlin AWS SuperLambBanana")
    }
}

private fun InputStream.parseRequest() : Request? {
    val moshi = Moshi.Builder().build()
    val requestAdapter : JsonAdapter<Request> = moshi.adapter(Request::class.java)
    return requestAdapter.fromJson(Okio.buffer(Okio.source(this)))
}

private fun Request.parseBody() : HandlerInput? {
    val moshi = Moshi.Builder().build()
    val inputAdapter : JsonAdapter<HandlerInput> = moshi.adapter(HandlerInput::class.java)
    return inputAdapter.fromJson(this.body)
}

private inline infix fun <reified T : HandlerOutput> OutputStream.with(output: T) {
    val body = Moshi.Builder().build().adapter(T::class.java).toJson(output)
    when(output) {
        is Greeting -> this.with(Response(200, body))
        is Error -> this.with(Response(500, body))
    }
}

private infix fun OutputStream.with(response : Response) {
    val bufferedSink = Okio.buffer(Okio.sink(this))
    Moshi.Builder()
            .build()
            .adapter(Response::class.java)
            .toJson(bufferedSink, response)
    bufferedSink.emit()
}


import com.squareup.moshi.Moshi
import okio.Okio
import okio.Sink
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.InputStreamReader

class HandlerShould {

    data class Input(val input: String)
    data class Output(val output: String)

    @Test
    fun not_a_test_but_a_spike_using_moshi_with_okio() {
        val input = "{\"input\":\"input\"}"
        val inputStream = input.byteInputStream()
        val inputObj = Moshi.Builder().build().adapter<Input>(Input::class.java).fromJson(Okio.buffer(Okio.source(inputStream)))

        println(inputObj)

        val output = Output("output")

        val outputStream = ByteArrayOutputStream()

        outputStream.write("hello".toByteArray())

        println(outputStream.toString())

        outputStream.reset()

        val sink = Okio.buffer(Okio.sink(outputStream))
        Moshi.Builder().build().adapter<Output>(Output::class.java).toJson(sink, output)
        sink.emit()

        val outputStr = outputStream.toString()

        println(outputStr)
    }
}
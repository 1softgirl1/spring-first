import org.assertj.core.api.Assertions
import java.util.UUID
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

val baseUrl: String by env
val path: String by env

POST("$baseUrl/$path") {
    contentType("application/json")
    body("""
        {
            "name": "Ivan",
            "surname": "Ivanov"
        }
    """.trimIndent())
} then {
    Assertions.assertThat(code).isEqualTo(200)

    val responseBodyText = body?.string() ?: ""
    println(responseBodyText)

    Assertions.assertThat(responseBodyText).contains("\"id\"")
    Assertions.assertThat(responseBodyText).contains("\"text\"")

    val mapper = jacksonObjectMapper()
    val json = mapper.readValue<Map<String, Any>>(responseBodyText)
    val userId = UUID.fromString(json["id"].toString())
    println("Saved userId: $userId")

    GET("$baseUrl/$path/$userId") {
        accept("application/json")
    } then {
        Assertions.assertThat(code).isEqualTo(200)
        val getResponse = body?.string() ?: ""
        println(getResponse)

        Assertions.assertThat(getResponse).contains("\"name\"")
        Assertions.assertThat(getResponse).contains("\"surname\"")
    }
}



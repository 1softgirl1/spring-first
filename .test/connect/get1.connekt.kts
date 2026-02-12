import org.assertj.core.api.Assertions

val baseUrl: String by env
val path: String by env

GET("$baseUrl/$path") {
    accept("application/json")
} then {
    val responseBody = body?.string() ?: throw IllegalStateException("Body is null")
    Assertions.assertThat(code).isEqualTo(200)
    Assertions.assertThat(responseBody).contains("\"text\"")
    println(responseBody)
}
import java.io.File
import java.nio.charset.Charset

fun File.detectEncoding(): Charset {
    val data = this.readBytes()

    println("Прочитано байт из файла: ${data.size}")

    if (data.size >= 2) {
        val first = data[0]
        val second = data[1]

        println("Первые два байта файла: ${first.toUByte().toString(16)} ${second.toUByte().toString(16)}")

        if (first == 0xFE.toByte() && second == 0xFF.toByte()) {
            println("Обнаружена кодировка: UTF-16BE")
            return Charset.forName("UTF-16BE")
        } else if (first == 0xFF.toByte() && second == 0xFE.toByte()) {
            println("Обнаружена кодировка: UTF-16LE")
            return Charset.forName("UTF-16LE")
        }
    }

    println("Кодировка по умолчанию: UTF-8 (BOM не найден)")
    return Charset.forName("UTF-8")
}

fun main() {
    val file = File("C:\\file.txt")

    if (!file.exists()) {
        println("Ошибка: файл не найден!")
        return
    }

    val encoding = file.detectEncoding()
    println("Определённая кодировка файла: $encoding")

    val content = file.readText(encoding)
    println("Содержимое файла:")
    println("--------------------------------------------------")
    println(content)
    println("--------------------------------------------------")
}

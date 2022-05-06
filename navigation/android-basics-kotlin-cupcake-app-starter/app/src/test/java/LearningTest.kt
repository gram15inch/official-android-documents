
import kotlinx.coroutines.*
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val formatter = DateTimeFormatter.ISO_LOCAL_TIME
val time = { formatter.format(LocalDateTime.now()) }
class LearningTest {

    @Test
    fun test1(){
        repeat(3) {
            GlobalScope.launch {
                println("Hi from ${Thread.currentThread()}")
            }
        }

        runBlocking {
            val num1 = async { getValue() }
            val num2 = async { getValue() }
            println("result of num1 + num2 is ${num1.await() + num2.await()}")
        }
    }

    suspend fun getValue(): Double {
        println("entering getValue() at ${time()}")
        delay(3000)
        println("leaving getValue() at ${time()}")
        return Math.random()
    }


}
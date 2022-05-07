
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


val formatter = DateTimeFormatter.ISO_LOCAL_TIME
val time = { formatter.format(LocalDateTime.now()) }

suspend fun getValue(): Double {
    println("entering getValue() at ${time()}")
    delay(3000)
    println("leaving getValue() at ${time()}")
    return Math.random()
}

fun main() {

test2()
}

fun  test2(){
    val states = arrayOf("Starting", "Doing Task 1", "Doing Task 2", "Ending")
    runBlocking {
        repeat(3) {
            launch {
                println("${Thread.currentThread()} has started")

                for (i in states) {
                    println("${Thread.currentThread()} - $i")
                    delay(50)
                }
            }
        }
    }

}



fun test0(){
     runBlocking {
       val num1 = getValue()
       println("num1 btw num2")
       val num2 = getValue()
       println("result of num1 + num2 is ${num1 + num2}")
   }
}
fun test1(){
    runBlocking {

        val num1 = async { getValue() }
        println("num1 btw num2")
        val num2 = async { getValue() }
        println("num2 btw num3")
        val num3 = async { getValue() }
        repeat(3){
            println(it+1)
            delay(1000)
        }
        println("result of num1 + num2 is ${num2.await() + num3.await()}")
    }


}
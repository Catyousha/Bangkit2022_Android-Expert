class Engine {
    fun start() {
        println("Engine started")
    }
}

class CarWrong {
    // tightly coupled, ga bebas pakai engine apa aja
    private val engine: Engine = Engine()
    fun start() {
        engine.start()
    }
}

// losely coupled, bebas pakai engine apa aja
class CarDI(val engine: Engine) {
    fun start() {
        engine.start()
    }
}

fun main(){
    val engine = Engine()
    val car = CarDI(engine)
    car.start()
}
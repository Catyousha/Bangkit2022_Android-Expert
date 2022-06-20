class Car(private val horsePowers: Int) {
    fun move() {
        println("Car is moving")
    }

    // SINGLETON #1
    companion object {
        @Volatile
        private var instance: Car? = null

        fun getInstance(horsePowers: Int): Car {
            return instance ?: synchronized(this) {
                instance ?: Car(horsePowers).also { instance = it }
            }
        }
    }
}

// SINGLETON #2
object CarFactory {
    val cars = mutableListOf<Car>()
    fun makeCar(horsePowers: Int): Car {
        val car = Car(horsePowers)
        cars.add(car)
        return car
    }
}

fun main() {
    // SINGLETON #2 CREATE INIT
    val car = CarFactory.makeCar(100)
    car.move()
}

fun another_func(){
    // SINGLETON #2 GET INSTANCE
    val car = CarFactory.cars[0]
    car.move()

    // SINGLETON #1 INIT & GET INSTANCE
    // ga peduli udah dibikin dulu atau belum
    val car2 = Car.getInstance(100)
}
package creational

class Handphone private constructor(builder: Builder) {
    private val processor: String = builder.processor
    private val battery: String = builder.battery
    private val screenSize: String = builder.screenSize

    // Builder class
    class Builder(processor: String) {
        var processor: String = processor // wajib ada

        var battery: String = "4000MAH"
        var screenSize: String = "6inch"

        fun setBattery(battery: String): Builder {
            this.battery = battery
            return this
        }

        fun setScreenSize(screenSize: String): Builder {
            this.screenSize = screenSize
            return this
        }

        fun create(): Handphone{
            return Handphone(this)
        }
    }
}

fun main() {
    // langsung disambungkan dengan builder, Handphone ga perlu di-construct
    val myPhone = Handphone.Builder("Snapdragon 855")
        .setBattery("4000MAH")
        .setScreenSize("6inch")
        .create()
}
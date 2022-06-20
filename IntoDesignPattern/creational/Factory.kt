interface IHandphone {
    var processor: String
    var battery: String
    var screenSize: String
}

// udah ada template buat berbagai jenis tipe handphone
class HandphoneNexus5 : IHandphone {
    override var processor = "Snapdragon"
    override var battery = "2300 mAh"
    override var screenSize = "4.95 inch"
}

class HandphoneNexus9 : IHandphone {
    override var processor = "Nvidia Tegra"
    override var battery = "6700 mAh"
    override var screenSize = "8.9 inch"
}

enum class Type {
    NEXUS5, NEXUS9
}

class HandPhoneFactory {
    companion object {
        // pilih pakai yang mana
        fun createHandphone(type: Type): IHandphone {
            return when (type) {
                Type.NEXUS5 -> HandphoneNexus5()
                Type.NEXUS9 -> HandphoneNexus9()
            }
        }
    }
}

fun main() {
    val myPhone = HandPhoneFactory.createHandphone(Type.NEXUS5)
}
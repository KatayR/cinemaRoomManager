import java.util.Locale

var soldTicket = 0
var currentIncome = 0
var totalIncome= 0

fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatPerRow = readln().toInt()

    // creates seats
    val seatList = MutableList(rows) {"S".repeat(seatPerRow).toMutableList()}

    while (true) {
        showMenu()
        when (readln()) {
            "1" -> printSeats(seatPerRow, rows, seatList)
            "2" -> buySeat(seatPerRow, rows, seatList)
            "3" -> showStatistics(seatPerRow, rows)
            "0" -> break
        }
    }
}

fun showMenu() {
    println("""
        
        1. Show the seats
        2. Buy a ticket
        3. Statistics
        0. Exit
    """.trimIndent())
}

fun showStatistics(seatPerRow: Int, rows: Int) {
    val percentage = "%.2f".format(soldTicket / (seatPerRow * rows / 100.00))
    totalIncome = if (seatPerRow * rows > 60) {
        (seatPerRow * rows * 10) / 100 * 90
    } else seatPerRow * rows * 10


    println("""
        
        Number of purchased tickets: $soldTicket
        Percentage: ${percentage.format(Locale.US)}%
        Current income: $$currentIncome
        Total income: $$totalIncome
    """.trimIndent())
}

fun calculatePrice (seatPerRow: Int, rows: Int, boughtRow: Int) {
    val isMoreThan60Seats = seatPerRow * rows > 60
    val isEvenAmountOfRow = rows % 2 == 0
    val isBackRow = if (isEvenAmountOfRow) {
        boughtRow > rows / 2
    } else boughtRow > rows / 2

    if (isMoreThan60Seats && isBackRow) {
        currentIncome += 8
        println("\nTicket price: \$8")
    } else {
        currentIncome += 10
        println("\nTicket price: \$10")
    }
}

fun printSeats(seatPerRow: Int, rows: Int, seatList: MutableList<MutableList<Char>>) {
    print("\nCinema:\n ")
    for (i in 1..seatPerRow) print(" $i")
    println("")
    for (i in 0 until rows) {
        println("${i + 1} ${seatList[i].joinToString(" ")}")
    }
}

fun buySeat(seatPerRow: Int, rows: Int, seatList: MutableList<MutableList<Char>> ) {
    var sold = false
    do {
        println("\nEnter a row number: ")
        val boughtRow = readln().toInt()
        println("Enter a seat number in that row: ")
        val boughtSeat = readln().toInt()
        // mark as bought
        try {
            if (seatList[boughtRow - 1][boughtSeat - 1] != 'B') {

                seatList[boughtRow - 1][boughtSeat - 1] = 'B'
                soldTicket++
                sold = true
                calculatePrice(seatPerRow, rows, boughtRow)
            } else println("That ticket has already been purchased!")
        } catch (e: IndexOutOfBoundsException) {
            println("Wrong input!")
        }
    } while (!sold)
}

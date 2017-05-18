import java.io.File

/**
 * Name: Alex Oladele
 * Unique-ID: OLADELAA
 * Date: 5/17/17
 * Project: PokeDex
 */

fun main(args: Array<String>) {
    process()
}

fun process() {
    var quit = false
    val pokedex = mutableMapOf<String, String>()
    do {
        print("Enter PokeDex command: ")

        val fullLine = readLine()
        val command: String
        val info: String?
        if (fullLine!!.contains(" ")) {
            info = fullLine.substring(fullLine.indexOf(" ")).trim()
        } else {
            info = null
        }
        if (info == null) {
            command = fullLine.substring(0).trim()
        } else {
            command = fullLine.substring(0, fullLine.indexOf(" ")).trim()
        }

        when (command) {
            "PUT" -> pokePut(info!!, pokedex)
            "GET" -> pokeGet(info!!, pokedex)
            "FIND" -> pokeFind(info!!, pokedex)
            "DELETE" -> pokeDelete(info!!, pokedex)
            "SAVE" -> pokeSave(pokedex)
            "LOAD" -> pokeLoad(pokedex)
            "QUIT" -> quit = true
            else -> {
                println("400 Bad Request. Please Try Again")
            }
        }
    } while (!quit)
}

fun pokeLoad(pokedex: MutableMap<String, String>) {
    if (File("./pokedex.sav").exists()) {
        val tempList = File("./pokedex.sav").readLines()
        tempList
                .map { it.split("-") }
                .forEach { pokedex.put(it[0], it[1]) }
        println("Pokedex successfully loaded!")
    } else {
        println("There is no Save file to load!")
    }
}

fun pokeSave(pokedex: MutableMap<String, String>) {
    File("./pokedex.sav").writeText(pokedex.entries.joinToString { "${it.key}-${it.value} \n\r" })
    println("Pokedex Successfully Saved!")
}

fun pokeDelete(command: String, pokedex: MutableMap<String, String>) {
    if (pokedex.containsKey(command)) {
        pokedex.remove(command)
        println("$command released back into the wild!")
    } else {
        println("$command not found in pokedex")
    }
}

fun pokeFind(command: String, pokedex: MutableMap<String, String>) {
    val listOfKeys = pokedex.keys
    listOfKeys
            .filter { it.contains(command) }
            .forEach { println(it) }
}

fun pokeGet(command: String, pokedex: MutableMap<String, String>) {
    if (pokedex.containsKey(command)) println(pokedex.getValue(command)) else println("That Pokemon has not been caught yet!")
}

fun pokePut(command: String, pokedex: MutableMap<String, String>) {
    val pokedexFile = File("pokemons.txt")
    val pokeList = pokedexFile.readLines()
    val pokemonName = command.substring(0, command.indexOf(" ")).toLowerCase()
    val pokemonInfo = command.substring(command.indexOf(" "))

    if (pokeList.contains(pokemonName)) {
        pokedex.put(pokemonName, pokemonInfo)
        println("$pokemonName has been added to Pokedex!")
    } else {
        println("Pokemon with name $pokemonName does not seem to exist")
    }
}


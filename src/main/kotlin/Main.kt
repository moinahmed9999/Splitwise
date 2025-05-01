import command.CommandExecutorFactory
import entity.Balances

fun main(args: Array<String>) {
    val commands = InputParser.parse()
    val balances = Balances(mutableMapOf())
    commands.forEach {
        CommandExecutorFactory.getCommandExecutor(it).execute(it, balances)
    }
}
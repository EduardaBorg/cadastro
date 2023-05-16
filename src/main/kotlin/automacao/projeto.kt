package automacao

import java.util.Scanner

object EmployeeRegistrationTool {
    private val scanner = Scanner(System.`in`)
    private val employees = mutableListOf<Employee>()

    @JvmStatic
    fun main(args: Array<String>) {
        while (true) {
            printMenu()
            val choice = readInt()
            when (choice) {
                1 -> {
                    registerEmployee()
                    waitForEnter()
                }
                2 -> {
                    listEmployees()
                    waitForEnter()
                }
                3 -> break
                else -> println("Opção inválida. Tente novamente.")
            }
        }
    }

    private fun printMenu() {
        println("Bem-vindo ao Employee Registration Tool")
        println("1 - Cadastrar funcionário")
        println("2 - Listar funcionários cadastrados")
        println("3 - Sair")
        print("Escolha uma opção: ")
    }

    private fun readInt(): Int {
        return scanner.nextInt().also { scanner.nextLine() }
    }

    private fun readString(): String {
        return scanner.nextLine()
    }

    private fun registerEmployee() {
        println("Cadastro de funcionário")
        print("Nome: ")
        val name = readString()
        print("Idade: ")
        val age = readInt()
        print("Cargo: ")
        val position = readString()

        val employee = Employee(name, age, position)
        employees.add(employee)

        println("Funcionário cadastrado com sucesso!")
    }

    private fun listEmployees() {
        if (employees.isEmpty()) {
            println("Nenhum funcionário cadastrado.")
        } else {
            println("Funcionários cadastrados:")
            employees.forEachIndexed { index, employee ->
                println("${index + 1}. ${employee.name} - ${employee.age} anos - ${employee.position}")
            }
        }
    }

    private fun waitForEnter() {
        println("Pressione Enter para continuar...")
        readString()
    }
}



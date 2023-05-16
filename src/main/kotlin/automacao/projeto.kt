package automacao

import java.io.File
import java.util.*

data class Employee(val name: String, val position: String, val salary: Double)

class EmployeeManagementTool {
    private val scanner = Scanner(System.`in`)
    private val employees = mutableListOf<Employee>()
    private val FILE_PATH = "employees.txt"

    init {
        loadEmployeesFromFile()
    }

    fun run() {
        while (true) when (showMenuAndGetChoice()) {
            1 -> registerEmployee()
            2 -> listEmployees()
            3 -> deleteEmployee()
            4 -> return
            else -> println("Opção inválida. Tente novamente.")
        }.also { saveEmployeesToFile() }
    }

    private fun showMenuAndGetChoice(): Int {
        println("""
            Bem-vindo ao Employee Management Tool
            1 - Cadastrar funcionário
            2 - Listar funcionários cadastrados
            3 - Excluir funcionário
            4 - Sair
            Escolha uma opção: 
        """.trimIndent())
        return readInt()
    }

    private fun readInt(): Int {
        val result = scanner.nextInt()
        scanner.nextLine()  // Consume newline left-over
        return result
    }

    private fun readDouble(): Double {
        while (true) {
            try {
                val result = scanner.nextDouble()
                scanner.nextLine()  // Consume newline left-over
                return result
            } catch (e: InputMismatchException) {
                println("Valor inválido. Digite um número válido.")
                scanner.nextLine()
            }
        }
    }

    private fun readString(): String = scanner.nextLine().trim()

    private fun registerEmployee() {
        println("Cadastro de funcionário")
        val fullName = readInput("Nome completo: ")
        val position = readInput("Cargo: ")
        val salary = readDoubleInput("Salário: ")

        val employee = Employee(fullName, position, salary)
        employees.add(employee)

        println("Funcionário cadastrado com sucesso!")
        waitForEnter()
    }

    private fun listEmployees() {
        if (employees.isEmpty()) {
            println("Nenhum funcionário cadastrado.")
        } else {
            println("Funcionários cadastrados:")
            employees.forEachIndexed { index, employee ->
                println("${index + 1}. Nome: ${employee.name}, Cargo: ${employee.position}, Salário: ${employee.salary}")
            }
        }
        waitForEnter()
    }

    private fun deleteEmployee() {
        if (employees.isEmpty()) {
            println("Nenhum funcionário cadastrado.")
            return
        }

        val name = readInput("Digite o nome do funcionário a ser excluído: ")

        val foundEmployees = employees.filter {
            it.name.split(" ")[0].equals(name, ignoreCase = true) ||
                    it.name.equals(name, ignoreCase = true)
        }

        if (foundEmployees.isNotEmpty()) {
            println("Foram encontrados ${foundEmployees.size} funcionário(s) com o nome \"$name\":")
            foundEmployees.forEachIndexed { index, employee ->
                println("${index + 1}. Nome: ${employee.name}, Cargo: ${employee.position}, Salário: ${employee.salary}")
            }

            val deleteChoice = readIntInput("Digite o número do funcionário que deseja excluir (ou 0 para cancelar): ")
            if (deleteChoice in 1..foundEmployees.size) {
                val employeeToDelete = foundEmployees[deleteChoice - 1]
                employees.remove(employeeToDelete)
                println("Funcionário removido com sucesso!")
            } else if (deleteChoice != 0) {
                println("Opção inválida. Nenhum funcionário foi excluído.")
            }
        } else {
            println("Funcionário não encontrado.")
        }
        waitForEnter()
    }

    private fun loadEmployeesFromFile() {
        val file = File(FILE_PATH)
        if (file.exists()) {
            file.useLines { lines ->
                lines.forEach { line ->
                    val parts = line.split(",")
                    if (parts.size == 3) {
                        val name = parts[0].trim()
                        val position = parts[1].trim()
                        val salary = parts[2].trim().toDoubleOrNull()
                        if (salary != null) {
                            val employee = Employee(name, position, salary)
                            employees.add(employee)
                        }
                    }
                }
            }
        }
    }

    private fun saveEmployeesToFile() {
        val file = File(FILE_PATH)
        file.bufferedWriter().use { writer ->
            employees.forEach { employee ->
                writer.write("${employee.name},${employee.position},${employee.salary}")
                writer.newLine()
            }
        }
    }

    private fun waitForEnter() {
        println("Pressione Enter para continuar...")
        readString() // Aguarda a próxima linha em branco
    }

    private fun readInput(prompt: String): String {
        print(prompt)
        return readString()
    }

    private fun readIntInput(prompt: String): Int {
        print(prompt)
        return readInt()
    }

    private fun readDoubleInput(prompt: String): Double {
        print(prompt)
        return readDouble()
    }
}

fun main() {
    val employeeManagementTool = EmployeeManagementTool()
    employeeManagementTool.run()
}


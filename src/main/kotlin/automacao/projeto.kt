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
        while (true) {
            val choice = showMenuAndGetChoice()
            when (choice) {
                1 -> registerEmployee()
                2 -> listEmployees()
                3 -> deleteEmployee()
                4 -> break
                else -> println("Opção inválida. Tente novamente.")
            }
        }

        saveEmployeesToFile()
    }

    private fun showMenuAndGetChoice(): Int {
        println("Bem-vindo ao Employee Management Tool")
        println("1 - Cadastrar funcionário")
        println("2 - Listar funcionários cadastrados")
        println("3 - Excluir funcionário")
        println("4 - Sair")
        print("Escolha uma opção: ")
        return readInt()
    }

    private fun readInt(): Int {
        return scanner.nextInt()
    }

    private fun readString(): String {
        return scanner.next().trim()
    }

    private fun registerEmployee() {
        println("Cadastro de funcionário")
        print("Nome: ")
        val name = readString()

        print("Cargo: ")
        val position = readString()

        print("Salário: ")
        val salary = readDouble()

        val employee = Employee(name, position, salary)
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

        print("Digite o nome do funcionário a ser excluído: ")
        val name = readString()

        val foundEmployees = employees.filter { it.name.equals(name, ignoreCase = true) }
        if (foundEmployees.isNotEmpty()) {
            println("Foram encontrados ${foundEmployees.size} funcionário(s) com o nome \"$name\":")
            foundEmployees.forEachIndexed { index, employee ->
                println("${index + 1}. Nome: ${employee.name}, Cargo: ${employee.position}, Salário: ${employee.salary}")
            }

            print("Digite o número do funcionário que deseja excluir (ou 0 para cancelar): ")
            val deleteChoice = readInt()
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

    private fun readDouble(): Double {
        return try {
            scanner.nextDouble()
        } catch (e: InputMismatchException) {
            println("Valor inválido. Digite um número válido.")
            scanner.nextLine() // Limpa o buffer do scanner
            readDouble()
        }
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
        scanner.nextLine() // Limpa a quebra de linha pendente
        scanner.nextLine() // Aguarda a próxima linha em branco
    }

}

fun main() {
    val employeeManagementTool = EmployeeManagementTool()
    employeeManagementTool.run()
}
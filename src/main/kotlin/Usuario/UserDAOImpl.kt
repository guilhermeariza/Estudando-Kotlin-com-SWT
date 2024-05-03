package Usuario

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

class UserDAOImpl : IUserDAO {
    private val url = "jdbc:postgresql://localhost:5432/kotlinGui"
    private val username = "postgres"
    private val password = "123123"

    override fun createUser(user: User) {
        val searchIfExistsWithName = "SELECT * FROM usuarios WHERE nome = ?"
        val userSearched = executeQuerySingle(searchIfExistsWithName, user.username)

        //isso nao funciona kkkkkkk
        if(userSearched != null){
            val sql = "INSERT INTO usuarios (nome, senha) VALUES (?, ?)"
            executeUpdate(sql, user.username, user.password)
        }
    }

    override fun getUser(username: String): User? {
        val sql = "SELECT * FROM usuarios WHERE nome = ?"
        return executeQuerySingle(sql, username)
    }

    override fun updateUser(user: User) {
        val sql = "UPDATE usuarios SET senha = ? WHERE nome = ?"
        executeUpdate(sql, user.password, user.username)
    }

    override fun deleteUser(username: String) {
        val sql = "DELETE FROM usuarios WHERE nome = ?"
        executeUpdate(sql, username)
    }

    override fun logIn(username: String, password: String): Boolean {
        val sql = "SELECT * FROM usuarios WHERE nome = ? AND senha = ?"
        val searchLogin:User? = executeQuerySingle(sql, username, password)

        if(searchLogin != null){
            return true
        }else{
            return false
        }
    }


    private fun executeUpdate(sql: String, vararg params: String) {
        var connection: Connection? = null
        var preparedStatement: PreparedStatement? = null
        try {
            connection = DriverManager.getConnection(url, username, password)
            preparedStatement = connection.prepareStatement(sql)
            params.forEachIndexed { index, param -> preparedStatement.setString(index + 1, param) }
            preparedStatement.executeUpdate()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            preparedStatement?.close()
            connection?.close()
        }
    }

    private fun executeQuerySingle(sql: String, vararg params: String): User? {
        var connection: Connection? = null
        var preparedStatement: PreparedStatement? = null
        var resultSet: ResultSet? = null
        try {
            connection = DriverManager.getConnection(url, username, password)
            preparedStatement = connection.prepareStatement(sql)
            params.forEachIndexed { index, param -> preparedStatement.setString(index + 1, param) }
            resultSet = preparedStatement.executeQuery()
            if (resultSet.next()) {
                val nome = resultSet.getString("nome")
                val senha = resultSet.getString("senha")
                return User(nome, senha)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            resultSet?.close()
            preparedStatement?.close()
            connection?.close()
        }
        return null
    }
}

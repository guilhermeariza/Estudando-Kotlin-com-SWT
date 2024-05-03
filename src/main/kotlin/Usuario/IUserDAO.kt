package Usuario

interface IUserDAO {
    fun createUser(user: User)
    fun getUser(username: String): User?
    fun updateUser(user: User)
    fun deleteUser(username: String)
    fun logIn(username:String, password: String): Boolean
}


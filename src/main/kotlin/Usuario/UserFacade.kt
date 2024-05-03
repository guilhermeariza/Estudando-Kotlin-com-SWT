package Usuario

class UserFacade(private val userDAO: IUserDAO) {
    fun createUser(username: String, password: String) {
        userDAO.createUser(User(username, password))
    }

    fun getUser(username: String): User? {
        return userDAO.getUser(username)
    }

    fun updateUser(username: String, password: String) {
        val user = getUser(username)
        if (user != null) {
            userDAO.updateUser(User(username, password))
        }
    }

    fun deleteUser(username: String) {
        userDAO.deleteUser(username)
    }

    fun logIn(username:String, password: String):Boolean{
        return userDAO.logIn(username, password)
    }
}


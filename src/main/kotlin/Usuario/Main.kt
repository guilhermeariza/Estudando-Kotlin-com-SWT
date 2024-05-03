package Usuario

import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*

val userDAO = UserDAOImpl()
val userFacade = UserFacade(userDAO)

fun main() {

    val display = Display()
    val shell = Shell(display)
    shell.text = "Cadastro de Usuário"
    shell.layout = GridLayout(2, false)

    val usernameLabel = Label(shell, SWT.NONE)
    usernameLabel.text = "Usuário:"
    val usernameText = Text(shell, SWT.BORDER)
    val usernameGd = GridData(SWT.FILL, SWT.CENTER, true, false)
    usernameGd.horizontalSpan = 2
    usernameText.layoutData = usernameGd

    val passwordLabel = Label(shell, SWT.NONE)
    passwordLabel.text = "Senha:"
    val passwordText = Text(shell, SWT.BORDER or SWT.PASSWORD)
    val passwordGd = GridData(SWT.FILL, SWT.CENTER, true, false)
    passwordGd.horizontalSpan = 2
    passwordText.layoutData = passwordGd

    val registerButton = Button(shell, SWT.PUSH)
    registerButton.text = "Cadastrar"
    val registerGd = GridData(SWT.FILL, SWT.CENTER, true, false)
    registerGd.horizontalSpan = 2
    registerButton.layoutData = registerGd
    registerButton.addListener(SWT.Selection) {
        val username = usernameText.text
        val password = passwordText.text
        if (username.isNotEmpty() && password.isNotEmpty()) {
            userFacade.createUser(username, password)
            switchToLogin(shell, display)
        } else {
            println("Por favor, preencha todos os campos!")
        }
    }

    val loginLink = Link(shell, SWT.NONE)
    loginLink.text = "Já tem uma conta? <a>Login</a>"
    loginLink.addListener(SWT.Selection) {
        switchToLogin(shell, display)
    }

    shell.pack()
    shell.open()

    while (!shell.isDisposed) {
        if (!display.readAndDispatch()) {
            display.sleep()
        }
    }
    display.dispose()
}

fun switchToLogin(previousShell: Shell, display: Display) {
    previousShell.dispose()

    val loginShell = Shell(display)
    loginShell.text = "Login"
    loginShell.layout = GridLayout(2, false)

    val usernameLabel = Label(loginShell, SWT.NONE)
    usernameLabel.text = "Usuário:"
    val usernameText = Text(loginShell, SWT.BORDER)
    val usernameGd = GridData(SWT.FILL, SWT.CENTER, true, false)
    usernameGd.horizontalSpan = 2
    usernameText.layoutData = usernameGd

    val passwordLabel = Label(loginShell, SWT.NONE)
    passwordLabel.text = "Senha:"
    val passwordText = Text(loginShell, SWT.BORDER or SWT.PASSWORD)
    val passwordGd = GridData(SWT.FILL, SWT.CENTER, true, false)
    passwordGd.horizontalSpan = 2
    passwordText.layoutData = passwordGd

    val loginButton = Button(loginShell, SWT.PUSH)
    loginButton.text = "Login"
    val loginGd = GridData(SWT.FILL, SWT.CENTER, true, false)
    loginGd.horizontalSpan = 2
    loginButton.layoutData = loginGd
    loginButton.addListener(SWT.Selection) {
        val username = usernameText.text
        val password = passwordText.text
        if (username.isNotEmpty() && password.isNotEmpty()) {
            val existsInDB = userFacade.logIn(username, password)
            if(existsInDB == true){
                switchToWelcome(loginShell, display, username)

            }else{
                println("Credenciais incorretas!")
            }
        } else {
            println("Por favor, preencha todos os campos!")
        }
    }

    loginShell.pack()
    loginShell.open()

    while (!loginShell.isDisposed) {
        if (!display.readAndDispatch()) {
            display.sleep()
        }
    }
    display.dispose()
}

fun switchToWelcome(previousShell: Shell, display: Display, username: String) {
    previousShell.dispose()

    val welcomeShell = Shell(display)
    welcomeShell.text = "Bem-vindo"
    welcomeShell.layout = GridLayout(1, true)

    val welcomeLabel = Label(welcomeShell, SWT.NONE)
    welcomeLabel.text = "Bem-vindo, $username!"
    welcomeLabel.layoutData = GridData(SWT.CENTER, SWT.CENTER, true, true)

    welcomeShell.pack()
    welcomeShell.open()

    while (!welcomeShell.isDisposed) {
        if (!display.readAndDispatch()) {
            display.sleep()
        }
    }
    display.dispose()
}

package pt.isel.icd;

import java.io.IOException;
import pt.isel.icd.communication.SchemaValidator;
import pt.isel.icd.communication.Server;
import pt.isel.icd.communication.SimpleSocketManager;
import pt.isel.icd.database.XmlFileStore;
import pt.isel.icd.serialization.CommandRegistry;
import pt.isel.icd.serialization.CommandSerializer;
import pt.isel.icd.user.management.UserServerRepository;

public class ServerApplication {

    public static void main(String[] args) throws IOException {
        // Serialization
        CommandSerializer commandSerializer = new CommandSerializer();
        CommandRegistry.registerAll(commandSerializer);

        // Validation
        SchemaValidator schemaValidator = new SchemaValidator();

        // Communication
        SimpleSocketManager simpleSocketManager = new SimpleSocketManager();
        Server server = new Server(
            simpleSocketManager,
            commandSerializer,
            schemaValidator
        );

        // Base de dados.
        // O diretorio de dados e configuravel via a propriedade de sistema
        // "dab.data.dir" (ex.: -Ddab.data.dir=/data nos contentores Docker),
        // deixando de depender do diretorio de trabalho atual (resolve a L7).
        // Por omissao usa "src/main/resources" para preservar a execucao local.
        String dataDir = System.getProperty(
            "dab.data.dir",
            "src/main/resources"
        );
        XmlFileStore xmlFileStore = new XmlFileStore();
        xmlFileStore.setFileStorePath(dataDir);
        System.out.println("Diretorio de dados: " + dataDir);

        // Repository
        UserServerRepository userServerRepository = new UserServerRepository(
            xmlFileStore
        );
        userServerRepository.loadUsers();
        userServerRepository.loadProfiles();

        // Controller
        ServerController serverController = new ServerController(
            userServerRepository,
            simpleSocketManager
        );

        // Authentication
        simpleSocketManager.setAuthenticator(serverController);

        // Register controller
        server.addController(serverController);

        // Start server
        System.out.println("Starting Dots and Boxes Server...");
        server.listen();
    }
}

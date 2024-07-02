### Pre-requisites

- **Maven**
- **Java 17**

### Project Overview

This project demonstrates examples of blocking and non-blocking (NIO) server-client connections.

In the `connection` package, you will find the implementation of both blocking and non-blocking servers and clients.

#### Project Structure

Here is an overview of the key files in the project:

```
/player-message-exchange/src/main/java/com/test/blocking/OtherPlayerMain.java
/player-message-exchange/src/main/java/com/test/blocking/InitiatorPlayerMain.java
/player-message-exchange/src/main/java/com/test/blocking/BlockingServerMain.java
/player-message-exchange/src/main/java/com/test/blocking/BlockingMain.java
/player-message-exchange/src/main/java/com/test/nonblocking/NonBlockingServerMain.java
/player-message-exchange/src/main/java/com/test/nonblocking/NonBlockingMain.java
/player-message-exchange/src/main/java/com/test/nonblocking/NonBlockingOtherPlayerMain.java
/player-message-exchange/src/main/java/com/test/nonblocking/NonBlockingInitiatorPlayerMain.java
/player-message-exchange/src/main/java/com/pme/connection/server/blocking/BlockingClientHandler.java
/player-message-exchange/src/main/java/com/pme/connection/server/blocking/BlockingServer.java
/player-message-exchange/src/main/java/com/pme/connection/server/nonblocking/NioNonBlockingServer.java
/player-message-exchange/src/main/java/com/pme/connection/server/IServer.java
/player-message-exchange/src/main/java/com/pme/connection/client/blocking/BlockingClient.java
/player-message-exchange/src/main/java/com/pme/connection/client/nonblocking/NioNonBlockingClient.java
/player-message-exchange/src/main/java/com/pme/connection/client/IClient.java
/player-message-exchange/src/main/java/com/pme/Player.java
```

### Running the Project

You have two options to run the project:

1. **Using Scripts**: The scripts are located in the `scripts` folder (outside the `src` folder).
2. **Running Main Files Directly**: If you choose to run the main files directly, ensure to run them in the following order to maintain proper execution flow:
    1. Server
    2. Initiator
    3. Other Player

### Using Scripts

You can run the project examples using the following scripts inside the `scripts` folder:

- **Blocking Example**:
  ```bash
  /bin/bash blocking-example.sh 
  ```

- **Blocking Example with Different PIDs**:
  ```bash
  /bin/bash blocking-example-different-pids.sh
  ```

- **Non-Blocking Example**:
  ```bash
  /bin/bash non-blocking-example.sh
  ```

- **Non-Blocking Example with Different PIDs**:
  ```bash
  /bin/bash non-blocking-example-different-pids.sh
  ```

### Notes

- **Blocking Example**: Demonstrates a server and clients using blocking I/O operations.
- **Non-Blocking Example**: Demonstrates a server and clients using non-blocking I/O operations (NIO).
- **Different PIDs**: Running scripts with different PIDs ensures that the server and clients run in separate processes.

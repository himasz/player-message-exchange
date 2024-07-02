### Pre-requisites 

- **Maven**
- **Java 17**

The project is developed to give an example of blocking and non-blocking (nio) server-client connection.
In the connection package you will find the implementation of the blocking and non-blocking server and client.

To runt the project, you could use the scripts in the scripts folder (outside src folder)
Or just run the main files directly .. You should know that running them in different PIDs would require running them in order (server, Initiator and other player).  


**Inside the scripts folder, please run them using the following scripts:**

```
/bin/bash blocking-example.sh 
/bin/bash blocking-example-different-pids.sh
/bin/bash non-blocking-example.sh
/bin/bash non-blocking-example-different-pids.sh
```

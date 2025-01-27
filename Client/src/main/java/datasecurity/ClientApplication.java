package datasecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@AutoConfigureOrder
public class ClientApplication {

    public static void main(String[] args) throws IOException {

        SpringApplication.run(ClientApplication.class, args);
        System.out.println("The client application is started\n" +
                "You can access the web server on port 8443\n" +
                "\u001B[38;5;208m"+"(to access the port, you need to trust the provided certificate in the certificate folder\"group10.crt\")"+"\u001B[37m");

    }



}

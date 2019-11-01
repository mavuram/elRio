package elrio.restservice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application

object Application extends App{
  val applicationContext = SpringApplication.run(classOf[Application])
}

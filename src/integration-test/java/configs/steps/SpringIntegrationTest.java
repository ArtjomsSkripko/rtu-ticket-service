package configs.steps;

import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ticket.TicketServiceApplication;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = TicketServiceApplication.class, loader = SpringBootContextLoader.class)
public class SpringIntegrationTest {
}


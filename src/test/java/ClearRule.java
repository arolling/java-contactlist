import org.junit.rules.ExternalResource;

public class ClearRule extends ExternalResource {

  protected void before() { }

  protected void after() {
    Contact.clear();
    PhoneNumber.clear();
    Email.clear();
    Address.clear();
  }
}

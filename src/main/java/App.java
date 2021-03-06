import java.util.HashMap;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;

public class App {
  public static void main(String[] args) {

    ProcessBuilder process = new ProcessBuilder();
     Integer port;
     if (process.environment().get("PORT") != null) {
         port = Integer.parseInt(process.environment().get("PORT"));
     } else {
         port = 4567;
     }
    setPort(port);


    String layout = "templates/layout.vtl";
    staticFileLocation("/public");

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String newFirstName = request.queryParams("firstName");
      String newLastName = request.queryParams("lastName");
      Contact newContact = new Contact(newFirstName, newLastName);

      model.put("allContacts", Contact.all());
      model.put("template", "/templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/displayContact", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int newID = Integer.parseInt(request.queryParams("chooseContact"));
      Contact selectedContact = Contact.getContactByID(newID);
      model.put("selectedContact", selectedContact);
      model.put("allContacts", Contact.all());
      model.put("template", "/templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/newPhone", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int newID = Integer.parseInt(request.queryParams("selectedContact"));
      Contact selectedContact = Contact.getContactByID(newID);
      String newPhone = request.queryParams("phoneNumber");
      String newPhoneType = request.queryParams("phoneType");
      PhoneNumber newPhoneNumber = new PhoneNumber(newPhone, newPhoneType);
      selectedContact.addPhoneNumber(newPhoneNumber);
      model.put("allContacts", Contact.all());
      model.put("template", "/templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


  }
} //end of App

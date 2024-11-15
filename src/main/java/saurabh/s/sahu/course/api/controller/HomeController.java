package saurabh.s.sahu.course.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(path = {"/", "/hello"})
    public String home()
    {
          return ("<h1>Welcome</h1>");
    }

    @GetMapping("/user")
    public String getUser()
    {
        return ("<h1>Welcome User</h1>");
    }

    @GetMapping("/admin")
    public String getAdmin()
    {
        return ("<h1>Welcome Admin</h1>");
    }

}

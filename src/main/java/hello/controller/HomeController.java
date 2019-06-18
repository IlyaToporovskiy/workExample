//package hello.controller;
//
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
////@RestController
////public class HomeController {
////
////
////    @GetMapping("/")
////    public String index() {
////        return "Welcome to the home page!";
////    }
////}
//
//@Controller
////@RequestMapping("/")
//public class HomeController {
//
//
//    @RequestMapping("/")
//    public String index()
//    {
//        UserDetails userDetails =
//                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        return "index";
//    }
////
////
//////    @RequestMapping(value = "profile", method = RequestMethod.GET)
//////    public ModelAndView interfaces()
//////    {
//////        return new ModelAndView("profile");
//////    }
//}
//

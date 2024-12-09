package com.example.demo.controller.web;

import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.authentication.BadCredentialsException;

@Controller
public class JwtController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/token") //No auth
    public String generateToken(Model m, HttpSession session, @ModelAttribute AuthenticationRequest authenticationRequest, HttpServletResponse res) throws Exception{
        AuthenticationResponse result;
        try{
            result = authenticationService.authenticate(authenticationRequest);
        } catch(UsernameNotFoundException e) {
            session.setAttribute("msg", "Bad credentials");
            return "redirect:/loginui";

        } catch(BadCredentialsException e)
        {
            session.setAttribute("msg","Bad Credentials");
            return "redirect:/loginui";
        }

     try {
        final String token = result.getToken();
        Cookie cookie = new Cookie("token",token);
        cookie.setMaxAge(Integer.MAX_VALUE);
         cookie.setPath("/");
        res.addCookie(cookie);
        return "redirect:/userui";
     }catch(Exception e)
     {
        session.setAttribute("msg","Credentials were right But something went wrong!!");
        return "redirect:/loginui";
     }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response){
        //invalidate session
        session.invalidate();

        //Remove token
        Cookie[] cookies =request.getCookies();
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    cookie.setValue(null);
                    cookie.setMaxAge(0); //Remove cookie
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
        return "redirect:/loginui?logout";
    }
}

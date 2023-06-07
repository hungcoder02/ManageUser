package com.example.usermanage.Controller;

import com.example.usermanage.DTO.RoleDTO;
import com.example.usermanage.DTO.UserDTO;
import com.example.usermanage.Model.Respone;
import com.example.usermanage.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
@PreAuthorize("hasAuthority('admin')")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<Respone> getAll(){
        Respone respone = userService.getAll();
        return ResponseEntity.status(respone.getStatus()).body(respone);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<Respone> getOne(@PathVariable long id){
        Respone respone = userService.getOne(id);
        return ResponseEntity.status(respone.getStatus()).body(respone);
    }
    @ResponseBody
    @RequestMapping(value = "/user/update/{id}", headers = {"content-type=application/json"},
            consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Respone> update(@RequestBody UserDTO userDTO, @PathVariable("id") long id){
        Respone respone = userService.update(userDTO, id);
        return ResponseEntity.status(respone.getStatus()).body(respone);
    }
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Respone> delete(@PathVariable long id){
        Respone respone = userService.delete(id);
        return ResponseEntity.status(respone.getStatus()).body(respone);
    }
    @RequestMapping(value = "/user/addRole/{id}", method = RequestMethod.POST)
    public ResponseEntity<Respone> addRole(@PathVariable long id, @RequestBody RoleDTO roleDTO){
        Respone respone = userService.addRole(roleDTO, id);
        return ResponseEntity.status(respone.getStatus()).body(respone);
    }
    @RequestMapping(value = "/user/deleteRole/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Respone> deleteRole(@PathVariable long id, @RequestBody RoleDTO roleDTO){
        Respone respone = userService.deleteRole(roleDTO, id);
        return ResponseEntity.status(respone.getStatus()).body(respone);
    }
}

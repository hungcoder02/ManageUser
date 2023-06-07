package com.example.usermanage.Service;

import com.example.usermanage.DTO.RoleDTO;
import com.example.usermanage.DTO.UserDTO;
import com.example.usermanage.Model.Respone;
import com.example.usermanage.Model.Role;
import com.example.usermanage.Model.User;
import com.example.usermanage.Repository.RoleRepo;
import com.example.usermanage.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    public Respone getAll(){
        List<User> users = userRepo.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users){
            UserDTO dto = new UserDTO();
            dto.setUserName(user.getName());

            for (Role role : user.getRoles()) {
                dto.getRole().add(role.getName());
            }
            userDTOS.add(dto);
        }
        Respone respone = new Respone<User>();
        respone.setStatus(200);
        respone.setMessage("successful");
        respone.setData(userDTOS);
        return respone;
    }

    public Respone getOne(long id){
        User user = userRepo.findById(id).orElse(null);
        Respone respone = new Respone<UserDTO>();
        if (user != null){
            List<UserDTO> userDTOS = new ArrayList<UserDTO>();
            UserDTO dto = new UserDTO();
            dto.setUserName(user.getName());
            for (Role role : user.getRoles()) {
                dto.getRole().add(role.getName());
            }
            userDTOS.add(dto);
            respone.setStatus(200);
            respone.setMessage("successful");
            respone.setData(userDTOS);
            return respone;
        }
        respone.setStatus(404);
        respone.setMessage("Error");
        return respone;
    }

    public Respone add(UserDTO userDTO){
        User user = userRepo.findByName(userDTO.getUserName()).orElse(new User(""));
        Respone respone = new Respone<UserDTO>();
        if (user.getName().equals("")){
            user.setName(userDTO.getUserName());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String rawPassword = userDTO.getPassword();
            String encoderPassword = encoder.encode(rawPassword);
            user.setPassword(encoderPassword);
            userRepo.save(user);

            List<UserDTO> userDTOS = new ArrayList<UserDTO>();
            userDTOS.add(userDTO);
            respone.setStatus(200);
            respone.setMessage("created");
            respone.setData(userDTOS);
            return respone;
        }else {
            respone.setStatus(400);
            respone.setMessage("User Existed");
            return respone;
        }
    }

    public Respone update(UserDTO userDTO, long id){
        User user = userRepo.findById(id).orElse(null);
        Respone respone = new Respone<UserDTO>();
//        neu user khong bi trong
        if(user != null){
//            kiem tra thuoc tinh username cua userDTO co rong hay k
            if(!userDTO.getUserName().equals("")){
                user.setName(userDTO.getUserName());
            }
            if (!userDTO.getPassword().equals("")){
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String rawPassword = userDTO.getPassword();
                String encodedPassword = encoder.encode(rawPassword);
                user.setPassword(encodedPassword);
            }
            userRepo.save(user);
            List<UserDTO> userDTOS = new ArrayList<UserDTO>();
            userDTOS.add(userDTO);
            respone.setStatus(200);
            respone.setMessage("Update");
            respone.setData(userDTOS);
        }
            respone.setStatus(404);
            respone.setMessage("Cant find");
            return respone;
    }

    public Respone delete(long id){
        User user = userRepo.findById(id).orElse(null);
        Respone respone = new Respone<>();
        if(user != null){
            respone.setStatus(200);
            respone.setMessage("Deleted");
            userRepo.delete(user);
            return respone;
        }
        respone.setStatus(404);
        respone.setMessage("Error");
        return respone;
    }

    //Role
    public Respone addRole(RoleDTO roleDTO, long id){
        User user = userRepo.findById(id).orElse(null);
        Respone respone = new Respone<User>();
        if(user != null){
            for (String s : roleDTO.getRoleName()){
                Role role = roleRepo.findByName(s);
                user.getRoles().add(role);
            }
            userRepo.save(user);
            respone.setStatus(200);
            respone.setMessage("update");
            List<User> users = new ArrayList<User>();
            respone.setData(users);
            return respone;
        }
        respone.setStatus(404);
        respone.setMessage("ERROR");
        return respone;
    }

    public Respone deleteRole(RoleDTO roleDTO, long id){
        User user = userRepo.findById(id).orElse(null);
        Respone respone = new Respone<User>();
        if(user != null){
            if(user.getRoles().size() > 0){
                for (String s : roleDTO.getRoleName()){
                    Role role = roleRepo.findByName(s);
                    user.getRoles().remove(role);
                }
                userRepo.save(user);
                respone.setStatus(200);
                respone.setMessage("Deleted");
                List<User> users = new ArrayList<User>();
                respone.setData(users);
                return respone;
            }
        }
        respone.setStatus(404);
        respone.setMessage("ERROR");
        return respone;
    }
    public User findByName(String name){
        return userRepo.findByName(name).get();
    }
}

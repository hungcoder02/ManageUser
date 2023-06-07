package com.example.usermanage.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {
        private String userName;
        private String password;
        private List<String> role = new ArrayList<String>();
}

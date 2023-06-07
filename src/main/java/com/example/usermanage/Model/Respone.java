package com.example.usermanage.Model;

import lombok.Data;

import java.util.List;

@Data
public class Respone <T>{
private int status;
private String message;
private List<T> data;
}

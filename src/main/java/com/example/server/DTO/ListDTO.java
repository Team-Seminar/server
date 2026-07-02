package com.example.server.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListDTO {
    List<?> data;
    int count;
    public ListDTO(List<?> list) {
        this.data=list;
        this.count = list.size();
    }
}

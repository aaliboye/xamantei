package com.example.xamantei.dto;

import lombok.Data;

@Data
public class AddCustomLinksDto {
    private String title;
    private String url;
    private int order;
    private String platform;

}

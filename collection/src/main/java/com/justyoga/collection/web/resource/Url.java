package com.justyoga.collection.web.resource;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Url {
    @NotEmpty private String url;
}

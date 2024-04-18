package net.lishen.dto.api;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author joseph,
 * @since 2024-03-06
 */
@Data
public class ApiDTO implements Serializable {


    private Long id;


    private Long projectId;


    private Long moduleId;


    private Long environmentId;


    private String name;


    private String description;


    private String level;


    private String path;

    private String method;


    private String query;


    private String header;


    private String body;


    private String bodyType;


    private Date gmtCreate;


    private Date gmtModified;
}

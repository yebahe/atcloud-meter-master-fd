package net.lishen.dto;

import lombok.Data;

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
public class ApiCaseStepDTO {

    private Long id;


    private Long projectId;


    private Long environmentId;


    private Long caseId;


    private Long num;

    private String name;

    private String description;


    private String assertion;


    private String relation;

    private String path;


    private String method;


    private String query;


    private String header;


    private String body;


    private String bodyType;


    private Date gmtCreate;


    private Date gmtModified;
}

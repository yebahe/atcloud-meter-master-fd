package net.lishen.dto.api;

import lombok.Data;
import net.lishen.dto.ApiCaseStepDTO;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author joseph,
 * @since 2024-03-06
 */
@Data
public class ApiCaseDTO {


    private Long id;


    private Long projectId;


    private Long moduleId;

    private String name;


    private String description;


    private String level;


    private Date gmtCreate;


    private Date gmtModified;

    private List<ApiCaseStepDTO> list;
}
